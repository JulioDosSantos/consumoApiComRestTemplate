package com.julio.resttemplate.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.julio.resttemplate.model.Endereco;
import com.julio.resttemplate.services.CepService;

@RestController
@RequestMapping(value = "/cep")
public class CepResource {

	@Autowired
	private CepService service;

	@GetMapping("/{cep}")
	public ResponseEntity<Endereco> getCep(@PathVariable String cep) {
		
		Endereco endereco = service.buscaEnderecoPorCep(cep);
		return endereco != null ? ResponseEntity.ok().body(endereco) : ResponseEntity.notFound().build();
	}

}
