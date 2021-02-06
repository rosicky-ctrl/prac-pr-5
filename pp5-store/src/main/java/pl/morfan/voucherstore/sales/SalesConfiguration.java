package pl.morfan.voucherstore.sales;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.morfan.payment.payu.PayU;
import pl.morfan.payment.payu.PayUApiCredentials;
import pl.morfan.payment.payu.http.NetHttpClientPayuHttp;
import pl.morfan.voucherstore.productcatalog.ProductCatalogFacade;
import pl.morfan.voucherstore.sales.basket.InMemoryBasketStorage;
import pl.morfan.voucherstore.sales.offer.OfferMaker;
import pl.morfan.voucherstore.sales.ordering.ReservationRepository;
import pl.morfan.voucherstore.sales.payment.PayUPaymentGateway;
import pl.morfan.voucherstore.sales.payment.PaymentGateway;
import pl.morfan.voucherstore.sales.productd.ProductCatalogProductDetailsProvider;
import pl.morfan.voucherstore.sales.productd.ProductDetailsProvider;

import java.util.UUID;

@Configuration
public class SalesConfiguration {

    @Bean
    SalesFacade salesFacade(ProductCatalogFacade productCatalogFacade, OfferMaker offerMaker, PaymentGateway paymentGateway, ReservationRepository reservationRepository) {
        var alwaysSameCustomer = UUID.randomUUID().toString();

        return new SalesFacade(
                new InMemoryBasketStorage(),
                productCatalogFacade,
                () -> alwaysSameCustomer,
                (productId) -> true,
                offerMaker,
                paymentGateway,
                reservationRepository
        );
    }

    @Bean
    PaymentGateway payUPaymentGateway(PayU payU) {
        return new PayUPaymentGateway(payU);
    }

    @Bean
    PayU payU() {
        return new PayU(
            PayUApiCredentials.sandbox(),
            new NetHttpClientPayuHttp()
        );
    }

    @Bean
    OfferMaker offerMaker(ProductDetailsProvider productDetailsProvider) {
        return new OfferMaker(productDetailsProvider);
    }

    @Bean
    ProductDetailsProvider productDetailsProvider(ProductCatalogFacade productCatalogFacade) {
        return new ProductCatalogProductDetailsProvider(productCatalogFacade);
    }
}
