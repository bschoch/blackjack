package blackjack.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class Game {

    private static Integer idCount = 0;
    private final Integer id = idCount++;
    private Integer bet;
    private final Player dealer = new Player(false);
    private final Player player = new Player(true);
    private final Account account;
    private final Deck deck = new Deck();
    private Boolean finished = false;

    public Game(Account account, Integer bet) {
        this.account = account;
        this.bet = bet;
    }

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
            finishGame();
        }
    }

    public Boolean doubleDown() {
        if (!account.adjustBalance(-bet)) {
            return false;
        }

        bet *= 2;
        player.add(deck.deal());
        stand();
        return true;
    }

    public void stand() {
        while (dealer.score() < 17) {
            dealer.add(deck.deal());
        }
        finishGame();
    }

    @JsonSerialize
    public String getGameResult() {
        if (finished) {
            if (push()) {
                return "PUSH";
            } else if (playerWins()) {
                return "PLAYER WINS";
            } else {
                return "DEALER WINS";
            }
        }
        return null;
    }

    @JsonSerialize
    public Boolean getFinished() {
        return finished;
    }

    @JsonSerialize
    public Integer getBet() {
        return bet;
    }

    @JsonIgnore
    public Account getAccount() {
        return account;
    }

    private void finishGame() {
        getDealer().setShowScore(true);
        this.finished = true;
        if (push()) {
            account.adjustBalance(bet);
        } else if (playerWins()) {
            account.adjustBalance(2 * bet);
        }
    }

    // assumes game is finished
    private Boolean playerWins() {
        return player.score() <= 21 && (dealer.score() > 21 || dealer.score() < player.score());
    }

    // assumes game is finished
    private Boolean push() {
        return player.score() <= 21 && dealer.score() <= 21 && player.score().equals(dealer.score());
    }
}
