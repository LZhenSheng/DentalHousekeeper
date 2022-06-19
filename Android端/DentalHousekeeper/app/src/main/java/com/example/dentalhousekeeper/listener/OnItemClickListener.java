package com.example.dentalhousekeeper.listener;


import com.example.dentalhousekeeper.adapter.BaseRecyclerViewAdapter;

/***
* Adapter的item点击事件监听器
* @author 胜利镇
* @time 2020/8/14 16:13
*/
public interface OnItemClickListener {
    /**
     * item点击事件
     *
     * @param holder   点击的ViewHolder
     * @param position 点击的位置
     */
    void onItemClick(BaseRecyclerViewAdapter.ViewHolder holder, int position);
}
