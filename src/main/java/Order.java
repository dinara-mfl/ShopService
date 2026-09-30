import java.math.BigDecimal;
import java.util.Map;

public record Order(int id, Map<Product, Integer> products) {

    public BigDecimal totalPrice() {
        BigDecimal total = BigDecimal.ZERO;

        for (Product product : products.keySet()) {
            int quantity = products.get(product);
            BigDecimal subtotal = product.price().multiply(BigDecimal.valueOf(quantity));
            total = total.add(subtotal);
        }

        return total;
    }
}