package com.javaspringboot.tictacktoe.model;
import lombok.Data;
@Data
public class Game {
    private String gameId;
    private Player player1;
    private Player player2;
    private GameStatus status;
    private int[][] game_board;
    private String winner;

}
