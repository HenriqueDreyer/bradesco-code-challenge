package com.dreyer.bradescocodechallenge.web.presenter;

import com.dreyer.bradescocodechallenge.business.boundary.output.PostCheckoutOutput;
import com.dreyer.bradescocodechallenge.business.boundary.responsemodel.PaymentMethodResponseModel;
import com.dreyer.bradescocodechallenge.common.ErrorResponseModel;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class PostCheckoutPresenter implements PostCheckoutOutput {
    private final ThreadLocal<List<ErrorResponseModel>> errorsThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<PaymentMethodResponseModel> sucessThreadLocal = new ThreadLocal<>();

    public void error(List<ErrorResponseModel> errors) {
        errorsThreadLocal.set(errors);
    }

    public void success(PaymentMethodResponseModel responseModel) {
        sucessThreadLocal.set(responseModel);
    }

    public ResponseEntity<Object> presenter() {
        try {

            if(errorsThreadLocal.get() != null && !errorsThreadLocal.get().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorsThreadLocal.get());
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.IMAGE_PNG)
                    .body(convert(sucessThreadLocal.get()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            errorsThreadLocal.remove();
            sucessThreadLocal.remove();
        }
    }

    private byte[] convert(PaymentMethodResponseModel responseModel) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(createQRwithText(responseModel.getValue(), "Bradesco Code Challenge", responseModel.getTransactionId()), "png", baos);
        return baos.toByteArray();
    }

    public BufferedImage createQRwithText(String data, String topText, String bottomText) throws WriterException, IOException {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix matrix = barcodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);
        return modifiedQRCode(matrix, topText, bottomText);
    }

    private BufferedImage modifiedQRCode(BitMatrix matrix, String topText, String bottomText) {
        int matrixWidth = matrix.getWidth();
        int matrixHeight = matrix.getHeight();

        BufferedImage image = new BufferedImage(matrixWidth, matrixHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixHeight);
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixHeight; j++) {
                if (matrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        FontMetrics fontMetrics = graphics.getFontMetrics();
        int topTextWidth = fontMetrics.stringWidth(topText);
        int bottomTextWidth = fontMetrics.stringWidth(bottomText);
        int finalWidth = Math.max(matrixWidth, Math.max(topTextWidth, bottomTextWidth)) + 1;
        int finalHeight = matrixHeight + fontMetrics.getHeight() + fontMetrics.getAscent() + 1;

        BufferedImage finalImage = new BufferedImage(finalWidth, finalHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D finalGraphics = finalImage.createGraphics();
        finalGraphics.setColor(Color.WHITE);
        finalGraphics.fillRect(0, 0, finalWidth, finalHeight);
        finalGraphics.setColor(Color.BLACK);

        finalGraphics.drawImage(image, (finalWidth - matrixWidth) / 2, fontMetrics.getAscent() + 2, null);
        finalGraphics.drawString(topText, (finalWidth - topTextWidth) / 2, fontMetrics.getAscent() + 2);
        finalGraphics.drawString(bottomText, (finalWidth - bottomTextWidth) / 2, finalHeight - fontMetrics.getDescent() - 5);

        return finalImage;
    }
/*
    private BufferedImage textToQrCode(String barcodeText) throws Exception {
        QrCode qrCode = QrCode.encodeText(barcodeText, QrCode.Ecc.MEDIUM);
        BufferedImage img = toImage(qrCode, 4, 10);
        return img;
    }

    private BufferedImage toImage(QrCode qr, int scale, int border) {
        return toImage(qr, scale, border, 0xFFFFFF, 0x000000);
    }

    private BufferedImage toImage(QrCode qr, int scale, int border, int lightColor, int darkColor) {
        Objects.requireNonNull(qr);
        if (scale <= 0 || border < 0) {
            throw new IllegalArgumentException("Value out of range");
        }
        if (border > Integer.MAX_VALUE / 2 || qr.size + border * 2L > Integer.MAX_VALUE / scale) {
            throw new IllegalArgumentException("Scale or border too large");
        }

        BufferedImage result = new BufferedImage(
                (qr.size + border * 2) * scale,
                (qr.size + border * 2) * scale,
                BufferedImage.TYPE_INT_RGB
        );
        for (int y = 0; y < result.getHeight(); y++) {
            for (int x = 0; x < result.getWidth(); x++) {
                boolean color = qr.getModule(x / scale - border, y / scale - border);
                result.setRGB(x, y, color ? darkColor : lightColor);
            }
        }
        return result;
    }
    */
}
