package ru.rgups.time.utils;

/*
 * Здесь будут всякие полезные няшки 
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import android.util.Log;

public class Slipper {
	public static final Pattern pattern = Pattern.compile(".+@.+\\.[a-z].+");
	 
	 
	//Генерация md5 из строки (с) - Кто-то с stackoverflow + какой-то грек исправивший глюк с 0
	public static final String md5(final String s) {
	    try {
	        // Create MD5 Hash
	        MessageDigest digest = java.security.MessageDigest
	                .getInstance("MD5");
	        digest.update(s.getBytes("UTF-8"));
	        byte messageDigest[] = digest.digest();
	 
	        // Create Hex String
	        StringBuffer hexString = new StringBuffer();
	        for (int i = 0; i < messageDigest.length; i++) {
	            String h = Integer.toHexString(0xFF & messageDigest[i]);
	            while (h.length() < 2)
	                h = "0" + h;
	            hexString.append(h);
	        }
	        return hexString.toString();
	 
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return "";
	}
	
	public static byte[] serializeObject(Object o) { 
	    ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
	 
	    try { 
	      ObjectOutput out = new ObjectOutputStream(bos); 
	      out.writeObject(o); 
	      out.close(); 
	 
	      // Get the bytes of the serialized object 
	      byte[] buf = bos.toByteArray(); 
	 
	      return buf; 
	    } catch(IOException ioe) { 
	      Log.e("serializeObject", "error", ioe); 
	 
	      return null; 
	    } 
	  } 

	public static ArrayList<String> deserializeObjectToString(byte[] object){
		try {
			ObjectInputStream stream = null;
			ArrayList<String> list = null;
			if(object!=null){
				stream = new ObjectInputStream(new ByteArrayInputStream(object));
				list = (ArrayList<String>) stream.readObject();
				return list;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
		
	}
		
	
	
	public static ArrayList<Integer> deserializeObjectToInteger(byte[] object){
		try {
			ObjectInputStream stream = null;
			ArrayList<Integer> list = null;
			if(object!=null){
				stream = new ObjectInputStream(new ByteArrayInputStream(object));
				list = (ArrayList<Integer>) stream.readObject();
				return list;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
		
	}
	

	public static ArrayList<Long> deserializeObjectToLong(byte[] object){
		try {
			ObjectInputStream stream = null;
			ArrayList<Long> list = null;
			if(object!=null){
				stream = new ObjectInputStream(new ByteArrayInputStream(object));
				list = (ArrayList<Long>) stream.readObject();
				return list;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
		
	}
	
}
