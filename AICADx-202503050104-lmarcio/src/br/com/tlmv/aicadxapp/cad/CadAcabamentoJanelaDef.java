/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadAcabamentoJanelaDef.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 14/02/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad;

import java.awt.Color;
import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.utils.FormatUtil;

public class CadAcabamentoJanelaDef extends CadAcabamentoDef 
{
//Public
    private String nome;
    private String descricao;
    private int tipo;
    private double espessuraBatente;
    private double larguraGuarnicao;
    private double alturaGuarnicao;
    private double espessuraGuarnicao;
    private Color corGuarnicao; 
    private Color corJanela; 
	
//Public

	public CadAcabamentoJanelaDef(
		String nome,
		String descricao,
	    int tipo,
	    double espessuraBatente,
	    double larguraGuarnicao,
	    double alturaGuarnicao,
	    double espessuraGuarnicao,
	    Color corGuarnicao,
	    Color corJanela,
		CadLayerDef oLayer) 
	{
		super(AppDefs.OBJTYPE_BIMACABAMENTOJANELA_DEF, oLayer);
		
		this.init(
			nome,
			descricao,
		    tipo,
		    espessuraBatente,
		    larguraGuarnicao,
		    alturaGuarnicao,
		    espessuraGuarnicao,
		    corGuarnicao,
		    corJanela); 
	}

	public CadAcabamentoJanelaDef(CadAcabamentoJanelaDef o) 
	{
		super(AppDefs.OBJTYPE_BIMACABAMENTOJANELA_DEF, o.getLayer());
		
		this.init(
			nome,
			descricao,
		    tipo,
		    espessuraBatente,
		    larguraGuarnicao,
		    alturaGuarnicao,
		    espessuraGuarnicao,
		    corGuarnicao,
		    corJanela); 
	}

	/* Methodes */
	
	private void init(
		String nome,
		String descricao,
	    int tipo,
	    double espessuraBatente,
	    double larguraGuarnicao,
	    double alturaGuarnicao,
	    double espessuraGuarnicao,
	    Color corGuarnicao,
	    Color corJanela) 
	{
		this.nome = nome;
		this.descricao = descricao;
		this.tipo = tipo;
	    this.espessuraBatente = espessuraBatente;
	    this.larguraGuarnicao = larguraGuarnicao;
	    this.alturaGuarnicao = alturaGuarnicao;
	    this.espessuraGuarnicao = espessuraGuarnicao;
	    this.corGuarnicao = corGuarnicao;
	    this.corJanela = corJanela; 
	}
	
	/* CREATE */
	
	public static CadAcabamentoJanelaDef create(
		String nome,
		String descricao,
	    int tipo,
	    double espessuraBatente,
	    double larguraGuarnicao,
	    double alturaGuarnicao,
	    double espessuraGuarnicao,
	    Color corGuarnicao,
	    Color corJanela,
		CadLayerDef oLayer) 
	{
		CadAcabamentoJanelaDef o = new CadAcabamentoJanelaDef(
			nome,
			descricao,
		    tipo,
		    espessuraBatente,
		    larguraGuarnicao,
		    alturaGuarnicao,
		    espessuraGuarnicao,
		    corGuarnicao,
		    corJanela,
		    oLayer); 
		return o;
	}
	
	public static CadAcabamentoJanelaDef create(CadAcabamentoJanelaDef o) 
	{
		CadAcabamentoJanelaDef oNew = new CadAcabamentoJanelaDef(o);
		return oNew;
	}
	
	/* DEBUG */
	
	@Override
	public String toStr() {
		NumberFormat nf0 = FormatUtil.newNumberFormatEnUs(0);
		
		float[] arrRGB_guarnicao = this.corGuarnicao.getRGBColorComponents(null);
		float[] arrRGB_janela = this.corJanela.getRGBColorComponents(null);
		
		String str = String.format(
			"Nome:%s;" +
			"descricao:%s;" +
			"tipo:%s;" +
			"espessuraBatente:%s;",
			"larguraGuarnicao:%s;",
			"alturaGuarnicao:%s;",
			"espessuraGuarnicao:%s;",
			"color:rgb(%s,%s,%s);",
			this.nome,
			this.descricao,
			this.tipo,
			this.espessuraBatente,
			this.larguraGuarnicao,
		    this.alturaGuarnicao,
			this.espessuraGuarnicao,
			nf0.format(arrRGB_guarnicao[0]),
			nf0.format(arrRGB_guarnicao[1]),
			nf0.format(arrRGB_guarnicao[2]),
			nf0.format(arrRGB_janela[0]),
			nf0.format(arrRGB_janela[1]),
			nf0.format(arrRGB_janela[2]) );
		return str;
	}
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}

	/* Getters/Setters */
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public double getEspessuraBatente() {
		return espessuraBatente;
	}

	public void setEspessuraBatente(double espessuraBatente) {
		this.espessuraBatente = espessuraBatente;
	}

	public double getEspessuraGuarnicao() {
		return espessuraGuarnicao;
	}

	public void setEspessuraGuarnicao(double espessuraGuarnicao) {
		this.espessuraGuarnicao = espessuraGuarnicao;
	}

	public double getLarguraGuarnicao() {
		return larguraGuarnicao;
	}

	public void setLarguraGuarnicao(double larguraGuarnicao) {
		this.larguraGuarnicao = larguraGuarnicao;
	}

	public double getAlturaGuarnicao() {
		return alturaGuarnicao;
	}

	public void setAlturaGuarnicao(double alturaGuarnicao) {
		this.alturaGuarnicao = alturaGuarnicao;
	}

	public Color getCorGuarnicao() {
		return corGuarnicao;
	}

	public void setCorGuarnicao(Color corGuarnicao) {
		this.corGuarnicao = corGuarnicao;
	}

	public Color getCorJanela() {
		return corJanela;
	}

	public void setCorJanela(Color corJanela) {
		this.corJanela = corJanela;
	}
	
}
