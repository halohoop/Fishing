package com.halohoop.fishing.executor;

import com.halohoop.fishing.function.FishNoParamWithReturn;

import java.util.Map;

/**
 * Created by Pooholah on 2017/6/9.
 */

public class FishMovementNpwrImpl implements FishMovement<String, FishNoParamWithReturn> {

    private Map<String, FishNoParamWithReturn> mContainer = null;

    public FishMovementNpwrImpl(Map<String, FishNoParamWithReturn> container) {
        mContainer = container;
    }

    @Override
    public <P, R> R execute(String funcName, P p, Class<R> clz) {
        FishNoParamWithReturn func = mContainer.get(funcName);
        if (func != null) {
            return clz.cast(func.function());
        }
        return null;
    }
}
