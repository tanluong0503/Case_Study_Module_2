package view;

import utils.AppUtils;

import java.util.Scanner;

public class MainLauncher {
    Scanner sc = new Scanner(System.in);
    int number;
    static AdminView adminView = new AdminView();
    static UserView userView = new UserView();
    static ProductView productView = new ProductView();
    static MainLauncher mainLauncher = new MainLauncher();
    static OrderView orderView = new OrderView();

    public void setMainLauncher() {
        adminView.menuLogin();
    }

    public void mainMenu() {
        System.out.println("\t----------------------------------------------------------");
        System.out.println("\t--░░░░░░░░░░░░░░░░░░░░[DANH SÁCH QUẢN LÝ]░░░░░░░░░░░░░░░--");
        System.out.println("\t----------------------------------------------------------");
        System.out.println("\t--                                                      --");
        System.out.println("\t--               【1】. QUẢN LÍ NGƯỜI DÙNG               --");
        System.out.println("\t--               【2】. QUẢN LÝ SẢN PHẨM                 --");
        System.out.println("\t--               【3】. QUẢN LÍ ĐƠN ĐẶT HÀNG             --");
        System.out.println("\t--               【0】. THOÁT CHƯƠNG TRÌNH               --");
        System.out.println("\t--                                                      --");
        System.out.println("\t----------------------------------------------------------");
        System.out.print("░░░░░░░░░░░░░░░░░░░░░░░░░░  CHỌN SỐ : ");
        do {
            try {
                number = Integer.parseInt(sc.nextLine());
                switch (number) {
                    case 1:
                        userView.menuUser();
                        break;
                    case 2:
                        productView.menuProduct();
                        break;
                    case 3:
                        orderView.orderMenu();
                        break;
                    case 0:
                        AppUtils.exit();
                }
            } catch (Exception e) {
                System.out.println("NHẬP SAI, XIN NHẬP LẠI");
            }
        } while (number != 0);

    }
}
