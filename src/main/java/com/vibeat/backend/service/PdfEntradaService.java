package com.vibeat.backend.service;

import com.vibeat.backend.model.EntradaOficial;
import com.vibeat.backend.model.EntradaNoOficial;
import com.vibeat.backend.repository.EntradaOficialRepository;
import com.vibeat.backend.repository.EntradaNoOficialRepository;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class PdfEntradaService {

    private final EntradaOficialRepository entradaOficialRepository;
    private final EntradaNoOficialRepository entradaNoOficialRepository;
    private final QrCodeService qrCodeService;

    private static final DateTimeFormatter TS_HUMANO =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
    private static final DateTimeFormatter DOB_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ROOT);

    // Color Azul Corporativo (#2c5aa0)
    private static final Color COLOR_AZUL = new Color(44, 90, 160);
    private static final Color COLOR_NEGRO = Color.BLACK;

    public PdfEntradaService(EntradaOficialRepository entradaOficialRepository,
                             EntradaNoOficialRepository entradaNoOficialRepository,
                             QrCodeService qrCodeService) {
        this.entradaOficialRepository = entradaOficialRepository;
        this.entradaNoOficialRepository = entradaNoOficialRepository;
        this.qrCodeService = qrCodeService;
    }

    // ---------- OFICIAL ----------
    public ResponseEntity<byte[]> descargarPdfOficial(Long id, String eventoNombre, String eventoDesc, String usuarioLogin, String tipoDesc) {
        EntradaOficial e = entradaOficialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entrada oficial no encontrada: id=" + id));

        byte[] pdf = buildPdfParaEntrada(
                e.getCodigoQr(),
                e.getReferencia(),
                eventoNombre, eventoDesc, e.getEventoId(),
                usuarioLogin, e.getUsuarioId(),
                e.getTipoEntrada(), tipoDesc,
                e.getNombreComprador(), e.getApellidosComprador(),
                e.getDniComprador(), e.getEmailComprador(),
                e.getTelefonoComprador(),
                e.getFechaNacimientoComprador() != null ? e.getFechaNacimientoComprador().format(DOB_FMT) : "-",
                e.getFechaCompra() != null ? e.getFechaCompra().format(TS_HUMANO) : "-"
        );

        String filename = buildPdfFilename(e.getReferencia(), e.getNombreComprador(), e.getApellidosComprador());
        return responsePdf(filename, pdf);
    }

    // ---------- NO OFICIAL ----------
    public ResponseEntity<byte[]> descargarPdfNoOficial(Long id, String eventoNombre, String eventoDesc, String usuarioLogin, String tipoDesc) {
        EntradaNoOficial e = entradaNoOficialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entrada no oficial no encontrada: id=" + id));

        byte[] pdf = buildPdfParaEntrada(
                e.getCodigoQr(),
                e.getReferencia(),
                eventoNombre, eventoDesc, e.getEventoId(),
                usuarioLogin, e.getUsuarioId(),
                e.getTipoEntrada(), tipoDesc,
                e.getNombreComprador(), e.getApellidosComprador(),
                e.getDniComprador(), e.getEmailComprador(),
                e.getTelefonoComprador(),
                e.getFechaNacimientoComprador() != null ? e.getFechaNacimientoComprador().format(DOB_FMT) : "-",
                e.getFechaCompra() != null ? e.getFechaCompra().format(TS_HUMANO) : "-"
        );

        String filename = buildPdfFilename(e.getReferencia(), e.getNombreComprador(), e.getApellidosComprador());
        return responsePdf(filename, pdf);
    }

    // ---------- Construcción del PDF ----------
    private byte[] buildPdfParaEntrada(
            String codigoQr,
            String referencia,
            String eventoNombre, String eventoDesc, Long eventoId,
            String usuarioLogin, Long usuarioId,
            String tipoEntrada, String tipoDesc,
            String nombre, String apellidos,
            String dni, String email, String telefono,
            String dob, String fechaCompra
    ) {
        if (codigoQr == null || codigoQr.isBlank()) {
            throw new IllegalStateException("La entrada no tiene CODIGO_QR");
        }

        byte[] qrPng = qrCodeService.generatePng(codigoQr, 600, 2, ErrorCorrectionLevel.Q);
        BufferedImage qrImage;
        try {
            qrImage = ImageIO.read(new ByteArrayInputStream(qrPng));
        } catch (Exception ex) {
            throw new IllegalStateException("No se pudo leer la imagen PNG del QR", ex);
        }

        try (PDDocument doc = new PDDocument(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            float pageWidth = page.getMediaBox().getWidth();
            float pageHeight = page.getMediaBox().getHeight();
            float margin = 50f;
            float width = pageWidth - 2 * margin;
            float y = pageHeight - margin;

            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                
                // 1. TÍTULO "Entrada:"
                cs.setNonStrokingColor(COLOR_NEGRO);
                y = writeMultiLineText(cs, "Entrada:", margin, y, width, 24, true, COLOR_NEGRO);
                y -= 5; 

                // 2. NOMBRE DEL EVENTO (Azul, Grande)
                String nombreEv = (eventoNombre != null && !eventoNombre.isBlank()) ? eventoNombre : "Evento ID: " + eventoId;
                y = writeMultiLineText(cs, nombreEv, margin, y, width, 22, true, COLOR_AZUL);
                
                y -= 20;

                // 3. IMAGEN QR (Centrada)
                PDImageXObject pdImage = LosslessFactory.createFromImage(doc, qrImage);
                float qrSize = 200f; 
                float qrX = (pageWidth - qrSize) / 2;
                cs.drawImage(pdImage, qrX, y - qrSize, qrSize, qrSize);
                y -= (qrSize + 20); 

                // 4. DESCRIPCIÓN DEL EVENTO (NUEVO REQUISITO: Bajo el QR)
                if (eventoDesc != null && !eventoDesc.isBlank()) {
                    // Etiqueta "Descripción del Evento:" en Azul/Negrita
                    y = writeMultiLineText(cs, "Descripción del Evento:", margin, y, width, 12, true, COLOR_AZUL);
                    // Valor en negro
                    y = writeMultiLineText(cs, eventoDesc, margin, y, width, 11, false, COLOR_NEGRO);
                    y -= 15; 
                }

                // 5. DETALLES (Etiquetas Azules y en Negrita)
                
                // Referencia
                if (referencia != null && !referencia.isBlank()) {
                    y = writeStyledField(cs, "Referencia:", referencia, margin, y, width);
                }

                // Usuario
                String usuarioTxt = (usuarioLogin != null && !usuarioLogin.isBlank()) ? usuarioLogin : "ID " + usuarioId;
                y = writeStyledField(cs, "Usuario:", usuarioTxt, margin, y, width);

                y -= 5; 

                // Tipo y Descripción Entrada
                y = writeStyledField(cs, "Tipo de entrada:", nvl(tipoEntrada), margin, y, width);
                if (tipoDesc != null && !tipoDesc.isBlank()) {
                    y = writeStyledField(cs, "Descripción entrada:", tipoDesc, margin, y, width);
                }

                y -= 10; // Separador

                // Datos Comprador
                y = writeStyledField(cs, "Comprador:", nvl(nombre) + " " + nvl(apellidos), margin, y, width);
                y = writeStyledField(cs, "DNI:", nvl(dni), margin, y, width);
                y = writeStyledField(cs, "Email:", nvl(email), margin, y, width);
                y = writeStyledField(cs, "Teléfono:", nvl(telefono), margin, y, width);
                y = writeStyledField(cs, "F. Nacimiento:", nvl(dob), margin, y, width);

                y -= 10;
                y = writeStyledField(cs, "Fecha de compra:", nvl(fechaCompra), margin, y, width);

                y -= 20;
                // Código Texto (Pequeño, todo negro)
                writeMultiLineText(cs, "Código: " + codigoQr, margin, y, width, 10, false, COLOR_NEGRO);
            }

            doc.save(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException("Error generando el PDF de la entrada", e);
        }
    }

    // --- Helper: Escribe "Etiqueta: Valor" con estilos mixtos ---
    private float writeStyledField(PDPageContentStream cs, String label, String value, float x, float y, float width) throws IOException {
        // 1. Escribir la Etiqueta en AZUL y NEGRITA
        cs.setNonStrokingColor(COLOR_AZUL);
        cs.setFont(PDType1Font.HELVETICA_BOLD, 12);
        cs.beginText();
        cs.newLineAtOffset(x, y);
        cs.showText(label);
        cs.endText();

        // Calculamos cuánto ocupa la etiqueta para saber dónde empezar el valor
        float labelWidth = (PDType1Font.HELVETICA_BOLD.getStringWidth(label) / 1000 * 12) + 5; // +5px de espacio
        
        // 2. Escribir el Valor en NEGRO y NORMAL justo después
        return writeMultiLineText(cs, value, x + labelWidth, y, width - labelWidth, 12, false, COLOR_NEGRO);
    }

    // --- LÓGICA DE WRAPPING (AJUSTE DE LÍNEA AUTOMÁTICO) ---
    private float writeMultiLineText(PDPageContentStream cs, String text, float x, float y, float allowedWidth, int fontSize, boolean bold, Color color) throws IOException {
        PDFont font = bold ? PDType1Font.HELVETICA_BOLD : PDType1Font.HELVETICA;
        float leading = 1.2f * fontSize; 

        cs.setFont(font, fontSize);
        cs.setNonStrokingColor(color);

        String safeText = (text != null) ? text.replace("\n", " ").replace("\r", " ") : "-";
        String[] words = safeText.split(" ");
        List<String> lines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            String testLine = currentLine.length() == 0 ? word : currentLine + " " + word;
            float textWidth = font.getStringWidth(testLine) / 1000 * fontSize;

            if (textWidth > allowedWidth) {
                if (currentLine.length() > 0) {
                    lines.add(currentLine.toString());
                    currentLine = new StringBuilder(word);
                } else {
                    lines.add(word); // Palabra muy larga
                    currentLine = new StringBuilder();
                }
            } else {
                currentLine = new StringBuilder(testLine);
            }
        }
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        // Escribir líneas
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            cs.beginText();
            // Si es la primera línea, usamos la X original.
            // Si son líneas siguientes (wrapping), usamos X ajustada al inicio? 
            // En este helper genérico, siempre usamos 'x'. 
            // Nota: Para 'writeStyledField', si el valor salta de línea, se alineará debajo del valor, no debajo de la etiqueta.
            cs.newLineAtOffset(x, y);
            cs.showText(line);
            cs.endText();
            y -= leading; 
        }

        return y; 
    }

    private static String nvl(String s) { return (s == null || s.isBlank()) ? "-" : s; }

    private String buildPdfFilename(String referencia, String nombre, String apellidos) {
        String ref = (referencia == null || referencia.isBlank()) ? "Entrada" : referencia;
        String safeRef = sanitizeForFilename(ref);
        String safeNom = sanitizeForFilename(nombre);
        String safeApe = sanitizeForFilename(apellidos);
        return "Entrada_" + safeRef + "_" + safeNom + safeApe + ".pdf";
    }

    private static String sanitizeForFilename(String input) {
        if (input == null) return "";
        String s = Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("\\p{M}+", "");
        s = s.replaceAll("[^a-zA-Z0-9._-]+", "_");
        s = s.replaceAll("_+", "_").replaceAll("^_+|_+$", "");
        return s;
    }

    private ResponseEntity<byte[]> responsePdf(String filename, byte[] pdfBytes) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename(filename).build());
        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}