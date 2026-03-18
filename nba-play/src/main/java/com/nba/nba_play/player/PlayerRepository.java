package com.nba.nba_play.player;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// extends the JpaRepository which provides CRUD operations for player entity
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    void deleteByPlayer(String playerName);

    Optional<Player> findByPlayer(String player);
}
