/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * ICadEntity.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 13/02/2025
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
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.cad.ecache.DrawCache;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeResult;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.record.BaseEntityRecord;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public interface ICadEntity
{
//Public

	/* Methodes */
	
	public void initEntity(CadLayerDef layer, CadLevel level, double zLevel, boolean bLocked);

	public void initEntity(BaseEntityRecord oRec, CadDocumentDef doc, CadBlockDef oBlkDef);
	
	public void initEntity(ICadEntity other);
	
	public void reset();
	
	/* SELECTxxx - Color, Line Style, Hatch Pattern, Font Style, Dimension Style */
	
	public Color selectColor(boolean bDragMode, boolean bSelected, boolean bHover, boolean bSelEnt);
	
	public Stroke selectLtype(boolean bDragMode, boolean bSelected, boolean bHover, boolean bSelEnt);
	
	/* OPERATIONS */
	
	public boolean isLocked();

	public void setLocked(boolean bLocked);

	public boolean lock();

	public boolean unlock();
	
	public ICadEntity duplicate();

	public ICadEntity duplicate(CadBlockDef blkDef);

	public ICadEntity copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs);

	public ICadEntity moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs);
	
	public ICadEntity scaleTo(double refDistMcs, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs);
    
	public ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs);
    
	public ICadEntity offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist);

	/* DEBUG */

	public ArrayList<ItemDataVO> toPropertyList();
	
	public String toStr();
	
	public void debug(int debugLevel);

    /* DRAWCACHE */	

	public void createAllDrawCache();

	public DrawCache createDrawCache2d();

	public DrawCache createDrawCache3d();

	public DrawCache createOsnapCache();

    /* REDRAW */
	
    public void redraw2d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, Graphics g);
    
	public void redraw3d(ICadViewBase view3d, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep);

	/* SELECT */
	
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity);

	public boolean select3d(ICadViewBase view3d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity);

	/* TOOLTIP */
	
	public boolean showTooltip2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, Graphics g);

	public boolean showTooltip3d(ICadViewBase view3d, GeomPoint2d pt2dMcs, double sclFact, Graphics g);

	/* TO_SHAPE */

	public ShapeResult toGeomShape2d_planView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);

	public ShapeResult toGeomShape2d_frontView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);
	
	public ShapeResult toGeomShape2d_backView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);
	
	public ShapeResult toGeomShape2d_leftView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);
	
	public ShapeResult toGeomShape2d_rightView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);
	
	public ShapeResult toGeomShape2d_topView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);

	public ShapeResult toGeomShape2d_bottomView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);

	public ShapeResult toGeomShape3d(boolean bAnnotation, GeomPoint3d ptBase3dMcs);

	/* OSNAP */

	public GeomPoint3d osnap3d(ICadViewBase view2d, int osnapMode, GeomPoint2d pt2dMcs, Graphics g);

	public ArrayList<GeomPoint3d> osnap3d(ICadViewBase view2d, int osnapMode, GeomPoint2d pt2dMcs);

	/* VALID */

	public boolean isValid();

	/* CENTROID */
	
	public GeomPoint3d centroid();
	
	/* UTILITIES */

	public boolean isVisible();

	public boolean isInside(GeomPoint2d ptIMcs, GeomPoint2d ptFMcs);

	public boolean isCrossingLine(GeomPoint2d ptIMcs, GeomPoint2d ptFMcs);

	public boolean isCrossing(GeomPoint2d ptIMcs, GeomPoint2d ptFMcs);

	public boolean isObjtypeOf(int[] arrSelectObjType);

	public boolean search(int objType, String searchBy);
	
	public boolean search(int[] arrObjType, String searchBy);
	
	public GeomPoint3d nearestPoint(GeomPoint3d ptRef);
	
	public GeomPoint3d nearestConexao(GeomPoint3d ptRef);
	
	public GeomPoint3d nearestConexaoEntrada(GeomPoint3d ptRef);
	
	public GeomPoint3d nearestConexaoSaida(GeomPoint3d ptRef);
	
	/* Getters/Setters */

	public GeomDimension3d getEnvelop3d();
	
	public GeomDimension2d getEnvelop2d();

	public GeomPoint3d getExternalPoint3d(GeomPoint3d ptRef3d);
	
	public GeomPoint2d getExternalPoint2d(GeomPoint2d ptRef2d);
	
	public String getSearchString();
	
}
