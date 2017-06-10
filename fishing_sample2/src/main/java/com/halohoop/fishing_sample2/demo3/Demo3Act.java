package com.halohoop.fishing_sample2.demo3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.halohoop.fishing.Fishing;
import com.halohoop.fishing.executor.FishHandler;
import com.halohoop.fishing.function.Fish;
import com.halohoop.fishing.function.FishWithParamWithReturn;
import com.halohoop.fishing.widget.BaseAppCompatAct;
import com.halohoop.fishing_sample2.R;

/**
 * Created by Pooholah on 2017/6/10.
 */

public class Demo3Act extends BaseAppCompatAct {
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo3);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, WpwrFragment.newInstance(), Constant3.LAKE_FRAG_TAG0)
                .commit();
        Button btn = (Button) findViewById(R.id.btn);
        btn.setText("从Fragment获取晚餐");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int money = 0;
                try {
                    String moneyStr = et.getText().toString();
                    money = Integer.valueOf(moneyStr);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                Fishing.getDefault().castWpwr(Constant3.LAKE_FRAG_TAG0, Constant3.FISH_FRAG_NAME0,
                        money, new FishHandler<String>() {
                            @Override
                            public void hook(String s) {
                                Toast.makeText(Demo3Act.this, s, Toast.LENGTH_SHORT).show();
                            }
                        }, String.class);
            }
        });
        et = (EditText) findViewById(R.id.et);
    }

    @Override
    public Fish[] fishCreator() {
        Fish[] fishes = new Fish[1];
        fishes[0] = new FishWithParamWithReturn<String, Integer>(getTag(), Constant3.FISH_ACT_NAME1) {
            @Override
            public String function(Integer integer) {
                if (integer > 20) {
                    return "午餐做好了！";
                }
                return "午餐钱不够！";
            }
        };
        return fishes;
    }

    @Override
    public String getTag() {
        return Constant3.LAKE_ACT_TAG1;
    }
}
