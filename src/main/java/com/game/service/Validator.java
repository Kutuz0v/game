package com.game.service;

import com.game.customExceptions.*;
import com.game.entity.Player;

public class Validator {
    private Player player;

    public boolean validatePlayerToCreate() throws BadRequestException {
        return  validName()       &&
                validTitle()      &&
                validRace()       &&
                validProfession() &&
                validBirthday()   &&
                validExperience();
    }

    public Validator(Player player) {
        this.player = player;
    }

    public boolean validName() throws BadRequestException{
        String name = player.getName();
        if (name == null) return false;
        if (name.length() <= 12 && name.length() != 0) return true;
        else throw new NotValidNameException(name);
    }

    public boolean validTitle() throws BadRequestException{
        String title = player.getTitle();
        if (title == null) return false;
        if (title.length() <= 30) return true;
        else throw new NotValidTitleException(title);
    }

    public boolean validRace(){
        return player.getRace() != null;
    }

    public boolean validProfession(){
        return player.getProfession() != null;
    }

    public boolean validBirthday() throws BadRequestException{
        long birthday = player.getBirthday() == null ? -1 : player.getBirthday().getTime();
        long minDate = 946677600000L;   //  2000 year
        long maxDate = 32503672800000L; //  3000 year
        if (player.getBirthday() == null) return false;
        if (birthday >= minDate && birthday <= maxDate) return true;
        else throw new NotValidBirthdayException(player.getBirthday());
    }

    public boolean validBanned(){
        return player.getBanned() != null;
    }

    public boolean validExperience() throws NotValidExperienceException {
        Integer experience = player.getExperience();
        if (experience == null) return false;
        if (experience >=0 && experience <= 10000000) return true;
        else throw new NotValidExperienceException(experience);
    }
}
