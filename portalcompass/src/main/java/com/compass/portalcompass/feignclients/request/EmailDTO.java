package com.compass.portalcompass.feignclients.request;

import com.compass.portalcompass.entities.Estagiario;
import com.compass.portalcompass.entities.EstagiarioSprint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {

	private String ownerRef;
	private String emailFrom;
	private String emailTo;
	private String subject;
	private String text;
	
	public EmailDTO(EstagiarioSprint vinculo) {
		Estagiario estagiario = vinculo.getEstagiario();
		
		this.ownerRef = estagiario.getNome();
		this.emailFrom = "compasstestemailapi@gmail.com";
		this.emailTo = estagiario.getEmail();
		this.subject = "teste";
		this.text = 
				String.format("Bom dia, %s! Segue os resultados da avaliação. Nota comportamental: %s. Nota técnica: %s. Temas de reforço: %s", 
						estagiario.getNome(), 
						vinculo.getNotaComportamental().toString(), 
						vinculo.getNotaTecnica().toString(),
						vinculo.getTemasReforco().toString());
	}
}
