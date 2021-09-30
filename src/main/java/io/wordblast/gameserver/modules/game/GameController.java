package io.wordblast.gameserver.modules.game;

import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The game REST endpoints.
 */
@RestController
public class GameController {
    @GetMapping("/game")
    public Game getGame(@RequestParam UUID uuid) {
        return new Game(uuid);
    }
}
