package com.example.dentalhousekeeper.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.emoji.widget.EmojiEditText;
import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.adapter.HttpObserver;
import com.example.dentalhousekeeper.api.Api;
import com.example.dentalhousekeeper.domin.DICOMImage;
import com.example.dentalhousekeeper.domin.KV;
import com.example.dentalhousekeeper.photoview.PhotoView;
import com.example.dentalhousekeeper.util.DicomUtil;
import com.example.dentalhousekeeper.util.ClickUtil;
import com.example.dentalhousekeeper.util.GPUImageUtil;
import com.example.dentalhousekeeper.util.LogUtil;
import com.example.dentalhousekeeper.util.OssDownload;
import com.example.dentalhousekeeper.util.PreferenceUtil;
import com.example.dentalhousekeeper.util.ToastUtil;
import com.github.gzuliyujiang.wheelpicker.OptionPicker;
import com.github.gzuliyujiang.wheelpicker.contract.OnOptionPickedListener;
import org.dcm4che3.android.Raster;
import org.dcm4che3.android.RasterUtil;
import org.dcm4che3.android.imageio.dicom.DicomImageReader;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.dcm4che3.io.DicomInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import lombok.var;

public class DisplayDicomActivity extends Activity implements SeekBar.OnSeekBarChangeListener, View.OnTouchListener, OnOptionPickedListener {
    String path = "";
    String patientID;
    String patientName;
    String patientSex;
    String patientBirthDate;
    String patientAge;
    String patientWeight;
    String patientAddress;
    String studyID;
    String studyTime;
    String studyDate;
    String modalitiesInStudy;
    String studyDescription;
    String seriesDate;
    String seriesTime;
    String seriesDescription;
    TextView imageCountView;
    LinearLayout navigationToolbar;
    Button button;
    Bitmap bmp;
    PhotoView iv;
    ImageView ivv;
    TextView imageIndexView;
    float win_center;
    float win_width;
    int max, min;
    LinearLayout thresholdLinearLayout;
    Button previousImageButton;
    int[] pixels;
    Button nextImageButton;
    private boolean mBusy = false;
    SeekBar seekBar;
    SeekBar seekBar2;
    private GPUImage gpuImage;
    Stack<String> back;
    Stack<String> forward;
    int levelStatus=0;
    int point_width=30;
    int line_width=2;
    int text_width=5;
    int widthflag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_display_dicom);
        ButterKnife.bind(this);
        initView();
        ivv.setOnTouchListener(this);
        button = findViewById(R.id.adjustWindowCenter);
        previousImageButton = findViewById(R.id.previousImageButton);
        nextImageButton = findViewById(R.id.nextImageButton);
        seekBar = findViewById(R.id.serieSeekBar);
        seekBar.setOnSeekBarChangeListener(this);
        navigationToolbar = findViewById(R.id.navigationToolbar);
        imageIndexView = findViewById(R.id.imageIndexView);
        imageCountView = findViewById(R.id.imageCountView);
        thresholdLinearLayout = findViewById(R.id.thresholdLinearLayout);
        seekBar2 = findViewById(R.id.seekBar);
        seekBar2.setOnSeekBarChangeListener(this);
        DICOMImage dicomImage=new DICOMImage();
        dicomImage.setId(PreferenceUtil.getDICOMID());
        Api.getInstance().findDicoByPatientId(dicomImage)
                .subscribe(new HttpObserver<DICOMImage>() {
                    @Override
                    public void onSucceeded(DICOMImage data) {
                        if(data!=null){
                            path=data.getUri().split("/")[data.getUri().split("/").length-1];
                            var ossDown = new OssDownload();
                            ossDown.downLoadFile(path,getApplicationContext(),path);
                            readFile(getApplication().getExternalCacheDir()+"/"+path);
                            if(bmp==null){
                                readFile(getApplication().getExternalCacheDir()+"/"+path);
                            }
                        }
                    }
                });
    }
    int screenWidth;
    int screenHeight;
    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        iv = findViewById(R.id.iv);
        ivv=findViewById(R.id.ivv);
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        back=new Stack<>();
        forward=new Stack<>();
        canvas=new Canvas();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(30);
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    double pixelSpacing=0;
    /**
     * 读取文件数据
     */
    public void readFile(String filePath) {
        bmp=null;
        seekBar.setProgress(50);
        imageIndexView.setText("50%");
        DicomImageReader dr = new DicomImageReader();
        try {
            File file = new File(filePath);
            DicomInputStream dcmInputStream = new DicomInputStream(file);
            Attributes attrs = dcmInputStream.readDataset(-1, -1);
            Log.e("TAG", "输出所有属性信息1:" + attrs);
            int row = attrs.getInt(Tag.Rows, 1);
            int columns = attrs.getInt(Tag.Columns, 1);
            win_center = attrs.getFloat(Tag.WindowCenter, 1);
            win_width = attrs.getFloat(Tag.WindowWidth, 1);
            patientID = attrs.getString(Tag.PatientID);
            patientName = attrs.getString(Tag.PatientName);
            pixelSpacing=Double.valueOf(attrs.getString(Tag.PixelSpacing));
            patientSex = attrs.getString(Tag.PatientSex);
            patientBirthDate = attrs.getString(Tag.PatientBirthDate);
            patientAge = String.valueOf(attrs.getInt(Tag.PatientAge, 0));
            patientWeight = String.valueOf(attrs.getInt(Tag.PatientWeight, 0));
            patientAddress = attrs.getString(Tag.PatientAddress);
            studyID = attrs.getString(Tag.StudyID);
            studyTime = attrs.getString(Tag.StudyTime);
            studyDate = attrs.getString(Tag.StudyDate);
            modalitiesInStudy = attrs.getString(Tag.ModalitiesInStudy);
            seriesDate = attrs.getString(Tag.SeriesDate);
            seriesTime = attrs.getString(Tag.SeriesTime);
            seriesDescription = attrs.getString(Tag.SeriesDescription);
            imageCountView.setText("WW:" + win_width + "/WL:" + win_center);
            byte[] b = attrs.getSafeBytes(Tag.PixelData);
            if (b != null) {
                Log.e("TAG", "" + "b.length=" + b.length);
            } else {
                Log.e("TAG", "" + "b==null");
            }

            attrs.setString(Tag.SpecificCharacterSet, VR.CS, "GB18030");

            dr.open(file);
            Attributes ds = dr.getAttributes();
            int wc = ds.getInt(Tag.WindowCenter, 0);
            String ww = ds.getString(Tag.WindowWidth);
            Log.e("TAG", "" + "wc=" + wc + ",ww=" + ww);
            Raster raster = dr.applyWindowCenter(0, (int) win_width, (int) win_center);
//            Log.e("TAGTAGTAG", "" + "raster.getWidth()=" + raster.getByteData());
//            Log.e("TAG", "" + "raster.getWidth()=" + raster.getWidth() + ",raster.getHeight()=" + raster.getHeight());
//            Log.e("TAG", "" + "raster.getByteData().length=" + raster.getByteData().length);
//            Log.e("TAG", "b==raster.getByteData()" + (b == raster.getByteData()));
            bmp = RasterUtil.gray8ToBitmap(columns, row, raster.getByteData());
            iv.setImageBitmap(bmp); //显示图片
            int h = bmp.getHeight(), w = bmp.getWidth();
            pixels = new int[h * w];
            bmp.getPixels(pixels, 0, w, 0, 0, w, h);
            max = min = pixels[0];
            for (int i = 1; i < w * h; i++) {
                if (max < pixels[i])
                    max = pixels[i];
                if (min > pixels[i])
                    min = pixels[i];
            }
            Log.d("TAG", "readFile: " + max + " " + min);
            dcmInputStream.close();
            dr.close();
        } catch (Exception e) {
            Log.e("TAG", "" + e);
        }
    }


    public void gray8ToBitmap(byte[] data) {
        int ilength = data.length;
        for(int i=0;i<data.length;i++){
            Log.d("TAG", "gray8ToBitmap: "+data[i]);
        }
    }

    @OnClick({R.id.soft_light,R.id.solar,R.id.sobel,R.id.monochrome,R.id.haze,R.id.sphere,R.id.gamma,R.id.color,R.id.exposure,R.id.dissolve,R.id.blur})
    public void onViewCo(View view){
        back.push(savePicture(bmp));
        switch (view.getId()){
            case R.id.exposure:
                bmp=GPUImageUtil.getGpuImage(bmp,
                        this,gpuImage,GPUImageUtil.FilterEnum.Exposure);
                iv.setImageBitmap(bmp);
                break;
            case R.id.color:
                bmp=GPUImageUtil.getGpuImage(bmp,
                        this,gpuImage,GPUImageUtil.FilterEnum.Color);
                iv.setImageBitmap(bmp);
                break;
            case R.id.gamma:
                bmp=GPUImageUtil.getGpuImage(bmp,
                        this,gpuImage,GPUImageUtil.FilterEnum.Gamma);
                iv.setImageBitmap(bmp);
                break;
            case R.id.sphere:
                bmp=GPUImageUtil.getGpuImage(bmp,
                        this,gpuImage,GPUImageUtil.FilterEnum.Sphere);
                iv.setImageBitmap(bmp);
                break;
            case R.id.haze:
                bmp=GPUImageUtil.getGpuImage(bmp,
                        this,gpuImage,GPUImageUtil.FilterEnum.Haze);
                iv.setImageBitmap(bmp);
                break;
            case R.id.monochrome:
                bmp=GPUImageUtil.getGpuImage(bmp,
                        this,gpuImage,GPUImageUtil.FilterEnum.Monochrome);
                iv.setImageBitmap(bmp);
                break;
            case R.id.sobel:
                bmp=GPUImageUtil.getGpuImage(bmp,
                        this,gpuImage,GPUImageUtil.FilterEnum.Sobel);
                iv.setImageBitmap(bmp);
                break;
            case R.id.solar:
                bmp=GPUImageUtil.getGpuImage(bmp,
                        this,gpuImage,GPUImageUtil.FilterEnum.Solar);
                iv.setImageBitmap(bmp);
                break;
            case R.id.soft_light:
                bmp=GPUImageUtil.getGpuImage(bmp,
                        this,gpuImage,GPUImageUtil.FilterEnum.SoftLight);
                iv.setImageBitmap(bmp);
                break;
        }
    }
    @OnClick({R.id.contrast,R.id.edge1,R.id.edge2,R.id.edge,R.id.emboss,R.id.sketch,R.id.inverse_image})
    public void onViewC(View view){
        back.push(savePicture(bmp));
        switch (view.getId()){
            case R.id.inverse_image:
                bmp=GPUImageUtil.getGpuImage(bmp,
                        this,gpuImage,GPUImageUtil.FilterEnum.ColorInvert);
                iv.setImageBitmap(bmp);
                break;
            case R.id.sketch:
                bmp=GPUImageUtil.getGpuImage(bmp,
                        this,gpuImage,GPUImageUtil.FilterEnum.Sketch);
                iv.setImageBitmap(bmp);
                break;
            case R.id.emboss:
                bmp=GPUImageUtil.getGpuImage(bmp,
                        this,gpuImage,GPUImageUtil.FilterEnum.Emboss);
                iv.setImageBitmap(bmp);
                break;
            case R.id.edge:
                bmp=GPUImageUtil.getGpuImage(bmp,
                        this,gpuImage,GPUImageUtil.FilterEnum.Edge);
                iv.setImageBitmap(bmp);
                break;
            case R.id.edge1:
                bmp=GPUImageUtil.getGpuImage(bmp,
                        this,gpuImage,GPUImageUtil.FilterEnum.Edge2);
                iv.setImageBitmap(bmp);
                break;
            case R.id.edge2:
                bmp=GPUImageUtil.getGpuImage(bmp,
                        this,gpuImage,GPUImageUtil.FilterEnum.Edge3);
                iv.setImageBitmap(bmp);
                break;
            case R.id.dissolve:
                bmp=GPUImageUtil.getGpuImage(bmp,
                        this,gpuImage,GPUImageUtil.FilterEnum.Dissolve);
                iv.setImageBitmap(bmp);
                break;
            case R.id.blur:
                bmp=GPUImageUtil.getGpuImage(bmp,
                        this,gpuImage,GPUImageUtil.FilterEnum.Blur);
                iv.setImageBitmap(bmp);
                break;
            case R.id.contrast:
                bmp=GPUImageUtil.getGpuImage(bmp,
                        this,gpuImage,GPUImageUtil.FilterEnum.Contrast);
                iv.setImageBitmap(bmp);
                break;
        }
    }

    @BindView(R.id.adjustWindowCenter)
    Button adjustWindowCenter;
    @OnClick({R.id.threshold, R.id.sharpen, R.id.detail, R.id.nextImageButton, R.id.enlarge, R.id.narrow, R.id.left, R.id.right, R.id.adjustWindowCenter, R.id.resume, R.id.previousImageButton})
    public void onViewClicked(View view) {
        if (ClickUtil.isFastClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.enlarge:
                back.push(savePicture(bmp));
                if (iv.getScale() < 2.9) {
                    iv.setScale((float) (iv.getScale() + 0.1));
                }
                break;
            case R.id.narrow:
                back.push(savePicture(bmp));
                if (iv.getScale() > 1.1) {
                    iv.setScale((float) (iv.getScale() - 0.1));
                }
                break;
            case R.id.left:
                back.push(savePicture(bmp));
                bmp = DicomUtil.rotate(bmp, 270);
                iv.setImageBitmap(bmp);
                break;
            case R.id.right:
                back.push(savePicture(bmp));
                bmp = DicomUtil.rotate(bmp, 90);
                iv.setImageBitmap(bmp);
                break;
            case R.id.adjustWindowCenter:
                labelDisplay.setVisibility(View.GONE);
                thresholdLinearLayout.setVisibility(View.INVISIBLE);
                if (adjustWindowCenter.getCurrentTextColor()==getResources().getColor(R.color.red)) {
                    navigationToolbar.setVisibility(View.INVISIBLE);
                    adjustWindowCenter.setTextColor(getResources().getColor(R.color.white));
                } else {
                    clearOther();
                    back.push(savePicture(bmp));
                    adjustWindowCenter.setTextColor(getResources().getColor(R.color.red));
                    navigationToolbar.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.resume:
                if(bmp!=null){
                    back.push(savePicture(bmp));
                }
                readFile(getApplication().getExternalCacheDir()+"/"+path);
                ToastUtil.successShortToast("重置成功");
                break;
            case R.id.previousImageButton:
                back.push(savePicture(bmp));
                nextImageButton.setVisibility(View.VISIBLE);
                if (seekBar.getProgress() > 5) {
                    seekBar.setProgress(seekBar.getProgress() - 5);
                } else if (seekBar.getProgress() > 1) {
                    seekBar.setProgress(seekBar.getProgress() - 1);
                } else {
                    seekBar.setProgress(0);
                    previousImageButton.setVisibility(View.INVISIBLE);
                }
                imageIndexView.setText(seekBar.getProgress() + "%");
                break;
            case R.id.nextImageButton:
                back.push(savePicture(bmp));
                previousImageButton.setVisibility(View.VISIBLE);
                if (seekBar.getProgress() < 95) {
                    seekBar.setProgress(seekBar.getProgress() + 5);
                } else if (seekBar.getProgress() < 99) {
                    seekBar.setProgress(seekBar.getProgress() + 1);
                } else if (seekBar.getProgress() >= 99 && seekBar.getProgress() < 100) {
                    seekBar.setProgress(100);
                    nextImageButton.setVisibility(View.INVISIBLE);
                }
                imageIndexView.setText(seekBar.getProgress() + "%");
                break;
            case R.id.detail:
                List<KV> data = new ArrayList<>();
                data.add(new KV("PatientId", patientID));
                data.add(new KV("PatientName", patientName));
                data.add(new KV("PatientSex", patientSex));
                data.add(new KV("PatientBirthDate", patientBirthDate));
                data.add(new KV("PatientAge", patientAge));
                data.add(new KV("PatientWeight", patientWeight));
                data.add(new KV("PatientAddress", patientAddress));
                data.add(new KV("StudyID", studyID));
                data.add(new KV("StudyTime", studyTime));
                data.add(new KV("StudyDate", studyDate));
                data.add(new KV("ModalitiesInStudy", modalitiesInStudy));
                data.add(new KV("StudyDescription", studyDescription));
                data.add(new KV("SeriesDate", seriesDate));
                data.add(new KV("SeriesTime", seriesTime));
                data.add(new KV("SeriesDescription", seriesDescription));
                OptionPicker picker = new OptionPicker(this);
                picker.setTitle("DICOM详情");
                picker.setBodyWidth(500);
                picker.setData(data);
                picker.setDefaultPosition(0);
                picker.show();
                break;
            case R.id.sharpen:
                back.push(savePicture(bmp));
                bmp = sharpenImageAmeliorate(bmp);
                iv.setImageBitmap(bmp);
                break;
            case R.id.threshold:
                navigationToolbar.setVisibility(View.INVISIBLE);
                labelDisplay.setVisibility(View.GONE);
                if (threshold.getCurrentTextColor()==getResources().getColor(R.color.red)) {
                    threshold.setTextColor(getResources().getColor(R.color.white));
                    thresholdLinearLayout.setVisibility(View.INVISIBLE);
                } else {
                    clearOther();
                    back.push(savePicture(bmp));
                    threshold.setTextColor(getResources().getColor(R.color.red));
                    thresholdLinearLayout.setVisibility(View.VISIBLE);
                    seekBar2.setProgress(0);
                }
                break;
        }
    }

    private void clearOther() {
        threshold.setTextColor(getResources().getColor(R.color.white));
        adjustWindowCenter.setTextColor(getResources().getColor(R.color.white));
        label.setTextColor(getResources().getColor(R.color.white));
    }

    @BindView(R.id.threshold)
    Button threshold;
    @BindView(R.id.label)
    Button label;
    @BindView(R.id.label_display)
    LinearLayout labelDisplay;
    @BindView(R.id.distance)
    Button distance;
    @OnClick(R.id.distance)
    public void onclic(View view){
        switch (view.getId()){
            case R.id.distance:
                levelStatus=0;
                if(line.getCurrentTextColor()==getResources().getColor(R.color.red)){
                    distance.setTextColor(getResources().getColor(R.color.white));
                }else{
                    levelStatus=8;
                    clearOthers();
                    distance.setTextColor(getResources().getColor(R.color.red));
                }
        }
    }
    @OnClick({R.id.text_width,R.id.point_width,R.id.line_width,R.id.oval,R.id.line1,R.id.rect,R.id.circle,R.id.text,R.id.line,R.id.point})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.point:
                levelStatus=0;
                if(point.getCurrentTextColor()==getResources().getColor(R.color.red)){
                    point.setTextColor(getResources().getColor(R.color.white));
                }else{
                    clearOthers();
                    levelStatus=1;
                    point.setTextColor(getResources().getColor(R.color.red));
                }
                break;
            case R.id.line:
                levelStatus=0;
                if(line.getCurrentTextColor()==getResources().getColor(R.color.red)){
                    line.setTextColor(getResources().getColor(R.color.white));
                }else{
                    levelStatus=2;
                    clearOthers();
                    line.setTextColor(getResources().getColor(R.color.red));
                }
                break;
            case R.id.text:
                levelStatus=0;
                if(text.getCurrentTextColor()==getResources().getColor(R.color.red)){
                    text.setTextColor(getResources().getColor(R.color.white));
                }else{
                    levelStatus=3;
                    clearOthers();
                    text.setTextColor(getResources().getColor(R.color.red));
                }
                break;
            case R.id.circle:
                levelStatus=0;
                if(circle.getCurrentTextColor()==getResources().getColor(R.color.red)){
                    circle.setTextColor(getResources().getColor(R.color.white));
                }else{
                    levelStatus=4;
                    clearOthers();
                    circle.setTextColor(getResources().getColor(R.color.red));
                }
                break;
            case R.id.rect:
                levelStatus=0;
                if(rect.getCurrentTextColor()==getResources().getColor(R.color.red)){
                    rect.setTextColor(getResources().getColor(R.color.white));
                }else{
                    levelStatus=5;
                    clearOthers();
                    rect.setTextColor(getResources().getColor(R.color.red));
                }
                break;
            case R.id.line1:
                levelStatus=0;
                if(line1.getCurrentTextColor()==getResources().getColor(R.color.red)){
                    line1.setTextColor(getResources().getColor(R.color.white));
                }else{
                    levelStatus=6;
                    clearOthers();
                    line1.setTextColor(getResources().getColor(R.color.red));
                }
                break;
            case R.id.oval:
                levelStatus=0;
                if(oval.getCurrentTextColor()==getResources().getColor(R.color.red)){
                    oval.setTextColor(getResources().getColor(R.color.white));
                }else{
                    levelStatus=7;
                    clearOthers();
                    oval.setTextColor(getResources().getColor(R.color.red));
                }
                break;
            case R.id.line_width:
                widthflag=1;
                displayDialog();
                break;
            case R.id.point_width:
                widthflag=2;
                displayDialog();
                break;
            case R.id.text_width:
                widthflag=3;
                displayDialog();
                break;
        }
    }

    private void clearOthers() {
        distance.setTextColor(getResources().getColor(R.color.white));
        point.setTextColor(getResources().getColor(R.color.white));
        line.setTextColor(getResources().getColor(R.color.white));
        text.setTextColor(getResources().getColor(R.color.white));
        circle.setTextColor(getResources().getColor(R.color.white));
        rect.setTextColor(getResources().getColor(R.color.white));
        line1.setTextColor(getResources().getColor(R.color.white));
        oval.setTextColor(getResources().getColor(R.color.white));
    }

    @OnClick({R.id.label,R.id.forward,R.id.back})
    public void onC(View v){
        switch (v.getId()){

            case R.id.back:
                if(back.size()!=0){
                    forward.push(savePicture(bmp));
                    bmp=readPicture(back.peek());
                    iv.setImageBitmap(bmp);
                    back.pop();
                }else{
                    ToastUtil.successLongToast("回退栈为空，无法回退");
                }
                break;
            case R.id.forward:
                if(forward.size()!=0){
                    back.push(forward.peek());
                    forward.pop();
                    bmp=readPicture(back.peek());
                    iv.setImageBitmap(bmp);
                }else{
                    ToastUtil.successLongToast("前进栈为空，无法前进");
                }
                break;
            case R.id.label:
                navigationToolbar.setVisibility(View.INVISIBLE);
                thresholdLinearLayout.setVisibility(View.INVISIBLE);
                if(label.getCurrentTextColor()==getResources().getColor(R.color.red)){
                    label.setTextColor(getResources().getColor(R.color.white));
                    labelDisplay.setVisibility(View.GONE);
                }else{
                    clearOther();
                    label.setTextColor(getResources().getColor(R.color.red));
                    labelDisplay.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private Bitmap readPicture(String file){
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            LogUtil.d("324324",e.toString());
            e.printStackTrace();
        }
        Bitmap bitmap  = BitmapFactory.decodeStream(fis);
        if(bitmap!=null){
            return bitmap;
        }else{
            ToastUtil.successLongToast("error");
            return null;
        }
    }

    public String savePicture(Bitmap bitmap) {
        String name= getApplicationContext().getExternalCacheDir()+"/"+System.currentTimeMillis()+".jpg";
        File file = new File(name);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            LogUtil.d("dlkjsfdf",e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            LogUtil.d("dlskfjfd",e.toString());
            e.printStackTrace();
        }
        return name;
    }

//    public String savePicture(Bitmap bitmap) {
//        String name=getApplication().getExternalCacheDir()+"/"+PreferenceUtil.getId()+"/"+System.currentTimeMillis()+".jpg";
//        File f = new File(name);
//        try {
//            f.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        FileOutputStream fOut = null;
//        try {
//            fOut = new FileOutputStream(f);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        if(bmp==null){
//            ToastUtil.successLongToast("bmp==NULL");
//        }else{
//            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
//        }
//        try {
//            fOut.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            fOut.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return name;
//    }

//    private String savePicture(Bitmap b){
//        String name=getApplication().getExternalCacheDir()+"/"+PreferenceUtil.getId()+"/"+System.currentTimeMillis()+".png";
//        try {
//            OutputStream os = new FileOutputStream(new File(name));
//            b.compress(Bitmap.CompressFormat.PNG, 100, os);
//            os.flush();
//            os.close();
//        } catch (Exception e) {
//            Log.e("TAG", "", e);
//        }
//        return name;
//    }

    private void displayDialog() {
        List<Integer> data = new ArrayList<>();
        for(int i=1;i<=30;i++){
            data.add(i);
        }
        OptionPicker picker = new OptionPicker(this);
        if(widthflag==1){
            picker.setTitle("线宽");
        }else if(widthflag==2){
            picker.setTitle("点宽");
        }else{
            picker.setTitle("字宽");
        }
        picker.setBodyWidth(500);
        picker.setData(data);
        picker.setDefaultPosition(0);
        picker.setOnOptionPickedListener(this);
        picker.show();
    }

    @BindView(R.id.oval)
    Button oval;
    @BindView(R.id.line1)
    Button line1;
    @BindView(R.id.rect)
    Button rect;
    @BindView(R.id.circle)
    Button circle;
    @BindView(R.id.text)
    Button text;
    @BindView(R.id.line)
    Button line;
    @BindView(R.id.point)
    Button point;

    /***
     * 锐化
     * @param bmp
     * @return
     */
    private Bitmap sharpenImageAmeliorate(Bitmap bmp) {
        long start = System.currentTimeMillis();
        // 拉普拉斯矩阵
        int[] laplacian = new int[]{-1, -1, -1, -1, 8, -1, -1, -1, -1};

        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;

        int idx = 0;
        float alpha = 0.3F;
        for (int i = 0; i < width * height; i++) {
            Log.d("TAG", "sharpenImageAmeliorate: " + pixels[i]);
        }
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                idx = 0;
                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        pixColor = pixels[(i + n) * width + k + m];
                        pixR = Color.red(pixColor);
                        pixG = Color.green(pixColor);
                        pixB = Color.blue(pixColor);

                        newR = newR + (int) (pixR * laplacian[idx] * alpha);
                        newG = newG + (int) (pixG * laplacian[idx] * alpha);
                        newB = newB + (int) (pixB * laplacian[idx] * alpha);
                        idx++;
                    }
                }

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                pixels[i * width + k] = Color.argb(255, newR, newG, newB);
                newR = 0;
                newG = 0;
                newB = 0;
            }
        }

        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        long end = System.currentTimeMillis();
        Log.d("may", "used time=" + (end - start));
        return bitmap;
    }

    boolean myflag = false;

    public synchronized void detection(int progress) {
        if (myflag)
            return;
        myflag = true;
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        //存放处理后的图象各像素点的数组
        int[] tmp = new int[w * h];
        bmp.getPixels(tmp, 0, w, 0, 0, w, h);
        Log.d("TAG", "getBitmap: " + tmp[0]);
        max = min = tmp[0];
        for (int i = 0; i < w * h; i++) {
            if (max < tmp[i])
                max = tmp[i];
            if (min > tmp[i])
                min = tmp[i];
        }
        int mid = (int) (min + (float) (max - min) * (float) progress / 100.0);
        Log.d("TAG", "detection: " + min + " " + max + " " + mid);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (tmp[i * w + j] < mid) {
                    tmp[i * w + j] = Color.BLACK;
                }
            }
        }
        bmp = Bitmap.createBitmap(tmp, w, h,
                Bitmap.Config.ARGB_8888);
        iv.setImageBitmap(bmp);
        myflag = false;
    }

    /**
     * 读取文件数据
     */
    public void refresh(int nx, int ny) {
        DicomImageReader dr = new DicomImageReader();
        try {
            File file = new File(getApplication().getExternalCacheDir()+"/"+path);
            DicomInputStream dcmInputStream = new DicomInputStream(file);
            Attributes attrs = dcmInputStream.readDataset(-1, -1);
            int row = attrs.getInt(Tag.Rows, 1);
            int columns = attrs.getInt(Tag.Columns, 1);
            attrs.setString(Tag.SpecificCharacterSet, VR.CS, "GB18030");
            dr.open(file);
            Raster raster = dr.applyWindowCenter(0, (int) (win_width + nx), (int) (win_center + ny));
            imageCountView.setText("WW:" + (win_width + nx) + "/WL:" + (win_center + ny));
            bmp = RasterUtil.gray8ToBitmap(columns, row, raster.getByteData());
            iv.setImageBitmap(bmp);
            dcmInputStream.close();
            dr.close();
        } catch (Exception e) {
            Log.e("TAG324324234324234", "" + e);
        }
    }

    @Override
    public synchronized void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.equals(this.seekBar)) {
            // If it is busy, do nothing
            if (mBusy)
                return;

            // It is busy now
            mBusy = true;
            if (progress == 0) {
                previousImageButton.setVisibility(View.INVISIBLE);
            } else if (progress == 100) {
                nextImageButton.setVisibility(View.INVISIBLE);
            } else {
                previousImageButton.setVisibility(View.VISIBLE);
                nextImageButton.setVisibility(View.VISIBLE);
            }
            refresh((progress - 50) * 100, (progress - 50) * 100);
            mBusy = false;
            imageIndexView.setText(progress + "%");
            Log.d("dklfj", progress + "kkdjsf");
        } else {
            detection(progress);
            Log.d("TAG", "onProgressChanged: 111111");
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public static int GYBySobel(int x, int y, int pixel[], int w, int h) {
        if (x >= 1 && x < w - 1 && y >= 1 && y < h - 1 && (x + 1) * w + y - 1 < w * h && x * w + y + 1 < w * h && w * (x + 1) + y + 1 < w * h) {
            int res = 1 * pixel[((x - 1) * w + y - 1)]
                    + 1 * pixel[x * w + y - 1]
                    + 1 * pixel[(x + 1) * w + y - 1]
                    + (-1) * pixel[(x - 1) * w + y + 1]
                    + (-1) * pixel[x * w + y + 1]
                    + (-1) * pixel[w * (x + 1) + y + 1];
            return res;
        }
        return pixel[x * w + y];
    }

    public Bitmap getBitmap() {
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int[] cmap = new int[w * h];
        int[] tmp = new int[w * h];
        bmp.getPixels(tmp, 0, w, 0, 0, w, h);
        Log.d("TAG", "getBitmap: " + tmp[0]);
        for (int i = 0; i < w * h; i++) {
            Log.d("TAG", "getBitmap: " + tmp[i]);
        }
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                cmap[i * w + j] = GYBySobel(i, j, tmp, w, h);
            }
        }
        Bitmap bm = Bitmap.createBitmap(cmap, bmp.getWidth(), bmp.getHeight(),
                Bitmap.Config.ARGB_8888);

        return bm;
    }
    int[] mov_x = {0,0},mov_y={0,0};
    private Paint paint;
    private Canvas canvas;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(!bmp.isMutable()){
            bmp = bmp.copy(Bitmap.Config.ARGB_8888, true);
        }
        canvas.setBitmap(bmp);
        float scale=((float)bmp.getHeight())/screenHeight;
//        float scalex=screenWidth-80-(float)bmp.getWidth()/((screenWidth-80-bmp.getWidth()/scale));
        if(event.getAction()==MotionEvent.ACTION_UP){
            back.push(savePicture(bmp));
            if(levelStatus==4){
                paint.setStrokeWidth(line_width);
                int x=(int)(((int) event.getX()-(screenWidth-80-bmp.getWidth()/scale)/2)*scale)-mov_x[0];
                int y=(int)((int) event.getY()*scale)-mov_y[0];
                canvas.drawCircle(mov_x[0], mov_y[0],(int)(Math.sqrt(x*x+y*y)) ,paint);// 大圆
                iv.setImageBitmap(bmp);
            }else if(levelStatus==5){
                paint.setStrokeWidth(line_width);
                int x=(int)(((int) event.getX()-(screenWidth-80-bmp.getWidth()/scale)/2)*scale);
                int y=(int)((int) event.getY()*scale);
                canvas.drawRect(mov_x[0], mov_y[0],x,y,paint);// 大圆
                iv.setImageBitmap(bmp);
            }else if(levelStatus==6){
                paint.setStrokeWidth(line_width);
                canvas.drawLine(mov_x[0],mov_y[0],((int) event.getX()-(screenWidth-80-bmp.getWidth()/scale)/2)*scale, (int) event.getY()*scale, paint);//画线
                iv.setImageBitmap(bmp);
            }else if(levelStatus==7){
                int x=(int)(((int) event.getX()-(screenWidth-80-bmp.getWidth()/scale)/2)*scale);
                int y=(int)((int) event.getY()*scale);
                RectF oval2 = new RectF(mov_x[0],mov_y[0],x,y);// 设置个新的长方形，扫描测量
                paint.setStrokeWidth(line_width);
                canvas.drawOval(oval2, paint);
                iv.setImageBitmap(bmp);
            }else if(levelStatus==8){
                paint.setStrokeWidth(line_width);
                double _x=((int) event.getX()-(screenWidth-80-bmp.getWidth()/scale)/2)*scale-mov_x[0];
                double _y=(int) event.getY()*scale-mov_y[0];
                double distances=Math.sqrt(_x*_x+_y*_y)*pixelSpacing;
                canvas.drawLine(mov_x[0],mov_y[0],((int) event.getX()-(screenWidth-80-bmp.getWidth()/scale)/2)*scale, (int) event.getY()*scale, paint);//画线
                paint.setTextSize(45);
                paint.setStrokeWidth(text_width);
                String result=String.format("%.2f",distances);
                canvas.drawText(result+"mm", ((int) event.getX()-(screenWidth-80-bmp.getWidth()/scale)/2)*scale+15, (int) event.getY()*scale+15, paint);
                iv.setImageBitmap(bmp);
            }
        }else if(event.getAction()==MotionEvent.ACTION_MOVE){
            if(levelStatus==2){
                paint.setStrokeWidth(line_width);
                canvas.drawLine(mov_x[1],mov_y[1],((int) event.getX()-(screenWidth-80-bmp.getWidth()/scale)/2)*scale, (int) event.getY()*scale, paint);//画线
                iv.setImageBitmap(bmp);
            }
        }else if(event.getAction()==MotionEvent.ACTION_DOWN) {
            mov_x[0]=(int)(((int) event.getX()-(screenWidth-80-bmp.getWidth()/scale)/2)*scale);
            mov_y[0]=(int)((int) event.getY()*scale);
            if(levelStatus==1){
                back.push(savePicture(bmp));
                paint.setStrokeWidth(point_width);
                canvas.drawPoint(mov_x[0], mov_y[0], paint);
                iv.setImageBitmap(bmp);
            }else if(levelStatus==3){
                final Dialog bgSetDialog = new Dialog(DisplayDicomActivity.this,R.style.BottomDialogStyle);
                View view1 = LayoutInflater.from(DisplayDicomActivity.this).inflate(R.layout.bg_set_dialog_delete, null);
                EmojiEditText emojiEditText=view1.findViewById(R.id.input);
                TextView textView=view1.findViewById(R.id.delete);
                TextView cancel=view1.findViewById(R.id.cancel);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        back.push(savePicture(bmp));
                        paint.setTextSize(45);
                        paint.setStrokeWidth(text_width);
                        canvas.drawText(emojiEditText.getText().toString(), mov_x[0], mov_y[0], paint);
                        iv.setImageBitmap(bmp);
                        bgSetDialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bgSetDialog.dismiss();
                    }
                });
                bgSetDialog.setContentView(view1);
                Window dialogWindow = bgSetDialog.getWindow();
                dialogWindow.setGravity(Gravity.BOTTOM);
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.width = (int) (DisplayDicomActivity.this.getWindowManager().getDefaultDisplay().getWidth()*0.95);
                lp.y = 60;
                dialogWindow.setAttributes(lp);
                bgSetDialog.show();
            }
        }
        mov_x[1]=(int)(((int) event.getX()-(screenWidth-80-bmp.getWidth()/scale)/2)*scale);
        mov_y[1]=(int)((int) event.getY()*scale);
        if(labelDisplay.getVisibility()==View.GONE)
            return false;
        else
            return true;
    }

    @Override
    public void onOptionPicked(int position, Object item) {
        if(widthflag==1){
            line_width=(Integer)item;
        }else if(widthflag==2){
            point_width=(Integer)item;
        }else{
            text_width=(Integer)item;
        }
    }

    @Override
    protected void onDestroy() {
        File file=new File(getApplicationContext().getExternalCacheDir().toString());
        if (file == null || !file.exists() || !file.isDirectory())
            return;
        for (File f : file.listFiles()) {
            if (f.isFile()&&f.getName().contains(".jpg"))
                f.delete();
        }
        super.onDestroy();
    }
}
