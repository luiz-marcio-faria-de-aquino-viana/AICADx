/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadAcabamentoParedeDef.java
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

public class CadAcabamentoParedeDef extends CadAcabamentoDef 
{
//Public
    private String nome;
    private String descricao;
    private int tipo;
    private double largura; 
    private Color color; 
	
//Public

	public CadAcabamentoParedeDef(
		String nome,
		String descricao,
	    int tipo,
	    double largura,
	    Color color,
		CadLayerDef oLayer) 
	{
		super(AppDefs.OBJTYPE_BIMACABAMENTOPAREDE_DEF, oLayer);
		
		this.init(
			nome,
			descricao,
		    tipo,
		    largura,
		    color); 
	}

	public CadAcabamentoParedeDef(CadAcabamentoParedeDef o) 
	{
		super(AppDefs.OBJTYPE_BIMACABAMENTOPAREDE_DEF, o.getLayer());
		
		this.init(o); 
	}

	/* Methodes */
	
	private void init(
		String nome,
		String descricao,
	    int tipo,
	    double largura,
	    Color color) 
	{
		this.nome = nome;
		this.descricao = descricao;
		this.tipo = tipo;
		this.largura = largura;
		this.color = color;
	}

	private void init(CadAcabamentoParedeDef o)
	{
		this.nome = o.nome;
		this.descricao = o.descricao;
		this.tipo = o.tipo;
		this.largura = o.largura;
		this.color = o.color;
	}

	/* CREATE */
	
	public static CadAcabamentoParedeDef create(
		String nome,
		String descricao,
	    int tipo,
	    double largura,
	    Color color,
		CadLayerDef oLayer) 
	{
		CadAcabamentoParedeDef o = new CadAcabamentoParedeDef(
			nome,
			descricao,
		    tipo,
		    largura,
		    color,
		    oLayer); 
		return o;
	}
	
	public static CadAcabamentoParedeDef create(CadAcabamentoParedeDef o)
	{
		CadAcabamentoParedeDef oNew = new CadAcabamentoParedeDef(o);
		return oNew;
	}
	
	/* DEBUG */
	
	@Override
	public String toStr() {
		NumberFormat nf0 = FormatUtil.newNumberFormatEnUs(0);
		
		float[] arrRGB = this.color.getRGBColorComponents(null);
		
		String str = String.format(
			"Nome:%s;" +
			"descricao:%s;" +
			"tipo:%s;" +
			"largura:%s;",
			"color:rgb(%s,%s,%s);",
			this.nome,
			this.descricao,
			this.tipo,
			this.largura,
			nf0.format(arrRGB[0]),
			nf0.format(arrRGB[1]),
			nf0.format(arrRGB[2]) );
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

	public double getLargura() {
		return largura;
	}

	public void setLargura(double largura) {
		this.largura = largura;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
}
