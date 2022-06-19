package com.example.dentalhousekeeper.listener;

import com.example.dentalhousekeeper.domin.User;

/***
* 用户接口
* @author 胜利镇
* @time 2020/8/22 16:43
*/
public interface UserListener {
    /**
     * 用户获取成功
     *
     * @param data
     */
    void onUser(User data);
}
