package com.halohoop.fishing;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.halohoop.fishing.executor.FishHandler;
import com.halohoop.fishing.executor.FishMovement;
import com.halohoop.fishing.function.Fish;
import com.halohoop.fishing.widget.FishPond;
import com.halohoop.fishing.widget.Pond;

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

    public void putFish(String pondTag, String name, Fish fish) {
        if (mFishPondMap != null) {
            FishPond fishPond = mFishPondMap.get(pondTag);
            if (fishPond != null) {
                fishPond.put(name, fish);
            }
        }
    }

    public FishPond getFishPond(String pondTag){
        if (mFishPondMap != null) {
            FishPond fishPond = mFishPondMap.get(pondTag);
            return fishPond;
        }
        return null;
    }

    public FishPond putFishPond(String pondTag, FishPond fishPond) {
        if (mFishPondMap == null) {
            mFishPondMap = new ArrayMap<>();
        }
        return mFishPondMap.put(pondTag, fishPond);
    }

    public void unbindPond(Object po) {
        if (po instanceof Pond) {
            Pond pond = (Pond) po;
            if (mFishPondMap != null) {
                FishPond remove = mFishPondMap.remove(pond.getTag());
                if (remove != null) {
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
    }


    public void bindPond(Object po) {
        if (po instanceof Pond) {
            Pond pond = (Pond) po;
            if (TextUtils.isEmpty(pond.getTag())) {
                throw new RuntimeException("Please offer a unique tag to your pond!");
            }
            FishPond fishPond = getFishPond(pond.getTag());
            if (fishPond != null) {
                return;
            }
            String tag = pond.getTag();
            Fish[] fish = pond.fishCreator();
            if (fish != null && fish.length > 0) {
                putFishPond(tag, new FishPond());
                for (int i = 0; i < fish.length; i++) {
                    putFish(tag, fish[i].getName(), fish[i]);
                }
            }
        } else {
            throw new RuntimeException(po.getClass().getSimpleName() + " must implement Pond!");
        }
    }

    //-----------------No return-------------------------
    private <P> void castNoReturn(String pondTag, String name, P p, boolean isUseThread, int type) {
        if (mFishPondMap == null) {
            return;
        }
        FishPond fishPond = mFishPondMap.get(pondTag);
        if (fishPond == null) {
            return;
        }
        FishMovement fishMovement = fishPond.getFishMovement(type);
        if (fishMovement == null) {
            return;
        }
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
    public void castNpnr(String pondTag, String name) {
        int type = FishPond.TYPE_NO_PARAM_NO_RTN;
        castNoReturn(pondTag, name, null, false, type);
    }

    public void castNpnrInThread(String pondTag, String name) {
        int type = FishPond.TYPE_NO_PARAM_NO_RTN;
        castNoReturn(pondTag, name, null, true, type);
    }

    //------------------------------------------
    //-----------------With param no return-------------------------
    public <P> void castWpnr(String pondTag, String name, P p) {
        int type = FishPond.TYPE_WITH_PARAM_NO_RTN;
        castNoReturn(pondTag, name, p, false, type);
    }

    public <P> void castWpnrInThread(String pondTag, String name, P p) {
        int type = FishPond.TYPE_WITH_PARAM_NO_RTN;
        castNoReturn(pondTag, name, p, true, type);
    }
    //------------------------------------------

    //==============================================
    //==============================================

    //-----------------With return-------------------------
    private <R, P> void castWithReturn(String pondTag, String name, P p,
                                       boolean isUseThread,
                                       boolean isCallbackAtUIThread,
                                       FishHandler<R> callback, Class<R> clz,
                                       int type) {
        if (mFishPondMap==null) {
            return;
        }
        FishPond fishPond = mFishPondMap.get(pondTag);
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
    public <P, R> void castNpwr(String pondTag, String name,
                                FishHandler<R> callback, Class<R> clz) {
        int type = FishPond.TYPE_NO_PARAM_WITH_RTN;
        castWithReturn(pondTag, name, null, false,
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
    public <P, R> void castWpwr(String pondTag, String name, P p,
                                FishHandler<R> callback, Class<R> clz) {
        int type = FishPond.TYPE_WITH_PARAM_WITH_RTN;
        castWithReturn(pondTag, name, p, false, true, callback, clz, type);
    }

    public <P, R> void castWpwrInThread(String pondTag, String name, P p,
                                        boolean isCallbackAtUIThread,
                                        FishHandler<R> callback, Class<R> clz) {
        int type = FishPond.TYPE_WITH_PARAM_WITH_RTN;
        castWithReturn(pondTag, name, p, true, isCallbackAtUIThread, callback, clz, type);
    }
    //------------------------------------------

    //==========================================
}
