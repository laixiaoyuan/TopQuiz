package QuizData;

/**
 * Created by Lexie on 11/27/16.
 */
public class Question {
    String topic;
    String question;
    String[] choice;
    String answer;
    String image;
    QuestionType type;
    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getChoice() {
        return choice;
    }

    public void setChoice(String[] choice) {
        this.choice = choice;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }
}
