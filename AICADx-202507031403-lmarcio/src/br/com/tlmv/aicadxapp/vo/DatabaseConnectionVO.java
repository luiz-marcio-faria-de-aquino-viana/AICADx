/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * DatabaseConnectionVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 05/06/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.vo;

import org.w3c.dom.Node;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.utils.XmlUtil;

public class DatabaseConnectionVO 
{
//Private
	private String databaseType;
	private String dsName;
	private String driver;
	private String url;
	private String user;
	private String pwd;
	
//Public

	public DatabaseConnectionVO() {
		this.databaseType = AppDefs.NULL_STR;
		this.dsName = AppDefs.NULL_STR;
		this.driver = AppDefs.NULL_STR;
		this.url = AppDefs.NULL_STR;
		this.user = AppDefs.NULL_STR;
		this.pwd = AppDefs.NULL_STR;
	}
	
	public DatabaseConnectionVO(
		String databaseType,
		String dsName,
		String driver,
		String url,
		String user,
		String pwd)
	{
		this.databaseType = databaseType;
		this.dsName = dsName;
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.pwd = pwd;
	}
	
	/* Methodes */
	
	public void fromXml(Node nDatabaseConnection)
	{
		try {
		    this.databaseType = XmlUtil.getChildNodeValueByName(nDatabaseConnection, AppDefs.CFG_TAG_CONFIGURATION_DATABASECONNECTION_DATABASETYPE, false, AppDefs.NULL_STR);
		    this.dsName = XmlUtil.getChildNodeValueByName(nDatabaseConnection, AppDefs.CFG_TAG_CONFIGURATION_DATABASECONNECTION_DSNAME, false, AppDefs.NULL_STR);
		    this.driver = XmlUtil.getChildNodeValueByName(nDatabaseConnection, AppDefs.CFG_TAG_CONFIGURATION_DATABASECONNECTION_DRIVER, false, AppDefs.NULL_STR);
		    this.url = XmlUtil.getChildNodeValueByName(nDatabaseConnection, AppDefs.CFG_TAG_CONFIGURATION_DATABASECONNECTION_URL, false, AppDefs.NULL_STR);
		    this.user = XmlUtil.getChildNodeValueByName(nDatabaseConnection, AppDefs.CFG_TAG_CONFIGURATION_DATABASECONNECTION_USER, false, AppDefs.NULL_STR);
		    this.pwd = XmlUtil.getChildNodeValueByName(nDatabaseConnection, AppDefs.CFG_TAG_CONFIGURATION_DATABASECONNECTION_PWD, false, AppDefs.NULL_STR);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String toXml()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("<DatabaseConnection>");
		
		String strDatabaseType = String.format("<DatabaseType>%s</DatabaseType>", this.databaseType);
		sb.append(strDatabaseType);
		
		String strDsName = String.format("<DsName>%s</DsName>", this.dsName);
		sb.append(strDsName);
		
		String strDriver = String.format("<Driver>%s</Driver>", this.driver);
		sb.append(strDriver);
		
		String strUrl = String.format("<URL>%s</URL>", this.url);
		sb.append(strUrl);
		
		String strUser = String.format("<User>%s</User>", this.user);
		sb.append(strUser);
		
		String strPwd = String.format("<Pwd>%s</Pwd>", this.pwd);
		sb.append(strPwd);
		
		sb.append("</DatabaseConnection>");
		
		return sb.toString();
	}
	
	public String toString()
	{
		return this.dsName;
	}
	
	/* Getters/Setters */

	public String getDatabaseType() {
		return databaseType;
	}

	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	public String getDsName() {
		return dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
}
