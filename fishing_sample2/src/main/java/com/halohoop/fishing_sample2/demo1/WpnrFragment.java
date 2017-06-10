package com.halohoop.fishing_sample2.demo1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.halohoop.fishing.Fishing;
import com.halohoop.fishing.function.Fish;
import com.halohoop.fishing.function.FishWithParamNoReturn;
import com.halohoop.fishing.widget.BaseFrag;
import com.halohoop.fishing_sample2.R;

/**
 * Created by Pooholah on 2017/6/6.
 */

public class WpnrFragment extends BaseFrag {

    private static final String TAG = "WpnrFragment";

    public static WpnrFragment newInstance() {

        Bundle args = new Bundle();

        WpnrFragment fragment = new WpnrFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.demo_fragment, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fishing.getDefault().castWpnr(Constant1.LAKE_ACT_TAG1, Constant1.FISH_ACT_NAME1,
                        "来自Fragment");
            }
        });
    }

    @Override
    public Fish[] fishCreator() {
        Fish[] fish = new Fish[1];
        fish[0] = new FishWithParamNoReturn<String>(getTag(), Constant1.FISH_FRAG_NAME0) {
            @Override
            public void function(String s) {
                Toast.makeText(getContext(), "Fragment收到了Activity的消息:"+s, Toast.LENGTH_SHORT).show();
            }
        };
        return fish;
    }
}
