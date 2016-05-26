package edu.niu.cs.z1761257.popnbooze;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
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
        private List<Drink> drinkList = null;
        private ArrayList<Drink> arraylist;
        Button bill20Btn;
//        Double money = MainActivity.getMoney();
        static Double total = MainActivity.TOTAL;




        public ListViewAdapter(Context context,
                List<Drink> drinkList) {
            this.context = context;
            this.drinkList = drinkList;
            inflater = LayoutInflater.from(context);
            this.arraylist = new ArrayList<Drink>();
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
        holder.price.setText(drinkList.get(position).getItem_Cost());
        holder.qty.setText(drinkList.get(position).getItem_Qty());
        // Set the results into ImageView
        imageLoader.DisplayImage(drinkList.get(position).getItem_img(),
                holder.pic);
        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Code goes here
//                Toast.makeText(context, "Money: $" + Drink.getMoney(), Toast.LENGTH_SHORT).show();


                Double cost = Double.parseDouble(drinkList.get(position).getItem_Cost());

                Double qty = Double.parseDouble(drinkList.get(position).getItem_Qty());

                Double a = MainActivity.getMoney();


                if (a > cost && qty > 0) {
                    total += cost;
                    a -= cost;
                    MainActivity.setMoney(a);
                    qty = qty - 1;
                    drinkList.get(position).setItem_Qty(qty.toString());
                    MainActivity.moneyTV.setText("money: $" + new DecimalFormat("##.##").format(a));
                    MainActivity.totalTV.setText("Total: " + new DecimalFormat("##.##").format(total));
                } else if (qty < 1) {

                    MainActivity.mp.start();

                    MainActivity.totalTV.setText("No Coke Stock");

                } else {
                    double b = cost - a;
                    MainActivity.totalTV.setText("Need more $" + b);
                }
            }

        });
        return view;


    }
}
