package edu.niu.cs.z1761257.popnbooze;

/**
 * Created by Pravin on 5/25/16.
 * Project: Pop'N'Booze
 */
public class Drinks {

    private String item_Name,item_img;
    private Double item_Cost, item_Calories, item_Qty;

    public void setItem_Name(String item_Name) {
        this.item_Name = item_Name;
    }

    public String getItem_Name() {
        return item_Name;
    }

    public void setItem_Cost(Double item_Cost) {
        this.item_Cost = item_Cost;
    }

    public Double getItem_Cost() {
        return item_Cost;
    }

    public void setItem_Qty(Double item_Qty) {
        this.item_Qty = item_Qty;
    }

    public Double getItem_Qty() {
        return item_Qty;
    }

    public void setItem_Calories(Double item_Calories) {
        this.item_Calories = item_Calories;
    }

    public Double getItem_Calories() {
        return item_Calories;
    }

    public void setItem_img(String item_img) {
        this.item_img = item_img;
    }

    public String getItem_img() {
        return item_img;
    }
}
