package view;

import model.Order;
import model.OrderItem;
import model.Product;
import severies.*;
import utils.AppUtils;
import utils.ValidateUtils;

import java.util.ArrayList;
import java.util.Arrays;
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
        ArrayList<OrderItem> orderItemArrays = new ArrayList<>();
        ProductView productView = new ProductView();
        try {
            orderService.findAll();
            long orderId = System.currentTimeMillis() / 1000;
            System.out.print("NHẬP TÊN KHÁCH HÀNG: ");
            String name = sc.nextLine();
            while (!ValidateUtils.isNameValid(name)) {
                System.out.println("TÊN PHẢI VIẾT CHỮ CÁI HOA ĐẦU TIÊN VÀ VIẾT KHÔNG DẤU");
                System.out.print("NHẬP TÊN KHÁCH HÀNG: ");
                name = sc.nextLine();
            }
            System.out.print("NHẬP SỐ ĐIỆN THOẠI : ");
            String phone = sc.nextLine();
            while (!ValidateUtils.isPhoneValid(phone)) {
                System.out.println("SỐ CỦA BẠN KHÔNG ĐÚNG ĐỊNH DẠNG (BẮT ĐẦU LÀ SỐ 0, VÀ ĐỦ 10 SỐ)");
                System.out.print("NHẬP SỐ ĐIỆN THOẠI : ");
                phone = sc.nextLine();
            }
            System.out.print("NHẬP ĐỊA CHỈ : ");
            String address = sc.nextLine();
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
            orderItemArrays.add(orderItem);
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
                        addOrderMore(orderId, name, phone, address, orderItemArrays);
                        break;
                    case "2":
                        showPaymentInfo(orderItemArrays, order);
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

    public void addOrderMore(long orderId, String name, String phone, String address, ArrayList<OrderItem> orderItemArrayList) {
        ProductView productView = new ProductView();
        productView.showProductsSub();
        try {
            OrderItem orderItem = addOrderItems(orderId);
            Order order = new Order(orderId, name, phone, address);
            oderItemService.add(orderItem);
            orderService.add(order);
            orderItemArrayList.add(orderItem);
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
//                        addOrderItems(System.currentTimeMillis() / 1000);
                        addOrderMore(orderId, name, phone, address, orderItemArrayList);
                        break;
                    case "2":
                        showPaymentInfo(orderItemArrayList, order);
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

        String choice;
        do {
            System.out.print("CHỌN CHỨC NĂNG : ");
            choice = sc.nextLine();
            switch (choice) {
                case "1":
                    addOrder();
                    break;
                case "2":
                    showAllOrder();
                    break;
                case "3":
                    ProductView productView = new ProductView();
                    productView.showProducts(InputOption.SHOW);
                    orderMenu();
                    break;
                case "4":
                    MainLauncher mainLauncher = new MainLauncher();
                    mainLauncher.mainMenu();
                    break;
                case "0":
                    AppUtils.exit();
                    break;
                default:
                    System.out.println("NHẬP KHÔNG ĐÚNG, XIN NHẬP LẠI");
                    break;
            }
        } while (true);
    }

    public void showAllOrder() {
        List<Order> orders = orderService.findAll();
        List<OrderItem> orderItems = oderItemService.findAll();
        OrderItem newOrderItem = new OrderItem();
        double a = 0;
        try {
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-17s %-20s %-20s %-15s %-25s %-17s %-20s\n",
                    " ID",
                    "TÊN KHÁCH HÀNG",
                    "SỐ ĐIỆN THOẠI",
                    "ĐỊA CHỈ",
                    "TÊN SẢN PHẨM",
                    "SỐ LƯỢNG",
                    "GIÁ",
                    "THÀNH TIỀN"
            );
            for (Order order : orders) {
                for (OrderItem orderItem : orderItems) {
                    if (orderItem.getOrderId() == order.getId()) {
                        newOrderItem = orderItem;
                        break;
                    }
                }
                System.out.printf("%-17s %-20s %-20s %-15s %-25s %-17s %-20s\n",
                        "--【" + order.getId() + "】",
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
            System.out.println("-- ══════════════════════════════════════════");
            System.out.println("-- TỔNG TIỀN :  " + AppUtils.doubleToVND(a));
            System.out.println("-- ══════════════════════════════════════════");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
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

    public void showPaymentInfo(ArrayList<OrderItem> orderItems, Order order) {
        double total = 0;
        double a = 0;
        try {

            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-17s %-20s %-20s %-30s %-15s %-17s %-15s %-15s\n",
                    " ID",
                    "TÊN KHÁCH HÀNG",
                    "SỐ ĐIỆN THOẠI",
                    "ĐỊA CHỈ",
                    "TÊN SẢN PHẨM",
                    "SỐ LƯỢNG",
                    "GIÁ",
                    "THÀNH TIỀN"
            );
            for (OrderItem orderItem : orderItems) {
                System.out.printf("%-17s %-20s %-20s %-30s %-15s %-17s %-15s %-15s\n",
                        "--【" + order.getId() + "】",
                        order.getFullName(),
                        order.getMobile(),
                        order.getAddress(),
                        orderItem.getProductName(),
                        orderItem.getQuantity(),
                        AppUtils.doubleToVND(orderItem.getPrice()),
                        AppUtils.doubleToVND(orderItem.getTotal())
                );
                total += orderItem.getTotal();
                System.out.println("\t\t\t\t TỔNG TIỀN CỦA HÓA ĐƠN: " + AppUtils.doubleToVND(total));
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
            }
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
