package com.greenfoxacademy.springwebapp.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
public class QrCodeGenerator {
  public static final String BASE_PATH = "src/main/resources/static/images/";

  public static File createQrCode(String qrCodeText, int size, String fileType) throws WriterException, IOException {
    String qrCodeFilePath = String.format("%s%s.%s", BASE_PATH, "qrcode", fileType);
    File qrFile = new File(qrCodeFilePath);
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size);

    BufferedImage bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
    bufferedImage.createGraphics();

    Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, size, size);
    graphics.setColor(Color.BLACK);

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (bitMatrix.get(i, j)) {
          graphics.fillRect(i, j, 1, 1);
        }
      }
    }
    ImageIO.write(bufferedImage, fileType, qrFile);

    return qrFile;
  }
}
