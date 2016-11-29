package UI;

import QuizData.Question;
import QuizData.QuestionBank;
import QuizData.QuestionType;
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
    String correctAnswer;
    Question oneQuestion;
    JPanel timerPanel;
    JPanel masterPanel;
    JPanel questionPanel;
    JPanel currentQuestionPanel;
    Display display;

    int colorIndex;

    public QuestionPanel(String startTopic) {

        setLayout(new BorderLayout());
        setBorder(outline);

        this.startTopic = startTopic;
        score = new Score();
        questionNumber = 0;
        radioBtnEventHandler = new RadioButtonEventHandler();

        timerPanel = createTimerPanel();
        questionPanel = createMasterPanel();
        add(timerPanel, BorderLayout.NORTH);
        add(questionPanel, BorderLayout.CENTER);
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
                return "History";
            default:
                return null;
        }
    }

    /**
     * The timer panel contains a timer shown area, and a stop button.
     * When click the stop button, the quiz will stop, and a score will show up.
     */
    private JPanel createTimerPanel() {
        JPanel timeControl = new JPanel();
        displayTime = new JLabel();

        display = new Display();
        colorIndex = 0;
        count = 30;

        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count > 10) {
                    displayTime.setFont(fontSmall);
                    displayTime.setForeground(Color.BLUE);
                }
                if (count <= 10 && count > 0) {
                    displayTime.setFont(fontBig);
                    displayTime.setForeground(Color.RED);
                }
                if (count <= 0) {

                    questionNumber++;
                    if (QuestionBank.getQBank().getOneQuestion(startTopic, questionNumber) == null) {
                        String newTopic = changeTopic(startTopic);
                        startTopic = newTopic;
                        questionNumber = 0;
                    }
                    oneQuestion = QuestionBank.getQBank().getOneQuestion(startTopic, questionNumber);
                    correctAnswer = oneQuestion.getAnswer();
                    JPanel newQuestionPanel = createQuestionPanel(oneQuestion);
                    newQuestionPanel.setVisible(true);
                    masterPanel.remove(currentQuestionPanel);
                    currentQuestionPanel = newQuestionPanel;
                    masterPanel.add(newQuestionPanel, BorderLayout.CENTER);
                    masterPanel.revalidate();
                    masterPanel.repaint();
                    count = 30;

                    timer.restart();
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
        timeControl.add(display);

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Quiz is now stopped.");
                timer.stop();

                timeControl.getParent().setVisible(false);
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(timeControl);
                JPanel scorePanel = new ScorePanel(score);
                scorePanel.setVisible(true);
                topFrame.add(scorePanel);
            }
        });

        timeControl.add(stopButton);
        timeControl.setBorder(outline);
        return timeControl;
    }

    private JPanel createMasterPanel() {
        oneQuestion = QuestionBank.getQBank().getOneQuestion(startTopic, questionNumber);
        if (oneQuestion == null) {
            questionNumber = 0;
            oneQuestion = QuestionBank.getQBank().getOneQuestion(changeTopic(startTopic), questionNumber);
        }
        score.addQuestionAttempt(startTopic);

        masterPanel = new JPanel();
        masterPanel.setLayout(new BorderLayout());

        JPanel questionPanel = createQuestionPanel(oneQuestion);
        currentQuestionPanel = questionPanel;
        masterPanel.add(questionPanel);

        JPanel buttonPanel = new JPanel();
        correctAnswer = oneQuestion.getAnswer();

        JButton nextQuestionBtn = new JButton("Next");
        nextQuestionBtn.setFont(fontSmall);
        nextQuestionBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                score.addQuestionAttempt(startTopic);

                if (customerAnswer != null && customerAnswer.equals(correctAnswer)) {
                    score.addCorrectQNo(startTopic);
                }
                questionNumber++;
                if (QuestionBank.getQBank().getOneQuestion(startTopic, questionNumber) == null) {
                    String newTopic = changeTopic(startTopic);
                    startTopic = newTopic;
                    questionNumber = 0;
                }
                oneQuestion = QuestionBank.getQBank().getOneQuestion(startTopic, questionNumber);
                correctAnswer = oneQuestion.getAnswer();
                JPanel newQuestionPanel = createQuestionPanel(oneQuestion);
                newQuestionPanel.setVisible(true);
                masterPanel.remove(currentQuestionPanel);
                currentQuestionPanel = newQuestionPanel;
                masterPanel.add(newQuestionPanel, BorderLayout.CENTER);
                masterPanel.revalidate();
                masterPanel.repaint();

                count = 30;
                colorIndex = 0;
                timer.restart();

            }
        });
        buttonPanel.add(nextQuestionBtn);

        JButton nextTopic = new JButton("Next Topic");
        nextTopic.setFont(fontSmall);
        nextTopic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                score.addQuestionAttempt(startTopic);

                if (customerAnswer != null && customerAnswer.equals(correctAnswer)) {
                    score.addCorrectQNo(startTopic);
                }
                String newTopic = changeTopic(startTopic);
                startTopic = newTopic;
                questionNumber = 0;
                oneQuestion = QuestionBank.getQBank().getOneQuestion(startTopic, questionNumber);
                correctAnswer = oneQuestion.getAnswer();
                JPanel newQuestionPanel = createQuestionPanel(oneQuestion);
                newQuestionPanel.setVisible(true);
                masterPanel.remove(currentQuestionPanel);
                currentQuestionPanel = newQuestionPanel;
                masterPanel.add(newQuestionPanel, BorderLayout.CENTER);
                masterPanel.revalidate();
                masterPanel.repaint();

                count = 30;
                timer.restart();

            }
        });
        buttonPanel.add(nextTopic);
        masterPanel.add(buttonPanel, BorderLayout.SOUTH);

        return masterPanel;
    }
    private JPanel createQuestionPanel(Question question) {
        switch (question.getType()) {
            case MULTIPLECHOICES:
                JPanel multiChoicesPanel = createMultipleChoicesPanel(question);
                return multiChoicesPanel;
            case ONEWORDANSWER:
                JPanel oneWordAnswerPanel = createOneWordAnswerPanel(question);
                return oneWordAnswerPanel;
            default:
                return null;
        }
    }

    private JPanel createMultipleChoicesPanel(Question oneQuestion) {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel();

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JLabel question = new JLabel(oneQuestion.getQuestion());
        question.setFont(fontSmall);

        leftPanel.add(question);

        ButtonGroup group = new ButtonGroup();
        choiceA = new JRadioButton(oneQuestion.getChoice()[0]);
        choiceA.setFont(fontSmall);
        choiceA.addActionListener(radioBtnEventHandler);

        choiceB = new JRadioButton(oneQuestion.getChoice()[1]);
        choiceB.setFont(fontSmall);
        choiceB.addActionListener(radioBtnEventHandler);
        group.add(choiceB);

        choiceC = new JRadioButton(oneQuestion.getChoice()[2]);
        choiceC.setFont(fontSmall);
        choiceC.addActionListener(radioBtnEventHandler);
        group.add(choiceC);

        choiceD = new JRadioButton(oneQuestion.getChoice()[3]);
        choiceD.setFont(fontSmall);
        choiceD.addActionListener(radioBtnEventHandler);

        group.add(choiceA);
        group.add(choiceB);
        group.add(choiceC);
        group.add(choiceD);
        leftPanel.add(choiceA);
        leftPanel.add(choiceB);
        leftPanel.add(choiceC);
        leftPanel.add(choiceD);

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

        JLabel question = new JLabel("<html><p>" + oneQuestion.getQuestion() + "</p></html>");
        question.setFont(fontSmall);

        leftPanel.add(question);

        JTextField answerArea = new JTextField(5);
        answerArea.setBorder(outline);
        answerArea.setPreferredSize(new Dimension(200, 24));
        leftPanel.add(answerArea);

        customerAnswer = answerArea.getText();

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
            finally {
                JLabel label = new JLabel(" ");
                rightPanel.add(label);
            }
        }

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }
    private class Display extends JPanel {

        public void setColor(Color color){

            setBackground(color);
        }
    }


}
