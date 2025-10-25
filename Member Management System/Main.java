import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        MemberManager manager = new MemberManager();
        // ask user which interface to use
        String[] options = {"GUI", "Console"};
        int choice = JOptionPane.showOptionDialog(null, "Choose interface mode:", "MMS Startup",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == 0) {
            new GUIInterface(manager).start();
        } else {
            new ConsoleInterface(manager).start();
        }
    }
}
