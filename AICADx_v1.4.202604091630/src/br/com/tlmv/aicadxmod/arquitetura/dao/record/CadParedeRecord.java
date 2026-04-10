/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadParedeRecord.java
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

package br.com.tlmv.aicadxmod.arquitetura.dao.record;

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
import br.com.tlmv.aicadxmod.arquitetura.cad.CadParede;

public class CadParedeRecord extends BaseEntityRecord
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
	
	public static final String sqlTableName = "cad_parede";

	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("cad_parede_id", 	AppDefs.TAG_SQLTYPE_INT),	
		new SqlColumnVO("tipo", 			AppDefs.TAG_SQLTYPE_INT),	
		new SqlColumnVO("altura",			AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("largura", 			AppDefs.TAG_SQLTYPE_DBL),		
		new SqlColumnVO("pti_x", 			AppDefs.TAG_SQLTYPE_DBL),		
		new SqlColumnVO("pti_y", 			AppDefs.TAG_SQLTYPE_DBL),		
		new SqlColumnVO("pti_z", 			AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("ptf_x", 			AppDefs.TAG_SQLTYPE_DBL),		
		new SqlColumnVO("ptf_y", 			AppDefs.TAG_SQLTYPE_DBL),		
		new SqlColumnVO("ptf_z", 			AppDefs.TAG_SQLTYPE_DBL)		
		
	};
	
//Private
	private int cadParedeId;
	private int tipo;
	private double altura;
	private double largura;
	private double ptIX;
	private double ptIY;
	private double ptIZ;
	private double ptFX;
	private double ptFY;
	private double ptFZ;
	
//Public
    
    public CadParedeRecord()
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
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL );
    }

    public CadParedeRecord(CadParede o)
    {
    	this.init(o);
    }
    
    public CadParedeRecord(ResultSet rs)
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
		int cadParedeId,
		int tipo,
		double altura,
		double largura,
		double ptIX,
		double ptIY,
		double ptIZ,
		double ptFX,
		double ptFY,
		double ptFZ )
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

		this.cadParedeId = cadParedeId;
		this.tipo = tipo;
		this.altura = altura;
		this.largura = largura;
    	this.ptIX = ptIX;
    	this.ptIY = ptIY;
    	this.ptIZ = ptIZ;
    	this.ptFX = ptFX;
    	this.ptFY = ptFY;
    	this.ptFZ = ptFZ;
    }
    
    public void init(CadParede o)
    {    	
		int cadParedeId = o.getObjectId();

		String strReference = AppDefs.LAYER_0;
    	String strLevelName = AppDefs.DEFAULT_LEVELNAME;

    	CadLayerDef oLayer = o.getLayer();
    	if(oLayer != null)
    		strReference = oLayer.getReference();
    	
    	CadLevel oLevel = o.getLevel();
    	if(oLevel != null)
    		strLevelName = oLevel.getLevelLocalName();

		GeomPoint3d ptI = new GeomPoint3d( o.getPtI() );
		GeomPoint3d ptF = new GeomPoint3d( o.getPtF() );
		
		double ptIX = ptI.getX();
		double ptIY = ptI.getY();
		double ptIZ = ptI.getZ();

		double ptFX = ptF.getX();
		double ptFY = ptF.getY();
		double ptFZ = ptF.getZ();
    	
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
    		strReference,
    		strLevelName,
    		//
    		o.getZLevel(),
    		//
    		cadParedeId,
    		o.getTipo(),
    		o.getAltura(),
    		o.getLargura(),
    		ptIX,
    		ptIY,
    		ptIZ,
    		ptFX,
    		ptFY,
    		ptFZ );
    }

    @Override
	public void init(DbUtil o)
	{
		super.initObj(o);
		
	    this.setCadParedeId( o.getNextInt() );
	    this.setTipo( o.getNextInt() );
		this.setAltura( o.getNextDbl() );
		this.setLargura( o.getNextDbl() );
		this.setPtIX( o.getNextDbl() );
		this.setPtIY( o.getNextDbl() );
		this.setPtIZ( o.getNextDbl() );
		this.setPtFX( o.getNextDbl() );
		this.setPtFY( o.getNextDbl() );
		this.setPtFZ( o.getNextDbl() );		
		
	}
	
	/* TO_CADxxx */
	
    @Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
    	CadDocumentDef doc = oBlkDef.getDocument();
    	
    	CadParede o = new CadParede(
			oBlkDef, 
			super.getCadLayerDef(doc), 
			super.getCadLevel(doc), 
			super.getZLevel(), 
			false );

    	o.init(
			this.getTipo(),
			this.getAltura(),
			this.getLargura(),
			this.getPtIX(),
			this.getPtIY(),
			this.getPtIZ(),
			this.getPtFX(),
			this.getPtFY(),
			this.getPtFZ() ); 
    	o.addAcabamentoDef(AppDefs.WALLFINISHDEF_CHAPISCO_EMBOCO_REBOCO_PINTURA);
		return o;
	}

    /* Getters/Setters */

	public double getPtIX() {
		return ptIX;
	}

	public void setPtIX(double ptIX) {
		this.ptIX = ptIX;
	}

	public double getPtIY() {
		return ptIY;
	}

	public void setPtIY(double ptIY) {
		this.ptIY = ptIY;
	}

	public double getPtIZ() {
		return ptIZ;
	}

	public void setPtIZ(double ptIZ) {
		this.ptIZ = ptIZ;
	}

	public double getPtFX() {
		return ptFX;
	}

	public void setPtFX(double ptFX) {
		this.ptFX = ptFX;
	}

	public double getPtFY() {
		return ptFY;
	}

	public void setPtFY(double ptFY) {
		this.ptFY = ptFY;
	}

	public double getPtFZ() {
		return ptFZ;
	}

	public void setPtFZ(double ptFZ) {
		this.ptFZ = ptFZ;
	}

	public int getCadParedeId() {
		return cadParedeId;
	}

	public void setCadParedeId(int cadParedeId) {
		this.cadParedeId = cadParedeId;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	public double getLargura() {
		return largura;
	}

	public void setLargura(double largura) {
		this.largura = largura;
	}
    
}
