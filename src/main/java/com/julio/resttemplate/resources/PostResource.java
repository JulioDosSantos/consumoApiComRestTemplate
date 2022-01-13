package com.julio.resttemplate.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.julio.resttemplate.model.Post;
import com.julio.resttemplate.services.PostService;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {

	@Autowired
	private PostService service;
	
	@GetMapping
	public ResponseEntity<List<Post>> findAll(){
		List<Post> post = service.findAll();
		return post != null && !post.isEmpty() ? ResponseEntity.ok().body(post) : ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Post> findById(@PathVariable String id) {
		Post post = service.findById(id);
		return post != null ? ResponseEntity.ok().body(post) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Post> save(@RequestBody Post post) {
		post = service.save(post);
		return ResponseEntity.status(HttpStatus.CREATED).body(post);
	}
	
	@PutMapping
	public ResponseEntity<Post> edit(@RequestBody Post post) {
		post = service.edit(post);
		return ResponseEntity.ok().body(post);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
