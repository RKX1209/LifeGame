import java.awt.*;
import javax.swing.*;

//Second Commit

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("LifeGame");
        MainPanel mainpanel = new MainPanel();
	SubPanel subpanel = new SubPanel(mainpanel);
	Container contentPane = getContentPane();
	contentPane.add(mainpanel,BorderLayout.CENTER);
	contentPane.add(subpanel,BorderLayout.EAST);
        pack();
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setResizable(false);
        frame.setVisible(true);
    }
}
