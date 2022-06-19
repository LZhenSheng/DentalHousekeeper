package com.example.dentalhousekeeper.adapter;

import android.app.Activity;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.UserManager;
import com.example.dentalhousekeeper.listener.UserListener;
import com.example.dentalhousekeeper.util.ImageUtil;
import com.example.dentalhousekeeper.util.LogUtil;
import com.example.dentalhousekeeper.util.MessageUtil;
import com.example.dentalhousekeeper.util.StringUtil;
import com.example.dentalhousekeeper.util.TimeUtil;

import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * 会话适配器
 */
public class ConversationAdapter extends BaseQuickAdapter<Conversation, BaseViewHolder> {

    /**
     * 构造方法
     *
     * @param layoutResId
     */
    public ConversationAdapter(int layoutResId) {
        super(layoutResId);
    }

    /**
     * 绑定数据
     *
     * @param helper
     * @param data
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, Conversation data) {

        //获取头像控件
        ImageView iv_avatar = helper.getView(R.id.iv_avatar);

        UserInfo user = (UserInfo) data.getTargetInfo();
        LogUtil.d("dlfksjkf", user.toString());
//获取用户信息
        UserManager.getInstance(mContext)
                .getUser(user.getUserName(), (UserListener) userData -> {
                    //显示头像
                    ImageUtil.showAvatar((Activity) mContext, iv_avatar, userData.getAvatar());
                    LogUtil.d("dlfksjkf", userData.toString());

                    //昵称
                    helper.setText(R.id.tv_nickname, userData.getName());
                });

//获取最后一条消息
        Message latestMessage = data.getLatestMessage();

        if (latestMessage == null) {
            //清空日志
            helper.setText(R.id.tv_time, "");

            //清空消息内容
            helper.setText(R.id.tv_info, "");
        } else {
            //获取消息时间
            long time = latestMessage.getCreateTime();

            //格式化日期
            helper.setText(R.id.tv_time, TimeUtil.commonFormat(time));

            //显示消息
            helper.setText(R.id.tv_info, MessageUtil.getContent(latestMessage.getContent()));
        }

        //未读消息数
        int count = data.getUnReadMsgCnt();
        if (count > 0) {
            //显示未读消息数控件
            helper.setVisible(R.id.tv_count, true);

            helper.setText(R.id.tv_count, StringUtil.formatMessageCount(count));
        } else {
            //隐藏未读消息数控件
            helper.setVisible(R.id.tv_count, false);
        }

    }
}
