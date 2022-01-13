package com.julio.resttemplate.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.julio.resttemplate.model.Endereco;

@Service
public class CepService {

	@Autowired
	private RestTemplate restTemplate;

	public Endereco buscaEnderecoPorCep(String cep) {

		String uri = "http://viacep.com.br/ws/{cep}/json/";
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("cep", cep);
		
		Endereco endereco = restTemplate.getForObject(uri, Endereco.class, params);

		return endereco;
	}

}
