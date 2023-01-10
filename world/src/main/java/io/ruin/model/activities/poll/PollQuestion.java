package io.ruin.model.activities.poll;

import java.util.List;

public class PollQuestion {

    public final String question;
    public final List<Integer> votes;
    public final List<String> answers;

    public PollQuestion(String question, List<Integer> votes, List<String> answers) {
        this.question = question;
        this.votes = votes;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public List<Integer> getVotes() {
        return votes;
    }
}