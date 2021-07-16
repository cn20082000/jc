package jc.models;

import java.util.List;

/**
 * Lớp chứa các thông tin về mỗi {@code Syntax} 
 * trong {@code Command} nhất định
 * @author Công Chính 
 */
public abstract class Syntax {

    /**
     * Hàm khởi tạo rỗng 
     */
    public Syntax() {}

    /**
     * Những {@code Syntax} bắt buộc tuân theo nếu 
     * muốn thực thi câu lệnh này 
     * @return danh sách các chuỗi {@code Syntax}
     */
    public abstract List<String> syntax();

    /**
     * Hàm trả về ý nghĩa của {@code Syntax}
     * @return ý nghĩa của {@code Syntax}
     */
    public abstract String description();

    /**
     * Số lượng tham số cần nhập vào 
     * @return số lượng tham số cần nhập 
     */
    // public abstract int numberOfParameter();

    /**
     * Hàm thực thi nếu gõ đúng câu lệnh
     * @param para danh sách tham số nhập vào 
     */
    public abstract void run(List<String> para);
}
