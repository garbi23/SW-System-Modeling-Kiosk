package com.crenu.kiosk.ui;

import com.crenu.kiosk.KioskSystem;
import com.crenu.kiosk.cart.CartItem;
import com.crenu.kiosk.placeOrder.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.PrimitiveIterator;

import static com.crenu.kiosk.KioskSystem.*;
import static com.crenu.kiosk.ui.PanelNameEntity.*;
import static com.crenu.kiosk.ui.PanelNameEntity.LOGIN_PANELNAME;

public class CartScreen {
    private JPanel main;
    private JPanel itemListPanel;
    public CartScreen(){
        this.main = new JPanel();
        this.main.setLayout(new BorderLayout());
        uiManager.addPanel(CART_PNAELNAME, this.main);

        initItemList();
        initPayment();
    }

    private void initItemList(){
        itemListPanel = new JPanel();
        itemListPanel.setLayout(new FlowLayout());
        itemListPanel.setPreferredSize(new Dimension(860, 800));
        itemListPanel.setBackground(Color.YELLOW);

        for(CartItem item : cart.getCartItems()){
            addItemPanel(item);
        }

        this.main.add(itemListPanel, BorderLayout.NORTH);
        itemListPanel.revalidate();
        itemListPanel.repaint();
    }

    private void addItemPanel(CartItem item){
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BorderLayout());
        itemPanel.setPreferredSize(new Dimension(860, 100));

        JButton plusButton = new JButton("+");
        JLabel nameText = new JLabel();
        nameText.setText(item.getName());
        JLabel countText = new JLabel();
        countText.setText("Count : " + item.getCount());
        plusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                item.addCount();
                countText.setText("Count : " + item.getCount());
            }
        });
        JButton minusButton = new JButton("-");
        minusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                item.minusCount();
                if(item.getCount() < 1){
                    cart.removeCartItem(item);
                    itemListPanel.remove(itemPanel);
                    itemListPanel.revalidate();
                    itemListPanel.repaint();
                    return;
                }
                countText.setText("Count : " + item.getCount());
            }
        });
        itemPanel.add(plusButton, BorderLayout.WEST);
        itemPanel.add(nameText, BorderLayout.NORTH);
        itemPanel.add(minusButton, BorderLayout.EAST);
        itemPanel.add(countText, BorderLayout.CENTER);
        itemListPanel.add(itemPanel);
    }

    private void showResultDialog(int orderNum){
        // Create a modal dialog that blocks input to other windows
        JDialog jd = new JDialog(uiManager, "Order Result", Dialog.ModalityType.APPLICATION_MODAL);
        jd.setLayout(new FlowLayout());
        jd.setBounds(430, 540, 200, 120);

        JLabel jLabel = new JLabel("Complete Order");
        JLabel jLabel2 = new JLabel("OrderNumber : " + orderNum);
        JButton jButton = new JButton("OK");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jd.dispose(); // Close the dialog
                KioskSystem.init(); // Reset the system or perform necessary actions after closing the dialog
            }
        });

        jd.add(jLabel);
        jd.add(jLabel2);
        jd.add(jButton);
        jd.setVisible(true);
    }

    private void initPayment(){
        JPanel paymentPanel = new JPanel();
        paymentPanel.setLayout(new GridLayout());
        paymentPanel.setPreferredSize(new Dimension(860, 200));
        this.main.add(paymentPanel, BorderLayout.SOUTH);
        JButton back = new JButton("BACK");
        paymentPanel.add(back, BorderLayout.SOUTH);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uiManager.allPanelVisibleOff();
                uiManager.panelSetVisible(MENU_PANELNAME, true);
            }
        });
        JButton creditButton = new JButton("CARD");
        creditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int orderNum = orderSystem.addNowOrder();
                showResultDialog(orderNum);
            }
        });
        paymentPanel.add(creditButton, BorderLayout.NORTH);
    }

}
