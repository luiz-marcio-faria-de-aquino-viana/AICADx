/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * FileUtil.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.utils;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.vo.FileDataVO;

public class FileUtil 
{
//Public
	
	public static String generateSchemaName()
	{
		String schemaName = String.format(AppDefs.DEF_SCHEMANAME_DEFAULT, UuidUtil.generateUUID());
		return schemaName;
	}
	
	public static String generateSchemaNameWithPrefix()
	{
		String schemaName = String.format(AppDefs.DEF_SCHEMAPREFIX_DEFAULT + AppDefs.DEF_SCHEMANAME_DEFAULT, UuidUtil.generateUUID());
		return schemaName;
	}
	
	public static String generateSchemaFileName()
	{
		String schemaName = String.format(AppDefs.DEF_SCHEMAFILE_DEFAULT, UuidUtil.generateUUID());
		return schemaName;
	}
	
	public static String generateFileName(String pathDir)
	{
		String fileName = pathDir + FileUtil.generateSchemaFileName();
		return fileName;
	}
	
	public static String generateSchemaName(String uuid)
	{
		String schemaName = String.format(AppDefs.DEF_SCHEMANAME_DEFAULT, uuid);
		return schemaName;
	}
	
	public static String generateSchemaNameWithPrefix(String uuid)
	{
		String schemaName = String.format(AppDefs.DEF_SCHEMAPREFIX_DEFAULT + AppDefs.DEF_SCHEMANAME_DEFAULT, UuidUtil.generateUUID());
		return schemaName;
	}
	
	public static String generateSchemaFileName(String uuid)
	{
		String schemaName = String.format(AppDefs.DEF_SCHEMAFILE_DEFAULT, uuid);
		return schemaName;
	}
	
	public static String generateFileName(String pathDir, String uuid)
	{
		String fileName = pathDir + FileUtil.generateSchemaFileName(uuid);
		return fileName;
	}
	
	public static String checkDatabaseName(String databaseName)
	{
		StringBuilder sb = new StringBuilder();
		
		int sz = databaseName.length();
		for(int i = 0; i < sz; i++) {
			char c = databaseName.charAt(i);
			
			if( ( (c >= 'A') && (c <= 'Z') ) || 
				( (c >= 'a') && (c <= 'z') ) || 
				( (c >= '0') && (c <= '9') ) ||
				(c == '_') || 
				(c == '-') ) {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	public static boolean launchExternalApplication(String fileName)
	{
		boolean result = false;
		
		try {
			File f = new File(fileName);
			
			Desktop oDesktop = Desktop.getDesktop();
			oDesktop.open(f);			
			
			result = true;
		}
		catch(Exception e) { }	
		
		return result;
	}
	
	public static void listFileDir(List<FileDataVO> lsFileData, String curFullFilePath, String[] extFilter, boolean filesOnly)
	{
		File fCurFullFilePath = new File(curFullFilePath);
		
		String[] arrFilePath = fCurFullFilePath.list();
		for(String newFilePath : arrFilePath)
		{
			String newFullFilePath = String.format("%s/%s", curFullFilePath, newFilePath);
			
			File fNewFullFilePath = new File(newFullFilePath);			
			if( fNewFullFilePath.isDirectory() )
			{
				String newFileName = FileUtil.getFileName(newFullFilePath);
				String newFileExt = FileUtil.getFileExtension(newFullFilePath);
				Date newFileDataModificacao = new Date(fNewFullFilePath.lastModified());
				
				if( !filesOnly )
				{					
					FileDataVO fileData = new FileDataVO(
							newFullFilePath,
							newFileName,
							newFileExt,
							newFileDataModificacao,
							false);
					lsFileData.add(fileData);
				}				
				listFileDir(lsFileData, newFullFilePath, extFilter, filesOnly);
			}
			else
			{
				String newFileName = FileUtil.getFileName(newFullFilePath);
				String newFileExt = FileUtil.getFileExtension(newFullFilePath);
				Date newFileDataModificacao = new Date(fNewFullFilePath.lastModified());
				
				if(ListUtil.findPosInList(extFilter, newFileExt) != -1)
				{
					FileDataVO fileData = new FileDataVO(
						newFullFilePath,
						newFileName,
						newFileExt,
						newFileDataModificacao,
						true);
					lsFileData.add(fileData);
				}
			}
		}
	}
	
	public static String getFileName(String fullFileName)
	{
		File fFullFileName = new File(fullFileName);

		String fileName = fFullFileName.getName();
		return fileName;
	}
	
	public static String getFileNameEx(String fullFileName)
	{
		StringBuilder sb = new StringBuilder();
		
		int sz = fullFileName.length();
		for(int i = sz; i > 0; i--) {
			int pos = i - 1;
			
			char ch = fullFileName.charAt(pos);
			if((ch == '\\') || (ch == '/')) break;

			sb.insert(0,  ch);
		}
		return sb.toString();
	}
	
	public static String getFileNameWithoutExtension(String fullFileName)
	{
		String strResult = "";

		String fileName = FileUtil.getFileName(fullFileName);

		String[] arr = StringUtil.split(fileName, '.');
		int szArr = arr.length - 1;
		for(int i = 0; i < szArr; i++) {
			if(strResult.length() == 0) {
				strResult = arr[i];
			}
			else {
				strResult += arr[i];
			}
		}
		return strResult;
	}
	
	public static String getFileExtension(String fullFileName)
	{
		File fFullFileName = new File(fullFileName);

		String fileName = fFullFileName.getName();
		
		String[] arrFileExt = StringUtil.split(fileName, '.');
		String fileExt = "";
		if(arrFileExt.length > 0)
			fileExt = arrFileExt[arrFileExt.length - 1];
		return fileExt;
	}
	
	public static boolean isExistFile(String fileName)
	{
		File f = new File(fileName);
		return f.exists();
	}

	public static void renameFile(String srcFileName, String dstFileName)
	{
		try 
		{
			File srcFile = new File(srcFileName);
			File dstFile = new File(srcFileName);
			
			if( srcFile.exists() )
			{
				if( srcFile.isFile() )
					srcFile.renameTo(dstFile);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void copyFile(String srcFileName, String dstFileName)
	{
		FileInputStream sin = null;
		FileOutputStream sout = null;
		
		try 
		{
			File srcFile = new File(srcFileName);
			File dstFile = new File(dstFileName);
			
			if( srcFile.exists() )
			{
				if( srcFile.isFile() )
				{
					sin = new FileInputStream(srcFile);
			        sout = new FileOutputStream(dstFile);

			        byte[] buf = new byte[4096];
			        int numread = -1;
			        while((numread = sin.read(buf, 0, 4096)) != -1) 
			        	sout.write(buf, 0, numread);
			        sout.flush();
			        
					sout.close();					
					sin.close();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
		
	//READ/WRITE DATA AS LIST OF STRING
	//
	public static ArrayList<String> readDataAsList(String fileName)
	{
		ArrayList<String> ls = new ArrayList<String>();
		
		BufferedReader fin = null;		
		try
		{
			fin = new BufferedReader(new FileReader(fileName));
			String sbuf = null;
			while((sbuf = fin.readLine()) != null)
				ls.add(sbuf);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				if(fin != null) fin.close();				
			}
			catch(Exception e1)
			{
				
			}
		}
		
		return ls;
	}
	
	public static ArrayList<String> readDataAsList(File f)
	{
		ArrayList<String> ls = new ArrayList<String>();
		
		BufferedReader fin = null;		
		try
		{
			fin = new BufferedReader(new FileReader(f));
			String sbuf = null;
			while((sbuf = fin.readLine()) != null)
				ls.add(sbuf);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				if(fin != null) fin.close();				
			}
			catch(Exception e1)
			{
				
			}
		}
		
		return ls;
	}
	
	public static boolean writeDataAsList(String fileName, ArrayList<String> lsData)
	{
		boolean rscode = false;
		
		BufferedWriter fout = null;		
		try
		{
			fout = new BufferedWriter(new FileWriter(fileName));
			for(String strData : lsData) {
				fout.write(strData + "\n");
			}

			rscode = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				if(fout != null) fout.close();				
			}
			catch(Exception e1)
			{
				
			}
		}
		
		return rscode;
	}
	
	public static boolean writeDataAsList(File f, ArrayList<String> lsData)
	{
		boolean rscode = false;
		
		BufferedWriter fout = null;		
		try
		{
			fout = new BufferedWriter(new FileWriter(f));
			for(String strData : lsData) {
				fout.write(strData + "\n");
			}
			rscode = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				if(fout != null) fout.close();				
			}
			catch(Exception e1)
			{
				
			}
		}
		
		return rscode;
	}
	
	//READ/WRITE TEMPFILE
	//
	public static boolean saveTempFile(String fileName, byte[] data)
		throws Exception
	{
		try {
			FileOutputStream f = new FileOutputStream(fileName);
			f.write(data);
			f.close();
			return true;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static String readTempFile(String fileName)
		throws Exception
	{
		StringBuffer sbuf = new StringBuffer();
		
	    try {
	    	BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
	    	String buf = null;
		    while((buf = in.readLine()) != null)
		    	sbuf.append(buf);
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
	    }
	    
	    return sbuf.toString();
	}
	
	//READ/WRITE DATA AS STRING
	//
	public static String readData(String fileName)
	{
		StringBuilder sb = new StringBuilder();
		
		BufferedReader fin = null;		
		try
		{
			fin = new BufferedReader(new FileReader(fileName));
			String sbuf = null;
			while((sbuf = fin.readLine()) != null)
				sb.append(sbuf);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				if(fin != null) fin.close();				
			}
			catch(Exception e1)
			{
				
			}
		}
		
		return sb.toString();
	}
	
	public static String readData(File f)
	{
		StringBuilder sb = new StringBuilder();
		
		BufferedReader fin = null;		
		try
		{
			fin = new BufferedReader(new FileReader(f));
			String sbuf = null;
			while((sbuf = fin.readLine()) != null)
				sb.append(sbuf);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				if(fin != null) fin.close();				
			}
			catch(Exception e1)
			{
				
			}
		}
		
		return sb.toString();
	}
	
	public static boolean writeData(String fileName, String strData)
	{
		boolean rscode = false;
		
		BufferedWriter fout = null;		
		try
		{
			fout = new BufferedWriter(new FileWriter(fileName));
			fout.write(strData);

			rscode = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				if(fout != null) fout.close();				
			}
			catch(Exception e1)
			{
				
			}
		}
		
		return rscode;
	}
	
	public static boolean writeData(File f, String strData)
	{
		boolean rscode = false;
		
		BufferedWriter fout = null;		
		try
		{
			fout = new BufferedWriter(new FileWriter(f));
			fout.write(strData);

			rscode = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				if(fout != null) fout.close();				
			}
			catch(Exception e1)
			{
				
			}
		}
		
		return rscode;
	}

}
