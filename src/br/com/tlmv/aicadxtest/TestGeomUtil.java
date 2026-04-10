/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * TestGeomUtil.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 22/05/2025
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

package br.com.tlmv.aicadxtest;

import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.cad.geom.GeomVector3d;
import br.com.tlmv.aicadxapp.cad.utils.GeomUtil;

public class TestGeomUtil 
{
//Private Static
	private static TestGeomUtil gApp = null;

//Private
	
	GeomPoint3d oPt3d0 = new GeomPoint3d(2.0, 14.0,  6.0);
	GeomPoint3d oPt3d1 = new GeomPoint3d(3.0, 12.0,  4.0);
	

	private void executeTest() {
		//PONTOS
		//
		GeomPoint3d[] arrPt3dMcs = {
			new GeomPoint3d(7.5, -2.5, 0.0)
			//new GeomPoint3d( 5.0, 2.5, 5.0) 
			//new GeomPoint3d( 0.0, 0.0, 10.0), 
			//new GeomPoint3d( 1.0, 0.0, 10.0), 
			//new GeomPoint3d( 5.0, 0.0, 10.0), 
			//new GeomPoint3d(10.0, 0.0, 10.0), 
			//new GeomPoint3d(10.0, 0.0,  5.0), 
			//new GeomPoint3d(10.0, 0.0,  1.0),
			//new GeomPoint3d(10.0, 0.0,  0.0) 
		};
		int szPt3dMcs = arrPt3dMcs.length;
		
		//EIXOS_ROTACAO
		//
		GeomVector3d[] arrVRef3dMcs = {
			new GeomVector3d(2.5, 2.5, 0.0, 7.5, -2.5,  5.0)
			//new GeomVector3d(2.5, 2.5, 0.0, 5.0, 2.5,  5.0),
			//new GeomVector3d(2.5, 2.5, 0.0, 7.5, 2.5,  5.0) 
			//new GeomVector3d(0.0, 0.0, 0.0, 10.0, 0.0, 10.0), 
			//new GeomVector3d(0.0, 0.0, 0.0, 10.0, 0.0,  5.0), 
			//new GeomVector3d(0.0, 0.0, 0.0, 10.0, 0.0,  1.0),
			//new GeomVector3d(0.0, 0.0, 0.0, 10.0, 0.0,  0.0),
			//
			//new GeomVector3d(0.0, 0.0, 0.0, 10.0, 0.0,  0.0), 
			//new GeomVector3d(0.0, 0.0, 0.0, 10.0, 0.0,  1.0) 
			//new GeomVector3d(0.0, 0.0, 0.0, 10.0, 0.0,  5.0), 
			//new GeomVector3d(0.0, 0.0, 0.0, 10.0, 0.0, 10.0), 
			//new GeomVector3d(0.0, 0.0, 0.0,  5.0, 0.0, 10.0), 
			//new GeomVector3d(0.0, 0.0, 0.0,  1.0, 0.0, 10.0),
			//new GeomVector3d(0.0, 0.0, 0.0,  0.0, 0.0, 10.0) 
		};
		int szVRef3dMcs = arrVRef3dMcs.length;

		//ROTACOES
		//
		for(int i = 0; i < szPt3dMcs; i++) {
			GeomPoint3d pt3dMcs = arrPt3dMcs[i];
			for(int j = 0; j < szVRef3dMcs; j++) {
				int pos = (i * szVRef3dMcs) + j;
				
				GeomVector3d vRef3dMcs = arrVRef3dMcs[j];
				GeomPoint3d newPt3dMcs = GeomUtil.rotationXY(pt3dMcs, vRef3dMcs);
				
				String strPt3dMcs = pt3dMcs.toStr();
				String strV3dMcs = vRef3dMcs.toStr();
				String strNewPt3dMcs = newPt3dMcs.toStr();
				
				String str = String.format("Pos:%s;Pt3d:%s;V3d:%s;NewPt3d:%s; ", pos, strPt3dMcs, strV3dMcs, strNewPt3dMcs);
				System.out.println(str);
			}
		}
	}
	
//Public
	
	public static void main(String[] args) {
		TestGeomUtil.gApp = new TestGeomUtil();
		gApp.executeTest();
	}

}
