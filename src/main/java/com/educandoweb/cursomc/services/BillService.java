package com.educandoweb.cursomc.services;

import com.educandoweb.cursomc.domain.Payment;
import com.educandoweb.cursomc.domain.PaymentWithBill;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BillService {

    public void fillPaymentWithBill(PaymentWithBill pgto, Date purchaseInstant){
        Calendar cal = Calendar.getInstance();
        cal.setTime(purchaseInstant);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        pgto.setDueDate(cal.getTime());
    }
}
