package com.halohoop.fishing.function;


import com.halohoop.fishing.Fishing;

/**
 * Created by Pooholah on 2017/6/6.
 * 方法必定有一个唯一的名字，或者说使用者可以使用手动管理的一个id来区分它们
 */
public abstract class Fish {
    private String name;

    public String getName() {
        return name;
    }

    public Fish(String lakeTag, String name) {
        this.name = name;
        Fishing.getDefault().putFish(lakeTag, name, this);
    }
}
