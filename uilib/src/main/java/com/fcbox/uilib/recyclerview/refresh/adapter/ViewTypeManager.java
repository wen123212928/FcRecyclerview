package com.fcbox.uilib.recyclerview.refresh.adapter;

import android.util.SparseIntArray;
import android.view.ViewGroup;

/**
 * 为避免反射，ViewType 交给开发者自己管理(IViewTypeFactory)
 *
 */

public class ViewTypeManager {

    private final String TAG = "ViewTypeManager";
    // position to Type
    private SparseIntArray mPositionToTypeMap;  //position --> ViewType

    private IViewHolderFactory mViewHolderFactory;

    public ViewTypeManager() {
        mPositionToTypeMap = new SparseIntArray();
    }

    protected int getViewType(int position) {
        return mPositionToTypeMap.get(position);
    }

    protected void putViewType(int position, int viewType) {
        mPositionToTypeMap.put(position, viewType);
    }

    public void setViewHolderFactory(IViewHolderFactory factory){
        mViewHolderFactory = factory;
    }

    protected <T extends BaseViewHolder> T getViewHolder(ViewGroup parent, int viewType){
        if (mViewHolderFactory != null) {
            return mViewHolderFactory.getViewHolder(parent, viewType);
        } else {
            return null;
        }
    }
}
