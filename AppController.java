package jc;

import java.util.ArrayList;
import java.util.List;

import jc.exceptions.AppControllerException;
import jc.templates.View;

/**
 * Lớp điều khiển toàn bộ hoạt động của các {@code View} 
 * trong chương trình.
 * @author Công Chính
 */
public class AppController {

    /**
     * Ngăn xếp chứa các {@code View} của chương trình
     */
    private static List<View> stack = new ArrayList<>();

    /**
     * Hàm thêm và khởi chạy {@code View} đầu tiên 
     * của chương trình 
     * @param initialView đầu tiên 
     */
    public static void start(View initialView) {
        stack.add(initialView);
        stack.get(0).create();
        stack.get(0).show();
    }

    /**
     * Hàm tạm dừng {@code View} trước đó, thêm và khởi 
     * chạy {@code View} mới 
     * @param view mới 
     * @throws AppControllerException "No initial view..." 
     * nếu chưa có {@code View} nào trong ngăn xếp
     */
    public static void pushView(View view) throws AppControllerException {
        if (stack.size() <= 0) {
            throw new AppControllerException("No initial view...");
        }

        stack.get(stack.size() - 1).hide();
        stack.add(view);
        stack.get(stack.size() - 1).create();
        stack.get(stack.size() - 1).show();
    }

    /**
     * Hàm dừng và tiêu diệt {@code View} hiện tại, 
     * tái khởi chạy {@code View} trước đó
     * @throws AppControllerException "No view to pop..." 
     * nếu hiện tại không có {@code View} nào đang chạy 
     * @throws AppControllerException "No view in stack..." 
     * nếu không có {@code View} nào để tái khởi động 
     */
    public static void popView() throws AppControllerException {
        if (stack.size() <= 0) {
            throw new AppControllerException("No view to pop...");
        }

        stack.get(stack.size() - 1).hide();
        stack.get(stack.size() - 1).destroy();
        stack.remove(stack.size() - 1);

        if (stack.size() <= 0) {
            throw new AppControllerException("No view in stack...");
        }
        stack.get(stack.size() - 1).show();
    }

    /**
     * Hàm kiểm tra {@code View} hiện tại có phải 
     * là {@code View} đầu tiên hay không 
     * @return đúng hoặc sai 
     */
    public static boolean isExit() {
        if (stack.size() > 1) {
            return false;
        }
        return true;
    }

    /**
     * Hàm kết thúc chương trình khẩn cấp 
     */
    public static void close() {
        System.exit(0);
    }
    
}
