import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class ProductRepoTest {

    private final ProductRepo productRepo = new ProductRepo();

    private final Product keyboard = new Product(1, "Tastatur", new BigDecimal("49.90"));
    private final Product mouse = new Product(2, "Maus", new BigDecimal("19.90"));

    @Test
    void getProductById_ShouldAddAndReturnProductById() {
        productRepo.addProduct(keyboard);
        productRepo.addProduct(mouse);

        assertThat(productRepo.getProductById(2)).isEqualTo(mouse);
        assertThat(productRepo.getAllProducts()).containsExactly(keyboard, mouse);
    }

    @Test
    void getProductById_ShouldReturnNull_WhenProductDoesNotExist() {
        productRepo.addProduct(keyboard);
        assertThat(productRepo.getProductById(999)).isNull();
    }

    @Test
    void deleteProduct_ShouldDeleteOnlySelectedProduct() {
        productRepo.addProduct(keyboard);
        productRepo.addProduct(mouse);

        productRepo.deleteProduct(keyboard);

        assertThat(productRepo.getAllProducts()).containsExactly(mouse);
        assertThat(productRepo.getProductById(1)).isNull();
    }

    @Test
    void getAllProducts_ShouldReturnEmptyList() {
        assertThat(productRepo.getAllProducts()).isEmpty();
    }

    @Test
    void getAllProducts_ShouldReturnCopyOfProductList() {
        productRepo.addProduct(keyboard);

        List<Product> result = productRepo.getAllProducts();
        result.clear();

        assertThat(productRepo.getAllProducts()).containsExactly(keyboard);
    }
}