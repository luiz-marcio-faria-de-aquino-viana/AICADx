/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
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
 
/*
 * # Released under MIT License
 *
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 * 
 * Created by Luiz Marcio Faria de Aquino Viana, Post-Doctor (COPPE/UFRJ in 1998-2002 and 2020-2022).
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the “Software”), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED 
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
 * IN THE SOFTWARE.
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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.dao.record.BasePointRecord;
import br.com.tlmv.aicadxapp.dao.record.CadLayerDefRecord;
import br.com.tlmv.aicadxapp.vo.FileDataVO;

public class FileUtil 
{
//Public
	
	public static String generateBackupFileName(String srcFileName)
	{
		String uuid = UuidUtil.generateUUID();
		
		String dstFileName = srcFileName + "-" + uuid + "." + AppDefs.EXT_BAK;
		return dstFileName;
	}
	
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
		String strResult = StringUtil.safeFileName(databaseName);
		return strResult;
	}
	
	public static String checkProjectName(String projectName)
	{
		String strResult = StringUtil.safeFileName(projectName);
		return strResult;
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

	public static void copyFile(File srcFile, File dstFile)
	{
		FileInputStream sin = null;
		FileOutputStream sout = null;
		
		try 
		{
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
	
	public static ArrayList<String> readDataAsList(File f, String commentMark)
	{
		ArrayList<String> ls = new ArrayList<String>();
		
		BufferedReader fin = null;		
		try {
			fin = new BufferedReader(new FileReader(f));
			String sbuf = null;
			while((sbuf = fin.readLine()) != null) {
				if( (commentMark != null) && sbuf.startsWith(commentMark) ) continue;

				ls.add(sbuf);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(fin != null) fin.close();				
			}
			catch(Exception e1) { }
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
	
	//READ/WRITE DATA_RECORD
	//	
	public static void createDataBackup(String srcFileName)
	{
		String uuid = UuidUtil.generateUUID();
		
		File f_src = new File( srcFileName );
		if( f_src.exists() ) {
			String dstFileName = FileUtil.generateBackupFileName(srcFileName);

			File f_dst = new File( dstFileName );
			f_src.renameTo(f_dst);
		}		
	}
	
	//DATA_RECORD
	//
	public static boolean writeDataRecord(String fileName, ArrayList<BaseObjectRecord> lsDataRecord)
	{
		boolean rscode = false;
		
		FileOutputStream fos = null;		
		ObjectOutputStream os = null;		
		try {
			FileUtil.createDataBackup(fileName);

			fos = new FileOutputStream(fileName);
			os = new ObjectOutputStream(fos);

			for(BaseObjectRecord oDataRecord : lsDataRecord) {
				os.writeObject(oDataRecord);
			}
			rscode = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(os != null) os.close();				
				if(fos != null) fos.close();				
			}
			catch(Exception e1) { }
		}		
		return rscode;
	}
	
	public static ArrayList<BaseObjectRecord> loadDataRecord(String fileName)
	{
		ArrayList<BaseObjectRecord> lsDataRecord = new ArrayList<BaseObjectRecord>();
		
		FileInputStream fis = null;		
		ObjectInputStream is = null;		
		try {
			File f = new File(fileName);
			if( f.exists() ) {
				fis = new FileInputStream(f);
				is = new ObjectInputStream(fis);
	
				BaseObjectRecord oDataRecord = null;
				boolean bEof = false;
				while( !bEof ) {
					try {
						oDataRecord = (BaseObjectRecord)is.readObject();
						lsDataRecord.add(oDataRecord);
					}
					catch(Exception e) {
						oDataRecord = null;
						bEof = true;
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(is != null) is.close();				
				if(fis != null) fis.close();				
			}
			catch(Exception e1) { }
		}		
		return lsDataRecord;
	}
	
	//LAYERDEF_RECORD
	//
	public static boolean writeLayerDefRecord(String fileName, ArrayList<CadLayerDefRecord> lsDataRecord)
	{
		boolean rscode = false;
		
		FileOutputStream fos = null;		
		ObjectOutputStream os = null;		
		try {
			FileUtil.createDataBackup(fileName);

			fos = new FileOutputStream(fileName);
			os = new ObjectOutputStream(fos);

			for(BaseObjectRecord oDataRecord : lsDataRecord) {
				os.writeObject(oDataRecord);
			}
			rscode = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(os != null) os.close();				
				if(fos != null) fos.close();				
			}
			catch(Exception e1) { }
		}		
		return rscode;
	}
	
	public static ArrayList<CadLayerDefRecord> loadLayerDefRecord(String fileName)
	{
		ArrayList<CadLayerDefRecord> lsDataRecord = new ArrayList<CadLayerDefRecord>();
		
		FileInputStream fis = null;		
		ObjectInputStream is = null;		
		try {
			File f = new File(fileName);
			if( f.exists() ) {
				fis = new FileInputStream(f);
				is = new ObjectInputStream(fis);
	
				CadLayerDefRecord oDataRecord = null;
				boolean bEof = false;
				while( !bEof ) {
					try {
						oDataRecord = (CadLayerDefRecord)is.readObject();
						lsDataRecord.add(oDataRecord);
					}
					catch(Exception e) {
						oDataRecord = null;
						bEof = true;
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(is != null) is.close();				
				if(fis != null) fis.close();				
			}
			catch(Exception e1) { }
		}		
		return lsDataRecord;
	}
	
	//POINT_RECORD
	//
	public static boolean writePointRecord(String fileName, ArrayList<BasePointRecord> lsDataRecord)
	{
		boolean rscode = false;
		
		FileOutputStream fos = null;		
		ObjectOutputStream os = null;		
		try {
			FileUtil.createDataBackup(fileName);

			fos = new FileOutputStream(fileName);
			os = new ObjectOutputStream(fos);

			for(BasePointRecord oDataRecord : lsDataRecord) {
				os.writeObject(oDataRecord);
			}
			rscode = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(os != null) os.close();				
				if(fos != null) fos.close();				
			}
			catch(Exception e1) { }
		}		
		return rscode;
	}
	
	public static ArrayList<BasePointRecord> loadPointRecord(String fileName)
	{
		ArrayList<BasePointRecord> lsDataRecord = new ArrayList<BasePointRecord>();
		
		FileInputStream fis = null;		
		ObjectInputStream is = null;		
		try {
			File f = new File(fileName);
			if( f.exists() ) {
				fis = new FileInputStream(f);
				is = new ObjectInputStream(fis);
	
				BasePointRecord oDataRecord = null;
				boolean bEof = false;
				while( !bEof ) {
					try {
						oDataRecord = (BasePointRecord)is.readObject();
						lsDataRecord.add(oDataRecord);
					}
					catch(Exception e) {
						oDataRecord = null;
						bEof = true;
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(is != null) is.close();				
				if(fis != null) fis.close();				
			}
			catch(Exception e1) { }
		}		
		return lsDataRecord;
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
	public static String readData(String fileName, String commentMark)
	{
		StringBuilder sb = new StringBuilder();
		
		BufferedReader fin = null;		
		try
		{
			fin = new BufferedReader(new FileReader(fileName));
			String sbuf = null;
			while((sbuf = fin.readLine()) != null) {
				if( (commentMark != null) && sbuf.startsWith(commentMark) ) continue;
				
				sb.append(sbuf);
			}
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
	
	public static String readData(File f, String commentMark)
	{
		StringBuilder sb = new StringBuilder();
		
		BufferedReader fin = null;		
		try
		{
			fin = new BufferedReader(new FileReader(f));
			String sbuf = null;
			while((sbuf = fin.readLine()) != null) {
				if( (commentMark != null) && sbuf.startsWith(commentMark) ) continue;
			
				sb.append(sbuf);
			}
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
	
	public static void mkdir(String strDir)
	{
		File dir = new File(strDir);
		if( !dir.exists() ) {
			dir.mkdir();
		}
	}

}
