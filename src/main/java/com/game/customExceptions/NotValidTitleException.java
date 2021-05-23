package com.game.customExceptions;

public class NotValidTitleException extends BadRequestException{
    public NotValidTitleException(String title) {
        super("Not valid title: " + title);
    }
}
