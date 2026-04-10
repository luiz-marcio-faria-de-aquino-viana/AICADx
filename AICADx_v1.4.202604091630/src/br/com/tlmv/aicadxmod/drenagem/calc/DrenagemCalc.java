/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * DrenagemCalc.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 08/04/2025
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

package br.com.tlmv.aicadxmod.drenagem.calc;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.CadLevel;
import br.com.tlmv.aicadxapp.cad.CadPipe;
import br.com.tlmv.aicadxapp.cad.CadProjectDef;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.tables.LayerTable;
import br.com.tlmv.aicadxapp.cad.tables.LevelTable;
import br.com.tlmv.aicadxapp.cad.utils.CadUtil;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.UuidUtil;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;
import br.com.tlmv.aicadxmod.drenagem.cad.CadCaixaInspecaoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cad.CadMemoriaCalculoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cad.CadMemoriaCalculoItemDrenagemOData;
import br.com.tlmv.aicadxmod.drenagem.cad.CadPerfilDrenagem;
import br.com.tlmv.aicadxmod.drenagem.cad.CadPerfilItemDrenagemOData;
import br.com.tlmv.aicadxmod.drenagem.cad.CadPontoDrenagem;
import br.com.tlmv.aicadxmod.drenagem.vo.CoeficienteChuvasIDFVO;
import br.com.tlmv.aicadxmod.drenagem.vo.FatorDrenagemVO;
import br.com.tlmv.aicadxmod.drenagem.vo.TubulacaoDrenagemVO;

public class DrenagemCalc 
{
//Public
	
    // INITIAL_PARAMS
    //
	public static double COTA_TERRENO_INICIAL      = 0.0;  			// cota inicial do terreno (=0.0 m)
	public static double DESNIVEL_MINIMO_POR_CAIXA = 0.03;    		// desnivel minimo por caixa (=3 cm)
	public static int MAX_NUMERO_COLETOR_PREDIAL   = 10;			// quantidade maxima de coletores prediais (=10)

    // COEF_MANNING
    //
	public static double COEFMANNING_SECAO_CIRCULAR   = 0.013;
	public static double COEFMANNING_SECAO_RETANGULAR = 0.015;
    
    // DECLIVIDADE
    //
	public static double DECLIVIDADE_MIN   = -0.005;
	//
	public static double DECLIVIDADE_0_5PC = -0.005;
	public static double DECLIVIDADE_1_0PC = -0.010;
	public static double DECLIVIDADE_1_5PC = -0.015;
	public static double DECLIVIDADE_2_0PC = -0.020;
	public static double DECLIVIDADE_2_5PC = -0.025;
	public static double DECLIVIDADE_3_0PC = -0.030;
	public static double DECLIVIDADE_3_6PC = -0.035;
	public static double DECLIVIDADE_4_0PC = -0.040;
	public static double DECLIVIDADE_4_5PC = -0.045;
	public static double DECLIVIDADE_5_0PC = -0.050;
	public static double DECLIVIDADE_5_5PC = -0.055;
	public static double DECLIVIDADE_6_0PC = -0.060;
	public static double DECLIVIDADE_6_5PC = -0.065;
	//
	public static double DECLIVIDADE_MAX   = -0.070;
	
	// TIPO_SECAO_ID
	//
	public static final int DEF_TIPOSECAO_CIRCULAR_VAL				= 5001;
	public static final int DEF_TIPOSECAO_RETANGULAR_VAL			= 5002;
	
    /* DESCRICAO_TIPO_SECAO
    */
    public static String DEF_TIPOSECAO_CIRCULAR_STR 				= "SECAO_CIRCULAR"; 
    public static String DEF_TIPOSECAO_RETANGULAR_STR 				= "SECAO_RETANGULAR"; 
    	
    // TIPOSECAO_TUBULACAO
    //
    public static ItemDataVO TB_TIPOSECAO_CIRCLE = new ItemDataVO(DEF_TIPOSECAO_CIRCULAR_VAL, DrenagemCalc.DEF_TIPOSECAO_CIRCULAR_STR);
    public static ItemDataVO TB_TIPOSECAO_RECTANGLE = new ItemDataVO(DEF_TIPOSECAO_RETANGULAR_VAL, DrenagemCalc.DEF_TIPOSECAO_RETANGULAR_STR);

    // ARR_TIPOSECAO_TUBULACAO
    //
    public static ItemDataVO[] ARR_TIPOSECAO_TUBULACAO = {
		TB_TIPOSECAO_CIRCLE,
		TB_TIPOSECAO_RECTANGLE };
        	
    // COEFIMPER DEFINITIONS
    //
    public static int COEFIMPER_SEQID = 1101;
    //
	public static ItemDataVO COEFIMPER_BAIXO = new ItemDataVO(COEFIMPER_SEQID++, "Baixo (Area Rural; Vegetacao Nativa)", 0.4);
	public static ItemDataVO COEFIMPER_MEDIO_BAIXO = new ItemDataVO(COEFIMPER_SEQID++, "Medio Baixo", 0.5);
	public static ItemDataVO COEFIMPER_MEDIO = new ItemDataVO(COEFIMPER_SEQID++, "Medio", 0.6);
	public static ItemDataVO COEFIMPER_MEDIO_ALTO = new ItemDataVO(COEFIMPER_SEQID++, "Medio Alto", 0.7);
	public static ItemDataVO COEFIMPER_ALTO = new ItemDataVO(COEFIMPER_SEQID++, "Alto (Area Urbana)", 0.8);

    // ARR_TIPOSECAO_TUBULACAO
    //
    public static ItemDataVO[] ARR_COEFIMPER = {
		DrenagemCalc.COEFIMPER_BAIXO,
		DrenagemCalc.COEFIMPER_MEDIO_BAIXO,
		DrenagemCalc.COEFIMPER_MEDIO,
		DrenagemCalc.COEFIMPER_MEDIO_ALTO,
		DrenagemCalc.COEFIMPER_ALTO };

    // CATEGORIA_TUBULACAO
    //
    public static int CAT_SEQID = 1101;
    //
    public static ItemDataVO CAT_TUBULACAO_CONCRETOCLASSEPA1 = new ItemDataVO(CAT_SEQID++, "Concreto Classe PA-1", DrenagemCalc.DEF_TIPOSECAO_CIRCULAR_STR);
    public static ItemDataVO CAT_TUBULACAO_CONCRETOCLASSEPA2 = new ItemDataVO(CAT_SEQID++, "Concreto Classe PA-2", DrenagemCalc.DEF_TIPOSECAO_CIRCULAR_STR);
    public static ItemDataVO CAT_TUBULACAO_CONCRETOCLASSEPA3 = new ItemDataVO(CAT_SEQID++, "Concreto Classe PA-3", DrenagemCalc.DEF_TIPOSECAO_CIRCULAR_STR);
    public static ItemDataVO CAT_TUBULACAO_PEAD = new ItemDataVO(CAT_SEQID++, "PEAD", DrenagemCalc.DEF_TIPOSECAO_CIRCULAR_STR);
    public static ItemDataVO CAT_TUBULACAO_CONCRETOARMADO = new ItemDataVO(CAT_SEQID++, "Concreto Armado", DrenagemCalc.DEF_TIPOSECAO_RETANGULAR_STR);

    // ARR_CATEGORIA_TUBULACAO
    //
    public static ItemDataVO[] ARR_CATEGORIA_TUBULACAO = {
    	DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA1,
    	DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA2,
    	DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA3,
    	DrenagemCalc.CAT_TUBULACAO_PEAD,
    	DrenagemCalc.CAT_TUBULACAO_CONCRETOARMADO };
    
    // DIAMETRO_TUBULACAO
    //
    public static int DIAM_SEQID = 1201;
	// CONCRETO_CLASSE_PA-1
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA1_400MM  = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA1.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA1.getDescricao(), "400mm",  0.40, 0.40, 0.45, 0.05, 1.00, 0.60, 0.78, 0.38);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA1_500MM  = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA1.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA1.getDescricao(), "500mm",  0.50, 0.50, 0.55, 0.05, 1.15, 0.65, 0.90, 0.40);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA1_600MM  = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA1.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA1.getDescricao(), "600mm",  0.60, 0.60, 0.65, 0.05, 1.30, 0.65, 1.02, 0.42);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA1_700MM  = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA1.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA1.getDescricao(), "700mm",  0.70, 0.70, 0.75, 0.05, 1.45, 0.75, 1.14, 0.44);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA1_800MM  = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA1.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA1.getDescricao(), "800mm",  0.80, 0.80, 0.85, 0.05, 1.60, 0.80, 1.26, 0.46);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA1_900MM  = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA1.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA1.getDescricao(), "900mm",  0.90, 0.90, 0.95, 0.05, 1.75, 0.85, 1.38, 0.48);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA1_1000MM = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA1.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA1.getDescricao(), "1000mm", 1.00, 1.00, 1.05, 0.05, 1.90, 0.90, 1.50, 0.50);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA1_1200MM = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA1.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA1.getDescricao(), "1200mm", 1.20, 1.20, 1.25, 0.05, 2.20, 1.00, 1.74, 0.54);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA1_1500MM = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA1.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA1.getDescricao(), "1500mm", 1.50, 1.50, 1.55, 0.05, 2.65, 1.15, 2.10, 0.60);
	// CONCRETO_CLASSE_PA-2
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA2_300MM  = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA2.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA2.getDescricao(), "300mm",  0.30, 0.30, 0.35, 0.05, 0.71, 0.71, 0.41, 0.41);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA2_400MM  = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA2.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA2.getDescricao(), "400mm",  0.40, 0.40, 0.45, 0.05, 1.00, 0.60, 0.78, 0.38);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA2_500MM  = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA2.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA2.getDescricao(), "500mm",  0.50, 0.50, 0.55, 0.05, 1.15, 0.65, 0.90, 0.40);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA2_600MM  = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA2.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA2.getDescricao(), "600mm",  0.60, 0.60, 0.65, 0.05, 1.30, 0.65, 1.02, 0.42);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA2_700MM  = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA2.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA2.getDescricao(), "700mm",  0.70, 0.70, 0.75, 0.05, 1.45, 0.75, 1.14, 0.44);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA2_800MM  = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA2.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA2.getDescricao(), "800mm",  0.80, 0.80, 0.85, 0.05, 1.60, 0.80, 1.26, 0.46);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA2_900MM  = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA2.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA2.getDescricao(), "900mm",  0.90, 0.90, 0.95, 0.05, 1.75, 0.85, 1.38, 0.48);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA2_1000MM = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA2.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA2.getDescricao(), "1000mm", 1.00, 1.00, 1.05, 0.05, 1.90, 0.90, 1.50, 0.50);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA2_1200MM = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA2.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA2.getDescricao(), "1200mm", 1.20, 1.20, 1.25, 0.05, 2.20, 1.00, 1.74, 0.54);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA2_1500MM = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA2.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA2.getDescricao(), "1500mm", 1.50, 1.50, 1.55, 0.05, 2.65, 1.15, 2.10, 0.60);
	// CONCRETO_CLASSE_PA-3
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA3_300MM  = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA3.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA3.getDescricao(), "300mm",  0.30, 0.30, 0.35, 0.05, 0.71, 0.41, 0.71, 0.41);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA3_400MM  = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA3.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA3.getDescricao(), "400mm",  0.40, 0.40, 0.45, 0.05, 0.81, 0.41, 0.81, 0.41);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA3_500MM  = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA3.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA3.getDescricao(), "500mm",  0.50, 0.50, 0.55, 0.05, 0.92, 0.42, 0.92, 0.42);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA3_600MM  = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA3.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA3.getDescricao(), "600mm",  0.60, 0.60, 0.65, 0.05, 1.04, 1.04, 1.04, 0.44);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA3_700MM  = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA3.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA3.getDescricao(), "700mm",  0.70, 0.70, 0.75, 0.05, 1.16, 0.46, 1.16, 0.46);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA3_800MM  = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA3.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA3.getDescricao(), "800mm",  0.80, 0.80, 0.85, 0.05, 1.28, 0.48, 1.28, 0.48);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA3_900MM  = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA3.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA3.getDescricao(), "900mm",  0.90, 0.90, 0.95, 0.05, 1.40, 0.50, 1.40, 0.50);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA3_1000MM = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA3.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA3.getDescricao(), "1000mm", 1.00, 1.00, 1.05, 0.05, 1.52, 0.52, 1.52, 0.52);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA3_1200MM = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA3.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA3.getDescricao(), "1200mm", 1.20, 1.20, 1.25, 0.05, 1.76, 0.56, 1.76, 0.56);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA3_1500MM = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA3.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA3.getDescricao(), "1500mm", 1.50, 1.50, 1.55, 0.05, 2.12, 0.62, 2.12, 0.62);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA3_1800MM = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA3.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA3.getDescricao(), "1500mm", 1.80, 1.80, 1.85, 0.05, 2.48, 0.68, 2.48, 0.68);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_CONCRETOCLASSEPA3_2000MM = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA3.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA3.getDescricao(), "1500mm", 2.00, 2.00, 2.05, 0.05, 2.72, 0.72, 2.72, 0.72);
	// PEAD
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_PEAD_450MM  = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_PEAD.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_PEAD.getDescricao(), "450mm (18 pol)",  0.45, 0.460, 0.545, 0.085, 0.810, 0.270, 0.810, 0.270);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_PEAD_600MM  = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_PEAD.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_PEAD.getDescricao(), "600mm (24 pol)",  0.60, 0.614, 0.717, 0.103, 0.970, 0.250, 0.970, 0.250);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_PEAD_750MM  = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_PEAD.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_PEAD.getDescricao(), "750mm (30 pol)",  0.75, 0.774, 0.900, 0.126, 1.140, 0.240, 1.140, 0.240);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_PEAD_900MM  = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_PEAD.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_PEAD.getDescricao(), "900mm (36 pol)",  0.90, 0.914, 1.044, 0.130, 1.280, 0.240, 1.280, 0.240);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_PEAD_1050MM = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_PEAD.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_PEAD.getDescricao(), "1050mm (42 pol)", 1.05, 1.050, 1.212, 0.162, 1.440, 0.230, 1.440, 0.230);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_PEAD_1200MM = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_PEAD.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_PEAD.getDescricao(), "1200mm (48 pol)", 1.20, 1.204, 1.367, 0.163, 1.590, 0.220, 1.590, 0.220);
    public static TubulacaoDrenagemVO DIAM_TUBULACAO_PEAD_1500MM = new TubulacaoDrenagemVO(DrenagemCalc.DIAM_SEQID++, DrenagemCalc.CAT_TUBULACAO_PEAD.getItemDataIdVal(), DrenagemCalc.CAT_TUBULACAO_PEAD.getDescricao(), "1500mm (60 pol)", 1.50, 1.500, 1.684, 0.184, 2.150, 0.650, 2.150, 0.650);

    // ARR_DIAMETRO_TUBULACAO
    //
    public static TubulacaoDrenagemVO[] ARR_DIAMETRO_TUBULACAO = {
		// CONCRETO_CLASSE_PA-1
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_400MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_500MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_600MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_700MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_800MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_900MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_1000MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_1200MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_1500MM,
		// CONCRETO_CLASSE_PA-2
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA2_300MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA2_400MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA2_500MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA2_600MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA2_700MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA2_800MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA2_900MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA2_1000MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA2_1200MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA2_1500MM,
		// CONCRETO_CLASSE_PA-3
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA3_300MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA3_400MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA3_500MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA3_600MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA3_700MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA3_800MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA3_900MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA3_1000MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA3_1200MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA3_1500MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA3_1800MM,
		DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA3_2000MM,
		// PEAD
		DrenagemCalc.DIAM_TUBULACAO_PEAD_450MM,
		DrenagemCalc.DIAM_TUBULACAO_PEAD_600MM,
		DrenagemCalc.DIAM_TUBULACAO_PEAD_750MM,
		DrenagemCalc.DIAM_TUBULACAO_PEAD_900MM,
		DrenagemCalc.DIAM_TUBULACAO_PEAD_1050MM,
		DrenagemCalc.DIAM_TUBULACAO_PEAD_1200MM,
		DrenagemCalc.DIAM_TUBULACAO_PEAD_1500MM };
    
	//IFC - LOCAIS DE MEDICAO (VALUE)
    //
	public static int IDFLOCAL_SANTACRUZ_VAL 			= 1001;
	public static int IDFLOCAL_CAMPOGRANDE_VAL 			= 1002;
	public static int IDFLOCAL_MEDANHA_VAL 				= 1003;
	public static int IDFLOCAL_BANGU_VAL 				= 1004;
	public static int IDFLOCAL_JARDIMBOTANICO_VAL 		= 1005;
	public static int IDFLOCAL_CAPELAMAYRINK_VAL 		= 1006;
	public static int IDFLOCAL_VIA11_VAL 				= 1007;
	public static int IDFLOCAL_SABOIALIMA_VAL 			= 1008;
	public static int IDFLOCAL_BENFICA_VAL 				= 1009;
	public static int IDFLOCAL_REALENGO_VAL 			= 1010;
	public static int IDFLOCAL_IRAJA_VAL 				= 1011;
	public static int IDFLOCAL_ELETROBRASTAQUARA_VAL 	= 1012;
	//
	public static int IDFLOCAL_ARARUAMA_VAL 			= 1021;
	public static int IDFLOCAL_JACAREPAGUA_VAL 			= 1022;

	//IFC - LOCAIS DE MEDICAO (TEXT)
	//
	public static String IDFLOCAL_SANTACRUZ_STR 		= "Santa Cruz";
	public static String IDFLOCAL_CAMPOGRANDE_STR 		= "Campo Grande";
	public static String IDFLOCAL_MEDANHA_STR 			= "Medanha";
	public static String IDFLOCAL_BANGU_STR 			= "Bangu";
	public static String IDFLOCAL_JARDIMBOTANICO_STR 	= "Jardim Botanico";
	public static String IDFLOCAL_CAPELAMAYRINK_STR 	= "Capela Mayrink";
	public static String IDFLOCAL_VIA11_STR 			= "Via 11";
	public static String IDFLOCAL_SABOIALIMA_STR 		= "Saboia Lima";
	public static String IDFLOCAL_BENFICA_STR 			= "Benfica";
	public static String IDFLOCAL_REALENGO_STR 			= "Realengo";
	public static String IDFLOCAL_IRAJA_STR 			= "Iraja";
	public static String IDFLOCAL_ELETROBRASTAQUARA_STR = "Eletrobras - Taquara";
	//
	public static String IDFLOCAL_ARARUAMA_STR 			= "Araruama";
	public static String IDFLOCAL_JACAREPAGUA_STR 		= "Jacarepagua";
	
	//IFC - COEFICIENTE DE CHUVAS
	//
	public static CoeficienteChuvasIDFVO[] ARR_COEF_CHUVAS_IDF = {
		new CoeficienteChuvasIDFVO(IDFLOCAL_SANTACRUZ_VAL, 			IDFLOCAL_SANTACRUZ_STR			,  711.3, 0.18,  7.00, 0.687), 
		new CoeficienteChuvasIDFVO(IDFLOCAL_CAMPOGRANDE_VAL, 		IDFLOCAL_CAMPOGRANDE_STR		,  891.6, 0.18, 14.00, 0.689), 
		new CoeficienteChuvasIDFVO(IDFLOCAL_MEDANHA_VAL, 			IDFLOCAL_MEDANHA_STR			,  843.7, 0.17, 12.00, 0.698), 
		new CoeficienteChuvasIDFVO(IDFLOCAL_BANGU_VAL, 				IDFLOCAL_BANGU_STR				, 1208.0, 0.17, 14.00, 0.788), 
		new CoeficienteChuvasIDFVO(IDFLOCAL_JARDIMBOTANICO_VAL, 	IDFLOCAL_JARDIMBOTANICO_STR		, 1239.0, 0.15, 20.00, 0.740), 
		new CoeficienteChuvasIDFVO(IDFLOCAL_CAPELAMAYRINK_VAL, 		IDFLOCAL_CAPELAMAYRINK_STR		,  921.3, 0.16, 15.40, 0.673), 
		new CoeficienteChuvasIDFVO(IDFLOCAL_VIA11_VAL, 				IDFLOCAL_VIA11_STR				, 1423.0, 0.19, 14.50, 0.796), 
		new CoeficienteChuvasIDFVO(IDFLOCAL_SABOIALIMA_VAL, 		IDFLOCAL_SABOIALIMA_STR			, 1782.0, 0.17, 16.60, 0.841), 
		new CoeficienteChuvasIDFVO(IDFLOCAL_BENFICA_VAL, 			IDFLOCAL_BENFICA_STR			, 7032.0, 0.15, 29.60, 1.141), 
		new CoeficienteChuvasIDFVO(IDFLOCAL_REALENGO_VAL, 			IDFLOCAL_REALENGO_STR			, 1164.0, 0.14,  6.96, 0.769), 
		new CoeficienteChuvasIDFVO(IDFLOCAL_IRAJA_VAL, 				IDFLOCAL_IRAJA_STR				, 5986.0, 0.15, 29.70, 1.050), 
		new CoeficienteChuvasIDFVO(IDFLOCAL_ELETROBRASTAQUARA_VAL, 	IDFLOCAL_ELETROBRASTAQUARA_STR	, 1660.0, 0.15, 14.70, 0.841), 
		//
		new CoeficienteChuvasIDFVO(IDFLOCAL_ARARUAMA_VAL, 			IDFLOCAL_ARARUAMA_STR			, 0.0, 0.0, 0.0, 0.0),
		new CoeficienteChuvasIDFVO(IDFLOCAL_JACAREPAGUA_VAL, 		IDFLOCAL_JACAREPAGUA_STR		, 0.0, 0.0, 0.0, 0.0)
	};
	
    // ARR_COLETOR_DRENAGEM
    //
//    public static VazaoColetorDrenagemVO[] ARR_COLETOR_PREDIAL = {
//        //declividade =0.5%
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_50MM,  DECLIVIDADE_0_5PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_75MM,  DECLIVIDADE_0_5PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_100MM, DECLIVIDADE_0_5PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_150MM, DECLIVIDADE_0_5PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_200MM, DECLIVIDADE_0_5PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_250MM, DECLIVIDADE_0_5PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_300MM, DECLIVIDADE_0_5PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_350MM, DECLIVIDADE_0_5PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_400MM, DECLIVIDADE_0_5PC),
//        //declividade =1.0%
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_50MM,  DECLIVIDADE_1PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_75MM,  DECLIVIDADE_1PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_100MM, DECLIVIDADE_1PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_150MM, DECLIVIDADE_1PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_200MM, DECLIVIDADE_1PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_250MM, DECLIVIDADE_1PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_300MM, DECLIVIDADE_1PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_350MM, DECLIVIDADE_1PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_400MM, DECLIVIDADE_1PC),
//        //declividade =2.0%
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_50MM,  DECLIVIDADE_2PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_75MM,  DECLIVIDADE_2PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_100MM, DECLIVIDADE_2PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_150MM, DECLIVIDADE_2PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_200MM, DECLIVIDADE_2PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_250MM, DECLIVIDADE_2PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_300MM, DECLIVIDADE_2PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_350MM, DECLIVIDADE_2PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_400MM, DECLIVIDADE_2PC),
//        //declividade =3.0%
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_50MM,  DECLIVIDADE_3PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_75MM,  DECLIVIDADE_3PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_100MM, DECLIVIDADE_3PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_150MM, DECLIVIDADE_3PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_200MM, DECLIVIDADE_3PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_250MM, DECLIVIDADE_3PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_300MM, DECLIVIDADE_3PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_350MM, DECLIVIDADE_3PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_400MM, DECLIVIDADE_3PC),
//        //declividade =4.0%
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_50MM,  DECLIVIDADE_4PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_75MM,  DECLIVIDADE_4PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_100MM, DECLIVIDADE_4PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_150MM, DECLIVIDADE_4PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_200MM, DECLIVIDADE_4PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_250MM, DECLIVIDADE_4PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_300MM, DECLIVIDADE_4PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_350MM, DECLIVIDADE_4PC),
//        new VazaoColetorDrenagemVO(DIAM_TUBULACAO_400MM, DECLIVIDADE_4PC),
//    };

    // FATOR_DRENAGEM
    //
    public static final FatorDrenagemVO[] ARR_TBLCOL_FATOR_DRENAGEM = {
    	//new FatorDrenagemVO(1001, 0.000000, 0.00,   0.000,   0.00),
    	new FatorDrenagemVO(1002, 0.000047, 0.01,   0.001,   0.01),
		new FatorDrenagemVO(1003, 0.000209, 0.02,   0.004,   0.01),
		new FatorDrenagemVO(1004, 0.000501, 0.03,   0.007,   0.02),
		new FatorDrenagemVO(1005, 0.000929, 0.04,   0.011,   0.03),
		new FatorDrenagemVO(1006, 0.001497, 0.05,   0.015,   0.03),
		new FatorDrenagemVO(1007, 0.002208, 0.06,   0.019,   0.04),
		new FatorDrenagemVO(1008, 0.003064, 0.07,   0.024,   0.05),
		new FatorDrenagemVO(1009, 0.004065, 0.08,   0.029,   0.05),
		new FatorDrenagemVO(1010, 0.005213, 0.09,   0.035,   0.06),
		new FatorDrenagemVO(1011, 0.006507, 0.10,   0.041,   0.06),
		new FatorDrenagemVO(1012, 0.007947, 0.11,   0.047,   0.07),
		new FatorDrenagemVO(1013, 0.009533, 0.12,   0.053,   0.08),
		new FatorDrenagemVO(1014, 0.011263, 0.13,   0.060,   0.08),
		new FatorDrenagemVO(1015, 0.013136, 0.14,   0.067,   0.09),
		new FatorDrenagemVO(1016, 0.015151, 0.15,   0.074,   0.09),
		new FatorDrenagemVO(1017, 0.017306, 0.16,   0.081,   0.10),
		new FatorDrenagemVO(1018, 0.019600, 0.17,   0.089,   0.10),
		new FatorDrenagemVO(1019, 0.022031, 0.18,   0.096,   0.11),
		new FatorDrenagemVO(1020, 0.024596, 0.19,   0.104,   0.12),
		new FatorDrenagemVO(1021, 0.027295, 0.20,   0.112,   0.12),
		new FatorDrenagemVO(1022, 0.030123, 0.21,   0.120,   0.13),
		new FatorDrenagemVO(1023, 0.033080, 0.22,   0.128,   0.13),
		new FatorDrenagemVO(1024, 0.036163, 0.23,   0.136,   0.14),
		new FatorDrenagemVO(1025, 0.039369, 0.24,   0.145,   0.14),
		new FatorDrenagemVO(1026, 0.042695, 0.25,   0.154,   0.15),
		new FatorDrenagemVO(1027, 0.046139, 0.26,   0.162,   0.15),
		new FatorDrenagemVO(1028, 0.049699, 0.27,   0.171,   0.16),
		new FatorDrenagemVO(1029, 0.053370, 0.28,   0.180,   0.16),
		new FatorDrenagemVO(1030, 0.057151, 0.29,   0.189,   0.17),
		new FatorDrenagemVO(1031, 0.061038, 0.30,   0.198,   0.17),
		new FatorDrenagemVO(1032, 0.065028, 0.31,   0.207,   0.18),
		new FatorDrenagemVO(1033, 0.069118, 0.32,   0.217,   0.18),
		new FatorDrenagemVO(1034, 0.073304, 0.33,   0.226,   0.18),
		new FatorDrenagemVO(1035, 0.077584, 0.34,   0.235,   0.19),
		new FatorDrenagemVO(1036, 0.081955, 0.35,   0.245,   0.19),
		new FatorDrenagemVO(1037, 0.086411, 0.36,   0.255,   0.20),
		new FatorDrenagemVO(1038, 0.090951, 0.37,   0.264,   0.20),
		new FatorDrenagemVO(1039, 0.095571, 0.38,   0.274,   0.21),
		new FatorDrenagemVO(1040, 0.100266, 0.39,   0.284,   0.21),
		new FatorDrenagemVO(1041, 0.105034, 0.40,   0.293,   0.21),
		new FatorDrenagemVO(1042, 0.109871, 0.41,   0.303,   0.22),
		new FatorDrenagemVO(1043, 0.114772, 0.42,   0.313,   0.22),
		new FatorDrenagemVO(1044, 0.119734, 0.43,   0.323,   0.23),
		new FatorDrenagemVO(1045, 0.124754, 0.44,   0.333,   0.23),
		new FatorDrenagemVO(1046, 0.129826, 0.45,   0.343,   0.23),
		new FatorDrenagemVO(1047, 0.134948, 0.46,   0.353,   0.24),
		new FatorDrenagemVO(1048, 0.140114, 0.47,   0.363,   0.24),
		new FatorDrenagemVO(1049, 0.145322, 0.48,   0.373,   0.24),
		new FatorDrenagemVO(1050, 0.150566, 0.49,   0.383,   0.25),
		new FatorDrenagemVO(1051, 0.155843, 0.50,   0.393,   0.25),
		new FatorDrenagemVO(1052, 0.161147, 0.51,   0.403,   0.25),
		new FatorDrenagemVO(1053, 0.166476, 0.52,   0.413,   0.26),
		new FatorDrenagemVO(1054, 0.171823, 0.53,   0.423,   0.26),
		new FatorDrenagemVO(1055, 0.177185, 0.54,   0.433,   0.26),
		new FatorDrenagemVO(1056, 0.182558, 0.55,   0.443,   0.26),
		new FatorDrenagemVO(1057, 0.187935, 0.56,   0.453,   0.27),
		new FatorDrenagemVO(1058, 0.193313, 0.57,   0.462,   0.27),
		new FatorDrenagemVO(1059, 0.198687, 0.58,   0.472,   0.27),
		new FatorDrenagemVO(1060, 0.204052, 0.59,   0.482,   0.28),
		new FatorDrenagemVO(1061, 0.209403, 0.60,   0.492,   0.28),
		new FatorDrenagemVO(1062, 0.214734, 0.61,   0.502,   0.28),
		new FatorDrenagemVO(1063, 0.220041, 0.62,   0.512,   0.28),
		new FatorDrenagemVO(1064, 0.225318, 0.63,   0.521,   0.28),
		new FatorDrenagemVO(1065, 0.230560, 0.64,   0.531,   0.29),
		new FatorDrenagemVO(1066, 0.235762, 0.65,   0.540,   0.29),
		new FatorDrenagemVO(1067, 0.240916, 0.66,   0.550,   0.29),
		new FatorDrenagemVO(1068, 0.246019, 0.67,   0.559,   0.29),
		new FatorDrenagemVO(1069, 0.251064, 0.68,   0.569,   0.29),
		new FatorDrenagemVO(1070, 0.256045, 0.69,   0.578,   0.29),
		new FatorDrenagemVO(1071, 0.260955, 0.70,   0.587,   0.30),
		new FatorDrenagemVO(1072, 0.265788, 0.71,   0.596,   0.30),
		new FatorDrenagemVO(1073, 0.270538, 0.72,   0.605,   0.30),
		new FatorDrenagemVO(1074, 0.275198, 0.73,   0.614,   0.30),
		new FatorDrenagemVO(1075, 0.279761, 0.74,   0.623,   0.30),
		new FatorDrenagemVO(1076, 0.284219, 0.75,   0.632,   0.30),
		new FatorDrenagemVO(1077, 0.288565, 0.76,   0.640,   0.30),
		new FatorDrenagemVO(1078, 0.292791, 0.77,   0.649,   0.30),
		new FatorDrenagemVO(1079, 0.296888, 0.78,   0.657,   0.30),
		new FatorDrenagemVO(1080, 0.300848, 0.79,   0.666,   0.30),
		new FatorDrenagemVO(1081, 0.304662, 0.80,   0.674,   0.30),
		new FatorDrenagemVO(1082, 0.308320, 0.81,   0.681,   0.30),
		new FatorDrenagemVO(1083, 0.311812, 0.82,   0.689,   0.30),
		new FatorDrenagemVO(1084, 0.315126, 0.83,   0.697,   0.30),
		new FatorDrenagemVO(1085, 0.318251, 0.84,   0.704,   0.30),
		new FatorDrenagemVO(1086, 0.321173, 0.85,   0.712,   0.30),
		new FatorDrenagemVO(1087, 0.323879, 0.85, 999.000, 999.00),
		new FatorDrenagemVO(1088, 0.326353, 0.86, 999.000, 999.00),
		new FatorDrenagemVO(1089, 0.328577, 0.87, 999.000, 999.00),
		new FatorDrenagemVO(1090, 0.330532, 0.88, 999.000, 999.00),
		new FatorDrenagemVO(1091, 0.332194, 0.89, 999.000, 999.00),
		new FatorDrenagemVO(1092, 0.333535, 0.90, 999.000, 999.00),
		new FatorDrenagemVO(1093, 0.334525, 0.91, 999.000, 999.00)
    };
    	
    // PARAMS CAIXA_INSPECAO
    //
	//ESGOTO - DIAMETRO
	public static final double DEF_ESGOTO_DIAMCI_60CM			= 0.6;
	public static final double DEF_ESGOTO_DIAMCI_100CM			= 1.0;
	//
	public static final double DEF_ESGOTO_PROFCI_100CM			= 1.0;
	//
	public static final double DEF_ESGOTO_DECLIVIDADEMINCI		= 0.005;	//min (m/m)
	public static final double DEF_ESGOTO_DECLIVIDADEMAXCI		= 0.040;	//max (m/m)
	
	//AGUA_PLUVIAL - DIAMETRO
	public static final double DEF_APLUVIAL_DIAMCI_60CM			= 0.6;
	public static final double DEF_APLUVIAL_DIAMCI_100CM		= 1.0;
	//
	public static final double DEF_APLUVIAL_PROFCI_100CM		= 1.0;
	//
	public static final double DEF_APLUVIAL_DECLIVIDADEMINCI	= 0.005;	//min (m/m)
	public static final double DEF_APLUVIAL_DECLIVIDADEMAXCI	= 0.040;	//max (m/m)

	//DRENAGEM - CAIXA_INSPECAO
	//
	//DIAMETROS
	public static final double DEF_DRENAGEM_DIAMCI_60CM				= 0.6;
	public static final double DEF_DRENAGEM_DIAMCI_100CM			= 1.0;
	//
	//PROFUNDIDADE
	public static final double DEF_DRENAGEM_PROFCI_100CM			= 1.0;
	//
	//ESPESSURAS
	public static final double DEF_DRENAGEM_ESPESSURA_CAIXA_15CM	= 0.15;
	public static final double DEF_DRENAGEM_ESPESSURA_TAMPAO_5CM	= 0.05;
	//
	//DECLIVIDADE
	public static final double DEF_DRENAGEM_DECLIVIDADEMINCI		= 0.005;	//min (m/m)
	public static final double DEF_DRENAGEM_DECLIVIDADEMAXCI		= 0.07;		//max (m/m)
	//AFASTAMENTO_TUBULACAO
	public static final double DEF_DRENAGEM_DISTANCIA_TUBULACAO_5CM	= 0.10;

	// RALOS_DRENAGEM
	//
	// RALO_SIMPLES
    public static final double DEF_RALO_SIMPLES_LARGURA 			= 0.6;
    public static final double DEF_RALO_SIMPLES_ALTURA 				= 0.3;
    public static final double DEF_RALO_SIMPLES_PROFUNDIDADE 		= -0.9;    	
	// BOCA_LOBO
    public static final double DEF_BOCA_LOBO_LARGURA 				= 0.6;
    public static final double DEF_BOCA_LOBO_ALTURA 				= 0.3;
    public static final double DEF_BOCA_LOBO_PROFUNDIDADE 			= -0.9;    	
	// RALO_COM_BOCA_LOBO
    public static final double DEF_RALO_COM_BOCA_LOBO_LARGURA 		= 0.6;
    public static final double DEF_RALO_COM_BOCA_LOBO_ALTURA 		= 0.5;
    public static final double DEF_RALO_COM_BOCA_LOBO_PROFUNDIDADE	= -0.9;    	
	
	// MEMORIA_CALCULO
	//
	//DRENAGEM
	public static final String DEF_MEMORIA_CALCULO_DRENAGEM_NOME		= "MEMORIA_CALCULO-%s";
	public static final String DEF_MEMORIA_CALCULO_DRENAGEM_DESCRICAO	= "Memoria de calculo da rede de drenagem criada em %s";
	
    /* METHODES */
    
	// selectFatorDrenagem(): seleciona item da tabela de fator de drenagem
	// Parametros:
	// f - valor do fator de drenagem usado na pesquisa
	public FatorDrenagemVO selectFatorDrenagem(double f) {
		FatorDrenagemVO oF = ARR_TBLCOL_FATOR_DRENAGEM[0];
		int sz = ARR_TBLCOL_FATOR_DRENAGEM.length;
		for(int i = 1; i < sz; i++) {
			oF = ARR_TBLCOL_FATOR_DRENAGEM[i];
			if(f < oF.getF()) {
				return ARR_TBLCOL_FATOR_DRENAGEM[i - 1];
			}
		}
		return oF;
	}
		
	// calcIndicePluviometrico(): calculo do indice pluviometrico
	// NOTA: A intensidade pluviométrica será calculada a partir da aplicação de equações de chuvas intensas (IDF) validas para o municipio do Rio de Janeiro
	// Parametros:
	// a, b, c, e d - valores dos coeficientes em funcao da localidade
	// Tr - periodo de recorrencia (em anos)
	// t - duracao da chuva (em minutos)
	public double calcIndicePluviometrico(
		double a,
		double b,
		double c,
		double d,
		double Tr,
		double t)
	{
		double Nr = (a * Math.pow(Tr, b));
		double D = Math.pow(t + c, d);
		
		double i = Nr / D;
		return i;
	}

	// calcIdfIndicePluviometrico(): calculo IDF do indice pluviometrico
	// NOTA: A intensidade pluviométrica será calculada a partir da aplicação de equações de chuvas intensas (IDF) validas para o municipio do Rio de Janeiro
	//
	// Parametros:
	// local - local da medicao
	// Tr - periodo de recorrencia (em anos)
	// t - duracao da chuva (em minutos)
	public double calcIdfIndicePluviometrico(
		int local,
		double Tr,
		double t)
	{
		int sz = DrenagemCalc.ARR_COEF_CHUVAS_IDF.length;
		
		CoeficienteChuvasIDFVO oCoefChuvasIDF0 = DrenagemCalc.ARR_COEF_CHUVAS_IDF[0];		

		int pos = local - oCoefChuvasIDF0.getOid();
		if( !((pos > 0) && (pos < sz)) ) return 0.0;
		
		CoeficienteChuvasIDFVO oCoefChuvasIDF = DrenagemCalc.ARR_COEF_CHUVAS_IDF[pos];
		
		double a = oCoefChuvasIDF.getA();
		double b = oCoefChuvasIDF.getB();
		double c = oCoefChuvasIDF.getC();
		double d = oCoefChuvasIDF.getD();
				
		double Nr = (a * Math.pow(Tr, b));
		double D = Math.pow(t + c, d);
		
		double i = Nr / D;
		return i;
	}
	
	/* CALCULATE - ODATA */

	public double calcComprimentoHoriz(CadCaixaInspecaoDrenagem oCIAtual, CadCaixaInspecaoDrenagem oCIProxima) {
		double dComprimentoHoriz = 0.0; 				

		if(oCIAtual == null) return dComprimentoHoriz;
		if(oCIProxima == null) return dComprimentoHoriz;

		GeomPoint2d ptAtual2d = new GeomPoint2d( oCIAtual.getPtIns() );
		GeomPoint2d ptProxima2d = new GeomPoint2d( oCIProxima.getPtIns() );

		dComprimentoHoriz = ptAtual2d.distTo(ptProxima2d);
		return dComprimentoHoriz;		
	}

	public double calcComprimentoVert(CadCaixaInspecaoDrenagem oCIAtual, CadCaixaInspecaoDrenagem oCIProxima) {
		double dComprimentoVert = 0.0; 				

		if(oCIAtual == null) return dComprimentoVert;
		if(oCIProxima == null) return dComprimentoVert;

		GeomPoint3d ptAtual = oCIAtual.getPtIns();
		GeomPoint3d ptProxima = oCIProxima.getPtIns();

		dComprimentoVert = ptProxima.getZ() - ptAtual.getZ();
		return dComprimentoVert;		
	}
	
	public double calcComprimento(CadCaixaInspecaoDrenagem oCIAtual, CadCaixaInspecaoDrenagem oCIProxima) {
		double dComprimento = 0.0; 				

		if(oCIAtual == null) return dComprimento;
		if(oCIProxima == null) return dComprimento;

		GeomPoint3d ptAtual = oCIAtual.getPtIns();
		GeomPoint3d ptProxima = oCIProxima.getPtIns();

		dComprimento = ptAtual.distTo(ptProxima);
		return dComprimento;		
	}

	public double calcDeclividadeTerreno(CadCaixaInspecaoDrenagem oCIAtual, CadCaixaInspecaoDrenagem oCIProxima) {
		double dDeclividadeGreide = 0.0;
		if(oCIAtual == null) return dDeclividadeGreide;
		if(oCIProxima == null) return dDeclividadeGreide;
		
		double dComprimento = this.calcComprimento(oCIAtual, oCIProxima);
		
		double dCotaTerrenoAtual = oCIAtual.getCt();
		double dCotaTerrenoProxima = oCIProxima.getCt();

		if(dComprimento >= AppDefs.MATHPREC_MIN)
			dDeclividadeGreide = (dCotaTerrenoProxima - dCotaTerrenoAtual) / dComprimento;
		return dDeclividadeGreide;
	}
	
	public double calcDeclividadeTubulacao(double declividadeTerreno, double declividadeTubulacao) {
		double dDeclividadeTubulacao = declividadeTubulacao;
		
		if(dDeclividadeTubulacao > AppDefs.DEF_DEFAULT_DRENAGEM_DECLIVIDADEMINIMA) {
			// declividade minima, mais negativa que declividade tubulacao.
			dDeclividadeTubulacao = AppDefs.DEF_DEFAULT_DRENAGEM_DECLIVIDADEMINIMA;
		}
		
		if(dDeclividadeTubulacao > declividadeTerreno) {
			// declividade terreno, mais negativa que declividade tubulacao.
			dDeclividadeTubulacao = declividadeTerreno;
		}
		return dDeclividadeTubulacao;
	}

	public double calcProfundidadeProximaInicial(CadCaixaInspecaoDrenagem oCIAnterior, CadCaixaInspecaoDrenagem oCIAtual)
	{
		double dProfundidadeAtual = AppDefs.DEF_DEFAULT_DRENAGEM_PROFUNDIDADEMINIMA;
		return dProfundidadeAtual;
	}
	
//	public double calcProfundidadeProximaInicialOLD(CadCaixaInspecaoDrenagem oCIAnterior, CadCaixaInspecaoDrenagem oCIAtual)
//	{
//		//PROFUNDIDADE
//		//
//		double dProfundidadeAtual = AppDefs.DEF_DEFAULT_DRENAGEM_PROFUNDIDADEMINIMA;
//		if(oCIAnterior == null) 
//			return dProfundidadeAtual;
//
//		//Declividade (m/m)
//		//
//		double dDeclividadeTerrenoAnterior = this.calcDeclividadeTerreno(oCIAnterior, oCIAtual);
//		double dDeclividadeTubulacaoAnterior = this.calcDeclividadeTubulacaoInicial(dDeclividadeTerrenoAnterior);
//		
//		//COTA_TERRENO / COTA_FUNDO
//		//
//		double dCotaTerrenoAnterior = oCIAnterior.getCt();
//		double dCotaFundoAnterior = oCIAnterior.getCb();
//		//
//		double dProfundidadeAnterior = - (dCotaTerrenoAnterior - dCotaFundoAnterior);
//
//		//COTA_TERRENO / COTA_FUNDO
//		//
//		GeomPoint3d ptCIAnterior = new GeomPoint3d( oCIAnterior.getPtIns() );
//		GeomPoint3d ptCIAtual = new GeomPoint3d( oCIAtual.getPtIns() );
//		
//		double dComprimentoAnterior = ptCIAnterior.distTo(ptCIAtual);
//		
//		double dY = dComprimentoAnterior * dDeclividadeTubulacaoAnterior;
//		
//		dProfundidadeAtual = dProfundidadeAnterior - dY;
//		return dProfundidadeAtual;
//	}
	
	public double calcProfundidadeProximaInicial(CadCaixaInspecaoDrenagem oCIAtual, double comprimento, double declividadeTubulacao)
	{
		double dProfundidadeAtual = 0.0;

		if(oCIAtual == null) 
			return dProfundidadeAtual;
		
		CadCaixaInspecaoDrenagem oCIProxima = oCIAtual.getProximo();
		if(oCIProxima != null) {
			double dProfundidadeProxima = oCIProxima.getProfundidade();
			
			double dY = comprimento * declividadeTubulacao;
			dProfundidadeAtual = dProfundidadeProxima + dY;
		}
		return dProfundidadeAtual;
	}
	
	public double calcFundoAtual(double cotaTerrenoAtual, double profundidadeAtual)
	{
		double fundo = cotaTerrenoAtual + profundidadeAtual;
		return fundo;
	}

	public double calcTempoConcentracao(double tempoConcAnterior, double tempoPercursoAnterior) {
		double tempoConc = tempoConcAnterior + tempoPercursoAnterior;
		return tempoConc;
	}
	
	public double calcCoefDistr(double areaTotal) {
		double exp = -0.15;
		double coefDistr = Math.pow(areaTotal, exp);
		return coefDistr;
	}
	
	public double calcCoefDistrFinal(double areaTotal, double coefDistr) {
		double coefDistrFinal = coefDistr;

		if(areaTotal < 1.0)
			coefDistrFinal = 1.0;
		return coefDistrFinal;
	}

	public double calcIndicePluviometrico(int iCodigoLocalMedicao, double periodoRecorrenciaEmAnos, CadMemoriaCalculoItemDrenagemOData oItemAnterior)
	{
		double dResult = 0.0;
		
		double dTempoConcAnterior = AppDefs.DEF_DEFAULT_DRENAGEM_TEMPOCONCENTRACAOINICIAL;
		if(oItemAnterior != null)
			dTempoConcAnterior = oItemAnterior.getTempoConc();
		
		if(iCodigoLocalMedicao == IDFLOCAL_CAMPOGRANDE_VAL) {
			if(dTempoConcAnterior < AppDefs.MATHPREC_MIN) return 0.0;
			dResult = 891.60 * Math.pow(periodoRecorrenciaEmAnos, 0.180) / Math.pow(dTempoConcAnterior + 14.00, 0.689);
		}
		else if(iCodigoLocalMedicao == IDFLOCAL_SANTACRUZ_VAL) {
			if(dTempoConcAnterior < AppDefs.MATHPREC_MIN) return 0.0;			
			dResult = 711.30 * Math.pow(periodoRecorrenciaEmAnos, 0.186) / Math.pow(dTempoConcAnterior +  7.00, 0.687); 
		}
		else if(iCodigoLocalMedicao == IDFLOCAL_MEDANHA_VAL) {
			if(dTempoConcAnterior < AppDefs.MATHPREC_MIN) return 0.0;
			dResult = 844.78 * Math.pow(periodoRecorrenciaEmAnos, 0.177) / Math.pow(dTempoConcAnterior + 12.00, 0.698);
		}
		else if(iCodigoLocalMedicao == IDFLOCAL_JARDIMBOTANICO_VAL) {
			if(dTempoConcAnterior < AppDefs.MATHPREC_MIN) return 0.0;
			dResult = 1239.00 * Math.pow(periodoRecorrenciaEmAnos, 0.150) / Math.pow(dTempoConcAnterior + 20.00, 0.740);
		}
		else if(iCodigoLocalMedicao == IDFLOCAL_VIA11_VAL) {
			if(dTempoConcAnterior < AppDefs.MATHPREC_MIN) return 0.0;
			dResult = 1423.00 * Math.pow(periodoRecorrenciaEmAnos, 0.196) / Math.pow(dTempoConcAnterior + 14.58, 0.796);
		}
		else if(iCodigoLocalMedicao == IDFLOCAL_IRAJA_VAL) {
			if(dTempoConcAnterior < AppDefs.MATHPREC_MIN) return 0.0;
			dResult = 5986.27 * Math.pow(periodoRecorrenciaEmAnos, 0.157) / Math.pow(dTempoConcAnterior + 29.70, 1.050);
		}
		else if(iCodigoLocalMedicao == IDFLOCAL_ARARUAMA_VAL) {
			if(dTempoConcAnterior < AppDefs.MATHPREC_MIN) return 0.0;
			dResult = 5986.27 * Math.pow(periodoRecorrenciaEmAnos, 0.157) / Math.pow(dTempoConcAnterior + 29.70, 1.050);
		}
		else if(iCodigoLocalMedicao == IDFLOCAL_IRAJA_VAL) {
			if(dTempoConcAnterior < AppDefs.MATHPREC_MIN) return 0.0;
			dResult = 709.00 * Math.pow(periodoRecorrenciaEmAnos, 0.104) / Math.pow(dTempoConcAnterior + 8.00, 0.721);
		}
		else if(iCodigoLocalMedicao == IDFLOCAL_JACAREPAGUA_VAL) {
			if(dTempoConcAnterior < AppDefs.MATHPREC_MIN) return 0.0;
			dResult = 71.11 / Math.pow((dTempoConcAnterior / 60.0) + 0.17, 0.721);
		}
		return dResult;
	}
	
	public double calcCoefDefluv(CadMemoriaCalculoItemDrenagemOData oItemAnterior, double coefImper, double indicePluviometrico) {
		double coefDefluv = 0.0;
		
		double dTempoConcAnterior = AppDefs.DEF_DEFAULT_DRENAGEM_TEMPOCONCENTRACAOINICIAL;
		if(oItemAnterior != null)
			dTempoConcAnterior = oItemAnterior.getTempoConc();
		
		double dTempoConc_IndicePluviometrico = dTempoConcAnterior * indicePluviometrico;
		double dExp = 1.0 / 3.0;
		double dTempoConc_IndicePluviometrico_Exp = Math.pow(dTempoConc_IndicePluviometrico, dExp);
		
		if(coefImper == 0.4) {
			coefDefluv = 0.029 * dTempoConc_IndicePluviometrico_Exp;
		}
		else if(coefImper == 0.5) {
			coefDefluv = 0.036 * dTempoConc_IndicePluviometrico_Exp;
		}
		else if(coefImper == 0.6) {
			coefDefluv = 0.043 * dTempoConc_IndicePluviometrico_Exp;
		}
		else if(coefImper == 0.7) {
			coefDefluv = 0.051 * dTempoConc_IndicePluviometrico_Exp;
		}
		else if(coefImper == 0.8) {
			coefDefluv = 0.058 * dTempoConc_IndicePluviometrico_Exp;
		}
		return coefDefluv;
	}

	public double calcDeflLocal(double area, double coefDistrFinal, double indicePluviometrico, double coefDefluv) {
		double deflLocal = area * coefDistrFinal * indicePluviometrico * coefDefluv * 2.78;
		return deflLocal;
	}
	
	public double calcDeflEscoar(CadMemoriaCalculoItemDrenagemOData oItemAnterior, double deflLocal) {
		double deflEscoarAnterior = 0.0;
		if(oItemAnterior != null) {
			deflEscoarAnterior = oItemAnterior.getDeflEscoar();
		}
		double deflEscoar = deflEscoarAnterior + deflLocal;	
		return deflEscoar;
	}
	
	public double calcVelocidade(int tipoSecao, double coefManning, double deflEscoar, CadMemoriaCalculoItemDrenagemOData oItemAtual) {

		double velocidade = 0.0;
		double dDeclividadeAnterior = 0.0;		
		
		if(coefManning < AppDefs.MATHPREC_MIN) return 0.0;		
		
		if(oItemAtual != null) {
			dDeclividadeAnterior = Math.abs( oItemAtual.getDeclividade() );
		}
		
		if(tipoSecao == DrenagemCalc.DEF_TIPOSECAO_RETANGULAR_VAL) {
			velocidade = (0.58 / Math.pow(coefManning, 0.75)) * Math.pow(deflEscoar / 1000.0, 1.0 / 4.0) * Math.pow(dDeclividadeAnterior, 3.0 / 8.0);
		}
		else {
			velocidade = (0.61 / Math.pow(coefManning, 0.75)) * Math.pow(deflEscoar / 1000.0, 1.0 / 4.0) * Math.pow(dDeclividadeAnterior, 3.0 / 8.0);
		}
		return velocidade;
	}
	
	public double calcTempoPercurso(CadCaixaInspecaoDrenagem oCIAtual, CadCaixaInspecaoDrenagem oCIProxima, double velocidade) {
		double dTempoPercurso = 0.0;

		if(oCIAtual == null) return dTempoPercurso;
		if(oCIProxima == null) return dTempoPercurso;

		if(velocidade < AppDefs.MATHPREC_MIN) return dTempoPercurso;		
		
		double dComprimentoAtual = this.calcComprimento(oCIAtual, oCIProxima);
		double dVelocidade = velocidade * 60.0;

		dTempoPercurso = dComprimentoAtual / dVelocidade;
		return dTempoPercurso;
	}
	
	public double calcTempoConc(CadMemoriaCalculoItemDrenagemOData oItemAnterior, double tempoPercursoAtual) {
		double dTempoConcAtual = AppDefs.DEF_DEFAULT_DRENAGEM_TEMPOCONCENTRACAOINICIAL;
		if(oItemAnterior == null) return dTempoConcAtual;

		double dTempoConcAnterior = oItemAnterior.getTempoConc();

		dTempoConcAtual = dTempoConcAnterior + tempoPercursoAtual;
		return dTempoConcAtual;
	}

	public double calcTempoTotalPerc(CadMemoriaCalculoItemDrenagemOData oItemAnterior, double tempoPercAtual) {
		double dTempoPercAnterior = 0.0;
		if(oItemAnterior != null) 
			dTempoPercAnterior = oItemAnterior.getTempoPercurso();
		
		double dTempoTotalPerc = dTempoPercAnterior + tempoPercAtual;	
		return dTempoTotalPerc;
	}
	
	public double calcF(double coefManning, double deflEscoar, double declividade, double dimensoes) {
		double dDeclividadePositiva = Math.abs( declividade );		
		if(dDeclividadePositiva < AppDefs.MATHPREC_MIN) return 0.0;		

		if(dimensoes < AppDefs.MATHPREC_MIN) return 0.0;		

		double fN = coefManning * (deflEscoar / 1000.0);

		double fD1 = Math.sqrt(dDeclividadePositiva);
		double fD2 = Math.pow(dimensoes, 8.0 / 3.0);
		double fD = fD1 * fD2;
		
		double f = fN / fD;
		return f;
	}

	public double calcAlturaAgua(int tipoSecao, double deflEscoar, double velocidade, double dimensoes, double f) {
		double dResult = 0.0;
		
		if(tipoSecao == DrenagemCalc.DEF_TIPOSECAO_RETANGULAR_VAL) {
			if(Math.abs( velocidade ) < AppDefs.MATHPREC_MIN) return 0.0;

			dResult = ((deflEscoar / 1000.0) / velocidade) / dimensoes;
		}
		else {
			FatorDrenagemVO oF = this.selectFatorDrenagem(f);
			dResult = oF.getHD() * dimensoes;
		}
		return dResult;
	}

	public double calcYd(double alturaAgua, double dimensoes) {
		double dYd = alturaAgua / dimensoes * 100.0;	
		return dYd;
	}
	
	public double calcNivelAgua(double fundoAtual, double alturaAguaAtual) {
		double nivelAguaAtual = fundoAtual + alturaAguaAtual;	
		return nivelAguaAtual;
	}
	
	public double calcAreaSecaoMolhada(int tipoSecao, double alturaAguaAtual, double dimensoes) {
		double areaSecao = 0.0;

		if(tipoSecao == DrenagemCalc.DEF_TIPOSECAO_RETANGULAR_VAL) {
			//AREA_MOLHADA = AREA_RETANGULO
			//
			double dS_retangulo = alturaAguaAtual * dimensoes;
			areaSecao = dS_retangulo;
		}
		else {
			double dRaioTubulacao = dimensoes / 2.0;			
	
			double diff = dRaioTubulacao - alturaAguaAtual;
			if(Math.abs( diff ) < AppDefs.MATHPREC_MIN) {
				//AREA_MOLHADA = AREA_SEMI-CIRCULO
				//
				double dS_semicirculo = (Math.PI * dRaioTubulacao * dRaioTubulacao) / 2.0;				
				areaSecao = dS_semicirculo;
			}
			else if(diff > 0.0) {
				//AREA_MOLHADA = AREA_CORDA
				//
				double dL1 = diff;				
				double acos_b = Math.acos( dL1 / dRaioTubulacao );

				double dL2 = dRaioTubulacao * Math.sin(acos_b);

				double dS2 = ((dL2 + dL2) * dL1) / 2.0;

				double dS1 = ((2.0 * acos_b) * dRaioTubulacao * dRaioTubulacao) / 2.0;
				
				double dS_corda = dS1 - dS2;				
				areaSecao = dS_corda;
			}
			else if(diff < 0.0) {
				//AREA_MOLHADA = AREA_CIRCULO - AREA_CORDA
				//
				double dL1 = - diff;				
				double acos_b = Math.acos( dL1 / dRaioTubulacao );

				double dL2 = dRaioTubulacao * Math.sin(acos_b);

				double dS2 = ((dL2 + dL2) * dL1) / 2.0;

				double dS1 = ((2.0 * acos_b) * dRaioTubulacao * dRaioTubulacao) / 2.0;

				double dS_corda = dS1 - dS2;
				
				double dS_circulo = Math.PI * dRaioTubulacao * dRaioTubulacao;				
				areaSecao = dS_circulo - dS_corda;				
			}
		}
		return areaSecao;
	}
	
	public double calcVazao(double areaSecaoMolhada, double velocidade) {
		double dVazaoAtual = 0.0;

		dVazaoAtual = areaSecaoMolhada * velocidade * 1000.0;		// vazao em litros/segundo
		return dVazaoAtual;
	}
	
//	public double calcVazaoAcumulada(CadMemoriaCalculoItemDrenagemOData oItemAnterior, double vazao) {
//		double dVazaoAcumulada = vazao;
//		if(oItemAnterior != null)
//			dVazaoAcumulada += oItemAnterior.getVazao(); 
//		return dVazaoAcumulada;
//	}

	public double calcProfMontJus(CadMemoriaCalculoItemDrenagemOData oItemAnterior, double profundidadeAtual)
	{
		double dProfMontJus = profundidadeAtual;
		return dProfMontJus;
	}

	
	/* REDE DRENAGEM */

	public void buildLista(ArrayList<CadEntity> lsEntity)
  	{
  		for(CadEntity oEnt1 : lsEntity) {
  			CadCaixaInspecaoDrenagem o1 = (CadCaixaInspecaoDrenagem)oEnt1;

  			for(CadEntity oEnt2 : lsEntity) {
  				CadCaixaInspecaoDrenagem o2 = (CadCaixaInspecaoDrenagem)oEnt2;
          	
  				if(o1.getProximaCI() == o2.getNumeroCI()) {
  					o1.setProximo(o2);
  				}
  				else if(o2.getProximaCI() == o1.getNumeroCI()) {
  					o1.addAnterior(o2);
  				}
  			}
  		}
	}
    
    public ArrayList<CadCaixaInspecaoDrenagem> findRoot(CadEntity[] arrEntity)
    {
    	ArrayList<CadCaixaInspecaoDrenagem> result = new ArrayList<CadCaixaInspecaoDrenagem>();
        for(CadEntity oEnt : arrEntity) {
        	CadCaixaInspecaoDrenagem caixaInspecao = (CadCaixaInspecaoDrenagem)oEnt;
        	
            if(caixaInspecao.getLsAnterior().size() == 0)
                result.add(caixaInspecao);
        }
        return result;
    }
	
    public CadCaixaInspecaoDrenagem findItem(CadEntity[] arrEntity, int numeroCI) 
    {
        for(CadEntity oEnt : arrEntity) {
        	CadCaixaInspecaoDrenagem caixaInspecao = (CadCaixaInspecaoDrenagem)oEnt;
        	
            if(caixaInspecao.getNumeroCI() == numeroCI)
                return caixaInspecao;
        }
        return null;
    }
    
    public void doPropagacaoArea(CadCaixaInspecaoDrenagem oCI)
    {
    	double dAreaLocal = oCI.getAreaLocal();
    	
    	CadCaixaInspecaoDrenagem oCIAtual = oCI.getProximo();
    	while(oCIAtual != null) {
    		double dAreaExternaAtual = oCIAtual.getAreaExterna();
    		dAreaExternaAtual = dAreaExternaAtual + dAreaLocal;
    		
    		oCIAtual.setAreaExterna(dAreaExternaAtual);
    		oCIAtual = oCIAtual.getProximo();
    	}    	
    }
    
	public void doUpdateAreas(ArrayList<CadMemoriaCalculoItemDrenagemOData> lsItem, CadDocumentDef doc)
	{
		CadBlockDef blkDef = doc.getCurrBlockDef();
		
		for(CadMemoriaCalculoItemDrenagemOData oItem : lsItem) {
			int iNumeroCI = oItem.getNumeroCI();
			double dAreaLocal = oItem.getAreaLocal();

			CadEntity oEnt = blkDef.getEntity(iNumeroCI);
  			if(oEnt != null) {
				CadCaixaInspecaoDrenagem oCI = (CadCaixaInspecaoDrenagem)oEnt;
				oCI.setAreaLocal(dAreaLocal);				
  			}			
		}
	}
    
	public void doUpdateCaixaInspecao(ArrayList<CadMemoriaCalculoItemDrenagemOData> lsItem, CadDocumentDef doc)
	{
		CadBlockDef blkDef = doc.getCurrBlockDef();
		
		for(CadMemoriaCalculoItemDrenagemOData oItem : lsItem) {
			int iNumeroCI = oItem.getNumeroCI();			
			CadEntity oEnt = blkDef.getEntity(iNumeroCI);
  			if(oEnt != null) {
  				//GET_ITEMDATA
  				//
  				//int cadRefEntityId = oItem.getCadRefEntityId();
  				//int rowid = oItem.getRowid();									// [automatico]
  				//int pos = oItem.getPos();										// 0, 1, 2, 3, 4... 					(array position)
  				//int numeroCI = oItem.getNumeroCI();								// Identificador da Caixa de Inspecao (ou Poco de Visita)
  				//int iCodigoLocalMedicao = oItem.getCodigoLocalMedicao();		// = IDFLOCAL_SANTACRUZ
  				//double coefManning = oItem.getCoefManning();					// = COEFMANNING_SECAO_CIRCULAR
  				String pv = oItem.getPv();										// PV-A2.1
  				int localId = oItem.getLocalId();								// 1001 - RUA DR. MARIO MACHADO
  				String local = oItem.getLocal();								// RUA DR. MARIO MACHADO
  				String estaca = oItem.getEstaca();								// 2 + 1.70 m
  				double cotaTerreno = oItem.getCotaTerreno();					// 2.841 m
  				double fundo = oItem.getFundo();								// Fundo = (CotaTerreno - 1) ou (Fundo - Comprimento * Declividade)
  				//double nivelAgua = oItem.getNivelAgua();						// NivelAgua = Fundo + AlturaAgua
  				double areaExterna = oItem.getAreaExterna();					// Area = 1.0 ha
  				double areaLocal = oItem.getAreaLocal();						// Area = 0.220 ha
  				double areaTotal = oItem.getAreaTotal();						// AreaTotal = AreaTotal[n-1] + Area
  				double areaTotalImp = oItem.getAreaTotalImp();					// AreaTotalImp = 0.0 ha
  				double coefImper = oItem.getCoefImper();						// 0.80
  				//double coefDistr = oItem.getCoefDistr();						// CoefDistr = AreaTotal ^ ( -0.15 )
  				//double coefDistrFinal = oItem.getCoefDistrFinal();				// Se CoefDistr < 1.0 Entao: CoefDistrFinal = 1.0; Senao: CoefDistrFinal = 1.0 / CoefDistr 
  				//double tempoConc = oItem.getTempoConc();						// TempoConcentracao = TempoConcentracao[n-1] + TempoPercurso
  				double declividade = oItem.getDeclividade();					// 0.00160
  				double dimensoesMeter = oItem.getDimensoesMeter();				// 0.60
  				double comprimento = oItem.getComprimento();					// 30 m
  				//String observacao = oItem.getObservacao(); 
  				//boolean bRoot = oItem.isRoot();									// Caixa de Inspecao (ou Poco de Visita) Inicial do Trecho
  				//boolean bFinish = oItem.isFinish();								// Caixa de Inspecao (ou Poco de Visita) Final do Trecho
  				//CadMemoriaCalculoItemDrenagemOData oItemAnterior = oItem.getItemAnterior();
  				
  				/* CALCULATED_FIELDS */
  				
  				//
  				// IndicePluviometrico
  				//
  				// [CAMPO_GRANDE] 		=  891.60 * (CoefManning_SecaoCircular ^ 0.180) / (TempoConcentracao + 14.00) ^ 0.689
  				// [SANTA_CRUZ]   		=  711.30 * (CorfManning_SecaoCircular ^ 0.186) / (TempoConcentracao +  7.00) ^ 0.687
  				// [ARARUAMA]     		=  709.00 * (CoefManning_SecaoCircular ^ 0.104) / (TempoConcentracao +  8.00) ^ 0.721
  				// [mendanha]      		=  844.78 * (CoefManning_SecaoCircular ^ 0.177) / (TempoConcentracao + 12.00) ^ 0.698
  				// [JARDIM_BOTANICO]	= 1239.00 * (CoefManning_SecaoCircular ^ 0.150) / (TempoConcentracao + 20.00) ^ 0.740
  				// [JACAREPAGUA]		=   71.11 / (TempoConcentracao / 60.0 + 0.17) ^ 0.7897
  				// [VIA11]				= 1423.00 * (CoefManning_SecaoCircular ^ 0.196) / (TempoConcentracao + 14.58) ^ 0.796
  				// [IRAJA]				= 5986.27 * (CoefManning_SecaoCircular ^ 0.157) / (TempoConcentracao + 29.70) ^ 1.050
  				//
  				//double indicePluviometrico = oItem.getIndicePluviometrico();	// IndicePluviometrico[CAMPO_GRANDE] = 891.60 * (CoefManning_SecaoCircular ^ 0.180) / (TempoConcentracao + 14.00) ^ 0.689
  				//
  				// CoefDefluv
  				//
  				// SE(CoefImper = 0.4) ENTAO: 0.029 * (TempoConcentracao * indicePluviometrico) ^ (1 / 3)
  				// ELSE: SE(CoefImper = 0.5) ENTAO: 0.036 * (TempoConcentracao * indicePluviometrico) ^ (1 / 3)
  				// ELSE: SE(CoefImper = 0.6) ENTAO: 0.043 * (TempoConcentracao * indicePluviometrico) ^ (1 / 3)
  				// ELSE: SE(CoefImper = 0.7) ENTAO: 0.051 * (TempoConcentracao * indicePluviometrico) ^ (1 / 3)
  				// ELSE: SE(CoefImper = 0.8) ENTAO: 0.058 * (TempoConcentracao * indicePluviometrico) ^ (1 / 3)
  				// ELSE: 0
  				//
  				//double coefDefluv = oItem.getCoefDefluv();
  				//double deflLocal = oItem.getDeflLocal();						// DeflLocal = Area * CoefDistr * IndicePluviometrico * CoefDefluv * 2.78
  				//double deflEscoar = oItem.getDeflEscoar();						// DeflEscoar = DeflLocal
  				//double f = oItem.getF();										// F = (CoefManning * DeflEscoar / 1000.0) / (SQRT(Declividade) * (Dimensoes ^ (8 / 3))
  				//double declividadeGreide = oItem.getDeclividadeGreide();		// DeclividadeGreide = (CotaTerreno[n-1] - CotaTerreno[n]) / Comprimento
  				//
  				// AlturaAgua
  				//
  				// SE(Dimensoes > 0) ENTAO: ((AlturaAgua[n-1] / 1000.0) / Velocidade) / Dimensoes
  				// ELSE: ( ARR_TBLCOL_FATOR_DRENAGEM[F] ou 2,0 ) * Dimensoes
  				//
  				//double alturaAgua = oItem.getAlturaAgua();
  				//double yd = oItem.getYd();										// Y/D = (AlturaAgua / Dimensoes) * 100.0
  				double profMontJus = oItem.getProfMontJus();					// ProfMontJus = CotaTerreno - Fundo
  				//
  				// Velocidade
  				//
  				// SE(Dimensoes > 0) ENTAO: (0.58 / (CoefManning_SecaoRetangular ^ 0.75)) * (DeflEscoar / 1000.0) ^ (1 / 4) * Declividade ^ ( 3 / 8 )
  				// ELSE: (0.61 / (CoefManning_SecaoCircular ^ 0.75)) * (DeflEscoar / 1000.0) ^ (1 / 4) * Declividade ^ (3 / 8))
  				//
  				//double velocidade = oItem.getVelocidade();
  				//double tempoPercurso = oItem.getTempoPercurso();				// TempoPercurso = Comprimento / (Velocidade / 60.0)
  				//double tempoTotal = oItem.getTempoTotal();						// TempoTotal = TempoTotal[n-1] + TempoPercurso
  				//
  				// Vazao / Vazao Acumulada
  				//
  				double vazao = oItem.getVazao();
  				double vazaoAcumulada = oItem.getVazaoAcumulada();  
  				//
  				// Cota Entrada / Cota Saida
  				//	
  				double cotaEntrada = oItem.getCotaEntrada();
  				double cotaSaida = oItem.getCotaSaida();
  				//
  				// Tubulacao
  				//
			    int iCategoriaTubulacaoId = DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_400MM.getCategoriaTubulacaoId();
			    String strDescricaoCategoriaTubulacao = DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_400MM.getDescricaoCategoriaTubulacao();
  				int qtdTubulacao = oItem.getQtdTubulacao();
  				double diametroTubulacaoMeter = oItem.getDiametroTubulacaoMeter();
  				//
  				// Comprimento Tubulacao (Horizontal/Vertical)
  				//
  				double dComprVertTubulacao = Math.abs(comprimento * declividade);
  				double dComprHorizTubulacao = Math.sqrt( (comprimento * comprimento) * (dComprVertTubulacao * dComprVertTubulacao) );

  				//SET_CAIXAINSPECAODRENAGEM
  				//
  				CadCaixaInspecaoDrenagem oCI = (CadCaixaInspecaoDrenagem)oEnt;
  				
  				oCI.setPv(pv);										// PV-A2.1
  				oCI.setLocalId(localId);							// 1001 - RUA DR. MARIO MACHADO
  				oCI.setLocal(local);								// RUA DR. MARIO MACHADO
  				oCI.setEstaca(estaca);								// 2 + 1.70 m
  				oCI.setAreaExterna(areaExterna);					// AreaExterna = SOMA(AreaTotal_Anterior)
  				oCI.setAreaLocal(areaLocal);						// AreaLocal = 0.220 ha
  				oCI.setAreaTotal(areaTotal);						// AreaTotal = AreaExterna + AreaLocal
  				oCI.setAreaTotalImp(areaTotalImp);					// AreaTotalImp = 0.0 ha 		- valor importado para comparacao com valor calculado
  			    oCI.setDiametroMeter(dimensoesMeter);				// Diametro = 0.60
  			    oCI.setVazao(vazao);								// VazaoCalculada
  			    oCI.setVazaoAcumulada(vazaoAcumulada);				// VazaoAcumulada = VazaoAcumuladaAnterior + VazaoCalculada  
  			    oCI.setCategoriaTubulacaoId(iCategoriaTubulacaoId);
  			    oCI.setDescricaoCategoriaTubulacao(strDescricaoCategoriaTubulacao);
  			    oCI.setQtdTubulacao(qtdTubulacao);					// QtdTubulacao = 1, 2, 3, 4...
  			    oCI.setDiametroTubulacaoMeter(diametroTubulacaoMeter);		// DiametroTubulacao = 250 mm (0.25 m)
  			    oCI.setDeclividade(declividade);					// Declividade = 0.00160
  				oCI.setCoefImper(coefImper);						// CoefImper = 0.80
  			    oCI.setProfundidade(profMontJus);					// Profundidade = -0.60 m
  			    oCI.setComprTubulacao(comprimento);					// ComprTubulacao = 50.0 m
  			    oCI.setComprHorizTubulacao(dComprHorizTubulacao);	// ComprHorizTubulacao = 40.0 m
  			    oCI.setComprVertTubulacao(dComprVertTubulacao);		// ComprVertTubulacao = 30.0 m
  			    oCI.setCt(cotaTerreno);								// CotaTerreno = 2.841 m
  			    oCI.setCb(fundo);									// Fundo = (CotaTerreno - 1.0) ou (Fundo - Comprimento * Declividade) = 1.841
  			    oCI.setCotaEntrada(cotaEntrada);					// CotaEntrada = CotaTerreno - (Diametro / 2.0) = 2.716 m 			;; entrada_tubulacao
  			    oCI.setCotaSaida(cotaSaida);						// CotaSaida = Fundo + (Diametro / 2.0) = 1.966 m					;; saida_tubulacao
  			}			
		}
	}
    
  	public void reInitTodasAreas(CadEntity[] arrCI, CadEntity[] arrArea, CadDocumentDef doc) 
  	{
  		//INIT: AREA_EXTERNA / AREA_LOCAL / AREA_TOTAL
  		//
  		for(CadEntity oEnt : arrCI) {
  			CadCaixaInspecaoDrenagem oCI = (CadCaixaInspecaoDrenagem)oEnt;
  			int iNumeroCI = oCI.getNumeroCI();

  			double dAreaLocal = CadUtil.calcAreaTotalFromCadAreaContribuicaoByNumeroCI(iNumeroCI, arrArea);
        	if(dAreaLocal < AppDefs.MATHPREC_MIN)
        		dAreaLocal = oCI.getAreaLocal();

  			oCI.setAreaExterna(0.0);
  			oCI.setAreaLocal(dAreaLocal);
  			oCI.setAreaTotal(0.0);
  		}
  		
  		//PROPAGACAO: AREA_LOCAL
  		//
  		for(CadEntity oEnt : arrCI) {
  			CadCaixaInspecaoDrenagem oCI = (CadCaixaInspecaoDrenagem)oEnt;
        	this.doPropagacaoArea(oCI);
  		}
  		
  		//CALC: AREA_TOTAL
  		//
  		for(CadEntity oEnt : arrCI) {
  			CadCaixaInspecaoDrenagem oCI = (CadCaixaInspecaoDrenagem)oEnt;

  			double dAreaExterna = oCI.getAreaExterna();
        	double dAreaLocal = oCI.getAreaLocal();
        	double dAreaTotal = dAreaExterna + dAreaLocal;
        	
  			oCI.setAreaExterna(dAreaExterna);
  			oCI.setAreaLocal(dAreaLocal);
  			oCI.setAreaTotal(dAreaTotal);
  		}
  	}
    
  	public ArrayList<CadPerfilDrenagem> findAllTrecho(CadEntity[] arrEntity, CadDocumentDef doc) 
  	{
  		ArrayList<CadPerfilDrenagem> lsTrechoDrenagem = new ArrayList<CadPerfilDrenagem>(); 

  		// LAYER_TABLE
  		//
		LayerTable oLayTbl = doc.getLayerTable();

		CadLayerDef oLayer = oLayTbl.getLayerDefByReference(AppDefs.LAYER_RPD_PERFIL_DRENAGEM);
		if(oLayer == null) 
			oLayer = doc.getDefaultLayerDef();

  		// LEVEL_TABLE
  		//
		LevelTable oLevelTbl = doc.getLevelTable();

		CadLevel oLevel = oLevelTbl.getLevel(AppDefs.DEFAULT_LEVELNAME);
		if(oLevel == null) 
			oLevel = doc.getDefaultLevel();
		
		GeomPoint3d ptIns = new GeomPoint3d(0.0, 0.0, 0.0);
  		
  		ArrayList<CadCaixaInspecaoDrenagem> lsRoot = this.findRoot(arrEntity);
  		int nTrecho = 1;
  		
  		CadCaixaInspecaoDrenagem oCIAnterior = null;
  		for(CadCaixaInspecaoDrenagem oCI : lsRoot) {
  			CadCaixaInspecaoDrenagem oCIAtual = oCI;
  			oCIAtual.setRoot(true);
  			
  			String nomeTrecho = String.format("Trecho %s", nTrecho);    			
  			CadPerfilDrenagem oTrecho = CadPerfilDrenagem.create(null, oLayer, oLevel, ptIns, nTrecho, nomeTrecho);
  			
  		    CadPerfilItemDrenagemOData oTrechoItem_1 = new CadPerfilItemDrenagemOData(doc, nTrecho, oCIAtual, null);
  			oTrecho.addTrechoItem(oTrechoItem_1);
  			
  			nTrecho += 1;
  			
  			oCIAnterior = oCIAtual;
  			oCIAtual = oCIAnterior.getProximo();

  			int nCaixaInspecao = 1;  			
  			while(oCIAtual != null)  {
  				oCIAtual.setRoot(false);
  				
  	  		    CadPerfilItemDrenagemOData oTrechoItem_2 = new CadPerfilItemDrenagemOData(doc, nCaixaInspecao, oCIAtual, oTrechoItem_1);
  	  			oTrecho.addTrechoItem(oTrechoItem_2);
  	  			
  	  			oCIAnterior = oCIAtual;
  	  			oCIAtual = oCIAnterior.getProximo();
  	  			
  	  			oTrechoItem_1 = oTrechoItem_2;
  	  			nCaixaInspecao += 1;
  			}
  			lsTrechoDrenagem.add(oTrecho);
  		}  	    
  		return lsTrechoDrenagem;
  	}
    
  	public ArrayList<CadCaixaInspecaoDrenagem> findAllNextCaixaInspecaoDrenagem(CadCaixaInspecaoDrenagem oCIRaiz) 
  	{
  		ArrayList<CadCaixaInspecaoDrenagem> lsCI = new ArrayList<CadCaixaInspecaoDrenagem>(); 

  		CadCaixaInspecaoDrenagem oCIAtual = oCIRaiz;
  		lsCI.add(oCIRaiz);
  		
  		CadCaixaInspecaoDrenagem oCIProximo = oCIAtual.getProximo();
  		while(oCIProximo != null) {
  	  		lsCI.add(oCIProximo);
  			
  			oCIAtual = oCIProximo;
  			oCIProximo = oCIAtual.getProximo();
  		}  	    
  		return lsCI;
  	}
    
  	public CadCaixaInspecaoDrenagem findUltimaCaixaInspecaoDrenagem(CadCaixaInspecaoDrenagem oCIRaiz) 
  	{
  		CadCaixaInspecaoDrenagem oCIAtual = oCIRaiz;
  		CadCaixaInspecaoDrenagem oCIProximo = oCIAtual.getProximo();
  		while(oCIProximo != null) {
  			oCIAtual = oCIProximo;
  			oCIProximo = oCIAtual.getProximo();
  		}  	    
  		return oCIAtual;
  	}
    
  	public CadCaixaInspecaoDrenagem findPenultimaCaixaInspecaoDrenagem(CadCaixaInspecaoDrenagem oCIRaiz) 
  	{
  		CadCaixaInspecaoDrenagem oCIPenultima = oCIRaiz;

  		CadCaixaInspecaoDrenagem oCIAtual = oCIRaiz;
  		CadCaixaInspecaoDrenagem oCIProximo = oCIAtual.getProximo();
  		while(oCIProximo != null) {
  			oCIPenultima = oCIAtual;
  			//
  			oCIAtual = oCIProximo;
  			oCIProximo = oCIAtual.getProximo();
  		}  	    
  		return oCIPenultima;
  	}
    
    /* Calculation */

//    public double calculaVazaoAnterior(ArrayList<CadCaixaInspecaoDrenagem> lsAnterior) 
//    {
//        double result = 0.0;
//        for(CadCaixaInspecaoDrenagem caixaInspecao : lsAnterior) {
//            result += caixaInspecao.getVazaoAcumulada();
//        }
//        return result;
//    }
//
//    public VazaoColetorDrenagemVO findVazaoColetorPredial(double declividade, double vazao) {
//        for (int n = 1; n < DrenagemCalc.MAX_NUMERO_COLETOR_PREDIAL; n++) {
//            for(VazaoColetorDrenagemVO o : DrenagemCalc.ARR_COLETOR_PREDIAL) {
//                if( (declividade <= o.getDeclividade()) && (vazao <= (o.getVazaoMax() * n)) ) {
//                	VazaoColetorDrenagemVO result = new VazaoColetorDrenagemVO(o.getDiametro(), o.getDeclividade(), o.getVazaoMax());
//                    result.setQtdTubulacao(n);
//                    return result;
//                }
//            }
//        }
//        return null;
//    }
	
//	public void conectedCaixaInspecaoDrenagem(CadCaixaInspecaoDrenagem oAtual, CadCaixaInspecaoDrenagem oProximo) {
//    	GeomPoint3d ptAtual3d = new GeomPoint3d(oAtual.getPtIns());
//    	GeomPoint3d ptProximo3d = new GeomPoint3d(oProximo.getPtIns());
//    			
//    	double vazaoAcumulada = this.calculaVazaoAnterior(oAtual.getLsAnterior()) + oAtual.getVazao();
//        oAtual.setVazaoAcumulada(vazaoAcumulada); // Root element
//
//        VazaoColetorDrenagemVO vazaoColetorPredial = this.findVazaoColetorPredial(oAtual.getDeclividade(), oAtual.getVazaoAcumulada());
//        if(vazaoColetorPredial == null) {
//        	String warnmsg = "ERR: Nao foi identificado o coletor predial ideal (declividade max. aceita = 4.0%).";
//            AppError.showMessageBox(null, warnmsg, "", DrenagemCalc.class);
//            return;
//        }
//        
//        oAtual.setQtdTubulacao(vazaoColetorPredial.getQtdTubulacao());
//
//        oAtual.setDiametroTubulacao(vazaoColetorPredial.getDiametro());
//        double pipeDiameterMeter = oAtual.getDiametroTubulacao() / 1000.0;
//        double pipeRadiusMeter = pipeDiameterMeter / 2.0;
//
//        double profundidadeAtual = Math.abs( oAtual.getProfundidade() );
//        if(profundidadeAtual > AppDefs.DEF_DEFAULT_DRENAGEM_PROFUNDIDADEMINIMA) {
//        	profundidadeAtual = AppDefs.DEF_DEFAULT_DRENAGEM_PROFUNDIDADEMINIMA;
//        }
//        
//        double declividadeAtual = DrenagemCalc.DECLIVIDADE_1PC;
//        
//        double ctAtual = oAtual.getCt();
//        double cbAtual = ctAtual - profundidadeAtual;
//        double cotaEntradaAtual = cbAtual + DrenagemCalc.DESNIVEL_MINIMO_POR_CAIXA + pipeRadiusMeter;
//        double cotaSaidaAtual = cbAtual + pipeRadiusMeter;
//
//        GeomPoint2d ptAtual2d = new GeomPoint2d(ptAtual3d);
//        GeomPoint2d ptProximo2d = new GeomPoint2d(ptProximo3d);
//        
//        double declVert = declividadeAtual / 100.0;
//        double declHoriz = (1.0 - declVert);
//        
//        double dL = ptAtual2d.distTo(ptProximo2d);
//
//        double d = dL / declHoriz; 
//        double dH = d * declVert;
//
//        double profundidadeProximaCI = Math.abs( oProximo.getProfundidade() );
//        if(profundidadeProximaCI > AppDefs.DEF_DEFAULT_DRENAGEM_PROFUNDIDADEMINIMA) {
//        	profundidadeProximaCI = AppDefs.DEF_DEFAULT_DRENAGEM_PROFUNDIDADEMINIMA;
//        }
//        
//        double declividadeProximaCI = DrenagemCalc.DECLIVIDADE_1PC;
//
//        double ctProximaCI = oProximo.getCt();
//        double cbProximaCI = ctProximaCI - profundidadeProximaCI;
//        double cotaSaidaProximaCI = cbProximaCI + DrenagemCalc.DESNIVEL_MINIMO_POR_CAIXA + pipeRadiusMeter;
//
//        double cotaEntradaProximaCI_orig = cbProximaCI + pipeRadiusMeter;
//        double cotaEntradaProximaCI_calc = cotaSaidaAtual - dH;
//
//        double cotaEntradaProximaCI = Math.min(cotaEntradaProximaCI_orig, cotaEntradaProximaCI_calc);
//        
//        if(cotaEntradaProximaCI < cotaSaidaProximaCI) {
//        	cotaSaidaProximaCI = cotaEntradaProximaCI - DrenagemCalc.DESNIVEL_MINIMO_POR_CAIXA;
//        	cbProximaCI = cotaSaidaProximaCI - pipeRadiusMeter;
//        }
//        
//        dH = Math.abs(cotaEntradaProximaCI - cotaSaidaAtual);
//        declividadeAtual = (dH / d);
//
//    	GeomPoint3d newPtAtual3d = new GeomPoint3d(ptAtual3d.getX(), ptAtual3d.getY(), ctAtual);
//
//    	oAtual.setPtIns(newPtAtual3d);
//    	oAtual.setProfundidade(profundidadeAtual);
//    	oAtual.setDeclividade(declividadeAtual);
//    	oAtual.setComprTubulacao(d);
//    	oAtual.setComprHorizTubulacao(dL);
//    	oAtual.setComprVertTubulacao(dH);
//    	oAtual.setCt(ctAtual);
//        oAtual.setCb(cbAtual);
//        oAtual.setCotaEntrada(cotaEntradaAtual);
//        oAtual.setCotaSaida(cotaSaidaAtual);
//
//    	GeomPoint3d newPtProximo3d = new GeomPoint3d(ptProximo3d.getX(), ptProximo3d.getY(), ctProximaCI);
//    	
//    	oProximo.setPtIns(newPtProximo3d);
//        oProximo.setProfundidade(profundidadeProximaCI);	
//        oProximo.setDeclividade(declividadeProximaCI);
//        oProximo.setComprTubulacao(0.0);
//        oProximo.setComprHorizTubulacao(0.0);
//        oProximo.setComprVertTubulacao(0.0);
//    	oProximo.setCt(ctProximaCI);
//    	oProximo.setCb(cbProximaCI);
//    	oProximo.setCotaEntrada(cotaEntradaProximaCI);
//    	oProximo.setCotaSaida(cotaSaidaProximaCI);
//	}
	
//	public void notConectedCaixaInspecaoDrenagem(CadCaixaInspecaoDrenagem oAtual) {
//    	GeomPoint3d ptAtual3d = new GeomPoint3d(oAtual.getPtIns());
//    			
//    	double vazaoAcumulada = this.calculaVazaoAnterior(oAtual.getLsAnterior()) + oAtual.getVazao();
//        oAtual.setVazaoAcumulada(vazaoAcumulada); // Root element
//
//        VazaoColetorDrenagemVO vazaoColetorPredial = this.findVazaoColetorPredial(oAtual.getDeclividade(), oAtual.getVazaoAcumulada());
//        if(vazaoColetorPredial == null) {
//        	String warnmsg = "ERR: Nao foi identificado o coletor predial ideal (declividade max. aceita = 4.0%).";
//            AppError.showMessageBox(null, warnmsg, "", DrenagemCalc.class);
//            return;
//        }
//        
//        oAtual.setQtdTubulacao(vazaoColetorPredial.getQtdTubulacao());
//
//        oAtual.setDiametroTubulacao(vazaoColetorPredial.getDiametro());
//        double pipeDiameterMeter = oAtual.getDiametroTubulacao() / 1000.0;
//        double pipeRadiusMeter = pipeDiameterMeter / 2.0;
//
//        double profundidadeAtual = Math.abs( oAtual.getProfundidade() );
//        if(profundidadeAtual > AppDefs.DEF_DEFAULT_DRENAGEM_PROFUNDIDADEMINIMA) {
//        	profundidadeAtual = AppDefs.DEF_DEFAULT_DRENAGEM_PROFUNDIDADEMINIMA;
//        }
//        
//        double ctAtual = oAtual.getCt();
//        double cbAtual = ctAtual - profundidadeAtual;
//        double cotaEntradaAtual = cbAtual + DrenagemCalc.DESNIVEL_MINIMO_POR_CAIXA + pipeRadiusMeter;
//        double cotaSaidaAtual = cbAtual + pipeRadiusMeter;
//
//    	GeomPoint3d newPtAtual3d = new GeomPoint3d(ptAtual3d.getX(), ptAtual3d.getY(), ctAtual);
//
//    	oAtual.setPtIns(newPtAtual3d);
//    	oAtual.setProfundidade(profundidadeAtual);
//    	oAtual.setDeclividade(0.0);
//    	oAtual.setComprTubulacao(0.0);
//    	oAtual.setComprHorizTubulacao(0.0);
//    	oAtual.setComprVertTubulacao(0.0);
//    	oAtual.setCt(ctAtual);
//        oAtual.setCb(cbAtual);
//        oAtual.setCotaEntrada(cotaEntradaAtual);
//        oAtual.setCotaSaida(cotaSaidaAtual);
//	}
		
//    public void processaRedeDrenagem(ArrayList<CadEntity> lsEntity) {
//        ArrayList<CadCaixaInspecaoDrenagem> root = this.findRoot(lsEntity);
//        for(CadCaixaInspecaoDrenagem oAtual : root) {
//        	CadCaixaInspecaoDrenagem oProximo = oAtual.getProximo();
//            if(oProximo != null) {
//            	conectedCaixaInspecaoDrenagem(oAtual, oProximo);
//                processaItemRedeDrenagem(oProximo);
//            }
//            else {
//            	notConectedCaixaInspecaoDrenagem(oAtual);
//            }
//        }
//    }
	
//    public void processaRedeDrenagemFromNode(ArrayList<CadEntity> lsEntity, CadEntity oRoot) {
//        CadCaixaInspecaoDrenagem oAtual = (CadCaixaInspecaoDrenagem)oRoot;
//
//        CadCaixaInspecaoDrenagem oProximo = oAtual.getProximo();
//        if(oProximo != null) {
//        	conectedCaixaInspecaoDrenagem(oAtual, oProximo);
//            processaItemRedeDrenagem(oProximo);
//        }
//        else {
//        	notConectedCaixaInspecaoDrenagem(oAtual);
//        }
//    }
    
//    public void processaItemRedeDrenagem(CadCaixaInspecaoDrenagem oAtual)
//    {
//    	CadCaixaInspecaoDrenagem oProximo = oAtual.getProximo();
//        if(oProximo != null) {
//        	conectedCaixaInspecaoDrenagem(oAtual, oProximo);
//            processaItemRedeDrenagem(oProximo);
//        }
//        else {
//        	notConectedCaixaInspecaoDrenagem(oAtual);
//        }
//    }
    
	public void doRemoveRedeDrenagem(CadDocumentDef doc) {
		CadBlockDef blkDef = doc.getCurrBlockDef();

		CadEntity[] arrEntity = blkDef.findAllEntityByObjType(AppDefs.OBJTYPE_BIMPIPE);
		for(CadEntity oEnt_1 : arrEntity) {
			CadLayerDef oLayer = oEnt_1.getLayer();
			if( AppDefs.LAYER_RPD_TB_DRENAGEM.equals(oLayer.getReference()) ) {
				oEnt_1.setDeleted(true);
			}
		}
	}
    	    
//	public void createRedeDrenagem(CadDocumentDef doc, ArrayList<CadEntity> lsEntity) {
//		LayerTable layTbl = doc.getLayerTable();
//		
//		CadLayerDef oLayer = layTbl.getLayerDefByReference(AppDefs.LAYER_RPD_TB_DRENAGEM);
//		if(oLayer == null)
//			oLayer = doc.getDefaultLayerDef();
//		
//		CadBlockDef blkDef = doc.getCurrBlockDef();
//		
//		for(CadEntity oEnt_1 : lsEntity) {
//			CadCaixaInspecaoDrenagem oCI_1 = (CadCaixaInspecaoDrenagem)oEnt_1;
//			int fromNumeroCI = oCI_1.getNumeroCI();
//
//			GeomPoint3d pt3d_1 = new GeomPoint3d(oCI_1.getPtIns());
//
//			double pipeDiameterMeter = oCI_1.getDiametroTubulacao() / 1000.0;
//			double pipeRadiusMeter = pipeDiameterMeter / 2.0;
//			
//			double x3d_1 = pt3d_1.getX();
//			double y3d_1 = pt3d_1.getY();
//			double z3d_1 = oCI_1.getCotaSaida();
//			
//			int objectId_2 = oCI_1.getProximaCI();
//			if(objectId_2 != AppDefs.NULL_INT) {
//				CadEntity oEnt_2 = blkDef.getEntity(objectId_2);
//
//				CadCaixaInspecaoDrenagem oCI_2 = (CadCaixaInspecaoDrenagem)oEnt_2;
//				int toNumeroCI = oCI_2.getNumeroCI();
//
//				GeomPoint3d pt3d_2 = new GeomPoint3d(oCI_2.getPtIns());
//
//				double x3d_2 = pt3d_2.getX();
//				double y3d_2 = pt3d_2.getY();
//				double z3d_2 = oCI_2.getCotaEntrada();
//				
//				GeomPoint3d ptPipe3d_1 = new GeomPoint3d(x3d_1, y3d_1, z3d_1);
//				GeomPoint3d ptPipe3d_2 = new GeomPoint3d(x3d_2, y3d_2, z3d_2);
//				
//				CadPipe oPipe = CadPipe.create(oLayer, ptPipe3d_1, ptPipe3d_2, fromNumeroCI, toNumeroCI, pipeDiameterMeter);
//				blkDef.addEntity(oPipe);
//			}
//		}
//	}
    
	public void doCreateRedeDrenagem(CadMemoriaCalculoDrenagem oMemoriaCalculo, CadDocumentDef doc) {

		// LAYER_TABLE
  		//
		LayerTable oLayTbl = doc.getLayerTable();

		CadLayerDef oLayer = oLayTbl.getLayerDefByReference(AppDefs.LAYER_RPD_TB_DRENAGEM);
		if(oLayer == null) 
			oLayer = doc.getDefaultLayerDef();

  		// LEVEL_TABLE
  		//
		LevelTable oLevelTbl = doc.getLevelTable();

		CadLevel oLevel = oLevelTbl.getLevel(AppDefs.DEFAULT_LEVELNAME);
		if(oLevel == null) 
			oLevel = doc.getDefaultLevel();
		
		CadBlockDef blkDef = doc.getCurrBlockDef();
		
		ArrayList<CadMemoriaCalculoItemDrenagemOData> lsItem = oMemoriaCalculo.getLsItem();		
		for(CadMemoriaCalculoItemDrenagemOData oItem : lsItem) {
			int fromNumeroCI_1 = oItem.getNumeroCI();
			CadCaixaInspecaoDrenagem oCI_1 = (CadCaixaInspecaoDrenagem)blkDef.getEntity(fromNumeroCI_1);
			if(oCI_1 != null) {
				double cotaSaida_1 = oCI_1.getCotaSaida();
				double pipeDiameterMili = oCI_1.getDiametroTubulacaoMeter() * 1000.0;	//diameter in milimeters
				double pipeThicknessMili = pipeDiameterMili * 0.05;						//thickness (5% diameter) in milimeters
								
				GeomPoint3d ptPipe3d_1 = new GeomPoint3d(oCI_1.getPtIns());
				ptPipe3d_1.setZ(cotaSaida_1);
				
				CadCaixaInspecaoDrenagem oCI_2 = oCI_1.getProximo();
				if(oCI_2 != null) {
					int toNumeroCI_2 = oCI_2.getNumeroCI();
					double cotaEntrada_2 = oCI_2.getCotaEntrada();
			
					GeomPoint3d ptPipe3d_2 = new GeomPoint3d(oCI_2.getPtIns());
					ptPipe3d_2.setZ(cotaEntrada_2);
					
					CadPipe oPipe = CadPipe.create(blkDef, oLayer, oLevel, ptPipe3d_1, ptPipe3d_2, fromNumeroCI_1, toNumeroCI_2, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA1.getDescricao(), pipeDiameterMili, pipeThicknessMili);
					blkDef.addEntity(oPipe);
				}
			}
		}
	}
	
	public void doCreatePipeFromRaloToRedeDrenagem(CadDocumentDef doc) {
		
		// LAYER_TABLE
  		//
		LayerTable oLayTbl = doc.getLayerTable();

		CadLayerDef oLayer = oLayTbl.getLayerDefByReference(AppDefs.LAYER_RPD_TB_DRENAGEM);
		if(oLayer == null) 
			oLayer = doc.getDefaultLayerDef();

  		// LEVEL_TABLE
  		//
		LevelTable oLevelTbl = doc.getLevelTable();

		CadLevel oLevel = oLevelTbl.getLevel(AppDefs.DEFAULT_LEVELNAME);
		if(oLevel == null) 
			oLevel = doc.getDefaultLevel();
				
		CadBlockDef blkDef = doc.getCurrBlockDef();

		CadEntity[] arrItem = blkDef.findAllEntityByObjType(AppDefs.OBJTYPE_MODDRPONTODRENAGEM);		
		for(CadEntity oItem : arrItem) {
			CadPontoDrenagem oRalo = (CadPontoDrenagem)oItem;
			if(oRalo != null) {
				int fromNumeroCI_1 = oRalo.getObjectId();
				
				double cotaSaida_1 = oRalo.getCotaSaida();
				double pipeDiameterMili = oRalo.getDiametroTubulacaoMeter() * 1000.0;		//diameter in milimeters
				double pipeThicknessMili = pipeDiameterMili * 0.05;							//thickness (5% diameter) in milimeters
								
				GeomPoint3d ptPipe3d_1 = new GeomPoint3d(oRalo.getPtIns());
				ptPipe3d_1.setZ(cotaSaida_1);
				
				CadEntity proxEnt = oRalo.getProxEnt();
				if(proxEnt != null) {
					if(proxEnt.getObjType() == AppDefs.OBJTYPE_MODDRCAIXAINSPECAO) {
						CadCaixaInspecaoDrenagem oCI_2 = (CadCaixaInspecaoDrenagem)proxEnt;

						int toNumeroCI_2 = oCI_2.getNumeroCI();
						double cotaEntrada_2 = oCI_2.getCotaEntrada();
				
						GeomPoint3d ptPipe3d_2 = new GeomPoint3d(oCI_2.getPtIns());
						ptPipe3d_2.setZ(cotaEntrada_2);
						
						CadPipe oPipe = CadPipe.create(blkDef, oLayer, oLevel, ptPipe3d_1, ptPipe3d_2, fromNumeroCI_1, toNumeroCI_2, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA1.getDescricao(), pipeDiameterMili, pipeThicknessMili);
						blkDef.addEntity(oPipe);
					}
					else if(proxEnt.getObjType() == AppDefs.OBJTYPE_MODDRPONTODRENAGEM) {
						CadPontoDrenagem oCI_2 = (CadPontoDrenagem)proxEnt;

						int toNumeroCI_2 = oCI_2.getObjectId();
						double cotaEntrada_2 = oCI_2.getCotaSaida();
				
						GeomPoint3d ptPipe3d_2 = new GeomPoint3d(oCI_2.getPtIns());
						ptPipe3d_2.setZ(cotaEntrada_2);
						
						CadPipe oPipe = CadPipe.create(blkDef, oLayer, oLevel, ptPipe3d_1, ptPipe3d_2, fromNumeroCI_1, toNumeroCI_2, DrenagemCalc.CAT_TUBULACAO_CONCRETOCLASSEPA1.getDescricao(), pipeDiameterMili, pipeThicknessMili);
						blkDef.addEntity(oPipe);
					}
				}
			}
		}
	}
	
	public CadMemoriaCalculoDrenagem createMemoriaCalculoDrenagem(CadDocumentDef doc, GeomPoint3d ptIns) 
	{
		NumberFormat nf3 = FormatUtil.newNumberFormatPtBr(3);
		
		Date dataAtualHora = new Date();
		
		Date dataAtual = new Date(dataAtualHora.getYear(), dataAtualHora.getMonth(), dataAtualHora.getDate());
		
		CadBlockDef blkDef = doc.getCurrBlockDef();
		
		Hashtable map = new Hashtable();

		CadEntity[] arrCI = blkDef.findAllEntityByObjType(AppDefs.OBJTYPE_MODDRCAIXAINSPECAO);
		
		CadEntity[] arrArea = blkDef.findAllEntityByObjType(AppDefs.OBJTYPE_MODDRAREACONTRIBUICAO);
				
		this.reInitTodasAreas(arrCI, arrArea, doc);
		
		ArrayList<CadPerfilDrenagem> lsPerfilDrenagem = this.findAllTrecho(arrCI, doc);
		
		//CADPROJECTDEF
		//
		CadProjectDef oProj = doc.getCurrProjectDef();

		String strNomeProjeto = oProj.getTituloProjeto();
		Date dtDataEmissao = dataAtual;
	    int iCodigoLocalMedicao = oProj.getCodigoPluviografo();
		String strPluviografo = oProj.getPluviografo();					// local medicao volume chuva
		double dCoefManning = oProj.getCoefManning();
		double dPeriodoRecorrencia = oProj.getPeriodoRecorrencia();
				
		// LAYER_TABLE
  		//
		LayerTable oLayTbl = doc.getLayerTable();

		CadLayerDef oLayer = oLayTbl.getLayerDefByReference(AppDefs.LAYER_RPD_PERFIL_DRENAGEM);
		if(oLayer == null) 
			oLayer = doc.getDefaultLayerDef();

  		// LEVEL_TABLE
  		//
		LevelTable oLevelTbl = doc.getLevelTable();

		CadLevel oLevel = oLevelTbl.getLevel(AppDefs.DEFAULT_LEVELNAME);
		if(oLevel == null) 
			oLevel = doc.getDefaultLevel();
		
		//CADMEMORIACALCULO
		//
		String uuid = UuidUtil.generateUUID();

		String strNome = String.format(AppDefs.DEF_DEFAULT_DRENAGEM_NOMEMEMORIACALCULO, uuid);
		String strDescricao = AppDefs.DEF_DEFAULT_DRENAGEM_DESCRICAOMEMORIACALCULO;

		CadMemoriaCalculoDrenagem o = CadMemoriaCalculoDrenagem.create(
			blkDef,
			oLayer,
			oLevel,
			ptIns,
			strNome,
			strDescricao,
			strNomeProjeto,
			dtDataEmissao,
			iCodigoLocalMedicao,
			strPluviografo,
			dCoefManning,
			dPeriodoRecorrencia,
			lsPerfilDrenagem);

		for(CadPerfilDrenagem oPerfil : lsPerfilDrenagem) {
			CadMemoriaCalculoItemDrenagemOData oItemAnterior = null;

			int sz = oPerfil.getSzLsTrechoItem();
			for(int i = 0; i < sz; i++) {
				CadPerfilItemDrenagemOData oPerfilItem = oPerfil.getTrechoItemAt(i);
				if(oPerfilItem != null) {
					CadCaixaInspecaoDrenagem oEnt1 = oPerfilItem.getCIAtual();

					int iNumeroCI = oEnt1.getNumeroCI();
					String strNumeroCI = Integer.toString(iNumeroCI);					

				    if( map.containsKey(strNumeroCI) ) break;

					boolean bRoot = (i == 0) ? true : false;

					int iProximaCI = oEnt1.getProximaCI();					
					boolean bFinish = (iProximaCI == -1) ? true : false;

					String strTipoCI = oEnt1.getTipoCI();				// _ESGOTO_ / _APLUVIAL_
				    String strSubtipoCI = oEnt1.getSubtipoCI();			// _PRIMARIO_ / _SECUNDARIO_ / _GORDURA_ / _SABAO_ 					
				    
				    int cadRefEntityId = o.getObjectId();
				    int pos = i + 1;
				    //int iProximaCI = oEnt1.getProximaCI();
					//int iCodigoLocalMedicao = oProj.getCodigoPluviografo();
					//double dCoefManning = oProj.getCoefManning();
				    String strPV = oEnt1.getPv();
					int iLocalId = oEnt1.getLocalId();
					String strLocal = oEnt1.getLocal();
					int iNumeroEstaca = oEnt1.getNumEstaca();
					double dDistEstaca = oEnt1.getDistEstaca();
				    String strEstaca = String.format(
				    	"%s - %s", 
				    	iNumeroEstaca,
				    	nf3.format(dDistEstaca)); 
				    double dCT = oEnt1.getCt();
				    double dCB = oEnt1.getCb();
		    		double dNivelAgua = 0.0;
		    		double dAreaExterna = oEnt1.getAreaExterna();
		    		double dAreaLocal = oEnt1.getAreaLocal();
		    		double dAreaTotal = oEnt1.getAreaTotal();
		    		double dAreaTotalImp = oEnt1.getAreaTotalImp();
					double dCoefImper = oEnt1.getCoefImper();
		    		double dCoefDistr = 0.0;
		    		double dCoefDistrFinal = 0.0;
		    		double dTempoConc = 0.0;
					double dDeclividade = oEnt1.getDeclividade();
					double dDimensoes = DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_400MM.getDiamNominalMeter();
		    		double dComprimentoHoriz = 0.0;
		    		double dComprimentoVert = 0.0;
					double dComprimento = 0.0;
					String strObservacao = "-";
					String strIsRoot = ( bRoot ) ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO;
					String strIsFinish = ( bFinish ) ? AppDefs.DEF_VALUES_SIM : AppDefs.DEF_VALUES_NAO;
					String strIsDeleted = AppDefs.DEF_VALUES_NAO;
					//
		    		double dIndicePluviometrico = 0.0;
		    		double dCoefDefluv = 0.0;
		    		double dDeflLocal = 0.0;
		    		double dDeflEscoar = 0.0;
		    		double dF = 0.0;
		    		double dDeclividadeGreide = 0.0;
		    		double dAlturaAgua = 0.0;
		    		double dYd = 0.0;
				    double dProfundidade = oEnt1.getProfundidade();
		    		double dVelocidade = 0.0;
		    		double dTempoPercurso = 0.0;
		    		double dTempoTotal = 0.0;
		    		//
		    		String strTipoSecaoTubulacao = DrenagemCalc.DEF_TIPOSECAO_CIRCULAR_STR;
				    int iCategoriaTubulacaoId = DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_400MM.getCategoriaTubulacaoId();
				    String strDescricaoCategoriaTubulacao = DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_400MM.getDescricaoCategoriaTubulacao();
				    int iQtdTubulacao = oEnt1.getQtdTubulacao();
				    double dDiametroTubulacao = DrenagemCalc.DIAM_TUBULACAO_CONCRETOCLASSEPA1_400MM.getDiamNominalMeter();
				    double dRaioTubulacao = dDiametroTubulacao / 2.0;
				    double dCotaEntrada = oEnt1.getCotaEntrada();
				    double dCotaSaida = oEnt1.getCotaSaida();
				    double dAreaSecaoMolhada = (Math.PI * dRaioTubulacao * dRaioTubulacao) / 2.0;	// area_secao_molhada = area_semi-circulo
				    double dVazao = oEnt1.getVazao();
				    double dVazaoAcumulada = oEnt1.getVazaoAcumulada();  
		    		//
				    double dDiametro = oEnt1.getDiametroMeter();
				    
					CadCaixaInspecaoDrenagem oProxima1 = oEnt1.getProximo();
				    if(oProxima1 != null) {
				    	GeomPoint3d oPtI = new GeomPoint3d(oEnt1.getPtIns()); 
				    	GeomPoint3d oPtF = new GeomPoint3d(oProxima1.getPtIns());
				    	
				    	dComprimento = oPtI.distTo(oPtF);
				    }

				    CadMemoriaCalculoItemDrenagemOData oItemAtual = CadMemoriaCalculoItemDrenagemOData.create(
				    	doc,
			    	    pos,
			    		pos,
			    		iNumeroCI, 
			    		iCodigoLocalMedicao,
			    		dCoefManning,
			    		strPV,
			    		iLocalId,
			    		strLocal,
			    		strEstaca,
			    		dCT,
	    	    		dCB,
	    	    		dNivelAgua,
	    	    		dAreaExterna,
	    	    		dAreaLocal,
	    	    		dAreaTotal,
	    	    		dAreaTotalImp,
			    		dCoefImper,
	    	    		dCoefDistr,
	    	    		dCoefDistrFinal,
	    	    		dTempoConc,
			    		dDeclividade,
			    		dDimensoes,
			    		dComprimentoHoriz,
			    		dComprimentoVert,
			    		dComprimento,
			    		strObservacao,
			    		strIsRoot,
			    		strIsFinish,
			    		oItemAnterior,
			    		AppDefs.DEF_VALUES_NAO,
			    		dIndicePluviometrico,
			    		dCoefDefluv,
			    		dDeflLocal,
			    		dDeflEscoar,
			    		dF,
			    		dDeclividadeGreide,
			    		dAlturaAgua,
			    		dYd,
			    		dProfundidade,
			    		dVelocidade,
			    		dTempoPercurso,
			    		dTempoTotal,
					    dCotaEntrada,
					    dCotaSaida,
					    dAreaSecaoMolhada,
					    dVazao,
					    dVazaoAcumulada,
					    strTipoSecaoTubulacao,
					    iCategoriaTubulacaoId,
					    strDescricaoCategoriaTubulacao,
					    iQtdTubulacao,
					    dDiametroTubulacao,
					    dDiametro);		
					o.addItem(oItemAtual);
					
					oItemAtual.debug(AppDefs.DEBUG_LEVEL32);

					oPerfilItem.setItemAtual(oItemAtual);

				    map.put(strNumeroCI, oItemAtual);
					
			    	oItemAnterior = oItemAtual;
				}
			}
		}
		return o;
	}
		
	public CadMemoriaCalculoDrenagem reCalculaRedeDrenagem(CadDocumentDef doc, CadMemoriaCalculoDrenagem oMemoriaCalculo, boolean bForce) 
	{
		ArrayList<CadPerfilDrenagem> lsPerfil = oMemoriaCalculo.getLsPerfilDrenagem();
		
		CadBlockDef blkDef = doc.getCurrBlockDef();
		
		CadEntity[] arrCI = blkDef.findAllEntityByObjType(AppDefs.OBJTYPE_MODDRCAIXAINSPECAO);

		for(CadPerfilDrenagem oPerfil : lsPerfil) {
			int szLsPerfilItem = oPerfil.getSzLsTrechoItem();

			CadMemoriaCalculoItemDrenagemOData oItemAnterior = null;
			int pos = 0;
			for(int i = 0; i < szLsPerfilItem; i++) {
				CadPerfilItemDrenagemOData oPerfilItem = oPerfil.getTrechoItemAt(i);
				
				CadMemoriaCalculoItemDrenagemOData oItemAtual = oPerfilItem.getItemAtual();
				if( oItemAtual == null ) {
					oItemAnterior = null;
					pos = 0;					
					continue;
				}
				
				int numeroCIAtual = oItemAtual.getNumeroCI();
				CadCaixaInspecaoDrenagem oCIAtual = this.findItem(arrCI, numeroCIAtual);			

				int numeroCIProxima = oCIAtual.getProximaCI();
				CadCaixaInspecaoDrenagem oCIProxima = null;
				if(numeroCIProxima != -1)
					oCIProxima = this.findItem(arrCI, numeroCIProxima);
				
				//CotaTerreno
				//
				double dCotaTerrenoAtual = oItemAtual.getCotaTerreno();
				
				//Profundidade
				//
				double dProfundidadeAtual = oItemAtual.getProfundidade();
				if(dProfundidadeAtual > AppDefs.DEF_DEFAULT_DRENAGEM_PROFUNDIDADEMINIMA) {
					dProfundidadeAtual = AppDefs.DEF_DEFAULT_DRENAGEM_PROFUNDIDADEMINIMA;
					oItemAtual.setProfundidade(dProfundidadeAtual);
				}
				
				//CotaFundo
				//
				double dCotaFundoAtual = dCotaTerrenoAtual + dProfundidadeAtual; 
				oItemAtual.setFundo(dCotaFundoAtual);
				
				if(numeroCIProxima == -1) {
					//Se Existe CAIXA_INSPECAO_ANTERIOR Entao: CALCULA_COTA_ENTRADA_TUBULACAO
					//
					if(oItemAnterior != null) {
						//DiametroTubulacao - ANTERIOR
						//
						double dDiametroTubulacaoAnteriorMeter = oItemAnterior.getDiametroTubulacaoMeter();			// diametro tubulacao (mm)
						
						// Dimensoes (= DiametroTubulacao; ou = LarguraTubulacao) - ANTERIOR
						//
						double dDimensoesAnteriorMeter = dDiametroTubulacaoAnteriorMeter;

						double dDimensoesAnteriorMeter2 = dDimensoesAnteriorMeter / 2.0;		// = DiametroTubulacao / 2.0 (ou LarguraTubulacao / 2.0)

						//CotaSaida
						//
						double dCotaSaida = dCotaFundoAtual + dDimensoesAnteriorMeter2;
						oItemAtual.setCotaSaida(dCotaSaida);
						
						//CotaEntrada
						//
						double dCotaEntrada = dCotaSaida + DrenagemCalc.DESNIVEL_MINIMO_POR_CAIXA;
						oItemAtual.setCotaEntrada(dCotaEntrada);
					}					
					break;
				}

				//AreaTotal
				//
				double dAreaExternaAtual = oItemAtual.getAreaExterna();
				double dAreaLocalAtual = oItemAtual.getAreaLocal();
				double dAreaTotalAtual = dAreaExternaAtual + dAreaLocalAtual;
				oItemAtual.setAreaTotal(dAreaTotalAtual);
			
				//Coef.Distr.
				//
				double dCoefDistrAtual = this.calcCoefDistr(dAreaTotalAtual);
				oItemAtual.setCoefDistr(dCoefDistrAtual);
				
				//Coef.Distr.Final
				//
				double dCoefDistrFinalAtual = this.calcCoefDistrFinal(dAreaTotalAtual, dCoefDistrAtual);
				oItemAtual.setCoefDistrFinal(dCoefDistrFinalAtual);

				//Declividade Greide (m/m)
				//
	    		double dComprimentoHorizAtual = this.calcComprimentoHoriz(oCIAtual, oCIProxima);
				oItemAtual.setComprimentoHoriz(dComprimentoHorizAtual);

	    		double dComprimentoVertAtual = this.calcComprimentoVert(oCIAtual, oCIProxima);
				oItemAtual.setComprimentoVert(dComprimentoVertAtual);
				
				double dComprimentoAtual = this.calcComprimento(oCIAtual, oCIProxima);
				oItemAtual.setComprimento(dComprimentoAtual);
				
				double dDeclividadeTerrenoAtual = this.calcDeclividadeTerreno(oCIAtual, oCIProxima);
				oItemAtual.setDeclividadeGreide(dDeclividadeTerrenoAtual);

				//Declividade (m/m)
				//
				double dDeclividadeTubulacaoAtual = oItemAtual.getDeclividade();

				dDeclividadeTubulacaoAtual = this.calcDeclividadeTubulacao(dDeclividadeTerrenoAtual, dDeclividadeTubulacaoAtual);
				oItemAtual.setDeclividade(dDeclividadeTubulacaoAtual);
				
				//DiametroTubulacao
				//
				double dDiametroTubulacaoAtualMeter = oItemAtual.getDiametroTubulacaoMeter();			// diametro tubulacao (mm)
				oItemAtual.setDiametroTubulacaoMeter(dDiametroTubulacaoAtualMeter);

				//Indice Pluviometrico
				//
				int iCodigoLocalMedicao = oMemoriaCalculo.getCodigoLocalMedicao();

				double dPeriodoRecorrencia = oMemoriaCalculo.getPeriodoRecorrencia();			
				
				double dIndicePluviometricoAtual = this.calcIndicePluviometrico(iCodigoLocalMedicao, dPeriodoRecorrencia, oItemAnterior);
				oItemAtual.setIndicePluviometrico(dIndicePluviometricoAtual);
				
				//Coef.Defluv.
				//
				double dCoefImperAtual = oItemAtual.getCoefImper();

				double dCoefDefluvAtual = this.calcCoefDefluv(oItemAnterior, dCoefImperAtual, dIndicePluviometricoAtual);
				oItemAtual.setCoefDefluv(dCoefDefluvAtual);
				
				//Defl.Local
				//
				double dDeflLocalAtual = this.calcDeflLocal(dAreaLocalAtual, dCoefDistrFinalAtual, dIndicePluviometricoAtual, dCoefDefluvAtual);
				oItemAtual.setDeflLocal(dDeflLocalAtual);

				//Defl.Escoar
				//
				double dDeflEscoarAtual = this.calcDeflEscoar(oItemAnterior, dDeflLocalAtual);
				oItemAtual.setDeflEscoar(dDeflEscoarAtual);

				//TipoSecaoTubulacao
				//
				int iTipoSecao = DrenagemCalc.TB_TIPOSECAO_CIRCLE.getItemDataIdVal();
				String strTipoSecao = oItemAtual.getTipoSecaoTubulacao();
				if( DrenagemCalc.DEF_TIPOSECAO_RETANGULAR_STR.equals(strTipoSecao) ) {
					iTipoSecao = DrenagemCalc.TB_TIPOSECAO_RECTANGLE.getItemDataIdVal();
				}

				// Dimensoes (= DiametroTubulacao; ou = LarguraTubulacao)
				//
				double dDimensoesMeter = dDiametroTubulacaoAtualMeter;

				double dDimensoesMeter2 = dDimensoesMeter / 2.0;		// = DiametroTubulacao / 2.0 (ou LarguraTubulacao / 2.0)
				
				//Velocidade
				//
				double dCoefManning = oMemoriaCalculo.getCoefManning();

				double dVelocidadeAtual = this.calcVelocidade(			// Velocidade de chegada no PV 
					iTipoSecao, 
					dCoefManning, 
					dDeflEscoarAtual, 
					oItemAtual);
				oItemAtual.setVelocidade(dVelocidadeAtual);

				//TempoPercurso
				//
				double dTempoPercursoAtual = this.calcTempoPercurso(oCIAtual, oCIProxima, dVelocidadeAtual);
				oItemAtual.setTempoPercurso(dTempoPercursoAtual);

				//TempoConcTotal
				//			
				double dTempoTotalPercAtual = this.calcTempoTotalPerc(oItemAnterior, dTempoPercursoAtual);
				oItemAtual.setTempoTotal(dTempoTotalPercAtual);

				//TempoConc
				//
				double dTempoConcAtual = this.calcTempoConc(oItemAnterior, dTempoPercursoAtual);
				oItemAtual.setTempoConc(dTempoConcAtual);
				
				//F
				//
				double dF = this.calcF(dCoefManning, dDeflEscoarAtual, dDeclividadeTubulacaoAtual, dDimensoesMeter);
				oItemAtual.setF(dF);
				
				//AlturaAgua
				//
				double dAlturaAguaAtual = this.calcAlturaAgua(
					iTipoSecao,
					dDeflEscoarAtual, 
					dDeclividadeTubulacaoAtual, 
					dDimensoesMeter,
					dF);
				oItemAtual.setAlturaAgua(dAlturaAguaAtual);
				
				//Y/D
				//
				double dYd = this.calcYd(dAlturaAguaAtual, dDimensoesMeter);
				oItemAtual.setYd(dYd);				

				//NivelAgua
				//
				double dNivelAgua = this.calcNivelAgua(dCotaFundoAtual, dAlturaAguaAtual);
				oItemAtual.setNivelAgua(dNivelAgua);
				
				//CotaSaida
				//
				double dCotaSaida = dCotaFundoAtual + dDimensoesMeter2;
				oItemAtual.setCotaSaida(dCotaSaida);
				
				//CotaEntrada
				//
				double dCotaEntrada = dCotaSaida + DrenagemCalc.DESNIVEL_MINIMO_POR_CAIXA;
				oItemAtual.setCotaEntrada(dCotaEntrada);
				
				//AreaSecaoMolhada
				//
				double dAreaSecaoMolhada = this.calcAreaSecaoMolhada(iTipoSecao, dAlturaAguaAtual, dDimensoesMeter);
				oItemAtual.setAreaSecaoMolhada(dAreaSecaoMolhada);
				
				//Vazao
				//
				double dVazaoAtual = this.calcVazao(dAreaSecaoMolhada, dVelocidadeAtual);
				oItemAtual.setVazao(dVazaoAtual);
				
				//VazaoAcumulada
				//
				//double dVazaoAcumulada = this.calcVazaoAcumulada(oItemAnterior, dVazaoAtual);
				//oItemAtual.setVazaoAcumulada(dVazaoAcumulada);
				oItemAtual.setVazaoAcumulada(dVazaoAtual);
				
				//Profundidade Montante-Jusante
				//
				double dProfMontJus = this.calcProfMontJus(oItemAnterior, dProfundidadeAtual);
				oItemAtual.setProfMontJus(dProfMontJus);
				
				oItemAnterior = oItemAtual;
				pos++;
			}
		}
		return oMemoriaCalculo;
	}
	
	public CadMemoriaCalculoDrenagem reCalculaRedeDrenagem_20251013(CadDocumentDef doc, CadMemoriaCalculoDrenagem oMemoriaCalculo, boolean bForce) 
	{
		ArrayList<CadMemoriaCalculoItemDrenagemOData> lsItem = oMemoriaCalculo.getLsItem();
		
		CadBlockDef blkDef = doc.getCurrBlockDef();
		
		CadEntity[] arrCI = blkDef.findAllEntityByObjType(AppDefs.OBJTYPE_MODDRCAIXAINSPECAO);

		CadMemoriaCalculoItemDrenagemOData oItemAnterior = null;
		int pos = 0;
		for(CadMemoriaCalculoItemDrenagemOData oItemAtual : lsItem) {
			int numeroCIAtual = oItemAtual.getNumeroCI();
			CadCaixaInspecaoDrenagem oCIAtual = this.findItem(arrCI, numeroCIAtual);			

			int numeroCIProxima = oCIAtual.getProximaCI();
			CadCaixaInspecaoDrenagem oCIProxima = null;
			if(numeroCIProxima != -1)
				oCIProxima = this.findItem(arrCI, numeroCIProxima);
			
			//CotaTerreno
			//
			double dCotaTerrenoAtual = oItemAtual.getCotaTerreno();
			
			//Profundidade
			//
			double dProfundidadeAtual = oItemAtual.getProfundidade();
			if(dProfundidadeAtual > AppDefs.DEF_DEFAULT_DRENAGEM_PROFUNDIDADEMINIMA) {
				dProfundidadeAtual = AppDefs.DEF_DEFAULT_DRENAGEM_PROFUNDIDADEMINIMA;
				oItemAtual.setProfundidade(dProfundidadeAtual);
			}
			
			//CotaFundo
			//
			double dCotaFundoAtual = dCotaTerrenoAtual + dProfundidadeAtual; 
			oItemAtual.setFundo(dCotaFundoAtual);
			
			if(numeroCIProxima == -1) break;

			//AreaTotal
			//
			double dAreaExternaAtual = oItemAtual.getAreaExterna();
			double dAreaLocalAtual = oItemAtual.getAreaLocal();
			double dAreaTotalAtual = dAreaExternaAtual + dAreaLocalAtual;
			oItemAtual.setAreaTotal(dAreaTotalAtual);
		
			//Coef.Distr.
			//
			double dCoefDistrAtual = this.calcCoefDistr(dAreaTotalAtual);
			oItemAtual.setCoefDistr(dCoefDistrAtual);
			
			//Coef.Distr.Final
			//
			double dCoefDistrFinalAtual = this.calcCoefDistrFinal(dAreaTotalAtual, dCoefDistrAtual);
			oItemAtual.setCoefDistrFinal(dCoefDistrFinalAtual);

			//Declividade Greide (m/m)
			//
    		double dComprimentoHorizAtual = this.calcComprimentoHoriz(oCIAtual, oCIProxima);
			oItemAtual.setComprimentoHoriz(dComprimentoHorizAtual);

    		double dComprimentoVertAtual = this.calcComprimentoVert(oCIAtual, oCIProxima);
			oItemAtual.setComprimentoVert(dComprimentoVertAtual);
			
			double dComprimentoAtual = this.calcComprimento(oCIAtual, oCIProxima);
			oItemAtual.setComprimento(dComprimentoAtual);
			
			double dDeclividadeTerrenoAtual = this.calcDeclividadeTerreno(oCIAtual, oCIProxima);
			oItemAtual.setDeclividadeGreide(dDeclividadeTerrenoAtual);

			//Declividade (m/m)
			//
			double dDeclividadeTubulacaoAtual = oItemAtual.getDeclividade();

			dDeclividadeTubulacaoAtual = this.calcDeclividadeTubulacao(dDeclividadeTerrenoAtual, dDeclividadeTubulacaoAtual);
			oItemAtual.setDeclividade(dDeclividadeTubulacaoAtual);
			
			//DiametroTubulacao
			//
			double dDiametroTubulacaoAtualMeter = oItemAtual.getDiametroTubulacaoMeter();			// diametro tubulacao (mm)
			oItemAtual.setDiametroTubulacaoMeter(dDiametroTubulacaoAtualMeter);

			//Indice Pluviometrico
			//
			int iCodigoLocalMedicao = oMemoriaCalculo.getCodigoLocalMedicao();

			double dPeriodoRecorrencia = oMemoriaCalculo.getPeriodoRecorrencia();			
			
			double dIndicePluviometricoAtual = this.calcIndicePluviometrico(iCodigoLocalMedicao, dPeriodoRecorrencia, oItemAnterior);
			oItemAtual.setIndicePluviometrico(dIndicePluviometricoAtual);
			
			//Coef.Defluv.
			//
			double dCoefImperAtual = oItemAtual.getCoefImper();

			double dCoefDefluvAtual = this.calcCoefDefluv(oItemAnterior, dCoefImperAtual, dIndicePluviometricoAtual);
			oItemAtual.setCoefDefluv(dCoefDefluvAtual);
			
			//Defl.Local
			//
			double dDeflLocalAtual = this.calcDeflLocal(dAreaLocalAtual, dCoefDistrFinalAtual, dIndicePluviometricoAtual, dCoefDefluvAtual);
			oItemAtual.setDeflLocal(dDeflLocalAtual);

			//Defl.Escoar
			//
			double dDeflEscoarAtual = this.calcDeflEscoar(oItemAnterior, dDeflLocalAtual);
			oItemAtual.setDeflEscoar(dDeflEscoarAtual);

			//TipoSecaoTubulacao
			//
			int iTipoSecao = DrenagemCalc.TB_TIPOSECAO_CIRCLE.getItemDataIdVal();
			String strTipoSecao = oItemAtual.getTipoSecaoTubulacao();
			if( DrenagemCalc.DEF_TIPOSECAO_RETANGULAR_STR.equals(strTipoSecao) ) {
				iTipoSecao = DrenagemCalc.TB_TIPOSECAO_RECTANGLE.getItemDataIdVal();
			}

			// Dimensoes (= DiametroTubulacao; ou = LarguraTubulacao)
			//
			double dDimensoesMeter = dDiametroTubulacaoAtualMeter / 1000.0;

			double dDimensoesMeter2 = dDimensoesMeter / 2.0;		// = DiametroTubulacao / 2.0 (ou LarguraTubulacao / 2.0)
			
			//Velocidade
			//
			double dCoefManning = oMemoriaCalculo.getCoefManning();

			double dVelocidadeAtual = this.calcVelocidade(			// Velocidade de chegada no PV 
				iTipoSecao, 
				dCoefManning, 
				dDeflEscoarAtual, 
				oItemAtual);
			oItemAtual.setVelocidade(dVelocidadeAtual);

			//TempoPercurso
			//
			double dTempoPercursoAtual = this.calcTempoPercurso(oCIAtual, oCIProxima, dVelocidadeAtual);
			oItemAtual.setTempoPercurso(dTempoPercursoAtual);

			//TempoConcTotal
			//			
			double dTempoTotalPercAtual = this.calcTempoTotalPerc(oItemAnterior, dTempoPercursoAtual);
			oItemAtual.setTempoTotal(dTempoTotalPercAtual);

			//TempoConc
			//
			double dTempoConcAtual = this.calcTempoConc(oItemAnterior, dTempoPercursoAtual);
			oItemAtual.setTempoConc(dTempoConcAtual);
			
			//F
			//
			double dF = this.calcF(dCoefManning, dDeflEscoarAtual, dDeclividadeTubulacaoAtual, dDimensoesMeter);
			oItemAtual.setF(dF);
			
			//AlturaAgua
			//
			double dAlturaAguaAtual = this.calcAlturaAgua(
				iTipoSecao,
				dDeflEscoarAtual, 
				dDeclividadeTubulacaoAtual, 
				dDimensoesMeter,
				dF);
			oItemAtual.setAlturaAgua(dAlturaAguaAtual);
			
			//Y/D
			//
			double dYd = this.calcYd(dAlturaAguaAtual, dDimensoesMeter);
			oItemAtual.setYd(dYd);				

			//NivelAgua
			//
			double dNivelAgua = this.calcNivelAgua(dCotaFundoAtual, dAlturaAguaAtual);
			oItemAtual.setNivelAgua(dNivelAgua);
			
			//CotaSaida
			//
			double dCotaSaida = dCotaFundoAtual + dDimensoesMeter2;
			oItemAtual.setCotaSaida(dCotaSaida);
			
			//CotaEntrada
			//
			double dCotaEntrada = dCotaSaida + DrenagemCalc.DESNIVEL_MINIMO_POR_CAIXA;
			oItemAtual.setCotaEntrada(dCotaEntrada);
			
			//AreaSecaoMolhada
			//
			double dAreaSecaoMolhada = this.calcAreaSecaoMolhada(iTipoSecao, dAlturaAguaAtual, dDimensoesMeter);
			oItemAtual.setAreaSecaoMolhada(dAreaSecaoMolhada);
			
			//Vazao
			//
			double dVazaoAtual = this.calcVazao(dAreaSecaoMolhada, dVelocidadeAtual);
			oItemAtual.setVazao(dVazaoAtual);
			
			//VazaoAcumulada
			//
			//double dVazaoAcumulada = this.calcVazaoAcumulada(oItemAnterior, dVazaoAtual);
			//oItemAtual.setVazaoAcumulada(dVazaoAcumulada);
			oItemAtual.setVazaoAcumulada(dVazaoAtual);
			
			//Profundidade Montante-Jusante
			//
			double dProfMontJus = this.calcProfMontJus(oItemAnterior, dProfundidadeAtual);
			oItemAtual.setProfMontJus(dProfMontJus);
			
			oItemAnterior = oItemAtual;
			pos++;
		}		
		return oMemoriaCalculo;
	}
		
}
