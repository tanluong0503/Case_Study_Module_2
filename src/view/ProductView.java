package view;

import model.Product;
import severies.IProductService;
import severies.ProductService;
import utils.AppUtils;
import utils.InstantUtils;

import java.util.List;
import java.util.Scanner;

public class ProductView {
    Scanner sc = new Scanner(System.in);
    private final IProductService productService;

    int number;

    public ProductView() {
        productService = ProductService.getInstance();
    }

    public String inputTitle(InputOption option) {
        switch (option) {
            case ADD:
                System.out.print("NHẬP TÊN SẢN PHẨM : ");
                break;
            case UPDATE:
                System.out.print("NHẬP TÊN MUỐN THAY ĐỔI : ");
                break;
        }
        String title;
        do {
            title = sc.nextLine();
            if (title.trim().isEmpty()) {
                System.out.println("KHÔNG ĐƯỢC BỎ TRỐNG, XIN NHẬP LẠI");
            }

        } while (title.trim().isEmpty());
        return title;
    }

    public int inputQuantity(InputOption option) {
        switch (option) {
            case ADD:
                System.out.print("NHẬP SỐ LƯỢNG : ");
                break;
            case UPDATE:
                System.out.print("NHẬP SỐ LƯỢNG MUỐN THAY ĐỔI : ");
                break;
        }
        int quantity;
        do {
            quantity = AppUtils.retryParseInt();
            if (quantity <= 0) {
                System.out.println("SỐ LƯỢNG PHẢI LƠN HƠN 0 ");
            }
        } while (quantity <= 0);
        return quantity;
    }

    public double inputPrice(InputOption option) {
        switch (option) {
            case ADD:
                System.out.print("NHẬP GIÁ : ");
                break;
            case UPDATE:
                System.out.print("NHẬP GIÁ MUỐN THAY ĐỔI : ");
                break;
        }
        double price;
        do {
            price = AppUtils.retryParseDouble();
            if (price <= 0) {
                System.out.println("GIÁ PHẢI LỚN HƠN 0 ");
            }
        } while (price <= 0);
        return price;
    }

    public Long inputId(InputOption option) {
        long id;
        switch (option) {
            case ADD:
                System.out.println("NHẬP ID");
                break;
            case UPDATE:
                System.out.println("NHẬP ID MUỐN THAY ĐỔI");
                break;
            case DELETE:
                System.out.println("NHẬP ID MUỐN XÓA");
                break;
            case SEARCH:
                System.out.println("NHẬP ID MUỐN TÌM");
                break;
        }
        boolean isRetry = false;
        do {
            id = AppUtils.retryParseInt();
            boolean exist = productService.existsById(id);
            switch (option) {
                case ADD:
                    if (exist) {
                        System.out.println("ID NÀY ĐÃ TỒN TẠI");
                    }
                    isRetry = exist;
                    break;
                case UPDATE:
                    if (!exist) {
                        System.out.println("ID NÀY KHÔNG CÓ TRONG DANH SÁCH CẦN TÌM");
                    }
                    isRetry = !exist;
                    break;
            }

        } while (isRetry);
        return id;
    }

    public void add() {
        do {
            long id = System.currentTimeMillis() / 1000;
            String title = inputTitle(InputOption.ADD);
            int quantity = inputQuantity(InputOption.ADD);
            double price = inputPrice(InputOption.ADD);
            Product product = new Product(id, title, quantity, price);
            productService.add(product);
            System.out.println("ĐÃ THÊM SẢN PHẨM THÀNH CÔNG ");
        } while (AppUtils.isRetry(InputOption.ADD));
    }

    public void update() {
        boolean isRetry;
        do {
            showProducts(InputOption.UPDATE);
            long id = inputId(InputOption.UPDATE);
            System.out.println("\t----------------------------------------------------------");
            System.out.println("\t--░░░░░░░░░░░░░░░░░░░░[THAY ĐỔI SẢN PHẨM]░░░░░░░░░░░░░░░--");
            System.out.println("\t----------------------------------------------------------");
            System.out.println("\t--                                                      --");
            System.out.println("\t--               【1】. THAY ĐỔI SẢN PHẨM                --");
            System.out.println("\t--               【2】. THAY ĐỔI SỐ LƯỢNG                --");
            System.out.println("\t--               【3】. THAY ĐỔI GIÁ                     --");
            System.out.println("\t--               【4】. THAY ĐỔI TẤT CẢ                  --");
            System.out.println("\t--               【5】. QUAY LẠI                         --");
            System.out.println("\t--               【0】. THOÁT CHƯƠNG TRÌNH               --");
            System.out.println("\t--                                                      --");
            System.out.println("\t----------------------------------------------------------");
            System.out.print("░░░░░░░░░░░░░░░░░░░░░░░░░░  CHỌN SỐ : ");
            int option = AppUtils.retryChoose(0, 5);
            Product product = new Product();
            product.setId(id);
            String title;
            int quantity;
            double price;
            switch (option) {
                case 0:
                    AppUtils.exit();
                case 1:
                    title = inputTitle(InputOption.UPDATE);
                    product.setTitle(title);
                    productService.update(product);
                    System.out.println("THAY ĐỔI THÀNH CÔNG");
                    break;
                case 2:
                    quantity = inputQuantity(InputOption.UPDATE);
                    product.setQuantity(quantity);
                    productService.update(product);
                    System.out.println("THAY ĐỔI SỐ LƯỢNG THÀNH CÔNG");
                    break;
                case 3:
                    price = inputPrice(InputOption.UPDATE);
                    product.setPrice(price);
                    productService.update(product);
                    System.out.println("THAY ĐỔI GIÁ THÀNH CÔNG");
                    break;
                case 4:
                    title = inputTitle(InputOption.UPDATE);
                    product.setTitle(title);
                    quantity = inputQuantity(InputOption.UPDATE);
                    product.setQuantity(quantity);
                    price = inputPrice(InputOption.UPDATE);
                    product.setPrice(price);
                    productService.update(product);
                    System.out.println("THAY ĐỔI THÀNH CÔNG");
                case 5:
                    menuProduct();
                    break;
            }
            isRetry = option != 5 && AppUtils.isRetry(InputOption.UPDATE);
        } while (isRetry);
    }

    public void remove() {
        showProducts(InputOption.DELETE);
        long id;
        while (!productService.exists(id = inputId(InputOption.DELETE))) {
            System.out.println("KHÔNG TÌM THẤY ID CẦN XÓA");
            System.out.println("NHẤN 1 ĐÊ TIẾP TỤC \t|\t NHẤN 2 ĐỂ QUAY LẠI \t|\t NHẤN 0 ĐỂ THOÁT CHƯƠNG TRÌNH");
            System.out.print("░░░ ");
            String option = sc.nextLine();
            switch (option) {
                case "1":
                    break;
                case "2":
                    return;
                case "0":
                    AppUtils.exit();
                    break;
                default:
                    System.out.println("CHỌN SAI, XIN CHỌN LẠI");
                    break;
            }
        }
        System.out.println("\t----------------------------------------------------------");
        System.out.println("\t--░░░░░░░░░░░░░░░░░░░░░░░[XÁC NHẬN XÓA]░░░░░░░░░░░░░░░░░--");
        System.out.println("\t----------------------------------------------------------");
        System.out.println("\t--                                                      --");
        System.out.println("\t--               【1】. ĐỒNG Ý XÓA                       --");
        System.out.println("\t--               【2】. QUAY LẠI                         --");
        System.out.println("\t--                                                      --");
        System.out.println("\t----------------------------------------------------------");
        int option = AppUtils.retryChoose(1, 2);
        if (option == 1) {
            productService.deleteById(id);
            System.out.println("XÓA THÀNH CÔNG !");
            AppUtils.isRetry(InputOption.DELETE);
        }
    }

    public void showProductsSort(InputOption option, List<Product> products) {
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.printf("%30s %-30s %-30s %-30s %-30s %-30s ", "--『 ID 』", "『 TÊN SẢN PHẨM 』 ", "『 SỐ LƯỢNG 』", "『 GIÁ 』", "『 THỜI GIAN TẠO 』", "『 THỜI GIAN CẬP NHẬT 』");
        System.out.println();
        for (Product product : products) {
            System.out.printf("%30s %-30s %-30s %-30s %-30s %-30s\n",
                    "--\t\t【" + product.getId() + "】",
                    product.getTitle(),
                    product.getQuantity(),
                    product.getPrice(),
                    product.getTimeNow(),
                    product.getTimeUpdate() == null ? "" : InstantUtils.instantToString(product.getTimeUpdate()));
        }
        System.out.println("-------------------------------------------------------------------------------------------");
        if (option == InputOption.SHOW) {
            AppUtils.isRetry(InputOption.SHOW);
        }
    }

    public void searchId(InputOption option) {
        long id;
        while (!productService.exists(id = inputId(option))) {
            System.out.println("KHÔNG TÌM THẤY ID CẦN TÌM");
            System.out.println("NHẤN 1 ĐÊ TIẾP TỤC \t|\t NHẤN 2 ĐỂ QUAY LẠI \t|\t NHẤN 0 ĐỂ THOÁT CHƯƠNG TRÌNH");
            System.out.print("░░░ ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    break;
                case "2":
                    return;
                case "0":
                    AppUtils.exit();
                    break;
                default:
                    System.out.println("CHỌN SAI, XIN CHỌN LẠI");
                    break;
            }
        }

        for (Product product : productService.findAll()) {
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.printf("%-30s %-30s %-30s %-30s %-30s %-30s\n",
                    "--          『 ID 』",
                    "『 TÊN SẢN PHẨM 』 ",
                    "『 SỐ LƯỢNG 』",
                    "『 GIÁ 』",
                    "『 THỜI GIAN TẠO 』",
                    "『 THỜI GIAN CẬP NHẬT 』");
            if (product.getId() == id) {
                System.out.printf("%-30s %-30s %-30s %-30s %-30s %-30s\n",
                        "--\t\t【" + product.getId() + "】",
                        product.getTitle(),
                        product.getQuantity(),
                        AppUtils.doubleToVND(product.getPrice()),
                        product.getTimeNow(),
                        product.getTimeUpdate() == null ? "" : InstantUtils.instantToString(product.getTimeUpdate()));
                System.out.println("-----------------------------------------------------------------------------------");
                break;
            }
        }
        System.out.println("TÌM KIẾM THÀNH CÔNG");
    }

    public void sortPriceASC() {
        showProductsSort(InputOption.SHOW, productService.sortASC());
    }

    public void sortPriceDESC() {
        showProductsSort(InputOption.SHOW, productService.sortDESC());
    }

    public void showProducts(InputOption option) {
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-30s %-30s %-30s %-30s %-30s %-30s ", "--          『 ID 』", "『 TÊN SẢN PHẨM 』 ", "『 SỐ LƯỢNG 』", "『 GIÁ 』", "『 THỜI GIAN TẠO 』", "『 THỜI GIAN CẬP NHẬT 』");
        System.out.println();
        for (Product product : productService.findAll()) {
            System.out.printf("%-30s %-30s %-30s %-30s %-30s %-30s\n",
                    "--\t\t【" + product.getId() + "】",
                    product.getTitle(),
                    product.getQuantity(),
                    AppUtils.doubleToVND(product.getPrice()),
                    InstantUtils.instantToString(product.getTimeNow()),
                    product.getTimeUpdate() == null ? "" : InstantUtils.instantToString(product.getTimeUpdate()));
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        if (option == InputOption.SHOW) {
            AppUtils.isRetry(InputOption.SHOW);
        }
    }

    public void showProductsSub() {
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-30s %-30s %-30s %-30s %-30s %-30s ", "--          『 ID 』", "『 TÊN SẢN PHẨM 』 ", "『 SỐ LƯỢNG 』", "『 GIÁ 』", "『 THỜI GIAN TẠO 』", "『 THỜI GIAN CẬP NHẬT 』");
        System.out.println();
        for (Product product : productService.findAll()) {
            System.out.printf("%-30s %-30s %-30s %-30s %-30s %-30s\n",
                    "--\t\t【" + product.getId() + "】",
                    product.getTitle(),
                    product.getQuantity(),
                    product.getPrice(),
                    product.getTimeNow(),
                    product.getTimeUpdate() == null ? "" : InstantUtils.instantToString(product.getTimeUpdate()));
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public void menuProduct() {
        System.out.println("\t--------------------------------------------------------------------------------------");
        System.out.println("\t--░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░[DANH SÁCH SẢN PHẨM]░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░--");
        System.out.println("\t--------------------------------------------------------------------------------------");
        System.out.println("\t--                                                                                  --");
        System.out.println("\t--                             【1】. HIỂN THỊ SẢN PHẨM                              --");
        System.out.println("\t--                             【2】. THÊM SẢN PHẨM                                  --");
        System.out.println("\t--                             【3】. SỬA SẢN PHẨM                                   --");
        System.out.println("\t--                             【4】. XÓA SẢN PHẨM                                   --");
        System.out.println("\t--                             【5】. SẮP XẾP TĂNG DẦN THEO GIÁ                      --");
        System.out.println("\t--                             【6】. SẮP XẾP GIẢM DẦN THEO GIÁ                      --");
        System.out.println("\t--                             【7】. TÌM KIẾM THEO ID                               --");
        System.out.println("\t--                             【8】. QUAY LẠI MENU                                  --");
        System.out.println("\t--                             【0】. THOÁT CHƯƠNG TRÌNH                             --");
        System.out.println("\t--                                                                                  --");
        System.out.println("\t--------------------------------------------------------------------------------------");
        do {
            System.out.print("░░░░░░░░░░░░░░░░░░░░░░░░░░  CHỌN SỐ : ");
            number = Integer.parseInt(sc.nextLine());
            switch (number) {
                case 1:
                    showProducts(InputOption.SHOW);
                    menuProduct();
                    break;
                case 2:
                    add();
                    menuProduct();
                    break;
                case 3:
                    update();
                    menuProduct();
                    break;
                case 4:
                    remove();
                    menuProduct();
                    break;
                case 5:
                    sortPriceASC();
                    menuProduct();
                    break;
                case 6:
                    sortPriceDESC();
                    menuProduct();
                    break;
                case 7:
                    searchId(InputOption.SEARCH);
                    menuProduct();
                    break;
                case 8:
                    MainLauncher mainLauncher = new MainLauncher();
                    mainLauncher.mainMenu();
                    break;
                case 0:
                    AppUtils.exit();
                    break;
                default:
                    System.out.println("NHẬP KHÔNG ĐÚNG, XIN NHẬP LẠI");
            }
        } while (number != 0);

    }

//    public static void main(String[] args) {
//        ProductView pd = new ProductView();
//        pd.menuProduct();
//    }
}
