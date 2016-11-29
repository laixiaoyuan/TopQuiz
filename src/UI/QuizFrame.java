package UI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by Lexie on 11/25/16.
 */
public class QuizFrame {
    CardLayout cardsMain;
    JPanel cardPanelMain;


    public QuizFrame() {
        JFrame frame = new JFrame("TopQuiz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height * 3 / 4;
        int width = screenSize.width * 3 / 4;
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);

        Border outline = BorderFactory.createLineBorder(Color.BLACK);

        JPanel topic = new TopicPanel();
        topic.setBorder(outline);

        JPanel question = new QuestionPanel();
        question.setBorder(outline);


        JPanel barGraph = new BarPanel();
        barGraph.setBorder(outline);


        cardsMain = new CardLayout();
        cardPanelMain = new JPanel();
        cardPanelMain.setLayout(cardsMain);
        cardPanelMain.add(topic, "topic");
        cardPanelMain.add(question, "question");
        cardPanelMain.add(barGraph, "barGraph");
        cardsMain.show(cardPanelMain, "topic");

        frame.add(cardPanelMain);
        frame.setVisible(true);
    }
}
