package QuizData;


import java.io.FileReader;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.*;

/**
 * Created by Lexie on 11/27/16.
 */

public class QuestionBank {

    String name1 = "History";
    Question[] name1Array;

    String name2 = "Maths";
    Question[] name2Array;

    String name3 = "Animals";
    Question[] name3Array;

    private static final QuestionBank soleInstance = new QuestionBank();
    private Map<String, Question[]> questionBank = new HashMap<>();

    /**
     * parse json file and store it in a HashMap with topic name as the key, and an Question[] array as the value
     */
    private QuestionBank() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("questions.json"));
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray history = (JSONArray) jsonObject.get(name1);
            name1Array = createNameArray(name1, history);

            JSONArray maths = (JSONArray) jsonObject.get(name2);
            name2Array = createNameArray(name2, maths);

            JSONArray animals = (JSONArray) jsonObject.get(name3);
            name3Array = createNameArray(name3, animals);

            questionBank.put(name1, name1Array);
            questionBank.put(name2, name2Array);
            questionBank.put(name3, name3Array);

        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public static QuestionBank getQBank() {
        return soleInstance;
    }

    /**
     *
     * @param name: topic name
     * @param topicArea: an JSONArray with the current topic
     * @return
     */
    private Question[] createNameArray(String name, JSONArray topicArea) {
        int topicSize = topicArea.size();
        Question[] nameArray = new Question[topicSize];
        for (int i = 0; i < topicSize; i++) {
            JSONObject historyQuestions = (JSONObject) topicArea.get(i);

            String question = (String) historyQuestions.get("question");
            String answer = (String) historyQuestions.get("answer");
            // create an Question object and add topic to the object
            Question oneQuestion = new Question(question, answer);
            oneQuestion.setTopic(name);

            // add options to the Question object if available
            if (historyQuestions.get("options") != null) {
                JSONArray jsonOptions = (JSONArray) historyQuestions.get("options");
                int length = jsonOptions.size();
                String[] options = new String[length];
                Iterator<String> iterator = jsonOptions.iterator();
                int j = 0;
                while (iterator.hasNext()) {
                    options[j++] = iterator.next();
                }
                oneQuestion.setChoice(options);
            }

            // add type to the Question object
            String type = (String) historyQuestions.get("type");
            QuestionType questionType;
            if (type.equals("Multiple Choice")) {
                questionType = QuestionType.MULTIPLECHOICES;
            } else {
                questionType = QuestionType.ONEWORDANSWER;
            }
            oneQuestion.setType(questionType);

            // add imageURL to the Question object if available
            if (historyQuestions.get("image") != null) {
                String image = (String) historyQuestions.get("image");
                oneQuestion.setImage(image);
            }

            // add the Question object to the question array that belongs to the topic
            nameArray[i] = oneQuestion;
        }
        return nameArray;
    }

    /**
     * passes a topic name and an index number to get a Question object on this topic
     * @param topicName: String
     * @param curQuestionNo: int
     * @return one Question object from the Question[] array
     * If the curQuestionNo is greater than the currentQuestionArray,
     * will return null, and print out a message to ask the system to change a topic name
     */
    public Question getOneQuestion(String topicName, int curQuestionNo) {
        Question[] currentQuestionArray = questionBank.get(topicName);
        if (currentQuestionArray == null || currentQuestionArray.length == 0 || curQuestionNo < currentQuestionArray.length) {
            return currentQuestionArray[curQuestionNo];
        }
        else {
            System.out.println("No questions available for this topic. Please change a topic");
            return null;
        }
    }


}
