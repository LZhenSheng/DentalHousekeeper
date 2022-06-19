package com.example.dentalhousekeeper.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.dentalhousekeeper.R;

/**
 * 选择性别对话框
 */
public class GenderDialogFragment extends DialogFragment {
    /**
     * 选择索引
     */
    private int selectedIndex;

    /**
     * 选择了监听器
     */
    private DialogInterface.OnClickListener onClickListener;

    /**
     * 创建对话框
     *
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                //设置标题
                .setTitle("请选择性别")

                //设置单选按钮
                .setSingleChoiceItems(R.array.dialog_gender, selectedIndex, onClickListener)
                .create();
    }

    /**
     * 显示
     *
     * @param fragmentManager
     * @param selectedIndex
     * @param onClickListener
     */
    public static void show(FragmentManager fragmentManager,
                            int selectedIndex,
                            DialogInterface.OnClickListener onClickListener) {
        //创建fragment
        GenderDialogFragment fragment = newInstance();

        //选择索引
        fragment.selectedIndex = selectedIndex;

        //回调监听器
        fragment.onClickListener = onClickListener;

        //显示
        fragment.show(fragmentManager, "GenderDialogFragment");
    }

    /**
     * 创建方法
     *
     * @return
     */
    public static GenderDialogFragment newInstance() {

        Bundle args = new Bundle();

        GenderDialogFragment fragment = new GenderDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
