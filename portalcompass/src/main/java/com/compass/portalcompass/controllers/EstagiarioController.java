package com.compass.portalcompass.controllers;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.compass.portalcompass.dto.EstagiarioDTO;
import com.compass.portalcompass.dto.EstagiarioFormDTO;
import com.compass.portalcompass.services.EstagiarioService;

@RestController
@RequestMapping(value = "/estagiarios")
public class EstagiarioController {

	@Autowired
	private EstagiarioService service;
	
	@GetMapping
	public Page<EstagiarioDTO> findAll(@RequestParam(defaultValue = "10") int size, 
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(required = false) String sort) {
		return service.findAll(size, page, sort);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<EstagiarioDTO> insert(@RequestBody EstagiarioFormDTO estagiarioBody) {
		EstagiarioDTO estagiario = service.insert(estagiarioBody);
		return ResponseEntity.status(HttpStatus.CREATED).body(estagiario);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<EstagiarioDTO> findById(@PathVariable Long id) {
		EstagiarioDTO estagiario = service.findById(id);
		return ResponseEntity.ok(estagiario);
	}
	
	@PutMapping(value = "/{id}")
	@Transactional
	public ResponseEntity<EstagiarioDTO> update(@PathVariable Long id, @RequestBody EstagiarioFormDTO estagiarioBody) {
		EstagiarioDTO estagiario = service.update(id, estagiarioBody);
		return ResponseEntity.ok(estagiario);
	}
	
	@DeleteMapping(value = "/{id}")
	@Transactional
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
