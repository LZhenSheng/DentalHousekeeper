package com.example.dentalhousekeeper.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.adapter.GroupConsultationAdapter;
import com.example.dentalhousekeeper.adapter.HttpObserver;
import com.example.dentalhousekeeper.adapter.PreOrderDoctorAdapter;
import com.example.dentalhousekeeper.api.Api;
import com.example.dentalhousekeeper.config.GenerateTestUserSig;
import com.example.dentalhousekeeper.domin.AppointMent;
import com.example.dentalhousekeeper.domin.Doctor;
import com.example.dentalhousekeeper.domin.GroupConsultation;
import com.example.dentalhousekeeper.util.PreferenceUtil;
import com.example.dentalhousekeeper.util.ToastUtil;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMSDKConfig;
import com.tencent.imsdk.v2.V2TIMSDKListener;
import com.tencent.imsdk.v2.V2TIMUserFullInfo;
import com.tencent.imsdk.v2.V2TIMValueCallback;
import com.tencent.liteav.basic.AvatarConstant;
import com.tencent.liteav.basic.UserModel;
import com.tencent.liteav.basic.UserModelManager;
import com.tencent.liteav.tuiroom.ui.CreateRoomActivity;
import com.tencent.liteav.tuiroom.ui.JoinRoomActivity;
import com.tencent.qcloud.tuicore.TUILogin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

public class GroupConsultationActivity extends BaseTitleActivity {

    @BindView(R.id.rv)
    RecyclerView rv;

    GroupConsultationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_consultation);
    }
    private              String    mAvatarUrl;       //????????????

    @Override
    public void initData() {
        super.initData();
        lightStatusBar(R.color.main_color);
        rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(GroupConsultationActivity.this);
        rv.setLayoutManager(layoutManager);
        adapter = new GroupConsultationAdapter(R.layout.item_group_consutation);
        rv.setAdapter(adapter);
        fetchData();
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                switch (view.getId()) {
                    case R.id.into:
                        PreferenceUtil.setAPPOINTMENTID(adapter.getData().get(i).getId());
                        init();
                        if(!adapter.getData().get(i).getStart().equals(adapter.getData().get(i).getReback())){
                            if(adapter.getData().get(i).getStart().equals(PreferenceUtil.getId())){
                                Intent intent=new Intent(GroupConsultationActivity.this, CreateRoomActivity.class);
                                intent.putExtra("data",PreferenceUtil.getAPPOINTMENTID());
                                startActivity(intent);
                            }else{
                                Intent intent=new Intent(GroupConsultationActivity.this, JoinRoomActivity.class);
                                intent.putExtra("data",PreferenceUtil.getAPPOINTMENTID());
                                startActivity(intent);
                            }
                        }else{
                            ToastUtil.successShortToast("?????????????????????????????????");
                        }

                        break;
                }
            }
        });
    }

    public void init() {
        final UserModel userModel = new UserModel();
        userModel.userId = PreferenceUtil.getId();
        userModel.userName = PreferenceUtil.getId();
        int index = new Random().nextInt(AvatarConstant.USER_AVATAR_ARRAY.length);
        String coverUrl = AvatarConstant.USER_AVATAR_ARRAY[index];
        userModel.userAvatar = coverUrl;
        userModel.userSig = GenerateTestUserSig.genTestUserSig(PreferenceUtil.getId());
        final UserModelManager manager = UserModelManager.getInstance();
        manager.setUserModel(userModel);
        V2TIMSDKConfig config = new V2TIMSDKConfig();
        config.setLogLevel(V2TIMSDKConfig.V2TIM_LOG_DEBUG);
        TUILogin.init(this, GenerateTestUserSig.SDKAPPID, null, new V2TIMSDKListener() {

            @Override
            public void onKickedOffline() {

            }

            @Override
            public void onUserSigExpired() {

            }
        });
        TUILogin.login(userModel.userId, userModel.userSig, new V2TIMCallback() {
            @Override
            public void onError(int code, String msg) {
                ToastUtils.showLong(R.string.app_toast_login_fail, code, msg);
                Log.d(TAG, "login fail code: " + code + " msg:" + msg);
            }

            @Override
            public void onSuccess() {
                Log.d(TAG, "login onSuccess");
                getUserInfo();
            }
        });
    }
    private void getUserInfo() {
        String[] avatarArr = AvatarConstant.USER_AVATAR_ARRAY;
        int index = new Random().nextInt(avatarArr.length);
        mAvatarUrl = avatarArr[index];
        final UserModelManager manager = UserModelManager.getInstance();
        final UserModel userModel = manager.getUserModel();
        //???????????????????????????
        List<String> userIdList = new ArrayList<>();
        userIdList.add(userModel.userId);
        Log.d(TAG, "setUserInfo: userIdList = " + userIdList);
        V2TIMManager.getInstance().getUsersInfo(userIdList, new V2TIMValueCallback<List<V2TIMUserFullInfo>>() {

            @Override
            public void onError(int code, String msg) {
                Log.e(TAG, "get group info list fail, code:" + code + " msg: " + msg);
            }

            @Override
            public void onSuccess(List<V2TIMUserFullInfo> resultList) {
                if (resultList == null || resultList.isEmpty()) {
                    return;
                }
                V2TIMUserFullInfo result = resultList.get(0);
                String userName = result.getNickName();
                String userAvatar = result.getFaceUrl();
                Log.d(TAG, "onSuccess: userName = " + userName + " , userAvatar = " + userAvatar);
                //??????????????????????????????,?????????????????????????????????
                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userAvatar)) {
                    V2TIMUserFullInfo v2TIMUserFullInfo = new V2TIMUserFullInfo();
                    v2TIMUserFullInfo.setFaceUrl(mAvatarUrl);
                    v2TIMUserFullInfo.setNickname(PreferenceUtil.getId());
                    V2TIMManager.getInstance().setSelfInfo(v2TIMUserFullInfo, new V2TIMCallback() {
                        @Override
                        public void onError(int code, String desc) {
                            Log.e(TAG, "set profile failed errorCode : " + code + " errorMsg : " + desc);
                            //???????????????????????????,???????????????????????????(??????????????????????????????),???????????????
                            ToastUtils.showLong(getString(R.string.app_toast_failed_to_set, desc));
                            Intent intent = new Intent();
                            intent.addCategory("android.intent.category.DEFAULT");
                            intent.setAction("com.tencent.liteav.action.portal");
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onSuccess() {
                            Log.i(TAG, "set profile success.");
                            ToastUtils.showLong(getString(R.string.app_toast_register_success_and_logging_in));
                            //???????????????????????????
                            UserModel userModel = UserModelManager.getInstance().getUserModel();
                            userModel.userName = PreferenceUtil.getId();
                            userModel.userAvatar = mAvatarUrl;
                            UserModelManager.getInstance().setUserModel(userModel);
                        }
                    });
                } else {
                    userModel.userAvatar = userAvatar;
                    userModel.userName = userName;
                    manager.setUserModel(userModel);
                }
            }
        });
    }

    private void fetchData() {
        GroupConsultation groupConsultation=new GroupConsultation();
        groupConsultation.setStart(PreferenceUtil.getId());
        Api.getInstance().findGroupConsultations(groupConsultation)
                .subscribe(new HttpObserver<List<GroupConsultation>>() {
                    @Override
                    public void onSucceeded(List<GroupConsultation> data) {
                        if(data!=null){
                            adapter.replaceData(data);
                        }
                    }
                });
    }



    /**
     * ????????????
     */
    private SearchView searchView;

    /**
     * ????????????
     *
     * @param menu
     * @return
     */
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //??????????????????
        MenuItem searchItem = menu.findItem(R.id.action_search);

        //??????????????????
        searchView = (SearchView) searchItem.getActionView();
        searchView.setIconified(true);
        //?????????????????????SearchView

        //?????????????????????
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /**
             * ???????????????
             * ????????????????????????
             * ?????????????????????
             * @param query
             * @return
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            /**
             * ??????????????????????????????
             * @param newText
             * @return
             */
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        //?????????????????????
        searchView.setOnCloseListener(() -> {
            return false;
        });

        //?????????????????????
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);

        //??????????????????
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }
    /**
     * ?????????????????????
     */
    private String content;
    /**
     * ????????????
     *
     * @param content
     */
    private void performSearch(String content) {
        this.content = content;
        GroupConsultation groupConsultation=new GroupConsultation();
        groupConsultation.setStart(PreferenceUtil.getId());
        Api.getInstance().findGroupConsultations(groupConsultation)
                .subscribe(new HttpObserver<List<GroupConsultation>>() {
                    @Override
                    public void onSucceeded(List<GroupConsultation> data) {
                        if(data!=null){
                            List<GroupConsultation> result=new ArrayList<>();
                            for(int i=0;i<data.size();i++){
                                if(data.get(i).getDate().contains(content)){
                                    result.add(data.get(i));
                                }
                            }
                            adapter.replaceData(result);
                        }
                    }
                });
    }
}
