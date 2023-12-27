package com.cadastro03.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadastro03.exception.CriptoExistsExc;
import com.cadastro03.exception.EmailExistsExc;
import com.cadastro03.model.Usuario;
import com.cadastro03.repository.UsuarioRepository;
import com.cadastro03.util.Util;


@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
		public void salvarUsuario(Usuario user) throws Exception {
			try {
				if (repository.findByUser(user.getUser()) != null) {
					throw new EmailExistsExc("Já existe um usuario cadastrado para: " + user.getUser());
				}
				user.setSenha(Util.md5(user.getSenha()));
			} catch (NoSuchAlgorithmException e) {
				throw new CriptoExistsExc("Erro na criptografia da senha");
			}
			repository.save(user);
		}
		
		public Usuario loginUsuario(String usuario, String senha) throws ExcService{
			
			Usuario userLogin = repository.buscarLogin(usuario, senha);
			return userLogin;
		}
	}

