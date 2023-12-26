package com.cadastro03.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cadastro03.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{

	@Query("SELECT p FROM Pessoa p")
	public Page<Pessoa> list(Pageable pagReq);
	public List<Pessoa> findByNomeContaining(String nome);
	
}
