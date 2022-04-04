package javacode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
 
import javax.imageio.ImageIO;
 
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class generateQRcode {

	
	public static final int QRCODE_SIZE = 300;
	
	public static final String CONTENT = "sb";
	
	public static void generateQRcodePic(String content, int width, int height, String picFormat) {
		 
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.MARGIN, 1);
 
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
 
			String fileDir = "D:\\maven\\ticket-system\\QRcode";
			if (!new File(fileDir).exists()) {
				new File(fileDir).mkdirs();
			}
			Path file = new File(fileDir + File.separator + "myjjjjj" + "."+ picFormat).toPath();
 
			MatrixToImageWriter.writeToPath(bitMatrix, picFormat, file);
		} catch (WriterException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static byte[] generateQRcodeByte(String content, int width, String picFormat) {
		byte[] codeBytes = null;
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, width);
			BufferedImage image = toBufferedImage(bitMatrix);
 
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(image, picFormat, out);
 
			codeBytes = out.toByteArray();
 
		} catch (WriterException | IOException e) {
			e.printStackTrace();
		}
		return codeBytes;
	}
	
	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int onColor = 0xFF000000;
		int offColor = 0xFFFFFFFF;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? onColor : offColor);
			}
		}
		return image;
	}
	
	
	public static void main(String[] args) {
		 
		generateQRcodePic(CONTENT, QRCODE_SIZE, QRCODE_SIZE, "jpg");
	}
}


