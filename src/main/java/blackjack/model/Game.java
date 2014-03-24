package blackjack.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {

    private static int idCount = 0;
    private final int id = idCount++;
    private final Player dealer = new Player(false);
    private final List<Player> players = new ArrayList<Player>(Arrays.asList(new Player(true)));
    private final Account account;
    private final Deck deck = new Deck();

    public Game(Account account, Integer bet) {
        dealer.setBet(-1);
        this.account = account;
        this.getPlayers().get(0).setBet(bet);
    }

    @JsonSerialize
    public int getId() {
        return id;
    }

    @JsonSerialize
    public Player getDealer() {
        return dealer;
    }

    @JsonSerialize
    public List<Player> getPlayers() {
        return players;
    }

    @JsonIgnore
    public Player getNextPlayer() {
        for (Player player : players) {
            if (!player.getFinished()) {
                return player;
            }
        }
        return null;
    }

    @JsonSerialize
    public Integer getNextPlayerId() {
        Player player = getNextPlayer();
        if (player != null) {
            return player.getId();
        }
        return null;
    }

    public void deal(Card.Number debugSplitNumber) {
        deck.shuffle();
        players.get(0).add(deck.deal(debugSplitNumber));
        dealer.add(deck.deal());
        players.get(0).add(deck.deal(debugSplitNumber));
        dealer.add(deck.deal());
    }

    public void hit(Player player) {
        player.add(deck.deal());
        if (player.calculateScore() > 21) {
            finishPlayer(player);
            if (getGameFinished()) {
                finishGame();
            }
        }
    }

    public boolean doubleDown(Player player) {
        if (!account.adjustBalance(-player.getBet())) {
            return false;
        }
        player.setBet(player.getBet() * 2);
        player.add(deck.deal());
        stand(player);
        return true;
    }

    public void split() {
        Player player1 = players.get(0);
        Player player2 = new Player(true);
        player2.add(player1.splitCards());
        player2.setBet(player1.getBet());
        player2.setShowScore(false);
        players.add(player2);
        player1.add(deck.deal());
        player2.add(deck.deal());
    }

    public boolean eligibleForSplit() {
        return !(players.size() != 1 || !players.get(0).eligibleForSplit());
    }

    public void stand(Player player) {
        finishPlayer(player);
        if (getGameFinished()) {
            while (dealer.calculateScore() < 17) {
                dealer.add(deck.deal());
            }
            finishGame();
        }
    }

    @JsonSerialize
    public boolean getGameFinished() {
        boolean finished = true;
        for (Player player : players) {
            if (!player.getFinished()) {
                finished = false;
                break;
            }
        }
        return finished;
    }

    @JsonIgnore
    public Account getAccount() {
        return account;
    }

    private void finishGame() {
        getDealer().setShowScore(true);
        getDealer().setFinished(true);
        for (Player player : players) {
            if (push(player)) {
                player.setResult(Player.Result.PUSH);
                account.adjustBalance(player.getBet());
            } else if (playerWins(player)) {
                player.setResult(Player.Result.PLAYER);
                account.adjustBalance(2 * player.getBet());
            } else {
                player.setResult(Player.Result.DEALER);
            }
        }
    }

    // assumes game is finished
    private boolean playerWins(Player player) {
        return player.calculateScore() <= 21 && (dealer.calculateScore() > 21 || dealer.calculateScore() < player.calculateScore());
    }

    // assumes game is finished
    private boolean push(Player player) {
        return player.calculateScore() <= 21 && dealer.calculateScore() <= 21 && player.calculateScore() == dealer.calculateScore();
    }

    private void finishPlayer(Player player) {
        player.setFinished(true);
        Player next = getNextPlayer();
        if (next != null) {
            next.setShowScore(true);
        }
    }
}
