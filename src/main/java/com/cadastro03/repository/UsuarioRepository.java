package com.cadastro03.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cadastro03.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

	@Query("select i from Usuario i where i.user = :user")
	public Usuario findByUser(String user);
	
	@Query("select j from Usuario j where j.user = :user and j.senha = :senha")
	public Usuario buscarLogin(String user, String senha);
}
