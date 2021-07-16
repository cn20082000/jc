package jc.models;

import java.util.ArrayList;
import java.util.List;

import jc.exceptions.CommandException;

/**
 * Lớp chứa nội dung, công thức và ý nghĩa 
 * của một câu lệnh trong {@code CommandView}
 * @author Công Chính
 */
public abstract class Command {

    /**
     * Hàm khởi tạo rỗng 
     */
    public Command() {}

    /**
     * Hàm trả về nội dung của {@code Command}
     * @return nội dung của {@code Command}
     */
    public abstract String name();

    /**
     * Hàm trả về ý nghĩa của {@code Command}
     * @return ý nghĩa của {@code Command}
     */
    public abstract String description();

    /**
     * Hàm cho biết câu lệnh có công thức hay không. 
     * Nếu có cần gõ đúng {@code Syntax} trong {@link #syntaxes()} 
     * mới thực thi. 
     * Nếu không thì gõ đúng {@link #name()} là thực thi hàm 
     * {@link #run()} ngay
     * @return có hoặc không 
     */
    public abstract boolean isSyntax();

    /**
     * Hàm thực thi nếu {@link #isSyntax()} == {@value false}
     * @throws CommandException "This command has syntaxes, 
     * {@link #syntaxes()} funtion will help you..." nếu cố tình chạy hàm 
     * này trong khi {@link #isSyntax()} == {@value true}
     */
    public void run() throws CommandException {
        if (this.isSyntax()) {
            throw new CommandException("This command has syntaxes, syntaxes()" 
                + " funtion will help you...");
        }
    }

    /**
     * Hàm chứa các {@code Syntax} để gõ lệnh cho đúng, 
     * thực thi nếu {@link #isSyntax()} == {@value true}
     * @return danh sách {@code Syntax}
     * @throws CommandException "This command has not syntax, 
     * run() funtion will help you..." nếu cố tình chạy hàm 
     * này trong khi {@link #isSyntax()} == {@value false}
     */
    public List<Syntax> syntaxes() throws CommandException {
        if (!this.isSyntax()) {
            throw new CommandException("This command has not syntax, run()" 
                + " funtion will help you...");
        }
        return new ArrayList<>();
    }

    /**
     * Lớp chứa các phương thức dùng để 
     * phân tích một câu lệnh do người dùng 
     * nhập vào 
     * @author Công Chính 
     */
    public static abstract class Analyze {

        /**
         * Chuỗi thuộc loại String thông thường 
         */
        private static final int WORD_TYPE = 1;
        /**
         * Chuỗi thuộc loại String có khoảng trắng 
         */
        private static final int SEN_TYPE = 2;
        /**
         * Chuỗi thuộc loại lệnh phụ, có dấu "-" trước 
         * chuỗi, bên trong chuỗi không có khoảng cách
         */
        private static final int SYNTAX_TYPE = 3;
        /**
         * Danh sách kí tự bị cấm trong 1 chuỗi 
         */
        private static final List<String> FOBIDDEN_LIST = List.of("\"");

        /**
         * Hàm khởi tạo rỗng 
         */
        public Analyze() {}

        /**
         * Lệnh cần phân tích 
         * @return Lệnh cần phân tích 
         */
        protected abstract String stringToAnalyze();

        /**
         * Hàm trả về lệnh chính của câu lệnh 
         * @return lệnh chính 
         * @exception CommandException.Analyze 
         * "Error in command" trả về lỗi khi lệnh 
         * chính không phải là một từ 
         * @exception CommandException.Analyze 
         * các lỗi khác xảy ra trong quá trình phân tích lệnh 
         */
        public String command() throws CommandException.Analyze {
            if (stringToAnalyze().trim() == "") {
                throw new CommandException.Analyze("No thing to analyze");
            }

            int type = Command.Analyze.checkString(stringToAnalyze());
            if (type != Command.Analyze.WORD_TYPE) {
                throw new CommandException.Analyze("Error in command");
            }
            return Command.Analyze.nextString(stringToAnalyze(), type);
        }

        /**
         * Hàm trả về các lệnh phụ của câu lệnh 
         * @return danh sách lệnh phụ của câu lệnh 
         * @throws CommandException.Analyze 
         * các lỗi xảy ra trong quá trình phân tích lệnh 
         */
        public List<String> syntax() throws CommandException.Analyze {
            String s = stringToAnalyze();
            s = Command.Analyze.cutString(s, Command.Analyze.WORD_TYPE, 
                Command.Analyze.nextString(s, Command.Analyze.WORD_TYPE));

            List<String> result = new ArrayList<>();
            while (Command.Analyze.checkString(s) == Command.Analyze.SYNTAX_TYPE) {
                String syn = Command.Analyze.nextString(s, Command.Analyze.SYNTAX_TYPE);
                result.add(syn);
                s = Command.Analyze.cutString(s, Command.Analyze.SYNTAX_TYPE, syn);
            }

            return result;
        }

        /**
         * Hàm trả về các tham số của câu lệnh 
         * @return danh sách tham số của câu lệnh 
         * @throws CommandException.Analyze
         * các lỗi xảy ra trong quá trình phân tích lệnh 
         */
        public List<String> parameter() throws CommandException.Analyze {
            String s = stringToAnalyze();
            s = Command.Analyze.cutString(s, Command.Analyze.WORD_TYPE, 
                Command.Analyze.nextString(s, Command.Analyze.WORD_TYPE));

            List<String> syns = syntax();
            for (int i = 0; i < syns.size(); ++i) {
                s = Command.Analyze.cutString(s, Command.Analyze.SYNTAX_TYPE, syns.get(i));
            }

            List<String> result = new ArrayList<>();
            while (true) {
                int type = Command.Analyze.checkString(s);
                if (type == Command.Analyze.SYNTAX_TYPE) {
                    throw new CommandException.Analyze("A parameter must not have subtract symbol before");
                }

                String para = Command.Analyze.nextString(s, type);
                if (para == "") {
                    return result;
                }

                result.add(para);
                s = Command.Analyze.cutString(s, type, para);
            }
        }

        /**
         * Hàm trả về chuỗi con kế tiếp của 
         * chuỗi đầu vào, có lọc theo loại 
         * của chuỗi con 
         * @param string chuỗi đầu vào
         * @param typeOfFirst loại của chuỗi con 
         * đầu tiên 
         * @return chuỗi con đầu tiên 
         * @throws CommandException.Analyze "Can 
         * not find type of substring" không tìm thấy 
         * loại của chuỗi con 
         */
        private static String nextString(String string, int typeOfFirst) 
            throws CommandException.Analyze {
            if (typeOfFirst <= 0 || typeOfFirst > 3) {
                throw new CommandException.Analyze("Can not find type of substring");
            }

            string.trim();
            string += " ";
            switch (typeOfFirst) {
                case Command.Analyze.WORD_TYPE: {
                    int index = string.indexOf(" ");
                    return string.substring(0, index);
                }
                case Command.Analyze.SEN_TYPE: {
                    int index = string.substring(1).indexOf("\"");
                    return string.substring(1, index);
                }
                case Command.Analyze.SYNTAX_TYPE: {
                    int index = string.indexOf(" ");
                    return string.substring(1, index);
                }
                default: {
                    return "";
                }
            }
        } 

        /**
         * Hàm kiểm tra xem chuỗi con đầu tiên của 
         * chuỗi nhập vào là loại gì 
         * @param string chuỗi đầu vào 
         * @return loại của chuỗi con đầu tiên 
         * @throws CommandException.Analyze 
         * chuỗi con chứa kí tự bị cấm 
         */
        private static int checkString(String string) throws CommandException.Analyze {
            string.trim();
            string += " ";

            if (string.charAt(0) == '-') {
                int nextSpace = string.indexOf(" ");
                for (int i = 1; i < nextSpace; ++i) {
                    if (Command.Analyze.FOBIDDEN_LIST.contains(String.valueOf(string.charAt(i)))) {
                        throw new CommandException.Analyze("Syntax can not have space or double quote symbol");
                    }
                }
                return Command.Analyze.SYNTAX_TYPE;
            }
            if (string.charAt(0) == '\"') {
                int nextDoubleQuote = string.substring(1).indexOf("\"");
                if (nextDoubleQuote < 0) {
                    throw new CommandException.Analyze("Sentense must have two double quote sumbol at begin and end");
                }
                return Command.Analyze.SEN_TYPE;
            }
            return Command.Analyze.WORD_TYPE;
        }

        /**
         * Hàm trả về chuỗi mới sau khi cắt 
         * @param string chuỗi ban đầu
         * @param typeOfFirst loại của chuỗi con
         * @param sub chuỗi con cần cắt
         * @return chuỗi mới sau khi cắt 
         * @throws CommandException.Analyze không 
         * tìm thấy loại của chuỗi con 
         */
        private static String cutString(String string, int typeOfFirst, String sub) 
            throws CommandException.Analyze {
            if (typeOfFirst <= 0 || typeOfFirst > 3) {
                throw new CommandException.Analyze("Can not find type of substring");
            }

            switch (typeOfFirst) {
                case Command.Analyze.WORD_TYPE: {
                    return string.replaceFirst(sub, "");
                }
                case Command.Analyze.SEN_TYPE: {
                    return string.replaceFirst("\"" + sub + "\"", "");
                }
                case Command.Analyze.SYNTAX_TYPE: {
                    return string.replaceFirst("-" + sub, "");
                }
                default: {
                    return "";
                }
            }
        }
    }
}
