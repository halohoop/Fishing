package com.halohoop.fishing.executor;

import com.halohoop.fishing.function.FishWithParamWithReturn;

import java.util.Map;

/**
 * Created by Pooholah on 2017/6/9.
 */

public class FishMovementWpwrImpl implements FishMovement<String, FishWithParamWithReturn> {

    private Map<String, FishWithParamWithReturn> mContainer = null;

    public FishMovementWpwrImpl(Map<String, FishWithParamWithReturn> container) {
        mContainer = container;
    }

    @Override
    public <P, R> R execute(String funcName, P p, Class<R> clz) {
        FishWithParamWithReturn func = mContainer.get(funcName);
        if (func != null) {
            return clz.cast(func.function(p));
        }
        return null;
    }
}
