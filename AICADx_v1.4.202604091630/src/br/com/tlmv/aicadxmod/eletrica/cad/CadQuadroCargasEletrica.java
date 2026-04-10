/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CadQuadroCargasEletrica.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 07/01/2026
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
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
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
import br.com.tlmv.aicadxapp.cad.geom.GeomPlan2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.geom.shape.ShapeResult;
import br.com.tlmv.aicadxapp.cad.utils.DrawUtil;
import br.com.tlmv.aicadxapp.cad.utils.DxfUtil;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;
import br.com.tlmv.aicadxapp.cad.utils.PrepareDrawUtil;
import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.dao.BaseODataDao;
import br.com.tlmv.aicadxapp.dao.BaseEntityDao;
import br.com.tlmv.aicadxapp.dxf.dxfentry.DxfCadEntity;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxapp.vo.MoveData3dVO;
import br.com.tlmv.aicadxapp.vo.TableCellVO;
import br.com.tlmv.aicadxapp.vo.TableHeaderVO;
import br.com.tlmv.aicadxapp.vo.TableRowVO;
import br.com.tlmv.aicadxmod.drenagem.vo.ColunaTabelaVO;
import br.com.tlmv.aicadxmod.eletrica.calc.EletricaCalc;
import br.com.tlmv.aicadxmod.eletrica.cmp.CmpCadCircuitoQuadroCargasEletricaOData;
import br.com.tlmv.aicadxmod.eletrica.dao.record.CadCircuitoQuadroCargasEletricaODataRecord;
import br.com.tlmv.aicadxmod.eletrica.dao.record.CadQuadroCargasEletricaRecord;
import br.com.tlmv.aicadxmod.eletrica.vo.CircuitoFaseVO;
import br.com.tlmv.aicadxmod.eletrica.vo.DimensionamentoCircuito;
import br.com.tlmv.aicadxmod.eletrica.vo.DistribuicaoFaseVO;

public class CadQuadroCargasEletrica extends CadEntity
{
//Private
	private GeomPoint3d ptIns;
	//
	private String nomeQuadro;
	private String descricaoQuadro;
	private Double tensaoQuadro;
	private Double bitolaMinimaCondutor;
	private Double disjuntorMinimoProtecao;
	private Double temperaturaAmbiente;
	private Double fatorReducao;	
	private String sistemaFase;
	//
	private Integer qtdTotalCargasQuadro;	
	private Double totalCargasQuadro;	
	private Integer qtdCargasPaineisQuadro;	
	private Double cargasPaineisQuadro;	
	private Integer qtdCargasIluminacaoQuadro;	
	private Double cargasIluminacaoQuadro;	
	private Integer qtdCargasTomadaQuadro;	
	private Double cargasTomadaQuadro;	
	private Integer qtdCargasMotorQuadro;	
	private Double cargasMotorQuadro;	
	private Integer qtdCargasRaioXQuadro;	
	private Double cargasRaioXQuadro;	
	private Integer qtdCargasAquecimentoQuadro;	
	private Double cargasAquecimentoQuadro;	
	private Integer qtdCargasOutrosQuadro;	
	private Double cargasOutrosQuadro;	
	//
	private Double potenciaSemReservaQuadro;
	private Double potenciaQuadro;	
	private Double alimentadorQuadro;
	private Double alimentadorProtecaoQuadro;
	private Double disjuntorQuadro;
	private String faseQuadro;
	//
	private Double correnteBarraQuadro;
	private Double correnteBarraNeutroQuadro;
	private Double correnteBarraProtecaoQuadro;
	//
	private boolean bMinimized;

	private ArrayList<CadCircuitoQuadroCargasEletricaOData> lsItem = null;
	private Hashtable mapItem = null;

	private ArrayList<CadPontoEletrica> lsPontoEletrico = null;	
	
    //FONT_SIZE
    private double fontTitleSzMili = AppDefs.FONTSZ_MEDIUM;        
    private double fontHeaderSzMili = AppDefs.FONTSZ_NORMAL;        
    private double fontCellSzMili = AppDefs.FONTSZ_SMALL;
    
    //TABLE_HEADER/TABLE_ROWS
    private ArrayList<TableHeaderVO> lsHeader = null;
    private ArrayList<TableRowVO> lsRows = null;

	/* Methodes */
	
	public String generateDescricaoCircuito(String tip, String cir)
	{
		String strResult = "Circuito: ???";
		if( StringUtil.isEmpty(cir) ) return strResult;
		
		if( AppDefs.FIA_S_QUADRO.equals(tip) ) {
			strResult = String.format("Circuito: %s - Paineis Eletricos", cir);			
		}
		else if( AppDefs.FIA_S_CARGA.equals(tip) ) {
			strResult = String.format("Circuito: %s - Tomadas", cir);			
		}
		else if( AppDefs.FIA_S_ILUMINACAO.equals(tip) ) {
			strResult = String.format("Circuito: %s - Iluminacao", cir);			
		}
		else if( AppDefs.FIA_S_MOTOR.equals(tip) ) {
			strResult = String.format("Circuito: %s - Motores", cir);			
		}
		else if( AppDefs.FIA_S_RAIOX.equals(tip) ) {
			strResult = String.format("Circuito: %s - Aparelhos de Raio-X", cir);			
		}
		else if( AppDefs.FIA_S_AQUECIMENTO.equals(tip) ) {
			strResult = String.format("Circuito: %s - Aparelhos de Aquecimento", cir);			
		}
		else if( ( AppDefs.FIA_S_COMANDO.equals(tip) ) 		||
				 ( AppDefs.FIA_S_CAMPAINHA.equals(tip) ) 	||
				 ( AppDefs.FIA_S_CAIXA.equals(tip) ) 		||
				 ( AppDefs.FIA_S_DESVIO.equals(tip) ) 		||
				 ( AppDefs.FIA_S_CALHA.equals(tip) ) )
		{
			/* nothing todo! */
		}
		else {
			strResult = String.format("Circuito: %s - Outras Cargas", cir);
		}
		return strResult;
	}

	/* LOADALL - PONTO_ELETRICA */
	
	private void resetAllPontoEletrica()
	{
		this.lsItem = new ArrayList<CadCircuitoQuadroCargasEletricaOData>();
		this.mapItem = new Hashtable();
		
		this.lsPontoEletrico = new ArrayList<CadPontoEletrica>();

		this.qtdTotalCargasQuadro = 0;	
		this.totalCargasQuadro = 0.0;	
		this.qtdCargasPaineisQuadro = 0;
		this.cargasPaineisQuadro = 0.0;
		this.qtdCargasIluminacaoQuadro = 0;	
		this.cargasIluminacaoQuadro = 0.0;	
		this.qtdCargasTomadaQuadro = 0;	
		this.cargasTomadaQuadro = 0.0;	
		this.qtdCargasMotorQuadro = 0;	
		this.cargasMotorQuadro = 0.0;	
		this.qtdCargasRaioXQuadro = 0;
		this.cargasRaioXQuadro = 0.0;
		this.qtdCargasAquecimentoQuadro = 0;
		this.cargasAquecimentoQuadro = 0.0;
		this.qtdCargasOutrosQuadro = 0;
		this.cargasOutrosQuadro = 0.0;
		this.potenciaSemReservaQuadro = 0.0;
		this.potenciaQuadro = 0.0;
		this.alimentadorQuadro = 0.0;
		this.alimentadorProtecaoQuadro = 0.0;
		this.disjuntorQuadro = 0.0;
		this.faseQuadro = AppDefs.NULL_STR; 
	}
	
	private ArrayList<CadPontoEletrica> loadAllPontoEletrica()
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		CadEntity[] arrEnt = this.getBlkDef().findAllEntityByObjType(AppDefs.OBJTYPE_MODELINSEREPONTO);
		String cadRefEntityId = Integer.toString( this.getObjectId() );
		for(CadEntity ent : arrEnt) {
			CadPontoEletrica oEnt = (CadPontoEletrica)ent;
			
			int sz = oEnt.getSzLsParamEletrico();
			if(sz > 0) {
				for(int i = 0; i < sz; i++) {
					CadParamEletricoOData oParam = oEnt.getParamEletricoAt(i);
	
					int objectId = oEnt.getObjectId();
					int rowId = i + 1;
					
				    String tip = oParam.getTipo();
				    String org = StringUtil.toUpperCase(oParam.getQuadroOrigem());
				    double pot = oParam.getPotencia();
				    double dem = oParam.getPotenciaDemandada();
				    String fas = oParam.getSistema();
				    String cir = StringUtil.toUpperCase(oParam.getCircuito());
				    String cmd = StringUtil.toUpperCase(oParam.getComando());
	
				    String descricaoCircuito = this.generateDescricaoCircuito(tip, cir); 
				    
				    CadCircuitoQuadroCargasEletricaOData oCircuito = null;
					if(org.compareToIgnoreCase( this.nomeQuadro ) == 0)
					{
						if( !StringUtil.isEmpty(cir) ) {
							if( !this.mapItem.containsKey(cir) ) {
								oCircuito = CadCircuitoQuadroCargasEletricaOData.create(
									this.getDocument(),
									cadRefEntityId,
									rowId,
									org,
									cir,
									descricaoCircuito,
									fas,
							    	AppDefs.DEF_VALUES_NAO);
								this.lsItem.add(oCircuito);
	
								this.mapItem.put(cir, oCircuito);
							}
							else {
								oCircuito = (CadCircuitoQuadroCargasEletricaOData)this.mapItem.get(cir);
							}
							oCircuito.addPontoEletrico(oEnt);
						}
					}
				}
			}
		}

		CmpCadCircuitoQuadroCargasEletricaOData c = new CmpCadCircuitoQuadroCargasEletricaOData(true);
		this.lsItem.sort(c);
		
		return this.lsPontoEletrico;
	}
	    
	private void dimensionaQuadroCargas(double vfase, double btmin, double djmin, double temp, double fred)
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
        ArrayList<DistribuicaoFaseVO> lsFaseCirc = new ArrayList<DistribuicaoFaseVO>();
        Hashtable map = new Hashtable();
        
        EletricaCalc calc = new EletricaCalc(this.getDocument());
		
        double ftmp = calc.getFatorCorrecaoPorTemperatura(temp);
		
        double btminq = this.bitolaMinimaCondutor;
        double corminq = 0.0;

		for(CadCircuitoQuadroCargasEletricaOData oItem : this.lsItem) {
			oItem.reCalcCircuito(vfase, btmin, temp, fred);

			this.qtdCargasPaineisQuadro += oItem.getQtdCargaPainel();
			this.cargasPaineisQuadro += oItem.getCargaPainel();
			this.qtdCargasIluminacaoQuadro += oItem.getQtdCargaIluminacao();
			this.cargasIluminacaoQuadro += oItem.getCargaIluminacao();	
			this.qtdCargasTomadaQuadro += oItem.getQtdCargaTomada();	
			this.cargasTomadaQuadro += oItem.getCargaTomada();
			this.qtdCargasMotorQuadro += oItem.getQtdCargaMotor();
			this.cargasMotorQuadro += oItem.getCargaMotor();	
			this.qtdCargasRaioXQuadro += oItem.getQtdCargaRaioX();
			this.cargasRaioXQuadro += oItem.getCargaRaioX();
			this.qtdCargasAquecimentoQuadro += oItem.getQtdCargaAquecimento();
			this.cargasAquecimentoQuadro += oItem.getCargaAquecimento();
			//
			this.qtdTotalCargasQuadro += oItem.getQtdCargaCircuito();
			this.totalCargasQuadro += oItem.getCargaCircuito();
			this.potenciaSemReservaQuadro += oItem.getCargaCircuito();
			this.potenciaQuadro += oItem.getCargaCircuito();
			//
			this.alimentadorQuadro = 0.0;
			this.alimentadorProtecaoQuadro = 0.0;
			this.disjuntorQuadro = 0.0;
			this.faseQuadro = AppDefs.NULL_STR; 
			
			DimensionamentoCircuito dimCirc = oItem.getDimensionamentoCircuito();
			double bit1 = dimCirc.getBitolaCondutor();
			double cor1 = dimCirc.getCorrente();
            if (bit1 > btminq) {
                corminq = cor1;
                btminq = bit1;
            }

            DistribuicaoFaseVO faseCirc = oItem.getDistribuicaoFase();
            lsFaseCirc.add(faseCirc);
            
            String key = oItem.getNumeroCircuito();
            map.put(key, oItem);
		}		

        double potq = this.potenciaQuadro;
        String fasq = this.sistemaFase;
        double vfq = 0.0;
        double corq = 0.0;
        int nfq = -1;
        String fsq = "?";
        
        if( ( AppDefs.FIA_S_FN.equals(fasq)  ) || 
        	( AppDefs.FIA_S_FNT.equals(fasq) ) ) {
            vfq = vfase / Math.sqrt(3.0);
            corq = (potq / vfq) / (ftmp * fred);
            nfq = 1;
            fsq = "R";
        }
        else if( ( AppDefs.FIA_S_2F.equals(fasq)   ) || 
        		 ( AppDefs.FIA_S_2FT.equals(fasq)  ) || 
        		 ( AppDefs.FIA_S_2FN.equals(fasq)  ) || 
        		 ( AppDefs.FIA_S_2FNT.equals(fasq) ) ) {
            vfq = vfase;
            corq = (potq / vfq) / (ftmp * fred);
            nfq = 2;
            fsq = "RS";
        }
        else if( ( AppDefs.FIA_S_3F.equals(fasq)   ) || 
       		 	 ( AppDefs.FIA_S_3FT.equals(fasq)  ) || 
        		 ( AppDefs.FIA_S_3FN.equals(fasq)  ) || 
        		 ( AppDefs.FIA_S_3FNT.equals(fasq) ) ) {
            vfq = vfase;
            corq = (potq / (vfq * Math.sqrt(3.0))) / (ftmp * fred);
            nfq = 3;
            fsq = "RST";
        }
        
        ArrayList<CircuitoFaseVO> lcf = calc.processaEquilibrioFases(nfq, lsFaseCirc);
        for(CircuitoFaseVO cf : lcf)
        {
        	String key = cf.getCircuito();
        	
        	if( map.containsKey(key) ) {
        		CadCircuitoQuadroCargasEletricaOData ir = (CadCircuitoQuadroCargasEletricaOData)map.get(key);
                ir.setFaseCircuito(cf.getFase());        		
        	}
        }

        double prtq = calc.getProtecao(corq);

        double bitq = calc.getBitolaCondutor(nfq, corq, prtq);
        if (bitq < btminq)
        {
            bitq = btminq;
            corq = corminq;

            prtq = calc.getProtecao(corq);
        }

        if(prtq < djmin)
        	prtq = djmin;
        
        double bitprotq = calc.getBitolaCondutorProtecao(bitq);

        this.potenciaQuadro = potq;
        this.potenciaSemReservaQuadro = potq;
    	this.tensaoQuadro = vfq;
    	this.alimentadorQuadro = bitq;
    	this.alimentadorProtecaoQuadro = bitprotq;
    	this.disjuntorQuadro = prtq;
    	this.faseQuadro = fsq;
    	//
        this.correnteBarraQuadro = prtq * 1.5;					// IB = 150% * DISJUNTOR
        this.correnteBarraNeutroQuadro = correnteBarraQuadro;
        this.correnteBarraProtecaoQuadro = correnteBarraQuadro / 2.0;
        //
        //this.tensaoQuadro = vfase;
        this.bitolaMinimaCondutor = btmin;
        this.disjuntorMinimoProtecao = djmin;
        this.temperaturaAmbiente = temp;
        this.fatorReducao = fred;
    	
	}
	
//Public
    
    public CadQuadroCargasEletrica(CadBlockDef oBlkDef, CadLayerDef oLayer, CadLevel oLevel, double zLevel, boolean bLocked) {
    	super(AppDefs.OBJTYPE_MODELQUADROCARGAS, oBlkDef, oLayer, oLevel, zLevel, bLocked);
    }

    /* Methodes */
    
	public void init(
		GeomPoint3d ptIns,
		String nomeQuadro,
		String descricaoQuadro,
		Double tensaoQuadro,
		Double bitolaMinimaCondutor,
		Double disjuntorMinimoProtecao,
		Double temperaturaAmbiente,
		Double fatorReducao,	
		String sistemaFase,
		Double potenciaSemReservaQuadro,
		Double potenciaQuadro,
		Double alimentadorQuadro,
		Double alimentadorProtecaoQuadro,
		Double disjuntorQuadro,
		String faseQuadro)
	{
		this.ptIns = ptIns;
		this.nomeQuadro = nomeQuadro;
		this.descricaoQuadro = descricaoQuadro;
		this.tensaoQuadro = tensaoQuadro;
		this.bitolaMinimaCondutor = bitolaMinimaCondutor;
		this.disjuntorMinimoProtecao = disjuntorMinimoProtecao;
		this.temperaturaAmbiente = temperaturaAmbiente;
		this.fatorReducao = fatorReducao;	
		this.sistemaFase = sistemaFase;
		this.potenciaSemReservaQuadro = potenciaSemReservaQuadro;
		this.potenciaQuadro = potenciaQuadro;
		this.alimentadorQuadro = alimentadorQuadro;
		this.alimentadorProtecaoQuadro = alimentadorProtecaoQuadro;
		this.disjuntorQuadro = disjuntorQuadro;
		this.faseQuadro = faseQuadro;

		//RE-CALCULO QUADRO_CARGAS
		//
		double vfase = this.tensaoQuadro;
		double btmin = this.bitolaMinimaCondutor;
		double djmin = this.disjuntorMinimoProtecao;
		double temp = this.temperaturaAmbiente;
		double fred = this.fatorReducao;
		
		this.reCalcQuadroCargas(vfase, btmin, djmin, temp, fred);
	}
    
	@Override
	public void init(ICadObject o) {
		CadQuadroCargasEletrica other = (CadQuadroCargasEletrica)o;

		this.init(
			other.ptIns,
			other.nomeQuadro,
			other.descricaoQuadro,
			other.tensaoQuadro,
			other.getBitolaMinimaCondutor(),
			other.getDisjuntorMinimoProtecao(),
			other.getTemperaturaAmbiente(),
			other.getFatorReducao(),
			other.sistemaFase,
			other.potenciaSemReservaQuadro,
			other.potenciaQuadro,
			other.alimentadorQuadro,
			other.alimentadorProtecaoQuadro,
			other.disjuntorQuadro,
			other.faseQuadro );
	}

	/* INIT_TABLE */
	
    private void initTableHeader()
    {
    	this.lsHeader = new ArrayList<TableHeaderVO>();
    	
    	for(int i = 0; i < AppDefs.ARR_TBLCOL_QUADRO_CARGAS.length; i++) {
    		ColunaTabelaVO o = AppDefs.ARR_TBLCOL_QUADRO_CARGAS[i];

        	this.lsHeader.add( new TableHeaderVO(i, o.getTitulo(), AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, o.getWidth(), o.getHeight()) );  
    	}
    }

    private void initTableRows()
    {
    	this.lsRows = new ArrayList<TableRowVO>();
    	
    	NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
    	
    	int szRow = this.lsItem.size();
    	for(int j = 0; j < szRow; j++) {
    		CadCircuitoQuadroCargasEletricaOData oItem = this.lsItem.get(j);
    		
    		TableRowVO oRow = new TableRowVO(); 
    		int rowNum = j + 1;

	    	int szCol = AppDefs.ARR_TBLCOL_QUADRO_CARGAS.length;
	    	for(int i = 0; i < szCol; i++) {
	    		ColunaTabelaVO o = AppDefs.ARR_TBLCOL_QUADRO_CARGAS[i];
	
	        	String colName = o.getColumnName();  
	        	int dprec = o.getDprec();
	        	
	    		//Object oVal = oItem.toValueByName(colName);
	        	Object oVal = oItem.toStrValueByName(colName, dprec);
	    		
	    		String strVal = oVal.toString();
	
	    		oRow.addTableCell(new TableCellVO(rowNum, i, colName, strVal, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, o.getWidth(), o.getHeight()) );
	    	}
			this.lsRows.add(oRow);
    	}
    }
	
	public synchronized ArrayList<CadCircuitoQuadroCargasEletricaOData> getLsItem()
	{
		return this.lsItem;
	}
	
	public synchronized int getSzLsItem()
	{
		int sz = this.lsItem.size();
		return sz;
	}
	
	public synchronized void addItem(CadCircuitoQuadroCargasEletricaOData o)
	{
		String key = o.getNumeroCircuito();
		
		if( !this.mapItem.containsKey(key) ) {
			this.mapItem.put(key, o);
			this.lsItem.add(o);
		}
	}
	
	public synchronized CadCircuitoQuadroCargasEletricaOData getItemAt(int pos)
	{
		int sz = this.lsItem.size();
		if(pos < sz) {
			CadCircuitoQuadroCargasEletricaOData o = this.lsItem.get(pos);
			return o;
		}
		return null;
	}
	
	public synchronized CadCircuitoQuadroCargasEletricaOData getItemByKey(int numeroCI)
	{
		String key = Integer.toString(numeroCI);
		
		if( this.mapItem.containsKey(key) ) {
			CadCircuitoQuadroCargasEletricaOData o = (CadCircuitoQuadroCargasEletricaOData)this.mapItem.get(key);
			return o;
		}
		return null;
	}

	/* CREATE */
	
	public static CadQuadroCargasEletrica create(
		CadBlockDef oBlkDef, 
		CadLayerDef oLayer,
		CadLevel oLevel,
		GeomPoint3d ptIns,
		String nomeQuadro,
		String descricaoQuadro,
		Double tensaoQuadro,
		Double bitolaMinimaCondutor,
		Double disjuntorMinimoProtecao,
		Double temperaturaAmbiente,
		Double fatorReducao,	
		String sistemaFase)
	{
		CadQuadroCargasEletrica o = new CadQuadroCargasEletrica(oBlkDef, oLayer, oLevel, 0.0, false);

		o.init(
			ptIns,
			nomeQuadro,
			descricaoQuadro,
			tensaoQuadro,
			bitolaMinimaCondutor,
			disjuntorMinimoProtecao,
			temperaturaAmbiente,
			fatorReducao,
			sistemaFase,
			0.0,
			0.0,
			0.0,
			0.0,
			0.0,
			AppDefs.NULL_STR);
    	return o;
    }
	
	public static CadQuadroCargasEletrica create(CadQuadroCargasEletrica other)
	{
		CadQuadroCargasEletrica o = new CadQuadroCargasEletrica(other.getBlkDef(), other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	public static CadQuadroCargasEletrica create(CadBlockDef blkDef, CadQuadroCargasEletrica other)
	{
		CadQuadroCargasEletrica o = new CadQuadroCargasEletrica(blkDef, other.getLayer(), other.getLevel(), other.getZLevel(), other.isLocked());
    	o.init(other);
    	return o;
    }
	
	/* OPERATIONS */
	
	@Override
	public CadQuadroCargasEletrica duplicate()
	{
		CadQuadroCargasEletrica other = CadQuadroCargasEletrica.create(this);
		return other;
	}
	
	@Override
	public CadQuadroCargasEletrica duplicate(CadBlockDef blkDef)
	{
		CadQuadroCargasEletrica other = CadQuadroCargasEletrica.create(blkDef, this);
		return other;
	}
	
	@Override
	public CadQuadroCargasEletrica copyTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
		CadQuadroCargasEletrica other = CadQuadroCargasEletrica.create(this);
		other.moveTo(ptIMcs, ptFMcs);
		return other;
	}

	@Override
	public CadQuadroCargasEletrica moveTo(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
    	MoveData3dVO o = GeomUtil.moveToPt3d(ptIMcs, ptFMcs, this.ptIns);
    	this.ptIns = o.getPtDest();
    	return this;
	}
	
	@Override
	public ICadEntity mirror(GeomPoint3d ptIMcs, GeomPoint3d ptFMcs) {
    	return this;
	}
	
	@Override
	public CadQuadroCargasEletrica scaleTo(double refDist, GeomPoint3d ptIMcs, GeomPoint3d ptFMcs)
	{
    	return this;
	}
	
	@Override
	public CadQuadroCargasEletrica offsetTo(GeomPoint3d ptIMcs, GeomVector3d uDirMcs, double dist)
	{
		return this;
	}
	
	/* DEBUG */

	@Override
	public ArrayList<ItemDataVO> toPropertyList() {
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);

		ArrayList<ItemDataVO> lsProperty = GeomUtil.toBasicPropertyList(this);

		lsProperty.addAll( this.ptIns.toPropertyList("Pt.Ins.", true) );
				
		lsProperty.add( new ItemDataVO("Nome", this.nomeQuadro, true) );
		lsProperty.add( new ItemDataVO("Descricao", this.descricaoQuadro, true) );
		lsProperty.add( new ItemDataVO("Tensao(V)", nf0.format( this.tensaoQuadro ), true) );
		lsProperty.add( new ItemDataVO("Bitola Minima(mm2)", nf0.format( this.bitolaMinimaCondutor ), true) );
		lsProperty.add( new ItemDataVO("Disjuntor Minimo(A)", nf0.format( this.disjuntorMinimoProtecao ), true) );
		lsProperty.add( new ItemDataVO("Temp.Ambiente(C)", nf0.format( this.temperaturaAmbiente ), true) );
		lsProperty.add( new ItemDataVO("Fator Reducao", nf3.format( this.fatorReducao ), true) );
		lsProperty.add( new ItemDataVO("Sistema", nf0.format( this.sistemaFase ), true) );
		lsProperty.add( new ItemDataVO("Pot.Sem Reserva(VA)", nf0.format( this.potenciaSemReservaQuadro ), true) );
		lsProperty.add( new ItemDataVO("Potencia(VA)", nf0.format( this.potenciaQuadro ), true) );
		lsProperty.add( new ItemDataVO("Alimentador(mm2)", nf0.format( this.alimentadorQuadro ), true) );
		lsProperty.add( new ItemDataVO("Alim.Protecao(mm2)", nf0.format( this.alimentadorProtecaoQuadro ), true) );
		lsProperty.add( new ItemDataVO("Disjuntor(A)", nf0.format( this.disjuntorQuadro ), true) );
		lsProperty.add( new ItemDataVO("Fase", this.faseQuadro, true) );

		return lsProperty;
	}
	
	@Override
	public String toStr() {
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);
		
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		NumberFormat nf6 = FormatUtil.newNumberFormatPtBr(6);
		
		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATE_TYPE2_PTBR_MASC);

		String str = String.format(
			"ptIns:%s;" + 
			"nomeQuadro:%s;" + 
			"descricaoQuadro:%s;" + 
			"tensaoQuadro:%s;" + 
			"bitolaMinimaCondutor:%s;" +
			"disjuntorMinimoProtecao:%s;" +
			"temperaturaAmbiente:%s;" +
			"fatorReducao:%s;" +
			"sistemaFase:%s;" +
			"potenciaSemReservaQuadro:%s;" +
			"potenciaQuadro:%s;" +
			"alimentadorQuadro:%s;" +
			"alimentadorProtecaoQuadro:%s;" +
			"disjuntorQuadro:%s;" +
			"faseQuadro:%s;",
			this.ptIns.toStr(),
			this.nomeQuadro,
			this.descricaoQuadro,
			nf1.format( this.tensaoQuadro ),
			nf1.format( this.bitolaMinimaCondutor ),
			nf0.format( this.disjuntorMinimoProtecao ),
			nf1.format( this.temperaturaAmbiente ),
			nf3.format( this.fatorReducao ),	
			this.sistemaFase,
			nf3.format( this.potenciaSemReservaQuadro ),
			nf3.format( this.potenciaQuadro ),
			nf1.format( this.alimentadorQuadro ),
			nf1.format( this.alimentadorProtecaoQuadro ),
			nf0.format( this.disjuntorQuadro ),
			this.faseQuadro );
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
	public DrawCache createOsnapCache() {
		return null;
	}

    /* DRAWING */
    
	public void redraw2d_drawTable(ICadViewBase v, GeomPoint2d ptIns2d, double sclFact, String strNome, String strDescricao, Graphics g)
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);

		NumberFormat nf1 = FormatUtil.newNumberFormatWithoutGroupingPtBr(1);

		NumberFormat nf3 = FormatUtil.newNumberFormatWithoutGroupingPtBr(3);

		NumberFormat nf6 = FormatUtil.newNumberFormatWithoutGroupingPtBr(6);

        GeomPlan2d planMcs = v.getPlanMcs2d();
        
        GeomVector2d axisX = planMcs.getAxisX();
        GeomVector2d axisY = planMcs.getAxisY();
        
        double fTitleSzMcs = this.fontTitleSzMili * sclFact;
        double fHeaderSzMcs = this.fontHeaderSzMili * sclFact;
        double fCellSzMcs = this.fontCellSzMili * sclFact;
    	
        double hTitleLineMcs = 1.25 * fTitleSzMcs;
        double hHeaderLineMcs = 1.25 * fHeaderSzMcs;
        double hTextLineMcs = 1.25 * fCellSzMcs;
        
        double wTitleLineMcs = fTitleSzMcs;
        double wHeaderLineMcs = fHeaderSzMcs;
        double wTextLineMcs = fCellSzMcs;
        
        String strTitle1 = strNome;
        
        String strTitle2 = strDescricao;
        
        this.initTableHeader();
        
        this.initTableRows();
        
        int szRows = this.lsRows.size();
        
        double hTableTitleMcs =  ( 2.0 + 2.0) * hTitleLineMcs;
        double hTableParamMcs =  ( 7.0 + 2.0) * hTextLineMcs;
        double hTableHeaderMcs = ( 1.0 + 2.0) * hHeaderLineMcs;
        double hTableRowMcs =    ( 1.0 + 2.0) * hTextLineMcs;

        double hTableMcs = hTableTitleMcs + hTableParamMcs + hTableHeaderMcs + (szRows * hTableRowMcs);
        double wTableMcs = 0.0; 
        
        for(TableHeaderVO oHdr : this.lsHeader) {
        	double colWidthMcs = (oHdr.getColWidthScr() / AppDefs.UNIT_FACTOR_POL_TO_MM) * sclFact;
        	wTableMcs += colWidthMcs;
        }        
        
        GeomPoint2d pt0 = new GeomPoint2d(ptIns2d);
        GeomPoint2d pt1 = pt0.otherMoveTo(axisX, wTableMcs);
        GeomPoint2d pt2 = pt1.otherMoveTo(axisY, - hTableMcs - hTableRowMcs);
        GeomPoint2d pt3 = pt2.otherMoveTo(axisX, - wTableMcs);
        //
        DrawUtil.drawLineMcs(v, pt0, pt1, g);
        DrawUtil.drawLineMcs(v, pt1, pt2, g);
        DrawUtil.drawLineMcs(v, pt2, pt3, g);
        DrawUtil.drawLineMcs(v, pt3, pt0, g);
        
        GeomPoint2d pt4_A = pt0.otherMoveTo(axisY, - hTableTitleMcs);
        GeomPoint2d pt5_A = pt1.otherMoveTo(axisY, - hTableTitleMcs);
        //
        DrawUtil.drawLineMcs(v, pt4_A, pt5_A, g);

        GeomPoint2d pt4 = pt4_A.otherMoveTo(axisY, - hTableParamMcs);
        GeomPoint2d pt5 = pt5_A.otherMoveTo(axisY, - hTableParamMcs);
        //
        DrawUtil.drawLineMcs(v, pt4, pt5, g);

        GeomPoint2d pt6 = pt4.otherMoveTo(axisY, - hTableHeaderMcs);
        GeomPoint2d pt7 = pt5.otherMoveTo(axisY, - hTableHeaderMcs);
        //
        DrawUtil.drawLineMcs(v, pt6, pt7, g);

        for(int i = 0; i < szRows + 1; i++) {
        	double h = hTableRowMcs * i;
        	
            GeomPoint2d pt8 = pt6.otherMoveTo(axisY, - h);
            GeomPoint2d pt9 = pt7.otherMoveTo(axisY, - h);
            //
            DrawUtil.drawLineMcs(v, pt8, pt9, g);        	
        }        
        
        GeomPoint2d pt10 = new GeomPoint2d(pt4);
        GeomPoint2d pt11 = new GeomPoint2d(pt3);
        
        for(TableHeaderVO oHdr : this.lsHeader) {
        	double colWidthMcs = (oHdr.getColWidthScr() / AppDefs.UNIT_FACTOR_POL_TO_MM) * sclFact;
        	
            pt10 = pt10.otherMoveTo(axisX, colWidthMcs);
            pt11 = pt11.otherMoveTo(axisX, colWidthMcs);
            //
            DrawUtil.drawLineMcs(v, pt10, pt11, g);        	
        }        
        
        /* TEXTOS */
        
        GeomPoint2d ptMid2d = GeomUtil.midPointOf(pt0, pt1);
        
        GeomPoint2d ptLabelTitle1 = ptMid2d.otherMoveTo(axisY, - (2.0 * hTitleLineMcs));
        GeomPoint2d ptLabelTitle2 = ptLabelTitle1.otherMoveTo(axisY,  - hTitleLineMcs);
        
    	DrawUtil.drawTextMcs(v, strTitle1, ptLabelTitle1, fTitleSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_BOTTOM, g);
    	DrawUtil.drawTextMcs(v, strTitle2, ptLabelTitle2, fTitleSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_BOTTOM, g);
    	
    	//TABLE_PARAM
    	//
    	String strLabel1 = String.format("Tensao entre fases: %s V", nf0.format(this.tensaoQuadro) );
    	String strLabel2 = String.format("Bitola minima do condutor: %s mm2", nf1.format(this.bitolaMinimaCondutor) );
    	String strLabel3 = String.format("Disjuntor minimo de protecao: %s mm2", nf1.format(this.disjuntorMinimoProtecao) );
    	String strLabel4 = String.format("Temperatura ambiente: %s .C", nf1.format(this.temperaturaAmbiente) );
    	String strLabel5 = String.format("Fator de reducao: %s %%", nf1.format(this.fatorReducao * 100.0) );
    	//
    	//String strLabel6 = String.format("Carga total do quadro: %s VA", nf3.format(this.potenciaQuadro) );
    	//String strLabelx = String.format("%s VA", nf3.format(this.potenciaSemReservaQuadro) );
    	//String strLabel7 = String.format("Alimentador do quadro: %s mm2", nf1.format(this.alimentadorQuadro) );
    	//String strLabel8 = String.format("Alim. de protecao do quadro: %s mm2", nf1.format(this.alimentadorProtecaoQuadro) );
    	//String strLabel9 = String.format("Disjuntor geral do quadro: %s A", nf1.format(this.disjuntorQuadro) );
    	//String strLabelx = this.faseQuadro;
    	//
    	//String strLabelx = String.format("%s A", nf1.format(this.correnteBarraQuadro) );
    	String strLabel10 = String.format("Corrente max. da barra de neutros: %s A", nf1.format(this.correnteBarraNeutroQuadro) );
    	String strLabel11 = String.format("Corrente max. da barra de protecao: %s A", nf1.format(this.correnteBarraProtecaoQuadro) );

    	double xPtLabel1 = pt4_A.getX() + (2.0 * wTextLineMcs);
    	double yPtLabel1 = pt4_A.getY() - (2.0 * wTextLineMcs);
    	
        GeomPoint2d ptLabel1  = new GeomPoint2d(xPtLabel1, yPtLabel1);
        GeomPoint2d ptLabel2  = ptLabel1.otherMoveTo(axisY,  - hTextLineMcs);
        GeomPoint2d ptLabel3  = ptLabel2.otherMoveTo(axisY,  - hTextLineMcs);
        GeomPoint2d ptLabel4  = ptLabel3.otherMoveTo(axisY,  - hTextLineMcs);
        GeomPoint2d ptLabel5  = ptLabel4.otherMoveTo(axisY,  - hTextLineMcs);
        //
        //GeomPoint2d ptLabel6  = ptLabel5.otherMoveTo(axisY,  - hTextLineMcs);
        //GeomPoint2d ptLabel7  = ptLabel6.otherMoveTo(axisY,  - hTextLineMcs);
        //GeomPoint2d ptLabel8  = ptLabel7.otherMoveTo(axisY,  - hTextLineMcs);
        //GeomPoint2d ptLabel9  = ptLabel8.otherMoveTo(axisY,  - hTextLineMcs);
        //
        GeomPoint2d ptLabel10 = ptLabel5.otherMoveTo(axisY,  - hTextLineMcs);
        GeomPoint2d ptLabel11 = ptLabel10.otherMoveTo(axisY, - hTextLineMcs);
        
    	DrawUtil.drawTextMcs(v, strLabel1,  ptLabel1, fCellSzMcs,  AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);
    	DrawUtil.drawTextMcs(v, strLabel2,  ptLabel2, fCellSzMcs,  AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);
    	DrawUtil.drawTextMcs(v, strLabel3,  ptLabel3, fCellSzMcs,  AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);
    	DrawUtil.drawTextMcs(v, strLabel4,  ptLabel4, fCellSzMcs,  AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);
    	DrawUtil.drawTextMcs(v, strLabel5,  ptLabel5, fCellSzMcs,  AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);
    	//        
    	//DrawUtil.drawTextMcs(v, strLabel6,  ptLabel6, fCellSzMcs,  AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);
    	//DrawUtil.drawTextMcs(v, strLabel7,  ptLabel7, fCellSzMcs,  AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);
    	//DrawUtil.drawTextMcs(v, strLabel8,  ptLabel8, fCellSzMcs,  AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);
    	//DrawUtil.drawTextMcs(v, strLabel9,  ptLabel9, fCellSzMcs,  AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);
    	//
    	DrawUtil.drawTextMcs(v, strLabel10, ptLabel10, fCellSzMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);
    	DrawUtil.drawTextMcs(v, strLabel11, ptLabel11, fCellSzMcs, AppDefs.HORIZALIGN_LEFT, AppDefs.VERTALIGN_MIDDLE, g);
    	
    	//TABLE_HEADER
    	//
    	double w = 0.0;
        for(TableHeaderVO oHdr : this.lsHeader) {
        	double colWidthMcs = (oHdr.getColWidthScr() / AppDefs.UNIT_FACTOR_POL_TO_MM) * sclFact;
        	w = w + colWidthMcs;
        	
            double xPt12 = pt4.getX() + w - (colWidthMcs / 2.0);
            double yPt12 = pt4.getY() - (hTableHeaderMcs / 2.0);

            GeomPoint2d ptLabelHdr = new GeomPoint2d(xPt12, yPt12);
            
            String strHdr = oHdr.getTitle();
        	DrawUtil.drawTextMcs(v, strHdr, ptLabelHdr, fHeaderSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
        }

        //TABLE_ROWS
        //
        for(int i = 0; i < szRows; i++) {
        	double xPt13 = pt6.getX();
        	double yPt13 = pt6.getY() - (hTableRowMcs * i) - (hTableRowMcs / 2.0);

        	double w13 = 0.0;

        	TableRowVO oRow = this.lsRows.get(i);
        	
        	int sz1 = oRow.getNumTableCell();
            for(int j = 0; j < sz1; j++) {
            	TableCellVO oCell = oRow.getTableCell(j);
            	
            	double colWidthMcs = (oCell.getColWidthScr() / AppDefs.UNIT_FACTOR_POL_TO_MM) * sclFact;
                w13 = w13 + colWidthMcs;
            	
                double xPt14 = xPt13 + w13 - (colWidthMcs / 2.0);
                double yPt14 = yPt13;

                GeomPoint2d ptLabelCell = new GeomPoint2d(xPt14, yPt14);
            	
                String strCell = oCell.getText();
            	DrawUtil.drawTextMcs(v, strCell, ptLabelCell, fCellSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
            }

        }        

        //TABLE_SUMARY
        //
    	double xPt13 = pt6.getX();
    	double yPt13 = pt6.getY() - (hTableRowMcs * szRows) - (hTableRowMcs / 2.0);

    	double w13 = 0.0;

        for(ColunaTabelaVO oCol : AppDefs.ARR_TBLCOL_QUADRO_CARGAS) {
        	double colWidthMcs = (oCol.getWidth() / AppDefs.UNIT_FACTOR_POL_TO_MM) * sclFact;
            w13 = w13 + colWidthMcs;
        	
            double xPt14 = xPt13 + w13 - (colWidthMcs / 2.0);
            double yPt14 = yPt13;

            GeomPoint2d ptLabelCell = new GeomPoint2d(xPt14, yPt14);

            String strCell = "";
            
            if( "qtdCargaCircuito".equals(oCol.getColumnName()) ) {
            	if(this.qtdTotalCargasQuadro > 0)
            		strCell = String.format("%s", nf0.format(this.qtdTotalCargasQuadro));            	
            }
            else if( "cargaCircuito".equals(oCol.getColumnName()) ) {
            	if(this.totalCargasQuadro > AppDefs.MATHPREC_MIN)
            		strCell = String.format("%s", nf3.format(this.totalCargasQuadro));
            }
            else if( "qtdCargaPainel".equals(oCol.getColumnName()) ) {
            	if(this.qtdCargasPaineisQuadro > 0)
            		strCell = String.format("%s", nf0.format(this.qtdCargasPaineisQuadro));
            }
            else if( "cargaPainel".equals(oCol.getColumnName()) ) {
            	if(this.cargasPaineisQuadro > AppDefs.MATHPREC_MIN)
            		strCell = String.format("%s", nf3.format(this.cargasPaineisQuadro));
            }
            else if( "qtdCargaIluminacao".equals(oCol.getColumnName()) ) {
            	if(this.qtdCargasIluminacaoQuadro > 0)
            		strCell = String.format("%s", nf0.format(this.qtdCargasIluminacaoQuadro));
            }
            else if( "cargaIluminacao".equals(oCol.getColumnName()) ) {
            	if(this.cargasIluminacaoQuadro > AppDefs.MATHPREC_MIN)
            		strCell = String.format("%s", nf3.format(this.cargasIluminacaoQuadro));
            }
            else if( "qtdCargaTomada".equals(oCol.getColumnName()) ) {
            	if(this.qtdCargasTomadaQuadro > 0)
            		strCell = String.format("%s", nf0.format(this.qtdCargasTomadaQuadro));
            }
            else if( "cargaTomada".equals(oCol.getColumnName()) ) {
            	if(this.cargasTomadaQuadro > AppDefs.MATHPREC_MIN)
            		strCell = String.format("%s", nf3.format(this.cargasTomadaQuadro));
            }
            else if( "qtdCargaMotor".equals(oCol.getColumnName()) ) {
            	if(this.qtdCargasMotorQuadro > 0)
            		strCell = String.format("%s", nf0.format(this.qtdCargasMotorQuadro));
            }
            else if( "cargaMotor".equals(oCol.getColumnName()) ) {
            	if(this.cargasMotorQuadro > AppDefs.MATHPREC_MIN)
            		strCell = String.format("%s", nf3.format(this.cargasMotorQuadro));
            }
            else if( "qtdCargaRaioX".equals(oCol.getColumnName()) ) {
            	if(this.qtdCargasRaioXQuadro > 0)
            		strCell = String.format("%s", nf0.format(this.qtdCargasRaioXQuadro));
            }
            else if( "cargaRaioX".equals(oCol.getColumnName()) ) {
            	if(this.cargasRaioXQuadro > AppDefs.MATHPREC_MIN)
            		strCell = String.format("%s", nf3.format(this.cargasRaioXQuadro));
            }
            else if( "qtdCargaAquecimento".equals(oCol.getColumnName()) ) {
            	if(this.qtdCargasAquecimentoQuadro > 0)
            		strCell = String.format("%s", nf0.format(this.qtdCargasAquecimentoQuadro));
            }
            else if( "cargaAquecimento".equals(oCol.getColumnName()) ) {
            	if(this.cargasAquecimentoQuadro > AppDefs.MATHPREC_MIN)
            		strCell = String.format("%s", nf3.format(this.cargasAquecimentoQuadro));
            }
            else if( "qtdCargaOutra".equals(oCol.getColumnName()) ) {
            	if(this.qtdCargasOutrosQuadro > 0)
            		strCell = String.format("%s", nf0.format(this.qtdCargasOutrosQuadro));
            }
            else if( "cargaOutra".equals(oCol.getColumnName()) ) {
            	if(this.cargasOutrosQuadro > AppDefs.MATHPREC_MIN)
            		strCell = String.format("%s", nf3.format(this.cargasOutrosQuadro));
            }
            else if( "alimentadorCircuito".equals(oCol.getColumnName()) ) {
            	if(this.alimentadorQuadro > AppDefs.MATHPREC_MIN)
            		strCell = String.format("%s", nf1.format(this.alimentadorQuadro));
            }
            else if( "alimentadorProtecaoCircuito".equals(oCol.getColumnName()) ) {
            	if(this.alimentadorProtecaoQuadro > AppDefs.MATHPREC_MIN)
            		strCell = String.format("%s", nf1.format(this.alimentadorProtecaoQuadro));
            }
            else if( "disjuntorCircuito".equals(oCol.getColumnName()) ) {
            	if(this.disjuntorQuadro > AppDefs.MATHPREC_MIN)
            		strCell = String.format("%s", nf0.format(this.disjuntorQuadro));
            }
            else if( "faseCircuito".equals(oCol.getColumnName()) ) {
            	if( "".equals(this.faseQuadro) )
            		strCell = this.faseQuadro;
            }
            	
        	DrawUtil.drawTextMcs(v, strCell, ptLabelCell, fCellSzMcs, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE, g);
        }
        
	}
	
	@Override
    public void redraw2d(ICadViewBase v, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, Graphics g) 
    {
		NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
		
		NumberFormat nf1 = FormatUtil.newNumberFormatPtBr(1);

		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);

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

        GeomPoint2d ptDest2dMcs = new GeomPoint2d(this.ptIns);
        
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
		        	CadQuadroCargasEletrica other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, pt3dMcs);
		        	
		        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        }	        
		        else if( AppDefs.ACTION_EDIT2_MIRROR.equals(action) )
		        {
		        	CadQuadroCargasEletrica other = this.duplicate();
		        	other.mirror(ptBase3dMcs, pt3dMcs);
		        	
		        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        }
		        else if( AppDefs.ACTION_EDIT2_SCALE.equals(action) )
		        {
		        	if(dist > AppDefs.MATHPREC_MIN) {
		        		CadQuadroCargasEletrica other = this.duplicate();
			        	other.scaleTo(dist, ptBase3dMcs, pt3dMcs);
			        	
			        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        	}
		        }
		        else if( AppDefs.ACTION_DRAW1_OFFSET.equals(action) )
		        {
		        	GeomPoint3d newPt3dMcs = ptBase3dMcs.otherMoveTo(vDir3dMcs, dist);

		        	CadQuadroCargasEletrica other = this.duplicate();
		        	other.moveTo(ptBase3dMcs, newPt3dMcs);
		        	
		        	ptDest2dMcs = new GeomPoint2d(other.ptIns);
		        }
	        }        
        }

        //DRAW_QUADRO_CARGAS
        //
        String strNome = this.nomeQuadro;
    	String strDescricao = this.descricaoQuadro;
    	
        double ptInsX = this.ptIns.getX();
        double ptInsY = this.ptIns.getY();

        GeomPoint2d pt2d = new GeomPoint2d(ptInsX, ptInsY);

        GeomVector2d xDir2d = new GeomVector2d(ptInsX, ptInsY, ptInsX + 1, ptInsY);
        
    	redraw2d_drawTable(v, pt2d, sclFact, strNome, strDescricao, g);
            	
        GeomUtil.setColor(g, oldcol);
        
        GeomUtil.setLtype(g, oldltype);
    }

	@Override
	public void redraw3d(ICadViewBase view3d, double dist, GeomPoint2d ptBase2dMcs, GeomPoint2d pt2dMcs, double sclFact, boolean bDragMode, boolean bSelEnt, PrepareDrawUtil prep) {
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
		
        GeomPoint2d ptPoint2dMcs = new GeomPoint2d(this.ptIns);
        
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
	public boolean select3d(ICadViewBase view3d, GeomPoint2d pt2dMcs, double sclFact, boolean bSelectEntity) {
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
    	lsPtNodepoint.add(new GeomPoint3d(this.ptIns));    	
		
    	GeomPoint3d ptResult = null;
    	
    	ptResult = GeomUtil.osnap3d(view2d, AppDefs.OSNAPMODE_NODEPOINT, pt2dMcs, lsPtNodepoint, g);
    	if(ptResult != null) return ptResult;
    	
    	return null;
	}

	@Override
	public ArrayList<GeomPoint3d> osnap3d(ICadViewBase view2d, int osnapmode, GeomPoint2d pt2dMcs) 
	{
		if( this.isLocked() ) return null;
		
    	if( !this.isVisible() ) return null;

    	//NODEPOINT
    	//
    	ArrayList<GeomPoint3d> lsPtNodepoint = new ArrayList<GeomPoint3d>();
    	lsPtNodepoint.add(new GeomPoint3d(this.ptIns));    	
    	return lsPtNodepoint;
	}

	/* CENTROID */
	
	@Override
	public GeomPoint3d centroid()
	{
		GeomPoint3d ptResult = new GeomPoint3d(this.ptIns);
		return ptResult;
	}
	
	/* LOAD/SAVE */
	
	@Override
	public boolean load(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		return true;
	}

	public boolean save_lsdata(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		BaseODataDao odDao = dao.createODataDao(AppDefs.OBJTYPE_MEMORIACALCULOITEM_ODATA); 

		String strCadRefEntityId = Integer.toString(this.getObjectId());

		int sz = this.lsItem.size();
		for(int i = 0; i < sz; i++) {
			CadCircuitoQuadroCargasEletricaOData oItem = (CadCircuitoQuadroCargasEletricaOData)this.lsItem.get(i);

			CadCircuitoQuadroCargasEletricaODataRecord odataRec = new CadCircuitoQuadroCargasEletricaODataRecord(oItem);				
			odataRec.setCadRefEntityId(strCadRefEntityId);
			odataRec.setObjVer(objVer);
			
			Object[] arrVal = {
				//TODO:
			};

			int rscode = odDao.insertOrUpdate(
				objVer,
				schemaName,
				odataRec, 
				arrVal );
			if(rscode < 0) return false;
		}		
		return true;
	}
	
	public boolean save_entity(String objVer, BaseDao dao, String schemaName, CadDocumentDef doc)
	{
		boolean bResult = false;

		DateFormat df = new SimpleDateFormat(AppDefs.DEF_DATETIME_TYPE5_FILEFORMAT_MASC);
		
		this.setObjVer(objVer);
		
		String strIsMinimized = StringUtil.fromBoolToStr(this.bMinimized);
		
		Object[] arrVal = {
			new Double( this.ptIns.getX() ),
			new Double( this.ptIns.getY() ),
			new Double( this.ptIns.getZ() ),
			//
			//TODO:
		};
		
		BaseEntityDao entDao = dao.create(this.getObjType()); 
		
		CadQuadroCargasEletricaRecord entRec = new CadQuadroCargasEletricaRecord(this); 
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
		boolean bResult = this.save_entity(objVer, dao, schemaName, doc);
		if( !bResult ) return false;
		
		bResult = this.save_lsdata(objVer, dao, schemaName, doc);
		if( !bResult ) return false;

		return bResult;
	}

	/* READ/WRITE DXF R12 */
	
	@Override
	public void fromDxfR12(DxfCadEntity o)
	{
		//TODO:
	}
	
	@Override
	public ArrayList<DxfCadEntity> toDxfR12()
	{
		ArrayList<DxfCadEntity> lsDxfCadEntity = new ArrayList<DxfCadEntity>(); 
		
		ArrayList<DxfCadEntity> lsCadEntity2d = toDxfR12_view2d();
		lsDxfCadEntity.addAll( lsCadEntity2d );
		
		return lsDxfCadEntity;
	}
		
	@Override
	public ArrayList<DxfCadEntity> toDxfR12_view2d()
	{
		ArrayList<DxfCadEntity> lsDxfResult = new ArrayList<DxfCadEntity>();
				
    	NumberFormat nf0 = FormatUtil.newNumberFormatWithoutGroupingPtBr(0);
    	
    	NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
    	
		int objectId = this.getObjectId(); 
		int subObjectId = objectId * 1000;
		
		CadLayerDef oLayer = this.getLayer();
    	
    	double sclFact = AppDefs.DEF_DEFAULT_PROJECT_SCALEFACTOR;
		
        GeomVector2d axisX = new GeomVector2d(1.0, 0.0);
        GeomVector2d axisY = new GeomVector2d(0.0, 1.0);
        
        double fTitleSzMcs = this.fontTitleSzMili * sclFact;
        double fHeaderSzMcs = this.fontHeaderSzMili * sclFact;
        double fCellSzMcs = this.fontCellSzMili * sclFact;
    	
        double hTitleLineMcs = fTitleSzMcs;
        double hHeaderLineMcs = fHeaderSzMcs;
        double hTextLineMcs = fCellSzMcs;
    	
        String strNome = this.nomeQuadro;
    	String strDescricao = this.descricaoQuadro;
    	
    	GeomPoint2d ptIns2d = new GeomPoint2d(this.ptIns);
    	
        double ptInsX = ptIns2d.getX();
        double ptInsY = ptIns2d.getY();
        
        String strTitle1 = strNome;
        
        String strTitle2 = strDescricao;
        
        this.initTableHeader();
        
        this.initTableRows();
        
        int szRows = this.lsRows.size();
        
        double hTableTitleMcs = 4.0 * hTitleLineMcs;
        double hTableHeaderMcs = 2.0 * hHeaderLineMcs;
        double hTableRowMcs = 2.0 * hTextLineMcs;

        double hTableMcs = hTableTitleMcs + hTableHeaderMcs + (szRows * hTableRowMcs);
        double wTableMcs = 0.0; 
        
        for(TableHeaderVO oHdr : this.lsHeader) {
        	double colWidthMcs = (oHdr.getColWidthScr() / AppDefs.UNIT_FACTOR_POL_TO_MM) * sclFact;
        	wTableMcs += colWidthMcs;
        }        
        
        GeomPoint2d pt0 = new GeomPoint2d(ptIns2d);
        GeomPoint2d pt1 = pt0.otherMoveTo(axisX, wTableMcs);
        GeomPoint2d pt2 = pt1.otherMoveTo(axisY, - hTableMcs);
        GeomPoint2d pt3 = pt2.otherMoveTo(axisX, - wTableMcs);
        //
        lsDxfResult.addAll( DxfUtil.toDxfLine(oLayer, pt0.getX(), pt0.getY(), 0.0, pt1.getX(), pt1.getY(), 0.0) );
        lsDxfResult.addAll( DxfUtil.toDxfLine(oLayer, pt1.getX(), pt1.getY(), 0.0, pt2.getX(), pt2.getY(), 0.0) );
        lsDxfResult.addAll( DxfUtil.toDxfLine(oLayer, pt2.getX(), pt2.getY(), 0.0, pt3.getX(), pt3.getY(), 0.0) );
        lsDxfResult.addAll( DxfUtil.toDxfLine(oLayer, pt3.getX(), pt3.getY(), 0.0, pt0.getX(), pt0.getY(), 0.0) );
        
        GeomPoint2d pt4 = pt0.otherMoveTo(axisY, - hTableTitleMcs);
        GeomPoint2d pt5 = pt1.otherMoveTo(axisY, - hTableTitleMcs);
        //
        lsDxfResult.addAll( DxfUtil.toDxfLine(oLayer, pt4.getX(), pt4.getY(), 0.0, pt5.getX(), pt5.getY(), 0.0) );

        GeomPoint2d pt6 = pt4.otherMoveTo(axisY, - hTableHeaderMcs);
        GeomPoint2d pt7 = pt5.otherMoveTo(axisY, - hTableHeaderMcs);
        //
        lsDxfResult.addAll( DxfUtil.toDxfLine(oLayer, pt6.getX(), pt6.getY(), 0.0, pt7.getX(), pt7.getY(), 0.0) );

        int sz = this.lsRows.size();
        for(int i = 0; i < sz; i++) {
        	double h = hTableRowMcs * i;
        	
            GeomPoint2d pt8 = pt6.otherMoveTo(axisY, - h);
            GeomPoint2d pt9 = pt7.otherMoveTo(axisY, - h);
            //
            lsDxfResult.addAll( DxfUtil.toDxfLine(oLayer, pt8.getX(), pt8.getY(), 0.0, pt9.getX(), pt9.getY(), 0.0) );
        }        
        
        GeomPoint2d pt10 = new GeomPoint2d(pt4);
        GeomPoint2d pt11 = new GeomPoint2d(pt3);
        
        for(TableHeaderVO oHdr : this.lsHeader) {
        	double colWidthMcs = (oHdr.getColWidthScr() / AppDefs.UNIT_FACTOR_POL_TO_MM) * sclFact;
        	
            pt10 = pt10.otherMoveTo(axisX, colWidthMcs);
            pt11 = pt11.otherMoveTo(axisX, colWidthMcs);
            //
            lsDxfResult.addAll( DxfUtil.toDxfLine(oLayer, pt10.getX(), pt10.getY(), 0.0, pt11.getX(), pt11.getY(), 0.0) );
        }        
        
        /* TEXTOS */
        
        GeomPoint2d ptMid2d = GeomUtil.midPointOf(pt0, pt1);
        
        GeomPoint2d ptLabelTitle1 = ptMid2d.otherMoveTo(axisY, - (2.0 * hTitleLineMcs));
        GeomPoint2d ptLabelTitle2 = ptLabelTitle1.otherMoveTo(axisY, - hTitleLineMcs);

    	lsDxfResult.addAll( DxfUtil.toDxfText(oLayer, strTitle1, ptLabelTitle1.getX(), ptLabelTitle1.getY(), 0.0, hTitleLineMcs, 0.0, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE) );
    	lsDxfResult.addAll( DxfUtil.toDxfText(oLayer, strTitle2, ptLabelTitle2.getX(), ptLabelTitle2.getY(), 0.0, hTitleLineMcs, 0.0, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE) );
        
    	double w = 0.0;
        for(TableHeaderVO oHdr : this.lsHeader) {
        	double colWidthMcs = (oHdr.getColWidthScr() / AppDefs.UNIT_FACTOR_POL_TO_MM) * sclFact;
        	w = w + colWidthMcs;
        	
            double xPt12 = pt4.getX() + w - (colWidthMcs / 2.0);
            double yPt12 = pt4.getY() - (hTableHeaderMcs / 2.0);

            GeomPoint2d ptLabelHdr = new GeomPoint2d(xPt12, yPt12);
            
            String strHdr = oHdr.getTitle();
        	lsDxfResult.addAll( DxfUtil.toDxfText(oLayer, strHdr, ptLabelHdr.getX(), ptLabelHdr.getY(), 0.0, hHeaderLineMcs, 0.0, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE) );
        }

        sz = this.lsRows.size();
        for(int i = 0; i < sz; i++) {
        	double xPt13 = pt6.getX();
        	double yPt13 = pt6.getY() - (hTableRowMcs * i) - (hTableRowMcs / 2.0);

        	double w13 = 0.0;

        	TableRowVO oRow = this.lsRows.get(i);
        	
        	int sz1 = oRow.getNumTableCell();
            for(int j = 0; j < sz1; j++) {
            	TableCellVO oCell = oRow.getTableCell(j);
            	
            	double colWidthMcs = (oCell.getColWidthScr() / AppDefs.UNIT_FACTOR_POL_TO_MM) * sclFact;
                w13 = w13 + colWidthMcs;
            	
                double xPt14 = xPt13 + w13 - (colWidthMcs / 2.0);
                double yPt14 = yPt13;

                GeomPoint2d ptLabelCell = new GeomPoint2d(xPt14, yPt14);
            	
                String strCell = oCell.getText();
            	lsDxfResult.addAll( DxfUtil.toDxfText(oLayer, strCell, ptLabelCell.getX(), ptLabelCell.getY(), 0.0, hTextLineMcs, 0.0, AppDefs.HORIZALIGN_CENTER, AppDefs.VERTALIGN_MIDDLE) );
            }

        }        				
		return lsDxfResult;
	}
	
	@Override
	public ArrayList<DxfCadEntity> toDxfR12_view3d()
	{
		return null;
	}

	/* RECALC - QUADRO_CARGAS */
	
	public void reCalcQuadroCargas()
	{
		//RE-CALCULO QUADRO_CARGAS
		//
		double vfase = this.tensaoQuadro;
		double btmin = this.bitolaMinimaCondutor;
		double djmin = this.disjuntorMinimoProtecao;
		double temp = this.temperaturaAmbiente;
		double fred = this.fatorReducao;
		
		this.reCalcQuadroCargas(vfase, btmin, djmin, temp, fred);
	}
	
	public void reCalcQuadroCargas(double vfase, double btmin, double djmin, double temp, double fred)
	{
		this.resetAllPontoEletrica();
		this.loadAllPontoEletrica();
		this.dimensionaQuadroCargas(vfase, btmin, djmin, temp, fred);
	}
	
	/* Getters/Setters */

	@Override
	public GeomDimension3d getEnvelop3d() {
		GeomPoint3d ptIns3d = new GeomPoint3d(this.ptIns);
		
		GeomDimension3d oDim = new GeomDimension3d(ptIns3d, ptIns3d); 
		return oDim;
	}

	@Override
	public GeomDimension2d getEnvelop2d() {
		GeomPoint2d ptIns2d = new GeomPoint2d(this.ptIns);
		
		GeomDimension2d oDim = new GeomDimension2d(ptIns2d, ptIns2d); 
		return oDim;
	}
	
	@Override
	public String getSearchString() {
		String searchString = super.getSearchString() + "^" +
			"NOMEQUADRO=" + this.nomeQuadro;
		return searchString;
	}

	public String getNomeQuadro() {
		return nomeQuadro;
	}

	public void setNomeQuadro(String nomeQuadro) {
		this.nomeQuadro = nomeQuadro;
	}

	public String getDescricaoQuadro() {
		return descricaoQuadro;
	}

	public void setDescricaoQuadro(String descricaoQuadro) {
		this.descricaoQuadro = descricaoQuadro;
	}

	public Double getTensaoQuadro() {
		return tensaoQuadro;
	}

	public void setTensaoQuadro(Double tensaoQuadro) {
		this.tensaoQuadro = tensaoQuadro;
	}

	public String getSistemaFase() {
		return sistemaFase;
	}

	public void setSistemaFase(String sistemaFase) {
		this.sistemaFase = sistemaFase;
	}

	public Double getPotenciaSemReservaQuadro() {
		return potenciaSemReservaQuadro;
	}

	public void setPotenciaSemReservaQuadro(Double potenciaSemReservaQuadro) {
		this.potenciaSemReservaQuadro = potenciaSemReservaQuadro;
	}

	public Double getPotenciaQuadro() {
		return potenciaQuadro;
	}

	public void setPotenciaQuadro(Double potenciaQuadro) {
		this.potenciaQuadro = potenciaQuadro;
	}

	public Double getAlimentadorQuadro() {
		return alimentadorQuadro;
	}

	public void setAlimentadorQuadro(Double alimentadorQuadro) {
		this.alimentadorQuadro = alimentadorQuadro;
	}

	public Double getAlimentadorProtecaoQuadro() {
		return alimentadorProtecaoQuadro;
	}

	public void setAlimentadorProtecaoQuadro(Double alimentadorProtecaoQuadro) {
		this.alimentadorProtecaoQuadro = alimentadorProtecaoQuadro;
	}

	public Double getDisjuntorQuadro() {
		return disjuntorQuadro;
	}

	public void setDisjuntorQuadro(Double disjuntorQuadro) {
		this.disjuntorQuadro = disjuntorQuadro;
	}

	public String getFaseQuadro() {
		return faseQuadro;
	}

	public void setFaseQuadro(String faseQuadro) {
		this.faseQuadro = faseQuadro;
	}

	public Double getBitolaMinimaCondutor() {
		return bitolaMinimaCondutor;
	}

	public void setBitolaMinimaCondutor(Double bitolaMinimaCondutor) {
		this.bitolaMinimaCondutor = bitolaMinimaCondutor;
	}

	public Double getTemperaturaAmbiente() {
		return temperaturaAmbiente;
	}

	public void setTemperaturaAmbiente(Double temperaturaAmbiente) {
		this.temperaturaAmbiente = temperaturaAmbiente;
	}

	public Double getFatorReducao() {
		return fatorReducao;
	}

	public void setFatorReducao(Double fatorReducao) {
		this.fatorReducao = fatorReducao;
	}

	public Integer getQtdCargasPaineisQuadro() {
		return qtdCargasPaineisQuadro;
	}

	public void setQtdCargasPaineisQuadro(Integer qtdCargasPaineisQuadro) {
		this.qtdCargasPaineisQuadro = qtdCargasPaineisQuadro;
	}

	public Double getCargasPaineisQuadro() {
		return cargasPaineisQuadro;
	}

	public void setCargasPaineisQuadro(Double cargasPaineisQuadro) {
		this.cargasPaineisQuadro = cargasPaineisQuadro;
	}

	public boolean isMinimized() {
		return this.bMinimized;
	}

	public void setMinimized(boolean bMinimized) {
		this.bMinimized = bMinimized;
	}

	public Integer getQtdCargasIluminacaoQuadro() {
		return qtdCargasIluminacaoQuadro;
	}

	public void setQtdCargasIluminacaoQuadro(Integer qtdCargasIluminacaoQuadro) {
		this.qtdCargasIluminacaoQuadro = qtdCargasIluminacaoQuadro;
	}

	public Double getCargasIluminacaoQuadro() {
		return cargasIluminacaoQuadro;
	}

	public void setCargasIluminacaoQuadro(Double cargasIluminacaoQuadro) {
		this.cargasIluminacaoQuadro = cargasIluminacaoQuadro;
	}

	public Integer getQtdCargasTomadaQuadro() {
		return qtdCargasTomadaQuadro;
	}

	public void setQtdCargasTomadaQuadro(Integer qtdCargasTomadaQuadro) {
		this.qtdCargasTomadaQuadro = qtdCargasTomadaQuadro;
	}

	public Double getCargasTomadaQuadro() {
		return cargasTomadaQuadro;
	}

	public void setCargasTomadaQuadro(Double cargasTomadaQuadro) {
		this.cargasTomadaQuadro = cargasTomadaQuadro;
	}

	public Integer getQtdCargasMotorQuadro() {
		return qtdCargasMotorQuadro;
	}

	public void setQtdCargasMotorQuadro(Integer qtdCargasMotorQuadro) {
		this.qtdCargasMotorQuadro = qtdCargasMotorQuadro;
	}

	public Double getCargasMotorQuadro() {
		return cargasMotorQuadro;
	}

	public void setCargasMotorQuadro(Double cargasMotorQuadro) {
		this.cargasMotorQuadro = cargasMotorQuadro;
	}

	public Integer getQtdCargasOutrosQuadro() {
		return qtdCargasOutrosQuadro;
	}

	public void setQtdCargasOutrosQuadro(Integer qtdCargasOutrosQuadro) {
		this.qtdCargasOutrosQuadro = qtdCargasOutrosQuadro;
	}

	public Double getCargasOutrosQuadro() {
		return cargasOutrosQuadro;
	}

	public void setCargasOutrosQuadro(Double cargasOutrosQuadro) {
		this.cargasOutrosQuadro = cargasOutrosQuadro;
	}

	public Double getDisjuntorMinimoProtecao() {
		return disjuntorMinimoProtecao;
	}

	public void setDisjuntorMinimoProtecao(Double disjuntorMinimoProtecao) {
		this.disjuntorMinimoProtecao = disjuntorMinimoProtecao;
	}

	public GeomPoint3d getPtIns() {
		return ptIns;
	}

	public void setPtIns(GeomPoint3d ptIns) {
		this.ptIns = ptIns;
	}

	public Integer getQtdTotalCargasQuadro() {
		return qtdTotalCargasQuadro;
	}

	public void setQtdTotalCargasQuadro(Integer qtdTotalCargasQuadro) {
		this.qtdTotalCargasQuadro = qtdTotalCargasQuadro;
	}

	public Double getTotalCargasQuadro() {
		return totalCargasQuadro;
	}

	public void setTotalCargasQuadro(Double totalCargasQuadro) {
		this.totalCargasQuadro = totalCargasQuadro;
	}

	public Integer getQtdCargasRaioXQuadro() {
		return qtdCargasRaioXQuadro;
	}

	public void setQtdCargasRaioXQuadro(Integer qtdCargasRaioXQuadro) {
		this.qtdCargasRaioXQuadro = qtdCargasRaioXQuadro;
	}

	public Double getCargasRaioXQuadro() {
		return cargasRaioXQuadro;
	}

	public void setCargasRaioXQuadro(Double cargasRaioXQuadro) {
		this.cargasRaioXQuadro = cargasRaioXQuadro;
	}

	public Integer getQtdCargasAquecimentoQuadro() {
		return qtdCargasAquecimentoQuadro;
	}

	public void setQtdCargasAquecimentoQuadro(Integer qtdCargasAquecimentoQuadro) {
		this.qtdCargasAquecimentoQuadro = qtdCargasAquecimentoQuadro;
	}

	public Double getCargasAquecimentoQuadro() {
		return cargasAquecimentoQuadro;
	}

	public void setCargasAquecimentoQuadro(Double cargasAquecimentoQuadro) {
		this.cargasAquecimentoQuadro = cargasAquecimentoQuadro;
	}

	public Double getCorrenteBarraQuadro() {
		return correnteBarraQuadro;
	}

	public void setCorrenteBarraQuadro(Double correnteBarraQuadro) {
		this.correnteBarraQuadro = correnteBarraQuadro;
	}

	public Double getCorrenteBarraNeutroQuadro() {
		return correnteBarraNeutroQuadro;
	}

	public void setCorrenteBarraNeutroQuadro(Double correnteBarraNeutroQuadro) {
		this.correnteBarraNeutroQuadro = correnteBarraNeutroQuadro;
	}

	public Double getCorrenteBarraProtecaoQuadro() {
		return correnteBarraProtecaoQuadro;
	}

	public void setCorrenteBarraProtecaoQuadro(Double correnteBarraProtecaoQuadro) {
		this.correnteBarraProtecaoQuadro = correnteBarraProtecaoQuadro;
	}
	
}
