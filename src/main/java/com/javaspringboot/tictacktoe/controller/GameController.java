package com.javaspringboot.tictacktoe.controller;

import com.javaspringboot.tictacktoe.dto.connectRequest;
import com.javaspringboot.tictacktoe.execption.tictoeException;
import com.javaspringboot.tictacktoe.model.Game;
import com.javaspringboot.tictacktoe.model.GamePlay;
import com.javaspringboot.tictacktoe.model.Player;
import com.javaspringboot.tictacktoe.services.GameService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/startgame")
    public ResponseEntity<Game> start_Game(@RequestBody Player player){
        log.info("Start game request by :",player);
        return ResponseEntity.ok(gameService.createNewGame(player));
    }
    @PostMapping("/connect")
    public ResponseEntity<Game> connect(@RequestBody connectRequest request) throws tictoeException {
        log.info("connect request by ",request.getPlayer());
        return ResponseEntity.ok(gameService.ConnectToGame(request.getGame_id(),request.getPlayer()));
    }
    @PostMapping("/connect/random")
    public ResponseEntity<Game> ConnectToRandom(@RequestBody Player player) throws tictoeException {
        log.info("connect request to random ", player);
        return ResponseEntity.ok(gameService.connectToRendomGame(player));
    }
    @PostMapping("/gameplay")
    public ResponseEntity<Game> gamePlay(@RequestBody GamePlay gamePlay) throws tictoeException {
        log.info("gameplay : ",gamePlay);
        Game game = gameService.gamePlay(gamePlay);
        simpMessagingTemplate.convertAndSend("/topic/game-progress/"+game.getGameId(),game);
        return ResponseEntity.ok(game);
    }

}
