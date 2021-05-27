package com.javaspringboot.tictacktoe.execption;

public class tictoeException extends Exception {
    private String message;
    public tictoeException(String message){
        this.message=message;
    }
    public String getMessage(){
        return message;
    }
}
