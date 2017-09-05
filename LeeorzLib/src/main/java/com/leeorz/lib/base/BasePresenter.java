package com.leeorz.lib.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * Created by lee on 17/3/11.
 */

public class BasePresenter<V extends BaseView, M extends BaseModel> implements BaseRequestListener{

    protected M mModel;
    private Reference<V> mReference;

    public BasePresenter(V v) {
        try {
            Type type = getClass().getGenericSuperclass();
            ParameterizedType p = (ParameterizedType) type;
            Class c = (Class) p.getActualTypeArguments()[1];
            mModel = (M) Class.forName(c.getName()).newInstance();

            mReference = new WeakReference(v);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public V getIView() {
        if(mReference == null)return null;
        return mReference.get();
    }

    public void destroy() {
        mReference.clear();
        mReference = null;
        mModel.cancelRequest();
    }

    @Override
    public void onTokenInvalid() {
        if(getIView() == null)return;
        getIView().onUserTokenInvalid();
    }
}
