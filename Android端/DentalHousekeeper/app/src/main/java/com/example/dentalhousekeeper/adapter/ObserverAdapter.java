package com.example.dentalhousekeeper.adapter;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 通用实现Observer里面的方法
 * <p>
 * 目的是避免要实现所有方法
 */
public class ObserverAdapter<T> implements Observer<T> {

    /**
     * 开始定义了执行
     * 可以简单理解为开始执行前
     *
     * @param d
     */
    @Override
    public void onSubscribe(Disposable d) {

    }

    /**
     * 当前Observer执行完成了
     *
     * @param t
     */
    @Override
    public void onNext(T t) {

    }

    /**
     * 执行失败了
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {

    }

    /**
     * 调用了onNext方法后调用
     */
    @Override
    public void onComplete() {

    }
}
