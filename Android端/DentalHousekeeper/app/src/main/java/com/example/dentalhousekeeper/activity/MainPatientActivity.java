package com.example.dentalhousekeeper.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.adapter.MainAdapter;
import com.example.dentalhousekeeper.adapter.MainPatientAdapter;
import com.example.dentalhousekeeper.event.OnNewMessageEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.nekocode.badge.BadgeDrawable;

public class MainPatientActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.vp)
    ViewPager vp;

    @BindView(R.id.indicator_iv1)
    ImageView indicator_iv1;
    @BindView(R.id.indicator_iv3)
    ImageView indicator_iv3;
    @BindView(R.id.indicator_iv4)
    ImageView indicator_iv4;

    @OnClick({R.id.indicator_iv1,R.id.indicator_iv3,R.id.indicator_iv4})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.indicator_iv1:
                clearBackground();
                indicator_iv1.setImageResource(R.mipmap.home_selected);
                vp.setCurrentItem(0);
                break;
            case R.id.indicator_iv3:
                clearBackground();
                indicator_iv3.setImageResource(R.mipmap.notice_selected);
                vp.setCurrentItem(1);
                break;
            case R.id.indicator_iv4:
                clearBackground();
                indicator_iv4.setImageResource(R.mipmap.my_selected);
                vp.setCurrentItem(2);
                break;
        }
    }

    private void clearBackground() {
        indicator_iv1.setImageResource(R.mipmap.home);
        indicator_iv3.setImageResource(R.mipmap.notice);
        indicator_iv4.setImageResource(R.mipmap.my);
    }

    /**
     * ???????????????
     */
    @BindView(R.id.iv_count)
    ImageView iv_count;

//    /**
//     * ?????????
//     */
//    @BindView(R.id.mi)
//    MagicIndicator mi;

    MainPatientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_patient);
    }


    @Override
    public void initData() {
        super.initData();

        //?????????????????? ?????????????????????
        vp.setOffscreenPageLimit(3);

        //???????????????MainAda
        adapter = new MainPatientAdapter(getMainActivity(), getSupportFragmentManager());
        vp.setAdapter(adapter);

        ArrayList<Integer> datas = new ArrayList<>();
        datas.add(0);
        datas.add(1);
        datas.add(2);
        adapter.setDatum(datas);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                clearBackground();
                switch (position){
                    case 0:
                        indicator_iv1.setImageResource(R.mipmap.home_selected);
                        break;
                    case 1:
                        indicator_iv3.setImageResource(R.mipmap.notice_selected);
                        break;
                    case 2:
                        indicator_iv4.setImageResource(R.mipmap.my_selected);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }


        });
    }

    /**
     * ??????????????????
     */
    @Override
    protected void onResume() {
        super.onResume();

        //?????????????????????
        showMessageCount();
    }

    /**
     * ?????????????????????
     */
    private void showMessageCount() {
        //?????????????????????????????????
        int count = JMessageClient.getAllUnReadMsgCount();

        if (count > 0) {
            //???????????????

            //???????????????????????????drawable
            BadgeDrawable countDrawable = new BadgeDrawable.Builder()
                    .type(BadgeDrawable.TYPE_NUMBER)
                    .number(count)

                    //??????????????????
                    //???????????????????????????????????????
                    .badgeColor(ContextCompat.getColor(getMainActivity(), R.color.main_color))
                    .build();

            iv_count.setImageDrawable(countDrawable);
        } else {
            //??????????????????
            iv_count.setImageDrawable(null);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewMessageEvent(OnNewMessageEvent event){
        showMessageCount();
    }
}
