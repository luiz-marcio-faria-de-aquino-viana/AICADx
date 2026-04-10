/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CsvImportaCaixaInspecaoVO.java
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

public class CsvImportaCaixaInspecaoVO 
{
//Private
	private int id;
	private String pv;
	private double coordX1;
	private double coordY1;
	private double cotaTopo;
	private double cotaFundo;
	private double profundidade;
	private double runOff;
	private double areaLocal;
	private double areaTotal;
	private double vazaoPv;
	private double vazaoTotal;
	private int anoInst;
	private String documento;
	private int status;
	
//Public
	
	public CsvImportaCaixaInspecaoVO()
	{
		this.init(
			AppDefs.NULL_INT,
			AppDefs.NULL_STR,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_INT,
			AppDefs.NULL_STR,
			AppDefs.NULL_INT);
	}
	
	public CsvImportaCaixaInspecaoVO(
		int id,
		String pv,
		double coordX1,
		double coordY1,
		double cotaTopo,
		double cotaFundo,
		double profundidade,
		double runOff,
		double areaLocal,
		double areaTotal,
		double vazaoPv,
		double vazaoTotal,
		int anoInst,
		String documento,
		int status)
	{
		this.init(
			id,
			pv,
			coordX1,
			coordY1,
			cotaTopo,
			cotaFundo,
			profundidade,
			runOff,
			areaLocal,
			areaTotal,
			vazaoPv,
			vazaoTotal,
			anoInst,
			documento,
			status);
	}
	
	/* Methodes */
	
	public void init(
		int id,
		String pv,
		double coordX1,
		double coordY1,
		double cotaTopo,
		double cotaFundo,
		double profundidade,
		double runOff,
		double areaLocal,
		double areaTotal,
		double vazaoPv,
		double vazaoTotal,
		int anoInst,
		String documento,
		int status)
	{
		this.id = id;
		this.pv = pv;
		this.coordX1 = coordX1;
		this.coordY1 = coordY1;
		this.cotaTopo = cotaTopo;
		this.cotaFundo = cotaFundo;
		this.profundidade = profundidade;
		this.runOff = runOff;
		this.areaLocal = areaLocal;
		this.areaTotal = areaTotal;
		this.vazaoPv = vazaoPv;
		this.vazaoTotal = vazaoTotal;
		this.anoInst = anoInst;
		this.documento = documento;
		this.status = status;
	}
	
	/* TO/FROM */
		
	//1  2  3        4        5         6          7            8      9          10         11            12          13       14        15
	//﻿ID;PV;COORD_X1;COORD_Y1;COTA_TOPO;COTA_FUNDO;PROFUNDIDADE;RUNOFF;AREA_LOCAL;AREA_TOTAL;VAZAO_Pv_TR10;VAZAO_TOTAL;ANO_INST;Documento;STATUS
	//
	//2;PV-A15;643706.8210;7456963.5688;2,100;0,420;1,680;0,800;0,000;8,976;1984,920;0,012;2022;2-3-D-XXXX;
	//3;PV-A14.1;643708.5529;7456912.6271;2,394;1,594;0,800;0,800;0,066;0,066;19,231;0,005;2022;2-3-D-XXXX;
	//4;PV-A14.2;643719.0045;7456937.6404;2,260;1,460;0,800;0,800;0,026;0,092;26,721;0,007;2022;2-3-D-XXXX;
	//;;
	//
	public static CsvImportaCaixaInspecaoVO fromCsv(String str)
	{
		NumberFormat nfEnUs6 = FormatUtil.newNumberFormatEnUs(6); 
		
		NumberFormat nfPtBr6 = FormatUtil.newNumberFormatPtBr(6); 
		
		CsvImportaCaixaInspecaoVO o = null;
		
		String[] arr = StringUtil.split(str, ';');
		int szArr = arr.length;
		if(szArr >= 17) {
			int n = 0;
			
			o = new CsvImportaCaixaInspecaoVO();
			o.id = StringUtil.safeInt(arr[n++]);
			o.pv = StringUtil.safeStr(arr[n++]);
			o.coordX1 = StringUtil.safeDbl(nfEnUs6, arr[n++]);
			o.coordY1 = StringUtil.safeDbl(nfEnUs6, arr[n++]);
			o.cotaTopo = StringUtil.safeDbl(nfPtBr6, arr[n++]);
			o.cotaFundo = StringUtil.safeDbl(nfPtBr6, arr[n++]);
			o.profundidade = StringUtil.safeDbl(nfPtBr6, arr[n++]);
			o.runOff = StringUtil.safeInt(arr[n++]);
			o.areaLocal = StringUtil.safeDbl(nfPtBr6, arr[n++]);
			o.areaTotal = StringUtil.safeDbl(nfPtBr6, arr[n++]);
			o.vazaoPv = StringUtil.safeDbl(nfPtBr6, arr[n++]);
			o.vazaoTotal = StringUtil.safeDbl(nfPtBr6, arr[n++]);
			o.anoInst = StringUtil.safeInt(arr[n++]);
			o.documento = StringUtil.safeStr(arr[n++]);
			o.status = StringUtil.safeInt(arr[n++]);
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
			"pv:%s;" +
			"coordX1:%s;" +
			"coordY1:%s;" +
			"cotaTopo:%s;" +
			"cotaFundo:%s;" +
			"profundidade:%s;" +
			"runOff:%s;" +
			"areaLocal:%s;" +
			"areaTotal:%s;" +
			"vazaoPv:%s;" +
			"vazaoTotal:%s;" +
			"anoInst:%s;" +
			"documento:%s;" +
			"status:%s;",
			nf0.format(this.id),
			this.pv,
			nf6.format(this.coordX1),
			nf6.format(this.coordY1),
			nf6.format(this.cotaTopo),
			nf6.format(this.cotaFundo),
			nf6.format(this.profundidade),
			nf6.format(this.runOff),
			nf6.format(this.areaLocal),
			nf6.format(this.areaTotal),
			nf6.format(this.vazaoPv),
			nf6.format(this.vazaoTotal),
			nf0.format(this.anoInst),
			this.documento,
			this.status);
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
	public String getPv() {
		return pv;
	}
	public double getCoordX1() {
		return coordX1;
	}
	public double getCoordY1() {
		return coordY1;
	}
	public double getCotaTopo() {
		return cotaTopo;
	}
	public double getCotaFundo() {
		return cotaFundo;
	}
	public double getProfundidade() {
		return profundidade;
	}
	public double getRunOff() {
		return runOff;
	}
	public double getAreaLocal() {
		return areaLocal;
	}
	public double getAreaTotal() {
		return areaTotal;
	}
	public double getVazaoPv() {
		return vazaoPv;
	}
	public double getVazaoTotal() {
		return vazaoTotal;
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
	public void setPv(String pv) {
		this.pv = pv;
	}
	public void setCoordX1(double coordX1) {
		this.coordX1 = coordX1;
	}
	public void setCoordY1(double coordY1) {
		this.coordY1 = coordY1;
	}
	public void setCotaTopo(double cotaTopo) {
		this.cotaTopo = cotaTopo;
	}
	public void setCotaFundo(double cotaFundo) {
		this.cotaFundo = cotaFundo;
	}
	public void setProfundidade(double profundidade) {
		this.profundidade = profundidade;
	}
	public void setRunOff(double runOff) {
		this.runOff = runOff;
	}
	public void setAreaLocal(double areaLocal) {
		this.areaLocal = areaLocal;
	}
	public void setAreaTotal(double areaTotal) {
		this.areaTotal = areaTotal;
	}
	public void setVazaoPv(double vazaoPv) {
		this.vazaoPv = vazaoPv;
	}
	public void setVazaoTotal(double vazaoTotal) {
		this.vazaoTotal = vazaoTotal;
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
		
}
