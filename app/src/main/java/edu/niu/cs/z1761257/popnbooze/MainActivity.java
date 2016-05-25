package edu.niu.cs.z1761257.popnbooze;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    //Declare Variables
    Button cokeBtn, pepsiBtn, fireballBtn, blueguyBtn, spriteBtn;
    Double total = 0.0, money = 0.0, negativemoney=0.0;
    TextView totalTV, moneyTV;
    EditText moneyET;
    static int COKEQTY = 3,PEPSIQTY = 1, SPRITEQTY = 5;
    static double COKEPRICE = 1.85,
                  PEPSIPRICE = 1.99,
                  SPRITEPRICE = 1.35;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cokeBtn = (Button)findViewById(R.id.cokeButton);
        pepsiBtn = (Button)findViewById(R.id.cokeButton);
        fireballBtn = (Button)findViewById(R.id.fireballButton);
        blueguyBtn = (Button)findViewById(R.id.blueguyButton);
        spriteBtn = (Button)findViewById(R.id.spriteButton);

        totalTV = (TextView)findViewById(R.id.totalTV);
        moneyET = (EditText)findViewById(R.id.cashTextView);
        moneyTV = (TextView)findViewById(R.id.moneyEditText);

    }

    public void coke(View view){

        if(money>COKEPRICE&&COKEQTY>0) {
            total += COKEPRICE;
            money -= COKEPRICE;
            COKEQTY --;
            moneyTV.setText("money"+new DecimalFormat("##.##").format(money));
            totalTV.setText("Total: " + new DecimalFormat("##.##").format(total));
        }else if(COKEQTY<1){
           totalTV.setText("no coke avalable");
        }
        else{
                totalTV.setText("insert money");
        }
    }

    public void sprite(View view){
        if(money>SPRITEPRICE&&SPRITEQTY>0) {
            total += SPRITEPRICE;
            money -= SPRITEPRICE;
            SPRITEQTY --;
            moneyTV.setText("money: $"+new DecimalFormat("##.##").format(money));
            totalTV.setText("Total: " + new DecimalFormat("##.##").format(total));
        }else if(SPRITEQTY<1) {
            totalTV.setText("No Sprite");
        }
        else
        {
                totalTV.setText("insert money");
        }

    }

    public void pepsi(View view){
        if(money>PEPSIPRICE&&PEPSIQTY>0) {
            total += PEPSIPRICE;
            money -= PEPSIPRICE;
            PEPSIQTY--;
            moneyTV.setText("money: $"+new DecimalFormat("##.##").format(money));
            totalTV.setText("Total: " + new DecimalFormat("##.##").format(total));
        }else if (PEPSIQTY<1) {
            totalTV.setText("No pepsi available");
        }else
        {
            totalTV.setText("insert money");
        }
    }

    public void insertCash(View view){
        money+= Double.valueOf(moneyET.getText().toString());
        moneyTV.setText("money: $"+new DecimalFormat("##.##").format(money));
    }



}
