/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * IEntityDrawCache.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 30/08/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.ecache;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Stroke;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;

public interface IEntityDrawCache 
{
//Public
	
	public void init();

	public void init(Color oColor);

	public void init(Color oColor, Stroke oLtype);

	public void init(IEntityDrawCache other);

	public void init2d(Color oColor, Stroke oLtype, ArrayList<GeomPoint2d> lsPts2d);

	public void init3d(Color oColor, Stroke oLtype, ArrayList<GeomPoint3d> lsPts3d);

	/* BASIC_OPERATIONS */
	
	public void addPoint2d(GeomPoint2d oPt2d);

	public void addPoint3d(GeomPoint3d oPt3d);
	
	public void addAllPoint2d(ArrayList<GeomPoint2d> lsPts2d);

	public void addAllPoint3d(ArrayList<GeomPoint3d> lsPts3d);

	public GeomPoint2d getPoint2dAt(int pos);
	
	public GeomPoint3d getPoint3dAt(int pos);
	
	public int getSize();

	public IEntityDrawCache duplicate();

	/* SELF_OPERATIONS */
	
	public IEntityDrawCache selfOffsetTo(double deltaX, double deltaY, double deltaZ);
	
	public IEntityDrawCache selfMoveTo(GeomPoint3d ptBase, GeomPoint3d ptRef);
	
	public IEntityDrawCache selfMirrorTo(GeomPoint3d ptBase, GeomPoint3d ptRef);
	
	public IEntityDrawCache selfScaleTo(GeomPoint3d ptBase, GeomPoint3d ptRef, double scale);
	
	public IEntityDrawCache selfRotateTo(GeomPoint3d ptBase, GeomVector3d zDir, double angleRad);

	/* OTHER_OPERATIONS */
	
	public IEntityDrawCache otherOffsetTo(double deltaX, double deltaY, double deltaZ);
	
	public IEntityDrawCache otherMoveTo(GeomPoint3d ptBase, GeomPoint3d ptRef);
	
	public IEntityDrawCache otherMirrorTo(GeomPoint3d ptBase, GeomPoint3d ptRef);
	
	public IEntityDrawCache otherScaleTo(GeomPoint3d ptBase, GeomPoint3d ptRef, double scale);
	
	public IEntityDrawCache otherRotateTo(GeomPoint3d ptBase, GeomVector3d zDir, double angleRad);

    /* DRAWING */
	
    public void redraw2d(ICadViewBase view2d, Color oColor, Stroke oLtype, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, String action, Graphics g);
	
	public void redraw3d(ICadViewBase view3d, Color oColor, Stroke oLtype, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, String action, PrepareDrawUtil prep);
	        
	/* SELECT */

	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity);

	public boolean select3d(ICadViewBase view3d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity);

	/* OSNAP */

	public ArrayList<GeomPoint3d> osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs);

	public GeomPoint3d osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs, Graphics g);

	/* ENVELOP */
	
	public GeomDimension2d getEnvelop();

	/* Getters/Setters */
	
	public Color getColor();

	public void setColor(Color oColor);

	public Stroke getLtype();

	public void setLtype(Stroke oLtype);
		
}
