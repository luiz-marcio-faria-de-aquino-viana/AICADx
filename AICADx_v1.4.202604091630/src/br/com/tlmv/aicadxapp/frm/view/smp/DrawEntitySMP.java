/*
 * Copyright (c) 2025-2026 TLMV Consultoria e Sistemas EIRELI.
 *
 * DrawEntitySMP.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 27/01/2025
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
import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.AppProf;
import br.com.tlmv.aicadxapp.cad.CadBlockDef;
import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.CadObject;
import br.com.tlmv.aicadxapp.cad.ICadViewBase;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;
import br.com.tlmv.aicadxapp.frm.view.ICompView;

public class DrawEntitySMP
{
//Private
	private ArrayList<DrawEntityWorkerSMP> lsWorker = null;
	private int maxNumThread = AppDefs.NULL_INT;
	private int minEntitiesPerThread = AppDefs.NULL_INT; 
	
	private ICompView currCompView = null;
	private ICadViewBase currView = null;
	private CadBlockDef blkDef = null;
	private double dist = AppDefs.NULL_DBL;
	private GeomPoint2d ptBase2dMcs = AppDefs.NULL_GEOMPOINT2D;
	private GeomPoint2d pt2dMcs = AppDefs.NULL_GEOMPOINT2D;
	private double sclFact = AppDefs.NULL_DBL;
	private boolean bDragMode = false;
	private Graphics currGr = null;
	//
	private boolean bDrawSelected = false; 
	private boolean bDrawEntities = false;
	private boolean bDrawOsnap = false;
	private boolean bDrawTooltip = false;
	
//Public
	
	public DrawEntitySMP(
		ICompView compView,
		ICadViewBase v,
		CadBlockDef blkDef, 
		double dist,
		GeomPoint2d ptBase2dMcs,
		GeomPoint2d pt2dMcs,
		double sclFact,
		boolean bDragMode,
		Graphics g,
		boolean bDrawSelected, 
		boolean bDrawEntities,
		boolean bDrawOsnap, 
		boolean bDrawTooltip ) 
	{
		this.maxNumThread = AppDefs.SMP_MAX_NUM_THREADS;
		this.minEntitiesPerThread = AppDefs.SMP_MIN_NUM_ENTITIES;
		//
		this.currCompView = compView;
		this.currView = v;
		this.blkDef = blkDef;
		this.dist = dist;
		this.ptBase2dMcs = ptBase2dMcs;
		this.pt2dMcs = pt2dMcs;
		this.sclFact = sclFact;
		this.bDragMode = bDragMode;
		this.currGr = g;
		//
		this.bDrawSelected = bDrawSelected; 
		this.bDrawEntities = bDrawEntities;
		this.bDrawOsnap = bDrawOsnap;
		this.bDrawTooltip = bDrawTooltip;
	}
	
	/* Methodes */

	public void startWorkers() 
	{
		this.lsWorker = new ArrayList<DrawEntityWorkerSMP>();

		int numThreads = this.maxNumThread;

		int numEntities = this.blkDef.getEntityTableSz();
		if(numEntities < this.minEntitiesPerThread)
			numThreads = 1;

		int numEntitiesPerThread = (numEntities / numThreads) + 1;
		
		String warnmsg = String.format(
			"NumEntities:%s;MaxNumEntitiesPerThread:%s;NumThreads:%s; ", 
			numEntities, 
			numEntitiesPerThread,
			numThreads );
		AppError.showCmdWarn(AppDefs.DEBUG_LEVEL43, warnmsg, this.getClass());
		
		AppProf prof = new AppProf(AppDefs.DEBUG_LEVEL43, "DrawEntitySMP:toArrEntities");

		prof.start();
		
		CadEntity[] arrEntities = this.blkDef.toArrEntities();
		prof.stop();
		
		int startPos = 0;
		for(int i = 0; i < numThreads; i++) {
			int endPos = startPos + numEntitiesPerThread;
			if(endPos > numEntities)
				endPos = numEntities;
			
			DrawEntityWorkerSMP worker = new DrawEntityWorkerSMP(
				i, 
				//
				this.currCompView,
				this.currView,
				this.blkDef, 
				this.dist,
				this.ptBase2dMcs,
				this.pt2dMcs,
				this.sclFact,
				this.bDragMode,
				startPos, 
				endPos,
				this.currGr,
				this.bDrawSelected, 
				this.bDrawEntities,
				this.bDrawOsnap, 
				this.bDrawTooltip, 
				arrEntities );
			this.lsWorker.add(worker);
			worker.startThread();
			
			startPos = endPos;
		}
	}
	
	public void waitWorkers() 
	{
		for(DrawEntityWorkerSMP worker : this.lsWorker) {
			worker.waitThread();
		}
	}
	
	public void execute() 
	{
		AppProf prof = new AppProf(AppDefs.DEBUG_LEVEL43, "");
		
		prof.start();
		
		this.startWorkers();

		prof.mark();
		
		this.waitWorkers();
		
		prof.stop();
	}
	
}
