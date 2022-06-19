package com.example.dentalhousekeeper.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.adapter.HttpObserver;
import com.example.dentalhousekeeper.api.Api;
import com.example.dentalhousekeeper.domin.DetailResponse;
import com.example.dentalhousekeeper.domin.Doctor;
import com.example.dentalhousekeeper.domin.Patient;
import com.example.dentalhousekeeper.domin.Session;
import com.example.dentalhousekeeper.fragment.GenderDialogFragment;
import com.example.dentalhousekeeper.util.ImageUtil;
import com.example.dentalhousekeeper.util.LogUtil;
import com.example.dentalhousekeeper.util.OssDownload;
import com.example.dentalhousekeeper.util.PreferenceUtil;
import com.example.dentalhousekeeper.util.StringUtil;
import com.example.dentalhousekeeper.util.ToastUtil;
import com.github.gzuliyujiang.wheelpicker.BirthdayPicker;
import com.github.gzuliyujiang.wheelpicker.NumberPicker;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatePickedListener;
import com.github.gzuliyujiang.wheelpicker.contract.OnNumberPickedListener;
import com.github.gzuliyujiang.wheelpicker.contract.OnNumberSelectedListener;
import com.github.gzuliyujiang.wheelview.contract.WheelFormatter;
import com.ixuea.regionselector.Region;
import com.ixuea.regionselector.RegionSelector;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MessagePatientActivity extends BaseTitleActivity implements OnDatePickedListener, OnNumberPickedListener {

    /**
     * 地区
     */
    @BindView(R.id.tv_area)
    TextView tvArea;

    @BindView(R.id.et_nickname)
    EditText etNickname;

    @BindView(R.id.tv_gender)
    TextView tvGender;

    @BindView(R.id.tv_id)
    EditText tvId;

    /***
     * 省
     */
    String provinceName;

    /***
     * 市
     */
    String cityName;

    /***
     * 地区
     */
    String areaName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_patient);
    }

    @Override
    public void initData() {
        super.initData();
        Log.d(TAG, "initData: " + PreferenceUtil.getId());
        Patient patient = new Patient();
        patient.setId(PreferenceUtil.getId());
        Api.getInstance()
                .findPatientById(patient)
                .subscribe(new HttpObserver<Patient>() {
                    @Override
                    public void onSucceeded(Patient data) {
                        Log.d(TAG, "onSucceeded: " + data.toString());
                        if (data.getProvince() != null && data.getCity() != null && data.getArea() != null) {
                            tvArea.setText(data.getProvince() + "-" + data.getCity() + "-" + data.getCity());
                        }
                        if (data.getGender() == 0) {
                            tvGender.setText("女");
                        } else {
                            tvGender.setText("男");
                        }
                        etNickname.setText(data.getName());
                        tvId.setText(data.getCard());
                        tv_age.setText(String.valueOf(data.getAge()));
                        tv_birthday.setText(data.getBirthday());
                        ImageUtil.show(getApplicationContext(),iv_avatar,data.getAvatar());
                        avatar=data.getAvatar();
                    }

                    @Override
                    public boolean onFailed(Patient data, Throwable e) {
                        ToastUtil.errorShortToast(R.string.error_network_connect);
                        return false;
                    }
                });
    }

    @OnClick({R.id.avatar_container, R.id.age_container, R.id.birthday_container, R.id.gender_container, R.id.area_container, R.id.save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.age_container:
                NumberPicker picker = new NumberPicker(this);
                picker.setOnNumberPickedListener(this);
                picker.getWheelLayout().setOnNumberSelectedListener(new OnNumberSelectedListener() {
                    @Override
                    public void onNumberSelected(int position, Number item) {
                        picker.getTitleView().setText(picker.getWheelView().formatItem(position));
                    }
                });
                picker.setFormatter(new WheelFormatter() {
                    @Override
                    public String formatItem(@NonNull Object item) {
                        return item.toString();
                    }
                });
                picker.setRange(0, 100, 1);
                picker.setDefaultValue(172);
                picker.setTitle("年龄选择");
                picker.show();
                break;
            case R.id.birthday_container:
                BirthdayPicker picker1 = new BirthdayPicker(this);
                picker1.setDefaultValue(1991, 11, 11);
                picker1.setTitle("出生日期选择");
                picker1.setOnDatePickedListener(this);
                picker1.getWheelLayout().setResetWhenLinkage(false);
                picker1.show();
                break;
            case R.id.gender_container:
                GenderDialogFragment
                        .show(getSupportFragmentManager(), 0, ((dialog, which) -> {
                            //关闭对话框
                            dialog.dismiss();
                            switch (which) {
                                case 1:
                                    tvGender.setText("男");
                                    break;
                                case 2:
                                    tvGender.setText("女");
                                    break;
                            }
                        }));
                break;
            case R.id.area_container:
                //城市选择器初始化
                RegionSelector.init(this).start(this);
                break;
            case R.id.avatar_container:
                selectImage();
                break;
            case R.id.save:
                Patient patient = new Patient(PreferenceUtil.getId(), getContent(etNickname), tvGender.getText().toString().equals("男") ? 1 : 0,
                        provinceName, cityName, areaName, tvId.getText().toString(),Integer.valueOf(tv_age.getText().toString()),tv_birthday.getText().toString(),avatar);
                Api.getInstance()
                        .updateMessage(patient)
                        .subscribe(new HttpObserver<DetailResponse<Boolean>>() {
                            /**
                             * 登录成功
                             *
                             * @param data
                             */
                            @Override
                            public void onSucceeded(DetailResponse<Boolean> data) {
                                LogUtil.d(TAG, "onLoginClick success:" + data);

                                if (data.getData()) {
                                    ToastUtil.successShortToast("更新成功");
                                    finish();
                                } else {
                                    ToastUtil.successShortToast("更新失败");
                                }

                            }

                            /**
                             * 登录失败
                             *
                             * @param data
                             * @param e
                             * @return
                             */
                            @Override
                            public boolean onFailed(DetailResponse<Boolean> data, Throwable e) {
                                ToastUtil.errorShortToast(R.string.error_network_connect);
                                return false;
                            }
                        });
                break;
        }
    }

    String picPath;
    String avatar;
    @BindView(R.id.iv_avatar)
    CircleImageView iv_avatar;
    /**
     * 回调方法
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            //请求成功了

            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    //选择了媒体回调

                    //获取选择的资源
                    List<LocalMedia> datum = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    picPath = datum.get(0).getPath();
                    OssDownload ossDownload=new OssDownload();
                    avatar= UUID.randomUUID()+".png";
                    ossDownload.uploadFile(avatar,getApplicationContext(),picPath);
                    avatar="uri/"+avatar;
                    ToastUtil.successLongToast(avatar);
                    Glide.with(getMainActivity()).load(avatar).into(iv_avatar);
                    break;
                case RegionSelector.REQUEST_REGION:
                    //城市选择
                    //这里的Id和iOS那边城市选择框架的Id不一样
                    //这里我们没有用到所以没多大影响
                    //真实项目中要保持一致

                    //省
                    Region province = RegionSelector.getProvince(data);

                    //市
                    Region city = RegionSelector.getCity(data);

                    //区
                    Region area = RegionSelector.getArea(data);

                    //设置数据
                    //省
                    provinceName = province.getName();

                    //市
                    cityName = city.getName();

                    //区
                    areaName = area.getName();

                    //显示地区
                    tvArea.setText(getResources().getString(R.string.area_value2,
                            provinceName,
                            cityName,
                            areaName));
                    break;
            }
        }
    }


    String getContent(EditText editText) {
        if (tvArea.equals(editText)) {
            return tvArea.getText().toString();
        } else if (etNickname.equals(editText)) {
            return etNickname.getText().toString();
        } else if (tvGender.equals(editText)) {
            return tvGender.getText().toString();
        } else if (tvId.equals(editText)) {
            return tvId.getText().toString();
        } else {

            return null;
        }
    }

    @BindView(R.id.tv_birthday)
    TextView tv_birthday;

    @Override
    public void onDatePicked(int year, int month, int day) {
        tv_birthday.setText(year + "-" + month + "-" + day);
    }

    @BindView(R.id.tv_age)
    TextView tv_age;

    @Override
    public void onNumberPicked(int position, Number item) {
        tv_age.setText(String.valueOf(item));
    }

    /**
     * 选择图片
     */
    private void selectImage() {
        //进入相册
        //以下是例子
        //用不到的api可以不写
        PictureSelector
                .create(getMainActivity())
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                //.theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(3)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .previewVideo(false)// 是否可预览视频 true or false
                .enablePreviewAudio(false) // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.JPEG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                //.glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                //.hideBottomControls()// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
                //.compressSavePath(getPath())//压缩图片保存地址
                //.freeStyleCropEnabled()// 裁剪框是否可拖拽 true or false
                //.circleDimmedLayer()// 是否圆形裁剪 true or false
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
                //.selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(50)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(50)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(false) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                //.videoQuality()// 视频录制质量 0 or 1 int
                //.videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
                //.videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
                //.recordVideoSecond()//视频秒数录制 默认60s int
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

}
