/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadPerfilDrenagemItemODataRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 01/07/2025
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadCaixaInspecaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cad.CadPerfilItemDrenagemOData;

public class CadPerfilItemDrenagemODataRecord extends BaseObjectRecord 
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
	public static final String sqlTableName = "cad_perfil_item_drenagem_odata";
	
	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("perfil_drenagem_item_id", 		AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("numero_ci_atual", 				AppDefs.TAG_SQLTYPE_INT),		
		new SqlColumnVO("numero_ci_anterior", 			AppDefs.TAG_SQLTYPE_INT),		
		new SqlColumnVO("d",				 			AppDefs.TAG_SQLTYPE_DBL),		
		new SqlColumnVO("zcota_terreno_pos", 			AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("zfundo_pos", 					AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("zcota_entrada_pos", 			AppDefs.TAG_SQLTYPE_DBL),		
		new SqlColumnVO("zcota_saida_pos", 				AppDefs.TAG_SQLTYPE_DBL)

	};
			
//Private
	private int perfilDrenagemItemId;
	private int numeroCIAtual;
	private int numeroCIAnterior;
	private double d;
	private double zCotaTerrenoPos;
	private double zFundoPos;
	private double zCotaEntradaPos;
	private double zCotaSaidaPos;
	
//Public
	
	public CadPerfilItemDrenagemODataRecord()
	{
		this.init(
			AppDefs.NULL_INT, 
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
			AppDefs.NULL_INT, 
			AppDefs.NULL_INT, 
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL );
	}
	
	public CadPerfilItemDrenagemODataRecord(String cadRefEntityId, CadPerfilItemDrenagemOData o)
	{
		this.init(cadRefEntityId, o);
	}
	
	public CadPerfilItemDrenagemODataRecord(ResultSet rs)
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
		int perfilDrenagemItemId,
		int numeroCIAtual,
		int numeroCIAnterior,
		double d,
		double zCotaTerrenoPos,
		double zFundoPos,
		double zCotaEntradaPos,
		double zCotaSaidaPos )
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

		this.perfilDrenagemItemId = perfilDrenagemItemId; 
		this.numeroCIAtual = numeroCIAtual; 
		this.numeroCIAnterior = numeroCIAnterior;
		this.d = d;
		this.zCotaTerrenoPos = zCotaTerrenoPos;
		this.zFundoPos = zFundoPos;
		this.zCotaEntradaPos = zCotaEntradaPos;
		this.zCotaSaidaPos = zCotaSaidaPos;		
	}
	
	public void init(String cadRefEntityId, CadPerfilItemDrenagemOData o)
	{
		CadCaixaInspecaoDrenagem oCIAtual = o.getCIAtual();
		CadCaixaInspecaoDrenagem oCIAnterior = o.getCIAnterior();

		int numeroCIAtual = -1;
		if(oCIAtual != null)
			numeroCIAtual = oCIAtual.getNumeroCI();
		
		int numeroCIAnterior = -1;
		if(oCIAnterior != null)
			numeroCIAnterior = oCIAnterior.getNumeroCI();
		
		String strIsDeleted = ( o.isDeleted() ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO );

		this.init(
			AppDefs.NULL_LNG,
			//
			o.getObjectId(),
			o.getObjType(),
			o.getObjTypeStr(),
			o.getObjVer(),
			//
			cadRefEntityId,
			//
			strIsDeleted,
			//
			o.getPerfilDrenagemItemId(),
			numeroCIAtual,
			numeroCIAnterior,
			o.getD(),
			o.getZCotaTerrenoPos(),
			o.getZFundoPos(),
			o.getZCotaEntradaPos(),
			o.getZCotaSaidaPos() );
	}

	@Override
	public void init(DbUtil o)
	{
		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE3_MASC);

		super.initObj(o);
		
		this.setPerfilDrenagemItemId( o.getNextInt() );
		this.setNumeroCIAtual( o.getNextInt() );
		this.setNumeroCIAnterior( o.getNextInt() );
		this.setD( o.getNextDbl() );
		this.setZCotaTerrenoPos( o.getNextDbl() );
		this.setZFundoPos( o.getNextDbl() );
		this.setZCotaEntradaPos( o.getNextDbl() );
		this.setZCotaSaidaPos( o.getNextDbl() );
	}
	
	/* TO_CADxxx */

	@Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
		CadDocumentDef doc = oBlkDef.getDocument();
		
		CadBlockDef blkDef = doc.getCurrBlockDef();
		
		int numeroCIAtual = this.getNumeroCIAtual();
		int numeroCIAnterior = this.getNumeroCIAnterior();
		
		CadCaixaInspecaoDrenagem oCIAtual = (CadCaixaInspecaoDrenagem)blkDef.getEntityAt(numeroCIAtual);
		CadCaixaInspecaoDrenagem oCIAnterior = (CadCaixaInspecaoDrenagem)blkDef.getEntityAt(numeroCIAnterior);
		
		CadPerfilItemDrenagemOData o = new CadPerfilItemDrenagemOData(doc);
		
		o.init(
	    	this.getPerfilDrenagemItemId(),
	    	oCIAtual,
	    	oCIAnterior,
	    	this.getD(),
	    	this.getZCotaTerrenoPos(),
	    	this.getZFundoPos(),
	    	this.getZCotaEntradaPos(),
	    	this.getZCotaSaidaPos(),
	    	null,
	    	null);
		o.setObjectId(this.getObjectId());		
		return o;
	}
	
	/* Getters/Setters */

	public int getNumeroCIAtual() {
		return numeroCIAtual;
	}

	public void setNumeroCIAtual(int numeroCIAtual) {
		this.numeroCIAtual = numeroCIAtual;
	}

	public int getNumeroCIAnterior() {
		return numeroCIAnterior;
	}

	public void setNumeroCIAnterior(int numeroCIAnterior) {
		this.numeroCIAnterior = numeroCIAnterior;
	}

	public double getD() {
		return d;
	}

	public void setD(double d) {
		this.d = d;
	}

	public double getZCotaTerrenoPos() {
		return zCotaTerrenoPos;
	}

	public void setZCotaTerrenoPos(double zCotaTerrenoPos) {
		this.zCotaTerrenoPos = zCotaTerrenoPos;
	}

	public double getZFundoPos() {
		return zFundoPos;
	}

	public void setZFundoPos(double zFundoPos) {
		this.zFundoPos = zFundoPos;
	}

	public double getZCotaEntradaPos() {
		return zCotaEntradaPos;
	}

	public void setZCotaEntradaPos(double zCotaEntradaPos) {
		this.zCotaEntradaPos = zCotaEntradaPos;
	}

	public double getZCotaSaidaPos() {
		return zCotaSaidaPos;
	}

	public void setZCotaSaidaPos(double zCotaSaidaPos) {
		this.zCotaSaidaPos = zCotaSaidaPos;
	}

	public int getPerfilDrenagemItemId() {
		return perfilDrenagemItemId;
	}

	public void setPerfilDrenagemItemId(int perfilDrenagemItemId) {
		this.perfilDrenagemItemId = perfilDrenagemItemId;
	}

}
