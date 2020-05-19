package com.codeoftheweb.salvo.src;

import com.codeoftheweb.salvo.src.ScoreRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ScoreRepository extends JpaRepository<Score, Long> {

}
