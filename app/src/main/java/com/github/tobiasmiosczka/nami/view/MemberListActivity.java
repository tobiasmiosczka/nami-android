package com.github.tobiasmiosczka.nami.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.tobiasmiosczka.nami.R;
import com.github.tobiasmiosczka.nami.model.Nami;
import com.github.tobiasmiosczka.nami.view.adapter.MemberListRecyclerViewAdapter;

import nami.connector.namitypes.enums.NamiStufe;

public class MemberListActivity extends AppCompatActivity {

    private static final Nami NAMI = Nami.getInstance();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_member_list);
        final MemberListRecyclerViewAdapter recyclerViewAdapter = new MemberListRecyclerViewAdapter(this, Nami.getInstance().getFilteredMemberList().getValue());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        NAMI.getFilteredMemberList().observe(this, namiMitglieds -> recyclerViewAdapter.notifyDataSetChanged());

        final ProgressBar progressBar = findViewById(R.id.progress_download);

        NAMI.getProgressCurrent().observe(this, progressBar::setProgress);

        NAMI.getProgressMax().observe(this, progressBar::setMax);

        if (!NAMI.getIsLoggedIn().getValue()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_member_list, menu);

        final MenuItem mSearch = menu.findItem(R.id.menu_search);
        final MenuItem a = menu.findItem(R.id.search_all);
        final MenuItem w = menu.findItem(R.id.search_woelflinge);
        final MenuItem j = menu.findItem(R.id.search_jungpfadfinder);
        final MenuItem p = menu.findItem(R.id.search_pfadfinder);
        final MenuItem r = menu.findItem(R.id.search_rover);

        final SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search");

        NAMI.getNameFilter().observe(this, s -> mSearchView.setQuery(s, true));

        NAMI.getStufeFilter().observe(this, stufe -> {
            if (stufe == null) {
                a.setChecked(true);
                return;
            }
            switch (stufe) {
                case WOELFLING:
                    w.setChecked(true);
                    return;
                case JUNGPFADFINDER:
                    j.setChecked(true);
                    return;
                case PFADFINDER:
                    p.setChecked(true);
                    return;
                case ROVER:
                    r.setChecked(true);
                    return;
                default:
                    a.setChecked(true);
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                NAMI.setNameFilter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void onStufeselected(MenuItem item){
        switch(item.getItemId()){
            // Handle the non group menu items here
            case R.id.search_all:
                NAMI.setStufeFilter(null);
                return;
            case R.id.search_woelflinge:
                NAMI.setStufeFilter(NamiStufe.WOELFLING);
                item.setChecked(true);
                return;
            case R.id.search_jungpfadfinder:
                NAMI.setStufeFilter(NamiStufe.JUNGPFADFINDER);
                item.setChecked(true);
                return;
            case R.id.search_pfadfinder:
                NAMI.setStufeFilter(NamiStufe.PFADFINDER);
                item.setChecked(true);
                return;
            case R.id.search_rover:
                NAMI.setStufeFilter(NamiStufe.ROVER);
                item.setChecked(true);
                return;
            default:
        }
    }
}
