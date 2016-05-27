package edu.niu.cs.z1761257.popnbooze;

/**
 * Created by Pravin on 5/25/16.
 * Project: Pop'N'Booze
 */

/*contains details about drink*/

public class Drink {

    private String item_Name,
                   item_img,
                   objectID,
                   item_type,
                   item_Cost,
                   item_Calories,
                   item_Qty;

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public String getObjectID() {
        return objectID;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_Name(String item_Name) {
        this.item_Name = item_Name;
    }

    public String getItem_Name() {
        return item_Name;
    }

    public void setItem_Cost(String item_Cost) {
        this.item_Cost = item_Cost;
    }

    public String getItem_Cost() {
        return item_Cost;
    }

    public void setItem_Qty(String item_Qty) {
        this.item_Qty = item_Qty;
    }

    public String getItem_Qty() {
        return item_Qty;
    }

    public void setItem_Calories(String item_Calories) {
        this.item_Calories = item_Calories;
    }

    public String getItem_Calories() {
        return item_Calories;
    }

    public void setItem_img(String item_img) {
        this.item_img = item_img;
    }

    public String getItem_img() {
        return item_img;
    }

}
