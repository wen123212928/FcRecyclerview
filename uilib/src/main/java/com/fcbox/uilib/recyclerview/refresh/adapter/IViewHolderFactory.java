package com.fcbox.uilib.recyclerview.refresh.adapter;

import android.view.ViewGroup;

/**
 * 为避免反射，ViewType 交给开发者自己管理
 *
 */

public interface IViewHolderFactory {

    <V extends BaseViewHolder> V getViewHolder(ViewGroup parent, int viewType);
}
