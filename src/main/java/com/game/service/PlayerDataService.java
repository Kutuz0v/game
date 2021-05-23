package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.customExceptions.BadRequestException;
import com.game.customExceptions.NotValidExperienceException;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PlayerDataService {
    List<Player> getAll();

    Optional<Player> getById(Long id);

    List<Player> selectPlayers(
            String name, String title, Race race, Profession profession, Long after, Long before, Boolean banned,
            Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel);

    List<Player> sortByOrder(List<Player> players, PlayerOrder order);

    List<Player> findAllByIdIn(List<Long> ids, Pageable pageable);

    Boolean validatePlayerToCreate(Player player) throws BadRequestException;

    Player save(Player player);

    void calculateLevel(Player player);

    void calculateUnitNextLevel(Player player);

    Player updatePlayer(Player playerToUpdate, Player receivedPlayer) throws BadRequestException;

    void delete(Long id);
}


/*
    List<Player> selectPlayers(List<Player> players,
                                       String name, String title, Race race, Profession profession, Long after, Long before, Boolean banned,
                                       Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel);
 */