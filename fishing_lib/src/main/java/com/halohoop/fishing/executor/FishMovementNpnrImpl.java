package com.halohoop.fishing.executor;

import com.halohoop.fishing.function.FishNoParamNoReturn;

import java.util.Map;

/**
 * Created by Pooholah on 2017/6/9.
 */

public class FishMovementNpnrImpl implements FishMovement<String, FishNoParamNoReturn> {

    private Map<String, FishNoParamNoReturn> mContainer = null;

    public FishMovementNpnrImpl(Map<String, FishNoParamNoReturn> mContainer) {
        this.mContainer = mContainer;
    }

    @Override
    public <P, R> R execute(String funcName, P p, Class<R> clz) {
        FishNoParamNoReturn func = mContainer.get(funcName);
        if (func != null) {
            func.function();
        }
        return null;
    }
}
