/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * ProjectRepo.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 02/12/2025
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

package br.com.tlmv.aicadxapp.vo;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.tlmv.aicadxapp.AppCtx;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;

public class ProjectRepoVO 
{
//Private	
	private String name;
	private String projectDir;
	//
	private String safeName;				// safe name used for database
	//
	private String dbaseName;				// database schema name (= aix_[safeName])
	private String sqliteFileName;			// sqlite file name (= aix_[safeName].aix)
	private String nosqlFileNameMask;		// nosql file name (= aix_[safeName]_[objecttype].dat)
	//
	private String blkDir;
	private String dataDir;
	private String imageDir;
	private String pointDir;
	private String outputDir;
	//
	private String sqliteFullFileName;		// sqlite full file name (= [dataDir]/aix_[safeName]_[objecttype].dat)
	private String nosqlFullFileNameMask;	// nosql full file name (= [dataDir]aix_[safeName]_[objecttype].dat)

//Public
	
	public ProjectRepoVO(String name)
	{
		AppMain app = AppMain.getApp();

		AppCtx ctx = app.getCtx();
		
		this.name = name;
		//
		this.safeName = StringUtil.safeFileName(this.name);
		//
		this.dbaseName = AppDefs.DEF_PROJECTPREFIX_DEFAULT + this.safeName;
		this.sqliteFileName = AppDefs.DEF_SCHEMAPREFIX_DEFAULT + this.safeName + AppDefs.DEF_PROJECTFILEEXT_DEFAULT;
		this.nosqlFileNameMask = AppDefs.DEF_SCHEMAPREFIX_DEFAULT + this.safeName + "_%s" + AppDefs.DEF_PROJECTFILEEXT_DEFAULT;
		//
		this.projectDir = ctx.getRepositoryDir() + this.safeName;
		this.blkDir = this.projectDir + "/" + AppDefs.PRJ_BLKDIR;
		this.dataDir = this.projectDir + "/" + AppDefs.PRJ_DATADIR;
		this.imageDir = this.projectDir + "/" + AppDefs.PRJ_IMAGEDIR;
		this.pointDir = this.projectDir + "/" + AppDefs.PRJ_POINTDIR;
		this.outputDir = this.projectDir + "/" + AppDefs.PRJ_OUTPUTDIR;		
		//
		this.sqliteFullFileName = this.dataDir + this.sqliteFileName;
		this.nosqlFullFileNameMask = this.dataDir + this.nosqlFileNameMask;
	}
	
	/* Methodes */
	
	public boolean existProjectDir() 
	{
		String strProjectDir = this.projectDir;
		File f = new File( strProjectDir );
		if( !f.exists() )
			return false;
		return true;
	}
	
	public void createProjectDir() 
	{
		// PROJECT_DIR
		//
		FileUtil.mkdir(this.projectDir);
		//
		FileUtil.mkdir(this.blkDir);
		FileUtil.mkdir(this.dataDir);
		FileUtil.mkdir(this.imageDir);
		FileUtil.mkdir(this.pointDir);
		FileUtil.mkdir(this.outputDir);
	}
	
	/* Getters/Setters */

	public String getProjectDir() {
		return projectDir;
	}

	public void setProjectDir(String projectDir) {
		this.projectDir = projectDir;
	}

	public String getBlkDir() {
		return blkDir;
	}

	public void setBlkDir(String blkDir) {
		this.blkDir = blkDir;
	}

	public String getDataDir() {
		return dataDir;
	}

	public void setDataDir(String dataDir) {
		this.dataDir = dataDir;
	}

	public String getImageDir() {
		return imageDir;
	}

	public void setImageDir(String imageDir) {
		this.imageDir = imageDir;
	}

	public String getPointDir() {
		return pointDir;
	}

	public void setPointDir(String pointDir) {
		this.pointDir = pointDir;
	}

	public String getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSqliteBkpFullFileName() {
		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATETIME_TYPE4_MASC);
		
		Date dataHoraAtual = new Date();
		
		String strBkpFileName = this.sqliteFullFileName + "." + df.format(dataHoraAtual) + "." + AppDefs.EXT_BAK;
		return strBkpFileName;
	}
	
	public String getNosqlBkpFullFileNameMask() {
		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATETIME_TYPE4_MASC);
		
		Date dataHoraAtual = new Date();
		
		String strBkpFileName = this.nosqlFullFileNameMask + "." + df.format(dataHoraAtual) + "." + AppDefs.EXT_BAK;
		return strBkpFileName;
	}

	public String getSafeName() {
		return safeName;
	}

	public void setSafeName(String safeName) {
		this.safeName = safeName;
	}

	public String getDbaseName() {
		return dbaseName;
	}

	public void setDbaseName(String dbaseName) {
		this.dbaseName = dbaseName;
	}

	public String getSqliteFileName() {
		return sqliteFileName;
	}

	public void setSqliteFileName(String sqliteFileName) {
		this.sqliteFileName = sqliteFileName;
	}

	public String getNosqlFileNameMask() {
		return nosqlFileNameMask;
	}

	public void setNosqlFileNameMask(String nosqlFileNameMask) {
		this.nosqlFileNameMask = nosqlFileNameMask;
	}

	public String getSqliteFullFileName() {
		return sqliteFullFileName;
	}

	public void setSqliteFullFileName(String sqliteFullFileName) {
		this.sqliteFullFileName = sqliteFullFileName;
	}

	public String getNosqlFullFileNameMask() {
		return nosqlFullFileNameMask;
	}

	public void setNosqlFullFileNameMask(String nosqlFullFileNameMask) {
		this.nosqlFullFileNameMask = nosqlFullFileNameMask;
	}

}
