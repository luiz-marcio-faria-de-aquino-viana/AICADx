/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CsvImportaRedeVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 03/07/2025
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

package br.com.tlmv.aicadxmod.drenagem.vo;

import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.utils.FormatUtil;
import br.com.tlmv.aicadxapp.utils.StringUtil;

public class CsvImportaRedeVO 
{
//Private
	private int id;
	private String secaoTransversal;
	private String pvMont;
	private String pvJus;
	private double coordX1;
	private double coordY1;
	private double coordX2;
	private double coordY2;
	private String entrada;
	private int qtdRalos;
	private double dL;
	private double h;
	private String material;
	private double ctMont;
	private double cfMont;
	private double ctJus;
	private double cfJus;	
	private double profInicial;
	private double profFinal;
	private double profMedia;
	private String lamina;
	private double vel;
	private double comprimento;
	private double declividade;
	private int anoInst;
	private String documento;
	private int status;
	private String wktStr;
	
//Public
	
	public CsvImportaRedeVO()
	{
		this.init(
			AppDefs.NULL_INT,
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			AppDefs.NULL_STR,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_STR,
			AppDefs.NULL_INT,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_STR,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_STR,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_INT,
			AppDefs.NULL_STR,
			AppDefs.NULL_INT,
			AppDefs.NULL_STR);
	}
	
	public CsvImportaRedeVO(
		int id,
		String secaoTransversal,
		String pvMont,
		String pvJus,
		double coordX1,
		double coordY1,
		double coordX2,
		double coordY2,
		String entrada,
		int qtdRalos,
		double dL,
		double H,
		String material,
		double ctMont,
		double cfMont,
		double ctJus,
		double cfJus,
		double profInicial,
		double profFinal,
		double profMedia,
		String lamina,
		double vel,
		double comprimento,
		double declividade,
		int anoInst,
		String documento,
		int status,
		String wktStr)
	{
		this.init(
			id,
			secaoTransversal,
			pvMont,
			pvJus,
			coordX1,
			coordY1,
			coordX2,
			coordY2,
			entrada,
			qtdRalos,
			dL,
			H,
			material,
			ctMont,
			cfMont,
			ctJus,
			cfJus,
			profInicial,
			profFinal,
			profMedia,
			lamina,
			vel,
			comprimento,
			declividade,
			anoInst,
			documento,
			status,
			wktStr);
	}
	
	/* Methodes */
	
	public void init(
		int id,
		String secaoTransversal,
		String pvMont,
		String pvJus,
		double coordX1,
		double coordY1,
		double coordX2,
		double coordY2,
		String entrada,
		int qtdRalos,
		double dL,
		double h,
		String material,
		double ctMont,
		double cfMont,
		double ctJus,
		double cfJus,
		double profInicial,
		double profFinal,
		double profMedia,
		String lamina,
		double vel,
		double comprimento,
		double declividade,
		int anoInst,
		String documento,
		int status,
		String wktStr)
	{
		this.id = id;
		this.secaoTransversal = secaoTransversal;
		this.pvMont = pvMont;
		this.pvJus = pvJus;
		this.coordX1 = coordX1;
		this.coordY1 = coordY1;
		this.coordX2 = coordX2;
		this.coordY2 = coordY2;
		this.entrada = entrada;
		this.qtdRalos = qtdRalos;
		this.dL = dL;
		this.h = h;
		this.material = material;
		this.ctMont = ctMont;
		this.cfMont = cfMont;
		this.ctJus = ctJus;
		this.cfJus = cfJus;
		this.profInicial = profInicial;
		this.profFinal = profFinal;
		this.profMedia = profMedia;
		this.lamina = lamina;
		this.vel = vel;
		this.comprimento = comprimento;
		this.declividade = declividade;
		this.anoInst = anoInst;
		this.documento = documento;
		this.status = status;
		this.wktStr = wktStr;
	}
	
	/* TO/FROM */
		
	//1  2                 3       4      5        6        7        8        9       10        11 12 13       14      15      16     17     18           19         20         21     22  23          24          25       26  27     28
	//ID;SECAO_TRANSVERSAL;PV_MONT;PV_JUS;COORD_X1;COORD_Y1;COORD_X2;COORD_Y2;ENTRADA;Qtd_Ralos;D_L;H;MATERIAL;CT_MONT;CF_MONT;CT_JUS;CF_JUS;PROF_INICIAL;PROF_FINAL;PROF_MEDIA;LAMINA;VEL;COMPRIMENTO;DECLIVIDADE;ANO_INST;DOC;STATUS;WKT	
	//1;CIRCULAR;PV-A1;PV-A2;644086.7737;7456807.9889;644051.5019;7456822.3642;REDE;2;400;;CONCRETO;3,050;2,050;2,864;1,264;1,00;1,60;1,30;0,104;0,837;38,000;0,005;2022;2-3-D-XXXX;;LINESTRING (644086.7737 7456807.9889,644051.5019 7456822.3642)
	//2;CIRCULAR;PV-A2;PV-A3;644051.5019;7456822.3642;644021.8458;7456834.5002;REDE;4;800;;CONCRETO;2,864;1,264;2,964;1,232;1,60;1,73;1,67;0,536;0,904;32,000;0,001;2022;2-3-D-XXXX;;LINESTRING (644051.5019 7456822.3642,644021.8458 7456834.5002)
	//3;CIRCULAR;PV-A3;PV-A4;644021.8458;7456834.5002;643994.0567;7456845.8621;REDE;2;800;;CONCRETO;2,964;1,232;3,026;1,196;1,73;1,83;1,78;0,560;1,005;30,000;0,001;2022;2-3-D-XXXX;;LINESTRING (644021.8458 7456834.5002,643994.0567 7456845.8621)
	//;;
	//
	public static CsvImportaRedeVO fromCsv(String str)
	{
		NumberFormat nfEnUs6 = FormatUtil.newNumberFormatEnUs(6); 
		
		NumberFormat nfPtBr6 = FormatUtil.newNumberFormatPtBr(6); 
		
		CsvImportaRedeVO o = null;
		
		String[] arr = StringUtil.split(str, ';');
		int szArr = arr.length;
		if(szArr >= 28) {
			int n = 0;

			o = new CsvImportaRedeVO();
			o.id = StringUtil.safeInt(arr[n++]);
			o.secaoTransversal = StringUtil.safeStr(arr[n++]);
			o.pvMont = StringUtil.safeStr(arr[n++]);
			o.pvJus = StringUtil.safeStr(arr[n++]);
			o.coordX1 = StringUtil.safeDbl(nfEnUs6, arr[n++]);
			o.coordY1 = StringUtil.safeDbl(nfEnUs6, arr[n++]);
			o.coordX2 = StringUtil.safeDbl(nfEnUs6, arr[n++]);
			o.coordY2 = StringUtil.safeDbl(nfEnUs6, arr[n++]);
			o.entrada = StringUtil.safeStr(arr[n++]);
			o.qtdRalos = StringUtil.safeInt(arr[n++]);
			o.dL = StringUtil.safeDbl(nfPtBr6, arr[n++]);
			o.h = StringUtil.safeDbl(nfPtBr6, arr[n++]);
			o.material = StringUtil.safeStr(arr[n++]);
			o.ctMont = StringUtil.safeDbl(nfPtBr6, arr[n++]);
			o.cfMont = StringUtil.safeDbl(nfPtBr6, arr[n++]);
			o.ctJus = StringUtil.safeDbl(nfPtBr6, arr[n++]);
			o.cfJus = StringUtil.safeDbl(nfPtBr6, arr[n++]);
			o.profInicial = StringUtil.safeDbl(nfPtBr6, arr[n++]);
			o.profFinal = StringUtil.safeDbl(nfPtBr6, arr[n++]);
			o.profMedia = StringUtil.safeDbl(nfPtBr6, arr[n++]);
			o.lamina = StringUtil.safeStr(arr[n++]);
			o.vel = StringUtil.safeDbl(nfPtBr6, arr[n++]);
			o.comprimento = StringUtil.safeDbl(nfPtBr6, arr[n++]);
			o.declividade = StringUtil.safeDbl(nfPtBr6, arr[n++]);			
			o.anoInst = StringUtil.safeInt(arr[n++]);
			o.documento = StringUtil.safeStr(arr[n++]);
			o.status = StringUtil.safeInt(arr[n++]);
			o.wktStr = StringUtil.safeStr(arr[n++]);
		}
		return o;
	}
	
	/* VALID */
	
	public boolean isValid()
	{
		if(this.getId() == 0) return false;
		
		if(Math.abs( this.getCoordX1() ) < AppDefs.MATHPREC_MIN) return false;
		
		if(Math.abs( this.getCoordY1() ) < AppDefs.MATHPREC_MIN) return false;
		
		if( "".equals(this) ) return false;		

		return true;
	}

	/* DEBUG */

	public static String toCsv()
	{
		return null;
	}
	
	public String toStr()
	{
		NumberFormat nf0 = FormatUtil.newNumberFormatEnUs(0);

		NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);

		String str = String.format(
			"id:%s;" +
			"secaoTransversal:%s;" +
			"pvMont:%s;" +
			"pvJus:%s;" +
			"coordX1:%s;" +
			"coordY1:%s;" +
			"coordX2:%s;" +
			"coordY2:%s;" +
			"entrada:%s;" +
			"qtdRalos:%s;" +
			"dL:%s;" +
			"h:%s;" +
			"material:%s;" +
			"ctMont:%s;" +
			"cfMont:%s;" +
			"ctJus:%s;" +
			"cfJus:%s;" +
			"profInicial:%s;" +
			"profFinal:%s;" +
			"profMedia:%s;" +
			"lamina:%s;" +
			"vel:%s;" +
			"comprimento:%s;" +
			"declividade:%s;" +
			"anoInst:%s;" +
			"documento:%s;" +
			"status:%s;" +
			"wktStr:%s;",
			this.id,
			this.secaoTransversal,
			this.pvMont,
			this.pvJus,
			this.coordX1,
			this.coordY1,
			this.coordX2,
			this.coordY2,
			this.entrada,
			this.qtdRalos,
			this.dL,
			this.h,
			this.material,
			this.ctMont,
			this.cfMont,
			this.ctJus,
			this.cfJus,
			this.profInicial,
			this.profFinal,
			this.profMedia,
			this.lamina,
			this.vel,
			this.comprimento,
			this.declividade,
			this.anoInst,
			this.documento,
			this.status,
			this.wktStr);
		return str;
	}
	
	public void debug(int debugLevel)
	{
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;
		
		String warnmsg = this.toStr();
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}	
	
	/* Getters/Setters */
	
	public int getId() {
		return id;
	}
	public double getCoordX1() {
		return coordX1;
	}
	public double getCoordY1() {
		return coordY1;
	}
	public int getAnoInst() {
		return anoInst;
	}
	public String getDocumento() {
		return documento;
	}
	public int getStatus() {
		return status;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setAnoInst(int anoInst) {
		this.anoInst = anoInst;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public String getPvMont() {
		return pvMont;
	}

	public String getPvJus() {
		return pvJus;
	}

	public double getCoordX2() {
		return coordX2;
	}

	public double getCoordY2() {
		return coordY2;
	}

	public String getEntrada() {
		return entrada;
	}

	public double getdL() {
		return dL;
	}

	public double getH() {
		return h;
	}

	public String getMaterial() {
		return material;
	}

	public double getCtMont() {
		return ctMont;
	}

	public double getCfMont() {
		return cfMont;
	}

	public double getCtJus() {
		return ctJus;
	}

	public double getCfJus() {
		return cfJus;
	}

	public double getProfInicial() {
		return profInicial;
	}

	public double getProfFinal() {
		return profFinal;
	}

	public double getProfMedia() {
		return profMedia;
	}

	public String getLamina() {
		return lamina;
	}

	public double getComprimento() {
		return comprimento;
	}

	public double getDeclividade() {
		return declividade;
	}

	public void setPvMont(String pvMont) {
		this.pvMont = pvMont;
	}

	public void setPvJus(String pvJus) {
		this.pvJus = pvJus;
	}

	public void setCoordX1(double coordX1) {
		this.coordX1 = coordX1;
	}

	public void setCoordY1(double coordY1) {
		this.coordY1 = coordY1;
	}

	public void setCoordX2(double coordX2) {
		this.coordX2 = coordX2;
	}

	public void setCoordY2(double coordY2) {
		this.coordY2 = coordY2;
	}

	public void setEntrada(String entrada) {
		this.entrada = entrada;
	}

	public void setdL(double dL) {
		this.dL = dL;
	}

	public void setH(double h) {
		this.h = h;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public void setCtMont(double ctMont) {
		this.ctMont = ctMont;
	}

	public void setCfMont(double cfMont) {
		this.cfMont = cfMont;
	}

	public void setCtJus(double ctJus) {
		this.ctJus = ctJus;
	}

	public void setCfJus(double cfJus) {
		this.cfJus = cfJus;
	}

	public void setProfInicial(double profInicial) {
		this.profInicial = profInicial;
	}

	public void setProfFinal(double profFinal) {
		this.profFinal = profFinal;
	}

	public void setProfMedia(double profMedia) {
		this.profMedia = profMedia;
	}

	public void setLamina(String lamina) {
		this.lamina = lamina;
	}

	public void setComprimento(double comprimento) {
		this.comprimento = comprimento;
	}

	public void setDeclividade(double declividade) {
		this.declividade = declividade;
	}
		
}
