package UI;

import QuizData.Score;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Map;

/**
 * Created by Lexie on 11/25/16.
 */
public class ScorePanel extends JPanel {
    Score score;
    Map<String, Integer[]> scoreOnTopic;
    int eachScore;

    String topicName1 = "History";
    String topicName2 = "Maths";
    String topicName3 = "Animals";
    Integer[] scoreTopic1;
    Integer[] scoreTopic2;
    Integer[] scoreTopic3;

    Font fontBig = new Font("Arial", Font.BOLD, 30);
    Font fontSmall = new Font("Arial", Font.PLAIN, 20);

    public ScorePanel(Score score) {
        this.score = score;
        scoreOnTopic = score.getTotalScores();
        eachScore = score.getScoreForEachQuestion();

        if (!scoreOnTopic.containsKey(topicName1)) {
            scoreTopic1 = new Integer[]{0, 0};
        }
        else {
            scoreTopic1 = scoreOnTopic.get(topicName1);
        }

        if (!scoreOnTopic.containsKey(topicName2)) {
            scoreTopic2 = new Integer[]{0, 0};
        }
        else {
            scoreTopic2 = scoreOnTopic.get(topicName2);
        }

        if (!scoreOnTopic.containsKey(topicName3)) {
            scoreTopic3 = new Integer[]{0, 0};
        }
        else {
            scoreTopic3 = scoreOnTopic.get(topicName3);
        }

        setLayout(new BorderLayout());
        JPanel scorePanel = createScorePanel();
        BarChart scoreBar = createBarChart();
        add(scorePanel, BorderLayout.NORTH);
        add(scoreBar, BorderLayout.CENTER);
    }

    private JPanel createScorePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        int totalQuestionAttempted = scoreTopic1[0] + scoreTopic2[0] + scoreTopic3[0];
        JLabel label1 = new JLabel("Number of questions attempted: " + totalQuestionAttempted);
        label1.setFont(fontBig);
        panel.add(label1);

        int correctQuestionNo = scoreTopic1[1] + scoreTopic2[1] + scoreTopic3[1];
        JLabel label2 = new JLabel("Number of Questions that get correct: " +correctQuestionNo);
        label2.setFont(fontBig);
        panel.add(label2);

        int totalScore = correctQuestionNo * eachScore;
        JLabel label3 = new JLabel("Total Score: " + totalScore);
        return panel;

    }

    private BarChart createBarChart() {
        int[] array = new int[3];
        array[0] = scoreTopic1[1];
        array[1] = scoreTopic2[1];
        array[2] = scoreTopic3[1];

        BarChart barChart = new BarChart();
//        barChart.setPreferredSize(new Dimension(800, 800));
        for(int i = 0; i < array.length; i++) {
            barChart.addBar(array[i]);
        }
        barChart.setBorder(new CompoundBorder(new LineBorder(Color.BLUE), new EmptyBorder(10, 10, 10, 10)));
        return barChart;

    }
}
