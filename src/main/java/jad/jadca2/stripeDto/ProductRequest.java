package jad.jadca2.stripeDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private Long amount;
    private Long quantity;
    private String name;
    private String currency;
    private String userId; // Added user ID
    private String cartJson; // Added full cart data
}
