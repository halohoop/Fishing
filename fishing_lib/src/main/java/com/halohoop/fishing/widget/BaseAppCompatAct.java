package com.halohoop.fishing.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.halohoop.fishing.Fishing;

/**
 * Created by Pooholah on 2017/6/6.
 */

public abstract class BaseAppCompatAct extends AppCompatActivity implements Pond {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //构建  这个fragment→activity  的沟通，
        // 这个fragment主动聊，activity 被动做响应
        Fishing.getDefault().bindPond(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Fishing.getDefault().unbindPond(this);
    }
}
