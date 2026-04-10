/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * QuadroCircuitoFase.java
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

public class QuadroCircuitoFase 
{
//Private
    private String circuito;
    private String sistemaFase;
    private ArrayList<QuadroTipoCarga> lsTipoCarga;

//Public

    public QuadroCircuitoFase(String circuito, String sistemaFase)
    {
        this.circuito = circuito;
        this.sistemaFase = sistemaFase;
        this.lsTipoCarga = new ArrayList<QuadroTipoCarga>();
    }

    /* Getters/Setters */

    public String getCircuito() {
        return this.circuito;
    }

    public String getSistemaFase() {
        return this.sistemaFase;
    }

    public QuadroTipoCarga getTipoCarga(String tipoCarga)
    {
        for(QuadroTipoCarga o : this.lsTipoCarga) {
            if (o.getTipoCarga() == tipoCarga)
                return o;
        }
        return null;
    }

    public double getPotenciaCircuito()
    {
        double pot = 0.0;

        for(QuadroTipoCarga o1 : this.lsTipoCarga) {
            for(QuadroPotenciaQuantidade o2 : o1.getLsPotenciaQuantidade()) {
                pot = pot + o2.getPotenciaTotal();
            }
        }
        return pot;
    }

    public ArrayList<QuadroTipoCarga> getLsTipoCarga()
    {
        return this.lsTipoCarga;
    }

    public void add(QuadroTipoCarga o)
    {
        this.lsTipoCarga.add(o);
    }

}
