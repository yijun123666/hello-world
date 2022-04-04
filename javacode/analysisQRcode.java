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

public class analysisQRcode {
	
	public static final int QRCODE_SIZE = 300;
	
	public static void readQRcode(String filepath) {
		 
		MultiFormatReader multiFormatReader = new MultiFormatReader();
		File file = new File(filepath);
 
		BufferedImage image = null;
 
		BinaryBitmap binaryBitmap = null;
 
		Result result = null;
 
		try {
			image = ImageIO.read(file);
			binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
			result = multiFormatReader.decode(binaryBitmap);
		} catch (IOException | NotFoundException e1) {
			e1.printStackTrace();
		}
 
	
		System.out.println(result.getText());
	}

	public static void main(String[] args) {
		 
		
		String filepath =  "D:\\maven\\ticket-system\\QRcode" + File.separator + "123.jpg";
		readQRcode(filepath);
 
	
	}

}
