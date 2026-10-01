import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ShopServiceTest {
    private final ProductRepo productRepo = new ProductRepo();
    private final OrderRepo orderRepo = new OrderListRepo();
    private final ShopService service = new ShopService(productRepo, orderRepo);

    private final Product product = new Product(1, "Maus", new BigDecimal("19.90"));

    @Test
    void placeOrder_ShouldPlaceOrder() {
        productRepo.addProduct(product);
        service.placeOrder(1, Map.of(1, 2));
        Order order = orderRepo.getOrderById(1);

        assertThat(order).isNotNull();
        assertThat(order.products()).containsEntry(product, 2);
        assertThat(order.totalPrice()).isEqualByComparingTo("39.80");
    }

    @Test
    void placeOrder_ShouldNotPlaceOrder_WennUnknown() {
        service.placeOrder(1, Map.of(999, 2));
        assertThat(orderRepo.getAllOrders()).isEmpty();
    }

    @Test
    void placeOrder_ShouldNotPlaceOrder_WennQuantityIsZero() {
        productRepo.addProduct(product);
        service.placeOrder(1, Map.of(1, 0));
        assertThat(orderRepo.getAllOrders()).isEmpty();
    }

    @Test
    void changeQuantity_ShouldChangeQuantity() {
        productRepo.addProduct(product);
        service.placeOrder(1, Map.of(1, 2));
        service.changeQuantity(1, 1, 5);

        Order order = orderRepo.getOrderById(1);
        assertThat(order.products()).containsEntry(product, 5);
        assertThat(orderRepo.getAllOrders()).hasSize(1);
    }
}