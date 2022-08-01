package utils;

import view.InputOption;

import java.text.DecimalFormat;
import java.util.Scanner;

public class AppUtils {
    static Scanner sc = new Scanner(System.in);

    public static int retryChoose(int min, int max) {
        int option;
        do {
            try {
                option = Integer.parseInt(sc.nextLine());
                if (option > max || option < min) {
                    System.out.println("CHỌN SAI, XIN MỜI NHẬP LẠI");
                    continue;
                }
                break;
            } catch (Exception ex) {
                System.out.println("NHẬP SAI, XIN NHẬP LẠI");
            }
        } while (true);
        return option;
    }

    public static int retryParseInt() {
        int result;
        do {
            try {
                result = Integer.parseInt(sc.nextLine());
                return result;
            } catch (Exception ex) {
                System.out.println("NHẬP SAI, XIN NHẬP LẠI");
            }
        } while (true);
    }

    public static String retryTitle(){
        String title;
        while ((title = sc.nextLine()).isEmpty()){
            System.out.println("KHÔNG ĐƯỢC BỎ TRỐNG");
        }
        return title;
    }
    public static String retryString(String fieldName) {
        String result;
        while ((result = sc.nextLine()).isEmpty()) {
            System.out.printf("%s KHÔNG ĐƯỢC ĐỂ TRỐNG \n", fieldName);
            System.out.print("░░░ ");
        }
        return result;
    }

    public static double retryParseDouble() {
        double result;
        do {
            try {
                result = Double.parseDouble(sc.nextLine());
                return result;
            } catch (Exception ex) {
                System.out.println("NHẬP SAI, XIN NHẬP LẠI");
            }
        } while (true);
    }

    public static String doubleToVND(double value) {
        String patternVND = ",###₫";
        DecimalFormat decimalFormat = new DecimalFormat(patternVND);
        return decimalFormat.format(value);
    }

    public static boolean isRetry(InputOption inputOption) {
        do {
            switch (inputOption) {
                case ADD:
                    System.out.println("NHẤN 1 ĐÊ TIẾP TỤC \t|\t NHẤN 2 ĐỂ QUAY LẠI \t|\t NHẤN 0 ĐỂ THOÁT CHƯƠNG TRÌNH");
                    break;
                case UPDATE:
                    System.out.println("NHẤN 1 ĐÊ TIẾP TỤC \t|\t NHẤN 2 ĐỂ QUAY LẠI \t|\t NHẤN 0 ĐỂ THOÁT CHƯƠNG TRÌNH");
                    break;
                case DELETE:
                    System.out.println("NHẤN 1 ĐÊ TIẾP TỤC \t|\t NHẤN 2 ĐỂ QUAY LẠI \t|\t NHẤN 0 ĐỂ THOÁT CHƯƠNG TRÌNH");
                    break;
                case SHOW:
                    System.out.println("NHẤN 1 ĐÊ TIẾP TỤC \t|\t NHẤN 2 ĐỂ QUAY LẠI \t|\t NHẤN 0 ĐỂ THOÁT CHƯƠNG TRÌNH");
                    break;
                case SEARCH:
                    System.out.println("NHẤN 1 ĐÊ TIẾP TỤC \t|\t NHẤN 2 ĐỂ QUAY LẠI \t|\t NHẤN 0 ĐỂ THOÁT CHƯƠNG TRÌNH");
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + inputOption);
            }

            System.out.print("░░░ ");
            String option = sc.nextLine();
            switch (option) {
                case "1":
                    return true;
                case "2":
                    return false;
                case "0":
                    exit();
                    break;
                default:
                    System.out.println("CHỌN SAI SỐ, XIN NHẬP LẠI ");
                    break;
            }
        } while (true);
    }

    public static void exit() {
        System.out.println("░░░ CHÀO TẠM BIỆT, HẸN GẶP LẠI !!! ░░░");
        System.exit(5);
    }
}
