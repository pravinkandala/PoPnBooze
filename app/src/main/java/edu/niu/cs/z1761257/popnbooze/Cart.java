package edu.niu.cs.z1761257.popnbooze;

import java.util.List;
import java.util.Map;

/**
 * Created by Pravin on 5/26/16.
 * Project: Pop'N'Booze
 */
public class Cart {
    // {Drink1(Coke), 2},{Drink
    Map<Drink, Integer> cartItems;

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

}
