package jc.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import jc.models.Command;
import jc.templates.View;

/**
 * Lớp chứa các thành phần cơ bản tạo nên 
 * một {@code CommandView}. Mỗi {@code CommandView} 
 * chứa nhiều lệnh để thực hiện các công việc 
 * khác nhau 
 * @author Công Chính 
 */
public abstract class CommandView extends View {

    /**
     * Máy đọc 
     */
    private Scanner scanner = new Scanner(System.in);

    /**
     * Danh sách lệnh tổng 
     */
    private List<Command> list = new ArrayList<>();

    @Override
    public void create() {
        super.create();

        list = command();
        if (hasExit() && exitCommand() != null) {
            list.add(exitCommand());
        }
        if (hasHelp() && helpCommand() != null) {
            list.add(helpCommand());
        }
    }

    @Override
    protected void createPage() {
        System.out.println(welcomeSen());
    }

    @Override
    public void show() {
        System.out.println(welcomeSen());

        while (true) {
            System.out.print(operatingSymbol() + " ");
            String cmd = scanner.nextLine();

            Command.Analyze analyze = new Command.Analyze(){
                @Override
                protected String stringToAnalyze() {
                    return cmd;
                }
            };

            try {
                // Kiểm tra câu lệnh chính 
                String command = analyze.command();

                int commandIndex = -1;
                for (int i = 0; i < list.size(); ++i) {
                    if (list.get(i).name() == command) {
                        commandIndex = i;
                        break;
                    }
                }

                if (commandIndex == -1) {
                    System.out.println(whenCommandError());
                    continue;
                }

                // Kiểm tra câu lệnh phụ 
                List<String> syntax = analyze.syntax();

            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    /**
     * Hàm chứa câu chào mừng, hiện ra khi bắt đầu 
     * {@code CommandView}
     * @return câu chào mừng
     */
    protected String welcomeSen() {
        return "Chao mung den voi CommandView\n";
    }

    /**
     * Dấu mời trước mỗi câu lệnh
     * @return dấu mời
     */
    protected String operatingSymbol() {
        return ">>";
    }

    /**
     * Danh sách câu lệnh 
     * @return danh sách câu lệnh 
     */
    protected abstract List<Command> command();

    /**
     * Hàm cho biết có câu lệnh thoát hay không 
     * @return có hoặc không, mặc định là không 
     */
    protected boolean hasExit() {
        return false;
    }

    /**
     * Câu lệnh thoát 
     * @return câu lệnh thoát, mặc định là null 
     */
    protected Command exitCommand() {
        return null;
    }

    /**
     * Hàm cho biết có câu lệnh trợ giúp không 
     * @return có hoặc không, mặc định là không 
     */
    protected boolean hasHelp() {
        return false;
    }

    /**
     * Câu lệnh trợ giúp 
     * @return câu lệnh trợ giúp, mặc định là null 
     */
    protected Command helpCommand() {
        return null;
    }

    /**
     * Câu in ra khi người dùng gõ sai câu lệnh 
     * @return câu nào đó 
     */
    protected abstract String whenCommandError();

    /**
     * Câu in ra khi người dùng gõ lệnh đúng nhưng 
     * sai cú pháp lệnh 
     * @return câu nào đó 
     */
    protected abstract String whenSyntaxError();

    /**
     * Câu in ra khi người dùng gõ sai tham số 
     * đầu vào 
     * @return câu nào đó 
     */
    protected abstract String whenParaError();
}