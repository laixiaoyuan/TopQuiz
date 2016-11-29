package UI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by Lexie on 11/25/16.
 */
public class QuizFrame {

    public QuizFrame() {
        JFrame frame = new JFrame("TopQuiz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);

        Border outline = BorderFactory.createLineBorder(Color.BLACK);

        JPanel topic = new TopicPanel();
        topic.setBorder(outline);

//        JPanel barGraph = new BarPanel();
//        barGraph.setBorder(outline);

        frame.add(topic);
        frame.setVisible(true);
    }
}
