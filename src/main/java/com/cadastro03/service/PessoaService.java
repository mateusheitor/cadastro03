package com.cadastro03.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.cadastro03.model.Pessoa;
import com.cadastro03.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repository;
	
	public Page<Pessoa> List(Pageable pagReq){
		return repository.list(pagReq);
	}
	public Pessoa create(@RequestBody Pessoa pessoa) {
		return repository.save(pessoa);
	}
	
	 public List<Pessoa> findByNomeContaining(String nome) {
		 return repository.findByNomeContaining(nome);
	 }
}
