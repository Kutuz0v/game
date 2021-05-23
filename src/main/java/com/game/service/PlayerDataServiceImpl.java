package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.customExceptions.BadRequestException;
import com.game.customExceptions.NotValidExperienceException;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service("playerService")
@Transactional
public class PlayerDataServiceImpl implements PlayerDataService{
    @Autowired
    private PlayerCrudRepository repository;

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Player updatePlayer(Player player, Player receivedPlayer) throws BadRequestException {
        Validator validator = new Validator(receivedPlayer);
        if (validator.validName()) player.setName(receivedPlayer.getName());
        if (validator.validTitle()) player.setTitle(receivedPlayer.getTitle());
        if (validator.validRace()) player.setRace(receivedPlayer.getRace());
        if (validator.validProfession()) player.setProfession(receivedPlayer.getProfession());
        if (validator.validBirthday()) player.setBirthday(receivedPlayer.getBirthday());
        if (validator.validBanned())
            player.setBanned(receivedPlayer.getBanned());
        boolean valExp = validator.validExperience();
        if (valExp)
            player.setExperience(receivedPlayer.getExperience());
        updatePlayerData(player);
        return player;
    }

    @Override
    public Optional<Player> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Player save(Player player){
        updatePlayerData(player);
        return repository.save(player);
    }

    @Override
    public Boolean validatePlayerToCreate(Player player) throws BadRequestException {
        Validator validator = new Validator(player);
        return validator.validatePlayerToCreate();

    }

    @Override
    public List<Player> getAll() {
        return ((List<Player>) repository.findAll());
    }

    @Override
    public List<Player> findAllByIdIn(List<Long> ids, Pageable pageable) {
        return repository.findAllByIdIn(ids, pageable);
    }

    @Override
    public List<Player> selectPlayers(
            String name, String title, Race race, Profession profession, Long after, Long before, Boolean banned,
            Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel){
        List<Player> result = new ArrayList<>();
        List<Player> players = getAll();
        result = players.stream().filter(player ->
                (name == null || player.getName().contains(name))                 &&
                        (title == null || player.getTitle().contains(title))             &&
                        (race == null || player.getRace().equals(race))                   &&
                        (profession == null || player.getProfession().equals(profession)) &&
                        (after == null || player.getBirthday().after(new Date(after)))    &&
                        (before == null || player.getBirthday().before(new Date(before))) &&
                        (banned == null || player.getBanned().equals(banned))             &&
                        (minExperience == null || player.getExperience() >= minExperience) &&
                        (maxExperience == null || player.getExperience() <= maxExperience) &&
                        (minLevel == null || player.getLevel() >= minLevel)                &&
                        (maxLevel == null || player.getLevel() <= maxLevel)           ).collect(Collectors.toList());


        return result;
    }

    @Override
    public List<Player> sortByOrder(List<Player> players, PlayerOrder order){
        if (order == null) order = PlayerOrder.ID;
        PlayerOrder finalOrder = order;
        Comparator<Player> comparator = new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                switch (finalOrder){
                    case NAME: return o1.getName().compareTo(o2.getName());
                    case LEVEL: return o1.getLevel().compareTo(o2.getLevel());
                    case BIRTHDAY: return o1.getBirthday().compareTo(o2.getBirthday());
                    case EXPERIENCE: return o1.getExperience().compareTo(o2.getExperience());
                    default: return (int) (o1.getId() - o2.getId());
                }
            }
        };

        return players.stream().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public void calculateLevel(Player player) {
        Integer experience = player.getExperience();
        int level;
        level = (int) (Math.sqrt(2500d + 200*experience) - 50) / 100;
        player.setLevel(level);
    }

    @Override
    public void calculateUnitNextLevel(Player player) {
        int level = player.getLevel();
        int experience = player.getExperience();
        int unitNextLevel = 50 * (level + 1) * (level + 2) - experience;
        player.setUntilNextLevel(unitNextLevel);
    }

    private void updatePlayerData(Player player){
        calculateLevel(player);
        calculateUnitNextLevel(player);
    }
}
