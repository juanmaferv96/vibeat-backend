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
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.Normalizer;
import java.time.format.DateTimeFormatter;
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

    public PdfEntradaService(EntradaOficialRepository entradaOficialRepository,
                             EntradaNoOficialRepository entradaNoOficialRepository,
                             QrCodeService qrCodeService) {
        this.entradaOficialRepository = entradaOficialRepository;
        this.entradaNoOficialRepository = entradaNoOficialRepository;
        this.qrCodeService = qrCodeService;
    }

    // ---------- OFICIAL ----------
    public ResponseEntity<byte[]> descargarPdfOficial(Long id,
                                                      String eventoNombre,
                                                      String usuarioLogin,
                                                      String tipoDesc) {
        EntradaOficial e = entradaOficialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entrada oficial no encontrada: id=" + id));

        byte[] pdf = buildPdfParaEntrada(
                "Entrada Oficial",
                e.getCodigoQr(),
                e.getReferencia(),
                /*eventoNombre*/ eventoNombre,
                /*eventoId*/ e.getEventoId(),
                /*usuarioLogin*/ usuarioLogin,
                /*usuarioId*/ e.getUsuarioId(),
                /*tipoEntrada*/ e.getTipoEntrada(),
                /*tipoDesc*/ tipoDesc,
                e.getNombreComprador(),
                e.getApellidosComprador(),
                e.getDniComprador(),
                e.getEmailComprador(),
                e.getTelefonoComprador(),
                e.getFechaNacimientoComprador() != null ? e.getFechaNacimientoComprador().format(DOB_FMT) : "-",
                e.getFechaCompra() != null ? e.getFechaCompra().format(TS_HUMANO) : "-"
        );

        String filename = buildPdfFilename(e.getReferencia(), e.getNombreComprador(), e.getApellidosComprador());
        return responsePdf(filename, pdf);
    }

    // ---------- NO OFICIAL ----------
    public ResponseEntity<byte[]> descargarPdfNoOficial(Long id,
                                                        String eventoNombre,
                                                        String usuarioLogin,
                                                        String tipoDesc) {
        EntradaNoOficial e = entradaNoOficialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entrada no oficial no encontrada: id=" + id));

        byte[] pdf = buildPdfParaEntrada(
                "Entrada No Oficial",
                e.getCodigoQr(),
                e.getReferencia(),
                /*eventoNombre*/ eventoNombre,
                /*eventoId*/ e.getEventoId(),
                /*usuarioLogin*/ usuarioLogin,
                /*usuarioId*/ e.getUsuarioId(),
                /*tipoEntrada*/ e.getTipoEntrada(),
                /*tipoDesc*/ tipoDesc,
                e.getNombreComprador(),
                e.getApellidosComprador(),
                e.getDniComprador(),
                e.getEmailComprador(),
                e.getTelefonoComprador(),
                e.getFechaNacimientoComprador() != null ? e.getFechaNacimientoComprador().format(DOB_FMT) : "-",
                e.getFechaCompra() != null ? e.getFechaCompra().format(TS_HUMANO) : "-"
        );

        String filename = buildPdfFilename(e.getReferencia(), e.getNombreComprador(), e.getApellidosComprador());
        return responsePdf(filename, pdf);
    }

    // ---------- Construcción del PDF ----------
    private byte[] buildPdfParaEntrada(
            String titulo,
            String codigoQr,
            String referencia,
            String eventoNombre, Long eventoId,
            String usuarioLogin, Long usuarioId,
            String tipoEntrada, String tipoDesc,
            String nombre, String apellidos,
            String dni, String email, String telefono,
            String dob, String fechaCompra
    ) {
        if (codigoQr == null || codigoQr.isBlank()) {
            throw new IllegalStateException("La entrada no tiene CODIGO_QR");
        }

        byte[] qrPng = qrCodeService.generatePng(codigoQr, 512, 2, ErrorCorrectionLevel.Q);
        BufferedImage qrImage;
        try {
            qrImage = ImageIO.read(new ByteArrayInputStream(qrPng));
        } catch (Exception ex) {
            throw new IllegalStateException("No se pudo leer la imagen PNG del QR", ex);
        }

        try (PDDocument doc = new PDDocument(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            float margin = 50f;
            float y = page.getMediaBox().getHeight() - margin;
            float xLeft = margin;
            float xRight = page.getMediaBox().getWidth() - margin;

            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                // Título
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_BOLD, 20);
                cs.newLineAtOffset(xLeft, y);
                cs.showText(titulo);
                cs.endText();
                y -= 30;

                // Subtítulo: referencia
                if (referencia != null && !referencia.isBlank()) {
                    y = writeLine(cs, "Referencia: " + referencia, xLeft, y, 12, true);
                }

                // Evento / Usuario (ahora por nombre/username si vienen por query)
                if (eventoNombre != null && !eventoNombre.isBlank()) {
                    y = writeLine(cs, "Evento: " + eventoNombre, xLeft, y, 12, true);
                } else {
                    y = writeLine(cs, "Evento ID: " + (eventoId != null ? eventoId : "-"), xLeft, y, 12, true);
                }

                if (usuarioLogin != null && !usuarioLogin.isBlank()) {
                    y = writeLine(cs, "Usuario (login): " + usuarioLogin, xLeft, y, 12, false);
                } else {
                    y = writeLine(cs, "Usuario ID: " + (usuarioId != null ? usuarioId : "-"), xLeft, y, 12, false);
                }

                // Tipo y descripción
                y = writeLine(cs, "Tipo de entrada: " + nvl(tipoEntrada), xLeft, y, 12, false);
                if (tipoDesc != null && !tipoDesc.isBlank()) {
                    y = writeLine(cs, "Descripción: " + tipoDesc, xLeft, y, 12, false);
                }

                y -= 8;
                y = writeLine(cs, "Comprador: " + nvl(nombre) + " " + nvl(apellidos), xLeft, y, 12, true);
                y = writeLine(cs, "DNI: " + nvl(dni), xLeft, y, 12, false);
                y = writeLine(cs, "Email: " + nvl(email), xLeft, y, 12, false);
                y = writeLine(cs, "Teléfono: " + nvl(telefono), xLeft, y, 12, false);
                y = writeLine(cs, "Fecha de nacimiento: " + nvl(dob), xLeft, y, 12, false);

                y -= 8;
                y = writeLine(cs, "Fecha de compra: " + nvl(fechaCompra), xLeft, y, 12, true);

                y -= 18;
                y = writeLine(cs, "Código (texto): " + codigoQr, xLeft, y, 11, false);

                // Imagen QR (derecha)
                PDImageXObject pdImage = LosslessFactory.createFromImage(doc, qrImage);
                float qrSize = 200f;
                float qrX = xRight - qrSize;
                float qrY = page.getMediaBox().getHeight() - margin - qrSize;
                cs.drawImage(pdImage, qrX, qrY, qrSize, qrSize);
            }

            doc.save(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException("Error generando el PDF de la entrada", e);
        }
    }

    private static float writeLine(PDPageContentStream cs, String text, float x, float y, int size, boolean bold) throws Exception {
        cs.beginText();
        cs.setFont(bold ? PDType1Font.HELVETICA_BOLD : PDType1Font.HELVETICA, size);
        cs.newLineAtOffset(x, y);
        cs.showText(text != null ? text : "-");
        cs.endText();
        return y - (size + 6);
    }

    private static String nvl(String s) { return (s == null || s.isBlank()) ? "-" : s; }

    // ======= NUEVO: nombre de archivo sin corchetes =======
    private String buildPdfFilename(String referencia, String nombre, String apellidos) {
        String ref = (referencia == null || referencia.isBlank()) ? "Entrada" : referencia;
        String safeRef = sanitizeForFilename(ref);
        String safeNom = sanitizeForFilename(nombre);
        String safeApe = sanitizeForFilename(apellidos);
        // Formato: Entrada_<REFERENCIA>_<Nombre><Apellidos>.pdf
        return "Entrada_" + safeRef + "_" + safeNom + safeApe + ".pdf";
    }

    private static String sanitizeForFilename(String input) {
        if (input == null) return "";
        String s = Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("\\p{M}+", "");
        s = s.replaceAll("[^a-zA-Z0-9._-]+", "_");
        s = s.replaceAll("_+", "_").replaceAll("^_+|_+$", "");
        return s;
    }
    // =======================================================

    private ResponseEntity<byte[]> responsePdf(String filename, byte[] pdfBytes) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename(filename).build());
        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}
