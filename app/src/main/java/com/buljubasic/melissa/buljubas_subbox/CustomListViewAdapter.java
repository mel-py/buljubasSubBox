package com.buljubasic.melissa.buljubas_subbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Melissa on 2018-01-25.
 */

public class CustomListViewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Subscription> subs;
    private static LayoutInflater inflater = null;

    public CustomListViewAdapter(Context context, ArrayList<Subscription> data) {
        mContext = context;
        subs = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getItem(int position) {
        return subs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        if (subs != null) {
            return subs.size();
        } else {
            return 0;
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (convertView == null) {
            view = inflater.inflate(R.layout.list_item, null);

            TextView name = (TextView)view.findViewById(R.id.name);
            TextView date = (TextView)view.findViewById(R.id.date);
            TextView charge = (TextView)view.findViewById(R.id.charge);
            TextView comment = (TextView)view.findViewById(R.id.comment);

           Subscription mSub = subs.get(position);

            name.setText(mSub.getName());
            date.setText(mSub.getDate());
            charge.setText(mSub.getCharge());
            comment.setText(mSub.getComment());
        }

        return view;
    }
}
