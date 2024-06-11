package com.github.william853868671.logback.keying;

/**
 * @Author: 王卫东
 * @Email: a853868671@gmail.com
 * @Date: 2024/6/2 下午6:31
 */
public class NoKeyKeyingStrategy implements KeyingStrategy<Object> {

    @Override
    public byte[] createKey(Object e) {
        return null;
    }
}
