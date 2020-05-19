package com.codeoftheweb.salvo.src;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface HitsTakenRepository extends JpaRepository<HitsTaken, Long> {
}