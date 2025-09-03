package com.design_patterns.design_patterns.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//CrudRepository (é uma strategy) - interface - spring já identifica a intecao de usar repository (nem precisaria escrever
//@Repository

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long> {

}
