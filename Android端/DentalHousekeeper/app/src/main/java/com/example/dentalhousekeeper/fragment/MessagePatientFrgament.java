package com.example.dentalhousekeeper.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.activity.ChatActivity;
import com.example.dentalhousekeeper.activity.PreOrderPatientActivity;
import com.example.dentalhousekeeper.adapter.ConversationAdapter;
import com.example.dentalhousekeeper.event.OnNewMessageEvent;
import com.example.dentalhousekeeper.util.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;

/***
* 用户聊天界面Fragment
* @author 胜利镇
* @time 2020/10/13
* @dec 
*/
public class MessagePatientFrgament extends BaseTitleFragment {

    /**
     * 列表控件
     */
    @BindView(R.id.rv)
    RecyclerView rv;
    /**
     * 适配器
     */
    private ConversationAdapter adapter;
    /**
     * 构造方法
     *
     * 固定写法
     *
     * @return
     */
    public static MessagePatientFrgament newInstance() {
        Bundle args = new Bundle();
        MessagePatientFrgament fragment = new MessagePatientFrgament();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View getLayoutView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message_patient,container,false);
    }

    private void fetchData(){
        List<Conversation> datum = JMessageClient.getConversationList();

        if (datum == null) {
            //清空数据
            adapter.replaceData(new ArrayList<>());
        } else {
            //设置数据
            adapter.replaceData(datum);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        rv.setHasFixedSize(true);

        //布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getMainActivity());
        rv.setLayoutManager(layoutManager);

        //创建适配器
        adapter = new ConversationAdapter(R.layout.item_conversation);

        //设置适配器
        rv.setAdapter(adapter);
        //设置item点击事件
        adapter.setOnItemClickListener((adapter, view, position) -> {
            //获取点击的数据
            Conversation data = (Conversation) adapter.getItem(position);

            //获取目标用户
            UserInfo user = (UserInfo) data.getTargetInfo();
            LogUtil.d("djklf",user.getNickname());

            //跳转到聊天界面
            ChatActivity.start(getMainActivity(), user.getUserName());
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewMessageEvent(OnNewMessageEvent event){
        fetchData();
    }
}
