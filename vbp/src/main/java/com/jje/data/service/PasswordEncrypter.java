package com.jje.data.service;

import org.apache.commons.lang.StringUtils;

import com.jje.common.utils.MD5Utils;
import com.jje.data.util.TxtFileReader;
import com.jje.data.util.TxtFileWriter;



public class PasswordEncrypter {
	
	public final static String SEPARATOR = ",";
	public final static int ARRAY_LENGTH = 6;
	public final static int PASSWORD_POSITION = 1;
	public final static int CARDNO_POSITION = 0;
	
	/**
	 * @param oldFilePath
	 * @param newFilePath
	 */
	public void exec(String oldFilePath, String newFilePath){
		TxtFileReader reader = new TxtFileReader();
		TxtFileWriter writer = new TxtFileWriter();
		reader.open(oldFilePath);
		writer.open(newFilePath);
		
		int totalNum = 0;
		int emptyPwNum = 0;
		int newLineNum = 0;
		int errLineNum = 0;
		
		String newLine = "";
		String line = "";
		while(StringUtils.isNotBlank(line = reader.nextLine())){
//			if(totalNum == 100){
//				break;
//			}
			totalNum ++;
			
			if(totalNum % 1000 == 0){
				System.out.println("Already readed " +totalNum);
			}
			String[] array = line.split(SEPARATOR);
			if(array.length != ARRAY_LENGTH && array.length != ARRAY_LENGTH-1){
				errLineNum ++;
				continue;
			}
			if(StringUtils.isBlank(array[PASSWORD_POSITION])){
				
				emptyPwNum ++;
				continue;
			}
			
			array[CARDNO_POSITION] = array[CARDNO_POSITION].trim();
			array[PASSWORD_POSITION] = MD5Utils.generatePassword(array[PASSWORD_POSITION]);
			
			newLine = StringUtils.join(array, SEPARATOR);
			newLine = this.generateCsvLine(array);
			writer.writeLine(newLine);
			
			newLineNum++;
		}
		
		reader.close();
		writer.close();
		
		System.out.println("totalNum = "+totalNum);
		System.out.println("emptyPwNum = "+emptyPwNum);
		System.out.println("errLineNum = "+errLineNum);
		System.out.println("newLineNum = "+newLineNum);
	}
	
	private String generateCsvLine(String[] array){
		String line = array[0].trim()+SEPARATOR+array[1].trim()+SEPARATOR+array[2].trim()+SEPARATOR+array[3].trim()+SEPARATOR+array[4].trim();
		if(array.length == ARRAY_LENGTH){
			line += (SEPARATOR + array[5].trim());
		}
		return line;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String oldFilePath = "D:/DataTest/oldData.txt";
//		String newFilePath = "D:/DataTest/newData.csv";
//		String oldFilePath = "D:/DataTest/t1.csv";
//		String newFilePath = "D:/DataTest/t2.csv";
		String oldFilePath = "D:/DataTest/New_exported.csv";
		String newFilePath = "D:/DataTest/new_New_exported.csv";
		long t1 =  System.currentTimeMillis();
		PasswordEncrypter passwordEncrypter = new PasswordEncrypter();
		passwordEncrypter.exec(oldFilePath, newFilePath);
		long t2 = System.currentTimeMillis();
		
		System.out.println("exceute ok !");
		System.out.println("used time = " + (t2-t1)/1000);
		
//		String a = "ab,,c";
//		String[] ar = a.split(",");
//		System.out.println(ar.length);
//		for(int i=0; i<ar.length;i++){
//			System.out.println(ar[i]);
//		}
//		
	}

}
