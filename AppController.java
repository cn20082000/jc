package jc;

import java.util.ArrayList;
import java.util.List;

import jc.exceptions.AppControllerException;
import jc.templates.View;

public class AppController {

    private static List<View> stack = new ArrayList<>();

    public static void start(View initialView) {
        stack.add(initialView);
        stack.get(0).create();
        stack.get(0).show();
    }

    public static void pushView(View view) throws AppControllerException {
        if (stack.size() <= 0) {
            throw new AppControllerException("No initial view...");
        }

        stack.get(stack.size() - 1).hide();
        stack.add(view);
        stack.get(stack.size() - 1).create();
        stack.get(stack.size() - 1).show();
    }

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

    public static boolean isExit() {
        if (stack.size() > 1) {
            return false;
        }
        return true;
    }

    public static void close() {
        System.exit(0);
    }
    
}
