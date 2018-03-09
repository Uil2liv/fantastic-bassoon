import javax.swing.*;

public class GUI {
    private static void createAndShowMainFrame() {
        new MainFrame("fantastic-bassoon");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowMainFrame();
            }
        });
    }
}
