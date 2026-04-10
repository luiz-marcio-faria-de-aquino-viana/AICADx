/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadPipeConexao.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/12/2025
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

package br.com.tlmv.aicadxapp.cad;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Stroke;
import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.ecache.DrawCache;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeResult;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfCadEntity;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;

public class CadPipeConexao  extends CadEntity 
{
//Private
    private int numeroConexao 			 	= AppDefs.NULL_INT;
    private int tipoConexao				 	= AppDefs.NULL_INT;
    private String descricaoTipoConexao		= AppDefs.NULL_STR;
    private int objId_1						= AppDefs.NULL_INT;
    private int objId_2 					= AppDefs.NULL_INT;
    private int objId_3 					= AppDefs.NULL_INT;
    private int objId_4 					= AppDefs.NULL_INT;
    private double diameterMili_1			= AppDefs.NULL_DBL;
    private double diameterMili_2			= AppDefs.NULL_DBL;
    private double diameterMili_3			= AppDefs.NULL_DBL;
    private double diameterMili_4			= AppDefs.NULL_DBL;
    private GeomPoint3d ptIns				= null;
    private GeomPoint3d ptDir				= null;
    
//Public

    public CadPipeConexao(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_BIMPIPECONEXAO, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */
	
	public void init(
	    int numeroConexao,
	    int tipoConexao,
	    int objId_1,
	    int objId_2,
	    int objId_3,
	    int objId_4,
	    double diameterMili_1,
	    double diameterMili_2,
	    double diameterMili_3,
	    double diameterMili_4,
	    GeomPoint3d ptIns,
	    GeomPoint3d ptDir ) 
	{
		this.numeroConexao = numeroConexao;
		this.tipoConexao = tipoConexao;
		this.descricaoTipoConexao = AppDefs.arrPipeConnectionType[this.tipoConexao];
		this.objId_1 = objId_1;
		this.objId_2 = objId_2;
		this.objId_3 = objId_3;
		this.objId_4 = objId_4;
		this.diameterMili_1 = diameterMili_1;
		this.diameterMili_2 = diameterMili_2;
		this.diameterMili_3 = diameterMili_3;
		this.diameterMili_4 = diameterMili_4;
		this.ptIns = new GeomPoint3d(ptIns);
		this.ptDir = new GeomPoint3d(ptDir); 
    }

	@Override
	public void init(ICadObject o) {
		CadPipeConexao other = (CadPipeConexao)o;

    	this.init(
			other.getNumeroConexao(),
			other.getTipoConexao(),
			other.getObjId_1(),
			other.getObjId_2(),
			other.getObjId_3(),
			other.getObjId_4(),
			other.getDiameterMili_1(),
			other.getDiameterMili_2(),
			other.getDiameterMili_3(),
			other.getDiameterMili_4(),
			other.getPtIns(),
			other.getPtDir() );    	
	}
	
	/* CREATE */
	
	public static CadPipeConexao createNewPlug(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel, 
	    int numeroConexao,
	    int objId_1,
	    double diameterMili_1,
	    GeomPoint3d ptIns,
	    GeomPoint3d ptDir )
	{
    	CadPipeConexao o = new CadPipeConexao(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(
			numeroConexao,
		    AppDefs.pipeConnectionTypePlugVal,
		    objId_1,
		    AppDefs.NULL_INT,
		    AppDefs.NULL_INT,
		    AppDefs.NULL_INT,
		    diameterMili_1,
		    0.0,
		    0.0,
		    0.0,
		    ptIns,
		    ptDir );
    	return o;
    }
	
	public static CadPipeConexao createNewLuva(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel, 
	    int numeroConexao,
	    int objId_1,
	    int objId_2,
	    double diameterMili_1,
	    double diameterMili_2,
	    GeomPoint3d ptIns,
	    GeomPoint3d ptDir )
	{
    	CadPipeConexao o = new CadPipeConexao(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(
			numeroConexao,
		    AppDefs.pipeConnectionTypeLuvaVal,
		    objId_1,
		    objId_2,
		    AppDefs.NULL_INT,
		    AppDefs.NULL_INT,
		    diameterMili_1,
		    diameterMili_2,
		    0.0,
		    0.0,
		    ptIns,
		    ptDir );
    	return o;
    }
	
	public static CadPipeConexao createNewJoelho45(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel, 
	    int numeroConexao,
	    int objId_1,
	    int objId_2,
	    double diameterMili_1,
	    double diameterMili_2,
	    GeomPoint3d ptIns,
	    GeomPoint3d ptDir )
	{
    	CadPipeConexao o = new CadPipeConexao(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(
			numeroConexao,
		    AppDefs.pipeConnectionTypeJoelho45Val,
		    objId_1,
		    objId_2,
		    AppDefs.NULL_INT,
		    AppDefs.NULL_INT,
		    diameterMili_1,
		    diameterMili_2,
		    0.0,
		    0.0,
		    ptIns,
		    ptDir );
    	return o;
    }
	
	public static CadPipeConexao createNewJoelho90(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel, 
	    int numeroConexao,
	    int objId_1,
	    int objId_2,
	    double diameterMili_1,
	    double diameterMili_2,
	    GeomPoint3d ptIns,
	    GeomPoint3d ptDir )
	{
    	CadPipeConexao o = new CadPipeConexao(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(
			numeroConexao,
		    AppDefs.pipeConnectionTypeJoelho90Val,
		    objId_1,
		    objId_2,
		    AppDefs.NULL_INT,
		    AppDefs.NULL_INT,
		    diameterMili_1,
		    diameterMili_2,
		    0.0,
		    0.0,
		    ptIns,
		    ptDir );
    	return o;
    }
	
	public static CadPipeConexao createNewCurva45(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel, 
	    int numeroConexao,
	    int objId_1,
	    int objId_2,
	    double diameterMili_1,
	    double diameterMili_2,
	    GeomPoint3d ptIns,
	    GeomPoint3d ptDir )
	{
    	CadPipeConexao o = new CadPipeConexao(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(
			numeroConexao,
		    AppDefs.pipeConnectionTypeCurva45Val,
		    objId_1,
		    objId_2,
		    AppDefs.NULL_INT,
		    AppDefs.NULL_INT,
		    diameterMili_1,
		    diameterMili_2,
		    0.0,
		    0.0,
		    ptIns,
		    ptDir );
    	return o;
    }
	
	public static CadPipeConexao createNewCurva90(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel, 
	    int numeroConexao,
	    int objId_1,
	    int objId_2,
	    double diameterMili_1,
	    double diameterMili_2,
	    GeomPoint3d ptIns,
	    GeomPoint3d ptDir )
	{
    	CadPipeConexao o = new CadPipeConexao(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(
			numeroConexao,
		    AppDefs.pipeConnectionTypeCurva90Val,
		    objId_1,
		    objId_2,
		    AppDefs.NULL_INT,
		    AppDefs.NULL_INT,
		    diameterMili_1,
		    diameterMili_2,
		    0.0,
		    0.0,
		    ptIns,
		    ptDir );
    	return o;
    }
	
	public static CadPipeConexao createNewTuboOperculado(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel, 
	    int numeroConexao,
	    int objId_1,
	    int objId_2,
	    double diameterMili_1,
	    double diameterMili_2,
	    GeomPoint3d ptIns,
	    GeomPoint3d ptDir )
	{
    	CadPipeConexao o = new CadPipeConexao(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(
			numeroConexao,
		    AppDefs.pipeConnectionTypeTuboOperculadoVal,
		    objId_1,
		    objId_2,
		    AppDefs.NULL_INT,
		    AppDefs.NULL_INT,
		    diameterMili_1,
		    diameterMili_2,
		    0.0,
		    0.0,
		    ptIns,
		    ptDir );
    	return o;
    }
	
	public static CadPipeConexao createNewTe(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel, 
	    int numeroConexao,
	    int objId_1,
	    int objId_2,
	    int objId_3,
	    double diameterMili_1,
	    double diameterMili_2,
	    double diameterMili_3,
	    GeomPoint3d ptIns,
	    GeomPoint3d ptDir )
	{
    	CadPipeConexao o = new CadPipeConexao(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(
			numeroConexao,
		    AppDefs.pipeConnectionTypeTeVal,
		    objId_1,
		    objId_2,
		    objId_3,
		    AppDefs.NULL_INT,
		    diameterMili_1,
		    diameterMili_2,
		    diameterMili_3,
		    0.0,
		    ptIns,
		    ptDir );
    	return o;
    }
	
	public static CadPipeConexao createNewTe45(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel, 
	    int numeroConexao,
	    int objId_1,
	    int objId_2,
	    int objId_3,
	    double diameterMili_1,
	    double diameterMili_2,
	    double diameterMili_3,
	    GeomPoint3d ptIns,
	    GeomPoint3d ptDir )
	{
    	CadPipeConexao o = new CadPipeConexao(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(
			numeroConexao,
		    AppDefs.pipeConnectionTypeTe45Val,
		    objId_1,
		    objId_2,
		    objId_3,
		    AppDefs.NULL_INT,
		    diameterMili_1,
		    diameterMili_2,
		    diameterMili_3,
		    0.0,
		    ptIns,
		    ptDir );
    	return o;
    }
	
	public static CadPipeConexao createNewTe45Inv(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel, 
	    int numeroConexao,
	    int objId_1,
	    int objId_2,
	    int objId_3,
	    double diameterMili_1,
	    double diameterMili_2,
	    double diameterMili_3,
	    GeomPoint3d ptIns,
	    GeomPoint3d ptDir )
	{
    	CadPipeConexao o = new CadPipeConexao(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(
			numeroConexao,
		    AppDefs.pipeConnectionTypeTe45InvVal,
		    objId_1,
		    objId_2,
		    objId_3,
		    AppDefs.NULL_INT,
		    diameterMili_1,
		    diameterMili_2,
		    diameterMili_3,
		    0.0,
		    ptIns,
		    ptDir );
    	return o;
    }
	
	public static CadPipeConexao createNewCruzeta(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel, 
	    int numeroConexao,
	    int objId_1,
	    int objId_2,
	    int objId_3,
	    int objId_4,
	    double diameterMili_1,
	    double diameterMili_2,
	    double diameterMili_3,
	    double diameterMili_4,
	    GeomPoint3d ptIns,
	    GeomPoint3d ptDir )
	{
    	CadPipeConexao o = new CadPipeConexao(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(
			numeroConexao,
		    AppDefs.pipeConnectionTypeCruzetaVal,
		    objId_1,
		    objId_2,
		    objId_3,
		    objId_4,
		    diameterMili_1,
		    diameterMili_2,
		    diameterMili_3,
		    diameterMili_4,
		    ptIns,
		    ptDir );
    	return o;
    }
	
	public static CadPipeConexao create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel, 
	    int numeroConexao,
	    int tipoConexao,
	    int objId_1,
	    int objId_2,
	    int objId_3,
	    int objId_4,
	    double diameterMili_1,
	    double diameterMili_2,
	    double diameterMili_3,
	    double diameterMili_4,
	    GeomPoint3d ptIns,
	    GeomPoint3d ptDir )
	{
    	CadPipeConexao o = new CadPipeConexao(oBlkDef, oLayer, oLevel, 0.0, false);
    	o.init(
			numeroConexao,
		    tipoConexao,
		    objId_1,
		    objId_2,
		    objId_3,
		    objId_4,
		    diameterMili_1,
		    diameterMili_2,
		    diameterMili_3,
		    diameterMili_4,
		    ptIns,
		    ptDir );
    	return o;
    }
	
	public static CadPipeConexao create(CadPipeConexao other)
	{
    	CadPipeConexao o = new CadPipeConexao(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(
    		other.getNumeroConexao(),
    		other.getTipoConexao(),
    		other.getObjId_1(),
    		other.getObjId_2(),
    		other.getObjId_3(),
    		other.getObjId_4(),
    		other.getDiameterMili_1(),
    		other.getDiameterMili_2(),
    		other.getDiameterMili_3(),
    		other.getDiameterMili_4(),
    		other.getPtIns(),
    		other.getPtDir() );
    	return o;
    }
	
	public static CadPipeConexao create(CadBlockDef blkDef, CadPipeConexao other)
	{
    	CadPipeConexao o = new CadPipeConexao(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(
    		other.getNumeroConexao(),
    		other.getTipoConexao(),
    		other.getObjId_1(),
    		other.getObjId_2(),
    		other.getObjId_3(),
    		other.getObjId_4(),
    		other.getDiameterMili_1(),
    		other.getDiameterMili_2(),
    		other.getDiameterMili_3(),
    		other.getDiameterMili_4(),
    		other.getPtIns(),
    		other.getPtDir() );
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadPipeConexao duplicate()
	{
		CadPipeConexao other = CadPipeConexao.create(this);
		return other;
	}
	
	@Override
	public CadPipeConexao duplicate(CadBlockDef blkDef)
	{
		CadPipeConexao other = CadPipeConexao.create(blkDef, this);
		return other;
	}
	
	@Override
	public CadPipeConexao copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadPipeConexao other = CadPipeConexao.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
	public CadPipeConexao moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrigI2dMcs = new GeomPoint2d(this.ptIns);
    	MoveData2dVO oI = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptOrigI2dMcs);
    	this.ptIns = new GeomPoint3d(oI.getPtDest());
    	return this;
	}
	
	@Override
	public CadPipeConexao scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrigI2dMcs = new GeomPoint2d(this.ptIns);
    	ScaleData2dVO oI = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptOrigI2dMcs);
    	this.ptIns = new GeomPoint3d(oI.getPtDest());		
    	return this;
	}
	
    @Override
	public CadPipeConexao mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs) {
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
		this.ptIns = GeomUtil.mirror(this.ptIns, ptI2dMcs, ptF2dMcs);
		return this;
	}
	
	@Override
	public CadPipeConexao offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadPipeConexao o = copyTo(ptIMcs, ptFMcs);
		return o;
	}
    
	/* DEBUG */
	
	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatPtBr(0);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);
		
		lsProperty.add( new ItemDataVO("No. Conexao", Integer.toString(this.numeroConexao)) );

		lsProperty.add( new ItemDataVO("Tipo Conexao", this.descricaoTipoConexao) );

		lsProperty.add( new ItemDataVO("Ponto Insercao", this.ptIns.toStr()) );
	
		lsProperty.add( new ItemDataVO("Direcao", this.ptDir.toStr()) );
		
		if(this.objId_1 != AppDefs.NULL_INT) {
			lsProperty.add( new ItemDataVO("Tubulacao 1", Integer.toString(this.objId_1)) );
			lsProperty.add( new ItemDataVO("Diametro 1 (mm)", nf0.format(this.diameterMili_1)) );
		}
		
		if(this.objId_2 != AppDefs.NULL_INT) {
			lsProperty.add( new ItemDataVO("Tubulacao 2", Integer.toString(this.objId_2)) );
			lsProperty.add( new ItemDataVO("Diametro 2 (mm)", nf0.format(this.diameterMili_2)) );
		}
		
		if(this.objId_3 != AppDefs.NULL_INT) {
			lsProperty.add( new ItemDataVO("Tubulacao 3", Integer.toString(this.objId_3)) );
			lsProperty.add( new ItemDataVO("Diametro 3 (mm)", nf0.format(this.diameterMili_3)) );
		}
		
		if(this.objId_4 != AppDefs.NULL_INT) {
			lsProperty.add( new ItemDataVO("Tubulacao 4", Integer.toString(this.objId_4)) );
			lsProperty.add( new ItemDataVO("Diametro 4 (mm)", nf0.format(this.diameterMili_4)) );
		}
		
		return lsProperty;
	}

	@Override
	public String toStr() {
		NumberFormat nf0 = FormatUtil.newNumberFormatPtBr(0);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		String str = String.format(
			"numeroConexao:%s;" +
			"tipoConexao:%s;" +
			"descricaoTipoConexao:%s;" +
			"ptIns:%s;" +
			"ptDir:%s;" +
			"objId_1:%s;" +
			"diameterMili_1:%s;" +
			"objId_2:%s;" +
			"diameterMili_2:%s;" +
			"objId_3:%s;" +
			"diameterMili_3:%s;" +
			"objId_4:%s;" +
			"diameterMili_4:%s;", 
	    	Integer.toString(this.numeroConexao),
	    	this.tipoConexao,
	    	this.descricaoTipoConexao,
	    	this.ptIns.toStr(),
	    	this.ptDir.toStr(),
	    	Integer.toString(this.objId_1),
	    	nf0.format(this.diameterMili_1),
	    	Integer.toString(this.objId_2),
	    	nf0.format(this.diameterMili_2),
			Integer.toString(this.objId_3),
			nf0.format(this.diameterMili_3),
			Integer.toString(this.objId_4),
			nf0.format(this.diameterMili_4) );
		return str;
	}
	
	@Override
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}

    /* DRAWCACHE */

	@Override
	public DrawCache createDrawCache2d() {
		return null;
	}

	@Override
	public DrawCache createDrawCache3d() {
		return null;
	}

	@Override
	public DrawCache createOsnapCache() {
		return null;
	}

    /* DRAWING */
	
	public void redraw2d_highDetailLevel(ICadViewBase v, GeomPoint3d ptInsDest, GeomPoint3d ptDirDest, double sclFact, Graphics g)
	{
		//TODO:
	}

	public void redraw2d_lowDetailLevel(ICadViewBase v, GeomPoint3d ptInsDest, GeomPoint3d ptDirDest, double sclFact, Graphics g)
	{
		//TODO:		
	}
	
	@Override
    public void redraw2d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, Graphics g) 
    {
    	if( !this.isVisible() ) return;

        boolean bSelected = this.isSelected();
		boolean bHover = false;
		if( !bSelected )
			bHover = this.select2d(v, pt2dMcs, sclFact, false);
		
		Stroke b = selectLtype(bDragMode, bSelected, bHover, bSelEnt);

		Stroke oldltype = GeomUtil.setLtype(g, b);

		Color c = super.selectColor(bDragMode, bSelected, bHover, bSelEnt);

		Color oldcol = GeomUtil.setColor(g, c);		

        MainPanel panel = MainPanel.getMainPanel();
        String action = panel.getCurrAction();
        
        GeomPoint3d ptInsDest = new GeomPoint3d( this.ptIns );
        GeomPoint3d ptDirDest = new GeomPoint3d( this.ptDir );
        
        if( bDragMode ) 
        {
	        if(ptBase2dMcs != null) 
	        {        
	            GeomPoint3d ptBase3dMcs = new GeomPoint3d(ptBase2dMcs);
	            GeomPoint3d pt3dMcs = new GeomPoint3d(pt2dMcs);

	            GeomVector3d vDir3dMcs = new GeomVector3d(ptBase3dMcs, pt3dMcs);
	            
		        if( AppDefs.ACTION_EDIT2_COPY.equals(action) || 
		        	AppDefs.ACTION_EDIT2_MOVE.equals(action) )
		        {
		        	CadPipeConexao other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, pt3dMcs);
		        	
		        	ptInsDest = other.getPtIns();
		        	ptDirDest = other.getPtDir();
		        }	        
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadPipeConexao other = this.duplicate();
		        	other.mirror(ptBase3dMcs, pt3dMcs);
		        	
		        	ptInsDest = other.getPtIns();
		        	ptDirDest = other.getPtDir();
		        }
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
		        		CadPipeConexao other = this.duplicate();
			        	other.scaleTo(dist, ptBase3dMcs, pt3dMcs);
			        				        	
			        	ptInsDest = other.getPtIns();
			        	ptDirDest = other.getPtDir();
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadPipeConexao other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, newPt3dMcs);
		        	
		        	ptInsDest = other.getPtIns();
		        	ptDirDest = other.getPtDir();
		        }
	        }
        }
        
        String strDetailLevel = v.getDetailLevel();

        if( AppDefs.DEF_DETAILLEVEL_HIGH.equals(strDetailLevel) ) {
        	redraw2d_highDetailLevel(v, ptInsDest, ptDirDest, sclFact, g);
        }
        else {
        	redraw2d_lowDetailLevel(v, ptInsDest, ptDirDest, sclFact, g);
        }
        
        GeomUtil.setColor(g, oldcol);
        
        GeomUtil.setLtype(g, oldltype);
    }

	//VIEW-3D
	//
	public void redraw3d_view(ICadViewBase v, double dist, GeomPoint3d ptDestIMcs, GeomPoint3d ptDestFMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep)
	{
		//TODO:
	}

	@Override
	public void redraw3d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep)
	{
		//TODO:
	}
	        
	/* SELECT */
	
	public boolean select2d_planView(ICadViewBase view2d, GeomPoint2d pt2dMcs, GeomPoint3d ptI3dMcs, GeomPoint3d ptF3dMcs, double sclFact, boolean bSelectEntity) 
	{
		//TODO:
		
        return false;
	}

	@Override
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) 
	{
		//TODO:
		
        return false;
	}

	@Override
	public boolean select3d(ICadViewBase view3d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) {
		return false;
	}

	/* TO_SHAPE */

	@Override
	public ShapeResult toGeomShape2d_planView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_frontView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_backView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_leftView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_rightView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_topView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_bottomView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape3d(boolean bAnnotation, GeomPoint3d ptBase3dMcs)
	{
		return null;
	}

	/* OSNAP */

	public GeomPoint3d osnap3d_view(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs, GeomPoint3d ptI3dMcs, GeomPoint3d ptF3dMcs, Graphics g) 
	{
    	GeomPoint3d ptResult = null;

    	//TODO:
    	
    	return ptResult;
	}

	public ArrayList<GeomPoint3d> osnap3d_view(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs) 
	{
    	ArrayList<GeomPoint3d> lsResult = new ArrayList<GeomPoint3d>();

    	//TODO:
    	
    	return lsResult;    	
	}
	
	@Override
	public GeomPoint3d osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs, Graphics g) 
	{
    	if( !this.isVisible() ) return null;

		if(pt2dMcs == null) return null;

    	//TODO:

		return null;
	}

	@Override
	public ArrayList<GeomPoint3d> osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs) 
	{
    	if( !this.isVisible() ) return null;

		if(pt2dMcs == null) return null;
		
    	ArrayList<GeomPoint3d> lsResult = new ArrayList<GeomPoint3d>();

    	//TODO:

    	return null;
	}

	/* CENTROID */	
	
	@Override
	public GeomPoint3d centroid()
	{
    	//TODO:

		return null;
	}
	
	/* LOAD/SAVE */
	
	@Override
	public boolean load(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		return true;
	}

	@Override
	public boolean save(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		return false;
	}
	
	/* READ/WRITE DXF R12 */
	
	@Override
	public void fromDxfR12(DxfCadEntity o)
	{
		//TODO:
	}
	
	@Override
	public ArrayList<DxfCadEntity> toDxfR12()
	{
		ArrayList<DxfCadEntity> lsDxfCadEntity = new ArrayList<DxfCadEntity>(); 
    	if(this.isDeleted()) return lsDxfCadEntity;
		
		//ArrayList<DxfCadEntity> lsCadEntity2d = toDxfR12_view2d();
		//lsDxfCadEntity.addAll( lsCadEntity2d );

		//ArrayList<DxfCadEntity> lsCadEntity3d = toDxfR12_view3d();
		//lsDxfCadEntity.addAll( lsCadEntity3d );
		
		return lsDxfCadEntity;
	}

	/* DXFR12_VIEW2D */
		
	@Override
	public ArrayList<DxfCadEntity> toDxfR12_view2d()
	{
		ArrayList<DxfCadEntity> lsDxfCadEntity = new ArrayList<DxfCadEntity>(); 

		//TODO:
		
		return lsDxfCadEntity;
	}

	public ArrayList<DxfCadEntity> toDxfR12_flowdir_planView(CadLayerDef oLayer, CadEntity oEnt, GeomPoint2d ptI2dMcs, GeomPoint2d ptF2dMcs, double sclFact)
	{
		ArrayList<DxfCadEntity> lsCadEntity3d = new ArrayList<DxfCadEntity>(); 

		//TODO:
		
		return lsCadEntity3d;
	}
	
	public ArrayList<DxfCadEntity> toDxfR12_highDetailLevel_annotation_planView(CadLayerDef oLayer, CadEntity oEnt, GeomPoint2d ptI2dMcs, GeomPoint2d ptF2dMcs, double sclFact)
	{
		ArrayList<DxfCadEntity> lsCadEntity3d = new ArrayList<DxfCadEntity>(); 

		//TODO:
		
		return lsCadEntity3d;
	}
	
	public ArrayList<DxfCadEntity> toDxfR12_lowDetailLevel_annotation_planView(CadLayerDef oLayer, CadEntity oEnt, GeomPoint2d ptI2dMcs, GeomPoint2d ptF2dMcs, double sclFact)
	{
		ArrayList<DxfCadEntity> lsCadEntity3d = new ArrayList<DxfCadEntity>(); 

		//TODO:
		
		return lsCadEntity3d;
	}
		
	public ArrayList<DxfCadEntity> toDxfR12_sectionCircle_planView(CadLayerDef oLayer, CadEntity oEnt, GeomPoint2d ptI2dMcs, GeomPoint2d ptF2dMcs)
	{
		ArrayList<DxfCadEntity> lsCadEntity3d = new ArrayList<DxfCadEntity>(); 

		//TODO:
		
        return lsCadEntity3d;
	}
	
	public ArrayList<DxfCadEntity> toDxfR12_sectionRectangle_planView(CadLayerDef oLayer, CadEntity oEnt, GeomPoint2d ptI2dMcs, GeomPoint2d ptF2dMcs)
	{
		ArrayList<DxfCadEntity> lsCadEntity3d = new ArrayList<DxfCadEntity>(); 

		//TODO:
		
        return lsCadEntity3d;
	}
		
	//LOW_DETAIL_LEVEL
	//
	public ArrayList<DxfCadEntity> toDxfR12_lowDetailLevel_planView(CadLayerDef oLayer, CadEntity oEnt, GeomPoint2d ptI2dMcs, GeomPoint2d ptF2dMcs, double sclFact)
	{
		ArrayList<DxfCadEntity> lsCadEntity3d = new ArrayList<DxfCadEntity>(); 

		//TODO:
		
        return lsCadEntity3d;
	}

	/* DXFR12_VIEW3D */		
		
	@Override
	public ArrayList<DxfCadEntity> toDxfR12_view3d()
	{
		ArrayList<DxfCadEntity> lsCadEntity3d = new ArrayList<DxfCadEntity>();

		//TODO:
		
    	return lsCadEntity3d;
	}
	
	/* Getters/Setters */
	
	@Override
	public GeomDimension3d getEnvelop3d() 
	{
		//TODO:
		
		return null;
	}
	
	@Override
	public GeomDimension2d getEnvelop2d() 
	{

		//TODO:
		
		return null;
	}
	
	@Override
	public String getSearchString() 
	{
		//TODO:
		
		return null;
	}

	/* Getters/Setters */
	
	public int getNumeroConexao() {
		return numeroConexao;
	}

	public void setNumeroConexao(int numeroConexao) {
		this.numeroConexao = numeroConexao;
	}

	public int getTipoConexao() {
		return tipoConexao;
	}

	public void setTipoConexao(int tipoConexao) {
		this.tipoConexao = tipoConexao;
	}

	public String getDescricaoTipoConexao() {
		return descricaoTipoConexao;
	}

	public void setDescricaoTipoConexao(String descricaoTipoConexao) {
		this.descricaoTipoConexao = descricaoTipoConexao;
	}

	public int getObjId_1() {
		return objId_1;
	}

	public void setObjId_1(int objId_1) {
		this.objId_1 = objId_1;
	}

	public int getObjId_2() {
		return objId_2;
	}

	public void setObjId_2(int objId_2) {
		this.objId_2 = objId_2;
	}

	public int getObjId_3() {
		return objId_3;
	}

	public void setObjId_3(int objId_3) {
		this.objId_3 = objId_3;
	}

	public int getObjId_4() {
		return objId_4;
	}

	public void setObjId_4(int objId_4) {
		this.objId_4 = objId_4;
	}

	public double getDiameterMili_1() {
		return diameterMili_1;
	}

	public void setDiameterMili_1(double diameterMili_1) {
		this.diameterMili_1 = diameterMili_1;
	}

	public double getDiameterMili_2() {
		return diameterMili_2;
	}

	public void setDiameterMili_2(double diameterMili_2) {
		this.diameterMili_2 = diameterMili_2;
	}

	public double getDiameterMili_3() {
		return diameterMili_3;
	}

	public void setDiameterMili_3(double diameterMili_3) {
		this.diameterMili_3 = diameterMili_3;
	}

	public double getDiameterMili_4() {
		return diameterMili_4;
	}

	public void setDiameterMili_4(double diameterMili_4) {
		this.diameterMili_4 = diameterMili_4;
	}

	public GeomPoint3d getPtIns() {
		return ptIns;
	}

	public void setPtIns(GeomPoint3d ptIns) {
		this.ptIns = ptIns;
	}

	public GeomPoint3d getPtDir() {
		return ptDir;
	}

	public void setPtDir(GeomPoint3d ptDir) {
		this.ptDir = ptDir;
	}

}
