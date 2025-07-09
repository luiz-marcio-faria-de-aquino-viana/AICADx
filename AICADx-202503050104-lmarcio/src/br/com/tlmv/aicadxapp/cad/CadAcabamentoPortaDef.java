/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadAcabamentoPortaDef.java
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

public class CadAcabamentoPortaDef extends CadAcabamentoDef 
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
    private Color corPorta; 
	
//Public

	public CadAcabamentoPortaDef(
		String nome,
		String descricao,
	    int tipo,
	    double espessuraBatente,
	    double larguraGuarnicao,
	    double alturaGuarnicao,
	    double espessuraGuarnicao,
	    Color corGuarnicao,
	    Color corPorta,
	    CadLayerDef oLayer) 
	{
		super(AppDefs.OBJTYPE_BIMACABAMENTOPORTA_DEF, oLayer);
		
		this.init(
			nome,
			descricao,
		    tipo,
		    espessuraBatente,
		    larguraGuarnicao,
		    alturaGuarnicao,
		    espessuraGuarnicao,
		    corGuarnicao,
		    corPorta); 
	}

	public CadAcabamentoPortaDef(CadAcabamentoPortaDef o) 
	{
		super(AppDefs.OBJTYPE_BIMACABAMENTOPORTA_DEF, o.getLayer());
		
		this.init(o);
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
	    Color corPorta) 
	{
		this.nome = nome;
		this.descricao = descricao;
		this.tipo = tipo;
	    this.espessuraBatente = espessuraBatente;
	    this.larguraGuarnicao = larguraGuarnicao;
	    this.alturaGuarnicao = alturaGuarnicao;
	    this.espessuraGuarnicao = espessuraGuarnicao;
		this.corGuarnicao = corGuarnicao;
		this.corPorta = corPorta;
	}
	
	private void init(CadAcabamentoPortaDef o)
	{
		this.nome = o.nome;
		this.descricao = o.descricao;
		this.tipo = o.tipo;
	    this.espessuraBatente = o.espessuraBatente;
	    this.larguraGuarnicao = o.larguraGuarnicao;
	    this.alturaGuarnicao = o.alturaGuarnicao;
	    this.espessuraGuarnicao = o.espessuraGuarnicao;
		this.corGuarnicao = o.corGuarnicao;
		this.corPorta = o.corPorta;
	}
	
	/* CREATE */
	
	public static CadAcabamentoPortaDef create(
		String nome,
		String descricao,
	    int tipo,
	    double espessuraBatente,
	    double larguraGuarnicao,
	    double alturaGuarnicao,
	    double espessuraGuarnicao,
	    Color corGuarnicao,
	    Color corPorta,
	    CadLayerDef oLayer) 
	{
		CadAcabamentoPortaDef o = new CadAcabamentoPortaDef(
			nome,
			descricao,
		    tipo,
		    espessuraBatente,
		    larguraGuarnicao,
		    alturaGuarnicao,
		    espessuraGuarnicao,
		    corGuarnicao,
		    corPorta,
		    oLayer); 
		return o;
	}
	
	public static CadAcabamentoPortaDef create(CadAcabamentoPortaDef o)
	{
		CadAcabamentoPortaDef oNew = new CadAcabamentoPortaDef(o); 
		return oNew;
	}
	
	/* DEBUG */
	
	@Override
	public String toStr() {
		NumberFormat nf0 = FormatUtil.newNumberFormatEnUs(0);
		
		float[] arrRGB_guarnicao = this.corGuarnicao.getRGBColorComponents(null);
		float[] arrRGB_porta = this.corPorta.getRGBColorComponents(null);
		
		String str = String.format(
			"Nome:%s;" +
			"descricao:%s;" +
			"tipo:%s;" +
			"espessuraBatente:%s;",
			"larguraGuarnicao:%s;",
			"alturaGuarnicao:%s;",
			"espessuraGuarnicao:%s;",
			"corGuarnicao:rgb(%s,%s,%s);",
			"colorPorta:rgb(%s,%s,%s);",
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
			nf0.format(arrRGB_porta[0]),
			nf0.format(arrRGB_porta[1]),
			nf0.format(arrRGB_porta[2]) );
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

	public Color getCorPorta() {
		return corPorta;
	}

	public void setCorPorta(Color corPorta) {
		this.corPorta = corPorta;
	}
	
}
