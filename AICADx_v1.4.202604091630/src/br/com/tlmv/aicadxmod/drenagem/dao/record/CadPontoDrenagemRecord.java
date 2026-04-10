/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadPontoDrenagemRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 07/08/2025
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
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.Shape;
import br.com.tlmv.aicadxapp.cad.tables.ShapeTable;
import br.com.tlmv.aicadxapp.dao.record.BaseEntityRecord;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadCaixaInspecaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cad.CadPontoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.calc.DrenagemCalc;

public class CadPontoDrenagemRecord extends BaseEntityRecord
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
	public static final String sqlTableName = "cad_ponto_drenagem";
	
	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("shape_name", 						AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("shape_file_name",					AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("shape_default_z",					AppDefs.TAG_SQLTYPE_DBL),
		//
		new SqlColumnVO("ptins_x",							AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("ptins_y",							AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("ptins_z",							AppDefs.TAG_SQLTYPE_DBL),
		//
		new SqlColumnVO("rotate",							AppDefs.TAG_SQLTYPE_DBL),

		//PROPRIEDADES
		new SqlColumnVO("prox_ent_id", 						AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("tipo_secao_tubulacao", 			AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("categoria_tubulacao_id", 			AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("descricao_categoria_tubulacao", 	AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("qtd_tubulacao", 					AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("diametro_tubulacao", 				AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("largura", 							AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("altura", 							AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("profundidade", 					AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("ct", 								AppDefs.TAG_SQLTYPE_DBL),
		//
		new SqlColumnVO("cb", 								AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("cota_saida", 						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("declividade", 						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("compr_tubulacao", 					AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("compr_horiz_tubulacao", 			AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("compr_vert_tubulacao", 			AppDefs.TAG_SQLTYPE_DBL)
		
	};
	
//Private
	private String shapeName;
	private String shapeFileName;
	private double shapeDefaultZ;
	//
	private double ptInsX;
	private double ptInsY;
	private double ptInsZ;
	//
	private double rotate;
    
	//PROPRIEDADES
    private int proxEntId = AppDefs.NULL_INT;
    private String tipoSecaoTubulacao = DrenagemCalc.DEF_TIPOSECAO_CIRCULAR_STR;
    private int categoriaTubulacaoId = DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_400MM.getCategoriaTubulacaoId();
    private String descricaoCategoriaTubulacao = DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_400MM.getDescricaoCategoriaTubulacao();
    private int qtdTubulacao = 1;
    private double diametroTubulacaoMeter = DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_400MM.getDiamNominalMeter();
    private double largura = 0.0;
    private double altura = 0.0;
    private double profundidade = 0.0;
    private double ct = 0.0;
    //
    private double cb = 0.0;
    private double cotaSaida = 0.0;
    private double declividade = AppDefs.DEF_DEFAULT_DRENAGEM_DECLIVIDADEMINIMA;
    private double comprTubulacao = 0.0;
    private double comprHorizTubulacao = 0.0;
    private double comprVertTubulacao = 0.0;
	
//Public
    
    public CadPontoDrenagemRecord()
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
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 

    		//PROPRIEDADES
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_STR,
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_STR,
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		//
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL, 
    		AppDefs.NULL_DBL );
    }
	
	public CadPontoDrenagemRecord(CadPontoDrenagem o)
	{
		this.init(o);
	}
	
	public CadPontoDrenagemRecord(ResultSet rs)
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
		String shapeName,
		String shapeFileName,
		double shapeDefaultZ,
		//
		double ptInsX,
		double ptInsY,
		double ptInsZ,
		//
		double rotate,
	    
		//PROPRIEDADES
	    int proxEntId,
	    String tipoSecaoTubulacao,
	    int categoriaTubulacaoId,
	    String descricaoCategoriaTubulacao,
	    int qtdTubulacao,
	    double diametroTubulacaoMeter,
	    double largura,
	    double altura,
	    double profundidade,
	    double ct,
		//
	    double cb,
	    double cotaSaida,
	    double declividade,
	    double comprTubulacao,
	    double comprHorizTubulacao,
	    double comprVertTubulacao )
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

    	this.shapeName = shapeName;
    	this.shapeFileName = shapeFileName;
    	this.shapeDefaultZ = shapeDefaultZ;
    	//
    	this.ptInsX = ptInsX;
    	this.ptInsY = ptInsY;
    	this.ptInsZ = ptInsZ;
    	//
    	this.rotate = rotate;
        
    	//PROPRIEDADES
    	this.proxEntId = proxEntId;
    	this.tipoSecaoTubulacao = tipoSecaoTubulacao;
    	this.categoriaTubulacaoId = categoriaTubulacaoId;
    	this.descricaoCategoriaTubulacao = descricaoCategoriaTubulacao;
    	this.qtdTubulacao = qtdTubulacao;
    	this.diametroTubulacaoMeter = diametroTubulacaoMeter;
    	this.largura = largura;
    	this.altura = altura;
    	this.profundidade = profundidade;
    	this.ct = ct;
    	//
    	this.cb = cb;
    	this.cotaSaida = cotaSaida;
    	this.declividade = declividade;
    	this.comprTubulacao = comprTubulacao;
    	this.comprHorizTubulacao = comprHorizTubulacao;
    	this.comprVertTubulacao = comprVertTubulacao;    
    }
    
	public void init(CadPontoDrenagem o)
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
    		
		Shape oShape = o.getShape();

		GeomPoint3d ptIns = o.getPtIns();
		
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
    		oShape.getName(),
    		oShape.getFileName(),
    		oShape.getDefaultZ(),
    		//
    		ptIns.getX(),
    		ptIns.getY(),
    		ptIns.getZ(),
    		//
    		o.getRotate(),
            
        	//PROPRIEDADES
    		o.getProxEntId(),
    		o.getTipoSecaoTubulacao(),
    		o.getCategoriaTubulacaoId(),
    		o.getDescricaoCategoriaTubulacao(),
    		o.getQtdTubulacao(),
    		o.getDiametroTubulacaoMeter(),
    		o.getLargura(),
    		o.getAltura(),
    		o.getProfundidade(),
    		o.getCt(),
    		//
    		o.getCb(),
    		o.getCotaSaida(),
    		o.getDeclividade(),
    		o.getComprTubulacao(),
    		o.getComprHorizTubulacao(),
    		o.getComprVertTubulacao() );
	    
	}
    
	@Override
	public void init(DbUtil o)
	{
		super.initEntity(o);
		
		this.setShapeName( o.getNextStr() );
		this.setShapeFileName( o.getNextStr() );
		this.setShapeDefaultZ( o.getNextDbl() );
		//
		this.setPtInsX( o.getNextDbl() );
		this.setPtInsY( o.getNextDbl() );
		this.setPtInsZ( o.getNextDbl() );
		//
		this.setRotate( o.getNextDbl() );
        
    	//PROPRIEDADES
		this.setProxEntId( o.getNextInt() );
		this.setTipoSecaoTubulacao( o.getNextStr() );
		this.setCategoriaTubulacaoId( o.getNextInt() );
		this.setDescricaoCategoriaTubulacao( o.getNextStr() );
		this.setQtdTubulacao( o.getNextInt() );
		this.setDiametroTubulacaoMeter( o.getNextDbl() );
		this.setLargura( o.getNextDbl() );
		this.setAltura( o.getNextDbl() );
		this.setProfundidade( o.getNextDbl() );
		this.setCt( o.getNextDbl() );
		//
		this.setCb( o.getNextDbl() );
		this.setCotaSaida( o.getNextDbl() );
		this.setDeclividade( o.getNextDbl() );
		this.setComprTubulacao( o.getNextDbl() );
		this.setComprHorizTubulacao( o.getNextDbl() );
		this.setComprVertTubulacao( o.getNextDbl() );
	    
	}
	
	/* TO_CADxxx */
	
	@Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
    	CadDocumentDef doc = oBlkDef.getDocument();
    	
    	CadPontoDrenagem o = null;
    	
		//-- SHAPE
		ShapeTable shapeTable = doc.getShapeTable();
		
		Shape oShape = shapeTable.getShape(this.shapeName);
		if(oShape != null) {
			
			CadEntity oEnt = oBlkDef.getEntity(this.proxEntId);  
			if(oEnt != null) {
				CadCaixaInspecaoDrenagem oCI = (CadCaixaInspecaoDrenagem)oEnt; 

				o = (CadPontoDrenagem)super.toCadObject(oBlkDef, this.getClass());
		    	o.init(
		    		oCI,
		    		//
					this.getPtInsX(), 
					this.getPtInsY(), 
					this.getPtInsZ(),
					//
			    	this.getRotate(),
			    	//
		    		oShape,
		    		//
		    		this.getLargura(),
		    		this.getAltura(),
		    		this.getProfundidade() );
			}
		}
	    return o;
	}

    /* Getters/Setters */

	public String getShapeName() {
		return shapeName;
	}

	public void setShapeName(String shapeName) {
		this.shapeName = shapeName;
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

	public double getRotate() {
		return rotate;
	}

	public void setRotate(double rotate) {
		this.rotate = rotate;
	}

	public String getTipoSecaoTubulacao() {
		return tipoSecaoTubulacao;
	}

	public void setTipoSecaoTubulacao(String tipoSecaoTubulacao) {
		this.tipoSecaoTubulacao = tipoSecaoTubulacao;
	}

	public int getCategoriaTubulacaoId() {
		return categoriaTubulacaoId;
	}

	public void setCategoriaTubulacaoId(int categoriaTubulacaoId) {
		this.categoriaTubulacaoId = categoriaTubulacaoId;
	}

	public String getDescricaoCategoriaTubulacao() {
		return descricaoCategoriaTubulacao;
	}

	public void setDescricaoCategoriaTubulacao(String descricaoCategoriaTubulacao) {
		this.descricaoCategoriaTubulacao = descricaoCategoriaTubulacao;
	}

	public int getQtdTubulacao() {
		return qtdTubulacao;
	}

	public void setQtdTubulacao(int qtdTubulacao) {
		this.qtdTubulacao = qtdTubulacao;
	}

	public double getDiametroTubulacaoMeter() {
		return diametroTubulacaoMeter;
	}

	public void setDiametroTubulacaoMeter(double diametroTubulacaoMeter) {
		this.diametroTubulacaoMeter = diametroTubulacaoMeter;
	}

	public double getLargura() {
		return largura;
	}

	public void setLargura(double largura) {
		this.largura = largura;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	public double getProfundidade() {
		return profundidade;
	}

	public void setProfundidade(double profundidade) {
		this.profundidade = profundidade;
	}

	public double getCt() {
		return ct;
	}

	public void setCt(double ct) {
		this.ct = ct;
	}

	public double getCb() {
		return cb;
	}

	public void setCb(double cb) {
		this.cb = cb;
	}

	public double getCotaSaida() {
		return cotaSaida;
	}

	public void setCotaSaida(double cotaSaida) {
		this.cotaSaida = cotaSaida;
	}

	public double getDeclividade() {
		return declividade;
	}

	public void setDeclividade(double declividade) {
		this.declividade = declividade;
	}

	public double getComprTubulacao() {
		return comprTubulacao;
	}

	public void setComprTubulacao(double comprTubulacao) {
		this.comprTubulacao = comprTubulacao;
	}

	public double getComprHorizTubulacao() {
		return comprHorizTubulacao;
	}

	public void setComprHorizTubulacao(double comprHorizTubulacao) {
		this.comprHorizTubulacao = comprHorizTubulacao;
	}

	public double getComprVertTubulacao() {
		return comprVertTubulacao;
	}

	public void setComprVertTubulacao(double comprVertTubulacao) {
		this.comprVertTubulacao = comprVertTubulacao;
	}

	public int getProxEntId() {
		return proxEntId;
	}

	public void setProxEntId(int proxEntId) {
		this.proxEntId = proxEntId;
	}

	public String getShapeFileName() {
		return shapeFileName;
	}

	public void setShapeFileName(String shapeFileName) {
		this.shapeFileName = shapeFileName;
	}

	public double getShapeDefaultZ() {
		return shapeDefaultZ;
	}

	public void setShapeDefaultZ(double shapeDefaultZ) {
		this.shapeDefaultZ = shapeDefaultZ;
	}
    
}
