package com.codeoftheweb.salvo.src;

import java.util.List;

import com.codeoftheweb.salvo.src.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByUserName(String userName);
}

