package blackjack.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String id;
    private List<blackjack.model.Card> cards = new ArrayList<blackjack.model.Card>();
    private Boolean showScore;

    public Player(String id, Boolean showScore) {
        this.id = id;
        this.showScore = showScore;
    }

    public void reset() {
        cards.clear();
    }

    public void add(blackjack.model.Card card) {
        cards.add(card);
    }

    public Integer score() {
        int score = 0, aces = 0;

        for (blackjack.model.Card card : cards) {
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
    public String getId() {
        return id;
    }

    @JsonSerialize
    public String hand() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < cards.size(); ++i) {
            if (i == 0 || showScore) {
                stringBuffer.append(cards.get(i).toString());
            }
            if (showScore && (i+1) < cards.size()) {
                stringBuffer.append(", ");
            }
        }
        if (showScore) {
            stringBuffer.append(" Score: ").append(score());
        }
        return stringBuffer.toString();
    }

    public void setShowScore(Boolean showScore) {
        this.showScore = showScore;
    }
}
