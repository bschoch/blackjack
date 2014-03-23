package blackjack;

import blackjack.model.*;
import blackjack.model.Error;

import java.util.HashMap;
import java.util.Map;

public class GameRunner {

    private final Map<Integer, Game> gameMap = new HashMap<Integer, Game>();

    public GameResponse deal() {
        Game game = new Game();
        gameMap.put(game.getId(), game);
        game.deal();
        return new GameResponse(game);
    }

    public GameResponse stand(Integer gameId) {
        GameResponse gameResponse = getGameResponse(gameId);

        if (gameResponse.getErrors().isEmpty()) {
            gameResponse.getGame().stand();
        }

        return gameResponse;
    }

    public GameResponse hit(Integer gameId) {
        GameResponse gameResponse = getGameResponse(gameId);

        if (gameResponse.getErrors().isEmpty()) {
            gameResponse.getGame().hit();
        }

        return gameResponse;
    }

    private GameResponse getGameResponse(Integer gameId) {
        GameResponse gameResponse = new GameResponse(gameMap.get(gameId));

        if (gameResponse.getGame() == null) {
            gameResponse.addError(Error.GAME_NOT_FOUND);
        } else if (gameResponse.getGame().getFinished()) {
            gameResponse.addError(Error.GAME_COMPLETE);
        }

        return gameResponse;
    }
}
