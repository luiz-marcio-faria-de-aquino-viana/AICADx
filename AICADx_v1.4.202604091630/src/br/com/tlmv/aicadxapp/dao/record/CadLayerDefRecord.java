/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadLayerDefRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 04/06/2025
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

package br.com.tlmv.aicadxapp.dao.record;

import java.sql.ResultSet;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.BorderStrokeVO;
import br.com.tlmv.aicadxapp.vo.ColorVO;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;

public class CadLayerDefRecord extends BaseObjectRecord
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
	public static final String sqlTableName = "cad_layer_def";

	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("layer_name", 			AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("layer_reference", 		AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("ltype_name", 			AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("ltype_index",			AppDefs.TAG_SQLTYPE_INT),	
		new SqlColumnVO("color_name",			AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("color_index",			AppDefs.TAG_SQLTYPE_INT),	
		new SqlColumnVO("line_weight",			AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("min_dist",				AppDefs.TAG_SQLTYPE_DBL),	
		new SqlColumnVO("categoria_id",			AppDefs.TAG_SQLTYPE_INT),	
		new SqlColumnVO("descricao_categoria",	AppDefs.TAG_SQLTYPE_STR),	
		new SqlColumnVO("is_layer_on",			AppDefs.TAG_SQLTYPE_BOOL)
		
	};
	
//Private
	private String layerName;
	private String layerReference;
	private String ltypeName;
	private int ltypeIndex;
	private String colorName;
	private int colorIndex;
	private double lineWeight;
	private double minDist;
	private int categoriaId;
	private String descricaoCategoria;
	private String strIsLayerOn;
	
//Public
    
    public CadLayerDefRecord()
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
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR,
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_STR,
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_DBL,
    		AppDefs.NULL_INT, 
    		AppDefs.NULL_STR,
    		AppDefs.NULL_STR );
    }

    public CadLayerDefRecord(CadLayerDef o)
    {
    	this.init(o);
    }

    public CadLayerDefRecord(ResultSet rs)
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
		String layerName,
		String layerReference,
		String ltypeName,
		int ltypeIndex,
		String colorName,
		int colorIndex,
		double lineWeight,
		double minDist,
		int categoriaId,
		String descricaoCategoria,
		String strIsLayerOn )
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

		this.layerName = layerName;
		this.layerReference = layerReference;
		this.ltypeName = ltypeName;
		this.ltypeIndex = ltypeIndex;
		this.colorName = colorName;
		this.colorIndex = colorIndex;
		this.lineWeight = lineWeight;
		this.minDist = minDist;
		this.categoriaId = categoriaId;
		this.descricaoCategoria = descricaoCategoria;
		this.strIsLayerOn = strIsLayerOn; 
    
    }
    
    public void init(CadLayerDef o)
    {
    	BorderStrokeVO oLtype = o.getLtype();
    	
    	ColorVO oColor = o.getColor();
    	
    	String strIsDeleted = StringUtil.fromBoolToStr( o.isDeleted() );
    	
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
    		//
			o.getName(),
			o.getReference(),
			oLtype.getName(),
			oLtype.getBorderId(),
			oColor.getName(),
    		oColor.getColorIndex(),
    		o.getLineWeight(),
    		o.getMinDist(),
    		o.getCategoriaId(),
    		o.getDescricaoCategoria(),
    		strIsLayerOn ); 
    
    }
    
    @Override
    public void init(DbUtil o)
    {
    	super.initObj(o);

		this.setLayerName( o.getNextStr() );
		this.setLayerReference( o.getNextStr() );
		this.setLtypeName( o.getNextStr() );
		this.setLtypeIndex( o.getNextInt() );
		this.setColorName( o.getNextStr() );
		this.setColorIndex( o.getNextInt() );
		this.setLineWeight( o.getNextDbl() );
		this.setMinDist( o.getNextDbl() );
		this.setCategoriaId( o.getNextInt() );
		this.setDescricaoCategoria( o.getNextStr() );
		this.setIsLayerOn( o.getNextStr() );
		//
		this.setIsEntityObject( o.getNextStr() );
		this.setIsDeleted( o.getNextStr() );
		
    }
	
	/* TO_CADxxx */

    @Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
    	CadDocumentDef doc = oBlkDef.getDocument();
    	
    	CadLayerDef o = new CadLayerDef(doc);

		o.init(
			this.getLayerName(),
			this.getLayerReference(),
			this.getLtypeIndex(),
			this.getColorIndex(),
			this.getLineWeight(),
			this.getMinDist(),
			this.getCategoriaId(),
			this.getDescricaoCategoria(),
			StringUtil.fromStrToBool(this.getIsLayerOn()) ); 
    	o.setObjectId(this.getObjectId());
	    return o;
	}

    /* Getters/Setters */

	public String getLtypeName() {
		return ltypeName;
	}

	public void setLtypeName(String ltypeName) {
		this.ltypeName = ltypeName;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public double getLineWeight() {
		return lineWeight;
	}

	public void setLineWeight(double lineWeight) {
		this.lineWeight = lineWeight;
	}

	public double getMinDist() {
		return minDist;
	}

	public void setMinDist(double minDist) {
		this.minDist = minDist;
	}

	public int getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(int categoriaId) {
		this.categoriaId = categoriaId;
	}

	public String getDescricaoCategoria() {
		return descricaoCategoria;
	}

	public void setDescricaoCategoria(String descricaoCategoria) {
		this.descricaoCategoria = descricaoCategoria;
	}

	public int getLtypeIndex() {
		return ltypeIndex;
	}

	public void setLtypeIndex(int ltypeIndex) {
		this.ltypeIndex = ltypeIndex;
	}

	public int getColorIndex() {
		return colorIndex;
	}

	public void setColorIndex(int colorIndex) {
		this.colorIndex = colorIndex;
	}

	public String getIsLayerOn() {
		return strIsLayerOn;
	}

	public void setIsLayerOn(String strIsLayerOn) {
		this.strIsLayerOn = strIsLayerOn;
	}

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}

	public String getLayerReference() {
		return layerReference;
	}

	public void setLayerReference(String layerReference) {
		this.layerReference = layerReference;
	}
    
}
