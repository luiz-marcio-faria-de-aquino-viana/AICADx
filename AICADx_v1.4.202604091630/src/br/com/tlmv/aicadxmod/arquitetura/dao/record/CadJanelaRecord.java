/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadJanelaRecord.java
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
import br.com.tlmv.aicadxapp.dao.record.BaseEntityRecord;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadJanela;
import br.com.tlmv.aicadxmod.arquitetura.cad.CadParede;

public class CadJanelaRecord extends BaseEntityRecord
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
	
	public static final String sqlTableName = "cad_janela";

	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("cad_parede_id", 			AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("tipo", 			AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("altura_piso",		 	AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("altura", 			AppDefs.TAG_SQLTYPE_STR),		
		new SqlColumnVO("largura", 		AppDefs.TAG_SQLTYPE_STR),		
		new SqlColumnVO("espessura", 		AppDefs.TAG_SQLTYPE_INT),		
		new SqlColumnVO("dist", 		AppDefs.TAG_SQLTYPE_INT),		
		new SqlColumnVO("direcao", 		AppDefs.TAG_SQLTYPE_DBL)		
		
	};
	
//Private
	private int cadParedeId;
	private int tipo;
	private double alturaPiso;
	private double altura;
	private double largura;
	private double espessura;
	private double dist;
	private int direcao;
	
//Public
    
    public CadJanelaRecord()
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
    		AppDefs.NULL_INT );
    }
    
	public CadJanelaRecord(CadJanela o)
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
			
		// PAREDE
		//
		CadParede oParede = o.getParede();
		int paredeId = oParede.getObjectId();

		String strIsDeleted = ( o.isDeleted() ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO );
		
		String strIsLocked = ( o.isLocked() ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO );
		
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
			paredeId,
			o.getTipo(),
			o.getAlturaPiso(),
			o.getAltura(),
			o.getLargura(),
			o.getEspessura(),
			o.getDist(),
			o.getDirecao() );	
	}
	
	public CadJanelaRecord(ResultSet rs)
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
		double alturaPiso,
		double altura,
		double largura,
		double espessura,
		double dist,
		int direcao )
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
		this.alturaPiso = alturaPiso;
		this.altura = altura;
		this.largura = largura;
		this.espessura = espessura;
		this.dist = dist;
		this.direcao = direcao;
    }

	@Override
    public void init(DbUtil o)
    {
    	super.initObj(o);
    	
		this.setCadParedeId( o.getNextInt() );
		this.setTipo( o.getNextInt() );
		this.setAlturaPiso( o.getNextDbl() );
		this.setAltura( o.getNextDbl() );
		this.setLargura( o.getNextDbl() );
		this.setEspessura( o.getNextDbl() );
		this.setDist( o.getNextDbl() );
		this.setDirecao( o.getNextInt() );
    }
    
	/* TO_CADxxx */
	
    @Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
    	CadJanela o = null;
    	
		CadParede oParede = (CadParede)oBlkDef.getEntity(this.getCadParedeId());
		if(oParede != null) {
	    	CadDocumentDef doc = oBlkDef.getDocument();
	    	
	    	o = new CadJanela(
				oBlkDef, 
				super.getCadLayerDef(doc), 
				super.getCadLevel(doc), 
				super.getZLevel(), 
				false );
			
	    	o.init(
				this.getTipo(),
				this.getAltura(),
    			this.getLargura(),
    			this.getEspessura(),
    			oParede,
    			this.getDist(),
    			this.getDirecao(),
    			this.getAlturaPiso(),
    			AppDefs.WINDOWFINISHDEF_BASIC ); 
		}
	    return o;
	}

    /* Getters/Setters */

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

	public double getAlturaPiso() {
		return alturaPiso;
	}

	public void setAlturaPiso(double alturaPiso) {
		this.alturaPiso = alturaPiso;
	}

	public double getEspessura() {
		return espessura;
	}

	public void setEspessura(double espessura) {
		this.espessura = espessura;
	}

	public double getDist() {
		return dist;
	}

	public void setDist(double dist) {
		this.dist = dist;
	}

	public int getDirecao() {
		return direcao;
	}

	public void setDirecao(int direcao) {
		this.direcao = direcao;
	}
    
}
