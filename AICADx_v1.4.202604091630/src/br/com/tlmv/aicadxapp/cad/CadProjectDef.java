/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadProjectDef.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 14/04/2025
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
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.cad.ecache.DrawCache;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomDimension3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeResult;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.dao.record.CadProjectDefRecord;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData2dVO;
import br.com.tlmv.aicadxapp.vo.ScaleData2dVO;

public class CadProjectDef extends CadEntity
{
//Private
	private GeomPoint3d ptOrigem;
	private GeomVector3d xDir;

	//PROJECT_ATTR
	//
	private String codigoProjeto;
	private String tituloProjeto;
	private String descricaoProjeto;
	//
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String municipio;
	private String estado;
	private String cep;
	//
	private String art;
	private String nomeResponsavelTecnico;
	private String registroResponsavelTecnico;
	private String telefoneResponsavelTecnico;
	private String emailResponsavelTecnico;
	//
	private String espgCode;
	private int codigoPluviografo;							// codigo local medicao volume chuva
	private String pluviografo;								// nome local medicao volume chuva
	private double coefManning;
	private double periodoRecorrencia;
	//
	private double escala;
	private double papelLargura;
	private double papelAltura;	
	private double unidade; 
	private double scaleFactor;
	private double areaLargura;
	private double areaAltura;	
	
//Public

    public CadProjectDef() {
		super(AppDefs.OBJTYPE_PROJECT_DEF, null, null, null, 0.0, false);		
    }

    public CadProjectDef(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_PROJECT_DEF, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }
	
	/* Methodes */
	
	public void init(
		GeomPoint3d ptOrigem,
		GeomVector3d xDir,
		//	
		String codigoProjeto,
		String tituloProjeto,
		String descricaoProjeto,
		//
		String logradouro,
		String numero,
		String complemento,
		String bairro,
		String municipio,
		String estado,
		String cep,
		//
		String art,
		String nomeResponsavelTecnico,
		String registroResponsavelTecnico,
		String telefoneResponsavelTecnico,
		String emailResponsavelTecnico,
		//
		String espgCode,
		int codigoPluviografo,
		String pluviografo,
		double coefManning,
		double periodoRecorrencia,
		//
		double escala,
		double papelLargura,
		double papelAltura ) 
	{
		this.ptOrigem = new GeomPoint3d(ptOrigem);
		this.xDir = new GeomVector3d(xDir);
		//
		this.codigoProjeto = codigoProjeto;
		this.tituloProjeto = tituloProjeto;
		this.descricaoProjeto = descricaoProjeto;
		//
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.municipio = municipio;
		this.estado = estado;
		this.cep = cep;
		//
		this.art = art;
		//
		this.nomeResponsavelTecnico = nomeResponsavelTecnico;
		this.registroResponsavelTecnico = registroResponsavelTecnico;
		this.telefoneResponsavelTecnico = telefoneResponsavelTecnico;
		this.emailResponsavelTecnico = emailResponsavelTecnico;
		//
		this.espgCode = espgCode;
		this.codigoPluviografo = codigoPluviografo;
		this.pluviografo = pluviografo;
		this.coefManning = coefManning;
		this.periodoRecorrencia = periodoRecorrencia;
		//
		this.escala = escala;
		this.papelLargura = papelLargura;
		this.papelAltura = papelAltura;
		this.unidade = AppDefs.DEF_DEFAULT_PROJECT_UNIT;
		this.scaleFactor = this.escala / this.unidade;
		this.areaLargura = this.papelLargura * this.scaleFactor;
		this.areaAltura = this.papelAltura * this.scaleFactor; 
	}
		
	@Override
	public void init(ICadObject o) {
		CadProjectDef other = (CadProjectDef)o;

		this.init(
			other.ptOrigem,
			other.xDir,
			//
			other.codigoProjeto,
			other.tituloProjeto,
			other.descricaoProjeto,
			//
			other.logradouro,
			other.numero,
			other.complemento,
			other.bairro,
			other.municipio,
			other.estado,
			other.cep,
			//
			other.art,
			other.nomeResponsavelTecnico,
			other.registroResponsavelTecnico,
			other.telefoneResponsavelTecnico,
			other.emailResponsavelTecnico,
			//
			other.espgCode,
			other.codigoPluviografo,
			other.pluviografo,
			other.coefManning,
			other.periodoRecorrencia,
			//
			other.escala,
			other.papelLargura,
			other.papelAltura );
	}

	/* CREATE */
		
	public static CadProjectDef create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer, 
		CadLevel oLevel,
		//
		GeomPoint2d ptOrigem,
		GeomVector2d xDir,
		//
		String codigoProjeto,
		String tituloProjeto,
		String descricaoProjeto,
		//
		String logradouro,
		String numero,
		String complemento,
		String bairro,
		String municipio,
		String estado,
		String cep,
		//
		String art,
		String nomeResponsavelTecnico,
		String registroResponsavelTecnico,
		String telefoneResponsavelTecnico,
		String emailResponsavelTecnico,
		//
		String espgCode,
		int codigoPluviografo,
		String pluviografo,
		double coefManning,
		double periodoRecorrencia,
		//
		double escala,
		double papelLargura,
		double papelAltura ) 
	{
		GeomPoint3d ptOrigem3d = new GeomPoint3d(ptOrigem);
		GeomVector3d xDir3d = new GeomVector3d(xDir);
    	
    	CadProjectDef oEnt = new CadProjectDef(oBlkDef, oLayer, oLevel, 0.0, false);
    	oEnt.init(
    		ptOrigem3d,
    		xDir3d,
    		//
    		codigoProjeto,
    		tituloProjeto,
    		descricaoProjeto,
    		//
    		logradouro,
    		numero,
    		complemento,
    		bairro,
    		municipio,
    		estado,
    		cep,
    		//
    		art,
    		nomeResponsavelTecnico,
    		registroResponsavelTecnico,
    		telefoneResponsavelTecnico,
    		emailResponsavelTecnico,
    		//
    		espgCode,
    		codigoPluviografo,
    		pluviografo,
    		coefManning,
    		periodoRecorrencia,
    		//
    		escala,
    		papelLargura,
    		papelAltura );
    	return oEnt;
    }
	
	public static CadProjectDef create(
		CadBlockDef oBlkDef,
		CadLayerDef oLayer, 
		CadLevel oLevel,
		//
		GeomPoint3d ptOrigem,
		GeomVector3d xDir,
		//
		String codigoProjeto,
		String tituloProjeto,
		String descricaoProjeto,
		//
		String logradouro,
		String numero,
		String complemento,
		String bairro,
		String municipio,
		String estado,
		String cep,
		//
		String art,
		String nomeResponsavelTecnico,
		String registroResponsavelTecnico,
		String telefoneResponsavelTecnico,
		String emailResponsavelTecnico,
		//
		String espgCode,
		int codigoPluviografo,
		String pluviografo,
		double coefManning,
		double periodoRecorrencia,
		//
		double escala,
		double papelLargura,
		double papelAltura ) 
	{
		CadProjectDef oEnt = new CadProjectDef(oBlkDef, oLayer, oLevel, 0.0, false);
		oEnt.init(
    		ptOrigem,
    		xDir,
	    	//
	    	codigoProjeto,
    		tituloProjeto,
    		descricaoProjeto,
    		//
    		logradouro,
    		numero,
    		complemento,
    		bairro,
    		municipio,
    		estado,
    		cep,
    		//
    		art,
    		nomeResponsavelTecnico,
    		registroResponsavelTecnico,
    		telefoneResponsavelTecnico,
    		emailResponsavelTecnico,
    		//
    		espgCode,
    		codigoPluviografo,
    		pluviografo,
    		coefManning,
    		periodoRecorrencia,
    		//
    		escala,
    		papelLargura,
    		papelAltura );
		return oEnt;
	}
	
	public static CadProjectDef create(CadProjectDef other) {
		CadProjectDef o = new CadProjectDef(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadProjectDef create(CadBlockDef blkDef, CadProjectDef other) {
		CadProjectDef o = new CadProjectDef(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadProjectDef duplicate()
	{
		CadProjectDef other = CadProjectDef.create(this);
		return other;
	}
	
	@Override
	public CadProjectDef duplicate(CadBlockDef blkDef)
	{
		CadProjectDef other = CadProjectDef.create(blkDef, this);
		return other;
	}

	@Override
	public CadProjectDef copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadProjectDef other = CadProjectDef.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
	public CadProjectDef moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.ptOrigem);

    	MoveData2dVO o = GeomUtil.moveToPt2d(ptI2dMcs, ptF2dMcs, ptOrig2dMcs);
    	this.ptOrigem = new GeomPoint3d(o.getPtDest());
		return this;
	}
	
	@Override
	public CadProjectDef scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
    	GeomPoint2d ptOrig2dMcs = new GeomPoint2d(this.ptOrigem);

    	ScaleData2dVO o = GeomUtil.scaleToPt2dByRefDist(refDist, ptI2dMcs, ptF2dMcs, ptOrig2dMcs);
    	this.ptOrigem = new GeomPoint3d(o.getPtDest());
		return this;
	}
    
	@Override
	public CadProjectDef mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		GeomPoint2d ptI2dMcs = new GeomPoint2d(ptIMcs);
		GeomPoint2d ptF2dMcs = new GeomPoint2d(ptFMcs);
		
		this.ptOrigem = GeomUtil.mirror(this.ptOrigem, ptI2dMcs, ptF2dMcs);
		return this;
	}
	
	@Override
	public CadProjectDef offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		GeomPoint3d ptFMcs = ptIMcs.otherMoveTo(uDirMcs, dist);
		
		CadProjectDef oPoint = copyTo(ptIMcs, ptFMcs);
		return oPoint;
	}

	/* ESCALA */
	
	public void updateProjectScale(double newScaleVal)
	{
		this.escala = newScaleVal;
		
		double sclFact = this.escala / this.unidade;
		this.setScaleFactor(sclFact);
	}	
	
	/* DEBUG */
	
	@Override
	public ArrayList<ItemDataVO> toPropertyList()
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATETIME_TYPE2_PTBR_MASC);
		
		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.add( new ItemDataVO("Codigo:", this.codigoProjeto, true) );
		lsProperty.add( new ItemDataVO("Titulo:", this.tituloProjeto, true) );
		lsProperty.add( new ItemDataVO("Descricao:", this.descricaoProjeto, true) );
		//
		lsProperty.add( new ItemDataVO("Logradouro:", this.logradouro, true) );
		lsProperty.add( new ItemDataVO("Numero:", this.numero, true) );
		lsProperty.add( new ItemDataVO("Complemento:", this.complemento, true) );
		lsProperty.add( new ItemDataVO("Bairro:", this.bairro, true) );
		lsProperty.add( new ItemDataVO("Municipio:", this.municipio, true) );
		lsProperty.add( new ItemDataVO("Estado:", this.estado, true) );
		lsProperty.add( new ItemDataVO("CEP:", this.cep, true) );
		//
		lsProperty.add( new ItemDataVO("ART:", this.art, true) );
		lsProperty.add( new ItemDataVO("Resp.Tecnico:", this.nomeResponsavelTecnico, true) );
		lsProperty.add( new ItemDataVO("Registro:", this.registroResponsavelTecnico, true) );
		lsProperty.add( new ItemDataVO("Telefone:", this.telefoneResponsavelTecnico, true) );
		lsProperty.add( new ItemDataVO("E-mail:", this.emailResponsavelTecnico, true) );
		//
		lsProperty.add( new ItemDataVO("Cd.Pluviografo:", nf0.format(this.codigoPluviografo), false) );
		lsProperty.add( new ItemDataVO("Pluviografo:", this.pluviografo, false) );
		lsProperty.add( new ItemDataVO("Coef. Manning:", nf6.format(this.coefManning), false) );
		lsProperty.add( new ItemDataVO("Periodo Recorrencia:", nf0.format(this.periodoRecorrencia), false) );
		//
		lsProperty.add( new ItemDataVO("Escala:", nf0.format(this.escala), false) );
		lsProperty.add( new ItemDataVO("Largura Papel:", nf0.format(this.papelLargura), false) );
		lsProperty.add( new ItemDataVO("Altura Papel:", nf0.format(this.papelAltura), false) );
		//
		lsProperty.add( new ItemDataVO("ESPG:", this.espgCode, false) );
		lsProperty.addAll( this.ptOrigem.toPropertyList("Origem", false) );
		lsProperty.addAll( this.xDir.toPropertyList("Direcao", false) );
		//
		lsProperty.add( new ItemDataVO("Unidade:", nf0.format(this.unidade), false) );
		lsProperty.add( new ItemDataVO("Scale Factor:", nf6.format(this.scaleFactor), false) );
		lsProperty.add( new ItemDataVO("Largura Area:", nf6.format(this.areaLargura), false) );
		lsProperty.add( new ItemDataVO("Altura Area:", nf6.format(this.areaAltura), false) );

		return lsProperty;
	}

	@Override
	public String toStr() {
		NumberFormat nf0 = FormatUtil.newNumberFormatEnUs(0);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);
		
		DateFormat df = FormatUtil.newDateFormat(AppDefs.DEF_DATETIME_TYPE2_PTBR_MASC);
		
		String strLayerName = this.getLayer().getName();
		
		String str = String.format(
			"ObjectId:%s;" +
			"ObjType:%s;" +
			"Layer:%s;" +
			//
			"CodigoProjeto:%s;" + 
			"TituloProjeto:%s;" +
			"DescricaoProjeto:%s;" +
			//
			"Logradouro:%s;" +
			"Numero:%s;" +
			"Complemento:%s;" +
			"Bairro:%s;" +
			"Municipio:%s;" +
			"Estado:%s;" +
			"Cep:%s;" +
			//
			"Art:%s;" +
			"NomeResponsavelTecnico:%s;" +
			"RegistroResponsavelTecnico:%s;" +
			"TelefoneResponsavelTecnico:%s;" +
			"EmailResponsavelTecnico:%s;" +
			//
			"CodigoPluviografo:%s;" +
			"Pluviografo:%s;" +
			"CoefManning:%s;" +
			"PeriodoRecorrencia:%s;" +
			//
			"Escala:%s;" +
			"PapelLargura:%s;" +
			"PapelAltura:%s;" +
			//
			"ESPG:%s;" +
			"Origem - X:%s;" +
			"Origem - Y:%s;" +
			"Origem - Z:%s;" +
			"Eixo X - X:%s;" +
			"Eixo X - Y:%s;" +
			"Eixo X - Z:%s;" +
			//
			"Unidade:%s;" +
			"ScaleFactor:%s;" +
			"AreaLargura:%s;" +
			"AreaAltura:%s;", 
			this.getObjectId(),
			this.getObjType(),
			strLayerName,
			//
			this.codigoProjeto,
			this.tituloProjeto,
			this.descricaoProjeto,
			//
			this.logradouro,
			this.numero,
			this.complemento,
			this.bairro,
			this.municipio,
			this.estado,
			this.cep,
			//
			this.art,
			this.nomeResponsavelTecnico,
			this.registroResponsavelTecnico,
			this.telefoneResponsavelTecnico,
			this.emailResponsavelTecnico,
			//
			this.codigoPluviografo,
			this.pluviografo,
			nf6.format(this.coefManning),
			nf0.format(this.periodoRecorrencia),
			//
			nf0.format(this.escala),
			nf0.format(this.papelLargura),
			nf0.format(this.papelAltura),
			//
			this.espgCode,
			nf6.format(this.ptOrigem.getX()),
			nf6.format(this.ptOrigem.getY()),
			nf6.format(this.ptOrigem.getZ()),
			nf6.format(this.xDir.getXOrig()),
			nf6.format(this.xDir.getYOrig()),
			nf6.format(this.xDir.getZOrig()),
			//
			nf0.format(this.unidade),
			nf6.format(this.scaleFactor),
			nf6.format(this.areaLargura),
			nf6.format(this.areaAltura) );
		return str;
	}
	
	@Override
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}
	
    /* DRAWCACHE */
	
	@Override
	public DrawCache createDrawCache2d() {
		return null;
	}

	@Override
	public DrawCache createDrawCache3d() {
		return null;
	}

	@Override
	public DrawCache createOsnapCache()
	{
		return null;
	}

    /* DRAWING */
	
	@Override
    public void redraw2d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, Graphics g) 
    {
    	if( !this.isVisible() ) return;
    	
        boolean bSelected = this.isSelected();
		boolean bHover = false;
		if( !bSelected )
			bHover = this.select2d(v, pt2dMcs, sclFact, false);

		Stroke b = selectLtype(bDragMode, bSelected, bHover, bSelEnt);

		Stroke oldltype = GeomUtil.setLtype(g, b);
		
		Color c = super.selectColor(bDragMode, bSelected, bHover, bSelEnt);

		Color oldcol = GeomUtil.setColor(g, c);		

        MainPanel panel = MainPanel.getMainPanel();
        String action = panel.getCurrAction();

        GeomPoint2d ptDest2dMcs = new GeomPoint2d(this.ptOrigem);
        
        if( bDragMode ) 
        {        
	        if(ptBase2dMcs != null) 
	        {        
	            GeomPoint3d ptBase3dMcs = new GeomPoint3d(ptBase2dMcs);
	            GeomPoint3d pt3dMcs = new GeomPoint3d(pt2dMcs);

	            GeomVector3d vDir3dMcs = new GeomVector3d(ptBase3dMcs, pt3dMcs);

		        if( AppDefs.ACTION_EDIT2_COPY.equals(action) || 
		        	AppDefs.ACTION_EDIT2_MOVE.equals(action) )
		        {
		        	CadProjectDef other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, pt3dMcs);
		        	
		        	ptDest2dMcs = new GeomPoint2d(other.ptOrigem);
		        }	        
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadProjectDef other = this.duplicate();
		        	other.mirror(ptBase3dMcs, pt3dMcs);
		        	
		        	ptDest2dMcs = new GeomPoint2d(other.ptOrigem);
		        }
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
			        	CadProjectDef other = this.duplicate();
			        	other.scaleTo(dist, ptBase3dMcs, pt3dMcs);
			        	
			        	ptDest2dMcs = new GeomPoint2d(other.ptOrigem);
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadProjectDef other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, newPt3dMcs);
		        	
		        	ptDest2dMcs = new GeomPoint2d(other.ptOrigem);
		        }
	        }        
        }
        
        GeomVector2d xDir2d = new GeomVector2d(this.xDir);
        
    	DrawUtil.drawCoordsysMcs(v, ptDest2dMcs, xDir2d, AppDefs.COORDSYS_SIZE, g);
    	
        GeomUtil.setColor(g, oldcol);
        
        GeomUtil.setLtype(g, oldltype);
    }
	
	@Override
	public void redraw3d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep) 
	{
		//TODO:
	}
    
	/* SELECT */

	@Override
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) 
	{
		if( this.isLocked() ) return false;
		
    	if( !this.isVisible() ) return false;
    	
    	if(this.isSelected()) return true;
    	
		if(pt2dMcs == null) return false;
		
        GeomPoint2d ptPoint2dMcs = new GeomPoint2d(this.ptOrigem);
        
        double boxSz = view2d.fromScrToMcs(AppDefs.SELECTBOX_SIZE);
        
        double distMax = boxSz / 2.0;
        
        double dist = ptPoint2dMcs.distTo(pt2dMcs); 
        if(dist <= distMax) {
        	if( bSelectEntity ) {
        		this.setSelected(true);
        	}
        	return true;
        }
        return this.isSelected();
	}
	
	@Override
	public boolean select3d(ICadViewBase view3d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity)
	{
		return false;
	}

	/* TO_SHAPE */

	@Override
	public ShapeResult toGeomShape2d_planView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_frontView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_backView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_leftView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_rightView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_topView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_bottomView(boolean bAnnotation, GeomPoint2d ptBase2dMcs)
	{
		return null;
	}

	@Override
	public ShapeResult toGeomShape3d(boolean bAnnotation, GeomPoint3d ptBase3dMcs)
	{
		return null;
	}

	/* OSNAP */

	@Override
	public GeomPoint3d osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs, Graphics g) 
	{
		if( this.isLocked() ) return null;
		
    	if( !this.isVisible() ) return null;

    	//NODEPOINT
    	//
    	ArrayList<GeomPoint3d> lsPtNodepoint = new ArrayList<GeomPoint3d>();    	
    	lsPtNodepoint.add(this.ptOrigem);
		
    	GeomPoint3d ptResult = null;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_NODEPOINT, pt2dMcs, lsPtNodepoint, g);
    	if(ptResult != null) return ptResult;
    	
    	return ptResult;
	}

	@Override
	public ArrayList<GeomPoint3d> osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs) 
	{
		if( this.isLocked() ) return null;
		
    	if( !this.isVisible() ) return null;

    	//NODEPOINT
    	//
    	ArrayList<GeomPoint3d> lsPtNodepoint = new ArrayList<GeomPoint3d>();    	
    	lsPtNodepoint.add( new GeomPoint3d(AppDefs.OSNAPMODE_NODEPOINT, this.ptOrigem) );
    	return lsPtNodepoint;
	}

	/* CENTROID */
	
	@Override
	public GeomPoint3d centroid()
	{
		GeomPoint3d ptResult = new GeomPoint3d(this.ptOrigem);
		return ptResult;
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
		boolean bResult = false;
		
		this.setObjVer(objVer);
		
		Object[] arrVal = {
			new Double( ptOrigem.getX() ),
			new Double( ptOrigem.getY() ),
			new Double( ptOrigem.getZ() ),
			//
			new Double( xDir.getXI() ),
			new Double( xDir.getYI() ),
			new Double( xDir.getZI() ),
			//
			new Double( xDir.getXF() ),
			new Double( xDir.getYF() ),
			new Double( xDir.getZF() ),
			
			//PROJECT_ATTR
			//
			new String( codigoProjeto ),
			new String( tituloProjeto ),
			new String( descricaoProjeto ),
				//
			new String( logradouro ),
			new String( numero ),
			new String( complemento ),
			new String( bairro ),
			new String( municipio ),
			new String( estado ),
			new String( cep ),
			//
			new String( art ),
			new String( nomeResponsavelTecnico ),
			new String( registroResponsavelTecnico ),
			new String( telefoneResponsavelTecnico ),
			new String( emailResponsavelTecnico ),
			//
			new String( espgCode ),
			new Integer( codigoPluviografo ),						// codigo local medicao volume chuva
			new String( pluviografo ),								// nome local medicao volume chuva
			new Double( coefManning ),
			new Double( periodoRecorrencia ),
			//
			new Double( escala ),
			new Double( papelLargura ),
			new Double( papelAltura ),
			new Double( unidade ),
			new Double( scaleFactor ),
			new Double( areaLargura ),
			new Double( areaAltura )
				
		};
		
		BaseEntityDao entDao = dao.create(this.getObjType()); 
		
		CadProjectDefRecord entRec = new CadProjectDefRecord(this); 
		int rscode = entDao.insertOrUpdate(
			objVer,
			schemaName,
			entRec, 
			arrVal );
		
		if(rscode >= 0)
			bResult = true;
		return bResult;
	}
	
	/* Getters/Setters */

	@Override
	public GeomDimension3d getEnvelop3d() {
		double xMin = this.ptOrigem.getX();
		double yMin = this.ptOrigem.getY();
		double zMin = this.ptOrigem.getZ();
		
		double xMax = xMin + this.areaLargura;
		double yMax = yMin + this.areaAltura;
		double zMax = zMin + Math.max( this.areaLargura, this.areaAltura);
		
		GeomPoint3d ptMin = new GeomPoint3d(xMin, yMin, zMin);
		GeomPoint3d ptMax = new GeomPoint3d(xMax, yMax, zMax);
		
		GeomDimension3d oDim = new GeomDimension3d(ptMin, ptMax); 
		return oDim;
	}

	@Override
	public GeomDimension2d getEnvelop2d() {
		double xMin = this.ptOrigem.getX();
		double yMin = this.ptOrigem.getY();
		
		double xMax = xMin + this.areaLargura;
		double yMax = yMin + this.areaAltura;
		
		GeomPoint2d ptMin = new GeomPoint2d(xMin, yMin);
		GeomPoint2d ptMax = new GeomPoint2d(xMax, yMax);
		
		GeomDimension2d oDim = new GeomDimension2d(ptMin, ptMax); 
		return oDim;
	}

	/* Getters/Setters */
	
	public String getCodigoProjeto() {
		return codigoProjeto;
	}

	public void setCodigoProjeto(String codigoProjeto) {
		this.codigoProjeto = codigoProjeto;
	}

	public String getTituloProjeto() {
		return tituloProjeto;
	}

	public void setTituloProjeto(String tituloProjeto) {
		this.tituloProjeto = tituloProjeto;
	}

	public String getDescricaoProjeto() {
		return descricaoProjeto;
	}

	public void setDescricaoProjeto(String descricaoProjeto) {
		this.descricaoProjeto = descricaoProjeto;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getArt() {
		return art;
	}

	public void setArt(String art) {
		this.art = art;
	}

	public String getNomeResponsavelTecnico() {
		return nomeResponsavelTecnico;
	}

	public void setNomeResponsavelTecnico(String nomeResponsavelTecnico) {
		this.nomeResponsavelTecnico = nomeResponsavelTecnico;
	}

	public String getRegistroResponsavelTecnico() {
		return registroResponsavelTecnico;
	}

	public void setRegistroResponsavelTecnico(String registroResponsavelTecnico) {
		this.registroResponsavelTecnico = registroResponsavelTecnico;
	}

	public String getTelefoneResponsavelTecnico() {
		return telefoneResponsavelTecnico;
	}

	public void setTelefoneResponsavelTecnico(String telefoneResponsavelTecnico) {
		this.telefoneResponsavelTecnico = telefoneResponsavelTecnico;
	}

	public String getEmailResponsavelTecnico() {
		return emailResponsavelTecnico;
	}

	public void setEmailResponsavelTecnico(String emailResponsavelTecnico) {
		this.emailResponsavelTecnico = emailResponsavelTecnico;
	}

	public double getEscala() {
		return escala;
	}

	public void setEscala(double escala) {
		this.escala = escala;
	}

	public double getUnidade() {
		return unidade;
	}

	public void setUnidade(double unidade) {
		this.unidade = unidade;
	}

	public double getScaleFactor() {
		return scaleFactor;
	}

	public void setScaleFactor(double scaleFactor) {
		this.scaleFactor = scaleFactor;
	}

	public GeomPoint3d getPtOrigem() {
		return ptOrigem;
	}

	public void setPtOrigem(GeomPoint3d ptOrigem) {
		this.ptOrigem = ptOrigem;
	}

	public GeomVector3d getXDir() {
		return xDir;
	}

	public void setXDir(GeomVector3d xDir) {
		this.xDir = xDir;
	}

	public double getAreaLargura() {
		return areaLargura;
	}

	public void setAreaLargura(double areaLargura) {
		this.areaLargura = areaLargura;
	}

	public double getAreaAltura() {
		return areaAltura;
	}

	public void setAreaAltura(double areaAltura) {
		this.areaAltura = areaAltura;
	}

	public String getPluviografo() {
		return pluviografo;
	}

	public void setPluviografo(String pluviografo) {
		this.pluviografo = pluviografo;
	}

	public double getCoefManning() {
		return coefManning;
	}

	public void setCoefManning(double coefManning) {
		this.coefManning = coefManning;
	}

	public double getPeriodoRecorrencia() {
		return periodoRecorrencia;
	}

	public void setPeriodoRecorrencia(double periodoRecorrencia) {
		this.periodoRecorrencia = periodoRecorrencia;
	}

	public String getEspgCode() {
		return espgCode;
	}

	public void setEspgCode(String espgCode) {
		this.espgCode = espgCode;
	}

	public double getPapelLargura() {
		return papelLargura;
	}

	public void setPapelLargura(double papelLargura) {
		this.papelLargura = papelLargura;
	}

	public double getPapelAltura() {
		return papelAltura;
	}

	public void setPapelAltura(double papelAltura) {
		this.papelAltura = papelAltura;
	}

	public int getCodigoPluviografo() {
		return codigoPluviografo;
	}

	public void setCodigoPluviografo(int codigoPluviografo) {
		this.codigoPluviografo = codigoPluviografo;
	}

}
