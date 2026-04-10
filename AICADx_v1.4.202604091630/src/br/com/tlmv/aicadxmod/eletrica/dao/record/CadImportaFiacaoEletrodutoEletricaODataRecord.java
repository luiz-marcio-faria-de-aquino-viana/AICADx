/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadImportaFiacaoEletrodutoEletricaODataRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 08/02/2026
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

package br.com.tlmv.aicadxmod.eletrica.dao.record;

import java.sql.ResultSet;
import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;
import br.com.tlmv.aicadxmod.eletrica.cad.CadImportaFiacaoEletrodutoEletricaOData;

public class CadImportaFiacaoEletrodutoEletricaODataRecord extends BaseObjectRecord 
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
	public static final String sqlTableName = "cad_importa_fiacao_eletroduto_eletrica_odata";
	
	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("row_id", 							AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("hnd", 								AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("qdr", 								AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("cir", 								AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("lbl", 								AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("fia",			 					AppDefs.TAG_SQLTYPE_DBL)
			
	};
			
//Private
    private int rowId = AppDefs.NULL_INT;
    private String hnd = AppDefs.NULL_STR;
    private String qdr = AppDefs.NULL_STR;
    private String cir = AppDefs.NULL_STR;
    private String lbl = AppDefs.NULL_STR;
    private int fia = AppDefs.NULL_INT;
	  
//Public
    
    public CadImportaFiacaoEletrodutoEletricaODataRecord()
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
			AppDefs.NULL_STR,
			AppDefs.NULL_INT );
    }
    
    public CadImportaFiacaoEletrodutoEletricaODataRecord(ResultSet rs)
    {
		DbUtil o = new DbUtil(rs);
		
		this.init(o);
    }

    public CadImportaFiacaoEletrodutoEletricaODataRecord(CadImportaFiacaoEletrodutoEletricaOData o)
    {
    	String strIsDeleted = StringUtil.fromBoolToStr( o.isDeleted() );
    	
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
	    	o.getRowId(),
	    	o.getHnd(),
	    	o.getQdr(),
	    	o.getCir(),
	    	o.getLbl(),
	    	o.getFia() );
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
	    int rowId,
	    String hnd,
	    String qdr,
	    String cir,
	    String lbl,
	    int fia )
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

    	this.rowId = rowId;
    	this.hnd = hnd;
    	this.qdr = qdr;
    	this.cir = cir;
    	this.lbl = lbl;
    	this.fia = fia;
	}
    
	@Override
    public void init(DbUtil o)
    {
		super.initObj(o);
		
    	this.setRowId( o.getNextInt() );
    	this.setHnd( o.getNextStr() );
    	this.setQdr( o.getNextStr() );
    	this.setCir( o.getNextStr() );
    	this.setLbl( o.getNextStr() );
    	this.setFia( o.getNextInt() );
    }
    
    public void init(CadImportaFiacaoEletrodutoEletricaOData o)
    {
		super.initObj(
			AppDefs.NULL_LNG,
			//
			o.getObjectId(), 
			o.getObjType(), 
			o.getObjTypeStr(),
			o.getObjVer(),
			//
			o.getCadRefEntityId(),
			//
			AppDefs.DEF_VALUES_NAO,
			StringUtil.fromBoolToStr( o.isDeleted() ) );

    	this.rowId = o.getRowId();
    	this.hnd = o.getHnd();
    	this.qdr = o.getQdr();
    	this.cir = o.getCir();
    	this.lbl = o.getLbl();
    	this.fia = o.getFia();
    }
	
	/* DEBUG */

	public String toStr() {
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
			"RowId:%s;" +
			"Hnd:%s;" +
			"Qdr:%s;" +
			"Cir:%s;" +
			"Lbl:%s;" +
			"Fia:%s;",
	    	this.getRowId(),
	    	this.getHnd(),
	    	this.getQdr(),
	    	this.getCir(),
	    	this.getLbl(),
	    	this.getFia() );
		return str;
	}
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}
	
	/* TO_CADxxx */

	@Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
		return null;
	}

    /* Getters/Setters */
    
	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public String getHnd() {
		return hnd;
	}

	public void setHnd(String hnd) {
		this.hnd = hnd;
	}

	public String getQdr() {
		return qdr;
	}

	public void setQdr(String qdr) {
		this.qdr = qdr;
	}

	public String getCir() {
		return cir;
	}

	public void setCir(String cir) {
		this.cir = cir;
	}

	public String getLbl() {
		return lbl;
	}

	public void setLbl(String lbl) {
		this.lbl = lbl;
	}

	public int getFia() {
		return fia;
	}

	public void setFia(int fia) {
		this.fia = fia;
	}
	
}
