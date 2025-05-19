package com.vibeat.backend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vibeat.backend.model.TipoEntrada;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter
public class TipoEntradaConverter implements AttributeConverter<List<TipoEntrada>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<TipoEntrada> tiposEntrada) {
        try {
            return objectMapper.writeValueAsString(tiposEntrada);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir tiposEntrada a JSON", e);
        }
    }

    @Override
    public List<TipoEntrada> convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<TipoEntrada>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error al convertir JSON a tiposEntrada", e);
        }
    }
}
