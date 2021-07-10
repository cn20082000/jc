package jc.views;

import java.util.List;
import java.util.Scanner;

import jc.AppController;
import jc.models.Option;
import jc.templates.View;

/**
 * Hàm chứa các thành phần cơ bản để tạo nên 
 * một {@code OptionView}. Mỗi {@code OptionView} có thể 
 * chứa nhiều {@code Option} được đánh số, có nội dung 
 * và hành động riêng biệt 
 * @author Công Chính 
 */
public abstract class OptionView extends View {

    /**
     * Máy đọc 
     */
    private Scanner scanner = new Scanner(System.in);

    @Override
    protected void hidePage() {
        super.hidePage();
        System.out.println("");
    }

    @Override
    public void show() {
        super.show();
        // System.out.print("\033[H\033[2J");
        // System.out.flush();

        for (int i = 0; i < this.options().size(); ++i) {
            System.out.println((i + 1) + ". " + this.options().get(i).name());
        }

        if (this.hasReturnOption()) {
            if (AppController.isExit()) {
                System.out.println("0. " + this.exitSen());
            } else {
                System.out.println("0. " + this.returnSen());
            }
        }

        while (true) {
            System.out.print(this.choiceSen() + ": ");
            int choice = scanner.nextInt();

            if (choice > this.options().size() || choice <= 0) {
                if (this.hasReturnOption() && choice == 0) {
                    try {
                        AppController.popView();
                    } catch (Exception e) {
                        AppController.close();
                    }
                    break;
                } else {
                    System.out.println(this.errorChoiceSen() + "\n");
                }
            } else {
                this.options().get(choice - 1).run();
                if (!this.hasReturnOption()) {
                    AppController.close();
                }
            }
        }
    }

    /**
     * Hàm trả về các {@code Option} sử dụng cho 
     * {@code OptionView}
     * @return các {@code Option}
     */
    protected abstract List<Option> options();

    /**
     * Hàm cho biết có hiển thị tùy chọn "Trở về" 
     * hoặc "Thoát chương trình" hay không 
     * @return có hoặc không, mặc định là không 
     */
    protected boolean hasReturnOption() {
        return false;
    }

    /**
     * Hàm chứa nội dung của tùy chọn "Trở về"
     * @return nội dung tùy chọn, mặc định là 
     * "Tro lai"
     */
    protected String returnSen() {
        return "Tro lai";
    }

    /**
     * Hàm chứa nội dung của tùy chọn "Thoát chương trình"
     * @return nội dung tùy chọn, mặc định là 
     * "Thoat chuong trinh"
     */
    protected String exitSen() {
        return "Thoat chuong trinh";
    }

    /**
     * Hàm chứa nội dung của lời mời nhập lựa chọn 
     * @return nội dung tùy chọn, mặc định là 
     * "Lua chon cua ban"
     */
    protected String choiceSen() {
        return "Lua chon cua ban";
    }

    /**
     * Hàm chứa nội dung thông báo lỗi khi 
     * số bạn nhập không có trong {@link #options()}
     * @return nội dung tùy chọn, mặc định là 
     * "Khong ton tai lua chon"
     */
    protected String errorChoiceSen() {
        return "Khong ton tai lua chon";
    }

}
