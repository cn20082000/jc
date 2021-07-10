package jc.exceptions;

/**
 * Lớp chứa ngoại lệ khi có lỗi xảy ra tại lớp {@code AppController}
 * @author Công Chính
 */
public class AppControllerException extends Exception {

    /**
     * Hàm khởi tạo trả về một lỗi không xác định 
     * @return "Unknown exception!"
     */
    public AppControllerException() {
        super("Unknown exception!");
    }

    /**
     * Hàm khởi tạo trả về một lỗi có nội dung xác định 
     * @param message nội dung lỗi 
     */
    public AppControllerException(String message) {
        super(message);
    }
    
}
