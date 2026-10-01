import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductRepo products = new ProductRepo();
        OrderRepo orders = new OrderListRepo();
        ShopService shop = new ShopService(products, orders);

        while (true) {
            System.out.println("""
                    
                    1. Produkt hinzufügen
                    2. Produkte anzeigen
                    3. Produkt suchen
                    4. Produkt löschen
                    5. Bestellung aufgeben
                    6. Bestellungen anzeigen
                    7. Bestellung suchen
                    8. Bestellung löschen
                    9. Menge ändern
                    0. Beenden
                    """);

            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 0) {
                break;
            }

            switch (choice) {
                case 1 -> {
                    System.out.println("Produkt-ID:");
                    int id = Integer.parseInt(scanner.nextLine());

                    System.out.println("Name:");
                    String name = scanner.nextLine();

                    System.out.println("Preis:");
                    BigDecimal price = new BigDecimal(scanner.nextLine());

                    products.addProduct(new Product(id, name, price));
                }
                case 2 -> {
                    for (Product product : products.getAllProducts()) {
                        System.out.println(product);
                    }
                }
                case 3 -> {
                    System.out.println("Produkt-ID:");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.println(products.getProductById(id));
                }
                case 4 -> {
                    System.out.println("Produkt-ID:");
                    int id = Integer.parseInt(scanner.nextLine());
                    products.deleteProduct(products.getProductById(id));
                }
                case 5 -> {
                    System.out.println("Bestell-ID:");
                    int id = Integer.parseInt(scanner.nextLine());

                    Map<Integer, Integer> quantities = new HashMap<>();
                    String more;

                    do {
                        System.out.println("Produkt-ID:");
                        int productId = Integer.parseInt(scanner.nextLine());

                        System.out.println("Menge:");
                        int quantity = Integer.parseInt(scanner.nextLine());

                        quantities.put(productId, quantity);

                        System.out.println("Weiteres Produkt? (j/n)");
                        more = scanner.nextLine();
                    } while (more.equalsIgnoreCase("j"));

                    shop.placeOrder(id, quantities);
                }
                case 6 -> {
                    for (Order order : orders.getAllOrders()) {
                        System.out.println(order);
                        System.out.println("Summe: " + order.totalPrice());
                    }
                }
                case 7 -> {
                    System.out.println("Bestell-ID:");
                    int id = Integer.parseInt(scanner.nextLine());
                    Order order = orders.getOrderById(id);

                    if (order != null) {
                        System.out.println(order);
                        System.out.println("Summe: " + order.totalPrice());
                    } else {
                        System.out.println("Bestellung nicht gefunden.");
                    }
                }
                case 8 -> {
                    System.out.println("Bestell-ID:");
                    int id = Integer.parseInt(scanner.nextLine());
                    orders.deleteOrder(id);
                }
                case 9 -> {
                    System.out.println("Bestell-ID:");
                    int id = Integer.parseInt(scanner.nextLine());

                    System.out.println("Produkt-ID:");
                    int productId = Integer.parseInt(scanner.nextLine());

                    System.out.println("Neue Menge:");
                    int quantity = Integer.parseInt(scanner.nextLine());

                    shop.changeQuantity(id, productId, quantity);
                }
                default -> System.out.println("Ungültige Auswahl.");
            }
        }

        scanner.close();
    }
}
