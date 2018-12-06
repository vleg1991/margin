package com.vleg.margin.view;

import org.jdesktop.swingx.JXDatePicker;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Component(value = "dateField")
public class DateField extends JXDatePicker {

    private PurchaseInfo purchaseInfo;

    public DateField(PurchaseInfo purchaseInfo) {
        super();
        this.purchaseInfo = purchaseInfo;
    }

    @PostConstruct
    public void init() {
        this.setName("date");
        this.setPreferredSize(new Dimension(110,30));
        this.setDate(Calendar.getInstance().getTime());
        this.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
        this.getEditor().setEditable(false);
        this.getEditor().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                Date value = getDate();
                if (Objects.nonNull(value)) {
                    LocalDate date = getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    purchaseInfo.setDate(date);
                }
            }
        });
    }
}
