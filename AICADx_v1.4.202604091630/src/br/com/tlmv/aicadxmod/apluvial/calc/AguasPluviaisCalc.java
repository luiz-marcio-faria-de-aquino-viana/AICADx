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

package br.com.tlmv.aicadxmod.apluvial.calc;

import br.com.tlmv.aicadxmod.drenagem.vo.CoeficienteChuvasIDFVO;
import br.com.tlmv.aicadxmod.drenagem.vo.FatorDrenagemVO;
import br.com.tlmv.aicadxmod.drenagem.vo.VazaoColetorPredialVO;

public class AguasPluviaisCalc 
{
//Public
	
    // INITIAL_PARAMS
    //
	public static double COTA_TERRENO_INICIAL = 0.0;      			// cota inicial do terreno (=0.0 m)
	public static double DESNIVEL_MINIMO_POR_CAIXA = 0.03;    		// desnivel minimo por caixa (=3 cm)
	public static int MAX_NUMERO_COLETOR_PREDIAL = 10;				// quantidade maxima de coletores prediais (=10)

    // COEF_MANNING
    //
	public static double COEFMANNING_SECAO_CIRCULAR = 0.013;
	public static double COEFMANNING_SECAO_RETANGULAR = 0.015;
    
    // DECLIVIDADE
    //
	public static double DECLIVIDADE_MIN = 0.5;
	//
	public static double DECLIVIDADE_0_5PC = 0.5;
	public static double DECLIVIDADE_1PC = 1.0;
	public static double DECLIVIDADE_2PC = 2.0;
	public static double DECLIVIDADE_3PC = 3.0;
	public static double DECLIVIDADE_4PC = 4.0;
	public static double DECLIVIDADE_5PC = 5.0;
	public static double DECLIVIDADE_6PC = 6.0;
	//
	public static double DECLIVIDADE_MAX = 7.0;

    // DIAMETRO_TUBULACAO
    //
    public static double DIAM_TUBULACAO_50MM = 50.0;
    public static double DIAM_TUBULACAO_75MM = 75.0;
    public static double DIAM_TUBULACAO_100MM = 100.0;
    public static double DIAM_TUBULACAO_150MM = 150.0;
    public static double DIAM_TUBULACAO_200MM = 200.0;
    public static double DIAM_TUBULACAO_250MM = 250.0;
    public static double DIAM_TUBULACAO_300MM = 300.0;
    public static double DIAM_TUBULACAO_350MM = 350.0;
    public static double DIAM_TUBULACAO_400MM = 400.0;
	
	//IFC - LOCAIS DE MEDICAO (VALUE)
    //
	public static int IDFLOCAL_SANTACRUZ_VAL = 1001;
	public static int IDFLOCAL_CAMPOGRANDE_VAL = 1002;
	public static int IDFLOCAL_MEDANHA_VAL = 1003;
	public static int IDFLOCAL_BANGU_VAL = 1004;
	public static int IDFLOCAL_JARDIMBOTANICO_VAL = 1005;
	public static int IDFLOCAL_CAPELAMAYRINK_VAL = 1006;
	public static int IDFLOCAL_VIA11_VAL = 1007;
	public static int IDFLOCAL_SABOIALIMA_VAL = 1008;
	public static int IDFLOCAL_BENFICA_VAL = 1009;
	public static int IDFLOCAL_REALENGO_VAL = 1010;
	public static int IDFLOCAL_IRAJA_VAL = 1011;
	public static int IDFLOCAL_ELETROBRASTAQUARA_VAL = 1012;
	//
	public static int IDFLOCAL_ARARUAMA_VAL = 1021;
	public static int IDFLOCAL_JACAREPAGUA_VAL = 1022;

	//IFC - LOCAIS DE MEDICAO (TEXT)
	//
	public static String IDFLOCAL_SANTACRUZ_STR = "Santa Cruz";
	public static String IDFLOCAL_CAMPOGRANDE_STR = "Campo Grande";
	public static String IDFLOCAL_MEDANHA_STR = "Medanha";
	public static String IDFLOCAL_BANGU_STR = "Bangu";
	public static String IDFLOCAL_JARDIMBOTANICO_STR = "Jardim Botanico";
	public static String IDFLOCAL_CAPELAMAYRINK_STR = "Capela Mayrink";
	public static String IDFLOCAL_VIA11_STR = "Via 11";
	public static String IDFLOCAL_SABOIALIMA_STR = "Saboia Lima";
	public static String IDFLOCAL_BENFICA_STR = "Benfica";
	public static String IDFLOCAL_REALENGO_STR = "Realengo";
	public static String IDFLOCAL_IRAJA_STR = "Iraja";
	public static String IDFLOCAL_ELETROBRASTAQUARA_STR = "Eletrobras - Taquara";
	//
	public static String IDFLOCAL_ARARUAMA_STR = "Araruama";
	public static String IDFLOCAL_JACAREPAGUA_STR = "Jacarepagua";
	
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
	
    // ARR_COLETOR_PREDIAL
    //
    public static VazaoColetorPredialVO[] ARR_COLETOR_PREDIAL = {
        //declividade =0.5%
        new VazaoColetorPredialVO(DIAM_TUBULACAO_50MM,  DECLIVIDADE_0_5PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_75MM,  DECLIVIDADE_0_5PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_100MM, DECLIVIDADE_0_5PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_150MM, DECLIVIDADE_0_5PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_200MM, DECLIVIDADE_0_5PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_250MM, DECLIVIDADE_0_5PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_300MM, DECLIVIDADE_0_5PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_350MM, DECLIVIDADE_0_5PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_400MM, DECLIVIDADE_0_5PC),
        //declividade =1.0%
        new VazaoColetorPredialVO(DIAM_TUBULACAO_50MM,  DECLIVIDADE_1PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_75MM,  DECLIVIDADE_1PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_100MM, DECLIVIDADE_1PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_150MM, DECLIVIDADE_1PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_200MM, DECLIVIDADE_1PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_250MM, DECLIVIDADE_1PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_300MM, DECLIVIDADE_1PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_350MM, DECLIVIDADE_1PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_400MM, DECLIVIDADE_1PC),
        //declividade =2.0%
        new VazaoColetorPredialVO(DIAM_TUBULACAO_50MM,  DECLIVIDADE_2PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_75MM,  DECLIVIDADE_2PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_100MM, DECLIVIDADE_2PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_150MM, DECLIVIDADE_2PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_200MM, DECLIVIDADE_2PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_250MM, DECLIVIDADE_2PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_300MM, DECLIVIDADE_2PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_350MM, DECLIVIDADE_2PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_400MM, DECLIVIDADE_2PC),
        //declividade =3.0%
        new VazaoColetorPredialVO(DIAM_TUBULACAO_50MM,  DECLIVIDADE_3PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_75MM,  DECLIVIDADE_3PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_100MM, DECLIVIDADE_3PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_150MM, DECLIVIDADE_3PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_200MM, DECLIVIDADE_3PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_250MM, DECLIVIDADE_3PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_300MM, DECLIVIDADE_3PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_350MM, DECLIVIDADE_3PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_400MM, DECLIVIDADE_3PC),
        //declividade =4.0%
        new VazaoColetorPredialVO(DIAM_TUBULACAO_50MM,  DECLIVIDADE_4PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_75MM,  DECLIVIDADE_4PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_100MM, DECLIVIDADE_4PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_150MM, DECLIVIDADE_4PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_200MM, DECLIVIDADE_4PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_250MM, DECLIVIDADE_4PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_300MM, DECLIVIDADE_4PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_350MM, DECLIVIDADE_4PC),
        new VazaoColetorPredialVO(DIAM_TUBULACAO_400MM, DECLIVIDADE_4PC),
    };

    // FATOR_DRENAGEM
    //
    public static final FatorDrenagemVO[] ARR_TBLCOL_FATOR_DRENAGEM = {
    	new FatorDrenagemVO(1001, 0.000000, 0.00, 0.000, 0.00),
    	new FatorDrenagemVO(1002, 0.000047, 0.01, 0.001, 0.01),
		new FatorDrenagemVO(1003, 0.000209, 0.02, 0.004, 0.01),
		new FatorDrenagemVO(1004, 0.000501, 0.03, 0.007, 0.02),
		new FatorDrenagemVO(1005, 0.000929, 0.04, 0.011, 0.03),
		new FatorDrenagemVO(1006, 0.001497, 0.05, 0.015, 0.03),
		new FatorDrenagemVO(1007, 0.002208, 0.06, 0.019, 0.04),
		new FatorDrenagemVO(1008, 0.003064, 0.07, 0.024, 0.05),
		new FatorDrenagemVO(1009, 0.004065, 0.08, 0.029, 0.05),
		new FatorDrenagemVO(1010, 0.005213, 0.09, 0.035, 0.06),
		new FatorDrenagemVO(1011, 0.006507, 0.10, 0.041, 0.06),
		new FatorDrenagemVO(1012, 0.007947, 0.11, 0.047, 0.07),
		new FatorDrenagemVO(1013, 0.009533, 0.12, 0.053, 0.08),
		new FatorDrenagemVO(1014, 0.011263, 0.13, 0.060, 0.08),
		new FatorDrenagemVO(1015, 0.013136, 0.14, 0.067, 0.09),
		new FatorDrenagemVO(1016, 0.015151, 0.15, 0.074, 0.09),
		new FatorDrenagemVO(1017, 0.017306, 0.16, 0.081, 0.10),
		new FatorDrenagemVO(1018, 0.019600, 0.17, 0.089, 0.10),
		new FatorDrenagemVO(1019, 0.022031, 0.18, 0.096, 0.11),
		new FatorDrenagemVO(1020, 0.024596, 0.19, 0.104, 0.12),
		new FatorDrenagemVO(1021, 0.027295, 0.20, 0.112, 0.12),
		new FatorDrenagemVO(1022, 0.030123, 0.21, 0.120, 0.13),
		new FatorDrenagemVO(1023, 0.033080, 0.22, 0.128, 0.13),
		new FatorDrenagemVO(1024, 0.036163, 0.23, 0.136, 0.14),
		new FatorDrenagemVO(1025, 0.039369, 0.24, 0.145, 0.14),
		new FatorDrenagemVO(1026, 0.042695, 0.25, 0.154, 0.15),
		new FatorDrenagemVO(1027, 0.046139, 0.26, 0.162, 0.15),
		new FatorDrenagemVO(1028, 0.049699, 0.27, 0.171, 0.16),
		new FatorDrenagemVO(1029, 0.053370, 0.28, 0.180, 0.16),
		new FatorDrenagemVO(1030, 0.057151, 0.29, 0.189, 0.17),
		new FatorDrenagemVO(1031, 0.061038, 0.30, 0.198, 0.17),
		new FatorDrenagemVO(1032, 0.065028, 0.31, 0.207, 0.18),
		new FatorDrenagemVO(1033, 0.069118, 0.32, 0.217, 0.18),
		new FatorDrenagemVO(1034, 0.073304, 0.33, 0.226, 0.18),
		new FatorDrenagemVO(1035, 0.077584, 0.34, 0.235, 0.19),
		new FatorDrenagemVO(1036, 0.081955, 0.35, 0.245, 0.19),
		new FatorDrenagemVO(1037, 0.086411, 0.36, 0.255, 0.20),
		new FatorDrenagemVO(1038, 0.090951, 0.37, 0.264, 0.20),
		new FatorDrenagemVO(1039, 0.095571, 0.38, 0.274, 0.21),
		new FatorDrenagemVO(1040, 0.100266, 0.39, 0.284, 0.21),
		new FatorDrenagemVO(1041, 0.105034, 0.40, 0.293, 0.21),
		new FatorDrenagemVO(1042, 0.109871, 0.41, 0.303, 0.22),
		new FatorDrenagemVO(1043, 0.114772, 0.42, 0.313, 0.22),
		new FatorDrenagemVO(1044, 0.119734, 0.43, 0.323, 0.23),
		new FatorDrenagemVO(1045, 0.124754, 0.44, 0.333, 0.23),
		new FatorDrenagemVO(1046, 0.129826, 0.45, 0.343, 0.23),
		new FatorDrenagemVO(1047, 0.134948, 0.46, 0.353, 0.24),
		new FatorDrenagemVO(1048, 0.140114, 0.47, 0.363, 0.24),
		new FatorDrenagemVO(1049, 0.145322, 0.48, 0.373, 0.24),
		new FatorDrenagemVO(1050, 0.150566, 0.49, 0.383, 0.25),
		new FatorDrenagemVO(1051, 0.155843, 0.50, 0.393, 0.25),
		new FatorDrenagemVO(1052, 0.161147, 0.51, 0.403, 0.25),
		new FatorDrenagemVO(1053, 0.166476, 0.52, 0.413, 0.26),
		new FatorDrenagemVO(1054, 0.171823, 0.53, 0.423, 0.26),
		new FatorDrenagemVO(1055, 0.177185, 0.54, 0.433, 0.26),
		new FatorDrenagemVO(1056, 0.182558, 0.55, 0.443, 0.26),
		new FatorDrenagemVO(1057, 0.187935, 0.56, 0.453, 0.27),
		new FatorDrenagemVO(1058, 0.193313, 0.57, 0.462, 0.27),
		new FatorDrenagemVO(1059, 0.198687, 0.58, 0.472, 0.27),
		new FatorDrenagemVO(1060, 0.204052, 0.59, 0.482, 0.28),
		new FatorDrenagemVO(1061, 0.209403, 0.60, 0.492, 0.28),
		new FatorDrenagemVO(1062, 0.214734, 0.61, 0.502, 0.28),
		new FatorDrenagemVO(1063, 0.220041, 0.62, 0.512, 0.28),
		new FatorDrenagemVO(1064, 0.225318, 0.63, 0.521, 0.28),
		new FatorDrenagemVO(1065, 0.230560, 0.64, 0.531, 0.29),
		new FatorDrenagemVO(1066, 0.235762, 0.65, 0.540, 0.29),
		new FatorDrenagemVO(1067, 0.240916, 0.66, 0.550, 0.29),
		new FatorDrenagemVO(1068, 0.246019, 0.67, 0.559, 0.29),
		new FatorDrenagemVO(1069, 0.251064, 0.68, 0.569, 0.29),
		new FatorDrenagemVO(1070, 0.256045, 0.69, 0.578, 0.29),
		new FatorDrenagemVO(1071, 0.260955, 0.70, 0.587, 0.30),
		new FatorDrenagemVO(1072, 0.265788, 0.71, 0.596, 0.30),
		new FatorDrenagemVO(1073, 0.270538, 0.72, 0.605, 0.30),
		new FatorDrenagemVO(1074, 0.275198, 0.73, 0.614, 0.30),
		new FatorDrenagemVO(1075, 0.279761, 0.74, 0.623, 0.30),
		new FatorDrenagemVO(1076, 0.284219, 0.75, 0.632, 0.30),
		new FatorDrenagemVO(1077, 0.288565, 0.76, 0.640, 0.30),
		new FatorDrenagemVO(1078, 0.292791, 0.77, 0.649, 0.30),
		new FatorDrenagemVO(1079, 0.296888, 0.78, 0.657, 0.30),
		new FatorDrenagemVO(1080, 0.300848, 0.79, 0.666, 0.30),
		new FatorDrenagemVO(1081, 0.304662, 0.80, 0.674, 0.30),
		new FatorDrenagemVO(1082, 0.308320, 0.81, 0.681, 0.30),
		new FatorDrenagemVO(1083, 0.311812, 0.82, 0.689, 0.30),
		new FatorDrenagemVO(1084, 0.315126, 0.83, 0.697, 0.30),
		new FatorDrenagemVO(1085, 0.318251, 0.84, 0.704, 0.30),
		new FatorDrenagemVO(1086, 0.321173, 0.85, 0.712, 0.30),
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
	public static final double DEF_ESGOTO_PROFCI_60CM			= 0.6;
	public static final double DEF_ESGOTO_DECLIVIDADEMINCI		= 0.5;	//min: valor_percentual
	public static final double DEF_ESGOTO_DECLIVIDADEMAXCI		= 4.0;	//max: valor_percentual
	//AGUA_PLUVIAL - DIAMETRO
	public static final double DEF_APLUVIAL_DIAMCI_60CM			= 0.6;
	public static final double DEF_APLUVIAL_DIAMCI_100CM		= 1.0;
	public static final double DEF_APLUVIAL_PROFCI_60CM			= 0.6;
	public static final double DEF_APLUVIAL_DECLIVIDADEMINCI	= 0.5;	//min: valor_percentual
	public static final double DEF_APLUVIAL_DECLIVIDADEMAXCI	= 4.0;	//max: valor_percentual
	//DRENAGEM - DIAMETRO
	public static final double DEF_DRENAGEM_DIAMCI_60CM			= 0.6;
	public static final double DEF_DRENAGEM_DIAMCI_100CM		= 1.0;
	public static final double DEF_DRENAGEM_PROFCI_60CM			= 0.6;
	public static final double DEF_DRENAGEM_DECLIVIDADEMINCI	= 0.5;	//min: valor_percentual
	public static final double DEF_DRENAGEM_DECLIVIDADEMAXCI	= 4.0;	//max: valor_percentual
	
    /* METHODES */
    
	// selectFatorDrenagem(): seleciona item da tabela de fator de drenagem
	// Parametros:
	// f - valor do fator de drenagem usado na pesquisa
	public static FatorDrenagemVO selectFatorDrenagem(double f) {
		for(FatorDrenagemVO oF : ARR_TBLCOL_FATOR_DRENAGEM) {
			if(f <= oF.getF())
				return oF;
		}
		return null;
	}
		
	// calcIndicePluviometrico(): calculo do indice pluviometrico
	// NOTA: A intensidade pluviométrica será calculada a partir da aplicação de equações de chuvas intensas (IDF) validas para o municipio do Rio de Janeiro
	// Parametros:
	// a, b, c, e d - valores dos coeficientes em funcao da localidade
	// Tr - periodo de recorrencia (em anos)
	// t - duracao da chuva (em minutos)
	public static double calcIndicePluviometrico(
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
	public static double calcIdfIndicePluviometrico(
		int local,
		double Tr,
		double t)
	{
		int sz = AguasPluviaisCalc.ARR_COEF_CHUVAS_IDF.length;
		
		CoeficienteChuvasIDFVO oCoefChuvasIDF0 = AguasPluviaisCalc.ARR_COEF_CHUVAS_IDF[0];		

		int pos = local - oCoefChuvasIDF0.getOid();
		if( !((pos > 0) && (pos < sz)) ) return 0.0;
		
		CoeficienteChuvasIDFVO oCoefChuvasIDF = AguasPluviaisCalc.ARR_COEF_CHUVAS_IDF[pos];
		
		double a = oCoefChuvasIDF.getA();
		double b = oCoefChuvasIDF.getB();
		double c = oCoefChuvasIDF.getC();
		double d = oCoefChuvasIDF.getD();
				
		double Nr = (a * Math.pow(Tr, b));
		double D = Math.pow(t + c, d);
		
		double i = Nr / D;
		return i;
	}
	
	/* CALCULATE */
	
	public static double calcFundo(int pos, double cotaTerreno, double comprimento, double declividade) {
		double fundo = cotaTerreno - 1.0;
		if(pos != 0)
			fundo = fundo - (comprimento * declividade);
		return fundo;
	}

	public static double calcNivelAgua(double fundo, double alturaAgua) {
		double nivelAgua = fundo + alturaAgua;	
		return nivelAgua;
	}
	
	public static double calcAreaTotal(double areaTotalAnterior, double area) {
		double areaTotal = areaTotalAnterior + area;	
		return areaTotal;
	}
	
	public static double calcCoefDistr(double areaTotal) {
		double coefDistr = Math.pow(areaTotal, -0.15 );
		return coefDistr;
	}

	public static double calcTempoConcentracao(double tempoConcAnterior, double tempoPercurso) {
		double tempoConc = tempoConcAnterior + tempoPercurso;
		return tempoConc;
	}

	public static double calcIndicePluviometrico(int iCodigoLocalMedicao, double coefManning, double tempoConc)
	{
		double dResult = 0.0;
		
		if(iCodigoLocalMedicao == IDFLOCAL_CAMPOGRANDE_VAL) {
			dResult = 891.60 * Math.pow(coefManning, 0.180) / Math.pow(tempoConc + 14.00, 0.689);
		}
		else if(iCodigoLocalMedicao == IDFLOCAL_SANTACRUZ_VAL) {
			dResult = 711.30 * Math.pow(coefManning, 0.186) / Math.pow(tempoConc +  7.00, 0.687);
		}
		else if(iCodigoLocalMedicao == IDFLOCAL_MEDANHA_VAL) {
			dResult = 844.78 * Math.pow(coefManning, 0.177) / Math.pow(tempoConc + 12.00, 0.698);
		}
		else if(iCodigoLocalMedicao == IDFLOCAL_JARDIMBOTANICO_VAL) {
			dResult = 1239.00 * Math.pow(coefManning, 0.150) / Math.pow(tempoConc + 20.00, 0.740);
		}
		else if(iCodigoLocalMedicao == IDFLOCAL_VIA11_VAL) {
			dResult = 1423.00 * Math.pow(coefManning, 0.196) / Math.pow(tempoConc + 14.58, 0.796);
		}
		else if(iCodigoLocalMedicao == IDFLOCAL_IRAJA_VAL) {
			dResult = 5986.27 * Math.pow(coefManning, 0.157) / Math.pow(tempoConc + 29.70, 1.050);
		}

		else if(iCodigoLocalMedicao == IDFLOCAL_ARARUAMA_VAL) {
			dResult = 5986.27 * Math.pow(coefManning, 0.157) / Math.pow(tempoConc + 29.70, 1.050);
		}
		else if(iCodigoLocalMedicao == IDFLOCAL_IRAJA_VAL) {
			dResult = 709.00 * Math.pow(coefManning, 0.104) / Math.pow(tempoConc + 8.00, 0.721);
		}
		else if(iCodigoLocalMedicao == IDFLOCAL_JACAREPAGUA_VAL) {
			dResult = 71.11 / Math.pow((tempoConc / 60.0) + 0.17, 0.721);
		}
		return dResult;
	}
	
	public static double calcCoefDefluv(double coefImper, double tempoConc, double indicePluviometrico) {
		double coefDefluv = 0.0;
		
		if(coefImper == 0.4) {
			coefDefluv = 0.029 * Math.pow(tempoConc * indicePluviometrico, 1/3);
		}
		else if(coefImper == 0.5) {
			coefDefluv = 0.036 * Math.pow(tempoConc * indicePluviometrico, 1/3);
		}
		else if(coefImper == 0.6) {
			coefDefluv = 0.043 * Math.pow(tempoConc * indicePluviometrico, 1/3);
		}
		else if(coefImper == 0.7) {
			coefDefluv = 0.051 * Math.pow(tempoConc * indicePluviometrico, 1/3);
		}
		else if(coefImper == 0.8) {
			coefDefluv = 0.058 * Math.pow(tempoConc * indicePluviometrico, 1/3);
		}
		return coefDefluv;
	}
	
	public static double calcDeflLocal(double area, double coefDistr, double indicePluviometrico, double coefDefluv) {
		double deflLocal = area * coefDistr * indicePluviometrico * coefDefluv * 2.78;
		return deflLocal;
	}
	
	public static double calcDeflEscoar(double deflLocal) {
		double deflEscoar = deflLocal;	
		return deflEscoar;
	}
	
	public static double calcDeclividadeGreide(double cotaTerrenoAnterior, double cotaTerreno, double comprimento) {
		double declividadeGreide = (cotaTerrenoAnterior - cotaTerreno) / comprimento;	
		return declividadeGreide;
	}

	public static double calcAlturaAgua(double alturaAguaAnterior, double velocidade, double dimensoes, double f) {
		double dResult = 0.0;
		
		if(dimensoes > 0) {
			dResult = ((alturaAguaAnterior / 1000.0) / velocidade) / dimensoes;
		}
		else {
			FatorDrenagemVO oF = AguasPluviaisCalc.selectFatorDrenagem(f);
			if(oF == null) {
				dResult = 2.0 * dimensoes;
			}
			else {
				dResult = oF.getF() * dimensoes;
			}
		}
		return dResult;
	}
	
	public static double calcYD(double alturaAgua, double dimensoes) {
		double yd = (alturaAgua / dimensoes) * 100.0;
		return yd;
	}

	public static double calcProfMontJus(double cotaTerreno, double fundo) {
		double profMontJus = cotaTerreno - fundo;
		return profMontJus;
	}

	public static double calcVelocidade(double dimensoes, double coefManning, double deflEscoar, double declividade) {
		double velocidade = 0.0;
		
		if(dimensoes > 0) {
			velocidade = (0.58 / Math.pow(coefManning, 0.75)) * Math.pow((deflEscoar / 1000.0), (1 / 4)) * Math.pow(declividade, ( 3 / 8 ));
		}
		else {
			velocidade = (0.61 / Math.pow(coefManning, 0.75)) * Math.pow((deflEscoar / 1000.0), (1 / 4)) * Math.pow(declividade, ( 3 / 8 ));
		}
		return velocidade;
	}

	public static double calcTempoPercurso(double comprimento, double velocidade) {
		double tempoPercurso = comprimento / (velocidade / 60.0);
		return tempoPercurso;
	}
	
	public static double calcTempoTotal(double tempoTotalAnterior, double tempoPercurso) {
		double tempoTotal = tempoTotalAnterior + tempoPercurso;	
		return tempoTotal;	
	}
	
	public static double calcF(double coefManning, double deflEscoar, double declividade, double dimensoes) {
		double f = (coefManning * deflEscoar / 1000.0) / (Math.sqrt(declividade) * Math.pow(dimensoes, (8 / 3)));
		return f;
	}
	
}
