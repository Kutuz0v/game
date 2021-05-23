package com.game.customExceptions;

public class NotValidNameException extends BadRequestException{
    public NotValidNameException(String name) {
        super("Not valid name: " + name);
    }
}
