import java.util.*;

class DuplicateIdException extends Exception {
    public DuplicateIdException(String message) {
        super(message);
    }
}

class InvalidPriceException extends Exception {
    public InvalidPriceException(String message) {
        super(message);
    }
}

class NonRefundableException extends Exception {
    public NonRefundableException(String message) {
        super(message);
    }
}

class NotFoundException extends Exception {
    public NotFoundException(String message) {
        super(message);
    }
}

interface Deliverable {
    void deliver();
}

interface Refundable {
    void refund() throws NonRefundableException;
}

interface Payment {
    void pay(Order order);
}

abstract class Product implements Deliverable {
    protected String id;
    protected String name;
    protected double price;

    public Product(String id, String name, double price) throws InvalidPriceException {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %.2f VND", id, name, price);
    }

    @Override
    public abstract void deliver();
}

class Book extends Product implements Refundable {
    private String author;

    public Book(String id, String name, double price, String author) throws InvalidPriceException {
        super(id, name, price);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public void deliver() {
        System.out.println("Giao sach '" + name + "' cua tac gia " + author);
    }

    @Override
    public void refund() throws NonRefundableException {
        System.out.println("Hoan tien sach '" + name + "' thanh cong!");
    }

    @Override
    public String toString() {
        return super.toString() + " - Tác giả: " + author;
    }
}

class Phone extends Product implements Refundable {
    private String brand;

    public Phone(String id, String name, double price, String brand) throws InvalidPriceException {
        super(id, name, price);
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    @Override
    public void deliver() {
        System.out.println("Giao dien thoai " + brand + " " + name);
    }

    @Override
    public void refund() throws NonRefundableException {
        System.out.println("Hoan tien dien thoai '" + name + "' thanh cong!");
    }

    @Override
    public String toString() {
        return super.toString() + " - Thương hiệu: " + brand;
    }
}

class Laptop extends Product implements Refundable {
    private String brand;

    public Laptop(String id, String name, double price, String brand) throws InvalidPriceException {
        super(id, name, price);
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    @Override
    public void deliver() {
        System.out.println("Giao laptop " + brand + " " + name);
    }

    @Override
    public void refund() throws NonRefundableException {
        throw new NonRefundableException("Laptop '" + name + "' khong the hoan tien!");
    }

    @Override
    public String toString() {
        return super.toString() + " - Thương hiệu: " + brand;
    }
}

class Customer {
    private String id;
    private String name;
    private String email;

    public Customer(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s", id, name, email);
    }
}

class Order {
    private String id;
    private Customer customer;
    private List<Product> products;
    private double totalAmount;

    public Order(String id, Customer customer) {
        this.id = id;
        this.customer = customer;
        this.products = new ArrayList<>();
        this.totalAmount = 0;
    }

    public void addProduct(Product product) {
        products.add(product);
        totalAmount += product.getPrice();
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Đơn hàng ").append(id).append(" ===\n");
        sb.append("Khách hàng: ").append(customer.getName()).append("\n");
        sb.append("Sản phẩm:\n");
        for (Product p : products) {
            sb.append("  - ").append(p.toString()).append("\n");
        }
        sb.append(String.format("Tổng tiền: %.2f VND", totalAmount));
        return sb.toString();
    }
}

class CreditCardPayment implements Payment {
    private String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(Order order) {
        System.out.println("Thanh toan bang the tin dung **** " + cardNumber.substring(cardNumber.length() - 4));
        System.out.printf("So tien: %.2f VND - Don hang: %s\n", order.getTotalAmount(), order.getId());
    }
}

class PaypalPayment implements Payment {
    private String email;

    public PaypalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(Order order) {
        System.out.println("Thanh toan qua PayPal: " + email);
        System.out.printf("So tien: %.2f VND - Don hang: %s\n", order.getTotalAmount(), order.getId());
    }
}

class CashPayment implements Payment {
    @Override
    public void pay(Order order) {
        System.out.println("Thanh toan bang tien mat");
        System.out.printf("So tien: %.2f VND - Don hang: %s\n", order.getTotalAmount(), order.getId());
    }
}

class MoMoPayment implements Payment {
    private String phoneNumber;

    public MoMoPayment(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void pay(Order order) {
        System.out.println("Thanh toan qua MoMo: " + phoneNumber);
        System.out.printf("So tien: %.2f VND - Don hang: %s\n", order.getTotalAmount(), order.getId());
    }
}

class Repository<T> {
    protected Map<String, T> data;

    public Repository() {
        this.data = new HashMap<>();
    }

    public void add(T item, String id) throws DuplicateIdException {
        if (data.containsKey(id)) {
            throw new DuplicateIdException("ID đã tồn tại: " + id);
        }
        data.put(id, item);
    }

    public void update(T item, String id) throws NotFoundException {
        if (!data.containsKey(id)) {
            throw new NotFoundException("Không tìm thấy ID: " + id);
        }
        data.put(id, item);
    }

    public void delete(String id) throws NotFoundException {
        if (!data.containsKey(id)) {
            throw new NotFoundException("Không tìm thấy ID: " + id);
        }
        data.remove(id);
    }

    public List<T> findAll() {
        return new ArrayList<>(data.values());
    }

    public T findById(String id) {
        return data.get(id);
    }
}

class ProductRepository extends Repository<Product> {
    public void add(Product product) throws DuplicateIdException {
        super.add(product, product.getId());
    }

    public void update(Product product) throws NotFoundException {
        super.update(product, product.getId());
    }
}

class CustomerRepository extends Repository<Customer> {
    public void add(Customer customer) throws DuplicateIdException {
        super.add(customer, customer.getId());
    }

    public void update(Customer customer) throws NotFoundException {
        super.update(customer, customer.getId());
    }
}

class OrderRepository extends Repository<Order> {
    public void add(Order order) throws DuplicateIdException {
        super.add(order, order.getId());
    }

    public void update(Order order) throws NotFoundException {
        super.update(order, order.getId());
    }
}

public class BT_19_11 {
    public static void main(String[] args) {
        System.out.println("=== QUAN LY CUA HANG TRUC TUYEN ===\n");

        ProductRepository productRepo = new ProductRepository();
        CustomerRepository customerRepo = new CustomerRepository();
        OrderRepository orderRepo = new OrderRepository();

        try {
            System.out.println("1. THEM SAN PHAM");
            Product book1 = new Book("B001", "Clean Code", 250000, "Robert C. Martin");
            Product book2 = new Book("B002", "Effective Java", 300000, "Joshua Bloch");
            Product phone1 = new Phone("P001", "iPhone 15 Pro", 28000000, "Apple");
            Product phone2 = new Phone("P002", "Galaxy S24", 22000000, "Samsung");
            Product laptop1 = new Laptop("L001", "MacBook Pro M3", 45000000, "Apple");

            productRepo.add(book1);
            productRepo.add(book2);
            productRepo.add(phone1);
            productRepo.add(phone2);
            productRepo.add(laptop1);
            System.out.println("Da them 5 san pham\n");

            System.out.println("2. DANH SACH SAN PHAM");
            List<Product> products = productRepo.findAll();
            for (Product p : products) {
                System.out.println(p);
            }
            System.out.println();

            System.out.println("3. THEM KHACH HANG");
            Customer customer1 = new Customer("C001", "Nguyen Van A", "nguyenvana@email.com");
            Customer customer2 = new Customer("C002", "Tran Thi B", "tranthib@email.com");
            customerRepo.add(customer1);
            customerRepo.add(customer2);
            System.out.println("Da them 2 khach hang\n");

            System.out.println("4. TAO DON HANG");
            Order order1 = new Order("ORD001", customer1);
            order1.addProduct(book1);
            order1.addProduct(phone1);
            orderRepo.add(order1);

            Order order2 = new Order("ORD002", customer2);
            order2.addProduct(laptop1);
            order2.addProduct(book2);
            orderRepo.add(order2);

            System.out.println(order1);
            System.out.println("\n" + order2);
            System.out.println();

            System.out.println("5. GIAO HANG");
            for (Product p : order1.getProducts()) {
                p.deliver();
            }
            System.out.println();

            System.out.println("6. HOAN TIEN");
            try {
                if (book1 instanceof Refundable) {
                    ((Refundable) book1).refund();
                }
            } catch (NonRefundableException e) {
                System.out.println(e.getMessage());
            }

            try {
                if (phone1 instanceof Refundable) {
                    ((Refundable) phone1).refund();
                }
            } catch (NonRefundableException e) {
                System.out.println(e.getMessage());
            }

            try {
                if (laptop1 instanceof Refundable) {
                    ((Refundable) laptop1).refund();
                }
            } catch (NonRefundableException e) {
                System.out.println(e.getMessage());
            }
            System.out.println();

            System.out.println("7. THANH TOAN DA KENH");
            Payment creditCard = new CreditCardPayment("1234567812345678");
            creditCard.pay(order1);
            System.out.println();

            Payment paypal = new PaypalPayment("customer@paypal.com");
            paypal.pay(order2);
            System.out.println();

            Order order3 = new Order("ORD003", customer1);
            order3.addProduct(phone2);
            Payment cash = new CashPayment();
            cash.pay(order3);
            System.out.println();

            Order order4 = new Order("ORD004", customer2);
            order4.addProduct(book2);
            Payment momo = new MoMoPayment("0987654321");
            momo.pay(order4);
            System.out.println();

            System.out.println("8. XU LY NGOAI LE");
            System.out.println("Test DuplicateIdException:");
            try {
                Product duplicateProduct = new Book("B001", "Duplicate Book", 100000, "Unknown");
                productRepo.add(duplicateProduct);
            } catch (DuplicateIdException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("\nTest InvalidPriceException:");
            try {
                Product invalidProduct = new Book("B999", "Invalid Price Book", -50000, "Unknown");
            } catch (InvalidPriceException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("\nTest NotFoundException:");
            try {
                productRepo.delete("NOTEXIST");
            } catch (NotFoundException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("\nTest NonRefundableException:");
            System.out.println("Da test o phan 6 (Laptop khong the hoan tien)");
            System.out.println();

            System.out.println("9. THONG KE");
            System.out.println("Tong so san pham: " + productRepo.findAll().size());
            System.out.println("Tong so khach hang: " + customerRepo.findAll().size());
            System.out.println("Tong so don hang: " + orderRepo.findAll().size());

            double totalRevenue = 0;
            for (Order order : orderRepo.findAll()) {
                totalRevenue += order.getTotalAmount();
            }
            System.out.printf("Tong doanh thu: %.2f VND\n", totalRevenue);

        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
