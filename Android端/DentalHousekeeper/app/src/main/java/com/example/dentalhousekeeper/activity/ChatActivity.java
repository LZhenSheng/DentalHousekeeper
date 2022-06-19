package com.example.dentalhousekeeper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.textclassifier.ConversationAction;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.UserManager;
import com.example.dentalhousekeeper.adapter.ChatAdapter;
import com.example.dentalhousekeeper.event.OnNewMessageEvent;
import com.example.dentalhousekeeper.listener.ImageListener;
import com.example.dentalhousekeeper.util.Constant;
import com.example.dentalhousekeeper.util.LogUtil;
import com.example.dentalhousekeeper.util.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.wanglu.photoviewerlibrary.PhotoViewer;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

public class ChatActivity extends BaseCenterTitleActivity implements ImageListener, ViewTreeObserver.OnGlobalLayoutListener {

    /**
     * 适配器
     */
    private ChatAdapter adapter;

    /**
     * 列表控件
     */
    @BindView(R.id.rv)
    RecyclerView rv;

    /**
     * 输入框
     */
    @BindView(R.id.et_content)
    EditText et_content;
    /**
     * 用户管理器
     */
    private UserManager userManager;

    String id=null;
    private Conversation conversation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }
    @Override
    protected void initViews() {
        super.initViews();
        //尺寸固定
        rv.setHasFixedSize(true);

        //布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getMainActivity());
        rv.setLayoutManager(layoutManager);
    }

    @Override
    public void initData() {
        super.initData();
        id=extraId();
        LogUtil.d("djklfsf",id);

        //初始化用户管理器
        userManager = UserManager.getInstance(getMainActivity());

//显示标题
        userManager.getUser(id, data -> setTitle(data.getName()));

        conversation= Conversation.createSingleConversation(id);

        conversation.setUnReadMessageCnt(0);

        //创建适配器
        adapter = new ChatAdapter(getMainActivity(),this);


        //设置适配器
        rv.setAdapter(adapter);
    }

    /**
     * 启动界面
     *
     * @param activity 当前Activity
     * @param id       目标聊天用户Id
     */
    public static void start(Activity activity, String id) {
        //创建意图
        Intent intent = new Intent(activity, ChatActivity.class);

        //传递id
        intent.putExtra(Constant.ID, id);

        //启动界面
        activity.startActivity(intent);
    }

    private void fetchData() {
        //获取会话中所有消息
        //消息按照时间升序排列
        List<Message> messages = conversation.getAllMessage();

        if (messages == null) {
            adapter.setDatum(new ArrayList<>());
        } else {
            adapter.setDatum(messages);
        }
    }

    @Override
    public void onGlobalLayout() {
        scrollBottom();
    }

    @Override
    protected void onPause() {
        super.onPause();
        rv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        EventBus.getDefault().unregister(this);
        JMessageClient.exitConversation();
    }

    /**
     * 界面显示了
     */
    @Override
    protected void onResume() {
        super.onResume();

        fetchData();

        //添加布局监听器
        rv.getViewTreeObserver().addOnGlobalLayoutListener(this);

        //注册发布订阅框架
        EventBus.getDefault().register(this);
        JMessageClient.enterSingleConversation(id);
    }

    @Override
    public void onImageClick(ImageView rv, String imageUris, int index) {
        PhotoViewer.INSTANCE
                .setClickSingleImg(imageUris,rv)
                .setShowImageViewInterface((imageView, url) -> {
                    //使用Glide显示图片
                    Glide.with(ChatActivity.this)
                            .load(imageUris)
                            .into(imageView);
                }).start(this);
    }

    /**
     * 发送按钮
     */
    @OnClick(R.id.iv_send)
    public void onSendClick() {
        LogUtil.d(TAG, "onSendClick");

        String content = et_content.getText().toString().trim();
        if (StringUtils.isEmpty(content)) {
            ToastUtil.errorShortToast(R.string.hint_enter_message);
            return;
        }

        //创建文本消息
        Message message = conversation.createSendTextMessage(content);

        //设置消息监听器
        setSendMessageCallback(message);

        //发送文本消息
        JMessageClient.sendMessage(message);
    }

    /**
     * 设置消息监听器
     *
     * @param message
     */
    private void setSendMessageCallback(Message message) {
        message.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (responseCode == 0) {
                    //发送成功

                    LogUtil.d(TAG, "send message success");

                    //添加消息到列表
                    addMessage(message);

                    //清空输入框
                    clearInput();
                } else {
                    //发送失败
                    LogUtil.d(TAG, "send message failed:" + responseCode + "," + responseMessage);

                    //弹出提示
                    ToastUtil.successShortToast(R.string.error_send_message);
                }
            }
        });
    }


    /**
     * 清空输入框
     */
    private void clearInput() {
        et_content.setText("");
    }

    /**
     * 添加消息到列表
     *
     * @param data
     */
    private void addMessage(Message data) {
        //将消息添加到列表后面
        adapter.addData(data);

        //滚动到底部
        scrollBottom();
    }

    /**
     * 滚动到底部
     */
    private void scrollBottom() {
        rv.post(new Runnable() {
            @Override
            public void run() {
                //使用动画滚动到底部
                rv.smoothScrollToPosition(adapter.getItemCount());
            }
        });
    }


    /**
     * 有新消息了
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewMessageEvent(OnNewMessageEvent event) {
        //获取消息
        Message data = event.getData();

        //获取消息发送人
        UserInfo user = data.getFromUser();

        if (!user.getUserName().equals(id)) {
            //不是这个人的消息
            //就不能显示
            return;
        }

        //设置为已读
        data.setHaveRead(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                //添加到列表
                addMessage(data);
            }
        });
    }


    /**
     * 选择图片按钮
     */
    @OnClick(R.id.iv_select_image)
    public void onSelectImageClick() {
        LogUtil.d(TAG, "onSelectImageClick");

        //进入相册
        //以下是例子
        //用不到的api可以不写
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                //.theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(3)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                //.previewVideo()// 是否可预览视频 true or false
                //.enablePreviewAudio() // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.JPEG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                //.enableCrop()// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                //.glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                //.withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                //.hideBottomControls()// 是否显示uCrop工具栏，默认不显示 true or false
                //.isGif()// 是否显示gif图片 true or false
                //.compressSavePath(getPath())//压缩图片保存地址
                //.freeStyleCropEnabled()// 裁剪框是否可拖拽 true or false
                //.circleDimmedLayer()// 是否圆形裁剪 true or false
                //.showCropFrame()// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                //.showCropGrid()// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                //.openClickSound()// 是否开启点击声音 true or false
                //.selectionMedia(selectedImage)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                //.cropCompressQuality()// 裁剪压缩质量 默认90 int
                //.minimumCompressSize(100)// 小于100kb的图片不压缩
                //.synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                //.rotateEnabled() // 裁剪是否可旋转图片 true or false
                //.scaleEnabled()// 裁剪是否可放大缩小图片 true or false
                //.videoQuality()// 视频陆制质量 0 or 1 int
                //.videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
                //.videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
                //.recordVideoSecond()//视频秒数陆制 默认60s int
                //.isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            //获取选中的资源
            List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);

            //发送图片消息
            sendImageMessage(localMedia.get(0).getCompressPath());
        }
    }

    /**
     * 发送图片消息
     *
     * @param path
     */
    private void sendImageMessage(String path) {
        try {
            //创建消息
            Message message = conversation.createSendImageMessage(new File(path));

            //设置消息发送监听器
            //setSendMessageCallback(message);

            //发送消息
            JMessageClient.sendMessage(message);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            ToastUtil.errorShortToast(R.string.error_send_message);
        }
    }
}
