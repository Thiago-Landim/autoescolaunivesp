package com.sci.com.repositories;

import com.sci.com.entities.InstrutoresEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;


@Repository
@EnableJpaRepositories
public interface InstrutorRepository extends JpaRepository<InstrutoresEntity, Long > {
    InstrutoresEntity findByCpf(String cpf);
}
