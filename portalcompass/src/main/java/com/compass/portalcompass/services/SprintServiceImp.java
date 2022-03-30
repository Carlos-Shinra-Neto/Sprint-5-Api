package com.compass.portalcompass.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.compass.portalcompass.dto.SprintDTO;
import com.compass.portalcompass.dto.SprintFormDTO;
import com.compass.portalcompass.entities.Sprint;
import com.compass.portalcompass.exception.BancoDeDadosExcecao;
import com.compass.portalcompass.exception.NaoEncontradoExcecao;
import com.compass.portalcompass.repositories.SprintRepositorio;



@Service
public  class SprintServiceImp implements SprintService {

	
	@Autowired
	private SprintRepositorio repositorio;
	
	@Autowired 
	private ModelMapper mapper;
	
	
	public List<Sprint> findAll() {
		return repositorio.findAll();
	}
	
	@Override
	public SprintDTO save(SprintFormDTO SprintBody) {
		Optional<Sprint> sprintID = repositorio.findById(SprintBody.getId());
		if (!sprintID.isEmpty()) {
			throw new BancoDeDadosExcecao("ID Já existe = " + SprintBody.getId());
		}
		Sprint sprint = repositorio.save(mapper.map(SprintBody, Sprint.class));
		return mapper.map(sprint, SprintDTO.class);
	}
	
	@Override
	public Page<SprintDTO> findAll(int size, int page, String sort) {
		Pageable pageable;
		if(sort == null) pageable = PageRequest.of(page, size);
		else pageable = PageRequest.of(page, size, Sort.by(sort));
		
		Page<Sprint> sprintPaginacao = repositorio.findAll(pageable);
		return sprintPaginacao.map(e -> mapper.map(e, SprintDTO.class));
	}

	@Override
	public SprintDTO findById(Long id) {
		Sprint sprint = repositorio.findById(id)
				.orElseThrow(() -> new NaoEncontradoExcecao(id));
		return mapper.map(sprint, SprintDTO.class);
	}

	@Override
	public SprintDTO update(Long id, SprintFormDTO sprintBody) {
		Sprint sprint = repositorio.findById(id)
				.orElseThrow(() -> new NaoEncontradoExcecao(id));
		sprint.setNome(sprintBody.getNome());
		sprint.setDataDeInicio(sprintBody.getDataDeInicio());
		sprint.setDataDeTermino(sprintBody.getDataDeTermino());
		sprint.setTemas(sprintBody.getTemas());
		
		Sprint update = repositorio.save(sprint);
		return mapper.map(update, SprintDTO.class);
	}


	@Override
	public void delete(Long id) {
		try {
			Sprint sprint = repositorio.findById(id)
					.orElseThrow(() -> new NaoEncontradoExcecao(id));
			repositorio.delete(sprint);
		} catch (EmptyResultDataAccessException e) {
			throw new NaoEncontradoExcecao(id);
		} catch (BancoDeDadosExcecao e) {
			throw new BancoDeDadosExcecao(e.getMessage());
		}
	}

	@Override
	public Page<SprintDTO> findAll(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
