package com.cadastro03.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cadastro03.model.Pessoa;
import com.cadastro03.repository.PessoaRepository;
import com.cadastro03.service.PessoaService;

@Controller
public class PessoaController {

	@Autowired
	private PessoaService service;
	private PessoaRepository repository;
	
	@GetMapping("/inserir")
	public ModelAndView InsertPessoa(Pessoa pessoa) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("pessoa/add");
		mv.addObject("pessoa", new Pessoa());
		return mv;
	}
	
	@PostMapping("add")
	public ModelAndView inserirPessoa(Pessoa pessoa) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/pessoa/list/1");
		repository.save(pessoa);
		return mv;
	}
	
	@GetMapping("/pessoa/list/{pageNo}")
	public ModelAndView list(@PathVariable(value = "pageNo") int pageNo, @RequestParam(defaultValue = "1") int page) {
		int pageSize = 3;
		ModelAndView mv = new ModelAndView("/pessoa/list");
		mv.addObject("funcionario", new Pessoa());
		mv.addObject("action", "/pessoa/add");
		Pageable pagreq = PageRequest.of(pageNo - 1, pageSize, Sort.by("id"));
		Page<Pessoa> pessoas = service.List(pagreq);
		mv.addObject("pessoas", pessoas);
		mv.addObject("currentPage", pageNo);
		mv.addObject("totalPages", pessoas.getTotalPages());
		mv.addObject("totalItems", pessoas.getTotalElements());
		return mv;
	}
	@GetMapping("/pessoas/findAll")
	public ResponseEntity<List<Pessoa>> getAllTutorials(@RequestParam(required = true) String nome) {
	    try {
	      List<Pessoa> pessoas = new ArrayList<Pessoa>();
	      service.findByNomeContaining(nome).forEach(pessoas::add);
	      return new ResponseEntity<>(pessoas, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@GetMapping("/edit/{id}")
	public ModelAndView editar(@PathVariable("id") Integer Id) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("pessoa/edit");
		@SuppressWarnings("deprecation")
		Pessoa pessoa = repository.getOne(Id);
		mv.addObject("pessoa", pessoa);
		return mv;
	}
	
	@PostMapping("/edit")
	public ModelAndView edit(Pessoa pessoa) {
		ModelAndView mv = new ModelAndView();
		repository.save(pessoa);
		mv.setViewName("redirect:/pessoa/list/1");
		return mv;
	}
	
	@GetMapping("/del/{id}")
	public String excluir(@PathVariable("id")Integer id) {
		repository.deleteById(id);
		return "redirect:/pessoa/list/1";
	}
	
}
