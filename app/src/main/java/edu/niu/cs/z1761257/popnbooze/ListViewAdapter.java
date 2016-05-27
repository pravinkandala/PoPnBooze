package edu.niu.cs.z1761257.popnbooze;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

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
        static Double total = 0.0;
        final ParseObject eventObject = new ParseObject("Drinks");
        Cart cart = new Cart();
        OptimalAmount optimalAmount = new OptimalAmount();






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
            holder.qty = (TextView) view.findViewById(R.id.caloriesTV);
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

                Drink selectedDrink = drinkList.get(position);
                Double cost = Double.parseDouble(selectedDrink.getItem_Cost());

                Double qty = Double.parseDouble(selectedDrink.getItem_Qty());

                Double a = MainActivity.getMoney();


                if (a > cost && qty > 0) {
                    total += cost;
                    a -= cost;
                    MainActivity.setMoney(a);
                    qty = qty - 1;
                    selectedDrink.setItem_Qty(qty.toString());
//
                    MainActivity.moneyTV.setText("money: $" + new DecimalFormat("##.##").format(a));
                    MainActivity.totalTV.setText("Total: " + new DecimalFormat("##.##").format(total));

                    int existingValue = cart.cartItems.containsKey(selectedDrink) ? cart.cartItems.get(selectedDrink) : 0;
                    cart.cartItems.put(selectedDrink,existingValue + 1);

                    cart.optimise(a);
                    optimalAmount = cart.getReturnAmount();

                    Animation animAccelerateDecelerate = AnimationUtils.loadAnimation(context, R.anim.bottle_fall);
                    holder.pic.startAnimation(animAccelerateDecelerate);




                    Toast.makeText(context,"Dollars:"+ optimalAmount.dollars + "Quarters" + optimalAmount.quarters + "Dimes: " + optimalAmount.dimes + "Cents: "+ optimalAmount.cents,Toast.LENGTH_SHORT).show();

                } else if (qty < 1) {

                    MainActivity.mp.start();

                    MainActivity.totalTV.setText("No "+ selectedDrink.getItem_Name() + " Stock");

                } else {
                    double b = cost - a;
                    MainActivity.totalTV.setText("Need more $" + b);
                }
            }

        });


        return view;
    }

    public void collectButton(View view){
        total = 0.0;
        MainActivity.setMoney(0.0);

    }
}
