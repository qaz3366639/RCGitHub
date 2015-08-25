package org.rc.rclibrary.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: WuRuiqiang(263454190@qq.com)
 * Date: 2015-06-16 10:38
 */
public abstract class RCBaseExpandAdapter<T> extends BaseExpandableListAdapter {

    protected List<T> data;

    protected final Context context;

    protected int groupLayoutId;

    protected int childLayoutId;

    public RCBaseExpandAdapter(Context context, int layoutId, int childLayoutId) {
        this(context, null, layoutId, childLayoutId);
    }

    public RCBaseExpandAdapter(Context context, List<T> data, int groupLayoutId, int childLayoutId) {
        if (data == null) {
            data = new ArrayList<>(0);
        }
        this.context       = context;
        this.data          = data;
        this.groupLayoutId = groupLayoutId;
        this.childLayoutId = childLayoutId;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    protected abstract void displayItem(RCBaseViewHolder viewHolder, int position);

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
}
