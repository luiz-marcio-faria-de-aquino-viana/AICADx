/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadAlinhamentoEstacaDrenagemRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 13/06/2025
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

package br.com.tlmv.aicadxmod.drenagem.dao.record;

import java.sql.ResultSet;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.dao.record.BaseEntityRecord;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadAlinhamentoEstacaDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cad.CadCaixaInspecaoDrenagem;

public class CadAlinhamentoEstacaDrenagemRecord extends BaseEntityRecord 
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
	
	public static final String sqlTableName = "cad_alinhamento_estaca_point_drenagem_odata";

	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("numero_ci_raiz", 			AppDefs.TAG_SQLTYPE_INT),	
		new SqlColumnVO("numero_ci_finish", 		AppDefs.TAG_SQLTYPE_INT),		
		new SqlColumnVO("is_estaca_direita",		AppDefs.TAG_SQLTYPE_BOOL),	
		new SqlColumnVO("is_estaca_esquerda", 		AppDefs.TAG_SQLTYPE_BOOL),		
		new SqlColumnVO("numero_inicial_estaca", 	AppDefs.TAG_SQLTYPE_INT)
		
	};
	
//Private
	private int numeroCIRaiz = AppDefs.NULL_INT;
	private int numeroCIFinish = AppDefs.NULL_INT;
	private String isEstacaDireita = AppDefs.DEF_VALUES_NAO;
	private String isEstacaEsquerda = AppDefs.DEF_VALUES_NAO;
	private int numeroInicialEstaca = AppDefs.NULL_INT;
    
//Public
	
	public CadAlinhamentoEstacaDrenagemRecord()
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
			AppDefs.DEF_VALUES_NAO,
			//
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			//
			AppDefs.NULL_DBL,
			//
			AppDefs.NULL_INT,
			AppDefs.NULL_INT,
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			AppDefs.NULL_INT );
	}
	
	public CadAlinhamentoEstacaDrenagemRecord(ResultSet rs)
	{
		DbUtil o = new DbUtil(rs);
		
		this.init(o);
	}
	
	public CadAlinhamentoEstacaDrenagemRecord(CadAlinhamentoEstacaDrenagem o)
	{
    	// LAYER_DEF
    	//
    	CadLayerDef oLayer = o.getLayer();
    	String reference = oLayer.getReference(); 
		
    	// LEVEL
    	//
    	CadLevel oLevel = o.getLevel();
    	String levelName = AppDefs.DEFAULT_LEVELNAME;
    	if(oLevel != null)
        	levelName = oLevel.getLevelLocalName();
    		
		CadCaixaInspecaoDrenagem oCIRaiz = (CadCaixaInspecaoDrenagem)o.getCIRaiz();
		int numeroCIRaiz = oCIRaiz.getObjectId();
		
		CadCaixaInspecaoDrenagem oCIFinish = (CadCaixaInspecaoDrenagem)o.getCIFinish();
		int numeroCIFinish = oCIFinish.getObjectId();

		String strIsEstacaDireita = StringUtil.fromBoolToStr( o.isEstacaDireita() ); 

		String strIsEstacaEsquerda = StringUtil.fromBoolToStr( o.isEstacaEsquerda() ); 

		int numInicialEstaca = o.getNumeroInicialEstaca();
		
		String strIsDeleted = StringUtil.fromBoolToStr( o.isDeleted() ); 
		
		String strIsLocked = StringUtil.fromBoolToStr( o.isLocked() ); 
		
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
			strIsLocked,
			//
			reference,
			levelName,
			//
			o.getZLevel(),
			//
			numeroCIRaiz,
			numeroCIFinish,
			strIsEstacaDireita,
			strIsEstacaEsquerda,
			numInicialEstaca );
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
		String strIsLocked,
		//
	    String reference,
	    String levelName,
	    //
	    double zLevel,
		//
		int numeroCIRaiz,
		int numeroCIFinish,
		String isEstacaDireita,
		String isEstacaEsquerda,
		int numeroInicialEstaca )
	{
    	super.initEntity(
    		oid, 
    		//
    		objectId, 
    		objType, 
    		objTypeStr, 
    		objVer, 
    		//
    		cadRefEntityId,
    		//
    		strIsDeleted,
    		strIsLocked,
    		//
    		reference, 
    		levelName,
    		//
    		zLevel );

		this.numeroCIRaiz = numeroCIRaiz;
		this.numeroCIFinish = numeroCIFinish;
		this.isEstacaDireita = isEstacaDireita;
		this.isEstacaEsquerda = isEstacaEsquerda;
		this.numeroInicialEstaca = numeroInicialEstaca;
	}
	
    @Override
	public void init(DbUtil o)
	{
		this.initObj(o);
		
		this.setNumeroCIRaiz( o.getNextInt() );
		this.setNumeroCIFinish( o.getNextInt() );
		this.setIsEstacaDireita( o.getNextStr() );
		this.setIsEstacaEsquerda( o.getNextStr() );
		this.setNumeroInicialEstaca( o.getNextInt() );
	}
	
	/* TO_CADxxx */
	
	@Override
    public CadObject toCadObject(CadBlockDef oBlkDef) {		
		CadCaixaInspecaoDrenagem oCIRaiz = (CadCaixaInspecaoDrenagem)oBlkDef.getEntity(this.numeroCIRaiz); 
		CadCaixaInspecaoDrenagem oCIFinish = (CadCaixaInspecaoDrenagem)oBlkDef.getEntity(this.numeroCIFinish); 
		
		CadAlinhamentoEstacaDrenagem o = (CadAlinhamentoEstacaDrenagem)super.toCadObject(oBlkDef, this.getClass());
		o.init(
			oCIRaiz,
			oCIFinish,
			this.getNumeroInicialEstaca() );
	    return o;
	}
	
	/* Getters/Setters */

	public int getNumeroCIRaiz() {
		return numeroCIRaiz;
	}

	public void setNumeroCIRaiz(int numeroCIRaiz) {
		this.numeroCIRaiz = numeroCIRaiz;
	}

	public int getNumeroCIFinish() {
		return numeroCIFinish;
	}

	public void setNumeroCIFinish(int numeroCIFinish) {
		this.numeroCIFinish = numeroCIFinish;
	}

	public String getIsEstacaDireita() {
		return isEstacaDireita;
	}

	public void setIsEstacaDireita(String isEstacaDireita) {
		this.isEstacaDireita = isEstacaDireita;
	}

	public String getIsEstacaEsquerda() {
		return isEstacaEsquerda;
	}

	public void setIsEstacaEsquerda(String isEstacaEsquerda) {
		this.isEstacaEsquerda = isEstacaEsquerda;
	}

	public int getNumeroInicialEstaca() {
		return numeroInicialEstaca;
	}

	public void setNumeroInicialEstaca(int numeroInicialEstaca) {
		this.numeroInicialEstaca = numeroInicialEstaca;
	}
	
}
