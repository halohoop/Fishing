package com.halohoop.fishing.function;

/**
 * Created by Pooholah on 2017/6/6.
 * 没有参数没有返回值的方法的抽象
 */

public abstract class FishNoParamNoReturn extends Fish {

    public FishNoParamNoReturn(String lakeTag, String name) {
        super(lakeTag, name);
    }


    public abstract void function();
}
