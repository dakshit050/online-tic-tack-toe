package com.javaspringboot.tictacktoe.services;
import  com.javaspringboot.tictacktoe.execption.tictoeException;
import com.javaspringboot.tictacktoe.memorystorage.GameStrorage;
import com.javaspringboot.tictacktoe.model.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@AllArgsConstructor
@Service
public class GameService {
    public Game createNewGame(Player player1){
        Game game=new Game();
        game.setGame_board(new int[3][3]);
        game.setPlayer1(player1);
        game.setGameId(UUID.randomUUID().toString());
        game.setStatus(GameStatus.NEW_GAME);
        GameStrorage.getInstance().setGames(game);
        return game;
    }
    public Game connectToRendomGame(Player player2) throws tictoeException {
        Game game= GameStrorage.getInstance().getGames().values().stream().filter(game1 ->
            game1.getStatus().equals(GameStatus.NEW_GAME)
        ).findFirst().orElseThrow(()->new tictoeException("please try after some time ,currently No one is ready to play"));
        game.setPlayer2(player2);
        game.setStatus(GameStatus.IN_PROGRESS_GAME);
        GameStrorage.getInstance().setGames(game);
        return  game;
    }

    public Game ConnectToGame(String game_id,Player player2) throws tictoeException {
        if(!GameStrorage.getInstance().getGames().containsKey(game_id)){
            throw new tictoeException("game id :"+game_id+" is Invalid");
        }
        Game game = GameStrorage.getInstance().getGames().get(game_id);
        if(game.getPlayer2()!=null){
            throw new tictoeException("Some one already Joined this Game");
        }
        game.setPlayer2(player2);
        game.setStatus(GameStatus.IN_PROGRESS_GAME);
        GameStrorage.getInstance().setGames(game);
        return game;
    }

    public Game gamePlay(GamePlay gamePlay) throws tictoeException {
        if(!GameStrorage.getInstance().getGames().containsKey(gamePlay.getGameId())){
            throw new tictoeException("Game Not Found");
        }
        Game game= GameStrorage.getInstance().getGames().get(gamePlay.getGameId());
        if(game.getStatus().equals(GameStatus.FINISHED_GAME)){
            throw new tictoeException("Game is already finished");
        }
        int[][] board = game.getGame_board();
        board[gamePlay.getCor_x()][gamePlay.getCor_y()]=gamePlay.getType().getValue();
        Boolean is_X_isWinner=checkWinner(game.getGame_board(), TicTocToe.X);
        Boolean is_O_isWinner=checkWinner(game.getGame_board(), TicTocToe.O);
        if(is_X_isWinner){
            game.setWinner(game.getPlayer1().getPlayerName());
        }else if(is_O_isWinner){
            game.setWinner(game.getPlayer2().getPlayerName());
        }
        GameStrorage.getInstance().setGames(game);
        return game;
    }

    private Boolean checkWinner(int[][] game_board, TicTocToe x) {
            int[] boardArray = new int[9];
            int counterIndex = 0;
            for (int i = 0; i < game_board.length; i++) {
                for (int j = 0; j < game_board[i].length; j++) {
                    boardArray[counterIndex] = game_board[i][j];
                    counterIndex++;
                }
            }

            int[][] winCombinations = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
            for (int i = 0; i < winCombinations.length; i++) {
                int counter = 0;
                for (int j = 0; j < winCombinations[i].length; j++) {
                    if (boardArray[winCombinations[i][j]] == x.getValue()) {
                        counter++;
                        if (counter == 3) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }
