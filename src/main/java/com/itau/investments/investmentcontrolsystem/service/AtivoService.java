package com.itau.investments.investmentcontrolsystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itau.investments.investmentcontrolsystem.model.Ativo;
import com.itau.investments.investmentcontrolsystem.repository.AtivoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtivoService {

	private final AtivoRepository ativoRepository;
	
	public List<Ativo> listarTodos() {
		return ativoRepository.findAll();
	}
	
	public Ativo buscarPorId(Long id) {
		return ativoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Ativo não encontrado com o ID: " + id));
	}
	
	public Ativo buscarPorCodigo(String codigo) {
		return ativoRepository.findByCodigo(codigo)
				.orElseThrow(() -> new EntityNotFoundException("Ativo não encontrado com o código: " + codigo));
	}
	
	public Ativo salvar(Ativo ativo) {
		if (ativoRepository.existsByCodigo(ativo.getCodigo())) {
			throw new IllegalArgumentException("Já existe um ativo com este código.");
		}
		return ativoRepository.save(ativo);
	}
	
	public void deletar(Long id) {
		if (!ativoRepository.existsById(id)) {
			throw new EntityNotFoundException("Ativo não encontrado com o ID: " + id);
		}
		ativoRepository.deleteById(id);
	}
}
