package com.halohoop.fishing_sample;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.halohoop.fishing.Fishing;
import com.halohoop.fishing.executor.FishHandler;
import com.halohoop.fishing.function.Fish;
import com.halohoop.fishing.function.FishWithParamWithReturn;
import com.halohoop.fishing.widget.BaseAppCompatAct;


public class MainActivity extends BaseAppCompatAct {

    private TextView tvShow;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, Fragment0.newInstance(), Constant.LAKE_NAME_DINNER)
                .commit();
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int money = 0;
                try {
                    String moneyStr = et.getText().toString();
                    money = Integer.valueOf(moneyStr);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                Fishing.getDefault().castWpwr(Constant.LAKE_NAME_DINNER, Constant.FUNC_NAME_ASKFOR_DINNER,
                        money, new FishHandler<String>() {
                            @Override
                            public void hook(String s) {
                                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                            }
                        }, String.class);
            }
        });
        tvShow = (TextView) findViewById(R.id.tv_show);
        tvShow.setText("钓Fragment中的鱼");
        et = (EditText) findViewById(R.id.et);
    }

    @Override
    public Fish[] fishCreator() {
        Fish[] fishes = new Fish[1];
        fishes[0] = new FishWithParamWithReturn<String, Integer>(getTag(), Constant.FUNC_NAME_ASKFOR_LUNCH) {
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
        return Constant.LAKE_NAME_LUNCH;
    }
}
