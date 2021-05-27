package com.javaspringboot.tictacktoe.memorystorage;

import com.javaspringboot.tictacktoe.model.Game;

import java.util.HashMap;
import java.util.Map;

public class GameStrorage {
    private static Map<String, Game> games;
    private static GameStrorage instance;

    private GameStrorage(){
        games=new HashMap<>();
    }
    public static synchronized GameStrorage getInstance(){
        if(instance==null){
            return instance=new GameStrorage();
        }
        return instance;
    }
    public Map<String,Game> getGames(){
        return games;
    }
    public void setGames(Game game){
        games.put(game.getGameId(),game);
    }
}
