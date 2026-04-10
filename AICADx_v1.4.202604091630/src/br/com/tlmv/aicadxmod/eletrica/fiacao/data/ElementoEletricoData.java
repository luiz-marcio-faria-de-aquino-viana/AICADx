/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * ElementoEletricoData.java
 * Autor: Luiz Marcio Viana, 25/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.data;

import br.com.tlmv.aicadxapp.AppDefs;

public class ElementoEletricoData
{
//Private
    private String m_hnd;
    private String m_idx;
    private String m_tip;
    private String m_qdr;
    private String m_org;
    private String m_des;
    private String m_cir;
    private String m_cmd;
    private String m_fas;
    private Double m_pot;
    private Double m_dem;
    
//Public

    public ElementoEletricoData(
        String hnd,
        String idx,
        String tip,
        String qdr,
        String org,
        String des,
        String cir,
        String cmd,
        String fas,
        Double pot,
        Double dem)
    {
        m_hnd = hnd;
        m_idx = idx;
        m_tip = tip;
        m_qdr = qdr;
        m_org = org;
        m_des = des;
        m_cir = cir;
        m_cmd = cmd;
        m_fas = fas;
        m_pot = pot;
        m_dem = dem;
    }

    public String ToString()
    {
        String result = "Origem: " + this.m_org;

        if( (m_tip == AppDefs.FIA_S_CARGA) ||
            (m_tip == AppDefs.FIA_S_ILUMINACAO) )
        {
            result = "Origem: " + this.m_org + " - Circuito: " + this.m_cir + " - Comando: " + this.m_cmd;
        }
        else if( (m_tip == AppDefs.FIA_S_COMANDO) ||
                 (m_tip == AppDefs.FIA_S_CAMPAINHA) )
        {
            result = "Origem: " + this.m_org + " - Comando: " + this.m_cmd;
        }
        else if(m_tip == AppDefs.FIA_S_QUADRO)
        {
            result = "Nome: " + this.m_qdr + " - Origem: " + this.m_org + " - Circuito: " + this.m_cir;
        }
        else if(m_tip == AppDefs.FIA_S_DESVIO)
        {
            result = "Origem: " + this.m_org + " - Desvio: " + this.m_des;
        }
        else if(m_tip == AppDefs.FIA_S_CALHA)
        {
            result = "Origem: " + this.m_org;
        }
        else if(m_tip == AppDefs.FIA_S_CAIXA)
        {
            result = "Origem: " + this.m_org;
        }

        return result;
    }

    /* Getters/Setters */

    public String getHnd()
    {
        return m_hnd;
    }

    public void setHnd(String hnd)
    {
        m_hnd = hnd;
    }

    public String getIdx()
    {
        return m_idx;
    }

    public void setIdx(String idx)
    {
        m_idx = idx;
    }

    public String getTip()
    {
        return m_tip;
    }

    public void setTip(String tip)
    {
        m_tip = tip;
    }

    public String getQdr()
    {
        return m_qdr;
    }

    public void setQdr(String qdr)
    {
        m_qdr = qdr;
    }

    public String getOrg()
    {
        return m_org;
    }

    public void setOrg(String org)
    {
        m_org = org;
    }

    public String getDes()
    {
        return m_des;
    }

    public void setDes(String des)
    {
        m_des = des;
    }

    public String getCir()
    {
        return m_cir;
    }

    public void setCir(String cir)
    {
        m_cir = cir;
    }

    public String getCmd()
    {
        return m_cmd;
    }

    public void setCmd(String cmd)
    {
        m_cmd = cmd;
    }

    public String getFas()
    {
        return m_fas;
    }

    public void setFas(String fas)
    {
        m_fas = fas;
    }

    public Double getPot()
    {
        return m_pot;
    }

    public void setPot(Double pot)
    {
        m_pot = pot;
    }

    public Double getDem()
    {
        return m_dem;
    }

    public void setDem(Double dem)
    {
        m_dem = dem;
    }

}
