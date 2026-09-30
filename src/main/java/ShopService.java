import java.util.ArrayList;
import java.util.List;

public class ShopService {
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;


    public ShopService(ProductRepo productRepo, OrderRepo orderRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    public void placeOrder(int orderId, List<Integer> productIds) {
        List<Product> orderedProducts = new ArrayList<>();

        for (int productId : productIds) {
            Product product = productRepo.getProductsById(productId);

            if (product == null) {
                System.out.println("Produkt mit ID " + productId + " existiert nicht.");
                return;
            }
            orderedProducts.add(product);
        }

        orderRepo.addOrder(new Order(orderId, orderedProducts));
    }
}