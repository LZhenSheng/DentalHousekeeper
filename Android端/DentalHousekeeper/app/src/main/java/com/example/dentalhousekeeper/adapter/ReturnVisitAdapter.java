package com.example.dentalhousekeeper.adapter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dentalhousekeeper.R;
import com.example.dentalhousekeeper.activity.UserPersonMessageActivity;
import com.example.dentalhousekeeper.api.Api;
import com.example.dentalhousekeeper.domin.AppointMent;
import com.example.dentalhousekeeper.domin.Patient;
import com.example.dentalhousekeeper.domin.PreOrder;
import com.example.dentalhousekeeper.util.ImageUtil;
import com.example.dentalhousekeeper.util.PreferenceUtil;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReturnVisitAdapter extends BaseQuickAdapter<AppointMent, BaseViewHolder> {

    Context context;
    /**
     * 构造方法
     *
     * @param layoutResId
     */
    public ReturnVisitAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.context=context;
    }

    /**
     * 显示数据
     *
     * @param helper
     * @param data
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, AppointMent data) {
        Patient patient=new Patient();
        patient.setId(data.getPatient_id());
        Api.getInstance().findPatientById(patient)
                .subscribe(new HttpObserver<Patient>() {
                    @Override
                    public void onSucceeded(Patient data) {
                        if (data != null) {
                            helper.setText(R.id.name,data.getName());
                            CircleImageView circleImageView=helper.getView(R.id.iv_avatar);
                            if(data.getAvatar()!=null){
                                ImageUtil.show(context,circleImageView,data.getAvatar());
                            }
                        } else {
//                                    ToastUtil.errorShortToast(data);
                        }
                    }
                });
        helper.setText(R.id.content,data.getCreated_at().toString());
        helper.addOnClickListener(R.id.returnback);
    }
}
