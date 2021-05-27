package com.javaspringboot.tictacktoe.dto;

import com.javaspringboot.tictacktoe.model.Player;
import lombok.Data;

@Data
public class connectRequest {
    private Player player;
    private String game_id;
}
