package com.mesutemre.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import com.mesutemre.model.ResponseStatus;

/**
 * @author mesutemre.celenk
 */

public class KutuphaneSistemiUtil {

    public static String getEvetHayirByBooleanValue(Boolean value){
	if(value != null){
	    if(value == Boolean.TRUE){
		return "E";
	    }else{
		return "H";
	    }
	}
	return null;
    }
    
    public static String getStringByArrayList(List<String> liste){
	if(liste != null){
	    int size = liste.size();
	    StringBuilder sb = new StringBuilder();
	    
	    for(int i = 0;i<size;i++){
		sb.append(liste.get(i));
		if(i!=size-1){
		    sb.append(",");
		}
	    }
	    
	    return sb.toString();
	}
	return null;
    }
    
    public static boolean getBooelanByEH(String value){
	if(value != null&&value.equals("E")){
	    return true;
	}
	return false;
    }
    
    public static List<Integer> getIntegerListByString(String str){
	List<Integer> intListe = null;
	if(str != null){
	    intListe = new ArrayList<Integer>();
	    List<String> strListe = Arrays.asList(str.split(","));
	    int size = strListe.size();

	    for (int i = 0; i < size; i++) {
		intListe.add(Integer.parseInt(strListe.get(i)));
	    }
	}
	
	return intListe;
    }
    
    public static List<String> getStringList(String str){
	if(str != null){
	    return Arrays.asList(str.split(","));
	}
	return null;
    }
    
    public static String getLoggedInUser(){
	 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	if(!auth.getName().equals("anonymousUser")){
	    return auth.getName();
	}
	return null;
    }
    
    public static String getTempFileUploadPath(){
	String path = "";
	if(System.getProperty("os.name").startsWith("Windows")){
	    path = "Windows";
	}else{
	    path = "/home/mesud/Belgeler/kutuphanesistemitemp";
	}
	
	return path;
    }
    
    public static String getUploadProfilResmiPath(){
		String path = "";
		if(System.getProperty("os.name").startsWith("Windows")){
		    path = "Windows";
		}else{
		    path = "/home/mesut/Belgeler/kutuphanesistemi/profilresim/";
		}
		
		return path;
    }
    
    public static String getFileExtension(String fileName){
	String extension = "";

	int i = fileName.lastIndexOf('.');
	if (i > 0) {
	    extension = fileName.substring(i+1);
	}
	return extension;
    }
    
    public static File convertMultiPartFileToFile(MultipartFile multiPartFile){
	File convFile = new File(multiPartFile.getOriginalFilename());
	try {
	    convFile.createNewFile(); 
	    FileOutputStream fos = new FileOutputStream(convFile); 
	    fos.write(multiPartFile.getBytes());
	    fos.close();
	} catch (Exception e) {
	    e.printStackTrace();
	} 
	return convFile;
    }
    
    public static String readHTMLFile(String urlPath){
	StringBuilder ticket = new StringBuilder();
        BufferedReader br = null;
        try {
                        
            URL url = new URL(urlPath);            
            HttpURLConnection con = (HttpURLConnection)url.openConnection();            
            br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));            
            String input;
            while ((input = br.readLine()) != null) {
                ticket.append(input);
            }
            br.close();
        }catch(RuntimeException | IOException e){
		}finally{
		    if(br!=null)
			try {
			    br.close();
			} catch (IOException e) {
			    e.printStackTrace();
			}
        }
        return ticket.toString();
    }
    
    public static ResponseStatus getResponseStatus(String code,String message) {
		return new ResponseStatus(code, message);
	}
    
    public static String imageToBase64String(File imageFile){
		
	    String image=null;
	    try{
	    	String fileType = java.nio.file.Files.probeContentType(imageFile.toPath());
	    	if(fileType!=null && !fileType.startsWith("image")){
	    		throw new RuntimeException("Hatalı dosya tipi!");
	    	}
	    	if(fileType==null)
	    		return image;
	    	
		    BufferedImage buffImage = ImageIO.read(imageFile);
		
		    if(buffImage != null) {
		    	java.io.ByteArrayOutputStream os = new java.io.ByteArrayOutputStream();
		        ImageIO.write(buffImage, "png", os);
		        byte[] data = os.toByteArray();
		        image = Base64.encode(data);
 		    }
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return image;
	}
    
    public static String getKitapPath() {
    	String path = "";
    	if(System.getProperty("os.name").startsWith("Windows")){
    	    path = "Windows";
    	}else{
    	    path = "/home/mesut/Belgeler/kutuphanesistemi/kitaplar/";
    	}
    	
    	return path;
    }
    
    public static String getKutuphaneSistemiPath() {
    	String path = "";
    	if(System.getProperty("os.name").startsWith("Windows")){
    	    path = "Windows";
    	}else{
    	    path = "/home/mesut/Belgeler/kutuphanesistemi/";
    	}
    	
    	return path;
    }
    
    public static String getFullURL(HttpServletRequest request) {
        StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }
    
    public static byte[] resizeImage(File originalFile, int targetWidth, int targetHeight) throws IOException {
	    BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
	    Graphics2D graphics2D = resizedImage.createGraphics();
	    BufferedImage originalImage = ImageIO.read(originalFile);
	    graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
	    graphics2D.dispose();
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ImageIO.write(resizedImage, "png", baos);
	    return baos.toByteArray();
	}
}
