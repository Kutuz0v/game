package com.game.customExceptions;

public class NotValidIdException extends BadRequestException{
    public NotValidIdException(Long id) {
        super("Not valid id: " + id);
    }
}
