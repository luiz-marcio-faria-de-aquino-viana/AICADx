/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * FiacaoUtil.java
 * Autor: Luiz Marcio Viana, 13/02/2018
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxmod.eletrica.cad.CadImportaFiacaoEletricaOData;
import br.com.tlmv.aicadxmod.eletrica.fiacao.data.CondutorProtecaoData;
import br.com.tlmv.aicadxmod.eletrica.fiacao.vo.EleFiacaoEletroduto;
import br.com.tlmv.aicadxmod.eletrica.vo.ExportaFiacaoVO;

public class FiacaoUtil 
{
//Private
    private static char[] delimiter = { '(', ')', ' ' };

    private static long seqProcessaFiacaoId = -1L;

//Public
    
    public static long getNextSeqProcessaFiacaoId()
    {
    	Date dataHoraAtual = new Date();
    	
        seqProcessaFiacaoId = dataHoraAtual.getTime();
        return seqProcessaFiacaoId;
    }

    public static long getCurrSeqProcessaFiacaoId()
    {
        return seqProcessaFiacaoId;
    }
	
	public static double POL = 25.4;
	
	/* FIACAO */
	
    /* definicao das constantes que identificam a fiacao
    /* pertencente ao circuito que passa pelo eletroduto
    */
    public static int FIA_TR = 0x0001;      // terra
    public static int FIA_N = 0x0002;       // neutro
    public static int FIA_F1 = 0x0004;      // fase (1)
    public static int FIA_F2 = 0x0008;      // fase (2)
    public static int FIA_F3 = 0x0010;      // fase (3)
    public static int FIA_RC = 0x0020;      // retorno campainha
    public static int FIA_R1 = 0x0040;      // retorno (1)
    public static int FIA_R2 = 0x0080;      // retorno (2)
    public static int FIA_R3 = 0x0100;      // retorno (3)
    public static int FIA_R4 = 0x0200;      // retorno (4)
    public static int FIA_R5 = 0x0400;      // retorno (5)
    public static int FIA_R6 = 0x0800;      // retorno (6)
    public static int FIA_R7 = 0x1000;      // retorno (7)
    public static int FIA_R8 = 0x2000;      // retorno (8)
    public static int FIA_R9 = 0x4000;      // retorno (9)
    public static int FIA_R10 = 0x8000;     // retorno (10)

    // getQuantidadeCondutor(): funcao que obtem a quantidade de condutor 
    // int tipoCondutor - tipo do condutor
    public static int getQuantidadeCondutor(int tipoCondutor)
    {
    	int n = 0;

        //if((tipoCondutor & FIA_TR) == FIA_TR)
        //	n += 1;
        //if((tipoCondutor & FIA_N) == FIA_N)
        //	n += 1;
        if((tipoCondutor & FIA_F1) == FIA_F1)
        	n += 1;
        if((tipoCondutor & FIA_F2) == FIA_F2)
        	n += 1;
        if((tipoCondutor & FIA_F3) == FIA_F3)
        	n += 1;
        if((tipoCondutor & FIA_RC) == FIA_RC)
        	n += 1;
        if((tipoCondutor & FIA_R1) == FIA_R1)
        	n += 1;
        if((tipoCondutor & FIA_R2) == FIA_R2)
        	n += 1;
        if((tipoCondutor & FIA_R3) == FIA_R3)
        	n += 1;
        if((tipoCondutor & FIA_R4) == FIA_R4)
        	n += 1;
        if((tipoCondutor & FIA_R5) == FIA_R5)
        	n += 1;
        if((tipoCondutor & FIA_R6) == FIA_R6)
        	n += 1;
        if((tipoCondutor & FIA_R7) == FIA_R7)
        	n += 1;
        if((tipoCondutor & FIA_R8) == FIA_R8)
        	n += 1;
        if((tipoCondutor & FIA_R9) == FIA_R9)
        	n += 1;
        if((tipoCondutor & FIA_R10) == FIA_R10)
        	n += 1;
        
        return n;
    }

    // getQuantidadeCondutorNeutro(): funcao que obtem a quantidade de condutor neutro 
    // int tipoCondutor - tipo do condutor
    public static int getQuantidadeCondutorNeutro(int tipoCondutor)
    {
    	int n = 0;

        //if((tipoCondutor & FIA_TR) == FIA_TR)
        //	n += 1;
        if((tipoCondutor & FIA_N) == FIA_N)
        	n += 1;
        //if((tipoCondutor & FIA_F1) == FIA_F1)
        //	n += 1;
        //if((tipoCondutor & FIA_F2) == FIA_F2)
        //	n += 1;
        //if((tipoCondutor & FIA_F3) == FIA_F3)
        //	n += 1;
        //if((tipoCondutor & FIA_RC) == FIA_RC)
        //	n += 1;
        //if((tipoCondutor & FIA_R1) == FIA_R1)
        //	n += 1;
        //if((tipoCondutor & FIA_R2) == FIA_R2)
        //	n += 1;
        //if((tipoCondutor & FIA_R3) == FIA_R3)
        //	n += 1;
        //if((tipoCondutor & FIA_R4) == FIA_R4)
        //	n += 1;
        //if((tipoCondutor & FIA_R5) == FIA_R5)
        //	n += 1;
        //if((tipoCondutor & FIA_R6) == FIA_R6)
        //	n += 1;
        //if((tipoCondutor & FIA_R7) == FIA_R7)
        //	n += 1;
        //if((tipoCondutor & FIA_R8) == FIA_R8)
        //	n += 1;
        //if((tipoCondutor & FIA_R9) == FIA_R9)
        //	n += 1;
        //if((tipoCondutor & FIA_R10) == FIA_R10)
        //	n += 1;
        
        return n;
    }
    
	// tabela de bitola do condutor de protecao
    public static CondutorProtecaoData[] TPR = {
        new CondutorProtecaoData( 35.0,  25.0),
        new CondutorProtecaoData( 50.0,  25.0),
        new CondutorProtecaoData( 70.0,  35.0),
        new CondutorProtecaoData( 95.0,  50.0),
        new CondutorProtecaoData(120.0,  70.0),
        new CondutorProtecaoData(150.0,  70.0),
        new CondutorProtecaoData(185.0,  95.0),
        new CondutorProtecaoData(240.0, 120.0) };

    public static double BITOLA_MAX_CONDUTOR_PROTECAO = 150.0;

    // getBitolaCondutorProtecao(): funcao que calcula a bitola do condutor de protecao
    // btq - bitola do conduto fase
    public static double getBitolaCondutorProtecaoOLD(double btq)
    {
        if(btq <= 25.0) return btq;

        for(CondutorProtecaoData o : TPR)
        {
            if (btq <= o.getBitolaCondutorFase())
                return o.getBitolaCondutorProtecao();
        }

        return BITOLA_MAX_CONDUTOR_PROTECAO;
    }	
    
	// tabela de bitola do condutor neutro
	public static CondutorProtecaoData[] TPN = {
	    new CondutorProtecaoData( 35.0,  25.0),
	    new CondutorProtecaoData( 50.0,  25.0),
	    new CondutorProtecaoData( 70.0,  35.0),
	    new CondutorProtecaoData( 95.0,  50.0),
	    new CondutorProtecaoData(120.0,  70.0),
	    new CondutorProtecaoData(150.0,  70.0),
	    new CondutorProtecaoData(185.0,  95.0),
	    new CondutorProtecaoData(240.0, 120.0) };
	
	public static double BITOLA_MAX_CONDUTOR_NEUTRO = 150.0;
	
	// getBitolaCondutorProtecao(): funcao que calcula a bitola do condutor de protecao
	// btq - bitola do conduto fase
	public static double getBitolaCondutorNeutroOLD(double btq)
	{
	    if(btq <= 25.0) return btq;
	
	    for(CondutorProtecaoData o : TPN)
	    {
	        if (btq <= o.getBitolaCondutorFase())
	            return o.getBitolaCondutorProtecao();
	    }
	
	    return BITOLA_MAX_CONDUTOR_NEUTRO;
	}	
	
	// tabela de bitola eletroduto
    public static double[] TBT = {
    	//0.5 * POL,
    	0.75 * POL,
    	1.00 * POL,
    	1.25 * POL,
    	1.50 * POL,
    	2.00 * POL,
    	2.50 * POL,
    	3.00 * POL,
    	4.00 * POL,
    	5.00 * POL,
    	6.00 * POL
    };

    // getBitolaEletroduto(): funcao que calcula a bitola do eletroduto 
    // areaOcupada - area ocupada do eletroduto
    public static double getBitolaEletroduto(double areaOcupada, double taxaOcupacao)
    {
    	//double areaMinEletroduto = areaOcupada * 3.0 / 2.0;
    	double areaMinEletroduto = areaOcupada / taxaOcupacao;
    	
    	for(double bt : TBT)
    	{
    		double areaEletroduto = Math.PI * Math.pow((bt / 2.0), 2.0);
    		
    		if(areaEletroduto > areaMinEletroduto)
    			return bt;
    	}
    	return TBT[TBT.length - 1];
    }
    
    public static int obtemCodigoSistemaFase(String sistemaFase)
    {
        if(sistemaFase == AppDefs.FIA_S_FN) 
            return AppDefs.FIA_FN;
        else if(sistemaFase == AppDefs.FIA_S_2F)
            return AppDefs.FIA_2F;
        else if(sistemaFase == AppDefs.FIA_S_2FN)
            return AppDefs.FIA_2FN;
        else if(sistemaFase == AppDefs.FIA_S_3F)
            return AppDefs.FIA_3F;
        else if(sistemaFase == AppDefs.FIA_S_3FN)
            return AppDefs.FIA_3FN;
        else if(sistemaFase == AppDefs.FIA_S_FNT)
            return AppDefs.FIA_FNT;
        else if(sistemaFase == AppDefs.FIA_S_2FT)
            return AppDefs.FIA_2FT;
        else if(sistemaFase == AppDefs.FIA_S_2FNT)
            return AppDefs.FIA_2FNT;
        else if(sistemaFase == AppDefs.FIA_S_3FT)
            return AppDefs.FIA_3FT;
        else if(sistemaFase == AppDefs.FIA_S_3FNT)
            return AppDefs.FIA_3FNT;
        return -1;
    }

    public static int obtemCodigoTipoElemento(String tipoElemento)
    {
        if (tipoElemento == AppDefs.FIA_S_CARGA)
            return AppDefs.FIA_CARGA;
        else if (tipoElemento == AppDefs.FIA_S_COMANDO)
            return AppDefs.FIA_COMANDO;
        else if (tipoElemento == AppDefs.FIA_S_QUADRO)
            return AppDefs.FIA_QUADRO;
        else if (tipoElemento == AppDefs.FIA_S_CAMPAINHA)
            return AppDefs.FIA_CAMPAINHA;
        else if (tipoElemento == AppDefs.FIA_S_ILUMINACAO)
            return AppDefs.FIA_ILUMINACAO;
        else if (tipoElemento == AppDefs.FIA_S_CAIXA)
            return AppDefs.FIA_CAIXA;
        else if (tipoElemento == AppDefs.FIA_S_DESVIO)
            return AppDefs.FIA_DESVIO;
        else if (tipoElemento == AppDefs.FIA_S_CALHA)
            return AppDefs.FIA_CALHA;
        return -1;
    }

    public static void publicaFiacao(String fileName, ArrayList<ExportaFiacaoVO> lsExportaFiacao)
    {
//        AppWS ws = AppMain.getAppMain().getAppWS();
//
//        EleIdentificacaoModelo oIdentificacaoModelo = ws.obtemIdentificacaoModeloPeloNomeDisco(fileName);
//        if (oIdentificacaoModelo == null)
//        {
//            oIdentificacaoModelo = ws.atualizaIdentificacaoModelo(
//                -1,
//                fileName);
//        }
//
//        if (oIdentificacaoModelo != null)
//        {
//            ws.removeFiacao(oIdentificacaoModelo.getIdentificacaoModeloId());
//
//            for(ExportaFiacaoVO o : lsExportaFiacao)
//            {
//                ws.atualizaFiacao(
//                    -1,
//                    oIdentificacaoModelo.getIdentificacaoModeloId(),
//                    o.getHnd(),
//                    o.getHnd1(),
//                    o.getIdx1(),
//                    o.getTip1(),
//                    o.getQdr1(),
//                    o.getOrg1(),
//                    o.getDes1(),
//                    o.getCir1(),
//                    o.getCmd1(),
//                    o.getFas1(),
//                    o.getPot1(),
//                    o.getDem1(),
//                    o.getHnd2(),
//                    o.getIdx2(),
//                    o.getTip2(),
//                    o.getQdr2(),
//                    o.getOrg2(),
//                    o.getDes2(),
//                    o.getCir2(),
//                    o.getCmd2(),
//                    o.getFas2(),
//                    o.getPot2(),
//                    o.getDem2());
//            }
//        }
    }

    public static void publicaFiacaoFio(String fileName, ArrayList<CadImportaFiacaoEletricaOData> lsImportaFiacao)
    {
//        AppWS ws = AppMain.getAppMain().getAppWS();
//
//        EleIdentificacaoModelo oIdentificacaoModelo = ws.obtemIdentificacaoModeloPeloNomeDisco(fileName);
//        if (oIdentificacaoModelo == null)
//        {
//            oIdentificacaoModelo = ws.atualizaIdentificacaoModelo(
//                -1,
//                fileName);
//        }
//
//        if (oIdentificacaoModelo != null)
//        {
//            ws.removeFiacaoFio(oIdentificacaoModelo.getIdentificacaoModeloId());
//
//            for(ImportaFiacaoVO o : lsImportaFiacao)
//            {
//                for(ImportaFiacaoEletrodutoVO fia : o.getLsFia())
//                {
//                    ws.atualizaFiacaoFio(
//                        -1,
//                        oIdentificacaoModelo.getIdentificacaoModeloId(),
//                        o.getHnd(),
//                        fia.getQdr(),
//                        fia.getCir(),
//                        fia.getFia(),
//                        0.0);
//                }
//            }
//        }
    }

    public static int exportaFiacao(String fileName, ArrayList<ExportaFiacaoVO> lsExportaFiacao)
    {
		int rscode = AppDefs.RSERR;
		
		BufferedWriter fout = null;		
		try {
			fout = new BufferedWriter(new FileWriter(fileName));
			for(ExportaFiacaoVO expFiacao : lsExportaFiacao) {
				String strData = expFiacao.toStr();
				fout.write(strData);
				fout.write('\n');
			}
			rscode = AppDefs.RSOK;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(fout != null) fout.close();				
			}
			catch(Exception e1) { }
		}
		return rscode;
    }

    public static boolean processaFiacao(String exeFile, String srcFile, String dstFile)
    {
//        String args = String.format(AppDefs.CMD_FIACAO, srcFile, dstFile);
//        int timeout = 60000;  // timeout = 1 minute
//
//        ProcessStartInfo procInfo = new ProcessStartInfo(exeFile, args);
//        procInfo.WindowStyle = ProcessWindowStyle.Minimized;
//        
//        Process proc = Process.Start(procInfo);
//        return proc.WaitForExit(timeout);
    	return false;
    }

    public static ArrayList<CadImportaFiacaoEletricaOData> importaFiacao(String fileName)
    {
        ArrayList<CadImportaFiacaoEletricaOData> lsFia = new ArrayList<CadImportaFiacaoEletricaOData>();

//        StreamReader fis = new StreamReader(fileName);
//        String sbuf = null;
//
//        while((sbuf = fis.ReadLine()) != null) {
//            String[] arr = sbuf.split(delimiter);
//
//            ArrayList<String> lsArr = new ArrayList<String>();
//            for(String str : arr)
//            {
//                if(str.length() > 0)
//                    lsArr.add(str);
//            }
//
//            String hnd = lsArr[0].Substring(1, lsArr[0].length() - 2);
//            
//            ImportaFiacaoVO o = new ImportaFiacaoVO(hnd);
//            for(int i = 1; i < lsArr.size(); i += 4)
//            {
//                String qdr = lsArr[i].Substring(1, lsArr[i].length() - 2);
//
//                String cir = lsArr[i + 1].Substring(1, lsArr[i + 1].length() - 2);
//
//                String lbl = "";
//                if(lsArr[i + 2].length() > 2)
//                    lbl = lsArr[i + 2].Substring(1, lsArr[i + 2].Length - 2);
//                
//                int fas = StringUtil.safeInt(lsArr[i + 3]);
//
//                ImportaFiacaoEletrodutoVO fia = new ImportaFiacaoEletrodutoVO(qdr, cir, lbl, fas);
//                o.addFia(fia);
//            }
//            lsFia.add(o);
//        }
//        fis.Close();

        return lsFia;
    }

    public static ArrayList<EleFiacaoEletroduto> processaDimensionaEletroduto(String fileName)
    {
        ArrayList<EleFiacaoEletroduto> lsResult = null;

//        AppWS ws = AppMain.getAppMain().getAppWS();
//
//        EleIdentificacaoModelo oIdentificacaoModelo = ws.obtemIdentificacaoModeloPeloNomeDisco(fileName);
//        if (oIdentificacaoModelo == null)
//        {
//            oIdentificacaoModelo = ws.atualizaIdentificacaoModelo(
//                -1,
//                fileName);
//        }
//
//        if (oIdentificacaoModelo != null)
//            lsResult = ws.processaDimensionaEletroduto(oIdentificacaoModelo.getIdentificacaoModeloId());

        return lsResult;
    }
		
}
