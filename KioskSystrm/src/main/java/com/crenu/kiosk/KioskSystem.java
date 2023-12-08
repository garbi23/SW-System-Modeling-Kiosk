package com.crenu.kiosk;

import com.crenu.kiosk.admin.MenuManager;
import com.crenu.kiosk.cart.Cart;
import com.crenu.kiosk.placeOrder.OrderSystem;
import com.crenu.kiosk.ui.InitialScreen;
import com.crenu.kiosk.ui.UIManager;

public class KioskSystem {
    public static UIManager uiManager;
    public static MenuManager menuManager;
    public static Cart cart;
    public static OrderSystem orderSystem;
    public static void main(String[] args) {
        init();
    }
    public static void init(){
        uiManager = new UIManager();
        menuManager = new MenuManager();
        cart = new Cart();
        orderSystem = new OrderSystem();
        InitialScreen.init();
    }
}