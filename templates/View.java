package jc.templates;

/**
 * Lớp chứa các thành phần cơ bản nhất của một {@code View}.
 * Có thể tùy chỉnh một {@code View} mới từ đây.
 * @author Công Chính 
 */
public class View {

    /**
     * Hàm khởi tạo rỗng 
     */
    public View() {}

    /**
     * Hàm tạo ra {@code View}, chạy một lần duy nhất 
     * khi {@code View} được tạo ra
     */
    public void create() {
        this.createPage();
    }

    /**
     * Hàm thiết kế một màn hình chào mừng riêng 
     * cho {@link #create()}
     */
    protected void createPage() {}

    /**
     * Hàm hiện {@code View} ra màn hình, có thể được 
     * chạy nhiều lần sau khi {@link #create()} hoặc 
     * khôi phục từ trạng thái {@link #hide()}
     */
    public void show() {
        this.showPage();
    }

    /**
     * Hàm thiết kế một màn hình chào mừng riêng 
     * cho {@link #show()}
     */
    protected void showPage() {}

    /**
     * Hàm tạm ngưng hoạt động của {@code View} để 
     * nhường chỗ cho một {@code View} khác hoặc 
     * chuẩn bị {@link #destroy()}, có thể tạm ngưng 
     * nhiều lần 
     */
    public void hide() {
        this.hidePage();
    }
    
    /**
     * Hàm thiết kế một màn hình chào mừng riêng 
     * cho {@link #hide()}
     */
    protected void hidePage() {}

    /**
     * Hàm kết thúc của một {@code View}, chạy một lần
     * duy nhất khi {@code View} bị tiêu diệt
     */
    public void destroy() {
        this.destroyPage();
    }
    
    /**
     * Hàm thiết kế một màn hình chào mừng riêng 
     * cho {@link #destroy()}
     */
    protected void destroyPage() {}
}
