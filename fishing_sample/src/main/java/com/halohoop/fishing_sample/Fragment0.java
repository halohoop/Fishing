package com.halohoop.fishing_sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.halohoop.fishing.Fishing;
import com.halohoop.fishing.executor.FishHandler;
import com.halohoop.fishing.function.Fish;
import com.halohoop.fishing.widget.BaseFrag;

/**
 * Created by Pooholah on 2017/6/9.
 */

public class Fragment0 extends BaseFrag {

    private EditText et;

    public static Fragment0 newInstance() {
        
        Bundle args = new Bundle();
        
        Fragment0 fragment = new Fragment0();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.frag, null);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btn = (Button) view.findViewById(R.id.btn);
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
                Fishing.getDefault().castWpwr(Constant.LAKE_NAME_LUNCH, Constant.FUNC_NAME_ASKFOR_LUNCH,
                        money, new FishHandler<String>() {
                            @Override
                            public void hook(String s) {
                                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                            }
                        }, String.class);
            }
        });
        btn.setText("钓回Activity中的鱼");
        TextView tvShow = (TextView) view.findViewById(R.id.tv_show);
        tvShow.setText("钓回Activity中的鱼");
        et = (EditText) view.findViewById(R.id.et);
    }

    @Override
    public Fish[] fishCreator() {
        return null;//不想做一个lake就反空即可
//        Fish[] fishs = new Fish[1];
//        fishs[0] = new FishWithParamWithReturn<String, Integer>(getTag(), Constant.FUNC_NAME_ASKFOR_DINNER) {
//            @Override
//            public String function(Integer integer) {
//                if (integer > 20) {
//                    return "晚餐做好了！";
//                }
//                return "晚餐钱不够！";
//            }
//        };
//        return fishs;
    }
}
