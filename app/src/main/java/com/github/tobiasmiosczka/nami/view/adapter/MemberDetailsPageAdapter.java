package com.github.tobiasmiosczka.nami.view.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.github.tobiasmiosczka.nami.view.MemberDetailsGeneralFragment;
import com.github.tobiasmiosczka.nami.view.MemberDetailsPhoneFragment;

public class MemberDetailsPageAdapter extends FragmentPagerAdapter {

    private int id;

    MemberDetailsGeneralFragment memberDetailsGeneralFragment;
    MemberDetailsPhoneFragment memberDetailsPhoneFragment;

    public MemberDetailsPageAdapter(FragmentManager fm, int id) {
        super(fm);
        this.id = id;

        Bundle bundle = new Bundle();
        bundle.putInt("id", id);

        memberDetailsGeneralFragment = new MemberDetailsGeneralFragment();
        memberDetailsGeneralFragment.setArguments(bundle);
        memberDetailsPhoneFragment = new MemberDetailsPhoneFragment();
        memberDetailsPhoneFragment.setArguments(bundle);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0: return memberDetailsGeneralFragment;
            case 1: return memberDetailsPhoneFragment;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
