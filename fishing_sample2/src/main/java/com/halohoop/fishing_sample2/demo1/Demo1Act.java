package com.halohoop.fishing_sample2.demo1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.halohoop.fishing.Fishing;
import com.halohoop.fishing.function.Fish;
import com.halohoop.fishing.function.FishWithParamNoReturn;
import com.halohoop.fishing.widget.BaseAppCompatAct;
import com.halohoop.fishing_sample2.R;

/**
 * Created by Pooholah on 2017/6/10.
 */

public class Demo1Act extends BaseAppCompatAct {

    private Button tv_show;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        tv_show = (Button) findViewById(R.id.tv_show);
        tv_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fishing.getDefault().castWpnr(Constant1.LAKE_FRAG_TAG0, Constant1.FISH_FRAG_NAME0,
                        "来自Activity");
            }
        });
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, WpnrFragment.newInstance(), Constant1.LAKE_FRAG_TAG0)
                .commit();
    }


    @Override
    public Fish[] fishCreator() {
        Fish[] fish = new Fish[1];
        fish[0] = new FishWithParamNoReturn<String>(getTag(), Constant1.FISH_ACT_NAME1) {
            @Override
            public void function(String s) {
                Toast.makeText(Demo1Act.this, "Activity收到了Fragment的消息:"+s, Toast.LENGTH_SHORT).show();
            }
        };
        return fish;
    }

    @Override
    public String getTag() {
        return Constant1.LAKE_ACT_TAG1;
    }
}
