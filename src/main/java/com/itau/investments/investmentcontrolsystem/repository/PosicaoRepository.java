package com.itau.investments.investmentcontrolsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itau.investments.investmentcontrolsystem.model.*;

@Repository
public interface PosicaoRepository extends JpaRepository<Posicao, PosicaoId> {

	List<Posicao> findByUsuario(Usuario usuario);
	
	Optional<Posicao> findByUsuarioAndAtivo(Usuario usuario, Ativo ativo);
	
	boolean existsByUsuarioAndAtivo(Usuario usuario, Ativo ativo);
}
