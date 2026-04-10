/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * ExportaFiacaoVO.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 28/09/2025
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

import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.utils.FormatUtil;

public class ExportaFiacaoVO 
{
//Private
    private String hnd;
    private String hnd1;
    private String idx1;
    private String tip1;
    private String qdr1;
    private String org1;
    private String des1;
    private String cir1;
    private String cmd1;
    private String fas1;
    //private double pot1;
    //private double dem1;
    private String hnd2;
    private String idx2;
    private String tip2;
    private String qdr2;
    private String org2;
    private String des2;
    private String cir2;
    private String cmd2;
    private String fas2;
    //private double pot2;
    //private double dem2;

//Public

    public ExportaFiacaoVO(
        String hnd,
        String hnd1,
        String idx1,
        String tip1,
        String qdr1,
        String org1,
        String des1,
        String cir1,
        String cmd1,
        String fas1,
        //double pot1,
        //double dem1,
        String hnd2,
        String idx2,
        String tip2,
        String qdr2,
        String org2,
        String des2,
        String cir2,
        String cmd2,
        String fas2
        //double pot2,
        //double dem2
    )
    {
        this.hnd = hnd;
        this.hnd1 = hnd1;
        this.idx1 = idx1;
        this.tip1 = tip1;
        this.qdr1 = qdr1;
        this.org1 = org1;
        this.des1 = des1;
        this.cir1 = cir1;
        this.cmd1 = cmd1;
        this.fas1 = fas1;
        //this.pot1 = pot1;
        //this.dem1 = dem1;
        this.hnd2 = hnd2;
        this.idx2 = idx2;
        this.tip2 = tip2;
        this.qdr2 = qdr2;
        this.org2 = org2;
        this.des2 = des2;
        this.cir2 = cir2;
        this.cmd2 = cmd2;
        this.fas2 = fas2;
        //this.pot2 = pot2;
        //this.dem2 = dem2;
    }

    /* Methodes */

    public String toStr()
    {
        NumberFormat nf0 = FormatUtil.newNumberFormatEnUs(6);

        NumberFormat nf6 = FormatUtil.newNumberFormatEnUs(6);

        String str = String.format(
        	"%s@" +
        	"%s@%s@%s@%s@%s@%s@%s@%s@%s@" +
        	"%s@%s@%s@%s@%s@%s@%s@%s@%s",
            this.hnd,
            //
            this.hnd1,
            this.idx1,
            this.tip1,
            this.qdr1,
            this.org1,
            this.des1,
            this.cir1,
            this.cmd1,
            this.fas1,
            //
            this.hnd2,
            this.idx2,
            this.tip2,
            this.qdr2,
            this.org2,
            this.des2,
            this.cir2,
            this.cmd2,
            this.fas2
            //this.pot1,
            //this.dem1,
            //this.pot2,
            //this.dem2
        );
        return str;
    }

    /* Getters/Setters */

    public String getHnd()
    {
        return this.hnd;
    }

    public String getHnd1()
    {
        return this.hnd1;
    }

    public String getIdx1()
    {
        return this.idx1;
    }

    public String getTip1()
    {
        return this.tip1;
    }

    public String getQdr1()
    {
        return this.qdr1;
    }

    public String getOrg1()
    {
        return this.org1;
    }

    public String getDes1()
    {
        return this.des1;
    }

    public String getCir1()
    {
        return this.cir1;
    }

    public String getCmd1()
    {
        return this.cmd1;
    }

    public String getFas1()
    {
        return this.fas1;
    }

//    public double getPot1()
//    {
//        return this.pot1;
//    }
//
//    public double getDem1()
//    {
//        return this.dem1;
//    }

    public String getHnd2()
    {
        return this.hnd2;
    }

    public String getIdx2()
    {
        return this.idx2;
    }

    public String getTip2()
    {
        return this.tip2;
    }

    public String getQdr2()
    {
        return this.qdr2;
    }

    public String getOrg2()
    {
        return this.org2;
    }

    public String getDes2()
    {
        return this.des2;
    }

    public String getCir2()
    {
        return this.cir2;
    }

    public String getCmd2()
    {
        return this.cmd2;
    }

    public String getFas2()
    {
        return this.fas2;
    }

//    public double getPot2()
//    {
//        return this.pot2;
//    }
//
//    public double getDem2()
//    {
//        return this.dem2;
//    }
    
}
