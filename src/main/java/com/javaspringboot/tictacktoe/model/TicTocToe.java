package com.javaspringboot.tictacktoe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TicTocToe {
    X(1), O(2);
    private Integer value;
}
