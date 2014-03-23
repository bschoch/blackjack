package blackjack.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

public class GameResponse {
    private final List<blackjack.model.Error> errors = new ArrayList<blackjack.model.Error>();
    private final blackjack.model.Game game;

    public GameResponse(blackjack.model.Game game) {
        this.game = game;
    }

    public void addError(blackjack.model.Error error) {
        this.errors.add(error);
    }

    @JsonSerialize
    public List<blackjack.model.Error> getErrors() {
        return errors;
    }

    @JsonSerialize
    public blackjack.model.Game getGame() {
        return game;
    }
}
