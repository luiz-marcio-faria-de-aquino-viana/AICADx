/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * AppCtx.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 23/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp;

public class AppCtx 
{
//Private
	private String homeDir;
	//
	private String binDir;
	private String confDir;
	private String dataDir;
	private String docsDir;
	private String repositoryDir;
	private String tempDir;
	private String templatesDir;
	//
	private String confFile;
	private String confFileDefault;
	//
	private String layersFile;
	private String layersFileDefault;
		
//Public
	
	public AppCtx()
	{
		init();
	}
	
	public void init()
	{
		this.homeDir = AppDefs.CTX_HOMEDIR;
		
		String valHomeDir = System.getenv(AppDefs.APP_HOME); 
		if(valHomeDir != null)
			this.homeDir = valHomeDir;
		
		this.binDir = this.homeDir + AppDefs.CTX_BINDIR;
		this.confDir = this.homeDir + AppDefs.CTX_CONFDIR;
		this.dataDir = this.homeDir + AppDefs.CTX_DATADIR;
		this.docsDir = this.homeDir + AppDefs.CTX_DOCSDIR;
		this.repositoryDir = this.homeDir + AppDefs.CTX_REPOSITORYDIR;
		this.tempDir = this.homeDir + AppDefs.CTX_TEMPDIR;
		this.templatesDir = this.homeDir + AppDefs.CTX_TEMPLATESDIR;
		//
		this.confFile = this.confDir + AppDefs.CTX_CONFFILE;
		this.confFileDefault = this.confDir + AppDefs.CTX_CONFFILE_DEFAULT;
		//
		this.layersFile = this.confDir + AppDefs.CTX_LAYERSFILE;
		this.layersFileDefault = this.confDir + AppDefs.CTX_LAYERSFILE_DEFAULT;
	}
	
	/* Getters/Setters */
	
	public String getHomeDir() {
		return homeDir;
	}
	public void setHomeDir(String homeDir) {
		this.homeDir = homeDir;
	}
	public String getBinDir() {
		return binDir;
	}
	public void setBinDir(String binDir) {
		this.binDir = binDir;
	}
	public String getConfDir() {
		return confDir;
	}
	public void setConfDir(String confDir) {
		this.confDir = confDir;
	}
	public String getDataDir() {
		return dataDir;
	}
	public void setDataDir(String dataDir) {
		this.dataDir = dataDir;
	}
	public String getDocsDir() {
		return docsDir;
	}
	public void setDocsDir(String docsDir) {
		this.docsDir = docsDir;
	}
	public String getRepositoryDir() {
		return repositoryDir;
	}
	public void setRepositoryDir(String repositoryDir) {
		this.repositoryDir = repositoryDir;
	}
	public String getTempDir() {
		return tempDir;
	}
	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}
	public String getTemplatesDir() {
		return templatesDir;
	}
	public void setTemplatesDir(String templatesDir) {
		this.templatesDir = templatesDir;
	}
	public String getConfFile() {
		return confFile;
	}
	public void setConfFile(String confFile) {
		this.confFile = confFile;
	}
	public String getConfFileDefault() {
		return confFileDefault;
	}
	public void setConfFileDefault(String confFileDefault) {
		this.confFileDefault = confFileDefault;
	}

	public String getLayersFile() {
		return layersFile;
	}

	public void setLayersFile(String layersFile) {
		this.layersFile = layersFile;
	}

	public String getLayersFileDefault() {
		return layersFileDefault;
	}

	public void setLayersFileDefault(String layersFileDefault) {
		this.layersFileDefault = layersFileDefault;
	}
	
}
