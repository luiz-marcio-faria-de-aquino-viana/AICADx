/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadCaixaInspecaoAPluvialRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 03/04/2025
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

package br.com.tlmv.aicadxmod.apluvial.dao.record;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.dao.record.BaseEntityRecord;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;
import br.com.tlmv.aicadxmod.apluvial.cad.CadCaixaInspecaoAPluvial;

public class CadCaixaInspecaoAPluvialRecord extends BaseEntityRecord 
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
	
	public static final String sqlTableName = "cad_caixa_inspecao_apluvial";

	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("ptins_x", 			AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("ptins_y", 			AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("ptins_z",		 	AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("tipo_ci", 			AppDefs.TAG_SQLTYPE_STR),		
		new SqlColumnVO("subtipo_ci", 		AppDefs.TAG_SQLTYPE_STR),		
		new SqlColumnVO("numero_ci", 		AppDefs.TAG_SQLTYPE_INT),		
		new SqlColumnVO("proxima_ci", 		AppDefs.TAG_SQLTYPE_INT),		
		new SqlColumnVO("diametro", 		AppDefs.TAG_SQLTYPE_DBL),		
		new SqlColumnVO("profundidade", 	AppDefs.TAG_SQLTYPE_DBL),		
		new SqlColumnVO("declividade", 		AppDefs.TAG_SQLTYPE_DBL)		
		
	};
			
//Private
    private double ptInsX;
    private double ptInsY;
    private double ptInsZ;
    //
    private String tipoCI;			// _ESGOTO_ / _APLUVIAL_
    private String subtipoCI;		// _PRIMARIO_ / _SECUNDARIO_ / _GORDURA_ / _SABAO_ 
    //
    private int numeroCI;
    private int proximaCI;
    //
    private double diametro;
    private double profundidade;
    private double declividade;    
    
//Public
	
	public CadCaixaInspecaoAPluvialRecord()
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
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
		    //
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
		    //
			AppDefs.NULL_INT, 
			AppDefs.NULL_INT, 
		    //
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL );
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
		double ptInsX,
	    double ptInsY,
	    double ptInsZ,
	    //
	    String tipoCI,			// _ESGOTO_ / _APLUVIAL_
	    String subtipoCI,		// _PRIMARIO_ / _SECUNDARIO_ / _GORDURA_ / _SABAO_ 
	    //
	    int numeroCI,
	    int proximaCI,
	    //
	    double diametro,
	    double profundidade,
	    double declividade )
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

		this.ptInsX = ptInsX;
		this.ptInsY = ptInsY;
		this.ptInsZ = ptInsZ;
		//
		this.tipoCI = tipoCI;
		this.subtipoCI = subtipoCI; 
		//
		this.numeroCI = numeroCI;
		this.proximaCI = proximaCI;
		//
		this.diametro = diametro;
		this.profundidade = profundidade;
		this.declividade = declividade;
	}

	@Override
	public void init(DbUtil o)
	{
    	super.initObj(o);
    	
		this.setPtInsX( o.getNextDbl() );
		this.setPtInsY( o.getNextDbl() );
		this.setPtInsZ( o.getNextDbl() );
		//
		this.setTipoCI( o.getNextStr() );
		this.setSubtipoCI( o.getNextStr() );
		//
		this.setNumeroCI( o.getNextInt() );
		this.setProximaCI( o.getNextInt() );
		//
		this.setDiametro( o.getNextDbl() );
		this.setProfundidade( o.getNextDbl() );
		this.setDeclividade( o.getNextDbl() );
	}
	
	/* TO_CADxxx */
	
	@Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
    	CadDocumentDef doc = oBlkDef.getDocument();
    	
    	CadCaixaInspecaoAPluvial o = new CadCaixaInspecaoAPluvial(
			oBlkDef, 
			super.getCadLayerDef(doc), 
			super.getCadLevel(doc), 
			super.getZLevel(), 
			false );
    	
    	o.init(
			this.getPtInsX(), 
			this.getPtInsY(), 
			this.getPtInsZ(),
			this.getDiametro() );
	    return o;
	}
	
	/* Getters/Setters */

	public double getPtInsX() {
		return ptInsX;
	}

	public void setPtInsX(double ptInsX) {
		this.ptInsX = ptInsX;
	}

	public double getPtInsY() {
		return ptInsY;
	}

	public void setPtInsY(double ptInsY) {
		this.ptInsY = ptInsY;
	}

	public double getPtInsZ() {
		return ptInsZ;
	}

	public void setPtInsZ(double ptInsZ) {
		this.ptInsZ = ptInsZ;
	}

	public String getTipoCI() {
		return tipoCI;
	}

	public void setTipoCI(String tipoCI) {
		this.tipoCI = tipoCI;
	}

	public String getSubtipoCI() {
		return subtipoCI;
	}

	public void setSubtipoCI(String subtipoCI) {
		this.subtipoCI = subtipoCI;
	}

	public int getNumeroCI() {
		return numeroCI;
	}

	public void setNumeroCI(int numeroCI) {
		this.numeroCI = numeroCI;
	}

	public int getProximaCI() {
		return proximaCI;
	}

	public void setProximaCI(int proximaCI) {
		this.proximaCI = proximaCI;
	}

	public double getDiametro() {
		return diametro;
	}

	public void setDiametro(double diametro) {
		this.diametro = diametro;
	}

	public double getProfundidade() {
		return profundidade;
	}

	public void setProfundidade(double profundidade) {
		this.profundidade = profundidade;
	}

	public double getDeclividade() {
		return declividade;
	}

	public void setDeclividade(double declividade) {
		this.declividade = declividade;
	}
	
}
