package com.example.a1.shopping;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 12/14/2017.
 */

public class SectionPageAdapter extends FragmentPagerAdapter {
final List<Fragment> fragmentList=new ArrayList<>();
    final List<String>fragmentTitleList=new ArrayList<>();

    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
    }
    public void Add_Fragment(Fragment fragment,String Title){
        fragmentList.add(fragment);
        fragmentTitleList.add(Title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
