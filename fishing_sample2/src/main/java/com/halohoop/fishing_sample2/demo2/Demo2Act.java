package com.halohoop.fishing_sample2.demo2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.halohoop.fishing.Fishing;
import com.halohoop.fishing.executor.FishHandler;
import com.halohoop.fishing.function.Fish;
import com.halohoop.fishing.function.FishNoParamWithReturn;
import com.halohoop.fishing.widget.BaseAppCompatAct;
import com.halohoop.fishing_sample2.R;

/**
 * Created by Pooholah on 2017/6/10.
 */

public class Demo2Act extends BaseAppCompatAct {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        findViewById(R.id.tv_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fishing.getDefault().castNpwr(Constant2.LAKE_FRAG_TAG0, Constant2.FISH_FRAG_NAME0,
                        new FishHandler<String>() {
                            @Override
                            public void hook(String s) {
                                Toast.makeText(Demo2Act.this, s, Toast.LENGTH_SHORT).show();
                            }
                        }, String.class);
            }
        });
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, NpwrFragment.newInstance(), Constant2.LAKE_FRAG_TAG0)
                .commit();
    }

    @Override
    public Fish[] fishCreator() {
        Fish[] fish = new Fish[1];
        fish[0] = new FishNoParamWithReturn<String>(getTag(), Constant2.FISH_ACT_NAME1) {
            @Override
            public String function() {
                return "返回自Activity";
            }
        };
        return fish;
    }

    @Override
    public String getTag() {
        return Constant2.LAKE_ACT_TAG1;
    }
}
