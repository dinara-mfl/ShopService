import java.util.ArrayList;
import java.util.List;

public class ProductRepo {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public void deleteProduct(Product product) {
        products.remove(product);
    }

    public Product getProductsById(int id) {
        for (Product product: products) {
            if (product.id() == id) {
                return product;
            }
        }
        return null;
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }
}
