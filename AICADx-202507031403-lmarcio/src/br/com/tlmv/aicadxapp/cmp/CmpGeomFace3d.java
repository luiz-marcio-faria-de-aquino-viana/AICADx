/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmpGeomFace3d.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 04/03/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cmp;

import java.util.Comparator;

import br.com.tlmv.aicadxapp.cad.CadLayerDef;
import br.com.tlmv.aicadxapp.cad.geom.GeomFace3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.vo.NearPoint3dVO;

public class CmpGeomFace3d implements Comparator<GeomFace3d>
{
//Private
	private GeomPoint3d ptBase3d = null;
	private boolean bAsc = true;

	/* Methodes */

	public int compareAsc(GeomFace3d o1, GeomFace3d o2) 
	{
		NearPoint3dVO oNear1 = o1.nearPoint3d(ptBase3d);
		NearPoint3dVO oNear2 = o2.nearPoint3d(ptBase3d);
		
		double dEnt1 = oNear1.getDistEnt();
		double dEnt2 = oNear2.getDistEnt();
		
		double dFace1 = oNear1.getDistFace();
		double dFace2 = oNear2.getDistFace();
		
		if(dEnt2 < dEnt1) {
			return 1;
		}
		else if(dEnt2 > dEnt1) {
			return -1;
		}
		else {
			if(dFace2 < dFace1)
				return 1;
			else if(dFace2 > dFace1)
				return -1;
		}
		return 0;
	}

	public int compareDesc(GeomFace3d o1, GeomFace3d o2) 
	{
		int rscode = - this.compareAsc(o1, o2);
		return rscode;
	}
	
//Public

	public CmpGeomFace3d(GeomPoint3d ptBase3d, boolean bAsc)
	{
		this.ptBase3d = new GeomPoint3d(ptBase3d);
		this.bAsc = bAsc;
	}
	
	/* Methodes */
	
	@Override
	public int compare(GeomFace3d o1, GeomFace3d o2) 
	{
		int rscode = 0;
		
		if( this.bAsc )
			rscode = this.compareAsc(o1, o2);
		else
			rscode = this.compareDesc(o1, o2);
		return rscode;
	}

}
