/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * AppCtx.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 23/01/2025
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
	private String outputDir;
	private String repositoryDir;
	private String shapesDir;
	private String tempDir;
	private String templatesDir;
	private String videosDir;
	//
	private String confFile;
	private String confFileDefault;
	//
	private String layersFile;
	private String layersFileDefault;
	//
	private String shapeFiles;
	private String shapeFilesDefault;
	//
	private String templateFileMemoriaCalculo;
	private String templateFileListagemParedes;
	//
	//private String templateDbFileModBase;
	//private String templateDbFileModEle;
	private String templateDbFileModBasePgSql;
	private String templateDbFileModBaseSqLite;
	//
	private String templateDxfFile;
		
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
		this.outputDir = this.homeDir + AppDefs.CTX_OUTPUTDIR;
		this.repositoryDir = this.homeDir + AppDefs.CTX_REPOSITORYDIR;
		this.shapesDir = this.homeDir + AppDefs.CTX_SHAPESDIR;
		this.tempDir = this.homeDir + AppDefs.CTX_TEMPDIR;
		this.templatesDir = this.homeDir + AppDefs.CTX_TEMPLATESDIR;
		this.videosDir = this.homeDir + AppDefs.CTX_VIDEOSDIR;
		//
		this.confFile = this.confDir + AppDefs.CTX_CONFFILE;
		this.confFileDefault = this.confDir + AppDefs.CTX_CONFFILE_DEFAULT;
		//
		this.layersFile = this.confDir + AppDefs.CTX_LAYERSFILE;
		this.layersFileDefault = this.confDir + AppDefs.CTX_LAYERSFILE_DEFAULT;
		//
		this.shapeFiles = this.confDir + AppDefs.CTX_SHAPEFILE;
		this.shapeFilesDefault = this.confDir + AppDefs.CTX_SHAPEFILE_DEFAULT;
		//
		this.templateFileMemoriaCalculo = this.templatesDir + AppDefs.TEMPL_MEMORIA_CALCULO;
		this.templateFileListagemParedes = this.templatesDir + AppDefs.TEMPL_LISTAGEM_PAREDES;
		//
		//this.templateDbFileModBase = this.templatesDir + AppDefs.TEMPL_TEMPLATEDB_FILE_MODBASE_V1_1;
		//this.templateDbFileModEle = this.templatesDir + AppDefs.TEMPL_TEMPLATEDB_FILE_MODELE_V1_1;
		this.templateDbFileModBasePgSql = this.templatesDir + AppDefs.TEMPL_TEMPLATEDB_FILE_MODBASE_PGSQL_V1_3;
		this.templateDbFileModBaseSqLite = this.templatesDir + AppDefs.TEMPL_TEMPLATEDB_FILE_MODBASE_SQLITE_V1_3;
		//
		this.templateDxfFile = this.templatesDir + AppDefs.TEMPL_TEMPLATEDXF_FILE_V1_1;
		
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

	public String getTemplateFileMemoriaCalculo() {
		return templateFileMemoriaCalculo;
	}

	public void setTemplateFileMemoriaCalculo(String templateFileMemoriaCalculo) {
		this.templateFileMemoriaCalculo = templateFileMemoriaCalculo;
	}

	public String getTemplateFileListagemParedes() {
		return templateFileListagemParedes;
	}

	public void setTemplateFileListagemParedes(String templateFileListagemParedes) {
		this.templateFileListagemParedes = templateFileListagemParedes;
	}

	public String getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public String getShapesDir() {
		return shapesDir;
	}

	public void setShapesDir(String shapesDir) {
		this.shapesDir = shapesDir;
	}

	public String getShapeFiles() {
		return shapeFiles;
	}

	public void setShapeFiles(String shapeFiles) {
		this.shapeFiles = shapeFiles;
	}

	public String getShapeFilesDefault() {
		return shapeFilesDefault;
	}

	public void setShapeFilesDefault(String shapeFilesDefault) {
		this.shapeFilesDefault = shapeFilesDefault;
	}

	public String getTemplateDbFileModBasePgSql() {
		return templateDbFileModBasePgSql;
	}

	public void setTemplateDbFileModBasePgSql(String templateDbFileModBasePgSql) {
		this.templateDbFileModBasePgSql = templateDbFileModBasePgSql;
	}

	public String getTemplateDbFileModBaseSqLite() {
		return templateDbFileModBaseSqLite;
	}

	public void setTemplateDbFileModBaseSqLite(String templateDbFileModBaseSqLite) {
		this.templateDbFileModBaseSqLite = templateDbFileModBaseSqLite;
	}

	public String getTemplateDxfFile() {
		return templateDxfFile;
	}

	public void setTemplateDxfFile(String templateDxfFile) {
		this.templateDxfFile = templateDxfFile;
	}

	public String getVideosDir() {
		return videosDir;
	}

	public void setVideosDir(String videosDir) {
		this.videosDir = videosDir;
	}
	
}
