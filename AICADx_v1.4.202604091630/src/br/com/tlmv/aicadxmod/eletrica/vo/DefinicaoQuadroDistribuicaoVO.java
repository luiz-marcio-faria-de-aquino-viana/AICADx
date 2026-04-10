/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * DefinicaoQuadroDistribuicaoVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/10/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */
package br.com.tlmv.aicadxmod.eletrica.vo;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public class DefinicaoQuadroDistribuicaoVO extends ItemDataVO
{
//Private
    private double tensaoFase = 0.0;
    private double bitolaMinimaCondutor = 0.0;
    private double temperatura = 0.0;
    private double fatorReducao = 0.0;

//Public
    
    public DefinicaoQuadroDistribuicaoVO() {
		super( Double.toString( AppDefs.NULL_DBL ), Double.toString( AppDefs.NULL_DBL ) );
		
    	this.init(
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL,
			AppDefs.NULL_DBL );
    }
		
	public DefinicaoQuadroDistribuicaoVO(
	    double tensaoFase,
	    double bitolaMinimaCondutor,
	    double temperatura,
	    double fatorReducao )
	{
		super( Double.toString( tensaoFase ), Double.toString( bitolaMinimaCondutor ) );
		
    	this.init(
		    tensaoFase,
		    bitolaMinimaCondutor,
		    temperatura,
		    fatorReducao );
	}

    public DefinicaoQuadroDistribuicaoVO(DefinicaoQuadroDistribuicaoVO other)
    {
		super( Double.toString( other.tensaoFase ), Double.toString( other.bitolaMinimaCondutor ) );

		this.init(other);
    }

	/* Methodes */
		
	public void init(
	    double tensaoFase,
	    double bitolaMinimaCondutor,
	    double temperatura,
	    double fatorReducao )
	{
		this.tensaoFase = tensaoFase;
		this.bitolaMinimaCondutor = bitolaMinimaCondutor;
		this.temperatura = temperatura;
		this.fatorReducao = fatorReducao;
	}
	
    public void init(DefinicaoQuadroDistribuicaoVO other)
    {
    	this.init(
			other.tensaoFase,
			other.bitolaMinimaCondutor,
			other.temperatura,
			other.fatorReducao );
    }
	
    /* DEBUG */
    
	public String toStr() {
		String str = String.format(
			"tensaoFase:%s;" +
			"bitolaMinimaCondutor:%s;" +
			"temperatura:%s;" +
			"fatorReducao:%s;",
			this.tensaoFase,
			this.bitolaMinimaCondutor,
			this.temperatura,
			this.fatorReducao );
		return str;
	}

	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;
		
		String str = this.toStr();
		AppError.showCmdWarn(debugLevel, str, this.getClass());
	}
    
	/* Getters/Setters */

	public double getTensaoFase() {
		return tensaoFase;
	}

	public void setTensaoFase(double tensaoFase) {
		this.tensaoFase = tensaoFase;
	}

	public double getBitolaMinimaCondutor() {
		return bitolaMinimaCondutor;
	}

	public void setBitolaMinimaCondutor(double bitolaMinimaCondutor) {
		this.bitolaMinimaCondutor = bitolaMinimaCondutor;
	}

	public double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(double temperatura) {
		this.temperatura = temperatura;
	}

	public double getFatorReducao() {
		return fatorReducao;
	}

	public void setFatorReducao(double fatorReducao) {
		this.fatorReducao = fatorReducao;
	}

}
