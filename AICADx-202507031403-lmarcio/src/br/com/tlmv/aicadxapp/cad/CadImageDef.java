/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadImageDef.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 29/05/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad;

import java.awt.Image;
import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.AppDatabase;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.ImageUtil;

public class CadImageDef extends CadObject
{
//Private
	private String name = null;
	private String fileName = null;
	private double width = 0.0;
	private double height = 0.0;
	//
	private Image imageObj = null;
	
//Public

	public CadImageDef() {
		super(AppDefs.OBJTYPE_IMAGE_DEF);
	}

	/* Methodes */
	
	public void init(String name, String fileName) {
		this.name = name;
		this.fileName = fileName;

		try {
			this.imageObj = ImageUtil.readImageFromFile(fileName);
			
			this.width = imageObj.getWidth(null);
			this.height = imageObj.getHeight(null);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reset() {
		/* nothing todo! */
	}
	
	/* CREATE */
	
	public static CadImageDef create(String name, String fileName) {
		CadImageDef o = new CadImageDef(); 
		o.init(name, fileName);
		return o;
	}
	
	/* DEBUG */
	
	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
			"Name:%s;FileName:%s;Width:%s;Height:%s; ", 
			this.name,
			this.fileName,
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
	
	@Override
	public void load(AppDatabase db, String schemaName, CadDocumentDef doc)
	{
		
	}

	@Override
	public void save(AppDatabase db, String schemaName, CadDocumentDef doc)
	{

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

}
