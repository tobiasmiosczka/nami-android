package com.github.tobiasmiosczka.nami;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import nami.connector.namitypes.NamiMitglied;

public class MemberListViewModel extends ViewModel {

    private MutableLiveData<List<NamiMitglied>> memberList;

    public MemberListViewModel() {

    }

    public LiveData<List<NamiMitglied>> getMemberList() {
        return memberList;
    }
}
