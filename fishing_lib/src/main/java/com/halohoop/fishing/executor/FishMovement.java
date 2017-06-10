package com.halohoop.fishing.executor;

/**
 * Created by Pooholah on 2017/6/9.
 */

public interface FishMovement<K, V> {

    <P, R> R execute(String funcName, P p, Class<R> clz);
}
