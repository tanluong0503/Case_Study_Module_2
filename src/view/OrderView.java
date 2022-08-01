package view;

import model.Order;
import model.OrderItem;
import model.Product;
import severies.*;
import utils.AppUtils;
import utils.ValidateUtils;

import java.util.List;
import java.util.Scanner;

public class OrderView {
    Scanner sc = new Scanner(System.in);
    private final IProductService productService;
    private final IOrderService orderService;
    private final IOderItemService oderItemService;


    public OrderView() {
        productService = ProductService.getInstance();
        orderService = OrderService.getInstance();
        oderItemService = OrderItemService.getInstance();
    }

    public boolean checkQualityBakery(Product product, int quantity) {
        if (quantity <= product.getQuantity())
            return true;
        else
            return false;
    }

    public OrderItem addOrderItems(long orderId) {
        oderItemService.findAll();
        ProductView productView = new ProductView();
        productView.showProducts(InputOption.ADD);
        long id = System.currentTimeMillis() / 1000;
        System.out.print("NHẬP ID SẢN PHẨM CẦN MUA : ");
        int bakeryId = Integer.parseInt(sc.nextLine());

        while (!productService.existsById(bakeryId)) {
            System.out.println("ID SẢN PHẨM NÀY KHÔNG TỒN TẠI");
            System.out.print("NHẬP ID SẢN PHẨM CẦN MUA : ");
            bakeryId = Integer.parseInt(sc.nextLine());
        }
        Product product = productService.findById(bakeryId);

        double price = product.getPrice();
        System.out.print("NHẬP SỐ LƯỢNG : ");
        int quantity = Integer.parseInt(sc.nextLine());
        do {
            if (quantity <= 0) {
                System.out.println("SỐ LƯỢNG PHẢI LỚN HƠN 0");
                System.out.print("NHẬP SỐ LƯỢNG : ");
                quantity = Integer.parseInt(sc.nextLine());
            }
        } while (quantity <= 0);

        while (!checkQualityBakery(product, quantity)) {
            System.out.println("SỐ LƯỢNG KHÔNG ĐỦ, XIN NHẬP LẠI");
            System.out.print("NHẬP SỐ LƯỢNG : ");
            quantity = Integer.parseInt(sc.nextLine());
        }
        String bakeryName = product.getTitle();
        double total = quantity * price;
        product.setQuantity(product.getQuantity() - quantity);
        productService.update(product);
        OrderItem orderItem = new OrderItem(id, price, quantity, orderId, bakeryId, bakeryName, total);
        return orderItem;
    }

    public void addOrder() {
        ProductView productView = new ProductView();
        productView.showProductsSub();
        try {
            orderService.findAll();
            long orderId = System.currentTimeMillis() / 1000;
            System.out.print("NHẬP TÊN : ");
            String name = sc.nextLine();
            while (!ValidateUtils.isNameValid(name)) {
                System.out.println("TÊN PHẢI VIẾT CHỮ CÁI HOA ĐẦU TIÊN VÀ VIẾT KHÔNG DẤU");
                System.out.print("NHẬP TÊN : ");
                name = sc.nextLine();
            }
            System.out.print("NHẬP SỐ ĐIỆN THOẠI : ");
            String phone = sc.nextLine();
            while (!ValidateUtils.isPhoneValid(phone)) {
                System.out.println("SỐ CỦA BẠN KHÔNG ĐÚNG ĐỊNH DẠNG (BẮT ĐẦU LÀ SỐ 0, VÀ ĐỦ 10 SỐ)");
                System.out.print("NHẬP SỐ ĐIỆN THOẠI : ");
                phone = sc.nextLine();
            }
            String address;
            System.out.print("NHẬP ĐỊA CHỈ : ");
            address = sc.nextLine();
            do {
                if (address.trim().isEmpty()) {
                    System.out.println("ĐỊA CHỈ KHÔNG ĐƯỢC BỎ TRỐNG, XIN NHẬP NGHIÊM TÚC");
                    System.out.print("NHẬP ĐỊA CHỈ : ");
                    address = sc.nextLine();
                }
            } while (address.trim().isEmpty());
            OrderItem orderItem = addOrderItems(orderId);
            Order order = new Order(orderId, name, phone, address);
            oderItemService.add(orderItem);
            orderService.add(order);
            System.out.println("TẠO ĐƠN HÀNG THÀNH CÔNG");
            do {
                System.out.println("\t----------------------------------------------------------");
                System.out.println("\t--░░░░░░░░░░░░░░░░░░░░[QUẢN LÍ HÓA ĐƠN]░░░░░░░░░░░░░░░░░--");
                System.out.println("\t----------------------------------------------------------");
                System.out.println("\t--                                                      --");
                System.out.println("\t--               【1】. TẠO ĐƠN HÀNG TIẾP                --");
                System.out.println("\t--               【2】. IN HÓA ĐƠN                       --");
                System.out.println("\t--               【3】. QUAY LẠI                         --");
                System.out.println("\t--               【4】. THOÁT                            --");
                System.out.println("\t--                                                      --");
                System.out.println("\t----------------------------------------------------------");
                System.out.print("CHỌN SỐ : ");
                String choice = sc.nextLine();
                switch (choice) {
                    case "1":
                        addOrderItems(System.currentTimeMillis() / 1000);
                        addOrder();
                        break;
                    case "2":
                        showPaymentInfo(orderItem, order);
                        break;
                    case "3":
                        orderMenu();
                        break;
                    case "4":
                        AppUtils.exit();
                        break;
                    default:
                        System.out.println("NHẬP KHÔNG ĐÚNG, XIN NHẬP LẠI");
                }
            } while (true);
        } catch (Exception e) {
            System.out.println("NHẬP SAI, XIN NHẬP LẠI");
        }
    }

    public void orderMenu() {
        System.out.println("\t----------------------------------------------------------");
        System.out.println("\t--░░░░░░░░░░░░░░░░░░░░[QUẢN LÍ HÓA ĐƠN]░░░░░░░░░░░░░░░░░--");
        System.out.println("\t----------------------------------------------------------");
        System.out.println("\t--                                                      --");
        System.out.println("\t--               【1】. TẠO ĐƠN HÀNG                     --");
        System.out.println("\t--               【2】. DANH SÁCH ĐƠN HÀNG               --");
        System.out.println("\t--               【3】. DANH SÁCH SẢN PHẨM               --");
        System.out.println("\t--               【4】. QUAY LẠI                         --");
        System.out.println("\t--               【0】. THOÁT                            --");
        System.out.println("\t--                                                      --");
        System.out.println("\t----------------------------------------------------------");
        System.out.print("CHỌN SỐ : ");

        int number = Integer.parseInt(sc.nextLine());
        switch (number) {
            case 1:
                addOrder();
                break;
            case 2:
                showAllOrder();
                break;
            case 3:
                ProductView productView = new ProductView();
                productView.showProducts(InputOption.SHOW);
                orderMenu();
                break;
            case 4:
                MainLauncher mainLauncher = new MainLauncher();
                mainLauncher.mainMenu();
                break;
            case 0:
                AppUtils.exit();
            default:
                System.out.println("NHẬP KHÔNG ĐÚNG, XIN NHẬP LẠI");
        }
    }

    public void showAllOrder() {
        List<Order> orders = orderService.findAll();
        List<OrderItem> orderItems = oderItemService.findAll();
        OrderItem newOrderItem = new OrderItem();
        double a = 0;
        try {
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.printf("%-30s %-30s %-30s %-30s %-30s %-30s %-30s %-30s\n",
                    "--        『 ID 』",
                    "『 TÊN KHÁCH HÀNG 』 ",
                    "『 SỐ ĐIỆN THOẠI 』",
                    "『 ĐỊA CHỈ 』",
                    "『 TÊN SẢN PHẨM 』",
                    "『 SỐ LƯỢNG 』",
                    "『 GIÁ 』",
                    "『 THÀNH TIỀN 』"
            );
            for (Order order : orders) {
                for (OrderItem orderItem : orderItems) {
                    if (orderItem.getOrderId() == order.getId()) {
                        newOrderItem = orderItem;
                        break;
                    }
                }
                System.out.printf("%-30s %-30s %-30s %-30s %-30s %-30s %-30s %-30s\n",
                        "--\t\t【" + order.getId() + "】",
                        order.getFullName(),
                        order.getMobile(),
                        order.getAddress(),
                        newOrderItem.getProductName(),
                        newOrderItem.getQuantity(),
                        AppUtils.doubleToVND(newOrderItem.getPrice()),
                        AppUtils.doubleToVND(newOrderItem.getTotal())
                );
                AppUtils.doubleToVND(a += newOrderItem.getTotal());
            }
            System.out.println("-- TỔNG TIỀN :  " + AppUtils.doubleToVND(a));

            System.out.println("---------------------------------------------------------------------------------------");
            boolean is = true;
            do {
                System.out.println("NHẤN 1 ĐỂ QUAY LẠI \t|\t NHẤN 0 ĐỂ THOÁT CHƯƠNG TRÌNH");
                System.out.print("░░░ ");
                String choice = sc.nextLine();
                switch (choice) {
                    case "1":
                        orderMenu();
                        break;
                    case "0":
                        AppUtils.exit();
                        break;
                    default:
                        System.out.println("NHẬP SAI, XIN NHẬP LẠI");
                        is = false;
                }
            } while (!is);
        } catch (Exception e) {
            System.out.println("NHẬP SAI, XIN NHẬP LẠI");
        }
    }

    public void showPaymentInfo(OrderItem orderItem, Order order) {
        double a = 0;
        try {

            System.out.println("---------------------------------------------------------------------------------------");
            System.out.printf("%-30s %-30s %-30s %-30s %-30s %-30s %-30s %-30s\n",
                    "--        『 ID 』",
                    "『 TÊN KHÁCH HÀNG 』 ",
                    "『 SỐ ĐIỆN THOẠI 』",
                    "『 ĐỊA CHỈ 』",
                    "『 TÊN SẢN PHẨM 』",
                    "『 SỐ LƯỢNG 』",
                    "『 GIÁ 』",
                    "『 THÀNH TIỀN 』"
            );
            System.out.printf("%-30s %-30s %-30s %-30s %-30s %-30s %-30s %-30s\n",
                    "--\t\t【" + order.getId() + "】",
                    order.getFullName(),
                    order.getMobile(),
                    order.getAddress(),
                    orderItem.getProductName(),
                    orderItem.getQuantity(),
                    AppUtils.doubleToVND(orderItem.getPrice()),
                    AppUtils.doubleToVND(orderItem.getTotal())
            );
            System.out.println("---------------------------------------------------------------------------------------");
            boolean is = true;
            do {
                System.out.println("NHẤN 1 ĐÊ TIẾP TỤC \t|\t NHẤN 2 ĐỂ QUAY LẠI \t|\t NHẤN 0 ĐỂ THOÁT CHƯƠNG TRÌNH");
                System.out.print("░░░ ");
                String choice = sc.nextLine();
                switch (choice) {
                    case "1":
                        addOrder();
                        break;
                    case "2":
                        orderMenu();
                        break;
                    case "0":
                        AppUtils.exit();
                        break;
                    default:
                        System.out.println("NHẬP SAI, XIN NHẬP LẠI");
                        is = false;
                }
            } while (!is);
        } catch (Exception e) {
            System.out.println("NHẬP SAI, XIN NHẬP LẠI");
        }

    }
}
