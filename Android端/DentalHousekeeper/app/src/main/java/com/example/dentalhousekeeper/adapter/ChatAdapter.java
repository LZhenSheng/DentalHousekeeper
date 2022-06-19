package com.example.dentalhousekeeper.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.UserManager;
import com.example.dentalhousekeeper.listener.ImageListener;
import com.example.dentalhousekeeper.util.ImageUtil;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

import butterknife.BindView;
import cn.jpush.im.android.api.callback.DownloadCompletionCallback;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.MessageDirect;
import cn.jpush.im.android.api.model.Message;

import static com.example.dentalhousekeeper.util.Constant.TYPE_IMAGE_LEFT;
import static com.example.dentalhousekeeper.util.Constant.TYPE_IMAGE_RIGHT;
import static com.example.dentalhousekeeper.util.Constant.TYPE_TEXT_LEFT;
import static com.example.dentalhousekeeper.util.Constant.TYPE_TEXT_RIGHT;


/**
 * 聊天界面适配器
 */
public class ChatAdapter extends BaseRecyclerViewAdapter<Message, ChatAdapter.ViewHolder> {

    private final ImageListener imageListener;
    /**
     * 用户管理器
     */
    private final UserManager userManager;

    /**
     * 构造方法
     *
     * @param context
     */
    public ChatAdapter(Context context, ImageListener listener) {
        super(context);

        //初始化用户管理器
        userManager = UserManager.getInstance(context);
        this.imageListener=listener;
    }

    /**
     * 创建viewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //这里要区分是我发送的
        //还是其他人发送的

        //我发送的显示在右边
        //其他人发送的显示在左边

        //他们的布局都一样只是方向不一样
        //所以可以用同一个ViewHolder
        switch (viewType) {
            case TYPE_IMAGE_LEFT:
                //其他人发送的图片消息
                return new ImageViewHolder(getInflater().inflate(R.layout.item_chat_image_left, parent, false));
            case TYPE_IMAGE_RIGHT:
                //我发送的图片消息
                return new ImageViewHolder(getInflater().inflate(R.layout.item_chat_image_right, parent, false));
            case TYPE_TEXT_LEFT:
                //其他人发送的文本消息
                return new TextViewHolder(getInflater().inflate(R.layout.item_chat_text_left, parent, false));
            default:
                //我发送的文本消息
                return new TextViewHolder(getInflater().inflate(R.layout.item_chat_text_right, parent, false));
        }
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.bindData(getData(position));
    }

    /**
     * 聊天消息公共ViewHolder
     * 例如头像
     */
    abstract class ViewHolder extends BaseRecyclerViewAdapter.ViewHolder<Message> {

        /**
         * 头像
         */
        @BindView(R.id.iv_avatar)
        ImageView iv_avatar;

        /**
         * 构造方法
         *
         * @param itemView
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        /**
         * 绑定数据
         *
         * @param data
         */
        @Override
        public void bindData(Message data) {
            super.bindData(data);
            //显示当前消息发送人
            String userId = data.getFromUser().getUserName();
            userManager.getUser(userId, userData -> {
                //显示头像
                ImageUtil.showAvatar((Activity) context, iv_avatar, userData.getAvatar());
            });
        }
    }

    /**
     * 文本消息
     */
    class TextViewHolder extends ChatAdapter.ViewHolder {

        /**
         * 文本控件
         */
        @BindView(R.id.tv_content)
        TextView tv_content;

        /**
         * 构造方法
         *
         * @param itemView
         */
        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        /**
         * 绑定数据
         *
         * @param data
         */
        @Override
        public void bindData(Message data) {
            super.bindData(data);

            TextContent content = (TextContent) data.getContent();
            tv_content.setText(content.getText());

            //真实项目中
            //可能还会实现像评论那边的mention和hashTag
            //因为在评论那边讲解了
            //所以这里就不在重复讲解了
        }
    }

    /**
     * 返回view类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        Message data = getData(position);

        //获取消息方向
        MessageDirect messageDirect = data.getDirect();

        //获取消息内容
        MessageContent messageContent = data.getContent();

        //判断消息类型
        if (messageContent instanceof ImageContent) {
            //图片消息
            return messageDirect == MessageDirect.send ? TYPE_IMAGE_RIGHT : TYPE_IMAGE_LEFT;
        }

        //TODO 其他消息可以继续在这里扩展

        //文本消息
        //我发送的消息在右边
        return messageDirect == MessageDirect.send ? TYPE_TEXT_RIGHT : TYPE_TEXT_LEFT;
    }

    /**
     * 图片消息
     */
    class ImageViewHolder extends ChatAdapter.ViewHolder {
        /**
         * 图片控件
         */
        @BindView(R.id.iv_banner)
        ImageView iv_banner;

        /**
         * 构造方法
         *
         * @param itemView
         */
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        /**
         * 绑定数据方法
         *
         * @param data
         */
        @Override
        public void bindData(Message data) {
            super.bindData(data);

            //设置为默认图片
            //放置显示原来的图片
            iv_banner.setImageResource(R.drawable.placeholder);
            //获取图片内容
            ImageContent content = (ImageContent) data.getContent();

            if (StringUtils.isNotBlank(content.getLocalPath())) {
                //有本地路径

                //直接显示本地路径
                showImage(content);
            } else {
                //下载原图
                content.downloadOriginImage(data, new DownloadCompletionCallback() {
                    @Override
                    public void onComplete(int i, String s, File file) {
                        //这里没有处理错误
                        //下载完成后
                        //直接显示c
                        showImage(content);
                    }
                });
            }
        }

        /**
         * 显示图片
         *
         * @param data
         */
        private void showImage(ImageContent data) {
            ImageUtil.showLocalImage(context, iv_banner, data.getLocalPath());
            iv_banner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageListener.onImageClick(iv_avatar,data.getLocalPath(),0);
                }
            });
        }
    }
}