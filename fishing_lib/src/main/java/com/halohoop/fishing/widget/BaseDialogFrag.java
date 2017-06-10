package com.halohoop.fishing.widget;

import android.content.Context;
import android.support.v4.app.DialogFragment;

import com.halohoop.fishing.Fishing;

/**
 * Created by Pooholah on 2017/6/8.
 */

public abstract class BaseDialogFrag extends DialogFragment implements Pond {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //构建  activity、其他fragment→这个fragment  的沟通，
        // activity、其他fragment主动聊，这个fragment 被动做响应
        Fishing.getDefault().bindPond(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Fishing.getDefault().unbindPond(this);
    }
}
