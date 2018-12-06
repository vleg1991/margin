package com.vleg.margin.view;

import com.vleg.margin.controller.MarginController;
import com.vleg.margin.exceptions.InvalidInputException;
import org.joda.money.BigMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Component(value = "recalculateButton")
public class Button extends JButton {

    private MarginController marginController;
    private PurchaseInfo purchaseInfo;
    private MarginLabel marginLabel;

    @Autowired
    public Button( MarginController marginController,
                   PurchaseInfo purchaseInfo,
                   @Qualifier(value = "marginLabel") MarginLabel marginLabel ) {
        super();
        this.marginController = marginController;
        this.purchaseInfo = purchaseInfo;
        this.marginLabel = marginLabel;
    }

    @PostConstruct
    public void init() {
        final JButton self = this;
        this.setText("Recalculate");
        this.setPreferredSize(new Dimension(110,30));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    BigMoney margin = marginController.getMargin(purchaseInfo);
                    BigDecimal roundedMargin = margin.getAmount().setScale(2, RoundingMode.DOWN).stripTrailingZeros();
                    marginLabel.setText(BigMoney.of(margin.getCurrencyUnit(), roundedMargin).toString());
                } catch (InvalidInputException ex) {
                    JFrame frame = (JFrame) SwingUtilities.getRoot(self);
                    JOptionPane.showMessageDialog(frame,
                            ex.getMessage(),
                            "Inane error",
                            JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }
            }
        });
    }

}
