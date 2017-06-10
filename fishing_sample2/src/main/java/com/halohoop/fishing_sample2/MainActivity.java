package com.halohoop.fishing_sample2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.halohoop.fishing.function.Fish;
import com.halohoop.fishing.widget.BaseAppCompatAct;
import com.halohoop.fishing_sample2.demo0.Demo0Act;
import com.halohoop.fishing_sample2.demo1.Demo1Act;
import com.halohoop.fishing_sample2.demo2.Demo2Act;
import com.halohoop.fishing_sample2.demo3.Demo3Act;

/**
 * 一个activity有多个fragments的情况
 */
public class MainActivity extends BaseAppCompatAct {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings0:
                startAct(Demo0Act.class);
                break;
            case R.id.action_settings1:
                startAct(Demo1Act.class);
                break;
            case R.id.action_settings2:
                startAct(Demo2Act.class);
                break;
            case R.id.action_settings3:
                startAct(Demo3Act.class);
                break;
        }
        return true;
    }

    private void startAct(Class clz) {
        Intent intent = new Intent(this, clz);
        startActivity(intent);
    }

    @Override
    public Fish[] fishCreator() {
        return new Fish[0];
    }

    @Override
    public String getTag() {
        return null;
    }
}
