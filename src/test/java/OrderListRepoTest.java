import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class OrderListRepoTest {

    private final OrderListRepo orderListRepo = new OrderListRepo();

    private final Product product = new Product(1, "Maus", new BigDecimal("19.90"));

    private final Order firstOrder = new Order(1, Map.of(product, 2));
    private final Order secondOrder = new Order(2, Map.of(product, 3));

    @Test
    void getOrderById_ShouldAddAndFindOrderById() {
        orderListRepo.addOrder(firstOrder);
        orderListRepo.addOrder(secondOrder);

        assertThat(orderListRepo.getOrderById(2)).isEqualTo(secondOrder);
        assertThat(orderListRepo.getAllOrders()).containsExactly(firstOrder, secondOrder);
    }

    @Test
    void getOrderById_ShouldReturnNull_WhenOrderDoesNotExist() {
        orderListRepo.addOrder(firstOrder);
        assertThat(orderListRepo.getOrderById(999)).isNull();
    }

    @Test
    void deleteOrder_ShouldDeleteOnlySelectedOrder() {
        orderListRepo.addOrder(firstOrder);
        orderListRepo.addOrder(secondOrder);

        orderListRepo.deleteOrder(1);

        assertThat(orderListRepo.getAllOrders()).containsExactly(secondOrder);
        assertThat(orderListRepo.getOrderById(1)).isNull();
    }

    @Test
    void deleteOrder_shouldKeepOrders_WhenIdDoesNotExist() {
        orderListRepo.addOrder(firstOrder);

        orderListRepo.deleteOrder(999);

        assertThat(orderListRepo.getAllOrders()).containsExactly(firstOrder);
    }

    @Test
    void addOrder_ShouldReplaceOrderWithSameId() {
        orderListRepo.addOrder(firstOrder);
        orderListRepo.addOrder(secondOrder);
        Order updatedOrder = new Order(1, Map.of(product, 5));

        orderListRepo.addOrder(updatedOrder);

        assertThat(orderListRepo.getOrderById(1)).isEqualTo(updatedOrder);
        assertThat(orderListRepo.getAllOrders()).containsExactlyInAnyOrder(updatedOrder, secondOrder);
    }

    @Test
    void getAllOrders_shouldReturnEmptyList() {
        assertThat(orderListRepo.getAllOrders()).isEmpty();
    }

    @Test
    void getAllOrders_ShouldReturnCopyOfOrderList() {
        orderListRepo.addOrder(firstOrder);

        List<Order> result = orderListRepo.getAllOrders();
        result.clear();

        assertThat(orderListRepo.getAllOrders()).containsExactly(firstOrder);
    }
}