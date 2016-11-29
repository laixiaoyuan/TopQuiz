package UI;

import QuizData.Question;
import QuizData.QuestionBank;
import QuizData.Score;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * Created by Lexie on 11/25/16.
 */
public class QuestionPanel extends JPanel {
    JLabel displayTime;
    Timer timer;
    Integer count;

    CardLayout mainCards;
    JPanel mainPanel;

    Border outline = BorderFactory.createLineBorder(Color.BLACK);
    Font fontBig = new Font("Arial", Font.BOLD, 30);
    Font fontSmall = new Font("Arial", Font.PLAIN, 20);

    String startTopic;
    int questionNumber;

    ActionListener radioBtnEventHandler;
    JRadioButton choiceA;
    JRadioButton choiceB;
    JRadioButton choiceC;
    JRadioButton choiceD;
    String customerAnswer;

    Score score;

    JPanel timerPanel;
    JPanel questionPanel;


//    QuestionBank questionBank;


    public QuestionPanel() {

        super();
        setLayout(new BorderLayout());

        timerPanel = createTimerPanel();
        questionPanel = createQuestionPanel();
        add(timerPanel, BorderLayout.NORTH);
        add(questionPanel, BorderLayout.CENTER);

//        questionBank = new QuestionBank();
        questionNumber = 0;
        score = new Score();

        radioBtnEventHandler = new RadioButtonEventHandler();

    }

    class RadioButtonEventHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {

            if (choiceA.isSelected()) {
                customerAnswer = choiceA.getText();
            }
            else if (choiceB.isSelected()) {
                customerAnswer = choiceB.getText();
            }
            else if (choiceC.isSelected()) {
                customerAnswer = choiceC.getText();
            }
            else if (choiceD.isSelected()) {
                customerAnswer = choiceD.getText();
            }
        }
    }

    private String changeTopic(String topic) {
        switch (topic) {
            case "History":
                return "Maths";
            case "Maths":
                return "Animals";
            case "Animals":
                return "Maths";
            default:
                return null;
        }
    }

    @Override
    public void show() {
        super.show();
        count = 30;
        timer.restart();
    }

    @Override
    public void hide() {
        super.hide();
        count = 30;
    }

    /**
     * The timer panel contains a timer shown area, and a stop button.
     * When click the stop button, the quiz will stop, and a score will show up.
     */
    private JPanel createTimerPanel() {
        JPanel timeControl = new JPanel();
        displayTime = new JLabel();
        count = 30;

        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count <= 0) {
                    timer.stop();

                    // go to next question

                }
                displayTime.setText(count.toString());
                if (isVisible()){
                    count --;
                }
            }
        };
        timer = new Timer(1000, taskPerformer);
        timer.start();

        timeControl.add(displayTime);

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Quiz is now stopped.");
                timer.stop();
                mainPanel = (JPanel) timeControl.getParent().getParent();

                mainCards = (CardLayout) mainPanel.getLayout();
                mainCards.show(mainPanel, "barGraph");
            }
        });

        timeControl.add(stopButton);
        timeControl.setBorder(outline);
        return timeControl;
    }

    private JPanel createQuestionPanel() {

        Question oneQuestion = QuestionBank.getQBank().getOneQuestion(startTopic, questionNumber);
        if (oneQuestion == null) {
            questionNumber = 0;
            oneQuestion = QuestionBank.getQBank().getOneQuestion(changeTopic(startTopic), questionNumber);
        }
        score.addQuestionAttempt(startTopic);

        JPanel questionPanel = new JPanel();
        questionPanel.setBorder(outline);
        CardLayout cardLayout = new CardLayout();
        questionPanel.setLayout(cardLayout);

        JPanel multiChoicesPanel = createMultipleChoicesPanel(oneQuestion);
        JPanel oneWordAnswerPanel = createOneWordAnswerPanel(oneQuestion);

        questionPanel.add(multiChoicesPanel, "multiChoices");
        questionPanel.add(oneWordAnswerPanel, "oneWordAnswer");

        switch (oneQuestion.getType()) {
            case MULTIPLECHOICES:
                cardLayout.show(questionPanel, "multiChoices");
                break;
            case ONEWORDANSWER:
                cardLayout.show(questionPanel, "oneWordAnswer");
                break;
        }

        JPanel masterPanel = new JPanel();
        masterPanel.setLayout(new BorderLayout());
        masterPanel.add(questionPanel, BorderLayout.CENTER);

        String correctAnswer = oneQuestion.getAnswer();

        JButton nextQuestionBtn = new JButton("Next");
        nextQuestionBtn.setFont(fontSmall);
        nextQuestionBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (customerAnswer.equals(correctAnswer)) {
                    score.addCorrectQNo(startTopic);
                }
                JOptionPane.showMessageDialog(null, "Go to the next question.");
            }
        });
        masterPanel.add(nextQuestionBtn, BorderLayout.SOUTH);

        JButton nextTopic = new JButton("Next Topic");
        nextTopic.setFont(fontSmall);
        nextTopic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (customerAnswer.equals(correctAnswer)) {
                    score.addCorrectQNo(startTopic);
                }
                JOptionPane.showMessageDialog(null, "Select another topic.");
                String newTopic = changeTopic(startTopic);
                startTopic = newTopic;

            }
        });
        masterPanel.add(nextTopic, BorderLayout.SOUTH);


        return masterPanel;
    }

    private JPanel createMultipleChoicesPanel(Question oneQuestion) {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel();

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JLabel question = new JLabel(oneQuestion.getQuestion());
        question.setFont(fontBig);
        leftPanel.add(question);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        choiceA = new JRadioButton(oneQuestion.getChoice()[0]);
        choiceA.setFont(fontSmall);
        leftPanel.add(choiceA);

        choiceB = new JRadioButton(oneQuestion.getChoice()[1]);
        choiceB.setFont(fontSmall);
        leftPanel.add(choiceB);

        choiceC = new JRadioButton(oneQuestion.getChoice()[2]);
        choiceC.setFont(fontSmall);
        leftPanel.add(choiceC);

        choiceD = new JRadioButton(oneQuestion.getChoice()[3]);
        choiceD.setFont(fontSmall);
        leftPanel.add(choiceD);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // right panel to show image if there is one
        JPanel rightPanel = new JPanel();
        if (oneQuestion.getImage() != null) {
            try {
                String imagePath = oneQuestion.getImage();
                URL url = new URL(imagePath);
                BufferedImage image = ImageIO.read(url);
                JLabel label = new JLabel(new ImageIcon(image));
                rightPanel.add(label);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createOneWordAnswerPanel(Question oneQuestion) {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel();

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JLabel question = new JLabel(oneQuestion.getQuestion());
        question.setFont(fontBig);
        leftPanel.add(question);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JTextField answerArea = new JTextField(20);
        leftPanel.add(answerArea);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // right panel to show image if there is one
        JPanel rightPanel = new JPanel();
        if (oneQuestion.getImage() != null) {
            try {
                String imagePath = oneQuestion.getImage();
                URL url = new URL(imagePath);
                BufferedImage image = ImageIO.read(url);
                JLabel label = new JLabel(new ImageIcon(image));
                rightPanel.add(label);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }



}
