package com.game.controller;

import com.game.customExceptions.BadRequestException;
import com.game.customExceptions.NotFoundException;
import com.game.customExceptions.NotValidIdException;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/rest")
public class PlayersController {
    @Autowired
    private PlayerDataService playerService;

    @DeleteMapping("/players/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable("id") Long id) throws Exception{
        if (id < 1) throw new NotValidIdException(id);
        Optional<Player> player = playerService.getById(id);
        if (!player.isPresent()) throw new NotFoundException(id);

        playerService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("players/{id}")
    public ResponseEntity<?> updatePlayer(
            @PathVariable("id") long id,
            @RequestBody Player receivedPlayer) throws Exception {
        if (id < 1) throw new NotValidIdException(id);
        Optional<Player> playerById = playerService.getById(id);
        if (!playerById.isPresent()) throw new NotFoundException(id);

        Player updatedPlayer = playerService.updatePlayer(playerById.get(), receivedPlayer);

        return ResponseEntity.ok(updatedPlayer);
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<?> getPlayer(@PathVariable("id") long id) throws Exception {
        if (id < 1) throw new NotValidIdException(id);
        Optional<Player> player = playerService.getById(id);
        if (!player.isPresent()) throw new NotFoundException(id);

        return ResponseEntity.ok(player.get());
    }

    @PostMapping("/players")
    public ResponseEntity<?> createPlayer(
            @RequestBody Player player
            ) throws BadRequestException {
        if (player.getBanned() == null) player.setBanned(false);
        if (!playerService.validatePlayerToCreate(player)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        playerService.save(player);
        return ResponseEntity.ok(player);
    }

    @GetMapping("/players/count")
    @ResponseBody
    public ResponseEntity<?> countPlayers(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "race", required = false) Race race,
            @RequestParam(name = "profession", required = false) Profession profession,
            @RequestParam(name = "after", required = false) Long after,
            @RequestParam(name = "before", required = false) Long before,
            @RequestParam(name = "banned", required = false) Boolean banned,
            @RequestParam(name = "minExperience", required = false) Integer minExperience,
            @RequestParam(name = "maxExperience", required = false) Integer maxExperience,
            @RequestParam(name = "minLevel", required = false) Integer minLevel,
            @RequestParam(name = "maxLevel", required = false) Integer maxLevel){

        return ResponseEntity.ok(playerService.selectPlayers(
                name,
                title,
                race,
                profession,
                after,
                before,
                banned,
                minExperience,
                maxExperience,
                minLevel,
                maxLevel).size());
    }

    @GetMapping("/players")
    @ResponseBody
    public ResponseEntity<List<Player>> getAllPlayers(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "race", required = false) Race race,
            @RequestParam(name = "profession", required = false) Profession profession,
            @RequestParam(name = "after", required = false) Long after,
            @RequestParam(name = "before", required = false) Long before,
            @RequestParam(name = "banned", required = false) Boolean banned,
            @RequestParam(name = "minExperience", required = false) Integer minExperience,
            @RequestParam(name = "maxExperience", required = false) Integer maxExperience,
            @RequestParam(name = "minLevel", required = false) Integer minLevel,
            @RequestParam(name = "maxLevel", required = false) Integer maxLevel,
            @RequestParam(name = "order", required = false, defaultValue = "ID") PlayerOrder order,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "3") Integer pageSize
    ) {
        Pageable of = PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, order.getFieldName());

        List<Player> players = playerService.selectPlayers(
                name,
                title,
                race,
                profession,
                after,
                before,
                banned,
                minExperience,
                maxExperience,
                minLevel,
                maxLevel
        );
        List<Long> collect = players.stream().map(Player::getId).collect(Collectors.toList());
        List<Player> playerList = playerService.findAllByIdIn(collect, of);

        return ResponseEntity.ok(playerList);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleConstraintViolationException(BadRequestException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleConstraintViolationException(NotFoundException e) {
        return ResponseEntity.notFound().build();
    }

}
