/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * EntityDrawCache.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 30/08/2025
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

package br.com.tlmv.aicadxapp.cad.ecache;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Stroke;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfCadEntity;

public abstract class EntityDrawCache implements IEntityDrawCache
{
//Private
	private Color oColor = null;
	private Stroke oLtype = null;

//Public
	
	public void EntityDrawCache() {
		this.init();
	}

	public void EntityDrawCache(Color oColor, Stroke oLtype) {
		this.init(oColor, oLtype);
	}

	/* Methodes */
	
	@Override
	public void init() {
		this.init(null, null);
	}

	@Override
	public void init(Color oColor) {
		this.init(oColor, null);
	}

	@Override
	public void init(Color oColor, Stroke oLtype) {
		this.oColor = oColor;
		this.oLtype = oLtype;
	}
	
	@Override
	public abstract void init(IEntityDrawCache other);

	@Override
	public abstract void init2d(Color oColor, Stroke oLtype, ArrayList<GeomPoint2d> lsPts2d);

	@Override
	public abstract void init3d(Color oColor, Stroke oLtype, ArrayList<GeomPoint3d> lsPts3d);

	/* BASIC_OPERATIONS */
	
	@Override
	public abstract void addPoint2d(GeomPoint2d oPt2d);

	@Override
	public abstract void addPoint3d(GeomPoint3d oPt3d);
	
	@Override
	public abstract void addAllPoint2d(ArrayList<GeomPoint2d> lsPts2d);

	@Override
	public abstract void addAllPoint3d(ArrayList<GeomPoint3d> lsPts3d);

	@Override
	public abstract GeomPoint2d getPoint2dAt(int pos);

	@Override
	public abstract GeomPoint3d getPoint3dAt(int pos);

	@Override
	public abstract int getSize();

	@Override
	public abstract IEntityDrawCache duplicate();

	/* SELF_OPERATIONS */
	
	@Override
	public abstract IEntityDrawCache selfMoveTo(GeomPoint3d ptBase, GeomPoint3d ptRef);

	@Override
	public abstract IEntityDrawCache selfScaleTo(GeomPoint3d ptBase, GeomPoint3d ptRef, double scale);

	@Override
	public abstract IEntityDrawCache selfRotateTo(GeomPoint3d ptBase, GeomVector3d zDir, double angleRad);

	/* OTHER_OPERATIONS */

	@Override
	public abstract IEntityDrawCache otherMoveTo(GeomPoint3d ptBase, GeomPoint3d ptRef);
	
	@Override
	public abstract IEntityDrawCache otherScaleTo(GeomPoint3d ptBase, GeomPoint3d ptRef, double scale);

	@Override
	public abstract IEntityDrawCache otherRotateTo(GeomPoint3d ptBase, GeomVector3d zDir, double angleRad);

    /* DRAWING */
	
	@Override
	public abstract void redraw2d(ICadViewBase view2d, Color oColor, Stroke oLtype, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact,boolean bDragMode, boolean bSelEnt, String action, Graphics g);

	@Override
	public abstract void redraw3d(ICadViewBase view3d, Color oColor, Stroke oLtype, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, String action, PrepareDrawUtil prep);
    
	/* SELECT */

	@Override
	public abstract boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity);

	@Override
	public abstract boolean select3d(ICadViewBase view3d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity);

	/* OSNAP */

	@Override
	public abstract ArrayList<GeomPoint3d> osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs);

	@Override
	public abstract GeomPoint3d osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs, Graphics g);

	/* ENVELOP */
	
	@Override
	public abstract GeomDimension2d getEnvelop();
	
	/* READ/WRITE DXF R12 */
	
	public abstract ArrayList<DxfCadEntity> toDxfR12(CadLayerDef oLayer);
	
	public abstract ArrayList<DxfCadEntity> toDxfR12_view2d(CadLayerDef oLayer);
	
	public abstract ArrayList<DxfCadEntity> toDxfR12_view3d(CadLayerDef oLayer);

	/* Getters/Setters */
	
	public Color getColor() {
		return oColor;
	}

	public void setColor(Color oColor) {
		this.oColor = oColor;
	}

	public Stroke getLtype() {
		return oLtype;
	}

	public void setLtype(Stroke oLtype) {
		this.oLtype = oLtype;
	}

}
