package com.github.william853868671.logback.keying;

/**
 * @Author: 王卫东
 * @Email: a853868671@gmail.com
 * @Date: 2024/6/2 下午6:34
 */
public interface KeyingStrategy<E> {

    /**
     * creates a byte array key for the given {@link ch.qos.logback.classic.spi.ILoggingEvent}
     * @param e the logging event
     * @return a key
     */
    byte[] createKey(E e);

}
