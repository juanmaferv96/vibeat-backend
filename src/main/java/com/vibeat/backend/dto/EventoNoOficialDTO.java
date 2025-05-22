package com.vibeat.backend.dto;

import com.vibeat.backend.model.EventoNoOficial;
import com.vibeat.backend.model.Usuario;

public class EventoNoOficialDTO {
	private EventoNoOficial evento;
	private Usuario usuario;

	public EventoNoOficialDTO(EventoNoOficial evento, Usuario usuario) {
		this.evento = evento;
		this.usuario = usuario;
	}

	public EventoNoOficial getEvento() {
		return evento;
	}

	public Usuario getUsuario() {
		return usuario;
	}
}
