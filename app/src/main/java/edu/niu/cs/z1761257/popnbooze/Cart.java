package edu.niu.cs.z1761257.popnbooze;

import android.os.Debug;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pravin on 5/26/16.
 * Project: Pop'N'Booze
 */
public class Cart {
    // {Drink1(Coke), 2},{Drink
    Map<Drink, Integer> cartItems;
    String msg;
    OptimalAmount returnAmount;

    public Cart() {
        cartItems = new HashMap<>();
        returnAmount = new OptimalAmount();
    }
    public OptimalAmount getReturnAmount() {
        return returnAmount;
    }

    public void setCartItems(Map<Drink, Integer> cartItems) {
        this.cartItems = cartItems;
    }

    public Map<Drink, Integer> getCartItems() {
        return cartItems;
    }

    public double getCalories() {
        double totCal = 0;
        for (Map.Entry<Drink, Integer> item : cartItems.entrySet()) {
            totCal += Double.parseDouble(item.getKey().getItem_Calories())*item.getValue();
        }
        return totCal;
    }

    public double totalCartValue() {
        double totalCost = 0;
        for (Map.Entry<Drink, Integer> item : cartItems.entrySet()) {
            totalCost += Double.parseDouble(item.getKey().getItem_Cost())*item.getValue();
        }

        return totalCost;
    }

    //userCart.optimise(11.90)
    //OptimalAmount o = userCart.getReturnAmount();
    //o.dimes, o.nickels, o.dollars
    public void optimise(double amtReturn){

//        int realPart = (int) amtReturn;
//        double fractionalPart = amtReturn - realPart;
//        returnAmount.dollars = (int) amtReturn;
//        double cents = fractionalPart*100;
//        int centsInt = (int) cents;
//        totalCents(centsInt);
        int change = (int)(Math.ceil(amtReturn*100));
        int dollars = Math.round((int)change/100);
        change=change%100;
        int quarters = Math.round((int)change/25);
        change=change%25;
        int dimes = Math.round((int)change/10);
        change=change%10;
        int nickels = Math.round((int)change/5);
        change=change%5;
        int pennies = Math.round((int)change/1);
        returnAmount.dollars = dollars;
        returnAmount.quarters = quarters;
        returnAmount.dimes = dimes;
        returnAmount.nickels = nickels;
        returnAmount.cents = pennies;
    }
    private void totalCents(int totalCents){



        int p=0, n=0, d=0, q=0;
        for(p=0; p <= (totalCents-q*25-d*10-n*5) ; p++){

            for(n=0; (n*5) <= (totalCents-q*25-d*10) ; n++){

                for(d=0; (d*10) <= (totalCents-q*25) ; d++){

                    for(q=0; (q*25) <= totalCents ; q++){

                        if(((q*25) + (d*10) + (n*5) + p ) == totalCents){

                            //msg = "Q: "+q+" D: "+d+" N: "+n+" P: "+p;Log
                            Log.d("INFO","Quaters :" + d);
                            returnAmount.quarters = q;
                            returnAmount.dimes = d;
                            returnAmount.nickels = n;
                            returnAmount.cents = p;
                            break;
                        }
                    }
                }
            }
        }
    }
}
