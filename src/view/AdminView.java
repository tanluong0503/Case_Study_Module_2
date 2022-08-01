package view;

import severies.IUserService;
import severies.UserService;
import utils.AppUtils;

import java.util.Scanner;

public class AdminView {
    Scanner sc = new Scanner(System.in);
    private final IUserService userService;

    public AdminView() {
        userService = UserService.getInstance();
    }

    public void adminLogin() {
        boolean isRetry;
        System.out.println("------------------------ [ĐĂNG NHẬP ADMIN] ------------------------");
        do {
            System.out.print("NHẬP TÀI KHOẢN : ");
            String userName = AppUtils.retryString("TÊN ĐĂNG NHẬP");
            System.out.print("NHẬP MẬT KHẨU : ");
            String password = AppUtils.retryString("MẬT KHẨU");
            if (userService.adminLogin(userName, password) == null) {
                System.out.println("TÀI KHOẢN KHÔNG HỢP LỆ");
                isRetry = isRetry();
            } else {
                System.out.println("ĐĂNG NHẬP THÀNH CÔNG !!! ");
                isRetry = false;
            }
        } while (isRetry);
    }
    public void userLogin() {
        boolean isRetry;
        System.out.println("------------------------ [ĐĂNG NHẬP USER] ------------------------");
        do {
            System.out.print("NHẬP TÀI KHOẢN : ");
            String userName = AppUtils.retryString("TÊN ĐĂNG NHẬP");
            System.out.print("NHẬP MẬT KHẨU : ");
            String password = AppUtils.retryString("MẬT KHẨU");
            if (userService.userLogin(userName, password) == null) {
                System.out.println("TÀI KHOẢN KHÔNG HỢP LỆ");
                isRetry = isRetry();
            } else {
                System.out.println("ĐĂNG NHẬP THÀNH CÔNG !!! ");
                isRetry = false;
            }
        } while (isRetry);

    }

    public boolean isRetry() {
        do {
            try {

                System.out.println("\t----------------------------------------------------------");
                System.out.println("\t--                                                      --");
                System.out.println("\t--               【1】. ĐĂNG NHẬP LẠI                    --");
                System.out.println("\t--               【2】. QUAY LẠI                         --");
                System.out.println("\t--               【0】. THOÁT CHƯƠNG TRÌNH               --");
                System.out.println("\t--                                                      --");
                System.out.println("\t----------------------------------------------------------");
                System.out.print("░░░ CHỌN SỐ : ");
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        return true;
                    case 2:
                        menuLogin();
                    case 0:
                        AppUtils.exit();
                        break;
                    default:
                        System.out.println("CHỌN SAI SỐ, MỜI CHỌN LẠI : ");
                }
            }catch (Exception e){
                System.out.println("NHẬP SAI, XIN NHẬP LẠI");
            }
        } while (true);
    }

    public void menuLogin(){
        MainLauncher mainLauncher = new MainLauncher();
        do {
            try {
                System.out.println("\t----------------------------------------------------------");
                System.out.println("\t--                                                      --");
                System.out.println("\t--               【1】. ĐĂNG NHẬP ADMIN                  --");
                System.out.println("\t--               【2】. ĐĂNG NHẬP USER                   --");
                System.out.println("\t--               【0】. THOÁT CHƯƠNG TRÌNH               --");
                System.out.println("\t--                                                      --");
                System.out.println("\t----------------------------------------------------------");
                System.out.print("░░░ CHỌN SỐ : ");
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice){
                    case 1:
                        adminLogin();
                        mainLauncher.mainMenu();
                        break;
                    case 2:
                        userLogin();
                        UserSubMenu userSubMenu = new UserSubMenu();
                        userSubMenu.userSubMenu();
                        break;
                    case 0:
                        AppUtils.exit();
                    default:
                        System.out.println("CHỌN SAI SỐ, MỜI CHỌN LẠI : ");
                }
            }catch (Exception e){
                System.out.println("NHẬP SAI, XIN NHẬP LẠI");
            }
        }while(true);
    }
}
