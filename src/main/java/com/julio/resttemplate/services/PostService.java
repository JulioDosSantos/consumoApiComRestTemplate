package com.julio.resttemplate.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.julio.resttemplate.model.Post;

@Service
public class PostService {
	
	private final String URI_BASE = "https://jsonplaceholder.typicode.com/posts";

	@Autowired
	private RestTemplate restTemplate;

	public List<Post> findAll() {
		
		Post[] posts = restTemplate.getForObject(URI_BASE, Post[].class);
		
		return Arrays.asList(posts);
	}

	public Post findById(String id) {

		String uri = URI_BASE + "/{id}";

		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);

		// Forma 1 getForObject
		Post post = restTemplate.getForObject(uri, Post.class, params);

		// Forma 2 getForEntity passando params
		ResponseEntity<Post> responseEntityPost = restTemplate.getForEntity(uri, Post.class, params);
		post = responseEntityPost.getBody();

		// Forma 3 getForEntity utilizando o varargs
		ResponseEntity<Post> responseEntityPost2 = restTemplate.getForEntity(uri, Post.class, id);
		post = responseEntityPost2.getBody();

		// Forma 4 exchange utilizando o varargs
		ResponseEntity<Post> responseEntityPost3 = restTemplate.exchange(uri, HttpMethod.GET, null, Post.class, params);
		post = responseEntityPost3.getBody();
		
		return post;
	}

	public Post save(Post post) {
		
		// Forma 1 getForObject
		post = restTemplate.postForObject(URI_BASE, post, Post.class);
		
		// Forma 2 exchange
		ResponseEntity<Post> responseEntityPost = restTemplate.exchange(
				URI_BASE, 
				HttpMethod.POST,
				new HttpEntity<>(post, createJsonHeader()), 
				Post.class);
		post = responseEntityPost.getBody();
		
		return post;
	}

	public Post edit(Post post) {
		
		String uri = URI_BASE + "/{id}";
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", String.valueOf(post.getId()));

		// Forma 1 exchange
		ResponseEntity<Post> responseEntityPost = restTemplate.exchange(
				uri, 
				HttpMethod.PUT,
				new HttpEntity<>(post, createJsonHeader()), 
				Post.class,
				params);
		post = responseEntityPost.getBody();
		
		// Forma 2 put
		restTemplate.put(uri, responseEntityPost, params);
		
		return post;
	}

	public void delete(String id) {

		String uri = URI_BASE + "/{id}";
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		
		// Forma 1 exchange
		ResponseEntity<Void> responseEntityPost = restTemplate.exchange(
			uri, 
			HttpMethod.DELETE,
			null, 
			Void.class,
			params);
		
		
		// Forma 2 delete
		restTemplate.delete(uri, params);
		
	}
	
	private HttpHeaders createJsonHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

}
