package com.halohoop.fishing.widget;

import android.support.v4.util.ArrayMap;

import com.halohoop.fishing.executor.FishMovement;
import com.halohoop.fishing.executor.FishMovementNpnrImpl;
import com.halohoop.fishing.executor.FishMovementNpwrImpl;
import com.halohoop.fishing.executor.FishMovementWpnrImpl;
import com.halohoop.fishing.executor.FishMovementWpwrImpl;
import com.halohoop.fishing.function.FishNoParamNoReturn;
import com.halohoop.fishing.function.FishNoParamWithReturn;
import com.halohoop.fishing.function.FishWithParamNoReturn;
import com.halohoop.fishing.function.FishWithParamWithReturn;

import java.util.Map;

/**
 * Created by Pooholah on 2017/6/8.
 * 鱼塘，各种各样等待上钩的鱼
 */

final public class FishPond {

    public static final int TYPE_NO_PARAM_NO_RTN = 0;
    public static final int TYPE_WITH_PARAM_NO_RTN = 1;
    public static final int TYPE_WITH_PARAM_WITH_RTN = 2;
    public static final int TYPE_NO_PARAM_WITH_RTN = 3;
    /**
     * 四个容器，存储四种类型的方法
     * 全部使用ArrayMap代替HashMap，节省内存开支
     */
    private Map<String, FishWithParamNoReturn> mapWithParamNoReturn = null;
    private Map<String, FishNoParamWithReturn> mapNoParamWithReturn = null;
    private Map<String, FishWithParamWithReturn> mapWithParamWithReturn = null;
    private Map<String, FishNoParamNoReturn> mapNoParamNoReturn = null;

    private FishMovement fishsMovementWpnr = null;
    private FishMovement fishsMovementNpwr = null;
    private FishMovement fishsMovementWpwr = null;
    private FishMovement fishsMovementNpnr = null;

    public <P> void put(String name, P p) {
        if (p instanceof FishWithParamNoReturn) {
            if (null == mapWithParamNoReturn) {
                mapWithParamNoReturn = new ArrayMap<>();
                fishsMovementWpnr = new FishMovementWpnrImpl(mapWithParamNoReturn);
            }
            mapWithParamNoReturn.put(name, FishWithParamNoReturn.class.cast(p));
            return;
        }
        if (p instanceof FishNoParamWithReturn) {
            if (null == mapNoParamWithReturn) {
                mapNoParamWithReturn = new ArrayMap<>();
                fishsMovementNpwr = new FishMovementNpwrImpl(mapNoParamWithReturn);
            }
            mapNoParamWithReturn.put(name, FishNoParamWithReturn.class.cast(p));
            return;
        }
        if (p instanceof FishWithParamWithReturn) {
            if (null == mapWithParamWithReturn) {
                mapWithParamWithReturn = new ArrayMap<>();
                fishsMovementWpwr = new FishMovementWpwrImpl(mapWithParamWithReturn);
            }
            mapWithParamWithReturn.put(name, FishWithParamWithReturn.class.cast(p));
            return;
        }
        if (p instanceof FishNoParamNoReturn) {
            if (null == mapNoParamNoReturn) {
                mapNoParamNoReturn = new ArrayMap<>();
                fishsMovementNpnr = new FishMovementNpnrImpl(mapNoParamNoReturn);
            }
            mapNoParamNoReturn.put(name, FishNoParamNoReturn.class.cast(p));
            return;
        }
    }

    public Map<String, FishWithParamNoReturn> getMapWithParamNoReturn() {
        return mapWithParamNoReturn;
    }

    public Map<String, FishNoParamWithReturn> getMapNoParamWithReturn() {
        return mapNoParamWithReturn;
    }

    public Map<String, FishWithParamWithReturn> getMapWithParamWithReturn() {
        return mapWithParamWithReturn;
    }

    public Map<String, FishNoParamNoReturn> getMapNoParamNoReturn() {
        return mapNoParamNoReturn;
    }

    //executors
    private FishMovement getFishMovementWpnr() {
        return fishsMovementWpnr;
    }

    private FishMovement getFishMovementNpwr() {
        return fishsMovementNpwr;
    }

    private FishMovement getFishMovementWpwr() {
        return fishsMovementWpwr;
    }

    private FishMovement getFishMovementNpnr() {
        return fishsMovementNpnr;
    }

    public FishMovement getFishMovement(int funcType) {
        switch (funcType) {
            case TYPE_WITH_PARAM_NO_RTN:
                return getFishMovementWpnr();
            case TYPE_WITH_PARAM_WITH_RTN:
                return getFishMovementWpwr();
            case TYPE_NO_PARAM_WITH_RTN:
                return getFishMovementNpwr();
            case TYPE_NO_PARAM_NO_RTN:
                return getFishMovementNpnr();
            default:
                return null;
        }
    }
}
