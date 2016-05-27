package edu.niu.cs.z1761257.popnbooze;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


/**
 * Created by Pravin on 5/25/16.
 * Project: Pop'N'Booze
 */
public class ListViewAdapter extends BaseAdapter {


        ParseApplication parseApplication = new ParseApplication();

        // Declare Variables
        Context context;
        LayoutInflater inflater;
        ImageLoader imageLoader;
        private List<Drink> drinkList = null;
        private ArrayList<Drink> arraylist;


        static Double total = 0.0; //make value of total available on other .java files

    final ParseQuery<ParseObject> query = ParseQuery.getQuery("Drinks");
//        final ParseObject parseObject = new ParseObject("Drinks");

        //creating objects of respective Classes
        Cart cart = new Cart();
        OptimalAmount optimalAmount = new OptimalAmount();



        //Adapter for ListView
        public ListViewAdapter(Context context,
                List<Drink> drinkList) {
            this.context = context;
            this.drinkList = drinkList;
            inflater = LayoutInflater.from(context);
            this.arraylist = new ArrayList<Drink>();
            this.arraylist.addAll(drinkList);
            imageLoader = new ImageLoader(context);
        }//end of ListViewAdapter

        //Creating a class called ViewHolder which contains
        // declaration for elements on listview.
        public class ViewHolder {
            TextView name;
            TextView price;
            TextView calories;
            TextView qty;
            ImageView pic;
        }//end of ViewHolder

        //get num of elements
        @Override
        public int getCount() {
            return drinkList.size();
        }

        //returns position of item
        @Override
        public Object getItem(int position) {
            return drinkList.get(position);
        }//getItem

        //returns id of item
        @Override
        public long getItemId(int position) {
            return position;
        }

    public View getView(final int position, View view, final ViewGroup parent) {

        final ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);

            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.nameTV);
            holder.price = (TextView) view.findViewById(R.id.costTV);
            holder.calories = (TextView) view.findViewById(R.id.caloriesTV);

            // Locate the ImageView in listview_item.xml
            holder.pic = (ImageView) view.findViewById(R.id.pic);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // Set the results into TextViews
        holder.name.setText(drinkList.get(position).getItem_Name());
        holder.price.setText(drinkList.get(position).getItem_Cost());
        holder.calories.setText(drinkList.get(position).getItem_Calories());

        // Set the results into ImageView
        imageLoader.DisplayImage(drinkList.get(position).getItem_img(),
                holder.pic);

        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Code goes here
                //Toast.makeText(context, "Money: $" + Drink.getMoney(), Toast.LENGTH_SHORT).show();

                final Drink selectedDrink = drinkList.get(position);
                Double cost = Double.parseDouble(selectedDrink.getItem_Cost());

                Double qty = Double.parseDouble(selectedDrink.getItem_Qty());

                Double a = MainActivity.getMoney();


                if (a > cost && qty > 0) {
                    total += cost;
                    a -= cost;
                    MainActivity.setMoney(a);
                    qty = qty - 1;
                    selectedDrink.setItem_Qty(qty.toString());

                    MainActivity.moneyTV.setText("money: $" + new DecimalFormat("##.##").format(a));
                    MainActivity.totalTV.setText("Total: " + new DecimalFormat("##.##").format(total));

//                    MainActivity.totalTV.setText(selectedDrink.getItem_type());

                    if(selectedDrink.getItem_type().equals("alcohol")){
                        MainActivity.totalTV.setText("Please show your ID");
                    }


                    int existingValue = cart.cartItems.containsKey(selectedDrink) ? cart.cartItems.get(selectedDrink) : 0;
                    cart.cartItems.put(selectedDrink,existingValue + 1);

                    cart.optimise(a);
                    optimalAmount = cart.getReturnAmount();

                    Animation animAccelerateDecelerate = AnimationUtils.loadAnimation(context, R.anim.bottle_fall);
                    holder.pic.startAnimation(animAccelerateDecelerate);

//                    Toast.makeText(context, "Object ID: "+selectedDrink.getObjectID()+", Qty: "+
//                            selectedDrink.getItem_Qty(),Toast.LENGTH_SHORT).show();
                    // Retrieve the object by id
                    query.getInBackground(selectedDrink.getObjectID(), new GetCallback<ParseObject>() {
                        public void done(ParseObject update, ParseException e) {
                            if (e == null) {
                                update.put("item_Qty",selectedDrink.getItem_Qty());
                                update.saveInBackground();
                            }
                        }
                    });




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
    }//end of getView

    public void collectButton(View view){
        total = 0.0;
        MainActivity.setMoney(0.0);

    }
}
