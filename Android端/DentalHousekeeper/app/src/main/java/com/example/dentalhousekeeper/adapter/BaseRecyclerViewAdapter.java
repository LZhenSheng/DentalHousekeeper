package com.example.dentalhousekeeper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dentalhousekeeper.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.ButterKnife;

/***
 * 通用RecyclerViewAdapter,主要实现了一些通用方法
 * @author 胜利镇
 * @time 2020/8/14 16:12
 */
public abstract class BaseRecyclerViewAdapter<D, VH extends BaseRecyclerViewAdapter.ViewHolder> extends RecyclerView.Adapter<VH> {

    /**
     * 上下文
     */
    protected final Context context;

    /**
     * 布局加载器
     */
    private final LayoutInflater inflater;

    /**
     * 数据列表
     */
    private List<D> datum = new ArrayList<>();

    /**
     * item点击监听器
     */
    private OnItemClickListener onItemClickListener;

    /**
     * 构造方法
     *
     * @param context
     */
    public BaseRecyclerViewAdapter(Context context) {
        this.context = context;

        //创建布局加载器
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        //处理item点击监听器
        if (onItemClickListener != null) {
            //给itemView设置点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                /**
                 * 点击回调事件
                 * @param v
                 */
                @Override
                public void onClick(View v) {
                    //回调监听接口
                    onItemClickListener.onItemClick(holder, position);
                }
            });
        }
    }

    /**
     * 返回数量
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return datum.size();
    }

    /**
     * 获取当前位置数据
     *
     * @param position
     * @return
     */
    public D getData(int position) {
        return datum.get(position);
    }


    /**
     * 设置数据
     *
     * @param datum
     */
    public void setDatum(List<D> datum) {
        //清除原来的数据
        this.datum.clear();

        //添加数据
        this.datum.addAll(datum);

        //通知数据改变了
        notifyDataSetChanged();
    }

    /**
     * 获取数据
     *
     * @return
     */
    public List<D> getDatum() {
        return datum;
    }

    /**
     * 添加数据列表
     *
     * @param datum
     */
    public void addDatum(List<D> datum) {
        //添加数据
        this.datum.addAll(datum);

        //刷新数据
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param data
     */
    public void addData(D data) {
        //添加数据
        this.datum.add(data);

        //刷新数据
        notifyDataSetChanged();
    }

    /**
     * 删除指定位置的数据
     *
     * @param position
     */
    public void removeData(int position) {
        datum.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 清除数据
     */
    public void clearData() {
        datum.clear();
        notifyDataSetChanged();
    }

    /**
     * 设置点击监听器
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 获取布局填充器
     *
     * @return
     */
    public LayoutInflater getInflater() {
        return inflater;
    }

    /**
     * 通用ViewHolder
     * 主要是添加实现一些公共的逻辑
     */
    public abstract static class ViewHolder<D> extends RecyclerView.ViewHolder {

        /**
         * 构造方法
         *
         * @param itemView
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //可以在这里加入ButterKnife这样的框架
            //也可以不加入

            //初始化ButterKnife
            ButterKnife.bind(this, itemView);
        }

        /**
         * 绑定数据
         *
         * @param data
         */
        public void bindData(D data) {

        }
    }
}
