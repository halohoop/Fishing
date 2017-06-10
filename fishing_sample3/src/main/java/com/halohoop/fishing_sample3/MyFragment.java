package com.halohoop.fishing_sample3;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.halohoop.fishing.Fishing;
import com.halohoop.fishing.function.Fish;
import com.halohoop.fishing.function.FishNoParamNoReturn;
import com.halohoop.fishing.widget.Pond;

/**
 * Created by Pooholah on 2017/6/10.
 */

public class MyFragment extends Fragment implements Pond {

    private static final String TAG = "MyFragment";


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Fishing.getDefault().bindPond(this);
        Fishing.getDefault().bindPond(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Fishing.getDefault().unbindPond(this);
    }

    public static MyFragment newInstance() {

        Bundle args = new Bundle();

        MyFragment fragment = new MyFragment();
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
                Fishing.getDefault().castNpnr(Constant.LAKE_ACT_TAG1, Constant.FISH_ACT_NAME1);
            }
        });
    }

    @Override
    public Fish[] fishCreator() {
        Fish[] fish = new Fish[1];
        fish[0] = new FishNoParamNoReturn(getTag(), Constant.FISH_FRAG_NAME0) {
            @Override
            public void function() {
                Toast.makeText(getContext(), "Fragment收到了Activity的消息", Toast.LENGTH_SHORT).show();
            }
        };
        return fish;
    }
}
