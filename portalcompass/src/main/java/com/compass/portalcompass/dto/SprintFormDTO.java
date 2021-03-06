package com.compass.portalcompass.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.compass.portalcompass.entities.Sprint;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class SprintFormDTO {
	@NotBlank (message = "O campo precisa ser preenchido")
	private String nome;
	
	@NotNull (message = "O campo não pode ser nulo") 
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate dataDeInicio;
	
	@NotNull (message = "O campo não pode ser nulo")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate dataDeTermino;
	
	public SprintFormDTO(Sprint sprint) {
		this.nome = sprint.getNome();
		this.dataDeInicio = sprint.getDataDeInicio();
		this.dataDeTermino = sprint.getDataDeTermino();
	}
}
