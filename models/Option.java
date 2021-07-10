package jc.models;

/**
 * Lớp chứa nội dung và hành động của tùy chọn trong {@code OptionView}
 * @author Công Chính
 */
public abstract class Option {

    /**
     * Hàm khởi tạo rỗng 
     */
    public Option() {}

    /**
     * Hàm trả về nội dung của {@code Option}
     * @return nội dung của {@code Option}
     */
    public abstract String name();

    /**
     * Hàm trả về hành động của {@code Option}
     */
    public abstract void run();
    
}