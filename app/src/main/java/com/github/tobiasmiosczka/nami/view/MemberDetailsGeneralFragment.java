package com.github.tobiasmiosczka.nami.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.tobiasmiosczka.nami.R;
import com.github.tobiasmiosczka.nami.model.Nami;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import nami.connector.namitypes.NamiMitglied;
import nami.connector.namitypes.enums.NamiGeschlecht;

public class MemberDetailsGeneralFragment extends Fragment {

    private static final Nami NAMI = Nami.getInstance();
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_member_details_general, container, false);

        int id = getArguments().getInt("id");
        NamiMitglied member = NAMI.getMemberById(id);
        //TODO: what if member is null?

        TextView tvFirstname = view.findViewById(R.id.tv_firstname);
        tvFirstname.setText(member.getVorname());

        TextView tvLastname = view.findViewById(R.id.tv_lastname);
        tvLastname.setText(member.getNachname());

        TextView tvMemberId = view.findViewById(R.id.tv_member_id);
        tvMemberId.setText("" + member.getMitgliedsnummer());

        TextView tvGender = view.findViewById(R.id.tv_gender);
        tvGender.setText(getGenderAsString(member.getGeschlecht()));

        TextView tvBirthdate = view.findViewById(R.id.tv_birthdate);
        tvBirthdate.setText(dateToString(member.getGeburtsDatum()));

        return view;
    }

    private static String dateToString(Date date) {
        if (date == null)
            return "";
        return DATE_FORMAT.format(date);
    }

    private static String getGenderAsString(NamiGeschlecht geschlecht) {
        switch (geschlecht) {
            case WEIBLICH: return "weiblich";
            case MAENNLICH: return "männlich";
            default: return "";
        }
    }
}
