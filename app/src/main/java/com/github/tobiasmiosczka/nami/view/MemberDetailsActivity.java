package com.github.tobiasmiosczka.nami.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.github.tobiasmiosczka.nami.R;
import com.github.tobiasmiosczka.nami.model.Nami;
import com.github.tobiasmiosczka.nami.view.adapter.MemberDetailsPageAdapter;
import com.google.android.material.tabs.TabLayout;

import nami.connector.namitypes.NamiMitglied;

public class MemberDetailsActivity extends AppCompatActivity {

    private static final Nami NAMI = Nami.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        NamiMitglied member = NAMI.getMemberById(id);

        if (member == null)
            finish();

        if (member != null) {
            setTheme(Nami.getTheme(member.getStufe()));
            setContentView(R.layout.activity_member_details);

            TextView title = findViewById(R.id.title);
            title.setText(member.getVorname() + " " + member.getNachname());

            final MemberDetailsPageAdapter pageAdapter = new MemberDetailsPageAdapter(getSupportFragmentManager(), id);
            final ViewPager viewPager = findViewById(R.id.view_pager);
            final TabLayout tabLayout = findViewById(R.id.tablayout);
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
            viewPager.setAdapter(pageAdapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        }
    }
}