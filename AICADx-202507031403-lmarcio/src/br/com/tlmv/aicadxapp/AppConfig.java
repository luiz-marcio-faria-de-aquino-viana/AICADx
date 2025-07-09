/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * AppConfig.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 24/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp;

import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.XmlUtil;
import br.com.tlmv.aicadxapp.vo.DatabaseConnectionVO;

public class AppConfig 
{
//Private
	private String enviromentType;
	private String repositoryLocation;
	private DatabaseConnectionVO databaseConnection;
	
//Public
	
	public AppConfig(String configFileName)
	{
		String xmlData = FileUtil.readData(configFileName);
		loadData(xmlData);
	}	
	
	/* Methodes */

	public void loadData(String xmlData)
	{
		try {
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			InputSource is = new InputSource(new StringReader(xmlData));
		    Document doc = db.parse(is);
		    
		    Node nConfig = doc.getFirstChild();
		    
		    //LOAD: Config Data
		    
		    this.enviromentType = XmlUtil.getChildNodeValueByName(nConfig, AppDefs.CFG_TAG_CONFIGURATION_ENVIROMENT_TYPE, false, null);
		    this.repositoryLocation = XmlUtil.getChildNodeValueByName(nConfig, AppDefs.CFG_TAG_CONFIGURATION_REPOSITORY_LOCATION, false, null);
		    
		    Node nDatabaseConnection = XmlUtil.getChildNodeByName(nConfig, AppDefs.CFG_TAG_CONFIGURATION_DATABASECONNECTION);
		    
		    this.databaseConnection = new DatabaseConnectionVO();
		    this.databaseConnection.fromXml(nDatabaseConnection);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void saveData(String fileName, String xmlData)
	{
		try {
			DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATETIME_TYPE3_MASC);

			Date dataAtual = new Date();
			
			if( FileUtil.isExistFile(fileName) )
			{
				String bakFileName = String.format("%s.%s", fileName, df.format(dataAtual)); 
				FileUtil.renameFile(fileName, bakFileName);
			}
			FileUtil.writeData(fileName, xmlData);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public String toXml()
	{
		StringBuilder sb = new StringBuilder();
		
	    //SAVE: Config Data
		
		sb.append(XmlUtil.createXmlElemWithoutEncode(AppDefs.CFG_TAG_CONFIGURATION_ENVIROMENT_TYPE, this.enviromentType, 1));
		sb.append(XmlUtil.createXmlElemWithoutEncode(AppDefs.CFG_TAG_CONFIGURATION_REPOSITORY_LOCATION, this.repositoryLocation, 1));
		
		String strDatabaseConnection = this.databaseConnection.toXml();
		sb.append(strDatabaseConnection);
		
		String result = XmlUtil.createXmlDocElem(AppDefs.CFG_TAG_CONFIGURATION, sb.toString());
		return result;
	}
		
	/* Getters/Setters */

	public String getRepositoryLocation() {
		return this.repositoryLocation;
	}
	
	public void setRepositoryLocation(String repositoryLocation) {
		this.repositoryLocation = repositoryLocation;
	}

	public String getEnviromentType() {
		return enviromentType;
	}

	public void setEnviromentType(String enviromentType) {
		this.enviromentType = enviromentType;
	}

	public DatabaseConnectionVO getDatabaseConnection() {
		return databaseConnection;
	}

	public void setDatabaseConnection(DatabaseConnectionVO databaseConnection) {
		this.databaseConnection = databaseConnection;
	}
	
}
