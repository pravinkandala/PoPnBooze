package edu.niu.cs.z1761257.popnbooze;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import android.view.View;




public class MainActivity extends AppCompatActivity {

    // Declare Variables
    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    ListViewAdapter adapter;
    List<Drink> drinklist = null;
    static Double Money = 0.0, TOTAL =0.0;
    public static TextView moneyTV;
    public static TextView totalTV;
   // Drink drink;
    static final MediaPlayer mp = new MediaPlayer();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from activity_main.xml
        setContentView(R.layout.activity_main);
        // Execute RemoteDataTask AsyncTask
        new RemoteDataTask().execute();
        moneyTV = (TextView)findViewById(R.id.moneyTextView);
        totalTV = (TextView)findViewById(R.id.itemTotalTextView);
        mp.create(this,R.raw.basso);
    }//end of onCreate

    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           //  Create a progress dialog
            progressDialog();

        }

        public void progressDialog(){
            mProgressDialog = new ProgressDialog(MainActivity.this);
            // Set progress dialog title
            mProgressDialog.setTitle("Vending Machine");
            // Set progress dialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progres sdialog
            mProgressDialog.show();
        }//end of progressDialog

        @Override
        protected Void doInBackground(Void... params) {
            reloadList();
            return null;
        }//end of doInBackground

        //Download data from cloud
         public void reloadList(){
            // Create the array
            drinklist = new ArrayList<Drink>();
            try {
                // Locate the class table named "Country" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "Drinks");
                // Locate the column named "ranknum" in Parse.com and order list
                // by ascending
                query.orderByAscending("itemQty");

                ob = query.find();
                for (ParseObject drink : ob) {
                    // Locate images in flag column
                    ParseFile image = (ParseFile) drink.get("item_img");

                    Drink drinks = new Drink();
                    drinks.setItem_Name((String) drink.get("item_Name"));
                    drinks.setItem_Cost((String) drink.get("item_Price"));
                    drinks.setItem_Qty((String) drink.get("item_Qty"));
                    drinks.setObjectID((String) drink.get("objectId"));
                    drinks.setItem_type((String)drink.get("item_type"));
                    drinks.setItem_Calories((String) drink.get("item_Calories"));
                    drinks.setItem_img( image.getUrl());

                    // drinks.setFlag(image.getUrl());
                    drinklist.add(drinks);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
        }//end of reloadList

        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            listview = (ListView) findViewById(R.id.listview);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(MainActivity.this,
                    drinklist);
            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }//end of onPostExecute
    }//end of RemoteDataTask


    /*
        Buttons for currency input $20, $10, $5, $1, $.25
     */
    public void b20(View view){
        Money += 20;
        Display();
    }
    public void b10(View view){
        Money += 10;
        Display();
    }
    public void b5(View view){
        Money += 5;
        Display();
    }
    public void b1(View view){
        Money += 1;
        Display();
    }
    public void bp25(View view){
        Money += 0.25;
        Display();
    }

    //Displays total money @ moneyTextView
    public void Display(){
        this.setMoney(Money);

        moneyTV.setText("Money: $"+new DecimalFormat("##.##").format(Money));
    }


    //Set and get properties for money

    public static void setMoney(Double money) {
        Money = money;
    }

    public static Double getMoney() {
        return Money;
    }


}//end of MainActivity
