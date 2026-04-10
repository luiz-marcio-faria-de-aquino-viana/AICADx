/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadEletroduto3DEletrica.java
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

package br.com.tlmv.aicadxmod.eletrica.cad;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Stroke;
import java.text.NumberFormat;
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.AppMain;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.ICadEntity;
import br.com.tlmv.aicadxapp.cad.ICadObject;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
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
import br.com.tlmv.aicadxapp.dao.BaseODataDao;
import br.com.tlmv.aicadxapp.dao.BasePointDao;
import br.com.tlmv.aicadxapp.dao.record.BaseObjectRecord;
import br.com.tlmv.aicadxapp.dao.record.BasePointRecord;
import br.com.tlmv.aicadxapp.dao.record.CadPolylinePointRecord;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MidLineSegment2dVO;
import br.com.tlmv.aicadxmod.EletricaModule;
import br.com.tlmv.aicadxmod.eletrica.dao.record.CadEletroduto3DEletricaRecord;
import br.com.tlmv.aicadxmod.eletrica.dao.record.CadFioEletricoEletricaODataRecord;
import br.com.tlmv.aicadxmod.eletrica.dao.record.CadImportaFiacaoEletrodutoEletricaODataRecord;
import br.com.tlmv.aicadxmod.eletrica.fiacao.FiacaoHelper;

public class CadEletroduto3DEletrica extends CadEntity {
//Private
	private CadPontoEletrica entI = null;
	private CadPontoEletrica entF = null;
	private String tipoEletroduto = AppDefs.NULL_STR;
	private String nomeBitolaEletroduto = AppDefs.NULL_STR;
	private double bitolaEletrodutoInterna = AppDefs.DEF_DEFAULT_BITOLA_MINIMA_ELETRODUTO_INTERNA;
	private double bitolaEletrodutoExterna = AppDefs.DEF_DEFAULT_BITOLA_MINIMA_ELETRODUTO_EXTERNA;
	private double areaEletroduto = AppDefs.NULL_DBL;
	private double areaOcupada = AppDefs.NULL_DBL;
	private double taxaOcupacao = AppDefs.NULL_DBL;
	private int numeroCondutores = AppDefs.NULL_INT;

	//LISTA_PONTOS
	private ArrayList<GeomPoint3d> lsPts = null;

	//IMPORTA_FIACAO
	private CadImportaFiacaoEletricaOData oImportaFiacao = null;
	private ArrayList<CadFioEletricoEletricaOData> lsFio = null;
	
	//POSICAO_FIACAO_ELETRODUTO
	private int tipoIndicadorFiacao = AppDefs.DEF_POSFIA_ELETRODUTO_CENTRO;
	private GeomPoint3d ptInsIndicadorFiacao = null;
	private int numIndicadorFiacao = AppDefs.NULL_INT;
	
//Public

	public CadEletroduto3DEletrica(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
		super(AppDefs.OBJTYPE_MODELELETRODUTO3D, oBlkDef, oLayer, oLevel, zLevel, bLocked);
	}

	/* Methodes */

	public void init(CadPontoEletrica entI, CadPontoEletrica entF, ArrayList<GeomPoint3d> lsPts) {
		this.init(
			entI,
			entF,
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			AppDefs.DEF_DEFAULT_BITOLA_MINIMA_ELETRODUTO_INTERNA,
			AppDefs.DEF_DEFAULT_BITOLA_MINIMA_ELETRODUTO_EXTERNA,
			0.0,
			0.0,
			0.0,
			0,
			//
			//POSICAO_FIACAO_ELETRODUTO
			AppDefs.DEF_POSFIA_ELETRODUTO_CENTRO,
			AppDefs.NULL_INT,
			AppDefs.NULL_GEOMPOINT3D );
		this.init( lsPts );
	}
	
	public void init(
		CadPontoEletrica entI,
		CadPontoEletrica entF,
		//
		String tipoEletroduto,
		String nomeBitolaEletroduto,
		double bitolaEletrodutoInterna,
		double bitolaEletrodutoExterna,
		double areaEletroduto,
		double areaOcupada,
		double taxaOcupacao,
		int numeroCondutores,
		//
		//POSICAO_FIACAO_ELETRODUTO
		int tipoIndicadorFiacao,
		int numIndicadorFiacao,
		GeomPoint3d ptInsIndicadorFiacao ) 
	{
		this.entI = entI;
		this.entF = entF;
		//
		this.tipoEletroduto = tipoEletroduto;
		this.nomeBitolaEletroduto = nomeBitolaEletroduto;
		this.bitolaEletrodutoInterna = bitolaEletrodutoInterna;
		this.bitolaEletrodutoExterna = bitolaEletrodutoExterna;
		this.areaEletroduto = areaEletroduto;
		this.areaOcupada = areaOcupada;
		this.taxaOcupacao = taxaOcupacao;
		this.numeroCondutores = numeroCondutores;	
		//
		//POSICAO_FIACAO_ELETRODUTO
		this.tipoIndicadorFiacao = tipoIndicadorFiacao;
		this.numIndicadorFiacao = numIndicadorFiacao;
		this.ptInsIndicadorFiacao = new GeomPoint3d( ptInsIndicadorFiacao );

		//IMPORTA_FIACAO
		this.oImportaFiacao = null;
		this.lsFio = new ArrayList<CadFioEletricoEletricaOData>();
		
	}
	
	public void init(ArrayList<GeomPoint3d> lsPts3d) {
		this.lsPts = new ArrayList<GeomPoint3d>(lsPts3d);
    }
	
	@Override
	public void init(ICadObject o) {
		CadEletroduto3DEletrica other = (CadEletroduto3DEletrica)o;
		
		this.init(
			other.entI,
			other.entF,
			//
			other.getTipoEletroduto(),
			other.getNomeBitolaEletroduto(),
			other.getBitolaEletrodutoInterna(),
			other.getBitolaEletrodutoExterna(),
			other.getAreaEletroduto(),
			other.getAreaOcupada(),
			other.getTaxaOcupacao(),
			other.getNumeroCondutores(),
			//
			other.getTipoIndicadorFiacao(),
			other.getNumIndicadorFiacao(),
			other.getPtInsIndicadorFiacao() );
		this.init( other.getLsPts() );
	}

	/* CREATE */

	public static CadEletroduto3DEletrica create(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, CadPontoEletrica entI, CadPontoEletrica entF, ArrayList<GeomPoint3d> lsPts) {
		CadEletroduto3DEletrica o = new CadEletroduto3DEletrica(oBlkDef, oLayer, oLevel, 0.0, false);
		o.init(entI, entF, lsPts);
		return o;
	}

	public static CadEletroduto3DEletrica create(
			CadBlockDef oBlkDef,
			CadLayerDef oLayer, 
			CadLevel oLevel,
			//
			CadPontoEletrica entI,
			CadPontoEletrica entF,
			//
			String tipoEletroduto,
			String nomeBitolaEletroduto,
			double bitolaEletrodutoInterna,
			double bitolaEletrodutoExterna,
			double areaEletroduto,
			double areaOcupada,
			double taxaOcupacao,
			int numeroCondutores, 
			//
			ArrayList<GeomPoint3d> lsPts) 
	{
		CadEletroduto3DEletrica o = new CadEletroduto3DEletrica(oBlkDef, oLayer, oLevel, 0.0, false);
		o.init(
			entI,
			entF,
			//
			tipoEletroduto,
			nomeBitolaEletroduto,
			bitolaEletrodutoInterna,
			bitolaEletrodutoExterna,
			areaEletroduto,
			areaOcupada,
			taxaOcupacao,
			numeroCondutores,
			//
			//POSICAO_FIACAO_ELETRODUTO
			AppDefs.DEF_POSFIA_ELETRODUTO_CENTRO,
			AppDefs.NULL_INT,
			AppDefs.NULL_GEOMPOINT3D ); 
		o.init( lsPts );

		return o;
	}

	public static CadEletroduto3DEletrica create(
			CadBlockDef oBlkDef,
			CadLayerDef oLayer, 
			CadLevel oLevel,
			CadPontoEletrica entI,
			CadPontoEletrica entF,
			String tipoEletroduto,
			String nomeBitolaEletroduto,
			double bitolaEletrodutoInterna,
			double bitolaEletrodutoExterna,
			double areaEletroduto,
			double areaOcupada,
			double taxaOcupacao,
			int numeroCondutores)
	{
		CadEletroduto3DEletrica o = new CadEletroduto3DEletrica(oBlkDef, oLayer, oLevel, 0.0, false);
		o.init(
			entI,
			entF,
			//
			tipoEletroduto,
			nomeBitolaEletroduto,
			bitolaEletrodutoInterna,
			bitolaEletrodutoExterna,
			areaEletroduto,
			areaOcupada,
			taxaOcupacao,
			numeroCondutores,
			//
			//POSICAO_FIACAO_ELETRODUTO
			AppDefs.DEF_POSFIA_ELETRODUTO_CENTRO,
			AppDefs.NULL_INT,
			AppDefs.NULL_GEOMPOINT3D ); 
		return o;
	}

	public static CadEletroduto3DEletrica create(
			CadBlockDef oBlkDef,
			CadLayerDef oLayer, 
			CadLevel oLevel,
			CadPontoEletrica entI,
			CadPontoEletrica entF,
			String tipoEletroduto,
			String nomeBitolaEletroduto,
			double bitolaEletrodutoInterna,
			double bitolaEletrodutoExterna,
			double areaEletroduto,
			double areaOcupada,
			double taxaOcupacao,
			int numeroCondutores,
			boolean bLocked )
	{
		CadEletroduto3DEletrica o = new CadEletroduto3DEletrica(oBlkDef, oLayer, oLevel, 0.0, bLocked);
		o.init(
			entI,
			entF,
			//
			tipoEletroduto,
			nomeBitolaEletroduto,
			bitolaEletrodutoInterna,
			bitolaEletrodutoExterna,
			areaEletroduto,
			areaOcupada,
			taxaOcupacao,
			numeroCondutores,
			//
			//POSICAO_FIACAO_ELETRODUTO
			AppDefs.DEF_POSFIA_ELETRODUTO_CENTRO,
			AppDefs.NULL_INT,
			AppDefs.NULL_GEOMPOINT3D ); 
		return o;
	}
	
	public static CadEletroduto3DEletrica create(CadEletroduto3DEletrica other) {
		CadEletroduto3DEletrica o = new CadEletroduto3DEletrica(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
		o.init(other);
		return o;
	}
	
	public static CadEletroduto3DEletrica create(CadBlockDef blkDef, CadEletroduto3DEletrica other) {
		CadEletroduto3DEletrica o = new CadEletroduto3DEletrica(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
		o.init(other);
		return o;
	}

	/* OPERATIONS */

	@Override
	public CadEletroduto3DEletrica duplicate() {
		CadEletroduto3DEletrica other = CadEletroduto3DEletrica.create(this);
		return other;
	}

	@Override
	public CadEletroduto3DEletrica duplicate(CadBlockDef blkDef) {
		CadEletroduto3DEletrica other = CadEletroduto3DEletrica.create(blkDef, this);
		return other;
	}

	@Override
	public CadEletroduto3DEletrica copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs) {
		return this;
	}

	@Override
	public CadEletroduto3DEletrica moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs) {
		return this;
	}

	@Override
	public CadEletroduto3DEletrica scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs) {
		return this;
	}

	@Override
	public ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs) {
		return this;
	}

	@Override
	public CadEletroduto3DEletrica offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist) {
		return this;
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

	public void redraw2d_insereFios_DIREITA(ICadViewBase v, double dist, GeomPoint2d ptDestI2dMcs, GeomPoint2d ptDestF2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, Graphics g)
	{
		if(this.oImportaFiacao == null) return;
		
		double textSz = AppDefs.FONTSZ_SMALL * sclFact;

		double tickSz = AppDefs.TICKSZ_NORMAL * sclFact;
		double tickDist = tickSz * 1.5;

		//PT-DEST_I
		double xPtDestI = ptDestI2dMcs.getX();
		double yPtDestI = ptDestI2dMcs.getY();
		
		//PT-DEST_F
		double xPtDestF = ptDestF2dMcs.getX();
		double yPtDestF = ptDestF2dMcs.getY();

		GeomPoint2d ptDestI = null;
		GeomPoint2d ptDestF = null;		
		if(xPtDestI <= xPtDestF) {
			ptDestI = new GeomPoint2d(xPtDestI, yPtDestI);
			ptDestF = new GeomPoint2d(xPtDestF, yPtDestF);
		}
		else {
			ptDestI = new GeomPoint2d(xPtDestF, yPtDestF);
			ptDestF = new GeomPoint2d(xPtDestI, yPtDestI);
		}

		//PT-INS + V-DIR
		GeomVector2d vDirMcs = new GeomVector2d(ptDestI, ptDestF);
		GeomPoint2d ptInsMcs = ptDestI.otherMoveTo(vDirMcs, tickDist);

		//DRAW_FIOS
		//
		ArrayList<CadImportaFiacaoEletrodutoEletricaOData> lsFia = this.oImportaFiacao.getLsFia();
		for(CadImportaFiacaoEletrodutoEletricaOData oImportaFiacaoEletroduto : lsFia) {
			String label = oImportaFiacaoEletroduto.getLbl();
			int fios = oImportaFiacaoEletroduto.getFia();
			
			ptInsMcs = FiacaoHelper.drawFiosMcs(v, label, fios, ptInsMcs, vDirMcs, textSz, tickSz, tickDist, g);
		}
	}

	public void redraw2d_insereFios_ESQUERDA(ICadViewBase v, double dist, GeomPoint2d ptDestI2dMcs, GeomPoint2d ptDestF2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, Graphics g)
	{
		if(this.oImportaFiacao == null) return;
		
		double textSz = AppDefs.FONTSZ_SMALL * sclFact;

		double tickSz = AppDefs.TICKSZ_NORMAL * sclFact;
		double tickDist = tickSz * 1.5;

		//PT-DEST_I
		double xPtDestI = ptDestI2dMcs.getX();
		double yPtDestI = ptDestI2dMcs.getY();
		
		//PT-DEST_F
		double xPtDestF = ptDestF2dMcs.getX();
		double yPtDestF = ptDestF2dMcs.getY();

		GeomPoint2d ptDestI = null;
		GeomPoint2d ptDestF = null;		
		if(xPtDestI >= xPtDestF) {
			ptDestI = new GeomPoint2d(xPtDestI, yPtDestI);
			ptDestF = new GeomPoint2d(xPtDestF, yPtDestF);
		}
		else {
			ptDestI = new GeomPoint2d(xPtDestF, yPtDestF);
			ptDestF = new GeomPoint2d(xPtDestI, yPtDestI);
		}

		//PT-INS + V-DIR
		GeomVector2d vDirMcs = new GeomVector2d(ptDestI, ptDestF);
		GeomPoint2d ptInsMcs = ptDestI.otherMoveTo(vDirMcs, tickDist);

		//DRAW_FIOS
		//
		ArrayList<CadImportaFiacaoEletrodutoEletricaOData> lsFia = this.oImportaFiacao.getLsFia();
		for(CadImportaFiacaoEletrodutoEletricaOData oImportaFiacaoEletroduto : lsFia) {
			String label = oImportaFiacaoEletroduto.getLbl();
			int fios = oImportaFiacaoEletroduto.getFia();
			
			ptInsMcs = FiacaoHelper.drawFiosMcs(v, label, fios, ptInsMcs, vDirMcs, textSz, tickSz, tickDist, g);
		}
	}

	public void redraw2d_insereFios_CENTRO(ICadViewBase v, double dist, GeomPoint2d ptDestI2dMcs, GeomPoint2d ptDestF2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, Graphics g)
	{
		if(this.oImportaFiacao == null) return;
		
		double textSz = AppDefs.FONTSZ_SMALL * sclFact;

		double tickSz = AppDefs.TICKSZ_SMALL * sclFact;
		double tickDist = tickSz * 1.0;

		GeomPoint2d ptInsMcs = GeomUtil.midPointOf(ptDestI2dMcs, ptDestF2dMcs);
		GeomVector2d vDirMcs = new GeomVector2d(ptDestI2dMcs, ptDestF2dMcs);

		ArrayList<CadImportaFiacaoEletrodutoEletricaOData> lsFia = this.oImportaFiacao.getLsFia();
		for(CadImportaFiacaoEletrodutoEletricaOData oImportaFiacaoEletroduto : lsFia) {
			String label = oImportaFiacaoEletroduto.getLbl();
			int fios = oImportaFiacaoEletroduto.getFia();
			
			ptInsMcs = FiacaoHelper.drawFiosMcs(v, label, fios, ptInsMcs, vDirMcs, textSz, tickSz, tickDist, g);
		}
	}

	public void redraw2d_insereFios_INDICADORFIOS(ICadViewBase v, double dist, GeomPoint2d ptDestI2dMcs, GeomPoint2d ptDestF2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, Graphics g)
	{
		if(this.oImportaFiacao == null) return;
		
		double textSz = AppDefs.FONTSZ_SMALL * sclFact;

		double tickSz = AppDefs.TICKSZ_NORMAL * sclFact;
		double tickDist = tickSz * 1.5;
		
		double h2TickDist = tickDist / 2.0;
		double h4TickDist = tickDist / 4.0;

		GeomVector2d axisX = GeomUtil.axisX2d();
		
		GeomPoint2d ptMid0Mcs = GeomUtil.midPointOf(ptDestI2dMcs, ptDestF2dMcs);
		double xPtMid0Mcs = ptMid0Mcs.getX();

		GeomPoint2d ptIns0Mcs = new GeomPoint2d( this.ptInsIndicadorFiacao );
		double xPtIns0Mcs = ptIns0Mcs.getX();

		GeomPoint2d ptIns1Mcs = new GeomPoint2d( ptIns0Mcs );
		if(xPtIns0Mcs < xPtMid0Mcs) {
			ArrayList<CadImportaFiacaoEletrodutoEletricaOData> lsFia = this.oImportaFiacao.getLsFia();
			double szFios = tickDist; 
			for(CadImportaFiacaoEletrodutoEletricaOData oImportaFiacaoEletroduto : lsFia) {
				int fios = oImportaFiacaoEletroduto.getFia();
				szFios += FiacaoHelper.sizeOfFiosMcs(fios, tickDist);
			}
			szFios += tickDist;

			ptIns1Mcs = ptIns0Mcs.otherMoveTo(axisX, - szFios);
		}
		
		//DRAW-FIACAO
		//
		ArrayList<CadImportaFiacaoEletrodutoEletricaOData> lsFia = this.oImportaFiacao.getLsFia();
		GeomPoint2d ptInsMcs = ptIns1Mcs.otherMoveTo(axisX, h4TickDist);
		for(CadImportaFiacaoEletrodutoEletricaOData oImportaFiacaoEletroduto : lsFia) {
			String label = oImportaFiacaoEletroduto.getLbl();
			int fios = oImportaFiacaoEletroduto.getFia();
			
			ptInsMcs = FiacaoHelper.drawFiosMcs(v, label, fios, ptInsMcs, axisX, textSz, tickSz, h2TickDist, g);
		}

		GeomPoint2d ptInsFMcs = ptInsMcs.otherMoveTo(axisX, h4TickDist);
		if(xPtIns0Mcs < xPtMid0Mcs) {
			ptInsFMcs = new GeomPoint2d(ptIns1Mcs);			
		}
		
		//DRAW-SETA_FIACAO
		//
		DrawUtil.drawLineMcs(v, ptMid0Mcs, ptIns0Mcs, g);
		DrawUtil.drawLineMcs(v, ptIns0Mcs, ptInsFMcs, g);		
	}

	public void redraw2d_insereFios_TABELAFIOS(ICadViewBase v, double dist, GeomPoint2d ptDestI2dMcs, GeomPoint2d ptDestF2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, Graphics g)
	{
		if(this.oImportaFiacao == null) return;
		
		double textSz = AppDefs.FONTSZ_SMALL * sclFact;

		double lineHeight = 1.5 * textSz;
		
		GeomPoint2d ptIns0Mcs = GeomUtil.midPointOf(ptDestI2dMcs, ptDestF2dMcs);
		GeomVector2d vDirMcs = new GeomVector2d(ptDestI2dMcs, ptDestF2dMcs);

		GeomVector2d uDirMcs = vDirMcs.otherUnit();
		GeomVector2d nDirMcs = uDirMcs.otherNorm();

		GeomPoint2d ptInsMcs = ptIns0Mcs.otherMoveTo(nDirMcs, 2 * lineHeight);

		//DRAW-FIACAO
		//
		double radius = 1.5 * lineHeight;
		DrawUtil.drawCircleMcs(v, ptInsMcs, radius, g);

		String strNum = "?";
		if(this.numIndicadorFiacao != AppDefs.NULL_INT) {
			strNum = StringUtil.fillLeft( Integer.toString( this.numIndicadorFiacao ), '0', 4);
		}
		DrawUtil.drawTextMcs(v, strNum, ptInsMcs, lineHeight, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
	}
	
	@Override
	public void redraw2d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, Graphics g) 
	{
    	if( !this.isVisible() ) return;
		
		boolean bSelected = this.isSelected();
		boolean bHover = false;
		if (!bSelected)
			bHover = this.select2d(v, pt2dMcs, sclFact, false);

		Stroke b = selectLtype(bDragMode, bSelected, bHover, bSelEnt);

		Stroke oldltype = GeomUtil.setLtype(g, b);

		Color c = super.selectColor(bDragMode, bSelected, bHover, bSelEnt);

		Color oldcol = GeomUtil.setColor(g, c);

		AppMain app = AppMain.getApp();
		
        MainPanel panel = MainPanel.getMainPanel();
		String action = panel.getCurrAction();

		GeomPoint2d ptDestI2dMcs = new GeomPoint2d(this.entI.getPtIns());
		GeomPoint2d ptDestF2dMcs = new GeomPoint2d(this.entF.getPtIns());

		ArrayList<GeomPoint2d> lsDestPts2dMcs = GeomUtil.copyPt3dTo2dList(this.lsPts);

		if (bDragMode) {
			if (ptBase2dMcs != null) {
				GeomPoint3d ptBase3dMcs = new GeomPoint3d(ptBase2dMcs);
				GeomPoint3d pt3dMcs = new GeomPoint3d(pt2dMcs);

				GeomVector3d vDir3dMcs = new GeomVector3d(ptBase3dMcs, pt3dMcs);

				if (AppDefs.ACTION_EDIT2_COPY.equals(action) || AppDefs.ACTION_EDIT2_MOVE.equals(action)) {
					CadEletroduto3DEletrica other = this.duplicate();
					other.moveTo(ptBase3dMcs, pt3dMcs);

					ptDestI2dMcs = new GeomPoint2d(this.entI.getPtIns());
					ptDestF2dMcs = new GeomPoint2d(this.entF.getPtIns());
					
					lsDestPts2dMcs = GeomUtil.copyPt3dTo2dList(other.getLsPts());
				} else if (AppDefs.ACTION_EDIT2_MIRROR.equals(action)) {
					CadEletroduto3DEletrica other = this.duplicate();
					other.mirror(ptBase3dMcs, pt3dMcs);

					ptDestI2dMcs = new GeomPoint2d(this.entI.getPtIns());
					ptDestF2dMcs = new GeomPoint2d(this.entF.getPtIns());
					
					lsDestPts2dMcs = GeomUtil.copyPt3dTo2dList(other.getLsPts());
				} else if (AppDefs.ACTION_EDIT2_SCALE.equals(action)) {
					if (dist > AppDefs.MATHPREC_MIN) {
						CadEletroduto3DEletrica other = this.duplicate();
						other.scaleTo(dist, ptBase3dMcs, pt3dMcs);

						ptDestI2dMcs = new GeomPoint2d(this.entI.getPtIns());
						ptDestF2dMcs = new GeomPoint2d(this.entF.getPtIns());
						
						lsDestPts2dMcs = GeomUtil.copyPt3dTo2dList(other.getLsPts());
					}
				} else if (AppDefs.ACTION_DRAW1_OFFSET.equals(action)) {
					GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

					CadEletroduto3DEletrica other = this.duplicate();
					other.moveTo(ptBase3dMcs, newPt3dMcs);

					ptDestI2dMcs = new GeomPoint2d(this.entI.getPtIns());
					ptDestF2dMcs = new GeomPoint2d(this.entF.getPtIns());
					
					lsDestPts2dMcs = GeomUtil.copyPt3dTo2dList(other.getLsPts());
				}
			}
		}
		
        //ELETRODUTO
        //
    	GeomPoint3d ptI = new GeomPoint3d(ptDestI2dMcs);
    	for(GeomPoint3d oCurrPt : this.lsPts) {
    		DrawUtil.drawLineMcs(v, new GeomPoint2d(ptI), new GeomPoint2d(oCurrPt), g);
    		ptI = oCurrPt;
    	}
    	
    	GeomPoint3d ptF = new GeomPoint3d(ptDestF2dMcs);
		DrawUtil.drawLineMcs(v, new GeomPoint2d(ptI), new GeomPoint2d(ptF), g);

		//LISTA_FIOS
		//
		EletricaModule oEleMod = app.getElModule();
		int fiamode = oEleMod.getFiamode();
		if(fiamode == AppDefs.FIAMODE_ON) {
			
			GeomUtil.setColor(g, AppDefs.ELELETRODUTO_FIOS_COLOR1);
			
			GeomUtil.setLtype(g, AppDefs.ELFIOS_FIACAO_BORDERSTROKE1.getLtype());

			// MIDDLE_LINESEGMENT
			//
			MidLineSegment2dVO oMidSeg = GeomUtil.midSegmentOfLineString(ptDestI2dMcs, ptDestF2dMcs, lsDestPts2dMcs);
			
			if( this.tipoIndicadorFiacao == AppDefs.DEF_POSFIA_ELETRODUTO_DIREITA )
			{
				this.redraw2d_insereFios_DIREITA(v, dist, oMidSeg.getPtI(), oMidSeg.getPtF(), sclFact, bDragMode, bSelEnt, g);				
			}
			else if( this.tipoIndicadorFiacao == AppDefs.DEF_POSFIA_ELETRODUTO_CENTRO )
			{
				this.redraw2d_insereFios_CENTRO(v, dist, oMidSeg.getPtI(), oMidSeg.getPtF(), sclFact, bDragMode, bSelEnt, g);				
			}
			else if( this.tipoIndicadorFiacao == AppDefs.DEF_POSFIA_ELETRODUTO_ESQUERDA )
			{
				this.redraw2d_insereFios_ESQUERDA(v, dist, oMidSeg.getPtI(), oMidSeg.getPtF(), sclFact, bDragMode, bSelEnt, g);				
			}
			else if( this.tipoIndicadorFiacao == AppDefs.DEF_POSFIA_ELETRODUTO_INDICADORFIOS )
			{
				this.redraw2d_insereFios_INDICADORFIOS(v, dist, oMidSeg.getPtI(), oMidSeg.getPtF(), sclFact, bDragMode, bSelEnt, g);								
			}
			else if( this.tipoIndicadorFiacao == AppDefs.DEF_POSFIA_ELETRODUTO_TABELAFIOS )
			{
				this.redraw2d_insereFios_TABELAFIOS(v, dist, oMidSeg.getPtI(), oMidSeg.getPtF(), sclFact, bDragMode, bSelEnt, g);								
			}
			else {
				//TODO:
			}
			
		}
		
		GeomUtil.setColor(g, oldcol);

		GeomUtil.setLtype(g, oldltype);
	}

	@Override
	public void redraw3d(
		ICadViewBase v, 
		double dist, 
		GeomPoint2d ptBase2dMcs, 
		GeomPoint2d pt2dMcs, 
		double sclFact,
		boolean bDragMode, 
		boolean bSelEnt, 
		PrepareDrawUtil prep) 
	{
    	if( !this.isVisible() ) return;

    	CadBlockDef oBlkDef = this.getBlkDef();
    	
    	Color c = super.selectColor(bDragMode, false, false, bSelEnt);

    	//INTERNAL_DIAMETER
    	//
        double diamIntMeter = this.bitolaEletrodutoInterna / 1000.0;
        double radiusIntMeter = diamIntMeter / 2.0;

    	//EXTERNAL_DIAMETER
    	//
        double diamExtMeter = this.bitolaEletrodutoExterna / 1000.0;
        double radiusExtMeter = diamExtMeter / 2.0;

		//CONDUIT_UNION
		//
        double thicknessMili = Math.abs( this.bitolaEletrodutoExterna - this.bitolaEletrodutoInterna );
        double thicknessMeter = thicknessMili / 1000.0;

        //PT-I
        //
    	CadPontoEletrica oFromEnt = this.entI;
    	GeomPoint3d ptI = oFromEnt.getPtIns();
    	
        //PT-LIST
        //
    	for(GeomPoint3d oCurrPt : lsPts) {
        	GeomVector3d vDir3d = new GeomVector3d(ptI, oCurrPt);
        	double d = vDir3d.mod();

        	GeomVector3d uDir3d = vDir3d.otherUnit();

    		prep.addConduitSectionCirc(v, this, c, ptI, uDir3d, d, radiusIntMeter, radiusExtMeter, thicknessMeter);    		
    		ptI = oCurrPt;
    	}
    	
        //PT-F
        //
    	CadPontoEletrica oToEnt = this.entF;
    	GeomPoint3d ptF = oToEnt.getPtIns();

    	GeomVector3d vDir3d_F = new GeomVector3d(ptI, ptF);
    	double d_F = vDir3d_F.mod();

    	GeomVector3d uDir3d_F = vDir3d_F.otherUnit();

    	prep.addConduitSectionCirc(v, this, c, ptI, uDir3d_F, d_F, radiusIntMeter, radiusExtMeter, thicknessMeter);    		
        
	}

	/* SELECT */

	public boolean select2d_segment(ICadViewBase view2d, GeomPoint2d pt2dMcs, GeomPoint2d ptI2dMcs, GeomPoint2d ptF2dMcs, double sclFact, boolean bSelectEntity) 
	{
		GeomVector2d vIToF2dMcs = new GeomVector2d(ptI2dMcs, ptF2dMcs);
		GeomVector2d uIToF2dMcs = vIToF2dMcs.otherUnit();

		GeomVector2d vIToPt2dMcs = new GeomVector2d(ptI2dMcs, pt2dMcs);

		GeomPoint2d[] arrMaxMinPtMcs = GeomUtil.maxMinPointOf(ptI2dMcs, ptF2dMcs);
		GeomPoint2d ptMinMcs = arrMaxMinPtMcs[0];
		GeomPoint2d ptMaxMcs = arrMaxMinPtMcs[1];

		double boxSz = view2d.fromScrToMcs(AppDefs.SELECTBOX_SIZE);

		double distMax = boxSz / 2.0;

		double xMinMcs = ptMinMcs.getX() - distMax;
		double yMinMcs = ptMinMcs.getY() - distMax;

		double xMaxMcs = ptMaxMcs.getX() + distMax;
		double yMaxMcs = ptMaxMcs.getY() + distMax;

		double xMcs = pt2dMcs.getX();
		double yMcs = pt2dMcs.getY();

		if (((xMcs >= xMinMcs) && (xMcs <= xMaxMcs)) & ((yMcs >= yMinMcs) && (yMcs <= yMaxMcs))) {
			GeomVector3d vZ = uIToF2dMcs.vectProd(vIToPt2dMcs);
			double dZ = Math.abs(vZ.getZOrig());
			if (dZ <= distMax) {
				if (bSelectEntity) {
					this.setSelected(true);
				}
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean select2d(ICadViewBase view2d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) 
	{
		boolean bSelected = false; 
				
		if( this.isLocked() ) return false;
		
    	if( !this.isVisible() ) return false;
    	
		if (this.isSelected()) return true;

		if(pt2dMcs == null) return false;

		GeomPoint2d ptI2dMcs = new GeomPoint2d(this.entI.getPtIns());

		ArrayList<GeomPoint2d> lsPts2dMcs = GeomUtil.copyPt3dTo2dList(this.lsPts);
		for(GeomPoint2d ptF2dMcs : lsPts2dMcs) {
			bSelected = this.select2d_segment(view2d, pt2dMcs, ptI2dMcs, ptF2dMcs, sclFact, bSelectEntity);			
			if( bSelected ) return true;

			ptI2dMcs = ptF2dMcs;
		}

		GeomPoint2d ptF2dMcs = new GeomPoint2d(this.entF.getPtIns());

		bSelected = this.select2d_segment(view2d, pt2dMcs, ptI2dMcs, ptF2dMcs, sclFact, bSelectEntity);			
		if( bSelected ) return true;

		return false;
	}

	@Override
	public boolean select3d(ICadViewBase view3d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) {
		return false;
	}

	/* TO_SHAPE */

	@Override
	public ShapeResult toGeomShape2d_planView(boolean bAnnotation, GeomPoint2d ptBase2dMcs) {
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_frontView(boolean bAnnotation, GeomPoint2d ptBase2dMcs) {
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_backView(boolean bAnnotation, GeomPoint2d ptBase2dMcs) {
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_leftView(boolean bAnnotation, GeomPoint2d ptBase2dMcs) {
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_rightView(boolean bAnnotation, GeomPoint2d ptBase2dMcs) {
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_topView(boolean bAnnotation, GeomPoint2d ptBase2dMcs) {
		return null;
	}

	@Override
	public ShapeResult toGeomShape2d_bottomView(boolean bAnnotation, GeomPoint2d ptBase2dMcs) {
		return null;
	}

	@Override
	public ShapeResult toGeomShape3d(boolean bAnnotation, GeomPoint3d ptBase3dMcs) {
		return null;
	}

	/* OSNAP */

	@Override
	public GeomPoint3d osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs, Graphics g) 
	{
		if( this.isLocked() ) return null;
		
		if( !this.isVisible() ) return null;

		// ENDPOINT
		//
		ArrayList<GeomPoint3d> lsPtEndpoint = new ArrayList<GeomPoint3d>();
		lsPtEndpoint.add(new GeomPoint3d(this.entI.getPtIns()));
		lsPtEndpoint.add(new GeomPoint3d(this.entF.getPtIns()));

		// MIDDLE
		//
		GeomPoint3d pt3dMid = GeomUtil.midPointOf(this.entI.getPtIns(), this.entF.getPtIns());

		ArrayList<GeomPoint3d> lsPtMiddle = new ArrayList<GeomPoint3d>();
		lsPtMiddle.add(pt3dMid);

		GeomPoint3d ptResult = null;

		ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_ENDPOINT, pt2dMcs, lsPtEndpoint, g);
		if (ptResult != null)
			return ptResult;

		ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_MIDDLE, pt2dMcs, lsPtMiddle, g);
		if (ptResult != null)
			return ptResult;

		return ptResult;
	}

	@Override
	public ArrayList<GeomPoint3d> osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs) 
	{
		if( this.isLocked() ) return null;
		
		if( !this.isVisible() ) return null;

		GeomPoint3d ptI3d = this.entI.getPtIns();
		GeomPoint3d ptF3d = this.entF.getPtIns();
		
		// ENDPOINT
		//
		ArrayList<GeomPoint3d> lsPtEndpoint = new ArrayList<GeomPoint3d>();
		lsPtEndpoint.add(new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, ptI3d));
		lsPtEndpoint.add(new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, ptF3d));

		for(GeomPoint3d pt3d : this.lsPts) {
			lsPtEndpoint.add(new GeomPoint3d(AppDefs.OSNAPMODE_ENDPOINT, pt3d));			
		}
		
		// MIDDLE
		//
		ArrayList<GeomPoint3d> lsPtMiddle = new ArrayList<GeomPoint3d>();
		GeomPoint3d pt3dMid = null;
		for(GeomPoint3d pt3d : this.lsPts) {
			pt3dMid = GeomUtil.midPointOf(AppDefs.OSNAPMODE_MIDDLE, ptI3d, pt3d);
			lsPtMiddle.add( pt3dMid );
			
			ptI3d = pt3d;
		}
		
		pt3dMid = GeomUtil.midPointOf(AppDefs.OSNAPMODE_MIDDLE, ptI3d, ptF3d);
		lsPtMiddle.add( pt3dMid );

		ArrayList<GeomPoint3d> lsResult = new ArrayList<GeomPoint3d>();
		lsResult.addAll(lsPtEndpoint);
		lsResult.addAll(lsPtMiddle);
		return lsResult;
	}

	/* CENTROID */

	@Override
	public GeomPoint3d centroid() {
		// ENDPOINT
		//
		GeomPoint3d pt3dI = new GeomPoint3d(this.entI.getPtIns());
		GeomPoint3d pt3dF = new GeomPoint3d(this.entF.getPtIns());

		GeomPoint3d ptResult = GeomUtil.midPointOf(pt3dI, pt3dF);
		return ptResult;
	}
	
	/* LIST */
	
	public synchronized void loadAllItens(ArrayList<BasePointRecord> lsItens)
	{
		this.lsPts = new ArrayList<GeomPoint3d>();

		for(BasePointRecord oPt : lsItens) {
			long oid = oPt.getOid();
		    String refEntityId = oPt.getCadRefEntityId();
		    double ptX = oPt.getPtX();
		    double ptY = oPt.getPtY();
		    double ptZ = oPt.getPtZ();

		    GeomPoint3d pt3d = new GeomPoint3d(ptX, ptY, ptZ);
		    this.lsPts.add(pt3d);
		}
	}
		
	public synchronized void loadAllItensFia(ArrayList<BaseObjectRecord> lsItens)
	{
		if(lsItens.size() == 0) return;
		
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingEnUs(0);
		
		AppMain app = AppMain.getApp();
		
		EletricaModule oEleMod = app.getElModule();
		oEleMod.setFiamode(AppDefs.FIAMODE_ON);

		CadDocumentDef doc = this.getDocument();
		
		String cadRefEntityId = Integer.toString( this.getObjectId() );
		String hnd = nf0.format( cadRefEntityId );
		
		//IMPORTA_FIACAO
		this.oImportaFiacao = CadImportaFiacaoEletricaOData.create(
			doc,
			cadRefEntityId,
			hnd );
		
		for(BaseObjectRecord o : lsItens) {
			CadImportaFiacaoEletrodutoEletricaODataRecord oImpFiaRec = (CadImportaFiacaoEletrodutoEletricaODataRecord)o;
			
			CadImportaFiacaoEletrodutoEletricaOData oImpFia = CadImportaFiacaoEletrodutoEletricaOData.create(
				doc, 
	    	    oImpFiaRec.getRowId(),
	        	oImpFiaRec.getHnd(),
	        	oImpFiaRec.getQdr(),
	        	oImpFiaRec.getCir(),
	        	oImpFiaRec.getLbl(),
	        	oImpFiaRec.getFia() );
			this.oImportaFiacao.addFia(oImpFia);
		}
	}
		
	public synchronized void loadAllItensFioEletrico(ArrayList<BaseObjectRecord> lsItens)
	{
		if(lsItens.size() == 0) return;
		
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingEnUs(0);
		
		AppMain app = AppMain.getApp();
		
		EletricaModule oEleMod = app.getElModule();
		oEleMod.setFiamode(AppDefs.FIAMODE_ON);

		CadDocumentDef doc = this.getDocument();
		
		int cadRefEntityId = this.getObjectId();
		String hnd = nf0.format( cadRefEntityId );
		
		//FIO_ELETRICO
		for(BaseObjectRecord o : lsItens) {
			CadFioEletricoEletricaODataRecord oFioRec = (CadFioEletricoEletricaODataRecord)o;
			
			CadFioEletricoEletricaOData oFio = CadFioEletricoEletricaOData.create(
				doc, 
			    oFioRec.getRowId(),
			    oFioRec.getEletrodutoId(),
			    oFioRec.getNomeQuadro(),
			    oFioRec.getCircuito(),
			    oFioRec.getTipoCondutor(),
			    oFioRec.getBitolaCondutor() );
			this.lsFio.add(oFio);
		}
	}
	
	public synchronized int getSzLsPts() {
		int sz = this.lsPts.size();
		return sz;
	}

	public synchronized GeomPoint3d getLsPtsAt(int pos) {
		int sz = this.lsPts.size();
		if(pos < sz) {
			GeomPoint3d o = this.lsPts.get(pos);
			return o;
		}
		return null;
	}

	public synchronized void addLsPts(GeomPoint3d o) {
		this.lsPts.add(o);
	}
	
	/* DEBUG */

	@Override
	public ArrayList<ItemDataVO> toPropertyList() {
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);

		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.add( new ItemDataVO("Ponto Eletrico 1", Integer.toString(this.entI.getObjectId()), false) );
		lsProperty.add( new ItemDataVO("Ponto Eletrico 2", Integer.toString(this.entF.getObjectId()), false) );

		return lsProperty;
	}

	@Override
	public String toStr() {
		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);

		String str = String.format("EntI:%s;EntF:%s;", this.entI.getObjectId(), this.entF.getObjectId());
		return str;
	}

	@Override
	public void debug(int debugLevel) {
		if (debugLevel != AppDefs.DEBUG_LEVEL)
			return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}
	
	/* LOAD/SAVE */
	
	@Override
	public boolean load(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		return true;
	}

	public boolean save_pontosEletroduto(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		BasePointDao ptDao = dao.createPtDao(AppDefs.OBJTYPE_POLYLINE_GEOMPOINT); 

		String cadRefEntityId = Integer.toString(this.getObjectId());
		
		int szLsPts = this.lsPts.size();
		for(int i = 0; i < szLsPts; i++) {
			BasePointRecord ptRec = new BasePointRecord(cadRefEntityId, objVer, this.lsPts.get(i));
			
			int rscode = ptDao.insertOrUpdate(objVer, schemaName, CadPolylinePointRecord.sqlTableName, ptRec);  
			if(rscode < 0) return false;
		}		
		return true;
	}

	public boolean save_importacaoFiacaoEletrica(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		BaseODataDao odataDao1 = dao.createODataDao(AppDefs.OBJTYPE_IMPORTAFIACAOELETRODUTOELETRICA_ODATA); 

		String cadRefEntityId = Integer.toString( this.getObjectId() );
		
		for(CadImportaFiacaoEletrodutoEletricaOData oFia : this.oImportaFiacao.getLsFia()) {
			CadImportaFiacaoEletrodutoEletricaODataRecord odataRec1 = new CadImportaFiacaoEletrodutoEletricaODataRecord(oFia); 
			odataRec1.setCadRefEntityId(cadRefEntityId);
			odataRec1.setObjVer(objVer);

			Object[] arrVal = {
				new Integer( oFia.getRowId() ),
				new String( oFia.getHnd() ),
				new String( oFia.getQdr() ),
				new String( oFia.getCir() ),
				new String( oFia.getLbl() ),
				new Integer( oFia.getFia() )					
			};

			int rscode = odataDao1.insertOrUpdate(
				objVer,
				schemaName,
				odataRec1, 
				arrVal );
			if(rscode < 0) return false;
		}
		return true;
	}

	public boolean save_fiacaoEletrica(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		BaseODataDao odataDao2 = dao.createODataDao(AppDefs.OBJTYPE_FIOELETRICOELETRICA_ODATA); 

		String cadRefEntityId = Integer.toString( this.getObjectId() );
		
		for(CadFioEletricoEletricaOData oFio : this.lsFio) {
			CadFioEletricoEletricaODataRecord odataRec2 = new CadFioEletricoEletricaODataRecord(oFio); 
			odataRec2.setCadRefEntityId(cadRefEntityId);
			odataRec2.setObjVer(objVer);

			Object[] arrVal = {
				new Integer( oFio.getRowId() ),
				new Integer( oFio.getEletrodutoId() ),
				new String( oFio.getNomeQuadro() ),
				new String( oFio.getCircuito() ),
				new String( oFio.getTipoCondutor() ),
				new Double( oFio.getBitolaCondutor() )					
			};

			int rscode = odataDao2.insertOrUpdate(
				objVer,
				schemaName,
				odataRec2, 
				arrVal );
			if(rscode < 0) return false;
		}
		return true;
	}

	public boolean save_entity(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		if(entI == null) return false; 
		if(entF == null) return false; 

		boolean bResult = false;
		
		this.setObjVer(objVer);

		int objectId_I = this.entI.getObjectId();
		int objectId_F = this.entF.getObjectId();
		
		Object[] arrVal = {
			new Integer( objectId_I ),
			new Integer( objectId_F ),
			//
			new String( this.tipoEletroduto ),
			new String( this.nomeBitolaEletroduto ),
			new Double( this.bitolaEletrodutoInterna ),
			new Double( this.bitolaEletrodutoExterna ),
			new Double( this.areaEletroduto ),
			new Double( this.areaOcupada ),
			new Double( this.taxaOcupacao ),
			new Integer( this.numeroCondutores )
		};
				
		BaseEntityDao entDao = dao.create(this.getObjType());
		
		CadEletroduto3DEletricaRecord entRec = new CadEletroduto3DEletricaRecord(this); 
		int rscode = entDao.insertOrUpdate(
			objVer,
			schemaName,
			entRec, 
			arrVal );

		if(rscode >= 0)
			bResult = true;
		return bResult;
	}

	@Override
	public boolean save(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		this.setObjVer(objVer);
		
		boolean bResult = save_entity(objVer, dao, schemaName, doc);
		if( !bResult ) return false;
		
		bResult = this.save_pontosEletroduto(objVer, dao, schemaName, doc);
		if( !bResult ) return false;
		
		bResult = this.save_importacaoFiacaoEletrica(objVer, dao, schemaName, doc);
		if( !bResult ) return false;
		
		bResult = this.save_fiacaoEletrica(objVer, dao, schemaName, doc);
		return bResult;
	}

	/* Getters/Setters */

	@Override
	public GeomDimension3d getEnvelop3d() {
		// ENDPOINT
		//
		GeomPoint3d ptI3dMcs = new GeomPoint3d(this.entI.getPtIns());
		GeomPoint3d ptF3dMcs = new GeomPoint3d(this.entF.getPtIns());

		GeomPoint3d[] arr = GeomUtil.maxMinPointOf(ptI3dMcs, ptF3dMcs);

		GeomPoint3d ptMin3d = new GeomPoint3d(arr[0]);
		GeomPoint3d ptMax3d = new GeomPoint3d(arr[1]);

		GeomDimension3d oDim = new GeomDimension3d(ptMin3d, ptMax3d);
		return oDim;
	}

	@Override
	public GeomDimension2d getEnvelop2d() {
		// ENDPOINT
		//
		GeomPoint2d ptI2dMcs = new GeomPoint2d(this.entI.getPtIns());
		GeomPoint2d ptF2dMcs = new GeomPoint2d(this.entF.getPtIns());

		GeomPoint2d[] arr = GeomUtil.maxMinPointOf(ptI2dMcs, ptF2dMcs);

		GeomPoint2d ptMin2d = new GeomPoint2d(arr[0]);
		GeomPoint2d ptMax2d = new GeomPoint2d(arr[1]);

		GeomDimension2d oDim = new GeomDimension2d(ptMin2d, ptMax2d);
		return oDim;
	}
	
	@Override
	public String getSearchString() {
		String searchString = super.getSearchString() + "^" +
			"PONTO=" + Integer.toString( this.entI.getObjectId() ) +
			"PONTO=" + Integer.toString( this.entF.getObjectId() ) +
			"BITOLA=" + Double.toString( this.bitolaEletrodutoInterna );
		return searchString;
	}

	public CadEntity getEntI() {
		return this.entI;
	}

	public CadEntity getEntF() {
		return this.entF;
	}

	public String getTipoEletroduto() {
		return tipoEletroduto;
	}

	public void setTipoEletroduto(String tipoEletroduto) {
		this.tipoEletroduto = tipoEletroduto;
	}

	public String getNomeBitolaEletroduto() {
		return nomeBitolaEletroduto;
	}

	public void setNomeBitolaEletroduto(String nomeBitolaEletroduto) {
		this.nomeBitolaEletroduto = nomeBitolaEletroduto;
	}

	public double getAreaEletroduto() {
		return areaEletroduto;
	}

	public void setAreaEletroduto(double areaEletroduto) {
		this.areaEletroduto = areaEletroduto;
	}

	public double getAreaOcupada() {
		return areaOcupada;
	}

	public void setAreaOcupada(double areaOcupada) {
		this.areaOcupada = areaOcupada;
	}

	public double getTaxaOcupacao() {
		return taxaOcupacao;
	}

	public void setTaxaOcupacao(double taxaOcupacao) {
		this.taxaOcupacao = taxaOcupacao;
	}

	public int getNumeroCondutores() {
		return numeroCondutores;
	}

	public void setNumeroCondutores(int numeroCondutores) {
		this.numeroCondutores = numeroCondutores;
	}

	public ArrayList<CadFioEletricoEletricaOData> getLsFio() {
		return lsFio;
	}

	public void setLsFio(ArrayList<CadFioEletricoEletricaOData> lsFio) {
		this.lsFio = lsFio;
	}

	public CadImportaFiacaoEletricaOData getImportaFiacao() {
		return oImportaFiacao;
	}

	public void setImportaFiacao(CadImportaFiacaoEletricaOData oImportaFiacao) {
		this.oImportaFiacao = oImportaFiacao;
	}

	public int getTipoIndicadorFiacao() {
		return tipoIndicadorFiacao;
	}

	public void setTipoIndicadorFiacao(int tipoIndicadorFiacao) {
		this.tipoIndicadorFiacao = tipoIndicadorFiacao;
	}

	public GeomPoint3d getPtInsIndicadorFiacao() {
		return ptInsIndicadorFiacao;
	}

	public void setPtInsIndicadorFiacao(GeomPoint3d ptInsIndicadorFiacao) {
		this.ptInsIndicadorFiacao = ptInsIndicadorFiacao;
	}

	public int getNumIndicadorFiacao() {
		return numIndicadorFiacao;
	}

	public void setNumIndicadorFiacao(int numIndicadorFiacao) {
		this.numIndicadorFiacao = numIndicadorFiacao;
	}

	public ArrayList<GeomPoint3d> getLsPts() {
		return lsPts;
	}

	public CadImportaFiacaoEletricaOData getoImportaFiacao() {
		return oImportaFiacao;
	}

	public double getBitolaEletrodutoInterna() {
		return bitolaEletrodutoInterna;
	}

	public void setBitolaEletrodutoInterna(double bitolaEletrodutoInterna) {
		this.bitolaEletrodutoInterna = bitolaEletrodutoInterna;
	}

	public double getBitolaEletrodutoExterna() {
		return bitolaEletrodutoExterna;
	}

	public void setBitolaEletrodutoExterna(double bitolaEletrodutoExterna) {
		this.bitolaEletrodutoExterna = bitolaEletrodutoExterna;
	}

}
