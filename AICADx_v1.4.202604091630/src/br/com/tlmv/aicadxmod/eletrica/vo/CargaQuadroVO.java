/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CargaQuadroVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 30/11/2025
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

package br.com.tlmv.aicadxmod.eletrica.vo;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxmod.eletrica.cad.CadParamEletricoOData;
import br.com.tlmv.aicadxmod.eletrica.cad.CadPontoEletrica;

public class CargaQuadroVO 
{
//Private
    private String nomeQuadro;
    //
    private ArrayList<CadEntity> lsQuadro;
    private ArrayList<CadEntity> lsPontoEletrico;

//Public

    public CargaQuadroVO(String nomeQuadro)
    {
        this.init(nomeQuadro);
    }
    
    /* Methodes */

    public void init(String nomeQuadro)
    {
    	this.nomeQuadro = nomeQuadro;
    	//
    	this.lsQuadro = new ArrayList<CadEntity>();
        this.lsPontoEletrico = new ArrayList<CadEntity>();
    }

    public void add(CadEntity ent1) 
    {
		CadPontoEletrica oEnt1 = (CadPontoEletrica)ent1; 

		ArrayList<CadParamEletricoOData> oLsParam1 = oEnt1.getLsParamEletrico();
        int szLsParam1 = oLsParam1.size();
        if(szLsParam1 > 0) {
        	CadParamEletricoOData oParam1 = oLsParam1.get(0);
        	String strTip1 = oParam1.getTipo();
		
        	if( AppDefs.FIA_S_QUADRO.equals( strTip1 ) ) {
            	this.lsQuadro.add(ent1);            	
        	}
        	else {
            	this.lsPontoEletrico.add(ent1);            	
        	}
        }
    }

    /* DEBUG */
    
    @Override
	public String toString() {
    	String str = String.format("%s", this.nomeQuadro);    	
		return str;
	}

    /* Getters/Setters */
    
	public String getNomeQuadro() {
		return nomeQuadro;
	}

	public void setNomeQuadro(String nomeQuadro) {
		this.nomeQuadro = nomeQuadro;
	}

	public ArrayList<CadEntity> getLsQuadro() {
		return lsQuadro;
	}

	public void setLsQuadro(ArrayList<CadEntity> lsQuadro) {
		this.lsQuadro = lsQuadro;
	}

	public ArrayList<CadEntity> getLsPontoEletrico() {
		return lsPontoEletrico;
	}

	public void setLsPontoEletrico(ArrayList<CadEntity> lsPontoEletrico) {
		this.lsPontoEletrico = lsPontoEletrico;
	}
	
}
