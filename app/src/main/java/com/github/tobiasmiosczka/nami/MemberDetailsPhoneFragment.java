package com.github.tobiasmiosczka.nami;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tobiasmiosczka.nami.program.PhoneContact;

import java.util.ArrayList;
import java.util.List;

import nami.connector.namitypes.NamiMitglied;

public class MemberDetailsPhoneFragment extends Fragment {

    private static final Nami NAMI = Nami.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_member_details_phone, container, false);

        int id = getArguments().getInt("id");
        NamiMitglied member = NAMI.getMemberById(id);
        //TODO: what if member is null?

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_phone);

        List<PhoneContact> phoneContacts = new ArrayList<>();
        phoneContacts.addAll(PhoneContact.getPhoneContacts(member.getTelefon1()));
        phoneContacts.addAll(PhoneContact.getPhoneContacts(member.getTelefon2()));
        phoneContacts.addAll(PhoneContact.getPhoneContacts(member.getTelefon3()));
        phoneContacts.addAll(PhoneContact.getPhoneContacts(member.getTelefax()));


        PhoneListRecyclerViewAdapter recyclerViewAdapter = new PhoneListRecyclerViewAdapter(getActivity(), phoneContacts);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }
}
