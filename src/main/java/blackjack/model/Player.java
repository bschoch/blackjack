package blackjack.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private static Integer idCount = 0;
    private int id;
    private int bet;
    private List<Card> cards = new ArrayList<Card>();
    private Result result;
    private boolean finished;
    private boolean showScore;

    public Player(boolean showScore) {
        this.id = idCount++;
        this.showScore = showScore;
    }

    public void reset() {
        cards.clear();
    }

    public void add(Card card) {
        cards.add(card);
    }

    @JsonIgnore
    public int calculateScore() {
        int score = 0, aces = 0;

        for (Card card : cards) {
            if (card.getNumber() == Card.Number.Ace) {
                score += 11;
                ++aces;
            } else if (card.getNumber().ordinal() >= Card.Number.Ten.ordinal()) {
                score += 10;
            } else {
                score += card.getNumber().ordinal() + 1;
            }
        }

        while (aces > 0 && score > 21) {
            score -= 10;
            aces--;
        }

        return score;
    }

    @JsonSerialize
    public int getId() {
        return id;
    }

    @JsonSerialize
    public int getBet() {
        return bet;
    }

    @JsonSerialize
    public List<Card> getCards() {
        if (showScore) {
            return cards;
        }
        return cards.subList(0, 1);
    }

    @JsonSerialize
    public Integer score() {
        if (showScore) {
            return calculateScore();
        }
        return null;
    }

    @JsonSerialize
    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @JsonIgnore
    public boolean eligibleForSplit() {
        if (cards.size() == 2 && (cards.get(0).getNumber() == cards.get(1).getNumber() || (cards.get(0).getNumber().ordinal() >= 9 && cards.get(1).getNumber().ordinal() >= 9))) {
            return true;
        }
        return false;
    }

    //assumes eligible
    @JsonIgnore
    Card splitCards() {
        return cards.remove(0);
    }

    public void setShowScore(boolean showScore) {
        this.showScore = showScore;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public boolean getFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public enum Result {
        PLAYER,
        DEALER,
        PUSH
    }
}
