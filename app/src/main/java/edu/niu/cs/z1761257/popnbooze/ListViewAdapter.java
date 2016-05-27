package edu.niu.cs.z1761257.popnbooze;
import android.content.Context;
import android.media.MediaPlayer;
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

        // Declare Variables
        Context context;
        LayoutInflater inflater;
        ImageLoader imageLoader;
        private List<Drink> drinkList = null;
        private ArrayList<Drink> arraylist;
        Double remainingMoney = 0.0;
        static Double balance = 0.0,
                      newBalance = 0.0;
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Drinks");

        //creating object
        Cart cart = Cart.getInstance();

        //Adapter for ListView
        public ListViewAdapter(Context context,
                List<Drink> drinkList) {
            this.context = context;
            this.drinkList = drinkList;
            inflater = LayoutInflater.from(context);
            this.arraylist = new ArrayList<Drink>();
            this.arraylist.addAll(drinkList);
            imageLoader = new ImageLoader(context);
            setBalance(balance);
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
        holder.price.setText("$"+drinkList.get(position).getItem_Cost());
        holder.calories.setText(drinkList.get(position).getItem_Calories() + " cal");

        // Set the results into ImageView
        imageLoader.DisplayImage(drinkList.get(position).getItem_img(),
                holder.pic);

        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //variables
                //get drink position
                final Drink selectedDrink = drinkList.get(position);
                Double itemCost = Double.parseDouble(selectedDrink.getItem_Cost());
                int qty = Integer.parseInt(selectedDrink.getItem_Qty());
                Double depositeMoney = MainActivity.getMoney();

                /*
                Logic for each item in the listview
                if deposited money is greater than the item cost and
                also if there is item quantity proceed.
                 */
                if (depositeMoney > itemCost && qty > 0) {
                    newBalance = getBalance() + itemCost; //count total spent

                    remainingMoney = depositeMoney - itemCost; //done buying

                    setBalance(newBalance); //set new balance.
                    MainActivity.setMoney(depositeMoney); //set deposited money
                    qty = qty - 1; //decrease the quantity

                    selectedDrink.setItem_Qty(Integer.toString(qty));//get quantity of item

                    //Change property of button
                    MainActivity.toggle.setText("Collect");
                    MainActivity.toggle.setEnabled(true);
                    MainActivity.toggle.setVisibility(View.VISIBLE);

                    //Display Balance and Spent amount
                    MainActivity.moneyTV.setText("Balance: $" + new DecimalFormat("##.##").format(remainingMoney));
                    MainActivity.totalTV.setText("Spent: " + new DecimalFormat("##.##").format(newBalance));


                    //Check if the item is alcoholic product. if yes display a message
                    if(selectedDrink.getItem_type().equals("alcohol")){
                        MainActivity.totalTV.setText("Please show your ID");
                        MediaPlayer abc = MediaPlayer.create(context.getApplicationContext(), R.raw.blow);
                        abc.start();
                    }

                    //create cart items - to calculate calories, quantity etc.,
                    int existingValue = cart.cartItems.containsKey(selectedDrink) ? cart.cartItems.get(selectedDrink) : 0;
                    cart.cartItems.put(selectedDrink,existingValue + 1);


                    MainActivity.setMoney(remainingMoney); //set balance

                    //make bottle animate
                    Animation animAccelerateDecelerate = AnimationUtils.loadAnimation(context, R.anim.bottle_fall);
                    holder.pic.startAnimation(animAccelerateDecelerate);

                    //update quantity on cloud
                    query.getInBackground(selectedDrink.getObjectID(), new GetCallback<ParseObject>() {
                        public void done(ParseObject update, ParseException e) {
                            if (e == null) {
                                update.put("item_Qty",selectedDrink.getItem_Qty());
                                update.saveInBackground();
                            }
                        }
                    });
                }

                //check if there is no quantity available and displau a message
                else if (qty < 1) {

                    MainActivity.totalTV.setText("No "+ selectedDrink.getItem_Name() + " Stock");

                    //make noise if there is no quantity
                    MediaPlayer abc = MediaPlayer.create(context.getApplicationContext(), R.raw.basso);
                    abc.start();

                }
                //if user has less money than required
                else {
                    double negativeMoney = MainActivity.getMoney() - itemCost;
                    MainActivity.totalTV.setText("Need more $" + new DecimalFormat("##.##").format(Math.abs(negativeMoney)));

                    //make noise if user has less money
                    MediaPlayer abc = MediaPlayer.create(context.getApplicationContext(), R.raw.hero);
                    abc.start();
                }
            }

        });


        return view;
    }//end of getView

    //Set and get properties for balance.
   static public void setBalance(Double amount) {
        balance = amount;
    }

    static public Double getBalance() {
        return balance;
    }
}
