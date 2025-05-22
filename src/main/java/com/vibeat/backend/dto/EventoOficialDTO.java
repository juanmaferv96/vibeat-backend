package com.vibeat.backend.dto;

import com.vibeat.backend.model.Empresa;
import com.vibeat.backend.model.EventoOficial;

public class EventoOficialDTO {
	private EventoOficial evento;
	private Empresa empresa;

	public EventoOficialDTO(EventoOficial evento, Empresa empresa) {
		this.evento = evento;
		this.empresa = empresa;
	}

	public EventoOficial getEvento() {
		return evento;
	}

	public Empresa getEmpresa() {
		return empresa;
	}
}
