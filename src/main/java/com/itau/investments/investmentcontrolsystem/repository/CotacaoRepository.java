package com.itau.investments.investmentcontrolsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itau.investments.investmentcontrolsystem.model.*;

@Repository
public interface CotacaoRepository extends JpaRepository<Cotacao, Long> {
	
	Cotacao findTop1ByAtivoOrderByDataHoraDesc(Ativo ativo);
	
	List<Cotacao> findByAtivoOrderByDataHoraDesc(Ativo ativo);
}
