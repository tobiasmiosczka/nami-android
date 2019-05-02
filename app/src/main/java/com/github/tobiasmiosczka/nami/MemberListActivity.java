package com.github.tobiasmiosczka.nami;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import java.util.List;

import nami.connector.namitypes.NamiMitglied;

public class MemberListActivity extends AppCompatActivity {

    private static final Nami NAMI = Nami.getInstance();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_phone_list);
        final MemberListRecyclerViewAdapter recyclerViewAdapter = new MemberListRecyclerViewAdapter(this, Nami.getInstance().getMemberList().getValue());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        NAMI.getMemberList().observe(this, new Observer<List<NamiMitglied>>() {
            @Override
            public void onChanged(@Nullable List<NamiMitglied> namiMitglieds) {
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });


        final ProgressBar progressBar = findViewById(R.id.progress_download);

        NAMI.getProgressCurrent().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                progressBar.setProgress(integer);
            }
        });

        NAMI.getProgressMax().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                progressBar.setMax(integer);
            }
        });

        if (NAMI.getIsLoggedIn().getValue() == false) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
