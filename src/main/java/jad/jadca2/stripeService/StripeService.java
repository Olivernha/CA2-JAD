package jad.jadca2.stripeService;

import jad.jadca2.stripeDto.ProductRequest;
import jad.jadca2.stripeDto.StripeResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.secretKey}")
    private String secretKey;

    public StripeResponse checkoutProducts(ProductRequest productRequest) {
        // Set Stripe Secret Key
        Stripe.apiKey = secretKey;

        try {
            // Create a Product in Stripe checkout
            SessionCreateParams.LineItem.PriceData.ProductData productData =
                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                            .setName(productRequest.getName()) // Service name
                            .build();

            // Create price data (Amount should be in cents)
            SessionCreateParams.LineItem.PriceData priceData =
                    SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency(productRequest.getCurrency() != null ? productRequest.getCurrency() : "USD")
                            .setUnitAmount(productRequest.getAmount()) // Convert amount to cents if necessary
                            .setProductData(productData)
                            .build();

            // Create new line item
            SessionCreateParams.LineItem lineItem =
                    SessionCreateParams.LineItem.builder()
                            .setQuantity(productRequest.getQuantity())
                            .setPriceData(priceData)
                            .build();

            // Create new session with metadata
            SessionCreateParams.Builder sessionBuilder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:8080/CA1/CreateBookings?session_id={CHECKOUT_SESSION_ID}")
                    .setCancelUrl("http://localhost:8080/CA1/user/bookingFail.jsp")
                    .addLineItem(lineItem);

            // Store user ID and cart JSON as metadata
            if (productRequest.getUserId() != null) {
                sessionBuilder.putMetadata("userId", String.valueOf(productRequest.getUserId()));
            }
            if (productRequest.getCartJson() != null) {
                sessionBuilder.putMetadata("cartJson", productRequest.getCartJson());
            }

            // Create session
            Session session = Session.create(sessionBuilder.build());

            // Return Stripe response
            return StripeResponse.builder()
                    .status("SUCCESS")
                    .message("Payment session created")
                    .sessionId(session.getId())
                    .sessionUrl(session.getUrl())
                    .build();

        } catch (StripeException e) {
            e.printStackTrace();
            return StripeResponse.builder()
                    .status("FAILED")
                    .message("Error creating Stripe session: " + e.getMessage())
                    .build();
        }
    }
}
