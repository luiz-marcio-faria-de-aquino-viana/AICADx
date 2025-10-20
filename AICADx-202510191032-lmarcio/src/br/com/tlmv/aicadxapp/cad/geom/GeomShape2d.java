/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * GeomShape2d.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 20/04/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.geom;

import java.text.NumberFormat;
import java.util.ArrayList;

import org.apache.fop.util.XMLUtil;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeOper2d;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.XmlUtil;

public class GeomShape2d 
{
//Private
	private boolean bAnnotation = false;
	private ArrayList<ShapeOper2d> lsShpOper2d = null;
	
//Public
	
	public GeomShape2d()
	{
		this.init(false);
	}
	
	public GeomShape2d(boolean bAnnotation)
	{
		this.init(bAnnotation);
	}
	
	public GeomShape2d(GeomShape2d other)
	{
		this.init(other);
	}
	
	/* Methodes */
	
	public void init(boolean bAnnotation)
	{
		this.bAnnotation = bAnnotation;
		this.lsShpOper2d = new ArrayList<ShapeOper2d>();
	}
	
	public void init(GeomShape2d other)
	{
		this.bAnnotation = other.isAnnotation();

		this.lsShpOper2d = new ArrayList<ShapeOper2d>();
		
		int sz = other.size();
		for(int i = 0; i < sz; i++) {
			ShapeOper2d oldShp = other.getAt(i);
			ShapeOper2d newShp = new ShapeOper2d(oldShp);

			this.lsShpOper2d.add(newShp);
		}
	}
	
	public void loadFrom(Node nGeomShape2d)
	{
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		int iAnnotation = XmlUtil.getAttrAsIntByName(nGeomShape2d, AppDefs.SHPFILE_TAGPARM_SHAPE_GEOMSHAPE2D_ANNOTATION);
		if(iAnnotation == 1)
			this.bAnnotation = true;
		
		NodeList lsShapeOper2d = nGeomShape2d.getChildNodes();
		for(int i = 0; i < lsShapeOper2d.getLength(); i++) {
			Node nShapeOper2d = lsShapeOper2d.item(i);
			
			String strNodeName = nShapeOper2d.getNodeName();
			if( AppDefs.SHPFILE_TAG_SHAPE_GEOMSHAPE2D_SHAPEOPER2D.equals(strNodeName) ) {
				int drawLine = XmlUtil.getAttrAsIntByName(nShapeOper2d, AppDefs.SHPFILE_TAGPARM_SHAPE_GEOMSHAPE2D_SHAPEOPER2D_DRAWLINE);
				
				boolean bDrawLine = false;
				if(drawLine == 1) 
					bDrawLine = true;
				
				double xp = XmlUtil.getAttrAsDoubleByName(nf6, nShapeOper2d, AppDefs.SHPFILE_TAGPARM_SHAPE_GEOMSHAPE2D_SHAPEOPER2D_X);
				double yp = XmlUtil.getAttrAsDoubleByName(nf6, nShapeOper2d, AppDefs.SHPFILE_TAGPARM_SHAPE_GEOMSHAPE2D_SHAPEOPER2D_Y);
				
				ShapeOper2d o = new ShapeOper2d(bDrawLine, xp, yp);
				this.lsShpOper2d.add(o);
			}
		}
	}
	
	/* LIST */

	public synchronized int size()
	{
		int sz = this.lsShpOper2d.size();
		return sz;
	}

	public synchronized void add(ShapeOper2d o)
	{
		this.lsShpOper2d.add(o);
	}

	public synchronized ShapeOper2d getAt(int pos)
	{
		int sz = this.lsShpOper2d.size();
		if(pos < sz) {
			ShapeOper2d o = this.lsShpOper2d.get(pos);
			return o;
		}
		return null;
	}
		
	/* TO_SHAPE */
	
	public void toShape(String shapeName, String shapeFile)
	{
		String shape2dXml = this.toShapeOper2d();

		String xml = String.format(
			"<?xml version='1.0'?><Shape name=\"%s\"><GeomShape2d annotation=\"%s\">%s</GeomShape2d></Shape>",
			shapeName,
			( this.isAnnotation() ) ? "1" : "0",
			shape2dXml );
		FileUtil.writeData(shapeFile, xml);
	}
	
	public String toShapeOper2d() {
		StringBuilder sb = new StringBuilder();
		
		for(ShapeOper2d oper2d : this.lsShpOper2d) {
			GeomPoint2d pt2d = oper2d.getPt2d();			
			String str = String.format(
				"<ShapeOper2d drawLine=\"%s\" x=\"%s\" y=\"%s\"/>",
				( oper2d.isDrawLine() ) ? "1" : "0",
				pt2d.getX(),
				pt2d.getY() );
			sb.append(str);
		}
		return sb.toString();
	}
	
	/* Getters/Setters */

	public boolean isAnnotation() {
		return bAnnotation;
	}

	public void setAnnotation(boolean bAnnotation) {
		this.bAnnotation = bAnnotation;
	}

	public ArrayList<ShapeOper2d> getLsShpOper2d() {
		return lsShpOper2d;
	}

	public void setLsShpOper2d(ArrayList<ShapeOper2d> lsShpOper2d) {
		this.lsShpOper2d = lsShpOper2d;
	}
	
}
