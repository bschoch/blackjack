package blackjack;

import blackjack.model.*;
import blackjack.model.Error;

import java.util.*;

public class GameRunner {

    private final Map<Integer, Game> gameMap = new HashMap<Integer, Game>();
    private final Map<Integer, Account> accountMap = new HashMap<Integer, Account>();

    public GameResponse createAccount(Integer amount) {
        if (amount < 0) {
            return new GameResponse(Arrays.asList(Error.NEGATIVE_ACCOUNT_BALANCE), null, null);
        }

        Account account = new Account(amount);
        accountMap.put(account.getId(), account);
        return new GameResponse(null, account, null);
    }

    public GameResponse getAccount(Integer accountId) {
        Account account = accountMap.get(accountId);

        if (account == null) {
            return new GameResponse(Arrays.asList(Error.ACCOUNT_NOT_FOUND), null, null);
        }

        return new GameResponse(null, account, null);
    }


    public GameResponse deal(Integer accountId, Integer bet, Card.Number debugSplitNumber) {
        Account account = accountMap.get(accountId);
        if (account == null) {
            return new GameResponse(Arrays.asList(Error.ACCOUNT_NOT_FOUND), null, null);
        }
        if (bet < 0) {
            return new GameResponse(Arrays.asList(Error.BET_NEGATIVE_AMOUNT), account, null);
        }
        if (!account.adjustBalance(-bet)) {
            return new GameResponse(Arrays.asList(Error.NEGATIVE_ACCOUNT_BALANCE), account, null);
        }

        Game game = new Game(account, bet);
        gameMap.put(game.getId(), game);
        game.deal(debugSplitNumber);
        return new GameResponse(null, account, game);
    }

    public GameResponse stand(Integer gameId) {
        GameResponse gameResponse = getGameResponse(gameId);

        if (gameResponse.getErrors().isEmpty()) {
            gameResponse.getGame().stand(gameResponse.getGame().getNextPlayer());
        }

        return gameResponse;
    }

    public GameResponse hit(Integer gameId) {
        GameResponse gameResponse = getGameResponse(gameId);

        if (gameResponse.getErrors().isEmpty()) {
            gameResponse.getGame().hit(gameResponse.getGame().getNextPlayer());
        }

        return gameResponse;
    }

    public GameResponse doubleDown(Integer gameId) {
        GameResponse gameResponse = getGameResponse(gameId);

        if (gameResponse.getErrors().isEmpty()) {
            if (!gameResponse.getGame().doubleDown(gameResponse.getGame().getNextPlayer())) {
                gameResponse.getErrors().add(Error.NEGATIVE_ACCOUNT_BALANCE);
            }
        }

        return gameResponse;
    }

    public GameResponse split(Integer gameId) {
        GameResponse gameResponse = getGameResponse(gameId);

        if (gameResponse.getErrors().isEmpty()) {
            if (!gameResponse.getGame().eligibleForSplit()) {
                gameResponse.getErrors().add(Error.INELIGIBLE_FOR_SPLIT);
            } else if (!gameResponse.getAccount().adjustBalance(-gameResponse.getGame().getNextPlayer().getBet())) {
                gameResponse.getErrors().add(Error.NEGATIVE_ACCOUNT_BALANCE);
            } else {
                gameResponse.getGame().split();
            }
        }

        return gameResponse;
    }

    private GameResponse getGameResponse(Integer gameId) {
        Game game = gameMap.get(gameId);

        if (game == null) {
            return new GameResponse(Arrays.asList(Error.GAME_NOT_FOUND), null, null);
        } else if (game.getGameFinished()) {
            return new GameResponse(Arrays.asList(Error.GAME_COMPLETE), game.getAccount(), game);
        }

        return new GameResponse(null, game.getAccount(), game);
    }
}
