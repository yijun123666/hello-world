package javacode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import org.apache.commons.codec.binary.Base64;
import com.google.gson.JsonObject;
import com.alibaba.fastjson.JSONObject;

public class wallet {
	public static Map<Integer, String> keyMap = new HashMap<Integer, String>();  //to store the pub and priv key
	public static String data = "12345";
	private static String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/OpVr+aQu6B3stSUgsLcZWpaxatset8zTqat1FF543hoECcTnRqDXKwfX09J+RLCc/1fbITt0s4wUUwJNU7lKJSTGZp5/xHcEiFJjTa+XY6pQHQKvvZjAQMkyzC3H5tmaNTapKYJOAWw7u1dxcRNFdD3k5E+EiqSnlo30u7SLCwIDAQAB";
	private static String privateKeyStr = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL86lWv5pC7oHey1JSCwtxlalrFq2x63zNOpq3UUXnjeGgQJxOdGoNcrB9fT0n5EsJz/V9shO3SzjBRTAk1TuUolJMZmnn/EdwSIUmNNr5djqlAdAq+9mMBAyTLMLcfm2Zo1Nqkpgk4BbDu7V3FxE0V0PeTkT4SKpKeWjfS7tIsLAgMBAAECgYBicjt4geV3TIITWVJK2Q76G3vWzIcP8lmdYgzl0l2sZdMI3yqiUeb9vqZkAyWrYZt2x7GoGxyrwL9Nu0pFGuQZFaZIrHRj6LoNq/dgGUpN5zviXUDq2RrhhP7dW4Zc2UbbZqtTzn4jgv8/dviT+LACBmbavojjbb6YZHO/YDml2QJBAPWWu7SkyqfHSDOBBYWyI0GON2ApqTOIsENpQ572IvjNzT8TcXsNRr1hy4o5JfJN4KutBSsJkxAv3+nCc7pvRo0CQQDHVefkgjyuCyQjTtm8WPeIP7Ny8Rul44SmoyaSOANiPufsjIAPvxtNwyvkyUKtI7AMx6XrAWltRMWWiByVH533AkBp87fTfWz46V7a6YTqYyoWtDZrxE19MDFrQ9SqleIMmS09UzQYNGgaeECJx5H5cWPGbQTXxm+uAhmGDiBDhJJZAkEAu84SR1b1OL1CdQmrVyszPGlX9ul3NRphNmbsxkKD3aKK/HF7jlptrRw/VLTSXzIKgl/v0LRp0gtDZgojc9RwDQJBAJ2d0E9huqG9yP0bA9q0lIFwqJogLnoRvQCkNW6hATUrA5b7lrZYniPbwRfSALW2jgweTeTaeouPBHPWbVz/ws8=";


	private static void generateKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator;
		keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(1024);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		PublicKey publicKey = keyPair.getPublic();
		publicKeyStr = new String(Base64.encodeBase64(publicKey.getEncoded()));
		PrivateKey privateKey = keyPair.getPrivate();
		privateKeyStr = new String(Base64.encodeBase64(privateKey.getEncoded()));
		keyMap.put(0, publicKeyStr); //pub
		keyMap.put(1, privateKeyStr);//priv
	}

	private static PublicKey getPublicKey(String publicKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(keySpec);
	}


	private static PrivateKey getPrivateKey(String privateKey) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(privateKey);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(keySpec);
	}

	public static String encryptByPublicKey(String content) throws Exception {
		// ��ȡ��Կ
		PublicKey publicKey = getPublicKey(publicKeyStr);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] cipherText = cipher.doFinal(content.getBytes());
		String cipherStr = new String(Base64.encodeBase64(cipherText));
		return cipherStr;
	}

	public static String encryptByPrivateKey(String content) throws Exception {
		PrivateKey privateKey = getPrivateKey(privateKeyStr);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		byte[] cipherText = cipher.doFinal(content.getBytes());
		String cipherStr = new String(Base64.encodeBase64(cipherText));
		return cipherStr;
	}

	public static String decryptByPrivateKey(String content) throws Exception {
		PrivateKey privateKey = getPrivateKey(privateKeyStr);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] cipherText = Base64.decodeBase64(content);
		byte[] decryptText = cipher.doFinal(cipherText);
		return new String(decryptText);
	}

	public static String decryptByPublicKey(String content) throws Exception {
		PublicKey publicKey = getPublicKey(publicKeyStr);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		byte[] cipherText = Base64.decodeBase64(content);
		byte[] decryptText = cipher.doFinal(cipherText);
		return new String(decryptText);
	}
	
	public static int ticketHash(ticket t) {
		JsonObject jsonContainer =new JsonObject();
			jsonContainer.addProperty("ename", t.getEname());
			jsonContainer.addProperty("position", t.getSeat());

			
			return jsonContainer.hashCode();
	}
	
	public static String getTicketID(String jsonStr) {
		JSONObject jsonobject = JSONObject.parseObject(jsonStr );
		String s = jsonobject.getString("iD");
		return s;
	}
	
	public static String getTicketPrice(String jsonStr) {
		JSONObject jsonobject = JSONObject.parseObject(jsonStr );
		String s = jsonobject.getString("price");
		return s;
	}
	
	public static String getTicketSeat(String jsonStr) {
		JSONObject jsonobject = JSONObject.parseObject(jsonStr );
		String s = jsonobject.getString("position");
		return s;
	}
	
	public static String getTicketename(String jsonStr) {
		JSONObject jsonobject = JSONObject.parseObject(jsonStr );
		String s = jsonobject.getString("name");
		return s;
	}
	

		public static void createUserWallet(String username) throws IOException, NoSuchAlgorithmException {
			username="mu2"; // an example
				//1. 文件夹的路径  文件名
		        String directory = "C:\\Users\\lenovo\\Desktop\\FYP\\ticketSystem\\wallet";
		        String filename = username+".txt";

		        //2.  创建文件夹对象     创建文件对象
		        File file = new File(directory);
		        //如果文件夹不存在  就创建一个空的文件夹
		        if (!file.exists()) {
		            file.mkdirs();
		        }
		        File file2 = new File(directory, filename);
		        //如果文件不存在  就创建一个空的文件
		        if (!file2.exists()) {
		            try {
		                file2.createNewFile();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
		        //3.写入数据
		        //创建文件字节输出流
		        FileOutputStream fos = new FileOutputStream(directory + "\\" + filename);
		        //开始写
		        generateKeyPair();
		        String str = keyMap.get(0) + "\n" +keyMap.get(1);
		        byte[] bytes = str.getBytes();
		        //将byte数组中的所有数据全部写入
		        fos.write(bytes);
		        //关闭流
		        fos.close();
			}
			
			public static void extractUserWallet(String username) throws IOException {
				username ="mu2";//an example
				File file = new File("C:\\Users\\lenovo\\Desktop\\FYP\\ticketSystem\\wallet\\"+username+".txt");
				BufferedReader br = new BufferedReader(new FileReader(file));
				String str1=null;
				String str2=null;
				str1 = br.readLine();
				keyMap.put(0, str1);
				System.out.println("public key is: "+ str1);
				str2 = br.readLine();
				keyMap.put(1, str2);
				System.out.println("private key is: "+ str2);
				br.close();
				
			}

			public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
				wallet.createUserWallet("a");
				wallet.extractUserWallet("a");

			}

}
