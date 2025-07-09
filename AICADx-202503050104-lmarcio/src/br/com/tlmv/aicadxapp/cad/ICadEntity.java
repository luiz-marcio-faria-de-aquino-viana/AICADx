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
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.cad.geom.GeomFace3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;

public interface ICadEntity
{
//Public

	/* Methodes */
	
	public void init(CadLayerDef layer);
	
	public void init(ICadEntity other);
	
	public void reset();
	
	/* SELECTxxx - Color, Line Style, Hatch Pattern, Font Style, Dimension Style */
	
	public Color selectColor(boolean bDragMode, boolean bSelected, boolean bHover);
	
	/* OPERATIONS */
	
	public ICadEntity duplicate();

	public ICadEntity copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs);

	public ICadEntity moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs);
	
	public ICadEntity scaleTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs);
    
	public ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs);
    
    /* DRAWING */
	
    public void redraw2d(ICadViewBase v, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, boolean bDragMode, Graphics g);
    
	/* SELECT */
	
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, boolean bSelectEntity);

	/* OSNAP */

	public GeomPoint2d osnap2d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs, Graphics g);

	/* CENTROID */
	
	public GeomPoint3d centroid();
	
}
