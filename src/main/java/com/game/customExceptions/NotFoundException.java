package com.game.customExceptions;

public class NotFoundException extends Exception{
    public NotFoundException(Long id) {
        super("Not found player with id: " + id);
    }
}
