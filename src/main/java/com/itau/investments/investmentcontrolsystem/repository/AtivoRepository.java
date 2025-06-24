package com.itau.investments.investmentcontrolsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itau.investments.investmentcontrolsystem.model.Ativo;

@Repository
public interface AtivoRepository extends JpaRepository<Ativo, Long> {
	
	Optional<Ativo> findByCodigo(String codigo);
	
	boolean existsByCodigo(String codigo);
}
