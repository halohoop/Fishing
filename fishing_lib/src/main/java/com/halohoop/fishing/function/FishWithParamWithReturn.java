package com.halohoop.fishing.function;

/**
 * Created by Pooholah on 2017/6/6.
 * 有参数有返回值的方法的抽象
 */

public abstract class FishWithParamWithReturn<R, P> extends Fish {
    public FishWithParamWithReturn(String lakeTag, String name) {
        super(lakeTag, name);
    }

    public abstract R function(P p);
}
