package com.github.tobiasmiosczka.nami.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.github.tobiasmiosczka.nami.R;
import com.github.tobiasmiosczka.nami.program.NaMiDataLoader;
import com.github.tobiasmiosczka.nami.program.NamiDataLoaderHandler;
import com.github.tobiasmiosczka.nami.program.SortedList;

import java.io.IOException;
import java.util.List;

import nami.connector.NamiConnector;
import nami.connector.NamiServer;
import nami.connector.credentials.NamiCredentials;
import nami.connector.exception.NamiLoginException;
import nami.connector.namitypes.NamiMitglied;
import nami.connector.namitypes.NamiSearchedValues;
import nami.connector.namitypes.enums.NamiMitgliedStatus;
import nami.connector.namitypes.enums.NamiStufe;

//TODO: Make thread safe
public class Nami implements NamiDataLoaderHandler {

    private NamiConnector namiConnector;
    private final MutableLiveData<List<NamiMitglied>> memberList = new MutableLiveData<>();
    private final MutableLiveData<List<NamiMitglied>> filteredmemberList = new MutableLiveData<>();

    private final MutableLiveData<String> nameFilter = new MutableLiveData<>();
    private final MutableLiveData<NamiStufe> stufeFilter = new MutableLiveData<>();

    private final MutableLiveData<Boolean> isLoggedIn = new MutableLiveData<>();
    private final MutableLiveData<Integer> progressCurrent = new MutableLiveData<>();
    private final MutableLiveData<Integer> progressMax = new MutableLiveData<>();

    private static final Nami NAMI = new Nami();

    public static Nami getInstance() {
        return NAMI;
    }

    private Nami() {
        memberList.setValue(new SortedList<>((o1, o2) -> (o1.getVorname() + o1.getNachname()).compareTo(o2.getVorname()+o2.getNachname())));
        filteredmemberList.setValue(new SortedList<>((o1, o2) -> (o1.getVorname() + o1.getNachname()).compareTo(o2.getVorname()+o2.getNachname())));
        isLoggedIn.setValue(false);
        nameFilter.setValue("");
        stufeFilter.setValue(null);

        memberList.observeForever(namiMitglieds -> updateFilter());
        nameFilter.observeForever(s -> updateFilter());
        stufeFilter.observeForever(stufe -> updateFilter());
    }

    public void login(String username, String password) throws IOException, NamiLoginException {
        namiConnector = new NamiConnector(NamiServer.LIVESERVER_WITH_API, new NamiCredentials(username, password));
        namiConnector.namiLogin();
        isLoggedIn.postValue(true);
        Nami.getInstance().loadData();
    }

    public void loadData() {
        NamiSearchedValues namiSearchedValues = new NamiSearchedValues();
        namiSearchedValues.setMitgliedStatus(NamiMitgliedStatus.AKTIV);
        NaMiDataLoader dataLoader = new NaMiDataLoader(this.namiConnector, namiSearchedValues, this);
        dataLoader.start();
    }

    public LiveData<List<NamiMitglied>> getFilteredMemberList() {
        return this.filteredmemberList;
    }

    public LiveData<String> getNameFilter() {
        return this.nameFilter;
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter.postValue(nameFilter);
    }

    public void setStufeFilter(NamiStufe stufe) {
        this.stufeFilter.postValue(stufe);
    }

    public LiveData<NamiStufe> getStufeFilter() {
        return stufeFilter;
    }

    public LiveData<Integer> getProgressCurrent() {
        return progressCurrent;
    }

    public LiveData<Integer> getProgressMax() {
        return progressMax;
    }

    public NamiMitglied getMemberById(int id) {
        for (NamiMitglied m : memberList.getValue()) {
            if (m.getId() == id)
                return m;
        }
        return null;
    }

    public LiveData<Boolean> getIsLoggedIn() {
        return this.isLoggedIn;
    }

    @Override
    public void onUpdate(int i, int i1, NamiMitglied namiMitglied) {
        List<NamiMitglied> m = memberList.getValue();
        m.add(namiMitglied);
        memberList.postValue(m);
        progressCurrent.postValue(i);
        progressMax.postValue(i1);
    }

    @Override
    public void onDone(long l) {

    }

    @Override
    public void onException(String s, Exception e) {
        //TODO:add exception handling
        e.printStackTrace();
    }

    public static int getTheme(NamiStufe stufe) {
        if (stufe == null)
            return R.style.AppTheme_Default;
        switch (stufe) {
            case WOELFLING: return R.style.AppTheme_Woelflinge;
            case JUNGPFADFINDER: return R.style.AppTheme_Jungpfadfinder;
            case PFADFINDER: return R.style.AppTheme_Pfadfinder;
            case ROVER: return R.style.AppTheme_Rover;
            default: return R.style.AppTheme_Default;
        }
    }

    private void updateFilter() {
        List<NamiMitglied> l = filteredmemberList.getValue();
        l.clear();
        for (NamiMitglied member : memberList.getValue()) {
            String n = member.getVorname() + " " + member.getNachname();
            //name
            if (!n.toLowerCase().contains(nameFilter.getValue().toLowerCase()))
                continue;
            //stufe
            if (stufeFilter.getValue() != null && member.getStufe() != stufeFilter.getValue())
                continue;
            l.add(member);
        }
        filteredmemberList.postValue(l);
    }
}
