package com.github.tobiasmiosczka.nami;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nami.connector.namitypes.NamiMitglied;

public class MemberDetailsGeneralFragment extends Fragment {

    private static final Nami NAMI = Nami.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_member_details_general, container, false);

        int id = getArguments().getInt("id");
        NamiMitglied member = NAMI.getMemberById(id);
        //TODO: what if member is null?

        TextView tvName = view.findViewById(R.id.tv_firstname);
        tvName.setText(member.getVorname());

        return view;
    }
}
