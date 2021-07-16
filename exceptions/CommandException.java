package jc.exceptions;

/**
 * Lớp chứa ngoại lệ khi có lỗi xảy ra tại lớp {@code Command}
 * @author Công Chính
 */
public class CommandException extends Exception {
    
    /**
     * Hàm khởi tạo trả về một lỗi không xác định 
     * @return "Unknown exception!"
     */
    public CommandException() {
        super("Unknown exception!");
    }

    /**
     * Hàm khởi tạo trả về một lỗi có nội dung xác định 
     * @param message nội dung lỗi 
     */
    public CommandException(String message) {
        super(message);
    }

    /**
     * Lớp chứa ngoại lệ khi có lỗi xảy ra tại lớp {@code Command.Analyze}
     * @author Công Chính
     */
    public static class Analyze extends Exception {
        /**
         * Hàm khởi tạo trả về một lỗi không xác định 
         * @return "Unknown exception!"
         */
        public Analyze() {
            super("Unknown exception!");
        }

        /**
         * Hàm khởi tạo trả về một lỗi có nội dung xác định 
         * @param message nội dung lỗi 
         */
        public Analyze(String message) {
            super(message);
        }
    }
}
