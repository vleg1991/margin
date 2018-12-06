package com.vleg.margin.view;

import org.apache.commons.lang3.StringUtils;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.math.BigDecimal;

@Component(value = "amountField")
public class AmountField extends JTextField {


    private PurchaseInfo purchaseInfo;

    public AmountField(PurchaseInfo purchaseInfo) {
        super();
        this.purchaseInfo = purchaseInfo;
    }

    @PostConstruct
    public void init() {
        this.setName("amount");
        this.setPreferredSize(new Dimension(110,30));
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String value = getText();
                if (StringUtils.isNotEmpty(value)) {
                    if (value.matches("[+-]?([0-9]*[.])?[0-9]+")) {
                        BigDecimal valueDecimal = BigDecimal.valueOf(Double.parseDouble(value));
                        BigMoney result = BigMoney.of(CurrencyUnit.USD, valueDecimal);
                        purchaseInfo.setAmount(result);
                    } else {
                        setText("");
                    }
                } else {
                    purchaseInfo.setAmount(null);
                }
            }
        });
    }
}
