package com.br.ages.calculadoraback.repository;

import com.br.ages.calculadoraback.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
	Optional<UserEntity> findByDocument(String document);

	Optional<UserEntity> findByDocumentAndCodCoop_codCoop(String document, String codCoop);
}
