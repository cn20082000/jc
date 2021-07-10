package jc.views;

import java.util.List;
import java.util.Scanner;

import jc.AppController;
import jc.models.Option;
import jc.templates.View;

public abstract class OptionView extends View {

    private Scanner scanner = new Scanner(System.in);

    public OptionView() {
        super();
    }

    @Override
    public void create() {}

    @Override
    public void show() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

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

            if (choice > this.options().size() || choice < 0) {
                System.out.println(this.errorChoiceSen() + "\n");
            } else if (choice == 0) {
                try {
                    AppController.popView();
                } catch (Exception e) {
                    AppController.close();
                }
                break;
            } else {
                this.options().get(choice - 1).run();
                break;
            }
        }
    }

    @Override
    public void hide() {}

    @Override
    public void destroy() {}

    protected abstract List<Option> options();

    protected boolean hasReturnOption() {
        return false;
    }

    protected String returnSen() {
        return "Tro lai";
    }

    protected String exitSen() {
        return "Thoat chuong trinh";
    }

    protected String choiceSen() {
        return "Lua chon cua ban";
    }

    protected String errorChoiceSen() {
        return "Khong ton tai lua chon";
    }
}
