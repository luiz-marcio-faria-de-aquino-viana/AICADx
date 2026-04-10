/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadPerfilDrenagemRecord.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 15/06/2025
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
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.dao.record.BaseEntityRecord;
import br.com.tlmv.aicadxapp.utils.DbUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.SqlColumnVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadPerfilDrenagem;

public class CadPerfilDrenagemRecord extends BaseEntityRecord 
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
	public static final String sqlTableName = "cad_perfil_drenagem";
	
	public static final SqlColumnVO[] sqlColumn = {
		new SqlColumnVO("ptins_x", 						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("ptins_y", 						AppDefs.TAG_SQLTYPE_DBL),		
		new SqlColumnVO("ptins_z", 						AppDefs.TAG_SQLTYPE_DBL),		
		//
		new SqlColumnVO("trecho_drenagem_id", 			AppDefs.TAG_SQLTYPE_INT),		
		new SqlColumnVO("nome_trecho_drenagem", 		AppDefs.TAG_SQLTYPE_STR),
		//
		new SqlColumnVO("x_min", 						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("y_min", 						AppDefs.TAG_SQLTYPE_DBL),		
		new SqlColumnVO("x_max", 						AppDefs.TAG_SQLTYPE_DBL),
		new SqlColumnVO("y_max", 						AppDefs.TAG_SQLTYPE_DBL),		
		//
		new SqlColumnVO("w", 							AppDefs.TAG_SQLTYPE_DBL),		
		new SqlColumnVO("h", 							AppDefs.TAG_SQLTYPE_DBL)		

	};
	
//Private
	private double ptInsX;
	private double ptInsY;
	private double ptInsZ;
	//
    private int trechoDrenagemId;
    private String nomeTrechoDrenagem;
    //
    private double xMin;
    private double yMin;
    private double xMax;
    private double yMax;
    //
    private double w;
    private double h;
	
//Public
	
	public CadPerfilDrenagemRecord()
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
			AppDefs.NULL_INT, 
			AppDefs.NULL_STR,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL );
	}

	public CadPerfilDrenagemRecord(CadPerfilDrenagem o)
	{
		this.init(o);
	}
	
	public CadPerfilDrenagemRecord(ResultSet rs)
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
	    int trechoDrenagemId,
	    String nomeTrechoDrenagem,
	    //
	    double xMin,
	    double yMin,
	    double xMax,
	    double yMax,
	    //
	    double w,
	    double h )
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
		this.trechoDrenagemId = trechoDrenagemId;
		this.nomeTrechoDrenagem = nomeTrechoDrenagem;
		//
		this.xMin = xMin;
		this.yMin = yMin;
		this.xMax = xMax;
		this.yMax = yMax;
		//
		this.w = w;
		this.h = h;
	}

	public void init(CadPerfilDrenagem o)
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

		double ptInsX = ptIns.getX();
		double ptInsY = ptIns.getY();
		double ptInsZ = ptIns.getZ();
		
		String strIsDeleted = ( o.isDeleted() ) ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO; 
		
		String strIsLocked = ( o.isLocked() ) ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO; 
		
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
			ptInsX,
			ptInsY,
			ptInsZ,
			//
		    o.getTrechoDrenagemId(),
		    o.getNomeTrechoDrenagem(),
			//
		    o.getXMin(),
		    o.getYMin(),
		    o.getXMax(),
		    o.getYMax(),
			//
		    o.getW(),
		    o.getH() );
	}
	
	@Override
	public void init(DbUtil o)
	{
		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE3_MASC);

		super.initEntity(o);
		
		this.setPtInsX( o.getNextDbl() );
		this.setPtInsY( o.getNextDbl() );
		this.setPtInsZ( o.getNextDbl() );
		//
		this.setTrechoDrenagemId( o.getNextInt() );
		this.setNomeTrechoDrenagem( o.getNextStr() );
		//
		this.setXMin( o.getNextDbl() );
		this.setYMin( o.getNextDbl() );
		this.setXMax( o.getNextDbl() );
		this.setYMax( o.getNextDbl() );
		//
		this.setW( o.getNextDbl() );
		this.setH( o.getNextDbl() );
	}
	
	/* TO_CADxxx */

	@Override
	public CadObject toCadObject(CadBlockDef oBlkDef) {
		CadDocumentDef doc = oBlkDef.getDocument();
		
		boolean bLocked = StringUtil.fromStrToBool(this.getIsLocked());
	
		CadPerfilDrenagem o = new CadPerfilDrenagem(
				oBlkDef, 
				super.getCadLayerDef(doc), 
				super.getCadLevel(doc), 
				this.getZLevel(), 
				bLocked );
			
    	o.init(
			new GeomPoint3d( 
				this.getPtInsX(), 
				this.getPtInsY(), 
				this.getPtInsZ() ),
			this.getTrechoDrenagemId(),
			this.getNomeTrechoDrenagem() ); 
	    return o;
	}
	
	/* Getters/Setters */

	public int getTrechoDrenagemId() {
		return trechoDrenagemId;
	}

	public void setTrechoDrenagemId(int trechoDrenagemId) {
		this.trechoDrenagemId = trechoDrenagemId;
	}

	public String getNomeTrechoDrenagem() {
		return nomeTrechoDrenagem;
	}

	public void setNomeTrechoDrenagem(String nomeTrechoDrenagem) {
		this.nomeTrechoDrenagem = nomeTrechoDrenagem;
	}

	public double getXMin() {
		return xMin;
	}

	public void setXMin(double xMin) {
		this.xMin = xMin;
	}

	public double getYMin() {
		return yMin;
	}

	public void setYMin(double yMin) {
		this.yMin = yMin;
	}

	public double getXMax() {
		return xMax;
	}

	public void setXMax(double xMax) {
		this.xMax = xMax;
	}

	public double getYMax() {
		return yMax;
	}

	public void setYMax(double yMax) {
		this.yMax = yMax;
	}

	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
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
