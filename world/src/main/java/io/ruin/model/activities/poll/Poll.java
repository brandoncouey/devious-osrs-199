package io.ruin.model.activities.poll;

import java.util.ArrayList;
import java.util.List;

public class Poll {

    public final String pollTitle;
    public final String pollDescription;
    public final String pollEndDate;
    public final int pollNumber;
    public int totalVotes;
    public final List<PollQuestion> questions;
    public boolean limitVote;
    public List<String> votedUids;

    public Poll(String pollTitle, String pollDescription, String pollEndDate, boolean limitVote, int pollNumber, List<PollQuestion> questions) {
        this.pollTitle = pollTitle;
        this.pollDescription = pollDescription;
        this.pollEndDate = pollEndDate;
        this.limitVote = limitVote;
        this.pollNumber = pollNumber;
        this.questions = questions;
        this.votedUids = new ArrayList<>();
    }

    public List<PollQuestion> getQuestions() {
        return questions;
    }

    public String getPollTitle() {
        return pollTitle;
    }

    public int getPollNumber() {
        return pollNumber;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void incrementTotalVotes() {
        totalVotes++;
    }

    public String getPollDescription() {
        return pollDescription;
    }

    public String getEndDate() {
        return pollEndDate;
    }

    public boolean isLimitVote() {
        return limitVote;
    }

    public List<String> getVotedUids() {
        if (votedUids == null) {
            votedUids = new ArrayList<>();
        }
        return votedUids;
    }
}