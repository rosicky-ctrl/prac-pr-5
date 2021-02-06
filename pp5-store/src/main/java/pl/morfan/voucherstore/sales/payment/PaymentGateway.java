package pl.morfan.voucherstore.sales.payment;

import pl.morfan.payment.payu.exceptions.PayUException;
import pl.morfan.voucherstore.sales.ordering.Reservation;

public interface PaymentGateway {
    PaymentDetails registerFor(Reservation reservation) throws PayUException;

    boolean isTrusted(PaymentUpdateStatusRequest updateStatusRequest);
}
