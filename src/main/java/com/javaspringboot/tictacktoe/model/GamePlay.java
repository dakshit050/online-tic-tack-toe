package com.javaspringboot.tictacktoe.model;

import lombok.Data;

@Data
public class GamePlay {
    private TicTocToe type;
    private Integer cor_x;
    private Integer cor_y;
    private String gameId;
}
