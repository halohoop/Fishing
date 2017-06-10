package com.halohoop.fishing_sample3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.halohoop.fishing.Fishing;
import com.halohoop.fishing.function.Fish;
import com.halohoop.fishing.function.FishNoParamNoReturn;
import com.halohoop.fishing.widget.Pond;

public class MainActivity extends AppCompatActivity implements Pond {

    private Button tv_show;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_show = (Button) findViewById(R.id.tv_show);
        tv_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fishing.getDefault().castNpnr(Constant.LAKE_FRAG_TAG0, Constant.FISH_FRAG_NAME0);
//                Fishing.getDefault().castNpnrInThread(Constant1.LAKE_FRAG_TAG0, Constant1.FISH_FRAG_NAME0);
            }
        });
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, MyFragment.newInstance(), Constant.LAKE_FRAG_TAG0)
                .commit();
    }

    @Override
    public Fish[] fishCreator() {
        Fish[] fish = new Fish[1];
        fish[0] = new FishNoParamNoReturn(getTag(), Constant.FISH_ACT_NAME1) {
            @Override
            public void function() {
                Toast.makeText(MainActivity.this, "Activity收到了Fragment的消息", Toast.LENGTH_SHORT).show();
            }
        };
        return fish;
    }

    @Override
    public String getTag() {
        return Constant.LAKE_ACT_TAG1;
    }
}
