package com.halohoop.fishing_sample2.demo2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.halohoop.fishing.Fishing;
import com.halohoop.fishing.executor.FishHandler;
import com.halohoop.fishing.function.Fish;
import com.halohoop.fishing.function.FishNoParamWithReturn;
import com.halohoop.fishing.widget.BaseFrag;
import com.halohoop.fishing_sample2.R;

/**
 * Created by Pooholah on 2017/6/6.
 */

public class NpwrFragment extends BaseFrag {

    private static final String TAG = "NpwrFragment";

    public static NpwrFragment newInstance() {

        Bundle args = new Bundle();

        NpwrFragment fragment = new NpwrFragment();
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
                Fishing.getDefault().castNpwr(Constant2.LAKE_ACT_TAG1, Constant2.FISH_ACT_NAME1,
                        new FishHandler<String>() {
                            @Override
                            public void hook(String s) {
                                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                            }
                        }, String.class);
            }
        });
    }

    @Override
    public Fish[] fishCreator() {
        Fish[] fish = new Fish[1];
        fish[0] = new FishNoParamWithReturn<String>(getTag(), Constant2.FISH_FRAG_NAME0) {
            @Override
            public String function() {
                return "返回自Fragment";
            }
        };
        return fish;
    }
}
