public class Main {
    public static void main(String[] args) {
        ProductRepo productRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderMapRepo();

        ShopService shopService = new ShopService(productRepo, orderRepo);
    }
}
