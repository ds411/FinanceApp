package com.financeapp.financeapp.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.financeapp.financeapp.Fragments.DateFeedFragment;
import com.financeapp.financeapp.Fragments.FeedFragment;
import com.financeapp.financeapp.Fragments.StatFragment;


public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;

    public TabPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext = context;
    }

    @Override
    public Fragment getItem(int i) {
        switch(i) {
            case 0:
                return new FeedFragment();
            case 1:
                return new DateFeedFragment();
            case 2:
                return new StatFragment();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //todo:use mContext and strings.xml to get titles
        switch(position) {
            case 0:
                return "Feed";
            case 1:
                return "Transactions By Date";
            case 2:
                return "Stats";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
