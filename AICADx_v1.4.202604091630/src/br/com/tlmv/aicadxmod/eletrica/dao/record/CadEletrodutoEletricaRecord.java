/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadEletrodutoEletricaRecord.java
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

package br.com.tlmv.aicadxmod.eletrica.dao.record;

import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.dao.record.BaseEntityRecord;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;
import br.com.tlmv.aicadxmod.eletrica.cad.CadEletrodutoEletrica;
import br.com.tlmv.aicadxmod.eletrica.cad.CadPontoEletrica;
import br.com.tlmv.aicadxmod.eletrica.cad.CadFioEletricoEletricaOData;
import br.com.tlmv.aicadxmod.eletrica.cad.CadImportaFiacaoEletricaOData;

public class CadEletrodutoEletricaRecord extends BaseEntityRecord 
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
	public static final String sqlTableName = "cad_eletroduto_eletrica";
	
	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("enti", 							AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("entf", 							AppDefs.TAG_SQLTYPE_INT),
		//
		new SqlColumnVO("tipo_eletroduto", 					AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("nome_bitola_eletroduto", 			AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("bitola_eletroduto_interna", 		AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("bitola_eletroduto_externa", 		AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("area_eletroduto", 					AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("area_ocupada", 					AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("taxa_ocupacao",					AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("numero_condutores", 				AppDefs.TAG_SQLTYPE_INT),
		//
		new SqlColumnVO("tipo_indicador_fiacao", 			AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("ptins_x_indicador_fiacao", 		AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("ptins_y_indicador_fiacao", 		AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("ptins_z_indicador_fiacao", 		AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("num_indicador_fiacao", 			AppDefs.TAG_SQLTYPE_INT)
			
	};
	
//Private
	private int entI = AppDefs.NULL_INT;
	private int entF = AppDefs.NULL_INT;
	
	private String tipoEletroduto = AppDefs.NULL_STR;
	private String nomeBitolaEletroduto = AppDefs.NULL_STR;
	private double bitolaEletrodutoInterna = AppDefs.DEF_DEFAULT_BITOLA_MINIMA_ELETRODUTO_INTERNA;
	private double bitolaEletrodutoExterna = AppDefs.DEF_DEFAULT_BITOLA_MINIMA_ELETRODUTO_EXTERNA;
	private double areaEletroduto = AppDefs.NULL_DBL;
	private double areaOcupada = AppDefs.NULL_DBL;
	private double taxaOcupacao = AppDefs.NULL_DBL;
	private int numeroCondutores = AppDefs.NULL_INT;
	
	//POSICAO_FIACAO_ELETRODUTO
	private int tipoIndicadorFiacao = AppDefs.DEF_POSFIA_ELETRODUTO_CENTRO;
	private GeomPoint3d ptInsIndicadorFiacao = null;
	private int numIndicadorFiacao = AppDefs.NULL_INT;

	//IMPORTA_FIACAO
	private CadImportaFiacaoEletricaOData oImportaFiacao = null;
	private ArrayList<CadFioEletricoEletricaOData> lsFio = null;
    
//Public
	
	public CadEletrodutoEletricaRecord()
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
			//
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_INT,
			//
			//POSICAO_FIACAO_ELETRODUTO
			AppDefs.NULL_INT,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_INT );
	}

	public CadEletrodutoEletricaRecord(CadEletrodutoEletrica o)
	{
		this.init(o);
	}
	
	public CadEletrodutoEletricaRecord(ResultSet rs)
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
		String strIsLocked,
		//
		String reference,
		String levelName,
		//
		double zLevel,
		//
		int entI,
		int entF,
		//
		String tipoEletroduto,
		String nomeBitolaEletroduto,
		double bitolaEletrodutoInterna,
		double bitolaEletrodutoExterna,
		double areaEletroduto,
		double areaOcupada,
		double taxaOcupacao,
		int numeroCondutores,
		//
		//POSICAO_FIACAO_ELETRODUTO
		int tipoIndicadorFiacao,
		double ptInsXIndicadorFiacao,
		double ptInsYIndicadorFiacao,
		double ptInsZIndicadorFiacao,
		int numIndicadorFiacao )
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

		this.entI = entI;
		this.entF = entF;
		//
		this.tipoEletroduto = tipoEletroduto;
		this.nomeBitolaEletroduto = nomeBitolaEletroduto;
		this.bitolaEletrodutoInterna = bitolaEletrodutoInterna;
		this.bitolaEletrodutoExterna = bitolaEletrodutoExterna;
		this.areaEletroduto = areaEletroduto;
		this.areaOcupada = areaOcupada;
		this.taxaOcupacao = taxaOcupacao;
		this.numeroCondutores = numeroCondutores;

		//POSICAO_FIACAO_ELETRODUTO
		this.tipoIndicadorFiacao = tipoIndicadorFiacao;
		this.numIndicadorFiacao = numIndicadorFiacao;

		this.setPtInsIndicadorFiacao( new GeomPoint3d( ptInsXIndicadorFiacao, ptInsYIndicadorFiacao, ptInsZIndicadorFiacao ) );
	}
	
	public void init(CadEletrodutoEletrica o)
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
    		
		int entI = o.getEntI().getObjectId();
		int entF = o.getEntF().getObjectId();

		String strIsDeleted = ( o.isDeleted() ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO );
		
		String strIsLocked = ( o.isDeleted() ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO );
		
		//POSICAO_FIACAO_ELETRODUTO
		int tipoIndicadorFiacao = o.getTipoIndicadorFiacao();
		int numIndicadorFiacao = o.getNumIndicadorFiacao();

		GeomPoint3d ptInsIndicadorFiacao = new GeomPoint3d(0.0, 0.0, 0.0);
		if( o.getPtInsIndicadorFiacao() != null ) {
			ptInsIndicadorFiacao = new GeomPoint3d( o.getPtInsIndicadorFiacao() );
		}
		
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
			entI,
			entF,
			//
			o.getTipoEletroduto(),
			o.getNomeBitolaEletroduto(),
			o.getBitolaEletrodutoInterna(),
			o.getBitolaEletrodutoExterna(),
			o.getAreaEletroduto(),
			o.getAreaOcupada(),
			o.getTaxaOcupacao(),
			o.getNumeroCondutores(),

			//POSICAO_FIACAO_ELETRODUTO
			tipoIndicadorFiacao,
			ptInsIndicadorFiacao.getX(),
			ptInsIndicadorFiacao.getY(),
			ptInsIndicadorFiacao.getZ(),
			numIndicadorFiacao );
	}
	
	public void init(DbUtil o)
	{
		super.initEntity(o);

		this.setEntI( o.getNextInt() );
		this.setEntF( o.getNextInt() );
		//
		this.setTipoEletroduto( o.getNextStr() );
		this.setNomeBitolaEletroduto( o.getNextStr() );
		this.setBitolaEletrodutoInterna( o.getNextDbl() );
		this.setBitolaEletrodutoExterna( o.getNextDbl() );
		this.setAreaEletroduto( o.getNextDbl() );
		this.setAreaOcupada( o.getNextDbl() );
		this.setTaxaOcupacao( o.getNextDbl() );
		this.setNumeroCondutores( o.getNextInt() );
		
		//POSICAO_FIACAO_ELETRODUTO
		this.setTipoIndicadorFiacao( o.getNextInt() );

		double ptInsXIndicadorFiacao = o.getNextDbl();
		double ptInsYIndicadorFiacao = o.getNextDbl();
		double ptInsZIndicadorFiacao =o.getNextDbl();

		this.setPtInsIndicadorFiacao( new GeomPoint3d( ptInsXIndicadorFiacao, ptInsYIndicadorFiacao, ptInsZIndicadorFiacao ) );
		this.setNumIndicadorFiacao( o.getNextInt() );
	}
	
	/* TO_CADxxx */

	@Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
		CadDocumentDef doc = oBlkDef.getDocument();

		CadPontoEletrica entI = (CadPontoEletrica)oBlkDef.getEntity( this.entI );
		if(entI == null) return null;
		
		CadPontoEletrica entF = (CadPontoEletrica)oBlkDef.getEntity( this.entF );
		if(entF == null) return null;
		
		CadEletrodutoEletrica o = new CadEletrodutoEletrica(
	    		oBlkDef, 
	    		super.getCadLayerDef(doc), 
	    		super.getCadLevel(doc), 
	    		super.getZLevel(),
	    		false );
			
    	o.init(
			entI,
			entF,
			//
			this.getTipoEletroduto(),
			this.getNomeBitolaEletroduto(),
			this.getBitolaEletrodutoInterna(),
			this.getBitolaEletrodutoExterna(),
			this.getAreaEletroduto(),
			this.getAreaOcupada(),
			this.getTaxaOcupacao(),
			this.getNumeroCondutores(),
			//
			this.getTipoIndicadorFiacao(),
			this.getNumIndicadorFiacao(),
			this.getPtInsIndicadorFiacao() );
		return o;
	}
	
	/* Getters/Setters */

	public int getEntI() {
		return entI;
	}

	public void setEntI(int entI) {
		this.entI = entI;
	}

	public int getEntF() {
		return entF;
	}

	public void setEntF(int entF) {
		this.entF = entF;
	}

	public String getTipoEletroduto() {
		return tipoEletroduto;
	}

	public void setTipoEletroduto(String tipoEletroduto) {
		this.tipoEletroduto = tipoEletroduto;
	}

	public String getNomeBitolaEletroduto() {
		return nomeBitolaEletroduto;
	}

	public void setNomeBitolaEletroduto(String nomeBitolaEletroduto) {
		this.nomeBitolaEletroduto = nomeBitolaEletroduto;
	}

	public double getAreaEletroduto() {
		return areaEletroduto;
	}

	public void setAreaEletroduto(double areaEletroduto) {
		this.areaEletroduto = areaEletroduto;
	}

	public double getAreaOcupada() {
		return areaOcupada;
	}

	public void setAreaOcupada(double areaOcupada) {
		this.areaOcupada = areaOcupada;
	}

	public double getTaxaOcupacao() {
		return taxaOcupacao;
	}

	public void setTaxaOcupacao(double taxaOcupacao) {
		this.taxaOcupacao = taxaOcupacao;
	}

	public int getNumeroCondutores() {
		return numeroCondutores;
	}

	public void setNumeroCondutores(int numeroCondutores) {
		this.numeroCondutores = numeroCondutores;
	}

	public double getBitolaEletrodutoInterna() {
		return bitolaEletrodutoInterna;
	}

	public void setBitolaEletrodutoInterna(double bitolaEletrodutoInterna) {
		this.bitolaEletrodutoInterna = bitolaEletrodutoInterna;
	}

	public double getBitolaEletrodutoExterna() {
		return bitolaEletrodutoExterna;
	}

	public void setBitolaEletrodutoExterna(double bitolaEletrodutoExterna) {
		this.bitolaEletrodutoExterna = bitolaEletrodutoExterna;
	}

	public CadImportaFiacaoEletricaOData getImportaFiacao() {
		return oImportaFiacao;
	}

	public void setImportaFiacao(CadImportaFiacaoEletricaOData oImportaFiacao) {
		this.oImportaFiacao = oImportaFiacao;
	}

	public ArrayList<CadFioEletricoEletricaOData> getLsFio() {
		return lsFio;
	}

	public void setLsFio(ArrayList<CadFioEletricoEletricaOData> lsFio) {
		this.lsFio = lsFio;
	}

	public int getTipoIndicadorFiacao() {
		return tipoIndicadorFiacao;
	}

	public void setTipoIndicadorFiacao(int tipoIndicadorFiacao) {
		this.tipoIndicadorFiacao = tipoIndicadorFiacao;
	}

	public GeomPoint3d getPtInsIndicadorFiacao() {
		return ptInsIndicadorFiacao;
	}

	public void setPtInsIndicadorFiacao(GeomPoint3d ptInsIndicadorFiacao) {
		this.ptInsIndicadorFiacao = ptInsIndicadorFiacao;
	}

	public int getNumIndicadorFiacao() {
		return numIndicadorFiacao;
	}

	public void setNumIndicadorFiacao(int numIndicadorFiacao) {
		this.numIndicadorFiacao = numIndicadorFiacao;
	}
	
}
