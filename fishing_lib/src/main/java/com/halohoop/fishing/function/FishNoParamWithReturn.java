package com.halohoop.fishing.function;

/**
 * Created by Pooholah on 2017/6/6.
 * 没有参数有返回值
 */

public abstract class FishNoParamWithReturn<R> extends Fish {

    public FishNoParamWithReturn(String lakeTag, String name) {
        super(lakeTag, name);
    }

    public abstract R function();
}
