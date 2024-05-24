package com.dreyer.bradescocodechallenge.web.presenter;

import com.dreyer.bradescocodechallenge.business.boundary.output.PostCheckoutOutput;
import com.dreyer.bradescocodechallenge.business.boundary.responsemodel.PaymentMethodResponseModel;
import com.dreyer.bradescocodechallenge.common.ErrorResponseModel;
import io.nayuki.qrcodegen.QrCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Objects;

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
                    .body(convert(sucessThreadLocal.get().getValue()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            errorsThreadLocal.remove();
            sucessThreadLocal.remove();
        }
    }

    private byte[] convert(String value) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(textToQrCode(value), "png", baos);
        return baos.toByteArray();
    }

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
}
