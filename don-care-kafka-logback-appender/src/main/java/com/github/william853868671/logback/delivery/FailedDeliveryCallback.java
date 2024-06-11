package com.github.william853868671.logback.delivery;

/**
 * @Author: william853868671
 * @Email: a853868671@gmail.com
 * @Date: 2024/6/2 下午6:30
 */
public interface FailedDeliveryCallback<E> {
    /**
     * 投递失败的回调方法
     * @param evt
     * @param throwable
     */
    void onFailedDelivery(E evt, Throwable throwable);
}
