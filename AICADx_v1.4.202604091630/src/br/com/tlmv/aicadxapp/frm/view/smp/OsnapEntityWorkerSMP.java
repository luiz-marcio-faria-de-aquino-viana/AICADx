/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * OsnapEntityWorkerSMP.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 06/04/2026
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

package br.com.tlmv.aicadxapp.frm.view.smp;

import java.awt.Graphics;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint3d;
import br.com.tlmv.aicadxapp.frm.view.ICompView;

public class OsnapEntityWorkerSMP implements Runnable
{
//Private
	private ICompView currCompView = null;
	private ICadViewBase currView = null;
	private CadBlockDef blkDef = null;
	private GeomPoint2d pt2dMcs = AppDefs.NULL_GEOMPOINT2D;
	private double sclFact = AppDefs.NULL_DBL;
	private int startPos = AppDefs.NULL_INT;
	private int endPos = AppDefs.NULL_INT;
	//
	private GeomPoint3d ptResult = null;
	//
	private CadEntity[] arrEntities = null;

	private int threadId = AppDefs.NULL_INT;
	private Thread thread = null;
	
	private boolean bRunning = false;
	
//Public
	
	public OsnapEntityWorkerSMP(
		int threadId, 
		//
		ICompView compView,
		ICadViewBase v,
		CadBlockDef blkDef, 
		GeomPoint2d pt2dMcs,
		double sclFact,
		int startPos, 
		int endPos,
		//
		CadEntity[] arrEntities ) 
	{
		this.threadId = threadId;
		//
		this.currCompView = compView;
		this.currView = v;
		this.blkDef = blkDef;
		this.pt2dMcs = pt2dMcs;
		this.sclFact = sclFact;
		this.startPos = startPos;
		this.endPos = endPos;
		//
		this.arrEntities = arrEntities;
	}
	
	/* Methodes */

	public void startThread() {
		this.thread = new Thread(this);
		this.thread.setDaemon(true);
		this.thread.start();
	}

	public void stopThread() {
		this.bRunning = false;
		this.notifyAll();
	}

	public GeomPoint3d waitThread() {
		try {
			this.thread.join();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return this.ptResult;
	}
	
	/* RUN */

	@Override
	public void run() {
		this.bRunning = true;
		
		this.ptResult = this.currCompView.processOsnapWorkerSMP(
			this.currView, 
			this.blkDef, 
			this.pt2dMcs, 
			this.sclFact, 
			this.startPos, 
			this.endPos, 
			this.arrEntities );
		
		this.bRunning = false;
	}
	
}
