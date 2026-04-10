/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * AlinhamentoEstacaPointDrenagemOData.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 08/08/2025
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

package br.com.tlmv.aicadxmod.drenagem.cad;

import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.ICadObject;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.utils.FormatUtil;

public class CadAlinhamentoEstacaPointDrenagemOData extends CadObject
{
//Private
	private String alinhamentoEstacaDrenagemId = AppDefs.NULL_INTSTR;
    private GeomPoint3d ptEixo = null;
    private GeomPoint3d ptEstacaDireita = null;
    private GeomPoint3d ptEstacaEsquerda = null;
    private GeomVector3d vDirAtual = null;
    private GeomVector3d vDirProximo = null;
    private int numEstaca = 0;
    private double distancia = 0.0;
    private boolean bCI = false;
    private boolean bEstaca = false;
    //
	private double distAlinhamentoEstacaEixo = AppDefs.DEF_DEFAULT_DRENAGEM_DISTALINHAMENTOESTACAEIXO;
    
//Public
	
    public CadAlinhamentoEstacaPointDrenagemOData()
    {
    	super(AppDefs.OBJTYPE_ALINHAMENTOESTACAITEM_ODATA, null, null);
    }
	
    public CadAlinhamentoEstacaPointDrenagemOData(CadDocumentDef doc)
    {
    	super(AppDefs.OBJTYPE_ALINHAMENTOESTACAITEM_ODATA, doc, null);
    }
	
    public CadAlinhamentoEstacaPointDrenagemOData(
		CadDocumentDef doc,		
		String alinhamentoEstacaDrenagemId,
	    GeomPoint3d ptEixo,
	    GeomVector3d vDirAtual,
	    GeomVector3d vDirProximo,
	    int numEstaca,
	    double distancia,
	    boolean bCI,
	    boolean bEstaca)
    {
    	super(AppDefs.OBJTYPE_ALINHAMENTOESTACAITEM_ODATA, doc, null);
    	
    	this.init(
			alinhamentoEstacaDrenagemId,
	    	ptEixo,
		    vDirAtual,
		    vDirProximo,
	        numEstaca,
	        distancia,
	        bCI,
	        bEstaca);
    }
	
    public CadAlinhamentoEstacaPointDrenagemOData(CadAlinhamentoEstacaPointDrenagemOData other)
    {
    	super( AppDefs.OBJTYPE_ALINHAMENTOESTACAITEM_ODATA, 
    		   other.getDocument(), 
    		   null, 
    		   other.getCadRefEntityId() );
    	
    	this.init(
			other.getAlinhamentoEstacaDrenagemId(),
			other.getPtEixo(),
			other.getVDirAtual(),
			other.getVDirProximo(),
			other.getNumEstaca(),
			other.getDistancia(),
			other.isCI(),
	        other.isEstaca() );
    }

    /* Methodes */

    public void init(
		String alinhamentoEstacaDrenagemId,
	    GeomPoint3d ptEixo,
	    GeomVector3d vDirAtual,
	    GeomVector3d vDirProximo,
	    int numEstaca,
	    double distancia,
	    boolean bCI,
	    boolean bEstaca) 
    {
    	this.alinhamentoEstacaDrenagemId = alinhamentoEstacaDrenagemId;
    	this.ptEixo = new GeomPoint3d( ptEixo );
    	this.vDirAtual = new GeomVector3d( vDirAtual );
    	this.vDirProximo = new GeomVector3d( vDirProximo );
        this.numEstaca = numEstaca;
        this.distancia = distancia;
        this.bCI = bCI;
        this.bEstaca = bEstaca;

        //DIRECTION LEFT/RIGHT
        //
	    GeomVector2d vDirAtual2d = new GeomVector2d( this.vDirAtual );
	    GeomVector2d uDirAtual2d = vDirAtual2d.otherUnit();
	    GeomVector2d nDirAtual2d = uDirAtual2d.otherNorm();

	    GeomVector2d vDirProximo2d = new GeomVector2d( this.vDirProximo );
	    GeomVector2d uDirProxima2d = vDirProximo2d.otherUnit();
	    GeomVector2d nDirProxima2d = uDirProxima2d.otherNorm();
	    
	    GeomVector3d v = vDirAtual2d.vectProd(vDirProximo2d);
    	double dZ = v.getZOrig();
    	if(Math.abs( dZ ) < AppDefs.MATHPREC_MIN) {
            //ESTACAS DIREITA/ESQUERDA (MESMA DIRECAO)
            //
            GeomPoint2d ptEixo2d = new GeomPoint2d( this.ptEixo );

            GeomPoint2d ptEstacaDireita2d = ptEixo2d.otherMoveTo(nDirAtual2d, distAlinhamentoEstacaEixo);
            GeomPoint2d ptEstacaEsquerda2d = ptEixo2d.otherMoveTo(nDirAtual2d, - distAlinhamentoEstacaEixo);

            this.ptEstacaDireita = new GeomPoint3d( ptEstacaDireita2d.getX(), ptEstacaDireita2d.getY(), ptEixo.getZ() );
            this.ptEstacaEsquerda = new GeomPoint3d( ptEstacaEsquerda2d.getX(), ptEstacaEsquerda2d.getY(), ptEixo.getZ() );
    	}
    	else {
            //ESTACAS DIREITA/ESQUERDA (MUDANCA DIRECAO)
            //
            GeomPoint2d ptEixo2d = new GeomPoint2d( this.ptEixo );

            //PONTO_AUXILIAR_ESQUERDA
            GeomPoint2d ptEstacaEsquerdaAtual2d = ptEixo2d.otherMoveTo(nDirAtual2d, - distAlinhamentoEstacaEixo);
            GeomPoint2d ptEstacaEsquerdaProxima2d = ptEixo2d.otherMoveTo(nDirProxima2d, - distAlinhamentoEstacaEixo);
            
            GeomPoint2d ptEstacaEsquerdaAux2d = GeomUtil.midPointOf(ptEstacaEsquerdaAtual2d, ptEstacaEsquerdaProxima2d);
            
            double dL1 = ptEstacaEsquerdaAtual2d.distTo(ptEstacaEsquerdaAux2d);
            double dD1 = ptEixo2d.distTo(ptEstacaEsquerdaAux2d);

            double dL2 = (dL1 * dL1) / dD1;
            double dT = dD1 + dL2;
            
    	    GeomVector2d vEstacaEsquerda2d = new GeomVector2d(ptEixo2d, ptEstacaEsquerdaAux2d);

             //PONTO_FINAL_ESQUERDA
       	    GeomPoint2d ptEstacaEsquerda2d = ptEixo2d.otherMoveTo(vEstacaEsquerda2d, dT);
            this.ptEstacaEsquerda = new GeomPoint3d( ptEstacaEsquerda2d.getX(), ptEstacaEsquerda2d.getY(), ptEixo.getZ() );

            //PONTO_FINAL_DIREITA
       	    GeomPoint2d ptEstacaDireita2d = ptEixo2d.otherMoveTo(vEstacaEsquerda2d, - dT);
            this.ptEstacaDireita = new GeomPoint3d( ptEstacaDireita2d.getX(), ptEstacaDireita2d.getY(), ptEixo.getZ() );
    	}
    }
	
    public void init(
    	String alinhamentoEstacaDrenagemId,
    	GeomPoint3d ptEixo,
    	GeomPoint3d ptEstacaDireita,
    	GeomPoint3d ptEstacaEsquerda,
    	GeomVector3d vDirAtual,
    	GeomVector3d vDirProximo,
    	int numEstaca,
    	double distancia,
    	boolean bCI,
    	boolean bEstaca)
    {
    	this.alinhamentoEstacaDrenagemId = alinhamentoEstacaDrenagemId;
    	this.ptEixo = ptEixo;
    	this.ptEstacaDireita = ptEstacaDireita;
    	this.ptEstacaEsquerda = ptEstacaEsquerda;
    	this.vDirAtual = vDirAtual;
    	this.vDirProximo = vDirProximo;
    	this.numEstaca = numEstaca;
    	this.distancia = distancia;
    	this.bCI = bCI;
    	this.bEstaca = bEstaca;
    }
    
	@Override
	public void init(ICadObject o) {
		CadAlinhamentoEstacaPointDrenagemOData other = (CadAlinhamentoEstacaPointDrenagemOData)o; 
		
    	this.init(
			other.getAlinhamentoEstacaDrenagemId(),
			other.getPtEixo(),
			other.getVDirAtual(),
			other.getVDirProximo(),
			other.getNumEstaca(),
			other.getDistancia(),
			other.isCI(),
	        other.isEstaca() );
	} 
    
	/* CREATExxx */
    
    public static CadAlinhamentoEstacaPointDrenagemOData create(
		CadDocumentDef doc,    		
    	String cadRefEntityId,
    	//
	    GeomPoint3d ptEixo,
	    GeomVector3d vDirAtual,
	    GeomVector3d vDirProximo,
	    //
	    int numEstaca,
	    double distancia,
	    boolean bCI,
	    boolean bEstaca) 
    {
    	CadAlinhamentoEstacaPointDrenagemOData o = new CadAlinhamentoEstacaPointDrenagemOData(doc);

        o.init(
    		cadRefEntityId, 
    	    ptEixo,
    	    vDirAtual,
    	    vDirProximo,
    	    numEstaca,
    	    distancia,
    	    bCI,
    	    bEstaca); 
        return o;
    }
    
    public static CadAlinhamentoEstacaPointDrenagemOData create(
    	CadBlockDef oBlkDef,
    	String cadRefEntityId,
    	//
    	GeomPoint3d ptEixo,
    	GeomPoint3d ptEstacaDireita,
    	GeomPoint3d ptEstacaEsquerda,
    	GeomVector3d vDirAtual,
    	GeomVector3d vDirProximo,
	    //
    	int numEstaca,
    	double distancia,
    	boolean bCI,
    	boolean bEstaca)
    {
    	CadAlinhamentoEstacaPointDrenagemOData o = new CadAlinhamentoEstacaPointDrenagemOData(oBlkDef.getDocument());

        o.init(
    		cadRefEntityId, 
    	    ptEixo,
        	ptEstacaDireita,
        	ptEstacaEsquerda,
    	    vDirAtual,
    	    vDirProximo,
    	    numEstaca,
    	    distancia,
    	    bCI,
    	    bEstaca); 
        return o;
    }
	
    public static CadAlinhamentoEstacaPointDrenagemOData create(CadAlinhamentoEstacaPointDrenagemOData other)
    {
    	CadAlinhamentoEstacaPointDrenagemOData o = new CadAlinhamentoEstacaPointDrenagemOData(other.getDocument());

    	o.init(
    	    other.getAlinhamentoEstacaDrenagemId(), 
    	    other.getPtEixo(),
    	    other.getVDirAtual(),
    	    other.getVDirProximo(),
    	    other.getNumEstaca(),
    	    other.getDistancia(),
    	    other.isCI(),
    	    other.isEstaca()); 
        return o;
    }
    
	/* TO_STRING */
       
    @Override
	public String toString() {
    	String str = this.toStr(); 
    	return str;		
	}

	/* DEBUG */

	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);

		String str = String.format(
    		"AlinhamentoEstacaDrenagemId:%s;" +
    		"PtEixo:%s;" +
    		"PtEixoDireita:%s;" +
    		"PtEixoEsquerda:%s;" +
    		"NumEstaco:%s;" +
    		"Distancia:%s;" +
    		"IsCI:%s;" +
    		"IsEstaca:%s;",
			this.alinhamentoEstacaDrenagemId,
		    this.ptEixo.toStr(),
		    this.ptEstacaDireita.toStr(),
		    this.ptEstacaEsquerda.toStr(),
		    this.numEstaca,
		    nf6.format( this.distancia ),
		    ( this.bCI ) ? AppDefs.DEF_TEXT_SIM : AppDefs.DEF_TEXT_NAO,
    		( this.bEstaca ) ? AppDefs.DEF_TEXT_SIM : AppDefs.DEF_TEXT_NAO);
		return str;
	}
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
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
    
	/* RESET */

	@Override
	public void reset() {
		// TODO:
	}

    /* Getter/Setters */

	public String getAlinhamentoEstacaDrenagemId() {
		return alinhamentoEstacaDrenagemId;
	}

	public void setAlinhamentoEstacaDrenagemId(String alinhamentoEstacaDrenagemId) {
		this.alinhamentoEstacaDrenagemId = alinhamentoEstacaDrenagemId;
	}

	public int getNumEstaca() {
		return numEstaca;
	}

	public void setNumEstaca(int numEstaca) {
		this.numEstaca = numEstaca;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	public GeomPoint3d getPtEixo() {
		return ptEixo;
	}

	public void setPtEixo(GeomPoint3d ptEixo) {
		this.ptEixo = ptEixo;
	}

	public GeomPoint3d getPtEstacaDireita() {
		return ptEstacaDireita;
	}

	public void setPtEstacaDireita(GeomPoint3d ptEstacaDireita) {
		this.ptEstacaDireita = ptEstacaDireita;
	}

	public GeomPoint3d getPtEstacaEsquerda() {
		return ptEstacaEsquerda;
	}

	public void setPtEstacaEsquerda(GeomPoint3d ptEstacaEsquerda) {
		this.ptEstacaEsquerda = ptEstacaEsquerda;
	}

	public boolean isCI() {
		return bCI;
	}

	public void setCI(boolean bCI) {
		this.bCI = bCI;
	}

	public boolean isEstaca() {
		return bEstaca;
	}

	public void setEstaca(boolean bEstaca) {
		this.bEstaca = bEstaca;
	}

	public GeomVector3d getVDirAtual() {
		return vDirAtual;
	}

	public void setVDirAtual(GeomVector3d vDirAtual) {
		this.vDirAtual = vDirAtual;
	}

	public GeomVector3d getVDirProximo() {
		return vDirProximo;
	}

	public void setVDirProximo(GeomVector3d vDirProximo) {
		this.vDirProximo = vDirProximo;
	}

}
