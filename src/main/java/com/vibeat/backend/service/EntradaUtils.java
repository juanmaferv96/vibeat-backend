package com.vibeat.backend.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

final class EntradaUtils {
    static final ZoneId ZONE = ZoneId.of("Europe/Madrid");
    static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private EntradaUtils() {}
    static LocalDateTime now() { return LocalDateTime.now(ZONE); }
}
