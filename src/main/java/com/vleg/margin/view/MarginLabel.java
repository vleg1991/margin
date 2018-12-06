package com.vleg.margin.view;

import org.springframework.stereotype.Component;

import javax.swing.*;

@Component(value = "marginLabel")
public class MarginLabel extends JLabel {

    private PurchaseInfo purchaseInfo;

    public MarginLabel(PurchaseInfo purchaseInfo) {
        super();
        this.purchaseInfo = purchaseInfo;
    }
}
