/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * ShapeTable.java
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

package br.com.tlmv.aicadxapp.cad.tables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.ICadObject;
import br.com.tlmv.aicadxapp.cad.geom.shape.Shape;
import br.com.tlmv.aicadxapp.dao.BaseDao;

public class ShapeTable extends CadObject 
{
//Private
	private Hashtable<String,Shape> shapeTable;
	
//Public
	
	public ShapeTable(CadDocumentDef doc) {
		super(AppDefs.OBJTYPE_SHAPE_TABLE, doc, null);
		this.init();
	}
	
	private void init() {
		this.shapeTable = new Hashtable<String,Shape>();
	}
	
	@Override
	public void init(ICadObject o) {
		//TODO:
	}
	
	@Override
	public void reset() {
		Hashtable<String,Shape> newShapeTable = new Hashtable<String,Shape>();
		
		Collection colShape = this.shapeTable.values();
		Iterator iterShape = colShape.iterator();
		while( iterShape.hasNext() ) {
			Shape oShape = (Shape)iterShape.next();

			String shapeName = oShape.getName();
			newShapeTable.put(shapeName, oShape);
		}
		this.shapeTable = newShapeTable;
	}

	/* Methodes */
	
	public synchronized boolean hasShape(String name) {
		if( this.shapeTable.containsKey(name) ) {
			return true;
		}		
		return false;
	}
	
	public synchronized Shape newShape(Shape shape) {
		Shape oResult = new Shape(shape);

		String name = shape.getName();
		String fileName = shape.getFileName();
		
		if( this.shapeTable.containsKey(name) ) {
			oResult = (Shape)this.shapeTable.remove(name);
		}		
		oResult = new Shape(shape);
		this.shapeTable.put(oResult.getName(), oResult);
		return oResult;
	}
	
	public synchronized Shape newShape(String name, String fileName, double defaultZ) {
		Shape oResult = null;

		if( this.shapeTable.containsKey(name) ) {
			oResult = (Shape)this.shapeTable.get(name);
		}
		else {
			oResult = new Shape(this.getDocument(), name, fileName, defaultZ);
			this.shapeTable.put(oResult.getName(), oResult);
		}
		return oResult;
	}

	public synchronized Shape getShape(String name) {
		Shape oResult = null;

		if( this.shapeTable.containsKey(name) ) {
			oResult = (Shape)this.shapeTable.get(name);
		}		
		return oResult;
	}

	public synchronized ArrayList<Shape> getAllShape() {
		ArrayList<Shape> lsResult = new ArrayList<Shape>();

		Collection<Shape> colShape = this.shapeTable.values();
		for(Shape oShape : colShape) {
			lsResult.add(oShape);
		}
		return lsResult;
	}

	/* DEBUG */
	
	@Override
	public String toStr() {
		return null;
	}

	@Override
	public void debug(int debugLevel) {
		//TODO:
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

}
