package blackjack.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

public class GameResponse {
    private final List<Error> errors = new ArrayList<Error>();
    private final Account account;
    private final Game game;

    public GameResponse(List<Error> errors, Account account, Game game) {
        this.account = account;
        this.game = game;
        if (errors != null) {
            this.errors.addAll(errors);
        }
    }

    @JsonSerialize
    public List<Error> getErrors() {
        return errors;
    }

    @JsonSerialize
    public Account getAccount() {
        return account;
    }

    @JsonSerialize
    public Game getGame() {
        return game;
    }
}
