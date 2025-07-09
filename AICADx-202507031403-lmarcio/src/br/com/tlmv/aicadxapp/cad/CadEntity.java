/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadEntity.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Stroke;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDatabase;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomFace3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomShape2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeOper2d;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.vo.BorderStrokeVO;
import br.com.tlmv.aicadxapp.vo.ColorVO;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public abstract class CadEntity extends CadObject implements ICadEntity
{
//Private
	private CadLayerDef layer = null;
	private boolean bSelected = false;
	
//Public

	public CadEntity(int objType, CadLayerDef layer) {
		super(objType);
		this.init(layer);
	}

	/* Methodes */
	
	@Override
	public void init(CadLayerDef layer) 
	{
		this.layer = layer;
		this.bSelected = false;
	}
	
	@Override
	public void init(ICadEntity other) {
		CadEntity oEnt = (CadPoint)other;
		this.init(oEnt.layer);
	}
	
	@Override
	public void reset()
	{
		this.bSelected = false;		
	}
	
	/* SELECTxxx - Color, Line Style, Hatch Pattern, Font Style, Dimension Style */
	
	@Override
	public Color selectColor(boolean bDragMode, boolean bSelected, boolean bHover, boolean bSelEnt)
	{
		CadLayerDef oLayer = this.layer;
		
		ColorVO oColor = oLayer.getColor();
		
		Color c = oColor.getColor();
		
		if( bDragMode ) {
			c = AppDefs.DRAGOBJECTCOLOR_SELECTMODE;
		}
		else if( bSelected ){
	    	c = AppDefs.SELECTOBJECTCOLOR_SELECTMODE;
		}
		else if( bHover ){
	    	c = AppDefs.HOVEROBJECTCOLOR_SELECTMODE;
		}
		else if( bSelEnt ){
	    	c = AppDefs.CURRENTSELECTENTITYCOLOR;
		}

		return c;
	}
	
	@Override
	public Stroke selectLtype(boolean bDragMode, boolean bSelected, boolean bHover, boolean bSelEnt)
	{
		CadLayerDef oLayer = this.layer;
		
		BorderStrokeVO oLtype = oLayer.getLtype();
		
		if( bDragMode ) {
			oLtype = AppDefs.DRAGOBJECTLTYPE_SELECTMODE;
		}
		else if( bSelected ){
			oLtype = AppDefs.SELECTOBJECTLTYPE_SELECTMODE;
		}
		else if( bHover ){
			oLtype = AppDefs.HOVEROBJECTLTYPE_SELECTMODE;
		}
		else if( bSelEnt ){
			oLtype = AppDefs.CURRENTSELECTENTITYLTYPE;
		}

		Stroke b = oLtype.getLtype();		
		return b;
	}
	
	/* OPERATIONS */
	
	@Override
	public abstract ICadEntity duplicate();

	@Override
	public abstract ICadEntity copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs);

	@Override
	public abstract ICadEntity moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs);
	
	@Override
	public abstract ICadEntity scaleTo(double refDistMcs, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs);
    
	@Override
	public abstract ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs);
    
	@Override
	public abstract ICadEntity offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist);

	/* DEBUG */

	@Override
	public abstract ArrayList<ItemDataVO> toPropertyList();
	
	@Override
	public abstract String toStr();
	
	@Override
	public abstract void debug(int debugLevel);
	
	/* REDRAW */
	
	@Override
	public abstract void redraw2d(ICadViewBase view2d, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, Graphics g);
	
	@Override
	public abstract void redraw3d(ICadViewBase view3d, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep);
	
	/* SELECT */

	@Override
	public abstract boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity);

	@Override
	public abstract boolean select3d(ICadViewBase view3d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity);

	/* TO_SHAPE */

	@Override
	public abstract ArrayList<ShapeOper2d> toGeomShape2d_planView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);

	@Override
	public abstract ArrayList<ShapeOper2d> toGeomShape2d_frontView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);
	
	@Override
	public abstract ArrayList<ShapeOper2d> toGeomShape2d_backView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);
	
	@Override
	public abstract ArrayList<ShapeOper2d> toGeomShape2d_leftView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);
	
	@Override
	public abstract ArrayList<ShapeOper2d> toGeomShape2d_rightView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);
	
	@Override
	public abstract ArrayList<ShapeOper2d> toGeomShape2d_topView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);

	@Override
	public abstract ArrayList<ShapeOper2d> toGeomShape2d_bottomView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);

	@Override
	public abstract ArrayList<ShapeOper2d> toGeomShape3d(boolean bAnnotation, GeomPoint3d ptBase3dMcs);
	
	/* OSNAP */
	
	@Override
	public abstract GeomPoint3d osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs, Graphics g); 

	/* CENTROID */
	
	@Override
	public abstract GeomPoint3d centroid();
	
	/* UTILITIES */

	@Override
	public boolean isVisible()
	{
    	if(this.isDeleted()) return false;
    	
    	boolean layerOn = this.layer.isLayerOn();
    	if( !layerOn ) return false;
    	
    	return true;
	}

	@Override
	public boolean isInside(GeomPoint2d ptIMcs, GeomPoint2d ptFMcs)
	{
		GeomPoint2d[] arr = GeomUtil.maxMinPointOf(ptIMcs, ptFMcs);
		GeomPoint2d ptMin2d = arr[0];
		GeomPoint2d ptMax2d = arr[1];
		
		GeomDimension2d oEnvelop = this.getEnvelop();
		GeomPoint2d ptEnvelopMin2d = oEnvelop.getPtMin();
		GeomPoint2d ptEnvelopMax2d = oEnvelop.getPtMax();
		
		boolean bInside = GeomUtil.checkIfRectAInsideRectB(ptEnvelopMin2d, ptEnvelopMax2d, ptMin2d, ptMax2d);
		if( !bInside )
			bInside = GeomUtil.checkIfRectAInsideRectB(ptMin2d, ptMax2d, ptEnvelopMin2d, ptEnvelopMax2d);
		return bInside;
	}
	
	/* LOAD/SAVE */
	
	@Override
	public abstract void load(AppDatabase db, String schemaName, CadDocumentDef doc);

	@Override
	public abstract void save(AppDatabase db, String schemaName, CadDocumentDef doc);
	
	/* Getters/Setters */

	@Override
	public abstract GeomDimension2d getEnvelop();
	
	public CadLayerDef getLayer() {
		return this.layer;
	}

	public void setLayer(CadLayerDef layer) {
		this.layer = layer;
	}

	public boolean isSelected() {
		return bSelected;
	}

	public void setSelected(boolean bSelected) {
		this.bSelected = bSelected;
	}

}
