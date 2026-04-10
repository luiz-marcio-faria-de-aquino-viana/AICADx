/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadImageDef.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 29/05/2025
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

package br.com.tlmv.aicadxapp.cad;

import java.awt.Image;
import java.io.File;
import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseObjDefDao;
import br.com.tlmv.aicadxapp.dao.record.CadImageDefRecord;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.ImageUtil;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;

public class CadImageDef extends CadObject
{
//Private
	private int tipo = AppDefs.OPT_IMAGEDEF_NONE;
	private String name = null;
	private String fullFileName = null;
	private String fileName = null;
	private double width = 0.0;
	private double height = 0.0;

	private Image imageObj = null;
	
	/* Methodes */
	
	private void loadImage(String fullFileName) {
		if(fullFileName == null) return;

		try {
			this.fullFileName = fullFileName;
			this.fileName = FileUtil.getFileNameEx(fullFileName);
			this.imageObj = ImageUtil.readImageFromFile(fullFileName);

			this.width = imageObj.getWidth(null);
			this.height = imageObj.getHeight(null);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
//Public

	public CadImageDef(CadDocumentDef doc) {
		super(AppDefs.OBJTYPE_IMAGE_DEF, doc, null);
	}

	/* Methodes */

	public void init() 
	{
		this.tipo = AppDefs.NULL_INT;
		this.name = AppDefs.NULL_STR;
		this.fullFileName = null;
		this.fileName = null;
		this.imageObj = null;
		this.width = 0;
		this.height = 0;
	}
	
	public void init(
		int tipo,
		String name, 
		String fullFileName) 
	{
		this.init();

		this.loadImage(fullFileName);
	}

	public void init(
		int tipo,
		String name, 
		String fullFileName,
		double width,
		double height) 
	{
		this.init();

		this.loadImage(fullFileName);
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void init(ICadObject o) {
		//TODO:
	}

	@Override
	public void reset() {
		/* nothing todo! */
	}
	
	/* CREATE */
	
	public static CadImageDef create(CadDocumentDef doc, int tipo, String name, String fullFileName) {
		CadImageDef o = new CadImageDef(doc); 
		o.init(tipo, name, fullFileName);
		return o;
	}
	
	public static CadImageDef create(CadDocumentDef doc, int tipo, String name, String fullFileName, double width, double height) {
		CadImageDef o = new CadImageDef(doc); 
		o.init(tipo, name, fullFileName, width, height);
		return o;
	}
	
	/* DEBUG */
	
	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
			"Tipo:%s;Name:%s;FileName:%s;Width:%s;Height:%s; ", 
			this.tipo,
			this.name,
			this.fullFileName,
			nf6.format( this.width ),
			nf6.format( this.height ) );
		return str;
	}
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}
	
	/* LOAD/SAVE */

	public boolean save_data(BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		boolean bResult = false;
		
		BaseObjDefDao entDao = dao.createObjDefDao(this.getObjType()); 
		CadImageDefRecord entRec = new CadImageDefRecord(this); 
		int rscode = entDao.insertOrUpdate(schemaName, (CadImageDefRecord) entRec);		
		if(rscode >= 0)
			bResult = true;
		return bResult;
	}
	
	public boolean save_file(BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		ProjectRepoVO projectRepo = dao.getProjectRepo();
		if(projectRepo == null) return false;

		if(this.fullFileName == null) return false;
		
		String imageDir = projectRepo.getImageDir();
		
		String destFileName = imageDir + this.fileName;
		
		if( !destFileName.equals(this.fullFileName) ) {
			File srcFile = new File( this.fullFileName );  
			File dstFile = new File( destFileName );

			if( !srcFile.exists() ) return false;

			if( dstFile.exists() ) {
				String bkpFileName = FileUtil.generateBackupFileName(destFileName);
				File bkpFile = new File(bkpFileName);
				
				dstFile.renameTo(bkpFile);
			}
			
			FileUtil.copyFile(srcFile, dstFile);

			this.fullFileName = destFileName;
		}
		return true;
	}

	@Override
	public boolean load(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		return true;
	}

	@Override
	public boolean save(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		boolean bResult = false;
		
		this.setObjVer(objVer);

		bResult = this.save_file(dao, schemaName, doc);
		if( !bResult ) return false;

		bResult = this.save_data(dao, schemaName, doc);
		return bResult;
	}

	/* Getters/Setters */
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFullFileName() {
		return fullFileName;
	}

	public void setFullFileName(String fullFileName) {
		this.fullFileName = fullFileName;
	}

	public Image getImageObj() {
		return imageObj;
	}

	public void setImageObj(Image imageObj) {
		this.imageObj = imageObj;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

}
