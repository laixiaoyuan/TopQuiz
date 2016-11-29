package UI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Lexie on 11/25/16.
 */
public class TopicPanel extends JPanel {

    Font fontBig = new Font("Arial", Font.BOLD, 30);
    Font fontSmall = new Font("Arial", Font.PLAIN, 20);

    String topicName1 = "History";
    String topicName2 = "Maths";
    String topicName3 = "Animals";


    Border outline = BorderFactory.createLineBorder(Color.BLACK);

    public TopicPanel() {
        super();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel welcomePanel = createWelcomePanel();
        JPanel topicPanel = createTopicPanel();
        add(welcomePanel);
        add(topicPanel);


    }
    private JPanel createWelcomePanel() {
        JPanel welcome = new JPanel();
        welcome.setLayout(new BoxLayout(welcome, BoxLayout.Y_AXIS));

        JLabel welcomeMessage1 = new JLabel("Welcome to TopQuiz!");
        welcomeMessage1.setFont(fontBig);
        JLabel welcomeMessage2 = new JLabel("Please select one topic from below");
        welcomeMessage2.setFont(fontBig);
        welcome.add(welcomeMessage1);
        welcome.add(Box.createRigidArea(new Dimension(30, 30)));
        welcome.add(welcomeMessage2);
        welcome.setBorder(outline);
        return welcome;
    }
    private JPanel createTopicPanel() {
        JPanel topics = new JPanel();
        topics.setLayout(new FlowLayout());

        JLabel topicHeader = new JLabel("Topics:");
        topicHeader.setFont(fontBig);
        topics.add(topicHeader);
        addButton(topics, topicName1);
        addButton(topics, topicName2);
        addButton(topics, topicName3);
        topics.setBorder(outline);
        return topics;
    }
    private void addButton(JPanel panel, String buttonName) {

        JButton button = new JButton(buttonName);
        button.setFont(fontSmall);
        panel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "You selected: " + buttonName);
                panel.getParent().setVisible(false);

                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                JPanel questionPanel = new QuestionPanel(buttonName);
                questionPanel.setVisible(true);
                topFrame.add(questionPanel);
            }
        });
    }

}
