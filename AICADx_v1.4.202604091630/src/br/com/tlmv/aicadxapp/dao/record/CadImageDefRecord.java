/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadImageDefRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 12/06/2025
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

package br.com.tlmv.aicadxapp.dao.record;

import java.sql.ResultSet;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadImageDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;

public class CadImageDefRecord extends BaseObjectRecord
{
//Public

	/* SQL */

	@Override
	public String getSqlTableName() {
		return sqlTableName;
	}
	
	@Override
	public SqlColumnVO[] getSqlColumn() {
		return sqlColumn;
	}

//Public Static
	public static final String sqlTableName = "cad_image_def";

	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("tipo", 				AppDefs.TAG_SQLTYPE_INT),	
		new SqlColumnVO("name", 				AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("full_file_name", 		AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("file_name",			AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("width",				AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("height",				AppDefs.TAG_SQLTYPE_DBL)	
		
	};
	
//Private
	private Integer tipo;
	private String nameStr;
	private String fullFileNameStr;
	private String fileNameStr;
	private double width;
	private double height;
	
//Public
    
    public CadImageDefRecord()
    {
    	this.init(
    		AppDefs.NULL_LNG, 
    		//
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		//
    		AppDefs.NULL_INTSTR, 
    		//
    		AppDefs.DEF_VALUES_NAO,
    		//
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL );
    }

    public CadImageDefRecord(CadImageDef o)
    {
    	this.init(o);
    }
    
    public CadImageDefRecord(ResultSet rs)
    {
		DbUtil o = new DbUtil(rs);
		
    	this.init(o);
    }
    
    /* Methodes */
    
    public void init(
		long oid,
		//
		int objectId,
		int objType,
		String objTypeStr,
		String objVer,
		//
		String cadRefEntityId,
		//
	    String strIsDeleted,
	    //
		Integer tipo,
		String nameStr,
		String fullFileNameStr,
		String fileNameStr,
		double width,
		double height )
    {
    	super.initObj(
    		oid, 
    		//
    		objectId, 
    		objType, 
    		objTypeStr, 
    		objVer, 
    		//
    		cadRefEntityId,
    		//
    		AppDefs.DEF_VALUES_NAO,
    		strIsDeleted );

		this.tipo = tipo;
    	this.nameStr = nameStr;
		this.fullFileNameStr = fullFileNameStr;
    	this.fileNameStr = fileNameStr;
    	this.width = width;
    	this.height = height;
    }

    public void init(CadImageDef o)
    {
    	String strIsDeleted = ( o.isDeleted() ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO);
    	
        this.init(
    		AppDefs.NULL_LNG,
    		//
    		o.getObjectId(),
    		o.getObjType(),
    		o.getObjTypeStr(),
    		o.getObjVer(),
    		//
    		o.getCadRefEntityId(),
    		//
    		strIsDeleted,
    		//
    		o.getTipo(),	
    		o.getName(),
    		o.getFullFileName(),
    		o.getFileName(),
    		o.getWidth(),
    		o.getHeight() );
    }
    
    @Override
    public void init(DbUtil o)
    {
    	super.initObj(o);
    	
    	this.setTipo( o.getNextInt() );	
    	this.setNameStr( o.getNextStr() );
    	this.setFullFileNameStr( o.getNextStr() );
    	this.setFileNameStr( o.getNextStr() );
    	this.setWidth( o.getNextDbl() );	
    	this.setHeight( o.getNextDbl() );	
    	
    }
	
	/* TO_CADxxx */

    @Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
    	CadDocumentDef doc = oBlkDef.getDocument();
    	
    	CadImageDef o = new CadImageDef(doc);

    	o.init(
			this.getTipo(),
			this.getNameStr(),
			this.getFullFileNameStr(),
	    	this.getWidth(),	
	    	this.getHeight() );
    	o.setObjectId(this.getObjectId());
	    return o;
	}

    /* Getters/Setters */

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

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getNameStr() {
		return nameStr;
	}

	public void setNameStr(String nameStr) {
		this.nameStr = nameStr;
	}

	public String getFullFileNameStr() {
		return fullFileNameStr;
	}

	public void setFullFileNameStr(String fullFileNameStr) {
		this.fullFileNameStr = fullFileNameStr;
	}

	public String getFileNameStr() {
		return fileNameStr;
	}

	public void setFileNameStr(String fileNameStr) {
		this.fileNameStr = fileNameStr;
	}
    
}
