package com.halohoop.fishing;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.halohoop.fishing.executor.FishHandler;
import com.halohoop.fishing.executor.FishMovement;
import com.halohoop.fishing.function.Fish;
import com.halohoop.fishing.widget.FishPond;
import com.halohoop.fishing.widget.Lake;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Pooholah on 2017/6/10.
 */

public class Fishing {
    /**
     * 单例模式
     */
    private Fishing() {
    }

    private static class SingletonHolder {
        private final static Fishing instance = new Fishing();
    }

    public static Fishing getDefault() {
        return SingletonHolder.instance;
    }

    private ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * key：lake tag
     * value：FishPond bean
     */
    private Map<String, FishPond> mFishPondMap = null;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private void executeInThread(Runnable runnable) {
        if (runnable != null) {
            executorService.execute(runnable);
        }
    }

    public void put(String lakeTag, String name, Fish fish) {
        if (mFishPondMap != null) {
            FishPond fishPond = mFishPondMap.get(lakeTag);
            if (fishPond != null) {
                fishPond.put(name, fish);
            }
        }
    }

    public FishPond put(String lakeTag, FishPond fishPond) {
        if (mFishPondMap == null) {
            mFishPondMap = new ArrayMap<>();
        }
        return mFishPondMap.put(lakeTag, fishPond);
    }

    public void remove(String lakeTag) {
        if (mFishPondMap != null) {
            FishPond remove = mFishPondMap.remove(lakeTag);
            if (remove!=null) {
                if (remove.getMapNoParamNoReturn() != null) {
                    remove.getMapNoParamNoReturn().clear();
                }
                if (remove.getMapWithParamNoReturn() != null) {
                    remove.getMapWithParamNoReturn().clear();
                }
                if (remove.getMapNoParamWithReturn() != null) {
                    remove.getMapNoParamWithReturn().clear();
                }
                if (remove.getMapWithParamWithReturn() != null) {
                    remove.getMapWithParamWithReturn().clear();
                }
            }
        }
    }

    public void bindLake(Object lak) {
        if (lak instanceof Lake) {
            Lake lake = (Lake) lak;
            if (TextUtils.isEmpty(lake.getTag())) {
                throw new RuntimeException("Please offer a unique tag to your lake!");
            }
            String tag = lake.getTag();
            Fish[] fish = lake.fishCreator();
            if (fish != null && fish.length > 0) {
                Fishing.getDefault().put(tag, new FishPond());
                for (int i = 0; i < fish.length; i++) {
                    Fishing.getDefault().put(tag, fish[i].getName(), fish[i]);
                }
            }
        } else {
            throw new RuntimeException(lak.getClass().getSimpleName() + " must implement Lake!");
        }
    }

    //-----------------No return-------------------------
    private <P> void castNoReturn(String lakeTag, String name, P p, boolean isUseThread, int type) {
        FishPond fishPond = mFishPondMap.get(lakeTag);
        if (fishPond == null) {
            return;
        }
        FishMovement fishMovement = fishPond.getFishMovement(type);
        if (!isUseThread) {
            fishMovement.execute(name, p, null);
        } else {
            final String tmpName = name;
            final P tmpP = p;
            final FishMovement tmpFishMovement = fishMovement;
            executeInThread(new Runnable() {
                @Override
                public void run() {
                    tmpFishMovement.execute(tmpName, tmpP, null);
                }
            });
        }
    }

    //------------------------------------------
    //-----------------No param no return-------------------------
    public void castNpnr(String lakeTag, String name) {
        int type = FishPond.TYPE_NO_PARAM_NO_RTN;
        castNoReturn(lakeTag, name, null, false, type);
    }

    public void castNpnrInThread(String lakeTag, String name) {
        int type = FishPond.TYPE_NO_PARAM_NO_RTN;
        castNoReturn(lakeTag, name, null, true, type);
    }

    //------------------------------------------
    //-----------------With param no return-------------------------
    public <P> void castWpnr(String lakeTag, String name, P p) {
        int type = FishPond.TYPE_WITH_PARAM_NO_RTN;
        castNoReturn(lakeTag, name, p, false, type);
    }

    public <P> void castWpnrInThread(String lakeTag, String name, P p) {
        int type = FishPond.TYPE_WITH_PARAM_NO_RTN;
        castNoReturn(lakeTag, name, p, true, type);
    }
    //------------------------------------------

    //==============================================
    //==============================================

    //-----------------With return-------------------------
    private <R, P> void castWithReturn(String lakeTag, String name, P p,
                                       boolean isUseThread,
                                       boolean isCallbackAtUIThread,
                                       FishHandler<R> callback, Class<R> clz,
                                       int type) {
        FishPond fishPond = mFishPondMap.get(lakeTag);
        if (fishPond == null) {
            return;
        }
        FishMovement fishMovement = fishPond.getFishMovement(type);
        if (!isUseThread) {
            if (callback != null) {
                callback.hook(clz.cast(fishMovement.execute(name, p, clz)));
            }
        } else {
            final String tmpFuncName = name;
            final P tmpP = p;
            final FishMovement tmpFishMovement = fishMovement;
            final boolean tmpIsCallbackAtUIThread = isCallbackAtUIThread;
            final FishHandler<R> tmpCallback = callback;
            final Class<R> tmpClz = clz;
            executeInThread(new Runnable() {
                @Override
                public void run() {
                    tmpFishMovement.execute(tmpFuncName, tmpP, tmpClz);
                    if (tmpCallback != null) {
                        if (tmpIsCallbackAtUIThread) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    tmpCallback.hook(tmpClz.cast(tmpFishMovement.execute(tmpFuncName, tmpP, tmpClz)));
                                }
                            });
                        } else {
                            tmpCallback.hook(tmpClz.cast(tmpFishMovement.execute(tmpFuncName, tmpP, tmpClz)));
                        }
                    }
                }
            });
        }
    }

    //------------------------------------------
    //-----------------No param With return-------------------------
    public <P, R> void castNpwr(String lakeTag, String name,
                                FishHandler<R> callback, Class<R> clz) {
        int type = FishPond.TYPE_NO_PARAM_WITH_RTN;
        castWithReturn(lakeTag, name, null, false,
                true, callback,
                clz, type);
    }

    public <P, R> void castNpwrInThread(String fragTag, String funcName,
                                        boolean isCallbackAtUIThread,
                                        FishHandler<R> callback, Class<R> clz) {
        int type = FishPond.TYPE_NO_PARAM_WITH_RTN;
        castWithReturn(fragTag, funcName, null, true,
                isCallbackAtUIThread, callback,
                clz, type);
    }

    //------------------------------------------
    //-----------------With param With return-------------------------
    public <P, R> void castWpwr(String lakeTag, String name, P p,
                                FishHandler<R> callback, Class<R> clz) {
        int type = FishPond.TYPE_WITH_PARAM_WITH_RTN;
        castWithReturn(lakeTag, name, p, false, true, callback, clz, type);
    }

    public <P, R> void castWpwrInThread(String lakeTag, String name, P p,
                                        boolean isCallbackAtUIThread,
                                        FishHandler<R> callback, Class<R> clz) {
        int type = FishPond.TYPE_WITH_PARAM_WITH_RTN;
        castWithReturn(lakeTag, name, p, true, isCallbackAtUIThread, callback, clz, type);
    }
    //------------------------------------------

    //==========================================
}
