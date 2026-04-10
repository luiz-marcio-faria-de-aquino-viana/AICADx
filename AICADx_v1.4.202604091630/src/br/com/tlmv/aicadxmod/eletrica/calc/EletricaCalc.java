/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * EletricaCalc.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 07/01/2026
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacaostring
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

package br.com.tlmv.aicadxmod.eletrica.calc;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.utils.FileUtil;
import br.com.tlmv.aicadxapp.vo.ProjectRepoVO;
import br.com.tlmv.aicadxmod.eletrica.cmp.CmpDistribuicaoFaseVO;
import br.com.tlmv.aicadxmod.eletrica.rpt.RptQuadroDistribuicao;
import br.com.tlmv.aicadxmod.eletrica.vo.CapacidadeCorrenteVO;
import br.com.tlmv.aicadxmod.eletrica.vo.CircuitoFaseVO;
import br.com.tlmv.aicadxmod.eletrica.vo.CondutorProtecaoVO;
import br.com.tlmv.aicadxmod.eletrica.vo.DimensionamentoCircuito;
import br.com.tlmv.aicadxmod.eletrica.vo.DimensionamentoQuadro;
import br.com.tlmv.aicadxmod.eletrica.vo.DistribuicaoFaseVO;
import br.com.tlmv.aicadxmod.eletrica.vo.ElementoEletricoVO;
import br.com.tlmv.aicadxmod.eletrica.vo.FatorCorrecaoPorTemperaturaVO;
import br.com.tlmv.aicadxmod.eletrica.vo.QuadroCircuitoFase;
import br.com.tlmv.aicadxmod.eletrica.vo.QuadroPotenciaFase;
import br.com.tlmv.aicadxmod.eletrica.vo.QuadroPotenciaQuantidade;
import br.com.tlmv.aicadxmod.eletrica.vo.QuadroTipoCarga;

public class EletricaCalc
{
//Public Static
    public static double DEF_TENSAOFASE_PADRAO = 220.0;  	  			// tensao fase-fase do projeto padrao
    public static double DEF_BITOLAMINIMACONDUTOR_PADRAO = 2.5;      	// bitola nominal minima do condutor padrao
    public static double DEF_DISJUNTORMINIMO_PADRAO = 15;		      	// disjuntor de protecao padrao
    public static double DEF_TEMPERATURA_PADRAO = 30.0;   				// temperatura ambiente padrao
    public static double DEF_FATORREDUCAO_PADRAO = 0.9;   				// fator de reducao padrao
    //
    public static double DEF_FATORREDUCAO_MINIMO = 0.5;			   		// fator de reducao minimo
	
//Private Static
	
    // tabela de fatores de correcao de temperatura para condutores de isolamento de PVC 70C (k1)
    private static FatorCorrecaoPorTemperaturaVO[] TTEMP = {
        new FatorCorrecaoPorTemperaturaVO(10.0, 1.20),
        new FatorCorrecaoPorTemperaturaVO(15.0, 1.15),
        new FatorCorrecaoPorTemperaturaVO(20.0, 1.10),
        new FatorCorrecaoPorTemperaturaVO(25.0, 1.05),
        new FatorCorrecaoPorTemperaturaVO(30.0, 1.00),
        new FatorCorrecaoPorTemperaturaVO(35.0, 0.95),
        new FatorCorrecaoPorTemperaturaVO(40.0, 0.85),
        new FatorCorrecaoPorTemperaturaVO(45.0, 0.80),
        new FatorCorrecaoPorTemperaturaVO(50.0, 0.70),
        new FatorCorrecaoPorTemperaturaVO(55.0, 0.60),
        new FatorCorrecaoPorTemperaturaVO(60.0, 0.50) };
    
    // tabela da capacidade de corrente para cabos de cobre isolados com PVC 70C instalados em eletrodutos embutidos
    private static CapacidadeCorrenteVO[] TCC = {
        new CapacidadeCorrenteVO(  1.5,  17.5,  15.5),
        new CapacidadeCorrenteVO(  2.5,  24.0,  21.0), 
        new CapacidadeCorrenteVO(  4.0,  32.0,  28.0),
        new CapacidadeCorrenteVO(  6.0,  41.0,  36.0), 
        new CapacidadeCorrenteVO( 10.0,  57.0,  50.0),
        new CapacidadeCorrenteVO( 16.0,  76.0,  68.0),
        new CapacidadeCorrenteVO( 25.0, 101.0,  89.0), 
        new CapacidadeCorrenteVO( 35.0, 125.0, 111.0),
        new CapacidadeCorrenteVO( 50.0, 151.0, 134.0),
        new CapacidadeCorrenteVO( 70.0, 192.0, 171.0), 
        new CapacidadeCorrenteVO( 95.0, 232.0, 207.0),
        new CapacidadeCorrenteVO(120.0, 269.0, 239.0),
        new CapacidadeCorrenteVO(150.0, 309.0, 272.0), 
        new CapacidadeCorrenteVO(185.0, 353.0, 310.0),
        new CapacidadeCorrenteVO(240.0, 415.0, 364.0),
        new CapacidadeCorrenteVO(300.0, 473.0, 429.0), 
        new CapacidadeCorrenteVO(400.0, 566.0, 502.0), 
        new CapacidadeCorrenteVO(500.0, 651.0, 578.0) };
    
    // tabela de bitola do condutor de protecao
    private static CondutorProtecaoVO[] TPR = {
        new CondutorProtecaoVO( 35.0,  25.0),
        new CondutorProtecaoVO( 50.0,  25.0),
        new CondutorProtecaoVO( 70.0,  35.0),
        new CondutorProtecaoVO( 95.0,  50.0),
        new CondutorProtecaoVO(120.0,  70.0),
        new CondutorProtecaoVO(150.0,  70.0),
        new CondutorProtecaoVO(185.0,  95.0),
        new CondutorProtecaoVO(240.0, 120.0) };

    // tabela de disjuntores de protecao
    private static double[] TDISJ = { 10, 15, 20, 25, 30, 35, 40, 50, 60, 70, 75, 100, 125, 150, 175, 200, 225, 250, 300, 350, 400, 450, 500 };

    private static double BITOLA_MAX_CONDUTOR_PROTECAO = 150.0;

//Private
    private CadDocumentDef doc = null;
    
  	private double tensaoFase = EletricaCalc.DEF_TENSAOFASE_PADRAO;    						// tensao fase-fase do projeto
  	private double bitolaMinimaCondutor = EletricaCalc.DEF_BITOLAMINIMACONDUTOR_PADRAO;		// bitola nominal minima do condutor
  	private double temperatura = EletricaCalc.DEF_TEMPERATURA_PADRAO;   					// temperatura ambiente
  	private double fatorReducao = EletricaCalc.DEF_FATORREDUCAO_PADRAO;  		 			// fator de reducao

  	/* Methodes */    

    private ElementoEletricoVO obtemQuadroDistribuicao(String nomeQuadro, ArrayList<ElementoEletricoVO> lsQuadroDistribuicao)
    {
        for(ElementoEletricoVO o : lsQuadroDistribuicao) {
            if (o.getQdr() == nomeQuadro)
                return o;
        }
        return null;
    }

    private ArrayList<ElementoEletricoVO> obtemRaizQuadroDistribuicao(ArrayList<ElementoEletricoVO> lsQuadroDistribuicao)
    {
        ArrayList<ElementoEletricoVO> lsRoot = new ArrayList<ElementoEletricoVO>();
        for(ElementoEletricoVO o : lsQuadroDistribuicao) {
            ElementoEletricoVO quadro = obtemQuadroDistribuicao(o.getOrg(), lsQuadroDistribuicao);
            if (quadro == null)
                lsRoot.add(o);
        }
        return lsRoot;
    }

    private ArrayList<ElementoEletricoVO> obtemFilhosQuadroDistribuicao(String nomeQuadro, ArrayList<ElementoEletricoVO> lsQuadroDistribuicao)
    {
        ArrayList<ElementoEletricoVO> lsChild = new ArrayList<ElementoEletricoVO>();
        for(ElementoEletricoVO o : lsQuadroDistribuicao) {
            if( nomeQuadro.compareToIgnoreCase(o.getOrg()) == 0 ) {
                lsChild.add(o);

                ArrayList<ElementoEletricoVO> lsChildrenOfChild = obtemFilhosQuadroDistribuicao(o.getQdr(), lsQuadroDistribuicao);
                lsChild.addAll(lsChildrenOfChild);
            }
        }
        return lsChild;
    }

    private ArrayList<ElementoEletricoVO> obtemFilhosQuadroDistribuicaoNivel1(String nomeQuadro, ArrayList<ElementoEletricoVO> lsQuadroDistribuicao)
    {
        ArrayList<ElementoEletricoVO> lsChild = new ArrayList<ElementoEletricoVO>();
        for(ElementoEletricoVO o : lsQuadroDistribuicao)
        {
            if( nomeQuadro.compareToIgnoreCase( o.getOrg() ) == 0 )
                lsChild.add(o);
        }
        return lsChild;
    }

    private DimensionamentoQuadro obtemDimensionamentoQuquadro(String nomeQuadro, ArrayList<DimensionamentoQuadro> lsDimensionamento)
    {
        for(DimensionamentoQuadro o : lsDimensionamento) {
            if( nomeQuadro.compareToIgnoreCase( o.getNomeQuadro() ) == 0 )
                return o;
        }
        return null;
    }

    private ArrayList<ElementoEletricoVO> inverteListaQuadroDistribuicao(ArrayList<ElementoEletricoVO> lsElem)
    {
        ArrayList<ElementoEletricoVO> lsResult = new ArrayList<ElementoEletricoVO>(); ;

        int sz = lsElem.size() - 1;
        for(int i = sz; i >= 0; i--) {
        	ElementoEletricoVO o = lsElem.get(i);
            lsResult.add(o);
        }
        return lsResult;
    }
    
    private ArrayList<ElementoEletricoVO> ajustaQuadroDistribuicao(ArrayList<ElementoEletricoVO> lsQuadroDistribuicao)
    {
        ArrayList<ElementoEletricoVO> lsResult = new ArrayList<ElementoEletricoVO>(); ;

        ArrayList<ElementoEletricoVO> lsRoot = obtemRaizQuadroDistribuicao(lsQuadroDistribuicao);
        int sz = lsRoot.size();
        if (sz > 0) {
            lsResult.addAll(lsRoot);

            for(ElementoEletricoVO root : lsRoot) {
                ArrayList<ElementoEletricoVO> lsChild = obtemFilhosQuadroDistribuicao(root.getQdr(), lsQuadroDistribuicao);
                lsResult.addAll(lsChild);
            }
        }
        
        ArrayList<ElementoEletricoVO> lsNewResult = this.inverteListaQuadroDistribuicao(lsResult);
        return lsNewResult;
    }

    private void ajustaDimensionamentoQuadro(String nomeQuadro, ArrayList<ElementoEletricoVO> lsQuadroDistribuicao, DimensionamentoQuadro dimensionamento, ArrayList<DimensionamentoQuadro> lsDimensionamento)
    {
        ArrayList<ElementoEletricoVO> lsChild = this.obtemFilhosQuadroDistribuicaoNivel1(nomeQuadro, lsQuadroDistribuicao);
        for(ElementoEletricoVO elem : lsChild) {
            DimensionamentoQuadro quadro = obtemDimensionamentoQuquadro(elem.getQdr(), lsDimensionamento);
            if(quadro != null) {
                DimensionamentoCircuito circuito = dimensionamento.getDimensionamentoCircuito(elem.getCir());
                circuito.setDisjuntorProtecao(quadro.getDisjuntorProtecaoQuadro());
                circuito.setBitolaCondutor(quadro.getBitolaAlimentadorQuadro());
            }
        }
    }

    // processaLevantamentoQuadro(): funcao de levantamento das cargas do quadro
    // qdr - nome do quadro
    private QuadroPotenciaFase processaLevantamentoQuadro(ArrayList<ElementoEletricoVO> lsPontoEletrico, String qdr)
    {
        QuadroPotenciaFase quadro = new QuadroPotenciaFase(0.0, AppDefs.FIA_S_3FNT);

        ArrayList<ElementoEletricoVO> lsElemQuadro = new ArrayList<ElementoEletricoVO>();

        for(ElementoEletricoVO o : lsPontoEletrico)
        {
            if (o.getTip() == AppDefs.FIA_S_QUADRO)
            {
                if (o.getQdr() == qdr)
                    quadro.setSistemaFaseQuadro(o.getFas());
                else if (o.getOrg() == qdr)
                {
                    if (o.getDem() > 0.0)
                        quadro.setPotenciaQuadro(quadro.getPotenciaQuadro() + o.getDem());
                    else
                        quadro.setPotenciaQuadro(quadro.getPotenciaQuadro() + o.getPot());
                }
            }
            else if ((o.getTip() == AppDefs.FIA_S_CARGA) || (o.getTip() == AppDefs.FIA_S_ILUMINACAO))
            {
                if (o.getOrg() == qdr)
                    quadro.setPotenciaQuadro(quadro.getPotenciaQuadro() + o.getPot());
            }

            if ((o.getTip() == AppDefs.FIA_S_QUADRO) || (o.getTip() == AppDefs.FIA_S_CARGA) || (o.getTip() == AppDefs.FIA_S_ILUMINACAO))
            {
                if ((o.getQdr() != qdr) && (o.getOrg() == qdr))
                {
                    QuadroCircuitoFase circuito = quadro.getCircuitoFase(o.getCir());
                    if (circuito != null)
                    {
                        QuadroTipoCarga tipoCarga = circuito.getTipoCarga(o.getTip());
                        if (tipoCarga != null)
                        {
                            QuadroPotenciaQuantidade potencia = tipoCarga.getPotenciaQuantidade(o.getPot());
                            if (potencia != null)
                            {
                                potencia.setQuantidadeCarga(potencia.getQuantidadeCarga() + 1);
                            }
                            else
                            {
                                potencia = new QuadroPotenciaQuantidade(o.getPot(), 1);
                                tipoCarga.add(potencia);
                            }
                        }
                        else
                        {
                            tipoCarga = new QuadroTipoCarga(o.getTip());
                            tipoCarga.add(new QuadroPotenciaQuantidade(o.getPot(), 1));
                            circuito.add(tipoCarga);
                        }
                    }
                    else
                    {
                        QuadroPotenciaQuantidade potencia = new QuadroPotenciaQuantidade(o.getPot(), 1);
                        QuadroTipoCarga tipoCarga = new QuadroTipoCarga(o.getTip());
                        tipoCarga.add(potencia);
                        circuito = new QuadroCircuitoFase(o.getCir(), o.getFas());
                        circuito.add(tipoCarga);
                        quadro.add(circuito);
                    }
                }
            }
        }

        return quadro;
    }

    // processaDimensionamentoQuadro(): funcao de dimensionamento do quadro de cargas
    // vfase - tensao fase-fase do projeto
    // btmin - bitola minima do condutor
    // temp - temperatura ambiente
    // fred - fator de reducao por agrupamento
    // lscar - lista de cargas do quadro
    private DimensionamentoQuadro processaDimensionamentoQuadro(String nomeQuadro, double vfase, double btmin, double temp, double fred, QuadroPotenciaFase lscar)
    {
        DimensionamentoQuadro lr = new DimensionamentoQuadro(nomeQuadro);
        ArrayList<DistribuicaoFaseVO> ls = new ArrayList<DistribuicaoFaseVO>();

        double ftmp = getFatorCorrecaoPorTemperatura(temp);

        double potq = lscar.getPotenciaQuadro();
        String fasq = lscar.getSistemaFaseQuadro();
        double vfq = 0.0;
        double corq = 0.0;
        int nfq = -1;
        String fsq = "?";

        double btminq = this.bitolaMinimaCondutor;
        double corminq = 0.0;

        for(QuadroCircuitoFase lc : lscar.getLsCircuitoFase()) {
            String cir1 = lc.getCircuito();
            String fas1 = lc.getSistemaFase();
            double pot1 = lc.getPotenciaCircuito();
            double vf1 = 0.0;
            double cor1 = 0.0;
            int nf1 = -1;

            if( (fas1 == AppDefs.FIA_S_FN) || (fas1 == AppDefs.FIA_S_FNT) ) {
                vf1 = vfase / Math.sqrt(3.0);
                cor1 = (pot1 / vf1) / (ftmp * fred);
                nf1 = 1;
            }
            else if( (fas1 == AppDefs.FIA_S_2F) || (fas1 == AppDefs.FIA_S_2FN) || (fas1 == AppDefs.FIA_S_2FNT) ) {
                vf1 = vfase;
                cor1 = (pot1 / vf1) / (ftmp * fred);
                nf1 = 2;
            }
            else if( (fas1 == AppDefs.FIA_S_3F) || (fas1 == AppDefs.FIA_S_3FN) || (fas1 == AppDefs.FIA_S_3FNT) ) {
                vf1 = vfase;
                cor1 = (pot1 / (vf1 * Math.sqrt(3.0))) / (ftmp * fred);
                nf1 = 3;
            }

            double prt1 = getProtecao(cor1);

            double bit1 = getBitolaCondutor(nf1, cor1, prt1);
            if (bit1 < btmin)
                bit1 = btmin;

            if (bit1 > btminq) {
                corminq = cor1;
                btminq = bit1;
            }

            ls.add(new DistribuicaoFaseVO(nf1, pot1, cir1));
            lr.add(new DimensionamentoCircuito(cir1, pot1, vf1, cor1, bit1, prt1, "?", fas1));
        }

        if ((fasq == AppDefs.FIA_S_FN) || (fasq == AppDefs.FIA_S_FNT))
        {
            vfq = vfase / Math.sqrt(3.0);
            corq = (potq / vfq) / (ftmp * fred);
            nfq = 1;
            fsq = "R";
        }
        else if ((fasq == AppDefs.FIA_S_2F) || (fasq == AppDefs.FIA_S_2FN) || (fasq == AppDefs.FIA_S_2FNT))
        {
            vfq = vfase;
            corq = (potq / vfq) / (ftmp * fred);
            nfq = 2;
            fsq = "RS";
        }
        else if ((fasq == AppDefs.FIA_S_3F) || (fasq == AppDefs.FIA_S_3FN) || (fasq == AppDefs.FIA_S_3FNT))
        {
            vfq = vfase;
            corq = (potq / (vfq * Math.sqrt(3.0))) / (ftmp * fred);
            nfq = 3;
            fsq = "RST";
        }

        ArrayList<CircuitoFaseVO> lcf = this.processaEquilibrioFases(nfq, ls);
        for(CircuitoFaseVO cf : lcf)
        {
            DimensionamentoCircuito ir = lr.getDimensionamentoCircuito(cf.getCircuito());
            ir.setFase(cf.getFase());
        }

        double prtq = getProtecao(corq);

        double bitq = getBitolaCondutor(nfq, corq, prtq);
        if (bitq < btminq)
        {
            bitq = btminq;
            corq = corminq;

            prtq = getProtecao(corq);
        }

        double bitprotq = getBitolaCondutorProtecao(bitq);

        lr.setPotenciaQuadro(potq);
        lr.setTensaoQuadro(vfq);
        lr.setBitolaAlimentadorQuadro(bitq);
        lr.setBitolaProtecaoQuadro(bitprotq);
        lr.setDisjuntorProtecaoQuadro(prtq);
        lr.setFaseQuadro(fsq);

        return lr;
    }

    private ArrayList<ElementoEletricoVO> addQuadroDistribuicao(ArrayList<ElementoEletricoVO> lsQuadroDistribuicao, ElementoEletricoVO quadroDistribuicao)
    {
        int pos = 0;
        for(ElementoEletricoVO o : lsQuadroDistribuicao) {
            if ( o.getQdr().compareToIgnoreCase( quadroDistribuicao.getOrg() ) == 0 ) {
            	lsQuadroDistribuicao.add(pos, quadroDistribuicao);
                return lsQuadroDistribuicao;
            }
            pos++;
        }
        lsQuadroDistribuicao.add(quadroDistribuicao);

        return lsQuadroDistribuicao;
    }

    private void exportaLevantamentoQuadro(String outputFile, String nomeQuadro, DimensionamentoQuadro dm)
    {
        RptQuadroDistribuicao rpt = new RptQuadroDistribuicao();
        rpt.exportaLevantamentoQuadro(outputFile, nomeQuadro, dm);
    }

//Public
    
    public EletricaCalc(CadDocumentDef doc) {
    	this.doc = doc;
    }
    
    /* Methodes */
    
//    private void publicaLevantamentoQuadro(String nomeDisco, String nomeQuadro, DimensionamentoQuadro dm)
//    {
//        try
//        {
//            AppConfig config = AppMain.getAppMain().getAppConfig();
//
//            AppWS ws = AppMain.getAppMain().getAppWS();
//
//            EleIdentificacaoModelo oIdentificacaoModelo = ws.obtemIdentificacaoModeloPeloNomeDisco(nomeDisco);
//            if (oIdentificacaoModelo == null)
//            {
//                oIdentificacaoModelo = ws.atualizaIdentificacaoModelo(
//                    -1,
//                    nomeDisco);
//            }
//
//            if (oIdentificacaoModelo != null)
//            {
//                EleDimensionamentoQuadro oDimensionamentoQuadro = ws.obtemDimensionamentoQuadroPeloNomeQuadro(oIdentificacaoModelo.getIdentificacaoModeloId(), nomeQuadro);
//
//                String possuiDps = AppDefs.DEF_ELE_QDR_POSSUI_DPS;
//                String classeDps = AppDefs.DEF_ELE_QDR_CLASSE_DPS;
//                double correnteNominalDps = AppDefs.DEF_ELE_QDR_CORRENTE_NOMINAL_DPS;
//                String possuiIdrDdr = AppDefs.DEF_ELE_QDR_POSSUI_IDR_DDR;
//                double correnteFugaIdrDdr = AppDefs.DEF_ELE_QDR_CORRENTE_FUGA_IDR_DDR;
//
//                if(oDimensionamentoQuadro != null)
//                {
//                    possuiDps = oDimensionamentoQuadro.getPossuiDps();
//                    classeDps = oDimensionamentoQuadro.getClasseDps();
//                    correnteNominalDps = oDimensionamentoQuadro.getCorrenteNominalDps();
//                    possuiIdrDdr = oDimensionamentoQuadro.getPossuiIdrDdr();
//                    correnteFugaIdrDdr = oDimensionamentoQuadro.getCorrenteFugaIdrDdr();
//                }
//
//                oDimensionamentoQuadro = ws.atualizaDimensionamentoQuadro(
//                    (oDimensionamentoQuadro != null) ? oDimensionamentoQuadro.getDimensionamentoQuadroId() : -1,
//                    oIdentificacaoModelo.getIdentificacaoModeloId(),
//                    nomeQuadro,
//                    dm.getPotenciaQuadro(),
//                    dm.getTensaoQuadro(),
//                    dm.getBitolaAlimentadorQuadro(),
//                    dm.getBitolaProtecaoQuadro(),
//                    dm.getDisjuntorProtecaoQuadro(),
//                    dm.getFaseQuadro(),
//                    dm.getSistemaFase(),
//                    possuiDps,
//                    classeDps,
//                    correnteNominalDps,
//                    possuiIdrDdr,
//                    correnteFugaIdrDdr,
//                    dm.getPotenciaQuadroSemReserva());
//
//                if (oDimensionamentoQuadro != null)
//                {
//                    ArrayList<DimensionamentoCircuito> lsDimensionaCircuito = dm.getLsDimensionamentoCircuito();
//                    for(DimensionamentoCircuito o : lsDimensionaCircuito)
//                    {
//                        EleDimensionamentoCircuito oDimensionamentoCircuito = ws.obtemDimensionamentoCircuitoPeloCircuito(oIdentificacaoModelo.getIdentificacaoModeloId(), oDimensionamentoQuadro.getDimensionamentoQuadroId(), o.getCircuito());
//
//                        int tipoCondutorId = AppDefs.DEF_ELE_TIPO_CONDUTOR_PADRAO;
//                        int tipoLinhaEletricaId = AppDefs.DEF_ELE_TIPO_LINHA_ELETRICA_PADRAO;
//                        String metodoReferencia = AppDefs.DEF_ELE_METODO_REFERENCIA_PADRAO;
//                        int tipoCaboId = AppDefs.DEF_ELE_TIPO_CABO_PADRAO;
//                        int isolacaoCondutorId = AppDefs.DEF_ELE_ISOLACAO_CONDUTOR_PADRAO;
//                        String tipoIsolacaoCondutor = AppDefs.DEF_ELE_TIPO_ISOLACAO_CONDUTOR_PADRAO;
//                        String tipoInstalacao = AppDefs.DEF_ELE_TIPO_INSTALACAO_PADRAO;
//                        String possuiDpsCir = AppDefs.DEF_ELE_POSSUI_DPS;
//                        String classeDpsCir = AppDefs.DEF_ELE_CLASSE_DPS;
//                        double correnteNominalDpsCir = AppDefs.DEF_ELE_CORRENTE_NOMINAL_DPS;
//                        String possuiIdrDdrCir = AppDefs.DEF_ELE_POSSUI_IDR_DDR;
//                        double correnteFugaIdrDdrCir = AppDefs.DEF_ELE_CORRENTE_FUGA_IDR_DDR;
//                        String grupoIdrDdrCir = AppDefs.DEF_ELE_GRUPO_IDR_DDR;
//
//                        ws.atualizaDimensionamentoCircuito(
//                            (oDimensionamentoCircuito != null) ? oDimensionamentoCircuito.getDimensionamentoCircuitoId() : -1,
//                            oIdentificacaoModelo.getIdentificacaoModeloId(),
//                            oDimensionamentoQuadro.getDimensionamentoQuadroId(),
//                            o.getCircuito(),
//                            o.getPotencia(),
//                            o.getTensao(),
//                            o.getBitolaCondutor(),
//                            o.getDisjuntorProtecao(),
//                            o.getFase(),
//                            o.getSistemaFase(),
//                            tipoCondutorId,
//                            tipoLinhaEletricaId,
//                            metodoReferencia,
//                            tipoCaboId,
//                            isolacaoCondutorId,
//                            tipoInstalacao,
//                            possuiDpsCir,
//                            classeDpsCir,
//                            correnteNominalDpsCir,
//                            possuiIdrDdrCir,
//                            correnteFugaIdrDdrCir,
//                            grupoIdrDdrCir,
//                            "N");
//                    }
//                }
//            }
//        }
//        catch (Exception e)
//        {
//            Console.WriteLine(e.Message);
//        }
//    }
    
    /* Utility Functions */
    
    // getFatorCorrecaoPorTemperatura(): funcao que calcula o fator de correcao por temperatura
    // t - temperatura ambiente
    public double getFatorCorrecaoPorTemperatura(double t)
    {
        for(FatorCorrecaoPorTemperaturaVO o : EletricaCalc.TTEMP) {
            if (t > o.getTemperatura())
                return o.getFatorCorrecao();
        }

        int pos = EletricaCalc.TTEMP.length - 1;        
        FatorCorrecaoPorTemperaturaVO ultFatorCorrecaoPorTemperatura = EletricaCalc.TTEMP[pos];
        return ultFatorCorrecaoPorTemperatura.getFatorCorrecao();
    }

    // getBitolaCondutor(): funcao que calcula a bitola do condutor aplicando a tabela capacidade de corrente
    // nf - numero de condutores carregados (2 ou 3)
    // cc - corrente do circuito
    // dj - disjuntor de protecao
    public double getBitolaCondutor(int nf, double cc, double dj)
    {
        for(CapacidadeCorrenteVO o : EletricaCalc.TCC) {
            if( ((nf <= 2) && (cc < o.getCapacidadeCondutor2())) ||
                ((nf == 3) && (cc < o.getCapacidadeCondutor3())) )
                return o.getBitolaCondutor();
        }

        int pos = EletricaCalc.TCC.length - 1;
        CapacidadeCorrenteVO ultCapacidadeCorrente = EletricaCalc.TCC[pos];
        return ultCapacidadeCorrente.getBitolaCondutor();
    }

    // getProtecao(): funcao que calcula o disjuntor de protecao
    // cc - corrente do circuito
    public double getProtecao(double cc)
    {
    	int sz = EletricaCalc.TDISJ.length;
        for(int i = 0; i < sz; i++) {
            if (cc < EletricaCalc.TDISJ[i])
                return EletricaCalc.TDISJ[i];
        }
        return EletricaCalc.TDISJ[sz - 1];
    }

    // getBitolaCondutorProtecao(): funcao que calcula a bitola do condutor de protecao
    // btq - bitola do conduto fase
    public double getBitolaCondutorProtecao(double btq)
    {
        if(btq <= 25.0) return btq;

        for(CondutorProtecaoVO o : EletricaCalc.TPR) {
            if (btq <= o.getBitolaCondutorFase())
                return o.getBitolaCondutorProtecao();
        }
        return BITOLA_MAX_CONDUTOR_PROTECAO;
    }

    // processaEquilibrioFases(): funcao que retorna uma lista de distribuicao das fases pelos circuitos
    // nf - numero maximo de condutores carregados admissivel
    // ls - lista de circuitos que serao equilibrados
    public ArrayList<CircuitoFaseVO> processaEquilibrioFases(int nf, ArrayList<DistribuicaoFaseVO> ls)
    {
        double potr = 0.0;
        double pots = 0.0;
        double pott = 0.0;

        ArrayList<CircuitoFaseVO> lr = new ArrayList<CircuitoFaseVO>();

        CmpDistribuicaoFaseVO c = new CmpDistribuicaoFaseVO(true);
        ls.sort(c);

        int sz = ls.size();
        for(int i = sz - 1; i >= 0; i--)
        {
            DistribuicaoFaseVO o = ls.get(i);

            int nf1 = o.getNumeroFases();
            double pot1 = o.getPotenciaCircuito();
            String cir1 = o.getIdentificacaoCircuito();

            String fas1 = "?";

            if (nf1 > nf)
                fas1 = "* ERROR *";
            else if (nf1 == 3)
                fas1 = "RST";
            else if (nf1 == 2)
            {
                if (nf1 == nf)
                    fas1 = "RS";
                else
                {
                    if ((potr > pots) && (potr > pott))
                    {
                        fas1 = "ST";
                        pots = pots + pot1;
                        pott = pott + pot1;
                    }
                    else if ((pots > potr) && (pots > pott))
                    {
                        fas1 = "TR";
                        potr = potr + pot1;
                        pott = pott + pot1;
                    }
                    else
                    {
                        fas1 = "RS";
                        potr = potr + pot1;
                        pots = pots + pot1;
                    }
                }
            }
            else if (nf1 == 1)
            {
                if (nf1 == nf)
                    fas1 = "R";
                else
                {
                    if ((nf == 1) ||
                         ((nf == 2) && (potr <= pots)) ||
                         ((potr <= pots) && (potr <= pott)))
                    {
                        fas1 = "R";
                        potr = potr + pot1;
                    }
                    else if ((nf == 2) ||
                         ((pots <= potr) && (pots <= pott)))
                    {
                        fas1 = "S";
                        pots = pots + pot1;
                    }
                    else
                    {
                        fas1 = "T";
                        pott = pott + pot1;
                    }
                }
            }

            CircuitoFaseVO newObj = new CircuitoFaseVO(cir1, fas1);
            lr.add(newObj);
        }
        return lr;
    }

    public void execute(
	  	double tensaoFase,
	  	double bitolaMinimaCondutor,
	  	double temperatura,
	  	double fatorReducao )
    {
        try {
            this.tensaoFase = tensaoFase;
            this.bitolaMinimaCondutor = bitolaMinimaCondutor;
            this.temperatura = temperatura;
            this.fatorReducao = fatorReducao;

            CadBlockDef blkDef = this.doc.getBlkDef();

            ArrayList<ElementoEletricoVO> lsQuadroDistribuicaoBase = new ArrayList<ElementoEletricoVO>();
            
            ArrayList<ElementoEletricoVO> lsPontoEletrico = null;		//blkDef.findAllEntityByObjType(AppDefs.OBJTYPE_MODELINSEREPONTO);
            for(ElementoEletricoVO o : lsPontoEletrico)
            {
                if (o.getTip() == AppDefs.FIA_S_QUADRO)
                    addQuadroDistribuicao(lsQuadroDistribuicaoBase, o);
            }
            ArrayList<ElementoEletricoVO> lsQuadroDistribuicao = ajustaQuadroDistribuicao(lsQuadroDistribuicaoBase);

            ArrayList<DimensionamentoQuadro> lsDimensionamento = new ArrayList<DimensionamentoQuadro>();
            for(ElementoEletricoVO quadroDistribuicao : lsQuadroDistribuicao)
            {
                QuadroPotenciaFase quadro = processaLevantamentoQuadro(lsPontoEletrico, quadroDistribuicao.getQdr());
                DimensionamentoQuadro dimensionamento = processaDimensionamentoQuadro(
                	quadroDistribuicao.getQdr(), 
                	this.tensaoFase, 
                	this.bitolaMinimaCondutor,
                	this.temperatura,
                	this.fatorReducao,
                	quadro);

                ajustaDimensionamentoQuadro(quadroDistribuicao.getQdr(), lsQuadroDistribuicao, dimensionamento, lsDimensionamento);

                lsDimensionamento.add(dimensionamento);

                ProjectRepoVO projectRepo = (ProjectRepoVO)this.doc.getProjectRepo();
                
                String pathDir = projectRepo.getOutputDir();

                String fullFileName = FileUtil.generateFileName(pathDir) + AppDefs.EXT_XLS;

                exportaLevantamentoQuadro(fullFileName, quadroDistribuicao.getQdr(), dimensionamento);

                //publicaLevantamentoQuadro(fullFileName, quadroDistribuicao.getQdr(), dimensionamento);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}
