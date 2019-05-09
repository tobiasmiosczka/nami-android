package com.github.tobiasmiosczka.nami.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.tobiasmiosczka.nami.R;
import com.github.tobiasmiosczka.nami.model.Nami;

import java.text.DateFormat;
import java.util.Date;

import de.cketti.mailto.EmailIntentBuilder;
import nami.connector.namitypes.NamiMitglied;
import nami.connector.namitypes.enums.NamiGeschlecht;

public class MemberDetailsGeneralFragment extends Fragment {

    private static final Nami NAMI = Nami.getInstance();
    private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_member_details_general, container, false);

        int id = getArguments().getInt("id");
        final NamiMitglied member = NAMI.getMemberById(id);
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

        TextView tvEmail = view.findViewById(R.id.tv_email);
        tvEmail.setText(member.getEmail());
        tvEmail.setOnClickListener(v -> EmailIntentBuilder.from(getActivity())
                .to(member.getEmail())
                .start());

        //TODO: implement
        TextView tvEmailParents = view.findViewById(R.id.tv_email_parents);
        tvEmailParents.setText(member.getEmailVertretungsberechtigter());
        tvEmailParents.setOnClickListener(v -> EmailIntentBuilder.from(getActivity())
                .to(member.getEmailVertretungsberechtigter())
                .start());

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
