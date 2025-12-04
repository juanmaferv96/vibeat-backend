package com.vibeat.backend.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.Map;

@Service
public class QrCodeService {

    /**
     * Genera un PNG de un código QR a partir del texto indicado (UTF-8).
     * @param text  texto a codificar (en nuestro caso, CODIGO_QR)
     * @param size  tamaño del lado en píxeles (p. ej., 384)
     * @param margin quiet zone (margen blanco) en módulos (p. ej., 2-4)
     * @param level nivel de corrección de errores (M/Q/H)
     */
    public byte[] generatePng(String text, int size, int margin, ErrorCorrectionLevel level) {
        try {
            Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.ERROR_CORRECTION, level != null ? level : ErrorCorrectionLevel.M);
            hints.put(EncodeHintType.MARGIN, margin);
            hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());

            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, size, size, hints);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", baos);
            return baos.toByteArray();
        } catch (WriterException | java.io.IOException e) {
            throw new IllegalStateException("Error generando el código QR", e);
        }
    }
}
