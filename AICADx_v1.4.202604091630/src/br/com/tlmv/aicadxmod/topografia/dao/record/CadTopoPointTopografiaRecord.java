/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadTopoPointRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 28/03/2025
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

package br.com.tlmv.aicadxmod.topografia.dao.record;

import java.sql.ResultSet;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.LevelTable;
import br.com.tlmv.aicadxapp.dao.record.BaseEntityRecord;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;
import br.com.tlmv.aicadxmod.topografia.cad.CadTopoPointTopografia;

public class CadTopoPointTopografiaRecord extends BaseEntityRecord
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
	public static final String sqlTableName = "cad_topo_point_topografia";
	
	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("ponto_id", 					AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("categoria_ponto_id", 			AppDefs.TAG_SQLTYPE_INT),
		new SqlColumnVO("descricao_categoria_ponto",    AppDefs.TAG_SQLTYPE_STR),
		//
		new SqlColumnVO("nome", 						AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("altura_antena", 				AppDefs.TAG_SQLTYPE_STR),
		new SqlColumnVO("dataAtualizacao",          	AppDefs.TAG_SQLTYPE_STR),
		//
		new SqlColumnVO("pt_ins_x", 					AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("pt_ins_y", 					AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("pt_ins_z", 					AppDefs.TAG_SQLTYPE_DBL)
		
	};
	
//Private
    private int pontoId;
    private int categoriaPontoId;
    private String descricaoCategoriaPonto;
    private String nome;
    private double alturaAntena;
    private String dataAtualizacao;
    //
    private double ptInsX;
    private double ptInsY;
    private double ptInsZ;
    
//Public
    
    public CadTopoPointTopografiaRecord()
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
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_STR,
    		//
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL );
    }
    
    public CadTopoPointTopografiaRecord(CadTopoPointTopografia o)
    {
    	this.init(o);
    }
    
    public CadTopoPointTopografiaRecord(ResultSet rs)
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
	    int pontoId,
	    int categoriaPontoId,
	    String descricaoCategoriaPonto,
	    String nome,
	    double alturaAntena,
	    String dataAtualizacao,
	    //
	    double ptInsX,
	    double ptInsY,
	    double ptInsZ )
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

    	this.pontoId = pontoId;
    	this.categoriaPontoId = categoriaPontoId;
    	this.descricaoCategoriaPonto = descricaoCategoriaPonto;
    	this.nome = nome;
    	this.alturaAntena = alturaAntena;
    	this.dataAtualizacao = dataAtualizacao;
    	//
    	this.ptInsX = ptInsX;
    	this.ptInsY = ptInsY;
    	this.ptInsZ = ptInsZ;
    }
    
    public void init(CadTopoPointTopografia o)
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
    	
    	GeomPoint3d oPtIns = o.getPt();
    	
    	String strIsDeleted = StringUtil.fromBoolToStr( o.isDeleted() );
    	
    	String strIsLocked = StringUtil.fromBoolToStr( o.isDeleted() );
    	
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
			o.getPontoId(),
			o.getCategoriaPontoId(),
			o.getDescricaoCategoriaPonto(),
			o.getNome(),
			o.getAlturaAntena(),
			o.getDataAtualizacao(),
			//
			oPtIns.getX(),
			oPtIns.getY(),
			oPtIns.getZ() );
    	
    }
    
    @Override
    public void init(DbUtil o)
    {
    	super.initEntity(o);
    	
		this.setPontoId( o.getNextInt() );
    	this.setCategoriaPontoId( o.getNextInt() );
    	this.setDescricaoCategoriaPonto( o.getNextStr() );
    	this.setNome( o.getNextStr() );
    	this.setAlturaAntena( o.getNextDbl() );
    	this.setDataAtualizacao( o.getNextStr() );
    	//
		this.setPtInsX( o.getNextDbl() );
		this.setPtInsY( o.getNextDbl() );
		this.setPtInsZ( o.getNextDbl() );
    }
	
	/* TO_CADxxx */

    @Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
		CadDocumentDef doc = oBlkDef.getDocument();

		String reference = this.getReference();
		String levelName = this.getLevelName();

		// LAYER_DEF
		//
		LayerTable oLayTbl = doc.getLayerTable();
		CadLayerDef oLayer = oLayTbl.getLayerDefByRef(reference);

		// LEVEL
		//
		LevelTable oLevelTbl = doc.getLevelTable();
		CadLevel oLevel = oLevelTbl.getLevel(levelName);    	
    	
		CadTopoPointTopografia o = CadTopoPointTopografia.create(
			oBlkDef,
			oLayer, 
			oLevel,
			this.getPontoId(),
			this.getCategoriaPontoId(),
			this.getDescricaoCategoriaPonto(),
			this.getNome(),
			this.getAlturaAntena(),
			this.getDataAtualizacao(),
			this.getPtInsX(), 
			this.getPtInsY(), 
			this.getPtInsZ() );
		o.setObjectId(this.getObjectId());

		return o;
	}

    /* Getters/Setters */

	public int getPontoId() {
		return pontoId;
	}

	public void setPontoId(int pontoId) {
		this.pontoId = pontoId;
	}

	public int getCategoriaPontoId() {
		return categoriaPontoId;
	}

	public void setCategoriaPontoId(int categoriaPontoId) {
		this.categoriaPontoId = categoriaPontoId;
	}

	public String getDescricaoCategoriaPonto() {
		return descricaoCategoriaPonto;
	}

	public void setDescricaoCategoriaPonto(String descricaoCategoriaPonto) {
		this.descricaoCategoriaPonto = descricaoCategoriaPonto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getAlturaAntena() {
		return alturaAntena;
	}

	public void setAlturaAntena(double alturaAntena) {
		this.alturaAntena = alturaAntena;
	}

	public String getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(String dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
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
    
}
