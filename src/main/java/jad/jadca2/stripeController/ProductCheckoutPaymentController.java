package jad.jadca2.stripeController;

import jad.jadca2.stripeDto.ProductRequest;
import jad.jadca2.stripeDto.StripeResponse;
import jad.jadca2.stripeService.StripeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:8080")
public class ProductCheckoutPaymentController {


    private StripeService stripeService;

    public ProductCheckoutPaymentController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/checkout_payment")
    public ResponseEntity<StripeResponse> checkoutProducts(@RequestBody ProductRequest productRequest) {
        StripeResponse stripeResponse = stripeService.checkoutProducts(productRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stripeResponse);
    }
}