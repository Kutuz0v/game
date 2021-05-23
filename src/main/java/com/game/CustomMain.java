package com.game;

import com.game.config.AppConfig;
import com.game.entity.Player;
import com.game.service.PlayerDataService;
import com.game.service.PlayerDataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class CustomMain {
    public final static String NORMAL_JSON =
            "{" +
                    "\"name\": \"Амарылис\"," +
                    "\"title\":\"Прозелит\"," +
                    "\"race\": \"HUMAN\"," +
                    "\"profession\": \"CLERIC\"," +
                    "\"birthday\" : 988059600000," +
                    "\"banned\":false," +
                    "\"experience\": 63986" +
                    "}";
    @Autowired
    static PlayerDataServiceImpl service;
    public static void main(String[] args) throws ParseException {
        //AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        //PlayerDataService playerService = context.getBean("playerService", PlayerDataService.class);
        Date date = new Date(2000, Calendar.JANUARY,0,0,0, 0);
        date.setTime(0);
        date.setYear(2000);
        System.out.println("2000 year: " + date.getTime());
        date.setYear(3000);
        System.out.println("3000 year: " + date.getTime());
        //SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        Date d = sf.parse("3000.01.01 00:00:00");
        System.out.println(d.getTime());
        System.out.println(sf.format(new Date(32503672800000L)));
    }
/*
2000 year: 946677600000
3000 year: 32503672800000
2000       946677600000
 */
    public static void printAll(List<Player> players){
        for (Player player :
                players) {
            System.out.println(player);
        }
    }
}
