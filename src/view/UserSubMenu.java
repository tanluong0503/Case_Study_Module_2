package view;

import utils.AppUtils;

import java.util.Scanner;

public class UserSubMenu {
    public void userSubMenu() {
        Scanner sc = new Scanner(System.in);
        OrderView orderView = new OrderView();
        System.out.println("\t----------------------------------------------------------");
        System.out.println("\t--░░░░░░░░░░░░░░░░░░░░[QUẢN LÍ HÓA ĐƠN]░░░░░░░░░░░░░░░░░--");
        System.out.println("\t----------------------------------------------------------");
        System.out.println("\t--                                                      --");
        System.out.println("\t--               【1】. TẠO ĐƠN HÀNG                     --");
        System.out.println("\t--               【2】. DANH SÁCH SẢN PHẨM               --");
        System.out.println("\t--               【0】. THOÁT                            --");
        System.out.println("\t--                                                      --");
        System.out.println("\t----------------------------------------------------------");
        System.out.print("CHỌN SỐ : ");

        int number = Integer.parseInt(sc.nextLine());
        switch (number) {
            case 1:
                orderView.addOrder();
                break;
            case 2:
                ProductView productView = new ProductView();
                productView.showProductsSub();
                userSubMenu();
                break;
            case 0:
                AppUtils.exit();
            default:
                System.out.println("NHẬP KHÔNG ĐÚNG, XIN NHẬP LẠI");
        }
    }
}
