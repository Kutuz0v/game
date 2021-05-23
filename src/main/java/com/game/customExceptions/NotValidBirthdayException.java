package com.game.customExceptions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotValidBirthdayException extends BadRequestException{
    public NotValidBirthdayException(Date birthday) {
        super("Not valid birthday: " + new SimpleDateFormat("yyyy.MM.dd").format(birthday));
    }
}
