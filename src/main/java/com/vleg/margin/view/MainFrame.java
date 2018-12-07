package com.vleg.margin.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
public class MainFrame extends JFrame {

    private Button button;
    private DateField dateField;
    private AmountField amountField;
    private MarginLabel marginLabel;

    @Autowired
    public MainFrame( @Qualifier(value = "recalculateButton") Button button,
                      @Qualifier(value = "dateField") DateField dateField,
                      @Qualifier(value = "amountField") AmountField amountField,
                      @Qualifier(value = "marginLabel") MarginLabel marginLabel ) throws HeadlessException {
        super();
        this.button = button;
        this.dateField = dateField;
        this.amountField = amountField;
        this.marginLabel = marginLabel;
    }

    @PostConstruct
    public void init() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(new Dimension(60, 250));
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        JLabel label1 = new JLabel();
        label1.setText("Purchase date ");
        panel.add(label1);

        panel.add(dateField);

        JLabel label2 = new JLabel();
        label2.setText("Amount USD ");
        panel.add(label2);

        panel.add(amountField);
        panel.add(button);
        panel.add(marginLabel);

        this.getContentPane().add(panel);

        this.setVisible(true);
        this.setState(Frame.NORMAL);
    }
}
