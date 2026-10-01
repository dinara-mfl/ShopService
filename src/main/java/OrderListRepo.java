import java.util.ArrayList;
import java.util.List;

public class OrderListRepo implements OrderRepo {
    private final List<Order> orders = new ArrayList<>();

    @Override
    public void addOrder(Order order) {
        deleteOrder(order.id());
        orders.add(order);
    }

    @Override
    public void deleteOrder(int id) {
        orders.remove(getOrderById(id));
    }

    @Override
    public Order getOrderById(int id) {
        for (Order order: orders) {
            if (order.id() == id) {
                return order;
            }
        }
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }
}
