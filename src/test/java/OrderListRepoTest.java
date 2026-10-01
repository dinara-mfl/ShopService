import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class OrderListRepoTest {

    private final OrderListRepo repo = new OrderListRepo();

    private final Product product = new Product(1, "Maus", new BigDecimal("19.90"));

    private final Order firstOrder = new Order(1, Map.of(product, 2));
    private final Order secondOrder = new Order(2, Map.of(product, 3));

    @Test
    void getOrderById_ShouldAddAndFindOrderById() {
        repo.addOrder(firstOrder);
        repo.addOrder(secondOrder);

        assertThat(repo.getOrderById(2)).isEqualTo(secondOrder);
        assertThat(repo.getAllOrders()).containsExactly(firstOrder, secondOrder);
    }

    @Test
    void getOrderById_ShouldReturnNull_WhenOrderDoesNotExist() {
        repo.addOrder(firstOrder);
        assertThat(repo.getOrderById(999)).isNull();
    }

    @Test
    void deleteOrder_ShouldDeleteOnlySelectedOrder() {
        repo.addOrder(firstOrder);
        repo.addOrder(secondOrder);

        repo.deleteOrder(1);

        assertThat(repo.getAllOrders()).containsExactly(secondOrder);
        assertThat(repo.getOrderById(1)).isNull();
    }

    @Test
    void deleteOrder_shouldKeepOrders_WhenIdDoesNotExist() {
        repo.addOrder(firstOrder);

        repo.deleteOrder(999);

        assertThat(repo.getAllOrders()).containsExactly(firstOrder);
    }

    @Test
    void addOrder_ShouldReplaceOrderWithSameId() {
        repo.addOrder(firstOrder);
        repo.addOrder(secondOrder);
        Order updatedOrder = new Order(1, Map.of(product, 5));

        repo.addOrder(updatedOrder);

        assertThat(repo.getOrderById(1)).isEqualTo(updatedOrder);
        assertThat(repo.getAllOrders()).containsExactlyInAnyOrder(updatedOrder, secondOrder);
    }

    @Test
    void getAllOrders_shouldReturnEmptyList() {
        assertThat(repo.getAllOrders()).isEmpty();
    }

    @Test
    void getAllOrders_ShouldReturnCopyOfOrderList() {
        repo.addOrder(firstOrder);

        List<Order> result = repo.getAllOrders();
        result.clear();

        assertThat(repo.getAllOrders()).containsExactly(firstOrder);
    }
}