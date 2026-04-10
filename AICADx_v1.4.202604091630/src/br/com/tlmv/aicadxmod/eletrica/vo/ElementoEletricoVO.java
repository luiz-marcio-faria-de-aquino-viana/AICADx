/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * CondutorProtecaoVO.java
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

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.vo.ItemDataVO;

public class ElementoEletricoVO extends ItemDataVO
{
//Private
    private String hnd;
    private String idx;
    private String tip;
    private String qdr;
    private String org;
    private String des;
    private String cir;
    private String cmd;
    private String fas;
    private double pot;
    private double dem;
    
//Public

    public ElementoEletricoVO(
        String hnd,
        String idx,
        String tip,
        String qdr,
        String org,
        String des,
        String cir,
        String cmd,
        String fas,
        double pot,
        double dem )
    {
    	super( hnd, tip );
    	
        this.hnd = hnd;
        this.idx = idx;
        this.tip = tip;
        this.qdr = qdr;
        this.org = org;
        this.des = des;
        this.cir = cir;
        this.cmd = cmd;
        this.fas = fas;
        this.pot = pot;
        this.dem = dem;
    }

    @Override
    public String toString()
    {
        String result = "Origem: " + this.org;

        if( (this.tip == AppDefs.FIA_S_CARGA) ||
            (this.tip == AppDefs.FIA_S_ILUMINACAO) ) {
            result = "Origem: " + this.org + " - Circuito: " + this.cir + " - Comando: " + this.cmd;
        }
        else if( (this.tip == AppDefs.FIA_S_COMANDO) ||
                 (this.tip == AppDefs.FIA_S_CAMPAINHA) ) {
            result = "Origem: " + this.org + " - Comando: " + this.cmd;
        }
        else if(this.tip == AppDefs.FIA_S_QUADRO) {
            result = "Nome: " + this.qdr + " - Origem: " + this.org + " - Circuito: " + this.cir;
        }
        else if(this.tip == AppDefs.FIA_S_DESVIO) {
            result = "Origem: " + this.org + " - Desvio: " + this.des;
        }
        else if(this.tip == AppDefs.FIA_S_CALHA) {
            result = "Origem: " + this.org;
        }
        else if(this.tip == AppDefs.FIA_S_CAIXA) {
            result = "Origem: " + this.org;
        }
        return result;
    }

    /* Getters/Setters */

    public String getHnd() {
        return this.hnd;
    }

    public void setHnd(String hnd)
    {
        this.hnd = hnd;
    }

    public String getIdx()
    {
        return this.idx;
    }

    public void setIdx(String idx)
    {
        this.idx = idx;
    }

    public String getTip()
    {
        return this.tip;
    }

    public void setTip(String tip)
    {
        this.tip = tip;
    }

    public String getQdr()
    {
        return this.qdr;
    }

    public void setQdr(String qdr)
    {
        this.qdr = qdr;
    }

    public String getOrg()
    {
        return this.org;
    }

    public void setOrg(String org)
    {
        this.org = org;
    }

    public String getDes()
    {
        return this.des;
    }

    public void setDes(String des)
    {
        this.des = des;
    }

    public String getCir()
    {
        return this.cir;
    }

    public void setCir(String cir)
    {
        this.cir = cir;
    }

    public String getCmd()
    {
        return this.cmd;
    }

    public void setCmd(String cmd)
    {
        this.cmd = cmd;
    }

    public String getFas()
    {
        return this.fas;
    }

    public void setFas(String fas)
    {
        this.fas = fas;
    }

    public double getPot()
    {
        return this.pot;
    }

    public void setPot(double pot)
    {
        this.pot = pot;
    }

    public double getDem()
    {
        return this.dem;
    }

    public void setDem(double dem)
    {
        this.dem = dem;
    }

}
