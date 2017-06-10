package com.halohoop.fishing.executor;

import com.halohoop.fishing.function.FishWithParamNoReturn;

import java.util.Map;

/**
 * Created by Pooholah on 2017/6/9.
 */

public class FishMovementWpnrImpl implements FishMovement<String, FishWithParamNoReturn> {

    private Map<String, FishWithParamNoReturn> mContainer = null;

    public FishMovementWpnrImpl(Map<String, FishWithParamNoReturn> container) {
        mContainer = container;
    }

    @Override
    public <P, R> R execute(String funcName, P p, Class<R> clz) {
        FishWithParamNoReturn func = mContainer.get(funcName);
        if (func != null) {
            func.function(p);
        }
        return null;
    }

}
