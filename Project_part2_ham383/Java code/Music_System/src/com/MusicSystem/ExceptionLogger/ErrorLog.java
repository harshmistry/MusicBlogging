package com.MusicSystem.ExceptionLogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorLog {
	private static FileWriter initiateLogger(FileWriter errorFile,FileOutputStream fos,OutputStreamWriter streamWriter,Writer writer) throws IOException{
		errorFile = new FileWriter(new File("D:/Workspace/Music_System/src/errorLog.txt"),true);
		
		return errorFile;
	}
	public static void logError(String errMsg) throws IOException {
		String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
		errMsg=timeStamp+"-->"+errMsg+"\n";
		FileWriter errorFile=null;
		FileOutputStream fos=null;
		OutputStreamWriter streamWriter=null;
		Writer write=null;
		errorFile=initiateLogger(errorFile,fos,streamWriter,write);
		if(null != errorFile){
			try {
				errorFile.write(errMsg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				errorFile.write(e.getMessage());
				//e.printStackTrace();
			}finally{
				if(null != errorFile)
					errorFile.close();		
			}
		}
	}
	
}
