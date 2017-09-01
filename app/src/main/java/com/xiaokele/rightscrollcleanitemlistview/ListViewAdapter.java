package com.xiaokele.rightscrollcleanitemlistview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by QiyiLive on 2017/9/1.
 */

public class ListViewAdapter extends ArrayAdapter<String> {

    public ListViewAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (null == convertView) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.listview_item, null);
        }else{
            view =convertView;
        }
        TextView tvContent = (TextView) view.findViewById(R.id.content_tv);
        tvContent.setText(getItem(position));
        return view;
    }
}
