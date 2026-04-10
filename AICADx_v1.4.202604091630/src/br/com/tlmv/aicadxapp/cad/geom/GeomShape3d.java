/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * GeomShape3d.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 20/04/2025
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

package br.com.tlmv.aicadxapp.cad.geom;

import java.text.NumberFormat;
import java.util.ArrayList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeFace3d;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.XmlUtil;

public class GeomShape3d 
{
//Private
	private boolean bAnnotation = false;
	private ArrayList<ShapeFace3d> lsFace3d = null;
	
//Public
	
	public GeomShape3d()
	{
		this.init(false);
	}
	
	public GeomShape3d(boolean bAnnotation)
	{
		this.init(bAnnotation);
	}
	
	public GeomShape3d(GeomShape3d other)
	{
		this.init(other);
	}
	
	/* Methodes */
	
	public void init(boolean bAnnotation)
	{
		this.bAnnotation = bAnnotation;
		this.lsFace3d = new ArrayList<ShapeFace3d>();
	}

	public void init(GeomShape3d other)
	{
		this.bAnnotation = other.isAnnotation();
		this.lsFace3d = new ArrayList<ShapeFace3d>();
		
		int sz = other.size();
		for(int i = 0; i < sz; i++) {
			ShapeFace3d oldShp = other.getAt(i);
			ShapeFace3d newShp = new ShapeFace3d(oldShp);

			this.lsFace3d.add(newShp);
		}
	}
	
	public void loadFrom(Node nGeomShape3d)
	{
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		int iAnnotation = XmlUtil.getAttrAsIntByName(nGeomShape3d, AppDefs.SHPFILE_TAGPARM_SHAPE_GEOMSHAPE3D_ANNOTATION);
		if(iAnnotation == 1)
			this.bAnnotation = true;
		
		NodeList lsShapeFace3d = nGeomShape3d.getChildNodes();
		for(int i = 0; i < lsShapeFace3d.getLength(); i++) {
			Node nShapeFace3d = lsShapeFace3d.item(i);
			
			String strNodeName = nShapeFace3d.getNodeName();
			if( AppDefs.SHPFILE_TAG_SHAPE_GEOMSHAPE3D_SHAPEFACE3D.equals(strNodeName) ) {
				int fill = XmlUtil.getAttrAsIntByName(nShapeFace3d, AppDefs.SHPFILE_TAGPARM_SHAPE_GEOMSHAPE3D_SHAPEFACE3D_FILL);
				
				boolean bFill = false;
				if(fill == 1) 
					bFill = true;
	
				ShapeFace3d oFace = new ShapeFace3d(bFill);
				
				NodeList lsVertice = nShapeFace3d.getChildNodes();
				for(int j = 0; j < lsVertice.getLength(); j++) {
					Node nVertice = lsVertice.item(j);
					
					String strNodeName1 = nVertice.getNodeName();
					if( AppDefs.SHPFILE_TAG_SHAPE_GEOMSHAPE3D_SHAPEFACE3D_VERTICE.equals(strNodeName1) ) {
						double xp = XmlUtil.getAttrAsDoubleByName(nf6, nVertice, AppDefs.SHPFILE_TAGPARM_SHAPE_GEOMSHAPE3D_SHAPEFACE3D_VERTICE_X);
						double yp = XmlUtil.getAttrAsDoubleByName(nf6, nVertice, AppDefs.SHPFILE_TAGPARM_SHAPE_GEOMSHAPE3D_SHAPEFACE3D_VERTICE_Y);
						double zp = XmlUtil.getAttrAsDoubleByName(nf6, nVertice, AppDefs.SHPFILE_TAGPARM_SHAPE_GEOMSHAPE3D_SHAPEFACE3D_VERTICE_Z);
					
						GeomPoint3d oPt = new GeomPoint3d(xp, yp, zp);
						oFace.addPoint(oPt);
					}
				}
				this.lsFace3d.add(oFace);
			}
		}
	}
	
	/* LIST */

	public synchronized int size()
	{
		int sz = this.lsFace3d.size();
		return sz;
	}

	public synchronized void add(ShapeFace3d o)
	{
		this.lsFace3d.add(o);
	}
	
	public synchronized ShapeFace3d getAt(int pos)
	{
		int sz = this.lsFace3d.size();
		if(pos < sz) {
			ShapeFace3d o = this.lsFace3d.get(pos);
			return o;
		}
		return null;
	}
	
	/* Getters/Setters */

	public boolean isAnnotation() {
		return bAnnotation;
	}

	public void setAnnotation(boolean bAnnotation) {
		this.bAnnotation = bAnnotation;
	}

	public ArrayList<ShapeFace3d> getLsFace3d() {
		return lsFace3d;
	}

	public void setLsFace3d(ArrayList<ShapeFace3d> lsFace3d) {
		this.lsFace3d = lsFace3d;
	}
	
}
