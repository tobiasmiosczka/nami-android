package com.github.tobiasmiosczka.nami.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.github.tobiasmiosczka.nami.R;
import com.github.tobiasmiosczka.nami.program.NaMiDataLoader;
import com.github.tobiasmiosczka.nami.program.NamiDataLoaderHandler;
import com.github.tobiasmiosczka.nami.program.SortedList;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import nami.connector.NamiConnector;
import nami.connector.NamiServer;
import nami.connector.credentials.NamiCredentials;
import nami.connector.exception.NamiLoginException;
import nami.connector.namitypes.NamiMitglied;
import nami.connector.namitypes.NamiSearchedValues;
import nami.connector.namitypes.enums.NamiMitgliedStatus;
import nami.connector.namitypes.enums.NamiStufe;

public class Nami implements NamiDataLoaderHandler {

    private NamiConnector namiConnector;
    private MutableLiveData<List<NamiMitglied>> memberList = new MutableLiveData<>();
    private MutableLiveData<List<NamiMitglied>> filteredmemberList = new MutableLiveData<>();

    private MutableLiveData<String> nameFilter = new MutableLiveData<>();
    private MutableLiveData<NamiStufe> stufeFilter = new MutableLiveData<>();

    private MutableLiveData<Boolean> isLoggedIn = new MutableLiveData<>();
    private MutableLiveData<Integer> progressCurrent = new MutableLiveData<>();
    private MutableLiveData<Integer> progressMax = new MutableLiveData<>();

    private static Nami NAMI = new Nami();
    public static Nami getInstance() {return NAMI;}

    private Nami() {
        memberList.setValue(new SortedList<>(new Comparator<NamiMitglied>() {
            @Override
            public int compare(NamiMitglied o1, NamiMitglied o2) {
                return (o1.getVorname() + o1.getNachname()).compareTo(o2.getVorname()+o2.getNachname());
            }
        }));
        filteredmemberList.setValue(new SortedList<>(new Comparator<NamiMitglied>() {
            @Override
            public int compare(NamiMitglied o1, NamiMitglied o2) {
                return (o1.getVorname() + o1.getNachname()).compareTo(o2.getVorname()+o2.getNachname());
            }
        }));
        isLoggedIn.setValue(false);
        nameFilter.setValue("");
        stufeFilter.setValue(null);

        memberList.observeForever(new Observer<List<NamiMitglied>>() {
            @Override
            public void onChanged(List<NamiMitglied> namiMitglieds) {
                updateFilter();
            }
        });

        nameFilter.observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                updateFilter();
            }
        });

        stufeFilter.observeForever(new Observer<NamiStufe>() {
            @Override
            public void onChanged(NamiStufe stufe) {
                updateFilter();
            }
        });
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

    public LiveData<List<NamiMitglied>> getMemberList() {
        return this.memberList;
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
