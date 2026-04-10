/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * Shape.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 21/04/2025
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

package br.com.tlmv.aicadxapp.cad.geom.shape;

import java.io.FileReader;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import br.com.tlmv.aicadxapp.AppCtx;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadParamMargemOData;
import br.com.tlmv.aicadxapp.cad.geom.GeomShape2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomShape3d;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.utils.XmlUtil;
import br.com.tlmv.aicadxmod.eletrica.cad.CadParamEletricoOData;

public class Shape 
{
//Private
	private CadDocumentDef doc = null;
	private String name = null;					//   1 -  80
	private String fileName = null;				//  82 - 161
	private double defaultZ = 0.0;				// 163 - 172
    private ArrayList<CadParamEletricoOData> lsParamEletrico = null;
    private ArrayList<CadParamMargemOData> lsParamMargem = null;

    private GeomShape2d planView2d = null;
    private GeomShape3d modelView3d = null;

//Public
    
    public Shape(CadDocumentDef doc, String name, String fileName, double defaultZ)
    {
    	this.init(doc, name, fileName, defaultZ);
    }
    
    public Shape(Shape shape)
    {
    	this.init(shape);
    }
    
    /* Methodes */
    
    public void init(CadDocumentDef doc, String name, String fileName, double defaultZ)
    {
    	this.doc = doc;
    	this.name = name;
    	this.fileName = fileName;
    	this.defaultZ = defaultZ;    	
    	this.lsParamEletrico = new ArrayList<CadParamEletricoOData>();
    	this.lsParamMargem = new ArrayList<CadParamMargemOData>();
    	
    	this.loadFile(this.fileName);
    }
    
    public void init(Shape shape)
    {
    	this.name = shape.getName();
    	this.fileName = shape.getFileName();
    	this.defaultZ = shape.getDefaultZ();
    	this.lsParamEletrico = new ArrayList<CadParamEletricoOData>();
    	this.lsParamMargem = new ArrayList<CadParamMargemOData>();

    	this.addAllParamEletrico(shape);
    	this.addAllParamMargem(shape);

    	this.planView2d = new GeomShape2d(shape.getPlanView2d());
    	this.modelView3d = new GeomShape3d(shape.getModelView3d());
    }
    
	public void loadFile(String fileName)
	{
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			InputSource is = new InputSource(new FileReader(fileName));
		    Document doc = db.parse(is);
		    
		    //LOAD: Shape Data
		    
		    NodeList lsShape = doc.getElementsByTagName(AppDefs.SHPFILE_TAG_SHAPE);
		    int szLsShape = lsShape.getLength();
		    if(szLsShape > 0) {
		    	Node nShape = lsShape.item(0);  

	    		String name = XmlUtil.getAttrAsStringByName(nShape, AppDefs.SHPFILE_TAGPARM_SHAPE_NAME);
	    		if( (name != null) && (name.compareToIgnoreCase(this.name) == 0) ) {
	    			String warnmsg = String.format("Loading shape file: %s...", name);
	    			PromptUtil.prompt(warnmsg);
	    		}
	    		else {
	    			String warnmsg = String.format("Loading shape file: %s... (Wrong shape name)", name);
	    			PromptUtil.prompt(warnmsg);
	    		}
		    }		    
		    
		    //LOAD: ParamEletrico Data
		    
		    NodeList lsParamEletrico = doc.getElementsByTagName(AppDefs.SHPFILE_TAG_SHAPE_PARAMELETRICO);
		    int szLsParamEletrico = lsParamEletrico.getLength();
		    if(szLsParamEletrico > 0) {
		    	for(int i = 0; i < szLsParamEletrico; i++) {
			    	Node nParamEletrico = lsParamEletrico.item(i);  
			    	
			    	CadParamEletricoOData oParamEletrico = new CadParamEletricoOData(this.doc);
			    	oParamEletrico.loadFrom(nParamEletrico);
			    	
			    	this.lsParamEletrico.add(oParamEletrico);
		    	}
		    }
		    
		    //LOAD: ParamEletrico Data
		    
		    NodeList lsParamMargem = doc.getElementsByTagName(AppDefs.SHPFILE_TAG_SHAPE_PARAMMARGEM);
		    int szLsParamMargem = lsParamMargem.getLength();
		    if(szLsParamMargem > 0) {
		    	for(int i = 0; i < szLsParamMargem; i++) {
			    	Node nParamMargem = lsParamMargem.item(i);  
			    	
			    	CadParamMargemOData oParamMargem = new CadParamMargemOData(this.doc);
			    	oParamMargem.loadFrom(nParamMargem);
			    	
			    	this.lsParamMargem.add(oParamMargem);
		    	}
		    }
		    
		    //LOAD: GeomShape2d Data
		    
		    NodeList lsGeomShape2d = doc.getElementsByTagName(AppDefs.SHPFILE_TAG_SHAPE_GEOMSHAPE2D);
		    int szLsGeomShape2d = lsGeomShape2d.getLength();
		    if(szLsGeomShape2d > 0) {
		    	Node nGeomShape2d = lsGeomShape2d.item(0);  
		    	
		    	this.planView2d = new GeomShape2d();
		    	this.planView2d.loadFrom(nGeomShape2d);
		    }
		    
		    //LOAD: GeomShape3d Data
		    
		    NodeList lsGeomShape3d = doc.getElementsByTagName(AppDefs.SHPFILE_TAG_SHAPE_GEOMSHAPE3D);
		    int szLsGeomShape3d = lsGeomShape3d.getLength();
		    if(szLsGeomShape3d > 0) {
		    	Node nGeomShape3d = lsGeomShape3d.item(0);  
		    	
		    	this.modelView3d = new GeomShape3d();
		    	this.modelView3d.loadFrom(nGeomShape3d);
		    }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
    
	/* CREATE_FROM STRING_DATA */
	
	//12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890
	//         1         2         3         4         5         6         7         8         9         0         1         2         3         4         5         6         7         8         9         0
	//                                                                                                   1         1         1         1         1         1         1         1         1         1         2
	//
	//1-80                                                                             82-161                                                                           163-172
	//SHAPE                                                                            SHAPE_FILE                                                                       Z          (DON'T ERASE THIS LINE!)
	//-------------------------------------------------------------------------------- -------------------------------------------------------------------------------- ---------- (DON'T ERASE THIS LINE!)
	//EL-Ponto_Luz_Teto                                                                EL/EL-Ponto_Luz_Teto.ais                                                         2.7
	//		  :
	//
	public static Shape createFrom(CadDocumentDef doc, String str)
	{
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		AppMain app = AppMain.getApp();
		
		AppCtx ctx = app.getCtx();
		
		if(str.length() < 164) 
			return null;
		
		String strName = StringUtil.trimAll(str.substring(0, 79));
		String strFileName = StringUtil.trimAll(str.substring(81, 160));
		String strDefaultZ = StringUtil.trimAll(str.substring(162));
		
		String strFullFileName = ctx.getShapesDir() + strFileName;
		double defaultZ = StringUtil.safeDbl(nf6, strDefaultZ);
		
		Shape oNewShape = new Shape(doc, strName, strFullFileName, defaultZ);
		return oNewShape;
	}
	
	/* LIST - PARAMELETRICO */
	
    public synchronized int getSzLsParamEletrico() {
    	return this.lsParamEletrico.size();
    }
	
    public synchronized CadParamEletricoOData getParamEletricoAt(int pos) {
    	int sz = this.lsParamEletrico.size();
    	if(pos < sz) {
    		CadParamEletricoOData o = this.lsParamEletrico.get(pos);
    		return o;
    	}
    	return null;
    }
	
    public synchronized void addAllParamEletrico(Shape shape) {
    	int sz = shape.getSzLsParamEletrico();
    	for(int i = 0; i < sz; i++) {
    		CadParamEletricoOData oParam = shape.getParamEletricoAt(i);
    		this.addParamEletrico( new CadParamEletricoOData(oParam) );
    	}
    }
    
    public synchronized void addParamEletrico(CadParamEletricoOData o) {
    	this.lsParamEletrico.add(o);
    }
	
	/* LIST - PARAMMARGEM */
	
    public synchronized int getSzLsParamMargem() {
    	return this.lsParamMargem.size();
    }
	
    public synchronized CadParamMargemOData getParamMargemAt(int pos) {
    	int sz = this.lsParamMargem.size();
    	if(pos < sz) {
    		CadParamMargemOData o = this.lsParamMargem.get(pos);
    		return o;
    	}
    	return null;
    }
	
    public synchronized void addAllParamMargem(Shape shape) {
    	int sz = shape.getSzLsParamMargem();
    	for(int i = 0; i < sz; i++) {
    		CadParamMargemOData oParam = shape.getParamMargemAt(i);
    		this.addParamMargem( new CadParamMargemOData(oParam) );
    	}
    }
    
    public synchronized void addParamMargem(CadParamMargemOData o) {
    	this.lsParamMargem.add(o);
    }
	
    /* Getters/Setters */

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GeomShape2d getPlanView2d() {
		return planView2d;
	}

	public CadDocumentDef getDocument() {
		return doc;
	}

	public void setDocument(CadDocumentDef doc) {
		this.doc = doc;
	}

	public void setPlanView2d(GeomShape2d planView2d) {
		this.planView2d = planView2d;
	}

	public GeomShape3d getModelView3d() {
		return modelView3d;
	}

	public void setModelView3d(GeomShape3d modelView3d) {
		this.modelView3d = modelView3d;
	}

	public ArrayList<CadParamEletricoOData> getLsParamEletrico() {
		return lsParamEletrico;
	}

	public void setLsParamEletrico(ArrayList<CadParamEletricoOData> lsParamEletrico) {
		this.lsParamEletrico = lsParamEletrico;
	}

	public ArrayList<CadParamMargemOData> getLsParamMargem() {
		return lsParamMargem;
	}

	public void setLsParamMargem(ArrayList<CadParamMargemOData> lsParamMargem) {
		this.lsParamMargem = lsParamMargem;
	}

	public double getDefaultZ() {
		return defaultZ;
	}

	public void setDefaultZ(double defaultZ) {
		this.defaultZ = defaultZ;
	}
    
}
