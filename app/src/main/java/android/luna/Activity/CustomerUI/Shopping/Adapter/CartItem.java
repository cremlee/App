package android.luna.Activity.CustomerUI.Shopping.Adapter;

import android.luna.Data.CustomerUI.DrinkMenuButton;
import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by Lee.li on 2018/3/21.
 */

public class CartItem implements Comparator<CartItem>,Comparable<CartItem> {
    private DrinkMenuButton drinkMenuButton;
    private int cups;
    public CartItem(DrinkMenuButton drinkMenuButton, int cups) {
        this.drinkMenuButton = drinkMenuButton;
        this.cups = cups;
    }

    public DrinkMenuButton getDrinkMenuButton() {
        return drinkMenuButton;
    }

    public void setDrinkMenuButton(DrinkMenuButton drinkMenuButton) {
        this.drinkMenuButton = drinkMenuButton;
    }

    public int getCups() {
        return cups;
    }

    public void setCups(int cups) {
        this.cups = cups;
    }

    @Override
    public int compareTo(@NonNull CartItem o) {
        return o.compare(o,this);
    }

    @Override
    public int compare(CartItem o1, CartItem o2) {
        if(o1.getCups()>o2.getCups())
            return 1;
        else if(o1.getCups()==o2.getCups())
            return 0;
        else
            return -1;
    }
}
