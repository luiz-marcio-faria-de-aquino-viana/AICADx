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

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.geom.GeomFace3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.vo.BorderStrokeVO;
import br.com.tlmv.aicadxapp.vo.ColorVO;
import javafx.scene.layout.BorderStrokeStyle;

public class CadEntity extends CadObject implements ICadEntity
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
	
	public void init(CadLayerDef layer) 
	{
		this.layer = layer;
		this.bSelected = false;
	}
	
	public void init(ICadEntity other) {
		CadEntity oEnt = (CadPoint)other;
		this.init(oEnt.layer);
	}
	
	public void reset()
	{
		super.reset();
		this.bSelected = false;		
	}
	
	/* SELECTxxx - Color, Line Style, Hatch Pattern, Font Style, Dimension Style */
	
	public Color selectColor(boolean bDragMode, boolean bSelected, boolean bHover)
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

		return c;
	}
	
	public Stroke selectLtype(boolean bDragMode, boolean bSelected, boolean bHover)
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

		Stroke b = oLtype.getLtype();		
		return b;
	}
	
	/* OPERATIONS */
	
	public ICadEntity duplicate()
	{
		return null;
	}

	public ICadEntity copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		return null;
	}

	public ICadEntity moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		return null;
	}
	
	public ICadEntity scaleTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		return null;
	}
    
	public ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		return null;
	}
    
	public ICadEntity offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		return null;
	}

	/* DEBUG */
	
	public String toStr() {
		String str = super.toStr();
		str += String.format(
			"%s;Selected:%s;",
			this.layer.toStr(),
			( this.bSelected ) ? "true" : "false" );
		return str;
	}
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}
	
	/* REDRAW */
	
	public void redraw2d(ICadViewBase view2d, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, boolean bDragMode, Graphics g) {
		//TODO:
	}
	
	public void redraw3d(ICadViewBase view3d, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, boolean bDragMode, PrepareDrawUtil prep) {
		//TODO:
	}
	
	/* SELECT */

	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, boolean bSelectEntity) {
		return false;
	}

	public boolean select3d(ICadViewBase view3d, GeomPoint2d pt2dMcs, boolean bSelectEntity) {
		return false;
	}

	/* OSNAP */
	
	public GeomPoint2d osnap2d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs, Graphics g) 
	{
		return null;
	}

	/* CENTROID */
	
	public GeomPoint3d centroid()
	{
		return null;
	}
	
	/* Utilities */

	public boolean isVisible()
	{
    	if(this.isDeleted()) return false;
    	
    	boolean layerOn = this.layer.isLayerOn();
    	if( !layerOn ) return false;
    	
    	return true;
	}

	/* Getters/Setters */
	
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
