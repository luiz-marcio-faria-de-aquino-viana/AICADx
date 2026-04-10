/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadQuadroCargasEletricaRecord.java
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

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.dao.record.BaseEntityRecord;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;
import br.com.tlmv.aicadxmod.eletrica.cad.CadQuadroCargasEletrica;

public class CadQuadroCargasEletricaRecord extends BaseEntityRecord 
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
	public static final String sqlTableName = "cad_quadro_cargas_eletrica";
	
	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("ptins_x", 								AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("ptins_y", 								AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("ptins_z", 								AppDefs.TAG_SQLTYPE_DBL),
		//
		new SqlColumnVO("nome_quadro", 							AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("descricao_quadro", 					AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("tensao_quadro", 						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("bitola_minima_condutor", 				AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("disjuntor_minimo_protecao", 			AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("temperatura_ambiente", 				AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("fator_reducao", 						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("sistema_fase", 						AppDefs.TAG_SQLTYPE_STR),
		//
		new SqlColumnVO("qtd_total_cargas_quadro", 				AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("total_cargas_quadro", 					AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("qtd_cargas_paineis_quadro", 			AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("cargas_paineis_quadro", 				AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("qtd_cargas_iluminacao_quadro", 		AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("cargas_iluminacao_quadro", 			AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("qtd_cargas_tomada_quadro", 			AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("cargas_tomada_quadro", 				AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("qtd_cargas_motor_quadro", 				AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("cargas_motor_quadro", 					AppDefs.TAG_SQLTYPE_DBL),
		//
		new SqlColumnVO("qtd_cargas_raiox_quadro", 				AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("cargas_raiox_quadro", 					AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("qtd_cargas_aquecimento_quadro", 		AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("cargas_aquecimento_quadro", 			AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("qtd_cargas_outros_quadro", 			AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("cargas_outros_quadro", 				AppDefs.TAG_SQLTYPE_DBL),
		//
		new SqlColumnVO("potencia_sem_reserva_quadro", 			AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("potencia_quadro", 						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("alimentador_quadro", 					AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("alimentador_protecao_quadro", 			AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("disjuntor_quadro", 					AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("fase_quadro", 							AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("corrente_barra_quadro", 				AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("corrente_barra_neutro_quadro", 		AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("corrente_barra_protecao_quadro", 		AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("minimized", 							AppDefs.TAG_SQLTYPE_BOOL)
		
	};
	
//Private
	private double ptInsX;
	private double ptInsY;
	private double ptInsZ;
	//
	private String nomeQuadro;
	private String descricaoQuadro;
	private Double tensaoQuadro;
	private Double bitolaMinimaCondutor;
	private Double disjuntorMinimoProtecao;
	private Double temperaturaAmbiente;
	private Double fatorReducao;	
	private String sistemaFase;
	//
	private Integer qtdTotalCargasQuadro;	
	private Double totalCargasQuadro;	
	private Integer qtdCargasPaineisQuadro;	
	private Double cargasPaineisQuadro;	
	private Integer qtdCargasIluminacaoQuadro;	
	private Double cargasIluminacaoQuadro;	
	private Integer qtdCargasTomadaQuadro;	
	private Double cargasTomadaQuadro;	
	private Integer qtdCargasMotorQuadro;	
	private Double cargasMotorQuadro;	
	//
	private Integer qtdCargasRaioXQuadro;	
	private Double cargasRaioXQuadro;	
	private Integer qtdCargasAquecimentoQuadro;	
	private Double cargasAquecimentoQuadro;	
	private Integer qtdCargasOutrosQuadro;	
	private Double cargasOutrosQuadro;	
	//
	private Double potenciaSemReservaQuadro;
	private Double potenciaQuadro;	
	private Double alimentadorQuadro;
	private Double alimentadorProtecaoQuadro;
	private Double disjuntorQuadro;
	private String faseQuadro;
	private Double correnteBarraQuadro;
	private Double correnteBarraNeutroQuadro;
	private Double correnteBarraProtecaoQuadro;
	private String isMinimized;

//Public
	
	public CadQuadroCargasEletricaRecord()
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
			AppDefs.NULL_DBL, 
			AppDefs.NULL_DBL, 
			AppDefs.NULL_DBL, 
			AppDefs.NULL_DBL, 
			AppDefs.NULL_DBL, 
			AppDefs.NULL_STR,
			//
			AppDefs.NULL_INT, 
			AppDefs.NULL_DBL, 
			AppDefs.NULL_INT, 
			AppDefs.NULL_DBL, 
			AppDefs.NULL_INT, 
			AppDefs.NULL_DBL, 
			AppDefs.NULL_INT, 
			AppDefs.NULL_DBL, 
			AppDefs.NULL_INT, 
			AppDefs.NULL_DBL, 
			AppDefs.NULL_INT, 
			AppDefs.NULL_DBL, 
			AppDefs.NULL_INT, 
			AppDefs.NULL_DBL, 
			AppDefs.NULL_INT, 
			AppDefs.NULL_DBL, 
			//
			AppDefs.NULL_DBL, 
			AppDefs.NULL_DBL, 
			AppDefs.NULL_DBL, 
			AppDefs.NULL_DBL, 
			AppDefs.NULL_DBL, 
			AppDefs.NULL_STR,
			//
			AppDefs.NULL_DBL, 
			AppDefs.NULL_DBL, 
			AppDefs.NULL_DBL, 
			//
			AppDefs.NULL_STR );
	}

	public CadQuadroCargasEletricaRecord(ResultSet rs)
	{
		DbUtil o = new DbUtil(rs);
		
    	this.init(o);
	}
	
	public CadQuadroCargasEletricaRecord(CadQuadroCargasEletrica o)
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
		
		GeomPoint3d ptIns = o.getPtIns();
		
		String strIsDeleted = StringUtil.fromBoolToStr( o.isDeleted() );
	
		String strIsLocked = StringUtil.fromBoolToStr( o.isLocked() );
		
		String strIsMinimized = StringUtil.fromBoolToStr( o.isMinimized() );
		
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
			ptIns.getX(),
			ptIns.getY(),
			ptIns.getZ(),
			//
			o.getNomeQuadro(),
			o.getDescricaoQuadro(),
			o.getTensaoQuadro(),
			o.getBitolaMinimaCondutor(),
			o.getDisjuntorMinimoProtecao(),
			o.getTemperaturaAmbiente(),
			o.getFatorReducao(),
			o.getSistemaFase(),
			//
			o.getQtdTotalCargasQuadro(),	
			o.getTotalCargasQuadro(),	
			o.getQtdCargasPaineisQuadro(),	
			o.getCargasPaineisQuadro(),
			o.getQtdCargasIluminacaoQuadro(),	
			o.getCargasIluminacaoQuadro(),
			o.getQtdCargasTomadaQuadro(),
			o.getCargasTomadaQuadro(),
			o.getQtdCargasMotorQuadro(),	
			o.getCargasMotorQuadro(),
			//
			o.getQtdCargasRaioXQuadro(),	
			o.getCargasRaioXQuadro(),
			o.getQtdCargasAquecimentoQuadro(),	
			o.getCargasAquecimentoQuadro(),
			o.getQtdCargasOutrosQuadro(),
			o.getCargasOutrosQuadro(),
			//
			o.getPotenciaSemReservaQuadro(),
			o.getPotenciaQuadro(),
			o.getAlimentadorQuadro(),
			o.getAlimentadorProtecaoQuadro(),
			o.getDisjuntorQuadro(),
			o.getFaseQuadro(),
			o.getCorrenteBarraQuadro(),
			o.getCorrenteBarraNeutroQuadro(),
			o.getCorrenteBarraProtecaoQuadro(),
			strIsMinimized );
		
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
		String nomeQuadro,
		String descricaoQuadro,
		Double tensaoQuadro,
		Double bitolaMinimaCondutor,
		Double disjuntorMinimoProtecao,
		Double temperaturaAmbiente,
		Double fatorReducao,
		String sistemaFase,
		//
		Integer qtdTotalCargasQuadro,	
		Double totalCargasQuadro,
		Integer qtdCargasPaineisQuadro,	
		Double cargasPaineisQuadro,
		Integer qtdCargasIluminacaoQuadro,	
		Double cargasIluminacaoQuadro,
		Integer qtdCargasTomadaQuadro,	
		Double cargasTomadaQuadro,
		Integer qtdCargasMotorQuadro,	
		Double cargasMotorQuadro,
		//
		Integer qtdCargasRaioXQuadro,	
		Double cargasRaioXQuadro,
		Integer qtdCargasAquecimentoQuadro,	
		Double cargasAquecimentoQuadro,
		Integer qtdCargasOutrosQuadro,
		Double cargasOutrosQuadro,
		//
		Double potenciaSemReservaQuadro,
		Double potenciaQuadro,
		Double alimentadorQuadro,
		Double alimentadorProtecaoQuadro,
		Double disjuntorQuadro,
		String faseQuadro,
		Double correnteBarraQuadro,
		Double correnteBarraNeutroQuadro,
		Double correnteBarraProtecaoQuadro,
		String isMinimized )
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
		this.nomeQuadro = nomeQuadro;
		this.descricaoQuadro = descricaoQuadro;
		this.tensaoQuadro = tensaoQuadro;
		this.bitolaMinimaCondutor = bitolaMinimaCondutor;
		this.disjuntorMinimoProtecao = disjuntorMinimoProtecao;
		this.temperaturaAmbiente = temperaturaAmbiente;
		this.fatorReducao = fatorReducao;
		this.sistemaFase = sistemaFase;
		//
		this.qtdTotalCargasQuadro = qtdTotalCargasQuadro;	
		this.totalCargasQuadro = totalCargasQuadro;
		this.qtdCargasPaineisQuadro = qtdCargasPaineisQuadro;	
		this.cargasPaineisQuadro = cargasPaineisQuadro;
		this.qtdCargasIluminacaoQuadro = qtdCargasIluminacaoQuadro;	
		this.cargasIluminacaoQuadro = cargasIluminacaoQuadro;
		this.qtdCargasTomadaQuadro = qtdCargasTomadaQuadro;	
		this.cargasTomadaQuadro = cargasTomadaQuadro;
		this.qtdCargasMotorQuadro = qtdCargasMotorQuadro;	
		this.cargasMotorQuadro = cargasMotorQuadro;
		this.qtdCargasRaioXQuadro = qtdCargasRaioXQuadro;	
		this.cargasRaioXQuadro = cargasRaioXQuadro;
		this.qtdCargasAquecimentoQuadro = qtdCargasAquecimentoQuadro;	
		this.cargasAquecimentoQuadro = cargasAquecimentoQuadro;
		this.qtdCargasOutrosQuadro = qtdCargasOutrosQuadro;
		this.cargasOutrosQuadro = cargasOutrosQuadro;
		//
		this.potenciaSemReservaQuadro = potenciaSemReservaQuadro;
		this.potenciaQuadro = potenciaQuadro;
		this.alimentadorQuadro = alimentadorQuadro;
		this.alimentadorProtecaoQuadro = alimentadorProtecaoQuadro;
		this.disjuntorQuadro = disjuntorQuadro;
		this.faseQuadro = faseQuadro;
		//
		this.correnteBarraQuadro = correnteBarraQuadro;
		this.correnteBarraNeutroQuadro = correnteBarraNeutroQuadro;
		this.correnteBarraProtecaoQuadro = correnteBarraProtecaoQuadro;
		//
		this.isMinimized = isMinimized;
	}
	
	@Override
	public void init(DbUtil o)
	{
		super.initEntity(o);
		
		this.setPtInsX( o.getNextDbl() );
		this.setPtInsY( o.getNextDbl() );
		this.setPtInsZ( o.getNextDbl() );
		//
		this.setNomeQuadro( o.getNextStr() );
		this.setDescricaoQuadro( o.getNextStr() );
		this.setTensaoQuadro( o.getNextDbl() );
		this.setBitolaMinimaCondutor( o.getNextDbl() );
		this.setDisjuntorMinimoProtecao( o.getNextDbl() );
		this.setTemperaturaAmbiente( o.getNextDbl() );
		this.setFatorReducao( o.getNextDbl() );
		this.setSistemaFase( o.getNextStr() );
		//
		this.setQtdTotalCargasQuadro( o.getNextInt() );	
		this.setTotalCargasQuadro( o.getNextDbl() );
		this.setQtdCargasPaineisQuadro( o.getNextInt() );	
		this.setCargasPaineisQuadro( o.getNextDbl() );
		this.setQtdCargasIluminacaoQuadro( o.getNextInt() );	
		this.setCargasIluminacaoQuadro( o.getNextDbl() );
		this.setQtdCargasTomadaQuadro( o.getNextInt() );	
		this.setCargasTomadaQuadro( o.getNextDbl() );
		this.setQtdCargasMotorQuadro( o.getNextInt() );	
		this.setCargasMotorQuadro( o.getNextDbl() );
		this.setQtdCargasRaioXQuadro( o.getNextInt() );	
		this.setCargasRaioXQuadro( o.getNextDbl() );
		this.setQtdCargasAquecimentoQuadro( o.getNextInt() );	
		this.setCargasAquecimentoQuadro( o.getNextDbl() );
		this.setQtdCargasOutrosQuadro( o.getNextInt() );
		this.setCargasOutrosQuadro( o.getNextDbl() );
		//
		this.setPotenciaSemReservaQuadro( o.getNextDbl() );
		this.setPotenciaQuadro( o.getNextDbl() );
		this.setAlimentadorQuadro( o.getNextDbl() );
		this.setAlimentadorProtecaoQuadro( o.getNextDbl() );
		this.setDisjuntorQuadro( o.getNextDbl() );
		this.setFaseQuadro( o.getNextStr() );
		//
		this.setCorrenteBarraQuadro( o.getNextDbl() );
		this.setCorrenteBarraNeutroQuadro( o.getNextDbl() );
		this.setCorrenteBarraProtecaoQuadro( o.getNextDbl() );
		//
		this.setIsMinimized( o.getNextStr() );
	}
	
	/* TO_CADxxx */

	@Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
		CadDocumentDef doc = oBlkDef.getDocument();

		CadQuadroCargasEletrica o = new CadQuadroCargasEletrica(
    		oBlkDef, 
    		super.getCadLayerDef(doc), 
    		super.getCadLevel(doc), 
    		super.getZLevel(),
    		false );
			
		GeomPoint3d ptIns = new GeomPoint3d( 
			this.ptInsX,
			this.ptInsY,
			this.ptInsZ );
		
		o.init(
			ptIns,
			this.nomeQuadro,
			this.descricaoQuadro,
			this.tensaoQuadro,
			this.bitolaMinimaCondutor,
			this.disjuntorMinimoProtecao,
			this.temperaturaAmbiente,
			this.fatorReducao,
			this.sistemaFase,
			this.potenciaSemReservaQuadro,
			this.potenciaQuadro,
			this.alimentadorQuadro,
			this.alimentadorProtecaoQuadro,
			this.disjuntorQuadro,
			this.faseQuadro );
		o.setObjectId(this.getObjectId());
				
		o.setQtdTotalCargasQuadro(this.qtdTotalCargasQuadro);	
		o.setTotalCargasQuadro(this.totalCargasQuadro);
		o.setQtdCargasPaineisQuadro(this.qtdCargasPaineisQuadro);	
		o.setCargasPaineisQuadro(this.cargasPaineisQuadro);
		o.setQtdCargasIluminacaoQuadro(this.qtdCargasIluminacaoQuadro);	
		o.setCargasIluminacaoQuadro(this.cargasIluminacaoQuadro);
		o.setQtdCargasTomadaQuadro(this.qtdCargasTomadaQuadro);	
		o.setCargasTomadaQuadro(this.cargasTomadaQuadro);
		o.setQtdCargasMotorQuadro(this.qtdCargasMotorQuadro);	
		o.setCargasMotorQuadro(this.cargasMotorQuadro);
		o.setQtdCargasRaioXQuadro(this.qtdCargasRaioXQuadro);	
		o.setCargasRaioXQuadro(this.cargasRaioXQuadro);
		o.setQtdCargasAquecimentoQuadro(this.qtdCargasAquecimentoQuadro);	
		o.setCargasAquecimentoQuadro(this.cargasAquecimentoQuadro);
		o.setQtdCargasOutrosQuadro(this.qtdCargasOutrosQuadro);
		o.setCargasOutrosQuadro(this.cargasOutrosQuadro);
		//
		o.setCorrenteBarraQuadro(this.correnteBarraQuadro);
		o.setCorrenteBarraNeutroQuadro(this.correnteBarraNeutroQuadro);
		o.setCorrenteBarraProtecaoQuadro(this.correnteBarraProtecaoQuadro);
		//
		o.setMinimized( AppDefs.DEF_VALUES_SIM.equals( this.isMinimized ) );

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

	public String getNomeQuadro() {
		return nomeQuadro;
	}

	public void setNomeQuadro(String nomeQuadro) {
		this.nomeQuadro = nomeQuadro;
	}

	public String getDescricaoQuadro() {
		return descricaoQuadro;
	}

	public void setDescricaoQuadro(String descricaoQuadro) {
		this.descricaoQuadro = descricaoQuadro;
	}

	public Double getTensaoQuadro() {
		return tensaoQuadro;
	}

	public void setTensaoQuadro(Double tensaoQuadro) {
		this.tensaoQuadro = tensaoQuadro;
	}

	public Double getBitolaMinimaCondutor() {
		return bitolaMinimaCondutor;
	}

	public void setBitolaMinimaCondutor(Double bitolaMinimaCondutor) {
		this.bitolaMinimaCondutor = bitolaMinimaCondutor;
	}

	public Double getDisjuntorMinimoProtecao() {
		return disjuntorMinimoProtecao;
	}

	public void setDisjuntorMinimoProtecao(Double disjuntorMinimoProtecao) {
		this.disjuntorMinimoProtecao = disjuntorMinimoProtecao;
	}

	public Double getTemperaturaAmbiente() {
		return temperaturaAmbiente;
	}

	public void setTemperaturaAmbiente(Double temperaturaAmbiente) {
		this.temperaturaAmbiente = temperaturaAmbiente;
	}

	public Double getFatorReducao() {
		return fatorReducao;
	}

	public void setFatorReducao(Double fatorReducao) {
		this.fatorReducao = fatorReducao;
	}

	public String getSistemaFase() {
		return sistemaFase;
	}

	public void setSistemaFase(String sistemaFase) {
		this.sistemaFase = sistemaFase;
	}

	public Integer getQtdTotalCargasQuadro() {
		return qtdTotalCargasQuadro;
	}

	public void setQtdTotalCargasQuadro(Integer qtdTotalCargasQuadro) {
		this.qtdTotalCargasQuadro = qtdTotalCargasQuadro;
	}

	public Double getTotalCargasQuadro() {
		return totalCargasQuadro;
	}

	public void setTotalCargasQuadro(Double totalCargasQuadro) {
		this.totalCargasQuadro = totalCargasQuadro;
	}

	public Integer getQtdCargasPaineisQuadro() {
		return qtdCargasPaineisQuadro;
	}

	public void setQtdCargasPaineisQuadro(Integer qtdCargasPaineisQuadro) {
		this.qtdCargasPaineisQuadro = qtdCargasPaineisQuadro;
	}

	public Double getCargasPaineisQuadro() {
		return cargasPaineisQuadro;
	}

	public void setCargasPaineisQuadro(Double cargasPaineisQuadro) {
		this.cargasPaineisQuadro = cargasPaineisQuadro;
	}

	public Integer getQtdCargasIluminacaoQuadro() {
		return qtdCargasIluminacaoQuadro;
	}

	public void setQtdCargasIluminacaoQuadro(Integer qtdCargasIluminacaoQuadro) {
		this.qtdCargasIluminacaoQuadro = qtdCargasIluminacaoQuadro;
	}

	public Double getCargasIluminacaoQuadro() {
		return cargasIluminacaoQuadro;
	}

	public void setCargasIluminacaoQuadro(Double cargasIluminacaoQuadro) {
		this.cargasIluminacaoQuadro = cargasIluminacaoQuadro;
	}

	public Integer getQtdCargasTomadaQuadro() {
		return qtdCargasTomadaQuadro;
	}

	public void setQtdCargasTomadaQuadro(Integer qtdCargasTomadaQuadro) {
		this.qtdCargasTomadaQuadro = qtdCargasTomadaQuadro;
	}

	public Double getCargasTomadaQuadro() {
		return cargasTomadaQuadro;
	}

	public void setCargasTomadaQuadro(Double cargasTomadaQuadro) {
		this.cargasTomadaQuadro = cargasTomadaQuadro;
	}

	public Integer getQtdCargasMotorQuadro() {
		return qtdCargasMotorQuadro;
	}

	public void setQtdCargasMotorQuadro(Integer qtdCargasMotorQuadro) {
		this.qtdCargasMotorQuadro = qtdCargasMotorQuadro;
	}

	public Double getCargasMotorQuadro() {
		return cargasMotorQuadro;
	}

	public void setCargasMotorQuadro(Double cargasMotorQuadro) {
		this.cargasMotorQuadro = cargasMotorQuadro;
	}

	public Integer getQtdCargasRaioXQuadro() {
		return qtdCargasRaioXQuadro;
	}

	public void setQtdCargasRaioXQuadro(Integer qtdCargasRaioXQuadro) {
		this.qtdCargasRaioXQuadro = qtdCargasRaioXQuadro;
	}

	public Double getCargasRaioXQuadro() {
		return cargasRaioXQuadro;
	}

	public void setCargasRaioXQuadro(Double cargasRaioXQuadro) {
		this.cargasRaioXQuadro = cargasRaioXQuadro;
	}

	public Integer getQtdCargasAquecimentoQuadro() {
		return qtdCargasAquecimentoQuadro;
	}

	public void setQtdCargasAquecimentoQuadro(Integer qtdCargasAquecimentoQuadro) {
		this.qtdCargasAquecimentoQuadro = qtdCargasAquecimentoQuadro;
	}

	public Double getCargasAquecimentoQuadro() {
		return cargasAquecimentoQuadro;
	}

	public void setCargasAquecimentoQuadro(Double cargasAquecimentoQuadro) {
		this.cargasAquecimentoQuadro = cargasAquecimentoQuadro;
	}

	public Integer getQtdCargasOutrosQuadro() {
		return qtdCargasOutrosQuadro;
	}

	public void setQtdCargasOutrosQuadro(Integer qtdCargasOutrosQuadro) {
		this.qtdCargasOutrosQuadro = qtdCargasOutrosQuadro;
	}

	public Double getCargasOutrosQuadro() {
		return cargasOutrosQuadro;
	}

	public void setCargasOutrosQuadro(Double cargasOutrosQuadro) {
		this.cargasOutrosQuadro = cargasOutrosQuadro;
	}

	public Double getPotenciaSemReservaQuadro() {
		return potenciaSemReservaQuadro;
	}

	public void setPotenciaSemReservaQuadro(Double potenciaSemReservaQuadro) {
		this.potenciaSemReservaQuadro = potenciaSemReservaQuadro;
	}

	public Double getPotenciaQuadro() {
		return potenciaQuadro;
	}

	public void setPotenciaQuadro(Double potenciaQuadro) {
		this.potenciaQuadro = potenciaQuadro;
	}

	public Double getAlimentadorQuadro() {
		return alimentadorQuadro;
	}

	public void setAlimentadorQuadro(Double alimentadorQuadro) {
		this.alimentadorQuadro = alimentadorQuadro;
	}

	public Double getAlimentadorProtecaoQuadro() {
		return alimentadorProtecaoQuadro;
	}

	public void setAlimentadorProtecaoQuadro(Double alimentadorProtecaoQuadro) {
		this.alimentadorProtecaoQuadro = alimentadorProtecaoQuadro;
	}

	public Double getDisjuntorQuadro() {
		return disjuntorQuadro;
	}

	public void setDisjuntorQuadro(Double disjuntorQuadro) {
		this.disjuntorQuadro = disjuntorQuadro;
	}

	public String getFaseQuadro() {
		return faseQuadro;
	}

	public void setFaseQuadro(String faseQuadro) {
		this.faseQuadro = faseQuadro;
	}

	public Double getCorrenteBarraQuadro() {
		return correnteBarraQuadro;
	}

	public void setCorrenteBarraQuadro(Double correnteBarraQuadro) {
		this.correnteBarraQuadro = correnteBarraQuadro;
	}

	public Double getCorrenteBarraNeutroQuadro() {
		return correnteBarraNeutroQuadro;
	}

	public void setCorrenteBarraNeutroQuadro(Double correnteBarraNeutroQuadro) {
		this.correnteBarraNeutroQuadro = correnteBarraNeutroQuadro;
	}

	public Double getCorrenteBarraProtecaoQuadro() {
		return correnteBarraProtecaoQuadro;
	}

	public void setCorrenteBarraProtecaoQuadro(Double correnteBarraProtecaoQuadro) {
		this.correnteBarraProtecaoQuadro = correnteBarraProtecaoQuadro;
	}

	public String getIsMinimized() {
		return isMinimized;
	}

	public void setIsMinimized(String isMinimized) {
		this.isMinimized = isMinimized;
	}
	
}
