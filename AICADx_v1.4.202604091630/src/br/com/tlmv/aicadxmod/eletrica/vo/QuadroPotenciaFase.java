/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * QuadroPotenciaFase.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 07/01/2026
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

import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public class QuadroPotenciaFase extends ItemDataVO
{
//Private
    private double potenciaQuadro;
    private String sistemaFaseQuadro;
    private ArrayList<QuadroCircuitoFase> lsCircuitoFase;

//Public

    public QuadroPotenciaFase(double potenciaQuadro, String sistemaFaseQuadro)
    {
    	super( Double.toString(potenciaQuadro), sistemaFaseQuadro );
    	
        this.potenciaQuadro = potenciaQuadro;
        this.sistemaFaseQuadro = sistemaFaseQuadro;
        this.lsCircuitoFase = new ArrayList<QuadroCircuitoFase>();
    }

    /* Getters/Setters */

    public double getPotenciaQuadro() {
        return this.potenciaQuadro;
    }

    public void setPotenciaQuadro(double potenciaQuadro) {
    	this.potenciaQuadro = potenciaQuadro;
    }

    public String getSistemaFaseQuadro() {
        return this.sistemaFaseQuadro;
    }

    public void setSistemaFaseQuadro(String sistemaFase) {
        this.sistemaFaseQuadro = sistemaFase;
    }

    public QuadroCircuitoFase getCircuitoFase(String circuito) {
        for(QuadroCircuitoFase o : this.lsCircuitoFase) {
            if (o.getCircuito() == circuito)
                return o;
        }
        return null;
    }

    public ArrayList<QuadroCircuitoFase> getLsCircuitoFase() {
        return this.lsCircuitoFase;
    }

    public void add(QuadroCircuitoFase o)
    {
        this.lsCircuitoFase.add(o);
    }

}
