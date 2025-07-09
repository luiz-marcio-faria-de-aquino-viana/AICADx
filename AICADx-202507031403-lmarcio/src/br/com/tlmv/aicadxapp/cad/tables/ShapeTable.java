/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * ShapeTable.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 20/04/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.tables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppDatabase;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.geom.shape.Shape;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;

public class ShapeTable extends CadObject 
{
//Private
	private Hashtable<String,Shape> shapeTable;
	
//Public
	
	public ShapeTable() {
		super(AppDefs.OBJTYPE_SHAPE_TABLE);
		this.init();
	}
	
	private void init() {
		this.shapeTable = new Hashtable<String,Shape>();
	}
	
	@Override
	public void reset() {
		//TODO:
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
		oResult = new Shape(fileName);
		this.shapeTable.put(oResult.getName(), oResult);
		return oResult;
	}
	
	public synchronized Shape newShape(String name, String fileName) {
		Shape oResult = null;

		if( this.shapeTable.containsKey(name) ) {
			oResult = (Shape)this.shapeTable.get(name);
		}
		else {
			oResult = new Shape(fileName);
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
	public void load(AppDatabase db, String schemaName, CadDocumentDef doc)
	{
		
	}

	@Override
	public void save(AppDatabase db, String schemaName, CadDocumentDef doc)
	{

	}

}
