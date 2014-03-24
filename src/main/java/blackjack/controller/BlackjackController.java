package blackjack.controller;

import blackjack.GameRunner;
import blackjack.model.Card;
import blackjack.model.GameResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class BlackjackController {

    private final GameRunner gameRunner = new GameRunner();

    @RequestMapping(value = "/accounts/new/{amount}",
            method = RequestMethod.GET)
    public @ResponseBody GameResponse newAccount(@PathVariable Integer amount) {
        return gameRunner.createAccount(amount);
    }

    @RequestMapping(value = "/accounts/{accountId}",
            method = RequestMethod.GET)
    public @ResponseBody GameResponse getAccount(@PathVariable Integer accountId) {
        return gameRunner.getAccount(accountId);
    }

    @RequestMapping(value = "{accountId}/deal/{bet}",
            method = RequestMethod.GET)
    public @ResponseBody GameResponse deal(@PathVariable Integer accountId, @PathVariable Integer bet, @RequestParam(value = "debugSplitNumber", required = false) Card.Number debugSplitNumber) {
        return gameRunner.deal(accountId, bet, debugSplitNumber);
    }

    @RequestMapping(value = "/{gameId}/hit",
            method = RequestMethod.GET)
    public @ResponseBody GameResponse hit(@PathVariable Integer gameId) {
        return gameRunner.hit(gameId);
    }

    @RequestMapping(value = "/{gameId}/stand",
            method = RequestMethod.GET)
    public @ResponseBody GameResponse stand(@PathVariable Integer gameId) {
        return gameRunner.stand(gameId);
    }

    @RequestMapping(value = "/{gameId}/double",
            method = RequestMethod.GET)
    public @ResponseBody GameResponse doubleDown(@PathVariable Integer gameId) {
        return gameRunner.doubleDown(gameId);
    }

    @RequestMapping(value = "/{gameId}/split",
            method = RequestMethod.GET)
    public @ResponseBody GameResponse split(@PathVariable Integer gameId) {
        return gameRunner.split(gameId);
    }
}