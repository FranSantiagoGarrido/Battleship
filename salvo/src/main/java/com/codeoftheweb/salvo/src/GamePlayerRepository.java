package com.codeoftheweb.salvo.src;

import java.util.List;

import com.codeoftheweb.salvo.src.GamePlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface GamePlayerRepository extends JpaRepository<GamePlayer, Long> {

}
