package com.game.repository;

import com.game.entity.Player;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PlayerCrudRepository extends CrudRepository<Player, Long> {

    List<Player> findAllByIdIn(List<Long> ids, Pageable pageable);
    Player save(Player player);
    Optional<Player> findById(Long id);
    void deleteById(Long id);
}
