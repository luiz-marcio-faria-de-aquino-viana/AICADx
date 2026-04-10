/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadFioEletricoEletricaODataRecord.java
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
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;
import br.com.tlmv.aicadxmod.eletrica.cad.CadFioEletricoEletricaOData;

public class CadFioEletricoEletricaODataRecord extends BaseObjectRecord 
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
	public static final String sqlTableName = "cad_fio_eletrico_eletrica_odata";
	
	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("row_id", 							AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("eletroduto_id", 					AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("nome_quadro", 						AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("circuito", 						AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("tipo_condutor", 					AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("bitola_condutor", 					AppDefs.TAG_SQLTYPE_DBL)
			
	};
	
//Private
    private int rowId = AppDefs.NULL_INT;
    private int eletrodutoId = AppDefs.NULL_INT;
    private String nomeQuadro = AppDefs.NULL_STR;
    private String circuito = AppDefs.NULL_STR;
    private String tipoCondutor = AppDefs.NULL_STR;
    private double bitolaCondutor = AppDefs.NULL_DBL;
	  
//Public
    
    public CadFioEletricoEletricaODataRecord()
    {
    	this.init(
			AppDefs.NULL_LNG, 
			//
			AppDefs.NULL_INT, 
			AppDefs.NULL_INT, 
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			//
			AppDefs.NULL_STR,
			//
			AppDefs.DEF_VALUES_NAO,
			//
			AppDefs.NULL_INT, 
			AppDefs.NULL_INT, 
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			AppDefs.NULL_DBL);
    }
    
    public CadFioEletricoEletricaODataRecord(ResultSet rs)
    {
		DbUtil o = new DbUtil(rs);
		
		this.init(o);
    }

    public CadFioEletricoEletricaODataRecord(CadFioEletricoEletricaOData o)
    {
    	String strIsDeleted = ( o.isDeleted() ) ? "S" : "N";
    	
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
	    	o.getEletrodutoId(),
	    	o.getNomeQuadro(),
	    	o.getCircuito(),
	    	o.getTipoCondutor(),
	    	o.getBitolaCondutor() );
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
	    int eletrodutoId,
	    String nomeQuadro,
	    String circuito,
	    String tipoCondutor,
	    double bitolaCondutor)
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
    	this.eletrodutoId = eletrodutoId;
    	this.nomeQuadro = nomeQuadro;
    	this.circuito = circuito;
    	this.tipoCondutor = tipoCondutor;
    	this.bitolaCondutor = bitolaCondutor;
	}
    
	@Override
    public void init(DbUtil o)
    {
		super.initObj(o);

    	this.setRowId( o.getNextInt() );
    	this.setEletrodutoId( o.getNextInt() );
    	this.setNomeQuadro( o.getNextStr() );
    	this.setCircuito( o.getNextStr() );
    	this.setTipoCondutor( o.getNextStr() );
    	this.setBitolaCondutor( o.getNextDbl() );
    }
    
    public void init(CadFioEletricoEletricaOData o)
    {
		super.initObj(
			AppDefs.NULL_LNG, 
			o.getObjectId(), 
			o.getObjType(), 
			o.getObjTypeStr(),
			o.getObjVer(),
			o.getCadRefEntityId(),
			AppDefs.DEF_VALUES_NAO, 
			StringUtil.fromBoolToStr( o.isDeleted() ) );

    	this.rowId = o.getRowId();
    	this.eletrodutoId = o.getEletrodutoId();
    	this.nomeQuadro = o.getNomeQuadro();
    	this.circuito = o.getCircuito();
    	this.tipoCondutor = o.getTipoCondutor();
    	this.bitolaCondutor = o.getBitolaCondutor();
    }
	
	/* DEBUG */

	public String toStr() {
		NumberFormat nf3 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
			"RowId:%s;" +
	    	"EletrodutoId:%s;" +
	    	"NomeQuadro:%s;" +
	    	"Circuito:%s;" +
	    	"TipoCondutor:%s;" +
	    	"BitolaCondutor:%s; ",
	    	this.getRowId(),
	    	this.getEletrodutoId(),
	    	this.getNomeQuadro(),
	    	this.getCircuito(),
	    	this.getTipoCondutor(),
	    	this.getBitolaCondutor() );
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
		CadDocumentDef doc = oBlkDef.getDocument(); 

		CadFioEletricoEletricaOData o = new CadFioEletricoEletricaOData(doc);
		
		o.init(
	    	this.getRowId(),
	    	this.getEletrodutoId(),
	    	this.getNomeQuadro(),
	    	this.getCircuito(),
	    	this.getTipoCondutor(),
	    	this.getBitolaCondutor() );
		o.setObjectId(this.getObjectId());
		return o;
	}

    /* Getters/Setters */

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public int getEletrodutoId() {
		return eletrodutoId;
	}

	public void setEletrodutoId(int eletrodutoId) {
		this.eletrodutoId = eletrodutoId;
	}

	public String getNomeQuadro() {
		return nomeQuadro;
	}

	public void setNomeQuadro(String nomeQuadro) {
		this.nomeQuadro = nomeQuadro;
	}

	public String getCircuito() {
		return circuito;
	}

	public void setCircuito(String circuito) {
		this.circuito = circuito;
	}

	public String getTipoCondutor() {
		return tipoCondutor;
	}

	public void setTipoCondutor(String tipoCondutor) {
		this.tipoCondutor = tipoCondutor;
	}

	public double getBitolaCondutor() {
		return bitolaCondutor;
	}

	public void setBitolaCondutor(double bitolaCondutor) {
		this.bitolaCondutor = bitolaCondutor;
	}
	
}
