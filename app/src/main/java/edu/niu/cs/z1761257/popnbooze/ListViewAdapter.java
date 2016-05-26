package edu.niu.cs.z1761257.popnbooze;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pravin on 5/25/16.
 * Project: Pop'N'Booze
 */
public class ListViewAdapter extends BaseAdapter {

        // Declare Variables
        Context context;
        LayoutInflater inflater;
        ImageLoader imageLoader;
        private List<Drinks> drinkList = null;
        private ArrayList<Drinks> arraylist;

        public ListViewAdapter(Context context,
                List<Drinks> drinkList) {
            this.context = context;
            this.drinkList = drinkList;
            inflater = LayoutInflater.from(context);
            this.arraylist = new ArrayList<Drinks>();
            this.arraylist.addAll(drinkList);
            imageLoader = new ImageLoader(context);
        }

        public class ViewHolder {
            TextView name;
            TextView price;
            TextView calories;
            TextView qty;
            ImageView pic;
        }

        @Override
        public int getCount() {
            return drinkList.size();
        }

        @Override
        public Object getItem(int position) {
            return drinkList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.nameTV);
            holder.price = (TextView) view.findViewById(R.id.costTV);
            holder.qty = (TextView) view.findViewById(R.id.caloriesLabel);
            // Locate the ImageView in listview_item.xml
            holder.pic = (ImageView) view.findViewById(R.id.pic);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(drinkList.get(position).getItem_Name());
//        holder.price.setText(drinkList.get(position).getItem_Cost().toString());
//        holder.qty.setText(drinkList.get(position).getItem_Qty().toString());
        // Set the results into ImageView
        imageLoader.DisplayImage(drinkList.get(position).getItem_img(),
                holder.pic);
        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
 //Code goes here
            }
        });
        return view;
    }
}
