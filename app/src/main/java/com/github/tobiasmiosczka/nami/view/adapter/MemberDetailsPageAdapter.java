package com.github.tobiasmiosczka.nami.view.adapter;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.github.tobiasmiosczka.nami.view.MemberDetailsGeneralFragment;
import com.github.tobiasmiosczka.nami.view.MemberDetailsPhoneFragment;

public class MemberDetailsPageAdapter extends FragmentPagerAdapter {

    private final MemberDetailsGeneralFragment memberDetailsGeneralFragment;
    private final MemberDetailsPhoneFragment memberDetailsPhoneFragment;

    public MemberDetailsPageAdapter(FragmentManager fm, int id) {
        super(fm);

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
