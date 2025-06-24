package com.itau.investments.investmentcontrolsystem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itau.investments.investmentcontrolsystem.model.Usuario;
import com.itau.investments.investmentcontrolsystem.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	
	public List<Usuario> listarTodos() {
		return usuarioRepository.findAll();
	}
	
	public Usuario buscarPorId(Long id) {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + id));
	}
	
	public Usuario salvar(Usuario usuario) {
		if (usuarioRepository.existsByEmail(usuario.getEmail())) {
			throw new IllegalArgumentException("Já existe um usuário com este e-mail.");
		}
		return usuarioRepository.save(usuario);
	}
	
	public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
		Usuario existente = buscarPorId(id);
		existente.setNome(usuarioAtualizado.getNome());
		existente.setEmail(usuarioAtualizado.getEmail());
		existente.setPercCorretagem(usuarioAtualizado.getPercCorretagem());
		return usuarioRepository.save(existente);
	}
	
	public void deletar(Long id) {
		if (!usuarioRepository.existsById(id)) {
			throw new EntityNotFoundException("Usuário não encontrado com o ID: " + id);
		}
		usuarioRepository.deleteById(id);
	}
}
