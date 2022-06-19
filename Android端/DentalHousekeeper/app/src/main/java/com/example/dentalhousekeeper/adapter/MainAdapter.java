package com.example.dentalhousekeeper.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.dentalhousekeeper.fragment.FirstPagesFragment;
import com.example.dentalhousekeeper.fragment.MeFragment;
import com.example.dentalhousekeeper.fragment.MessagePatientFrgament;

/**
 * 主界面ViewPager的Adapter
 *
 */
public class MainAdapter extends BaseFragmentPagerAdapter<Integer> {

    public MainAdapter(Context context, FragmentManager fm) {
        super(context, fm);
    }

    /**
     * 返回Fragment
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return FirstPagesFragment.newInstance();
        } else if (position==1){
            return MessagePatientFrgament.newInstance();
        } else {
            return MeFragment.newInstance();
        }
    }


}