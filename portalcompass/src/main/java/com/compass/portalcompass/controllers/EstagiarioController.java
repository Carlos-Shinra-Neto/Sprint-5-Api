package com.compass.portalcompass.controllers;

import java.security.Principal;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
import com.compass.portalcompass.dto.EstagiarioSprintDTO;
import com.compass.portalcompass.dto.VinculoEstagiarioSprintForm;
import com.compass.portalcompass.dto.VinculoInfosForm;
import com.compass.portalcompass.enums.TipoBolsa;
import com.compass.portalcompass.feignclients.request.EmailDTO;
import com.compass.portalcompass.feignclients.response.Email;
import com.compass.portalcompass.services.EstagiarioService;

@RestController
@RequestMapping(value = "/estagiarios")
public class EstagiarioController {

	@Autowired
	private EstagiarioService service;

	// Retorna todos os estagiários. Obs.: tem parâmetro opcional para buscar pelo
	// tipo da bolsa

	@GetMapping
	@Cacheable(value = "listaTodosOsEstagiarios")
	public Page<EstagiarioDTO> findAll(@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "0") int page, @RequestParam(required = false) String sort,
			@RequestParam(required = false) TipoBolsa tipoBolsa) {
		if (tipoBolsa != null)
			return service.findByTipoBolsa(tipoBolsa, size, page);
		return service.findAll(size, page, sort);
	}

	@GetMapping(value = "/{id}")
	@Cacheable(value = "listaOsEstagiariosPorId")
	public ResponseEntity<EstagiarioDTO> findById(@PathVariable Long id, Principal principal) {
		EstagiarioDTO estagiario = service.findById(id);

		// Valida usuário. Obs.: só permite se o usuário (bolsita) estiver consultando informações suas. Ou se for admin.
		UsernamePasswordAuthenticationToken upat = (UsernamePasswordAuthenticationToken) principal;
		if (estagiario.getEmail().equals(principal.getName()) == false
				&& (upat.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) == false))
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);

		return ResponseEntity.ok(estagiario);
	}

	@GetMapping(value = "/{idEstagiario}/sprint/{idSprint}")
	public ResponseEntity<EstagiarioSprintDTO> getEstagiarioSprint(@PathVariable Long idEstagiario,
			@PathVariable Long idSprint, Principal principal) {
		// Valida usuário. Obs.: só permite se o usuário (bolsita) estiver consultando informações suas. Ou se for admin.
		EstagiarioDTO estagiario = service.findById(idEstagiario);
		UsernamePasswordAuthenticationToken upat = (UsernamePasswordAuthenticationToken) principal;
		if (estagiario.getEmail().equals(principal.getName()) == false
				&& (upat.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")) == false))
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);

		EstagiarioSprintDTO infos = service.getEstagiarioSprint(idEstagiario, idSprint);

		return ResponseEntity.ok(infos);
	}

	@PostMapping
	@Transactional
	@CacheEvict(value = "listaTodosOsEstagiarios", allEntries = true)
	public ResponseEntity<EstagiarioDTO> insert(@RequestBody @Valid EstagiarioFormDTO estagiarioBody) {
		EstagiarioDTO estagiario = service.insert(estagiarioBody);
		return ResponseEntity.status(HttpStatus.CREATED).body(estagiario);
	}

	@PostMapping(value = "/emails")
	@Transactional
	public ResponseEntity<Email> sendEmail(@RequestBody EmailDTO emailBody) {
		Email email = service.sendEmail(emailBody);
		return ResponseEntity.status(HttpStatus.CREATED).body(email);
	}

	// registra as informações da relação estagiário-sprint
	@PutMapping(value = "/{idEstagiario}/sprint/{idSprint}")
	@Transactional
	public ResponseEntity<Void> cadastraInfos(@PathVariable Long idEstagiario, @PathVariable Long idSprint,
			@RequestBody @Valid VinculoInfosForm form) {
		service.cadastrarInfos(idEstagiario, idSprint, form);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping(value = "/{id}")
	@Transactional
	@CacheEvict(value = "listaTodosOsEstagiarios", allEntries = true)
	public ResponseEntity<EstagiarioDTO> update(@PathVariable Long id,
			@RequestBody @Valid EstagiarioFormDTO estagiarioBody) {
		EstagiarioDTO estagiario = service.update(id, estagiarioBody);
		return ResponseEntity.ok(estagiario);
	}

	@PutMapping(value = "/sprint")
	public ResponseEntity<?> vincularASprint(@RequestBody @Valid VinculoEstagiarioSprintForm form) {
		service.vincularASprint(form);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	@Transactional
	@CacheEvict(value = "listaTodosOsEstagiarios", allEntries = true)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
