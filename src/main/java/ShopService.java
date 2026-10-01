import java.util.HashMap;
import java.util.Map;

public class ShopService {
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;


    public ShopService(ProductRepo productRepo, OrderRepo orderRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    public void placeOrder(int orderId, Map<Integer, Integer> quantities) {
        Map<Product, Integer> products = new HashMap<>();

        for (int productId : quantities.keySet()) {
            int quantity = quantities.get(productId);

            if (quantity <= 0) {
                System.out.println("Die Menge muss größer als 0 sein.");
                return;
            }

            Product product = productRepo.getProductById(productId);

            if (product == null) {
                System.out.println("Produkt existiert nicht: " + productId);
                return;
            }

            products.put(product, quantity);
        }

        orderRepo.addOrder(new Order(orderId, products));
    }

    public void changeQuantity(int orderId, int productId, int quantity) {
        if (quantity <= 0) {
            System.out.println("Die Menge muss größer als 0 sein.");
            return;
        }

        Order order = orderRepo.getOrderById(orderId);

        if (order == null) {
            System.out.println("Bestellung existiert nicht.");
            return;
        }

        Map<Product, Integer> products = new HashMap<>(order.products());

        for (Product product : products.keySet()) {
            if (product.id() == productId) {
                products.put(product, quantity);
                orderRepo.addOrder(new Order(orderId, products));
                return;
            }
        }

        System.out.println("Produkt ist nicht in der Bestellung.");
    }
}