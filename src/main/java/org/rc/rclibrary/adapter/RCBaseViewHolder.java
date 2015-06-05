package org.rc.rclibrary.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * User: WuRuiqiang(263454190@qq.com)
 * Date: 2015-04-29
 * Time: 14:38
 * Description：
 */
public class RCBaseViewHolder {

    protected SparseArray<View> sparseArray;

    protected View mConvertView;

    private RCBaseViewHolder(int layoutId, ViewGroup parent) {
        this.sparseArray = new SparseArray<>();
        mConvertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
    }

    public static RCBaseViewHolder get(int layoutId, ViewGroup parent, View convertView) {
        if (convertView == null) {
            return new RCBaseViewHolder(layoutId, parent);
        } else {
            return (RCBaseViewHolder) convertView.getTag();
        }
    }

    public <T extends View> T getView(int viewId) {
        View view = sparseArray.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            sparseArray.put(viewId, view);
        }
        return (T) view;
    }

    public View getmConvertView() {
        return mConvertView;
    }
}
