package QuizData;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lexie on 11/28/16.
 */
public class Score {

    int eachQuestionSocre;

    Map<String, Integer[]> topicScores;

    /**
     * The Object Score stores a hashmap of topicScores and a score that worth for each question that is answered correctly.
     * For the HashMap:
     * @key String topic name
     * @value Integer[] array = new Integer[2].
     * array[0] = number of total questions attempt on this topic.
     * array[1] = number of questions that are answered correctly on this topic.
     */
    public Score() {
        eachQuestionSocre = 1;
        topicScores = new HashMap<String, Integer[]>();

    }

    /**
     * add 1 to the total number of questions attempted under the topic name
     * @param topicName
     */
    public void addQuestionAttempt(String topicName) {
        if (topicScores.containsKey(topicName)) {
            Integer[] currentScore = topicScores.get(topicName);
            currentScore[0]++;
            topicScores.put(topicName, currentScore);
        }
        else {
            Integer[] currentScore = new Integer[2];
            currentScore[0] = 1;
            currentScore[1] = 0;
            topicScores.put(topicName, currentScore);
        }
    }

    /**
     * add score to the total score under the topic name when answered correctly
     * @param topicName
     */
    public void addCorrectQNo(String topicName) {
        Integer[] currentScore = topicScores.get(topicName);
        currentScore[1]++;
        topicScores.put(topicName, currentScore);
    }


    public int getScoreForEachQuestion() {
        return eachQuestionSocre;
    }

    /**
     *
     * @return return a HashMap with topic name as the key, an Integer[] array as a value;
     * the Integer[] array has a length of 2.
     * array[0] is the total number of questions attempt
     * array[1] is the total
     */
    public Map<String, Integer[]> getTotalScores() {
        return topicScores;
    }


}
