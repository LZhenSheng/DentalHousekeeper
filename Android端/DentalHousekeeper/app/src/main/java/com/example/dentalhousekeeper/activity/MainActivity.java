package com.example.dentalhousekeeper.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.adapter.MainAdapter;
import com.example.dentalhousekeeper.event.OnNewMessageEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.nekocode.badge.BadgeDrawable;

/***
 * 主页面
 * @author 胜利镇
 * @time 2020/8/7 8:13
 */
public class MainActivity extends BaseActivity {

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
     * 消息未读数
     */
    @BindView(R.id.iv_count)
    ImageView iv_count;

//    /**
//     * 指示器
//     */
//    @BindView(R.id.mi)
//    MagicIndicator mi;

    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public void initData() {
        super.initData();

        //缓存页面数量 默认是缓存一个
        vp.setOffscreenPageLimit(3);

        //主界面页面MainAda
        adapter = new MainAdapter(getMainActivity(), getSupportFragmentManager());
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
     * 当界面显示了
     */
    @Override
    protected void onResume() {
        super.onResume();

        //显示消息未读数
        showMessageCount();
    }

    /**
     * 显示消息未读数
     */
    private void showMessageCount() {
        //获取所有会话未读消息数
        int count = JMessageClient.getAllUnReadMsgCount();

        if (count > 0) {
            //有未读消息

            //我的消息未读消息数drawable
            BadgeDrawable countDrawable = new BadgeDrawable.Builder()
                    .type(BadgeDrawable.TYPE_NUMBER)
                    .number(count)

                    //设置背景颜色
                    //这里使用了兼容方法获取颜色
                    .badgeColor(ContextCompat.getColor(getMainActivity(), R.color.main_color))
                    .build();

            iv_count.setImageDrawable(countDrawable);
        } else {
            //没有未读消息
            iv_count.setImageDrawable(null);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewMessageEvent(OnNewMessageEvent event){
        showMessageCount();
    }
}