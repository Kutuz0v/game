package com.game.customExceptions;

public class NotValidExperienceException extends BadRequestException{
    public NotValidExperienceException(Integer experience) {
        super("Not valid experience: " + experience);
    }
}
