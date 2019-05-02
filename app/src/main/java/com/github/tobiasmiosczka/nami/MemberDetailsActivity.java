package com.github.tobiasmiosczka.nami;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import nami.connector.namitypes.NamiMitglied;

public class MemberDetailsActivity extends AppCompatActivity {

    private static final Nami NAMI = Nami.getInstance();

    private NamiMitglied member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        member = NAMI.getMemberById(id);
        if(member == null)
            finish();

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
                                           }
            );
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}