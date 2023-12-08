package com.crenu.kiosk.placeOrder;

import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.HashMap;

import static com.crenu.kiosk.KioskSystem.cart;

public class OrderSystem {

    private HashMap<Integer, Order> orderList;
    private int orderNum;
    public OrderSystem(){
        orderList = new HashMap<>();
        orderNum = 0;
    }

    public int addNowOrder(){
        if(cart.getCartItems().isEmpty()) return -1;
        Order order = new Order(cart.getCartItems());
        orderNum++;
        order.setOrderNumber(orderNum);
        orderList.put(orderNum, order);
        return orderNum;
    }

    public void removeOrder(int orderNum){
        orderList.remove(orderNum);
    }

    public Order getOrder(int orderNum){
        return  orderList.get(orderNum);
    }
}