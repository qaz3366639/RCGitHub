package org.rc.rclibrary.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * User: WuRuiqiang(263454190@qq.com)
 * Date: 2015-04-29
 * Time: 14:00
 * Description：
 */
public class RCBaseAdapter<T> extends BaseAdapter {

    protected List<T> data;

    protected final Context context;

    protected int layoutId;

    public RCBaseAdapter(Context context, int layoutId) {
        this(context, null, layoutId);
    }

    public RCBaseAdapter(Context context, List<T> data, int layoutId) {
        if (data == null) {
            data = new ArrayList<>(0);
        }
        this.context = context;
        this.data = data;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RCBaseViewHolder viewHolder;
        viewHolder = RCBaseViewHolder.get(layoutId, parent, convertView);
        displayItem(viewHolder, position);
        return viewHolder.getmConvertView();
    }

    protected void displayItem(RCBaseViewHolder viewHolder, int position){}

    public void refresh(List<T> data) {
        this.data.clear();
        if (data != null) {
            this.data = data;
        }
        notifyDataSetChanged();
    }

    public void load(List<T> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return data;
    }
}
