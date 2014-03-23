package blackjack.controller;

import blackjack.GameRunner;
import blackjack.model.GameResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BlackjackController {

    private final GameRunner gameRunner = new GameRunner();


    @RequestMapping(value = "/deal",
            method = RequestMethod.GET)
    public @ResponseBody GameResponse deal() {
        return gameRunner.deal();
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
}