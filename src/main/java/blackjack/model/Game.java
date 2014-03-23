package blackjack.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;

public class Game {

    private static Integer idCount = 0;
    private Integer id = idCount++;
    private Player dealer = new Player("dealer", false);
    private Player player = new Player("player", true);
    private Deck deck = new Deck();
    private Boolean finished = false;

    @JsonSerialize
    public Integer getId() {
        return id;
    }

    @JsonSerialize
    public Player getDealer() {
        return dealer;
    }

    @JsonSerialize
    public Player getPlayer() {
        return player;
    }

    public void deal() {
        deck.shuffle();
        player.add(deck.deal());
        dealer.add(deck.deal());
        player.add(deck.deal());
        dealer.add(deck.deal());
    }

    public void hit() {
        player.add(deck.deal());
        if (player.score() > 21) {
            setFinished(true);
        }
    }

    public void stand() {
        while (dealer.score() < 17) {
            dealer.add(deck.deal());
        }
        setFinished(true);
    }

    @JsonSerialize
    public String getGameResult() {
        if (finished) {
            if (player.score() > 21 || (dealer.score() <= 21 && dealer.score() > player.score())) {
                return "DEALER WINS";
            } else {
                return "PLAYER WINS";
            }
        }
        return null;
    }

    @JsonSerialize
    public Boolean getFinished() {
        return finished;
    }

    private void setFinished(Boolean finished) {
        getDealer().setShowScore(finished);
        this.finished = finished;
    }
}
