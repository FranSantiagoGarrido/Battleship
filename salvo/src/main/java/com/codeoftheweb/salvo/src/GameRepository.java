package com.codeoftheweb.salvo.src;

import java.util.Date;
import java.util.List;

import com.codeoftheweb.salvo.src.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
   }
