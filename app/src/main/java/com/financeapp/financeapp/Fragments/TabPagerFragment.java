package com.financeapp.financeapp.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.financeapp.financeapp.Adapters.TabPagerAdapter;
import com.financeapp.financeapp.R;

public class TabPagerFragment extends Fragment {

    private static TabPagerAdapter tabPagerAdapter;

    private Activity activity;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_pager, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        viewPager = activity.findViewById(R.id.pager);
        tabLayout = activity.findViewById(R.id.pager_tabs);
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(activity, getChildFragmentManager());
        viewPager.setAdapter(tabPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
