package pl.morfan.voucherstore.sales.payment;

import pl.morfan.payment.payu.exceptions.PayUException;

public class PaymentException extends IllegalStateException {
    public PaymentException(PayUException e) {
        super(e);
    }
}
