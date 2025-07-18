/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ICadEntity.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 13/02/2025
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
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomFace3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomShape2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeOper2d;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public interface ICadEntity
{
//Public

	/* Methodes */
	
	public void init(CadLayerDef layer);
	
	public void init(ICadEntity other);
	
	public void reset();
	
	/* SELECTxxx - Color, Line Style, Hatch Pattern, Font Style, Dimension Style */
	
	public Color selectColor(boolean bDragMode, boolean bSelected, boolean bHover, boolean bSelEnt);
	
	public Stroke selectLtype(boolean bDragMode, boolean bSelected, boolean bHover, boolean bSelEnt);
	
	/* OPERATIONS */
	
	public ICadEntity duplicate();

	public ICadEntity copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs);

	public ICadEntity moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs);
	
	public ICadEntity scaleTo(double refDistMcs, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs);
    
	public ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs);
    
	public ICadEntity offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist);

	/* DEBUG */

	public ArrayList<ItemDataVO> toPropertyList();
	
	public String toStr();
	
	public void debug(int debugLevel);

    /* REDRAW */

    public void redraw2d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, Graphics g);
    
	public void redraw3d(ICadViewBase view3d, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep);

	/* SELECT */
	
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity);

	public boolean select3d(ICadViewBase view3d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity);

	/* TO_SHAPE */

	public ArrayList<ShapeOper2d> toGeomShape2d_planView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);

	public ArrayList<ShapeOper2d> toGeomShape2d_frontView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);
	
	public ArrayList<ShapeOper2d> toGeomShape2d_backView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);
	
	public ArrayList<ShapeOper2d> toGeomShape2d_leftView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);
	
	public ArrayList<ShapeOper2d> toGeomShape2d_rightView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);
	
	public ArrayList<ShapeOper2d> toGeomShape2d_topView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);

	public ArrayList<ShapeOper2d> toGeomShape2d_bottomView(boolean bAnnotation, GeomPoint2d ptBase2dMcs);

	public ArrayList<ShapeOper2d> toGeomShape3d(boolean bAnnotation, GeomPoint3d ptBase3dMcs);

	/* OSNAP */

	public GeomPoint3d osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs, Graphics g);

	/* CENTROID */
	
	public GeomPoint3d centroid();
	
	/* UTILITIES */

	public boolean isVisible();

	public boolean isInside(GeomPoint2d ptIMcs, GeomPoint2d ptFMcs);

	/* Getters/Setters */

	public abstract GeomDimension2d getEnvelop();
	
}
