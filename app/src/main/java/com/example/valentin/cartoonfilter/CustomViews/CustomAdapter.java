package com.example.valentin.cartoonfilter.CustomViews;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.valentin.cartoonfilter.R;

import java.util.List;


/**
 * Created by Valentin on 14/07/2015.
 */
public class CustomAdapter extends ArrayAdapter {

    public static final int MENU_ITEM = 0;
    private final List<ListViewItem> objects;

    public CustomAdapter(Context context, int resource, List<ListViewItem> objects) {
        super(context, resource, objects);
        this.objects = objects;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return objects.get(position).getType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        ListViewItem listViewItem = objects.get(position);
        int listViewItemType = getItemViewType(position);

        if (convertView == null) {
            TextView textView;
            if (listViewItemType == MENU_ITEM) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.menu_item, null);
            }
            textView = (TextView) convertView.findViewById(R.id.textModule);
            textView.setTextColor(Color.WHITE);
            viewHolder = new ViewHolder(textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.getText().setText(listViewItem.getText());
        return convertView;
    }
}
