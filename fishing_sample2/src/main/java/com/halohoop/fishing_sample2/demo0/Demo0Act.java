package com.halohoop.fishing_sample2.demo0;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.halohoop.fishing.Fishing;
import com.halohoop.fishing.function.Fish;
import com.halohoop.fishing.function.FishNoParamNoReturn;
import com.halohoop.fishing.widget.BaseAppCompatAct;
import com.halohoop.fishing_sample2.R;

/**
 * Created by Pooholah on 2017/6/10.
 */

public class Demo0Act extends BaseAppCompatAct {

    private Button tv_show;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        tv_show = (Button) findViewById(R.id.tv_show);
        tv_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fishing.getDefault().castNpnr(Constant.LAKE_FRAG_TAG0, Constant.FISH_FRAG_NAME0);
//                Fishing.getDefault().castNpnrInThread(Constant1.LAKE_FRAG_TAG0, Constant1.FISH_FRAG_NAME0);
            }
        });
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, NpnrFragment.newInstance(), Constant.LAKE_FRAG_TAG0)
                .commit();
    }

    @Override
    public Fish[] fishCreator() {
        Fish[] fish = new Fish[1];
        fish[0] = new FishNoParamNoReturn(getTag(), Constant.FISH_ACT_NAME1) {
            @Override
            public void function() {
                Toast.makeText(Demo0Act.this, "Activity收到了Fragment的消息", Toast.LENGTH_SHORT).show();
            }
        };
        return fish;
    }

    @Override
    public String getTag() {
        return Constant.LAKE_ACT_TAG1;
    }
}
