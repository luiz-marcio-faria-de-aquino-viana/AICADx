/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadMemoriaCalculoDrenagemRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/06/2025
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
import java.util.Date;

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
import br.com.tlmv.aicadxmod.drenagem.cad.CadMemoriaCalculoDrenagem;

public class CadMemoriaCalculoDrenagemRecord extends BaseEntityRecord 
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
	public static final String sqlTableName = "cad_memoria_calculo_drenagem";
	
	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("ptins_x", 				AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("ptins_y", 				AppDefs.TAG_SQLTYPE_INT),		
		new SqlColumnVO("ptins_z", 				AppDefs.TAG_SQLTYPE_INT),		
		//
		new SqlColumnVO("nome", 				AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("descricao",			AppDefs.TAG_SQLTYPE_BIGSTR),
		new SqlColumnVO("nome_projeto", 		AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("data_emissao",			AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("codigo_local_medicao", AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("pluviografo", 			AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("coef_manning", 		AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("periodo_recorrencia", 	AppDefs.TAG_SQLTYPE_DBL),
		//
		new SqlColumnVO("is_minimized", 		AppDefs.TAG_SQLTYPE_BOOL)

	};
	
//Private
	private double ptInsX;
	private double ptInsY;
	private double ptInsZ;
	//
	private String nome;
	private String descricao;
	private String nomeProjeto;
	private Date dataEmissao;
    int codigoLocalMedicao;
	private String pluviografo;							// = IDFLOCAL_SANTACRUZ
	private double coefManning;							// = COEFMANNING_SECAO_CIRCULAR
	private double periodoRecorrencia;
	//
	private String isMinimized;
    
//Public
	
	public CadMemoriaCalculoDrenagemRecord()
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
			AppDefs.NULL_STR,
			AppDefs.NULL_DATE,
			AppDefs.NULL_INT, 
			AppDefs.NULL_STR,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			//
			AppDefs.NULL_STR );
	}

	public CadMemoriaCalculoDrenagemRecord(CadMemoriaCalculoDrenagem o)
	{
		this.init(o);
	}

	public CadMemoriaCalculoDrenagemRecord(ResultSet rs)
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
		double ptInsX,
		double ptInsY,
		double ptInsZ,
		//
		String nome,
		String descricao,
		String nomeProjeto,
		Date dataEmissao,
	    int codigoLocalMedicao,
		String pluviografo,							// = IDFLOCAL_SANTACRUZ
		double coefManning,							// = COEFMANNING_SECAO_CIRCULAR
		double periodoRecorrencia,
		//
		String strIsMinimized )
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
		this.nome = nome;
		this.descricao = descricao;
	    this.nomeProjeto = nomeProjeto;
	    this.dataEmissao = dataEmissao;
	    this.codigoLocalMedicao = codigoLocalMedicao;
	    this.pluviografo = pluviografo;				// local medicao volume chuva
	    this.coefManning = coefManning;
	    this.periodoRecorrencia = periodoRecorrencia;  
		//
	    this.isMinimized = strIsMinimized;
	}
	
	public void init(CadMemoriaCalculoDrenagem o)
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
		
	    this.ptInsX = ptIns.getX();
	    this.ptInsY = ptIns.getY();
	    this.ptInsZ = ptIns.getZ();
		
	    String strIsMinimized = StringUtil.fromBoolToStr( o.isMinimized() );	    
	    
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
		    this.ptInsX,
		    this.ptInsY,
		    this.ptInsZ,
		    //
			o.getNome(),
			o.getDescricao(),
			o.getNomeProjeto(),
			o.getDataEmissao(),
			o.getCodigoLocalMedicao(),
			o.getPluviografo(),							// = IDFLOCAL_SANTACRUZ
			o.getCoefManning(),							// = COEFMANNING_SECAO_CIRCULAR
			o.getPeriodoRecorrencia(),
			//
			strIsMinimized );
			
	}
	
	@Override
	public void init(DbUtil o)
	{
		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATETIME_TYPE5_FILEFORMAT_MASC);
		
		super.initEntity(o);
		
		this.setPtInsX( o.getNextDbl() );
		this.setPtInsY( o.getNextDbl() );
		this.setPtInsZ( o.getNextDbl() );
		//
		this.setNome( o.getNextStr() );
		this.setDescricao( o.getNextStr() );
	    this.setNomeProjeto( o.getNextStr() );
	    this.setDataEmissao( StringUtil.safeDate(df, o.getNextStr()) );
	    this.setCodigoLocalMedicao( o.getNextInt() );
	    this.setPluviografo( o.getNextStr() );
	    this.setCoefManning( o.getNextDbl() );
	    this.setPeriodoRecorrencia( o.getNextDbl() );    
	    //
	    this.setIsMinimized( o.getNextStr() );
	}
	
	/* TO_CADxxx */

	@Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
		CadDocumentDef doc = oBlkDef.getDocument();

		GeomPoint3d ptIns = new GeomPoint3d(
			this.getPtInsX(),
			this.getPtInsY(),
			this.getPtInsZ() );
			
		boolean bLocked = StringUtil.fromStrToBool(this.getIsLocked());
		
		boolean bMinimized = StringUtil.fromStrToBool(this.getIsMinimized());
		
		CadMemoriaCalculoDrenagem o = new CadMemoriaCalculoDrenagem(
			oBlkDef, 
			super.getCadLayerDef(doc), 
			super.getCadLevel(doc), 
			this.getZLevel(), 
			bLocked );
		
		o.init(
			ptIns,
			this.nome,
			this.descricao,
			this.nomeProjeto,
			this.dataEmissao,
		    this.codigoLocalMedicao,
		    this.pluviografo,			// local medicao volume chuva
		    this.coefManning,
		    this.periodoRecorrencia,
		    bMinimized,
		    null); 
		o.setObjectId(this.getObjectId());		
		return o;
	}
	
	/* Getters/Setters */

	public String getNomeProjeto() {
		return nomeProjeto;
	}

	public void setNomeProjeto(String nomeProjeto) {
		this.nomeProjeto = nomeProjeto;
	}

	public String getPluviografo() {
		return pluviografo;
	}

	public void setPluviografo(String pluviografo) {
		this.pluviografo = pluviografo;
	}

	public double getCoefManning() {
		return coefManning;
	}

	public void setCoefManning(double coefManning) {
		this.coefManning = coefManning;
	}

	public double getPeriodoRecorrencia() {
		return periodoRecorrencia;
	}

	public void setPeriodoRecorrencia(double periodoRecorrencia) {
		this.periodoRecorrencia = periodoRecorrencia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

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

	public int getCodigoLocalMedicao() {
		return codigoLocalMedicao;
	}

	public void setCodigoLocalMedicao(int codigoLocalMedicao) {
		this.codigoLocalMedicao = codigoLocalMedicao;
	}

	public String getIsMinimized() {
		return isMinimized;
	}

	public void setIsMinimized(String isMinimized) {
		this.isMinimized = isMinimized;
	}
	
}
