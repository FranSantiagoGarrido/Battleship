package com.codeoftheweb.salvo.src;

import com.codeoftheweb.salvo.src.Salvo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SalvoRepository extends JpaRepository<Salvo, Long> {

}
