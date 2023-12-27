package com.cadastro03.controller;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cadastro03.model.Pessoa;
import com.cadastro03.model.Usuario;
import com.cadastro03.service.ExcService;
import com.cadastro03.service.UsuarioService;
import com.cadastro03.util.Util;


@Controller
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@GetMapping("/")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login/login");
		return mv;
	}

	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/pessoa/list/1");
		mv.addObject("pessoa", new Pessoa());
		return mv;
	}

	@GetMapping("/cadastro")
	public ModelAndView cadastrar() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("usuario", new Usuario());
		mv.setViewName("login/cadastro");
		return mv;
	}

	@PostMapping("salvarUsuario")
	public ModelAndView cadastrar(@Validated Usuario usuario) throws Exception {
		ModelAndView mv = new ModelAndView();
		service.salvarUsuario(usuario);
		mv.setViewName("redirect:/");
		return mv;
	}

	@PostMapping("/login")
	public ModelAndView login(Usuario usuario, BindingResult br, HttpSession session) throws NoSuchAlgorithmException, ExcService {

		ModelAndView mv = new ModelAndView();
		mv.addObject("usuario", new Usuario());
		if (br.hasErrors()) {
			mv.setViewName("login/login");
		}
		Usuario userLogin = service.loginUsuario(usuario.getUser(), Util.md5(usuario.getSenha()));
		if (userLogin == null) {
			mv.addObject("msg", "Usuario não encontrado. Tente novamente");
		} else {
			session.setAttribute("usuarioLogado", userLogin);
			return index();
		}
		return mv;
	}
	
	@PostMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		session.invalidate();
		return login();
	}
}
