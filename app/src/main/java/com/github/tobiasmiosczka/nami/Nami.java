package com.github.tobiasmiosczka.nami;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

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

public class Nami implements NamiDataLoaderHandler {

    private NamiConnector namiConnector;
    private MutableLiveData<List<NamiMitglied>> memberList = new MutableLiveData<>();
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
        isLoggedIn.setValue(false);
    }

    public void login(String username, String password) throws IOException, NamiLoginException {
        namiConnector = new NamiConnector(NamiServer.LIVESERVER_WITH_API, new NamiCredentials(username, password));
        namiConnector.namiLogin();
        isLoggedIn.postValue(true);
        Nami.getInstance().loadData();
    }

    public void loadData() {
        NamiSearchedValues namiSearchedValues = new NamiSearchedValues();
        NaMiDataLoader dataLoader = new NaMiDataLoader(this.namiConnector, namiSearchedValues, this);
        dataLoader.start();
    }

    public LiveData<List<NamiMitglied>> getMemberList() {
        return this.memberList;
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
        isLoggedIn.postValue(false);
        e.printStackTrace();
    }
}
