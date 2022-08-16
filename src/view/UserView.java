package view;

import model.Role;
import model.User;
import severies.IUserService;
import severies.UserService;
import utils.AppUtils;
import utils.InstantUtils;
import utils.ValidateUtils;

import java.util.Scanner;

public class UserView {
    Scanner sc = new Scanner(System.in);
    int number;
    private final IUserService userService;

    public UserView() {
        userService = UserService.getInstance();
    }

    private int inputId(InputOption option) {
        int id;
        switch (option) {
            case ADD:
                System.out.println("NHẬP ID");
                break;
            case UPDATE:
                System.out.println("NHẬP ID CẦN THAY ĐỔI");
                break;
        }
        boolean isRetry = false;
        do {
            id = AppUtils.retryParseInt();
            boolean exist = userService.existById(id);
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

    public String inputUsername() {
        System.out.print("NHẬP TÊN TÀI KHOẢN : ");
        String username;

        do {
            if (!ValidateUtils.isUsernameValid(username = AppUtils.retryString("Username"))) {
                System.out.println("TÊN TÀI KHOẢN CỦA BẠN NHẬP KHÔNG ĐÚNG ĐỊNH DẠNG (>7 KÝ TỰ, KHÔNG BAO GỒM KÍ TỰ ĐẶC BIỆT,...)");
                System.out.print("NHẬP TÊN TÀI KHOẢN : ");
                continue;
            }
            if (userService.existsByUsername(username)) {
                System.out.println("TÊN TÀI KHOẢN NÀY ĐÃ TỒN TẠI, XIN NHẬP LẠI");
                System.out.print("NHẬP TÊN TÀI KHOẢN : ");
                continue;
            }
            break;
        } while (true);

        return username;
    }

    private String inputFullName(InputOption option) {
        switch (option) {
            case ADD:
                System.out.print("NHẬP HỌ VÀ TÊN : ");
                break;
            case UPDATE:
                System.out.print("NHẬP TÊN MUỐN THAY ĐỔI : ");
                break;
        }

        String fullName;
        while (!ValidateUtils.isNameValid(fullName = sc.nextLine())) {
            System.out.println("TÊN KHÔNG ĐÚNG ĐỊNH DẠNG (PHẢI VIẾT HOA CHỮ CÁI ĐẦU TIÊN)");
            System.out.print("NHẬP LẠI : ");
        }
        return fullName;
    }

    public String inputPhone(InputOption option) {
        switch (option) {
            case ADD:
                System.out.print("NHẬP SỐ ĐIỆN THOẠI : ");
                break;
            case UPDATE:
                System.out.print("NHẬP SỐ ĐIỆN THOẠI MUỐN THAY ĐỔI : ");
                break;
        }
        String phone;
        do {
            phone = sc.nextLine();
            if (!ValidateUtils.isPhoneValid(phone)) {
                System.out.println("SỐ CỦA BẠN KHÔNG ĐÚNG ĐỊNH DẠNG (BẮT ĐẦU LÀ SỐ 0, VÀ ĐỦ 10 SỐ)");
                System.out.print("NHẬP SỐ ĐIỆN THOẠI : ");
                continue;
            }
            if (userService.existsByPhone(phone)) {
                System.out.println("SỐ NÀY ĐÃ TỒN TẠI, XIN NHẬP LẠI");
                System.out.print("NHẬP SỐ ĐIỆN THOẠI : ");
                continue;
            }
            break;
        } while (true);

        return phone;
    }

    private String inputEmail() {
        System.out.print("NHẬP EMAIL : ");
        String email;
        do {
            if (!ValidateUtils.isEmailValid(email = sc.nextLine())) {
                System.out.println("EMAIL CỦA BẠN KHÔNG ĐÚNG ĐỊNH DẠNG (tanluong@gmail.com)");
                System.out.print("NHẬP EMAIL : ");
                continue;
            }
            if (userService.existsByEmail(email)) {
                System.out.println("EMAIL CỦA BẠN ĐÃ TỒN TẠI, XIN NHẬP LẠI");
                System.out.print("NHẬP EMAIL : ");
                continue;
            }
            break;
        } while (true);
        return email;
    }

    private String inputPassword() {
        System.out.print("NHẬP MẬT KHẨU : ");
        String password;
        while (!ValidateUtils.isPasswordValid(password = sc.nextLine())) {
            System.out.println("MẬT KHẨU PHẢI > 7 KÍ TỰ");
            System.out.print("NHẬP MẬT KHẨU : ");
        }
        return password;
    }

    private String inputAddress(InputOption option) {
        switch (option) {
            case ADD:
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
                return address;
            case UPDATE:
                System.out.print("NHẬP ĐỊA CHỈ : ");
                address = sc.nextLine();
                do {
                    if (address.trim().isEmpty()) {
                        System.out.println("ĐỊA CHỈ KHÔNG ĐƯỢC BỎ TRỐNG, XIN NHẬP NGHIÊM TÚC");
                        System.out.print("NHẬP ĐỊA CHỈ : ");
                        address = sc.nextLine();
                    }
                } while (address.trim().isEmpty());
                return address;
        }
        return null;
    }

    public void setRole(User user) {
        System.out.println("\t----------------------------------------------------------");
        System.out.println("\t--░░░░░░░░░░░░░░░░░[PHÂN QUYỀN NGƯỜI DÙNG]░░░░░░░░░░░░░░--");
        System.out.println("\t----------------------------------------------------------");
        System.out.println("\t--                                                      --");
        System.out.println("\t--               【1】. THÀNH VIÊN                       --");
        System.out.println("\t--               【2】. QUẢN TRỊ VIÊN                    --");
        System.out.println("\t--                                                      --");
        System.out.println("\t----------------------------------------------------------");
        System.out.print("░░░░░░░░░░░░░░░░░░░░░░░░░░  CHỌN SỐ : ");
        String option = sc.nextLine();
        sc.nextLine();
        switch (option) {
            case "1":
                user.setRole(Role.USER);
                break;
            case "2":
                user.setRole(Role.ADMIN);
                break;
            default:
                System.out.println("NHẬP KHÔNG ĐÚNG, XIN NHẬP LẠI");
                setRole(user);
        }
    }

    public void addUser() {
        do {
            try {
                long id = System.currentTimeMillis() / 1000;
                String username = inputUsername();
                String password = inputPassword();
                String fullName = inputFullName(InputOption.ADD);
                String phone = inputPhone(InputOption.ADD);
                String address = inputAddress(InputOption.ADD);
                String email = inputEmail();
                User user = new User(id, username, password, fullName, phone, email, address, Role.USER);
                setRole(user);
                userService.add(user);
                System.out.println("ĐÃ THÊM THÀNH CÔNG");
            } catch (Exception ex) {
                System.out.println("NHẬP SAI, XIN NHẬP LẠI");
            }
        } while (AppUtils.isRetry(InputOption.ADD));
    }

    public void updateUser() {
        boolean isRetry = false;
        do {
            try {
                showUsers(InputOption.UPDATE);
                int id = inputId(InputOption.UPDATE);
                System.out.println("\t----------------------------------------------------------");
                System.out.println("\t--░░░░░░░░░░░░░░░░░░░[THAY ĐỔI NGƯỜI DÙNG]░░░░░░░░░░░░░░--");
                System.out.println("\t----------------------------------------------------------");
                System.out.println("\t--                                                      --");
                System.out.println("\t--               【1】. THAY ĐỔI TÊN                     --");
                System.out.println("\t--               【2】. THAY ĐỔI SỐ ĐIỆN THOẠI           --");
                System.out.println("\t--               【3】. THAY ĐỔI ĐỊA CHỈ                 --");
                System.out.println("\t--               【4】. THAY ĐỔI TẤT CẢ                  --");
                System.out.println("\t--               【5】. QUAY LẠI                         --");
                System.out.println("\t--               【0】. THOÁT CHƯƠNG TRÌNH               --");
                System.out.println("\t--                                                      --");
                System.out.println("\t----------------------------------------------------------");
                System.out.print("░░░░░░░░░░░░░░░░░░░░░░░░░░  CHỌN SỐ : ");
                int option = AppUtils.retryChoose(0, 5);
                User newUser = new User();
                newUser.setId(id);
                String name;
                String phone;
                String address;
                switch (option) {
                    case 1:
                        name = inputFullName(InputOption.UPDATE);
                        newUser.setFullName(name);
                        userService.update(newUser);
                        System.out.println("THAY ĐỔI TÊN THÀNH CÔNG");
                        break;
                    case 2:
                        phone = inputPhone(InputOption.UPDATE);
                        newUser.setMobile(phone);
                        userService.update(newUser);
                        System.out.println("THAY ĐỔI SỐ ĐIỆN THOẠI THÀNH CÔNG");
                        break;
                    case 3:
                        address = inputAddress(InputOption.UPDATE);
                        newUser.setAddress(address);
                        userService.update(newUser);
                        System.out.println("THAY ĐỔI ĐỊA CHỈ THÀNH CÔNG");
                        break;
                    case 4:
                        name = inputFullName(InputOption.UPDATE);
                        newUser.setFullName(name);
                        phone = inputPhone(InputOption.UPDATE);
                        newUser.setMobile(phone);
                        address = inputAddress(InputOption.UPDATE);
                        newUser.setAddress(address);
                        userService.update(newUser);
                        System.out.println("THAY ĐỔI THÀNH CÔNG");
                }
                isRetry = option != 4 && AppUtils.isRetry(InputOption.UPDATE);

            } catch (Exception e) {
                System.out.println("NHẬP SAI, XIN NHẬP LẠI");
            }
        } while (isRetry);
    }

    public void showUsers(InputOption option) {
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-18s %-20s %-20s %-30s %-15s %-17s %-20s",
                " ID",
                "TÊN",
                "SỐ ĐIỆN THOẠI",
                "EMAIL",
                "ĐỊA CHỈ",
                "NGƯỜI DÙNG",
                "NGÀY TẠO");
        System.out.println();
        for (User user : userService.findAll()) {
            System.out.printf("%-17s %-20s %-20s %-30s %-15s %-17s %-20s\n",
                    "【" + user.getId() + "】",
                    user.getFullName(),
                    user.getMobile(),
                    user.getEmail(),
                    user.getAddress(),
                    user.getRole(),
                    InstantUtils.instantToString(user.getCreatedAt()));
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
        if (option == InputOption.SHOW) {
            AppUtils.isRetry(InputOption.SHOW);
        }
    }

    public void menuUser() {
        int number;
        System.out.println("\t------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("\t--░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░[DANH SÁCH NGƯỜI DÙNG]░░░░░░░░░░░░░░░░░░░░░░░░░░░░░--");
        System.out.println("\t-------------------------------------------------------------------------------------");
        System.out.println("\t--                                                                                 --");
        System.out.println("\t--                        【1】. HIỂN THỊ NGƯỜI DÙNG                                --");
        System.out.println("\t--                        【2】. THÊM NGƯỜI DÙNG                                    --");
        System.out.println("\t--                        【3】. SỬA NGƯỜI DÙNG                                     --");
        System.out.println("\t--                        【4】. QUAY LẠI MENU                                      --");
        System.out.println("\t--                        【0】. THOÁT CHƯƠNG TRÌNH                                 --");
        System.out.println("\t--                                                                                 --");
        System.out.println("\t-------------------------------------------------------------------------------------");
        do {
            System.out.print("░░░░░░░░░░░░░░░░░░░░░░░░░░  CHỌN SỐ : ");
            number = Integer.parseInt(sc.nextLine());
            switch (number) {
                case 1:
                    showUsers(InputOption.SHOW);
                    menuUser();
                    break;
                case 2:
                    addUser();
                    menuUser();
                    break;
                case 3:
                    updateUser();
                    menuUser();
                    break;
                case 4:
                    MainLauncher mainLauncher = new MainLauncher();
                    mainLauncher.mainMenu();
                    break;
                case 0:
                    AppUtils.exit();
                    break;
            }
        } while (number != 0);
    }

}
