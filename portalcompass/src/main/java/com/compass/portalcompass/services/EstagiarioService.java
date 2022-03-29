package com.compass.portalcompass.services;

import org.springframework.data.domain.Page;


import com.compass.portalcompass.dto.EstagiarioDTO;
import com.compass.portalcompass.dto.EstagiarioFormDTO;

public interface EstagiarioService {

	Page<EstagiarioDTO> findAll(int size, int page, String sort);
	
	EstagiarioDTO insert(EstagiarioFormDTO estagiarioBody);
	
	EstagiarioDTO findById(Long id);
	
	EstagiarioDTO update(Long id, EstagiarioFormDTO estagiarioBody);
	
	void delete(Long id);
}