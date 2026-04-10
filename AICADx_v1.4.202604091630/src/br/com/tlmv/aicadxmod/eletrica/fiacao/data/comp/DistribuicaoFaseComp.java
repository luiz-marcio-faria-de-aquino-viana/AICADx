/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * DistribuicaoFaseComp.java
 * Autor: Luiz Marcio Viana, 25/03/2019
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao.data.comp;

import java.util.Comparator;

import br.com.tlmv.aicadxmod.eletrica.fiacao.data.DistribuicaoFaseData;

public class DistribuicaoFaseComp implements Comparator<DistribuicaoFaseData>
{
	
	public int compare(DistribuicaoFaseData o1, DistribuicaoFaseData o2) 
	{
        if( (o1.getNumeroFases() < o2.getNumeroFases()) || 
        	( (o1.getNumeroFases() == o2.getNumeroFases()) && 
        	  (o1.getPotenciaCircuito() < o2.getPotenciaCircuito()) ) )
            return -1;
        else if( (o1.getNumeroFases() < o2.getNumeroFases()) && 
        		 (o1.getPotenciaCircuito() == o2.getPotenciaCircuito()) )
            return 0;
        else return 1;
	}
	  
}
