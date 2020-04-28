
import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Praca domowa - kulki");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Panel mainPanel = new Panel();
        frame.getContentPane().add(mainPanel);
        frame.setPreferredSize(new Dimension(800,600));
        frame.setVisible(true);
        frame.setResizable(false);
        frame.pack();



    }
}