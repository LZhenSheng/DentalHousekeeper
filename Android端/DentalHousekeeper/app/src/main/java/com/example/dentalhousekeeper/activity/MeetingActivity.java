package com.example.dentalhousekeeper.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.dentalhousekeeper.R;
import com.tencent.liteav.tuiroom.model.TUIRoomCore;
import com.tencent.liteav.tuiroom.model.TUIRoomCoreCallback;
import com.tencent.liteav.tuiroom.model.TUIRoomCoreDef;
import com.tencent.liteav.tuiroom.model.TUIRoomCoreListener;
import com.tencent.trtc.TRTCCloudDef;
import com.tencent.trtc.TRTCStatistics;

import java.util.List;

public class MeetingActivity extends AppCompatActivity implements TUIRoomCoreListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        // 1.房主调用创建房间
        // 2.初始化TUIRoomCore实例
        TUIRoomCore mTUIRoomCore = TUIRoomCore.getInstance(getApplicationContext());
        mTUIRoomCore.setListener(this);
        // 1.房主调用创建房间
        mTUIRoomCore.createRoom("12345", TUIRoomCoreDef.SpeechMode.FREE_SPEECH,
                new TUIRoomCoreCallback.ActionCallback() {
                    @Override
                    public void onCallback(int code, String msg) {
                        if (code == 0) {
                            // 创建房间成功
                        }
                }
        });
    }

    @Override
    public void onError(int code, String message) {

    }

    @Override
    public void onDestroyRoom() {

    }

    @Override
    public void onUserVoiceVolume(String userId, int volume) {

    }

    @Override
    public void onRoomMasterChanged(String previousUserId, String currentUserId) {

    }

    @Override
    public void onRemoteUserEnter(String userId) {

    }

    @Override
    public void onRemoteUserLeave(String userId) {

    }

    @Override
    public void onRemoteUserCameraAvailable(String userId, boolean available) {

    }

    @Override
    public void onRemoteUserScreenVideoAvailable(String userId, boolean available) {

    }

    @Override
    public void onRemoteUserAudioAvailable(String userId, boolean available) {

    }

    @Override
    public void onRemoteUserEnterSpeechState(String userId) {

    }

    @Override
    public void onRemoteUserExitSpeechState(String userId) {

    }

    @Override
    public void onReceiveChatMessage(String userId, String message) {

    }

    @Override
    public void onReceiveRoomCustomMsg(String userId, String data) {

    }

    @Override
    public void onReceiveSpeechInvitation(String userId) {

    }

    @Override
    public void onReceiveInvitationCancelled(String userId) {

    }

    @Override
    public void onReceiveSpeechApplication(String userId) {

    }

    @Override
    public void onSpeechApplicationCancelled(String userId) {

    }

    @Override
    public void onSpeechApplicationForbidden(boolean isForbidden) {

    }

    @Override
    public void onOrderedToExitSpeechState(String userId) {

    }

    @Override
    public void onCallingRollStarted(String userId) {

    }

    @Override
    public void onCallingRollStopped(String userId) {

    }

    @Override
    public void onMemberReplyCallingRoll(String userId) {

    }

    @Override
    public void onChatRoomMuted(boolean muted) {

    }

    @Override
    public void onMicrophoneMuted(boolean muted) {

    }

    @Override
    public void onCameraMuted(boolean muted) {

    }

    @Override
    public void onReceiveKickedOff(String userId) {

    }

    @Override
    public void onStatistics(TRTCStatistics statistics) {

    }

    @Override
    public void onNetworkQuality(TRTCCloudDef.TRTCQuality localQuality, List<TRTCCloudDef.TRTCQuality> remoteQuality) {

    }

    @Override
    public void onScreenCaptureStarted() {

    }

    @Override
    public void onScreenCaptureStopped(int reason) {

    }
}
