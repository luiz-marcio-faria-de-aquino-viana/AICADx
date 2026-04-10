/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadAlinhamentoEstacaPointDrenagemODataRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 29/06/2025
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
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadAlinhamentoEstacaPointDrenagemOData;

public class CadAlinhamentoEstacaPointDrenagemODataRecord extends BaseObjectRecord 
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
		new SqlColumnVO("pt_eixo_x", 			AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("pt_eixo_y", 			AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("pt_eixo_z", 			AppDefs.TAG_SQLTYPE_DBL),
	    //
	    new SqlColumnVO("pt_estaca_direita_x", 	AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("pt_estaca_direita_y", 	AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("pt_estaca_direita_z", 	AppDefs.TAG_SQLTYPE_DBL),
	    //
	    new SqlColumnVO("pt_estaca_esquerda_x", AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("pt_estaca_esquerda_y", AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("pt_estaca_esquerda_z", AppDefs.TAG_SQLTYPE_DBL),
	    //
	    new SqlColumnVO("pt_dir_atual_xi", 		AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("pt_dir_atual_yi", 		AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("pt_dir_atual_zi", 		AppDefs.TAG_SQLTYPE_DBL),
	    //
	    new SqlColumnVO("pt_dir_atual_xf", 		AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("pt_dir_atual_yf", 		AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("pt_dir_atual_zf", 		AppDefs.TAG_SQLTYPE_DBL),
	    //
	    new SqlColumnVO("pt_dir_proximo_xi", 	AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("pt_dir_proximo_yi", 	AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("pt_dir_proximo_zi", 	AppDefs.TAG_SQLTYPE_DBL),
	    //
	    new SqlColumnVO("pt_dir_proximo_xf", 	AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("pt_dir_proximo_yf", 	AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("pt_dir_proximo_zf", 	AppDefs.TAG_SQLTYPE_DBL),
	    //
	    new SqlColumnVO("num_estaca", 			AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("distancia", 			AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("is_ci", 				AppDefs.TAG_SQLTYPE_BOOL),
		new SqlColumnVO("is_estaca", 			AppDefs.TAG_SQLTYPE_BOOL)
	};

//Private
	private double ptEixoX;
	private double ptEixoY;
	private double ptEixoZ;
	//
	private double ptEstacaDireitaX;
	private double ptEstacaDireitaY;
	private double ptEstacaDireitaZ;
	//
	private double ptEstacaEsquerdaX;
	private double ptEstacaEsquerdaY;
	private double ptEstacaEsquerdaZ;
	//
	private double ptDirAtualXI;
	private double ptDirAtualYI;
	private double ptDirAtualZI;
	//
	private double ptDirAtualXF;
	private double ptDirAtualYF;
	private double ptDirAtualZF;
	//
	private double ptDirProximoXI;
	private double ptDirProximoYI;
	private double ptDirProximoZI;
	//
	private double ptDirProximoXF;
	private double ptDirProximoYF;
	private double ptDirProximoZF;
	//
	private int numEstaca;
	private double distancia;
	private String isCI;
	private String isEstaca;
	
//Public
	
	public CadAlinhamentoEstacaPointDrenagemODataRecord()
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
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			//
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			//
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			//
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			//
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			//
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			//
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			//
			AppDefs.NULL_INT, 
			AppDefs.NULL_DBL,
			AppDefs.NULL_STR,				
			AppDefs.NULL_STR );

	}
	
	public CadAlinhamentoEstacaPointDrenagemODataRecord(ResultSet rs)
	{
		DbUtil o = new DbUtil(rs);
		
		this.init(o);
	}
	
	public CadAlinhamentoEstacaPointDrenagemODataRecord(String cadRefEntityId, CadAlinhamentoEstacaPointDrenagemOData o)
	{
		//PT-EIXO
		//
		GeomPoint3d ptEixo3d = o.getPtEixo();
		
		double ptEixoX = ptEixo3d.getX();
		double ptEixoY = ptEixo3d.getY();
		double ptEixoZ = ptEixo3d.getZ();
		
		//PT-ESTACA_DIREITA
		//
		GeomPoint3d ptEstacaDireita3d = o.getPtEstacaDireita();
		
		double ptEstacaDireitaX = ptEstacaDireita3d.getX();
		double ptEstacaDireitaY = ptEstacaDireita3d.getY();
		double ptEstacaDireitaZ = ptEstacaDireita3d.getZ();
		
		//PT-ESTACA_ESQUERDA
		//
		GeomPoint3d ptEstacaEsquerda3d = o.getPtEstacaEsquerda();

		double ptEstacaEsquerdaX = ptEstacaEsquerda3d.getX();
		double ptEstacaEsquerdaY = ptEstacaEsquerda3d.getY();
		double ptEstacaEsquerdaZ = ptEstacaEsquerda3d.getZ();
		
		//V-DIR-ATUAL
		//
		GeomVector3d vDirAtual3d = o.getVDirAtual();

		double ptDirAtualXI = vDirAtual3d.getXI();
		double ptDirAtualYI = vDirAtual3d.getYI();
		double ptDirAtualZI = vDirAtual3d.getZI();
		double ptDirAtualXF = vDirAtual3d.getXF();
		double ptDirAtualYF = vDirAtual3d.getYF();
		double ptDirAtualZF = vDirAtual3d.getZF();
		
		//V-DIR-PROXIMO
		//
		GeomVector3d vDirProximo3d = o.getVDirProximo();

		double ptDirProximoXI = vDirProximo3d.getXI();
		double ptDirProximoYI = vDirProximo3d.getYI();
		double ptDirProximoZI = vDirProximo3d.getZI();
		double ptDirProximoXF = vDirProximo3d.getXF();
		double ptDirProximoYF = vDirProximo3d.getYF();
		double ptDirProximoZF = vDirProximo3d.getZF();
		
		String strIsCI = ( o.isCI() ) ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO;
				
		String strIsEstaca = ( o.isEstaca() ) ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO;
		
		String strIsDeleted = ( o.isDeleted() ) ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO;
		
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
			ptEixoX,
			ptEixoY,
			ptEixoZ,
			//
			ptEstacaDireitaX,
			ptEstacaDireitaY,
			ptEstacaDireitaZ,
			//
			ptEstacaEsquerdaX,
			ptEstacaEsquerdaY,
			ptEstacaEsquerdaZ,
			//
			ptDirAtualXI,
			ptDirAtualYI,
			ptDirAtualZI,
			//
			ptDirAtualXF,
			ptDirAtualYF,
			ptDirAtualZF,
			//
			ptDirProximoXI,
			ptDirProximoYI,
			ptDirProximoZI,
			//
			ptDirProximoXF,
			ptDirProximoYF,
			ptDirProximoZF,
			//
			o.getNumEstaca(),
			o.getDistancia(),
			strIsCI,
			strIsEstaca );
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
		String srtIsDeleted,
		//
		double ptEixoX,
		double ptEixoY,
		double ptEixoZ,
		//
		double ptEstacaDireitaX,
		double ptEstacaDireitaY,
		double ptEstacaDireitaZ,
		//
		double ptEstacaEsquerdaX,
		double ptEstacaEsquerdaY,
		double ptEstacaEsquerdaZ,
		//
		double ptDirAtualXI,
		double ptDirAtualYI,
		double ptDirAtualZI,
		//
		double ptDirAtualXF,
		double ptDirAtualYF,
		double ptDirAtualZF,
		//
		double ptDirProximoXI,
		double ptDirProximoYI,
		double ptDirProximoZI,
		//
		double ptDirProximoXF,
		double ptDirProximoYF,
		double ptDirProximoZF,
		//
		int numEstaca,
		double distancia,
		String isCI,
		String isEstaca )
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
    		srtIsDeleted );

    	this.ptEixoX = ptEixoX;
		this.ptEixoY = ptEixoY;
		this.ptEixoZ = ptEixoZ;
		//
		this.ptEstacaDireitaX = ptEstacaDireitaX;
		this.ptEstacaDireitaY = ptEstacaDireitaY;
		this.ptEstacaDireitaZ = ptEstacaDireitaZ;
		//
		this.ptEstacaEsquerdaX = ptEstacaEsquerdaX;
		this.ptEstacaEsquerdaY = ptEstacaEsquerdaY;
		this.ptEstacaEsquerdaZ = ptEstacaEsquerdaZ;
		//
		this.ptDirAtualXI = ptDirAtualXI;
		this.ptDirAtualYI = ptDirAtualYI;
		this.ptDirAtualZI = ptDirAtualZI;
		//
		this.ptDirAtualXF = ptDirAtualXF;
		this.ptDirAtualYF = ptDirAtualYF;
		this.ptDirAtualZF = ptDirAtualZF;
		//
		this.ptDirProximoXI = ptDirProximoXI;
		this.ptDirProximoYI = ptDirProximoYI;
		this.ptDirProximoZI = ptDirProximoZI;
		//
		this.ptDirProximoXF = ptDirProximoXF;
		this.ptDirProximoYF = ptDirProximoYF;
		this.ptDirProximoZF = ptDirProximoZF;
		//
		this.numEstaca = numEstaca;
		this.distancia = distancia;
		this.isCI = isCI;
		this.isEstaca = isEstaca;
	}
	
    @Override
	public void init(DbUtil o)
	{
		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE3_MASC);

		this.initObj(o);
		
		this.setPtEixoX( o.getNextDbl() );
		this.setPtEixoY( o.getNextDbl() );
		this.setPtEixoZ( o.getNextDbl() );
		//
		this.setPtEstacaDireitaX( o.getNextDbl() );
		this.setPtEstacaDireitaY( o.getNextDbl() );
		this.setPtEstacaDireitaZ( o.getNextDbl() );
		//
		this.setPtEstacaEsquerdaX( o.getNextDbl() );
		this.setPtEstacaEsquerdaY( o.getNextDbl() );
		this.setPtEstacaEsquerdaZ( o.getNextDbl() );
		//
		this.setPtDirAtualXI( o.getNextDbl() );
		this.setPtDirAtualYI( o.getNextDbl() );
		this.setPtDirAtualZI( o.getNextDbl() );
		//
		this.setPtDirAtualXF( o.getNextDbl() );
		this.setPtDirAtualYF( o.getNextDbl() );
		this.setPtDirAtualZF( o.getNextDbl() );
		//
		this.setPtDirProximoXI( o.getNextDbl() );
		this.setPtDirProximoYI( o.getNextDbl() );
		this.setPtDirProximoZI( o.getNextDbl() );
		//
		this.setPtDirProximoXF( o.getNextDbl() );
		this.setPtDirProximoYF( o.getNextDbl() );
		this.setPtDirProximoZF( o.getNextDbl() );
		//
		this.setNumEstaca( o.getNextInt() );
		this.setDistancia( o.getNextDbl() );
		this.setIsCI( o.getNextStr() );
		this.setIsEstaca( o.getNextStr() );
	}
	
	/* TO_CADxxx */

	@Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
		CadDocumentDef doc = oBlkDef.getDocument();		

		CadAlinhamentoEstacaPointDrenagemOData o = new CadAlinhamentoEstacaPointDrenagemOData(doc);
		
	    boolean isCI = StringUtil.fromStrToBool( this.isCI );

	    boolean isEstaca = StringUtil.fromStrToBool( this.isEstaca );
		
		o.init(
			this.getCadRefEntityId(),
			//
		    new GeomPoint3d( 
		    	this.ptEixoX, this.ptEixoY, this.ptEixoZ ),
		    new GeomVector3d( 
		    	this.ptDirAtualXI, this.ptDirAtualYI, this.ptDirAtualZI,
	    		this.ptDirAtualXF, this.ptDirAtualYF, this.ptDirAtualZF ),
		    new GeomVector3d( 
		    	this.ptDirProximoXI, this.ptDirProximoYI, this.ptDirProximoZI,
	    		this.ptDirProximoXF, this.ptDirProximoYF, this.ptDirProximoZF ),
		    //
		    this.numEstaca,
		    this.distancia,
		    isCI,
		    isEstaca );
	    return o;
	}
	
	/* Getters/Setters */

	public double getPtEixoX() {
		return ptEixoX;
	}

	public void setPtEixoX(double ptEixoX) {
		this.ptEixoX = ptEixoX;
	}

	public double getPtEixoY() {
		return ptEixoY;
	}

	public void setPtEixoY(double ptEixoY) {
		this.ptEixoY = ptEixoY;
	}

	public double getPtEixoZ() {
		return ptEixoZ;
	}

	public void setPtEixoZ(double ptEixoZ) {
		this.ptEixoZ = ptEixoZ;
	}

	public double getPtEstacaDireitaX() {
		return ptEstacaDireitaX;
	}

	public void setPtEstacaDireitaX(double ptEstacaDireitaX) {
		this.ptEstacaDireitaX = ptEstacaDireitaX;
	}

	public double getPtEstacaDireitaY() {
		return ptEstacaDireitaY;
	}

	public void setPtEstacaDireitaY(double ptEstacaDireitaY) {
		this.ptEstacaDireitaY = ptEstacaDireitaY;
	}

	public double getPtEstacaDireitaZ() {
		return ptEstacaDireitaZ;
	}

	public void setPtEstacaDireitaZ(double ptEstacaDireitaZ) {
		this.ptEstacaDireitaZ = ptEstacaDireitaZ;
	}

	public double getPtEstacaEsquerdaX() {
		return ptEstacaEsquerdaX;
	}

	public void setPtEstacaEsquerdaX(double ptEstacaEsquerdaX) {
		this.ptEstacaEsquerdaX = ptEstacaEsquerdaX;
	}

	public double getPtEstacaEsquerdaY() {
		return ptEstacaEsquerdaY;
	}

	public void setPtEstacaEsquerdaY(double ptEstacaEsquerdaY) {
		this.ptEstacaEsquerdaY = ptEstacaEsquerdaY;
	}

	public double getPtEstacaEsquerdaZ() {
		return ptEstacaEsquerdaZ;
	}

	public void setPtEstacaEsquerdaZ(double ptEstacaEsquerdaZ) {
		this.ptEstacaEsquerdaZ = ptEstacaEsquerdaZ;
	}

	public double getPtDirAtualXI() {
		return ptDirAtualXI;
	}

	public void setPtDirAtualXI(double ptDirAtualXI) {
		this.ptDirAtualXI = ptDirAtualXI;
	}

	public double getPtDirAtualYI() {
		return ptDirAtualYI;
	}

	public void setPtDirAtualYI(double ptDirAtualYI) {
		this.ptDirAtualYI = ptDirAtualYI;
	}

	public double getPtDirAtualZI() {
		return ptDirAtualZI;
	}

	public void setPtDirAtualZI(double ptDirAtualZI) {
		this.ptDirAtualZI = ptDirAtualZI;
	}

	public double getPtDirAtualXF() {
		return ptDirAtualXF;
	}

	public void setPtDirAtualXF(double ptDirAtualXF) {
		this.ptDirAtualXF = ptDirAtualXF;
	}

	public double getPtDirAtualYF() {
		return ptDirAtualYF;
	}

	public void setPtDirAtualYF(double ptDirAtualYF) {
		this.ptDirAtualYF = ptDirAtualYF;
	}

	public double getPtDirAtualZF() {
		return ptDirAtualZF;
	}

	public void setPtDirAtualZF(double ptDirAtualZF) {
		this.ptDirAtualZF = ptDirAtualZF;
	}

	public double getPtDirProximoXI() {
		return ptDirProximoXI;
	}

	public void setPtDirProximoXI(double ptDirProximoXI) {
		this.ptDirProximoXI = ptDirProximoXI;
	}

	public double getPtDirProximoYI() {
		return ptDirProximoYI;
	}

	public void setPtDirProximoYI(double ptDirProximoYI) {
		this.ptDirProximoYI = ptDirProximoYI;
	}

	public double getPtDirProximoZI() {
		return ptDirProximoZI;
	}

	public void setPtDirProximoZI(double ptDirProximoZI) {
		this.ptDirProximoZI = ptDirProximoZI;
	}

	public double getPtDirProximoXF() {
		return ptDirProximoXF;
	}

	public void setPtDirProximoXF(double ptDirProximoXF) {
		this.ptDirProximoXF = ptDirProximoXF;
	}

	public double getPtDirProximoYF() {
		return ptDirProximoYF;
	}

	public void setPtDirProximoYF(double ptDirProximoYF) {
		this.ptDirProximoYF = ptDirProximoYF;
	}

	public double getPtDirProximoZF() {
		return ptDirProximoZF;
	}

	public void setPtDirProximoZF(double ptDirProximoZF) {
		this.ptDirProximoZF = ptDirProximoZF;
	}

	public int getNumEstaca() {
		return numEstaca;
	}

	public void setNumEstaca(int numEstaca) {
		this.numEstaca = numEstaca;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	public String getIsCI() {
		return isCI;
	}

	public void setIsCI(String isCI) {
		this.isCI = isCI;
	}

	public String getIsEstaca() {
		return isEstaca;
	}

	public void setIsEstaca(String isEstaca) {
		this.isEstaca = isEstaca;
	}

}
