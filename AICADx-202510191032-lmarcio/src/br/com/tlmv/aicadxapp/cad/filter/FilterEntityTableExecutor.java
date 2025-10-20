/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * FilterEntityTableExecutor.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 17/03/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad.filter;

import java.util.ArrayList;
import java.util.Hashtable;

import br.com.tlmv.aicadxapp.cad.CadEntity;
import br.com.tlmv.aicadxapp.cad.geom.GeomPoint2d;

public class FilterEntityTableExecutor implements Runnable
{
//Private
	private int threadId = -1;
	private FilterEntityTable parent = null;
	private int startPos = 0;
	private int endPos = 0;
	private GeomPoint2d ptMinMcs = null;
	private GeomPoint2d ptMaxMcs = null;
	
	private Thread thread = null;
	private boolean isRunning = false;
	
//Public
	
	public FilterEntityTableExecutor(
		int threadId,
		FilterEntityTable parent,
		int startPos,
		int endPos,
		GeomPoint2d ptMinMcs,
		GeomPoint2d ptMaxMcs)
	{
		this.threadId = threadId;
		this.parent = parent;
		this.startPos = startPos;
		this.endPos = endPos;
		this.ptMinMcs = new GeomPoint2d(ptMinMcs);
		this.ptMaxMcs = new GeomPoint2d(ptMaxMcs);
	}

	/* THREADS */
	
	public void startExecutor()
	{
		if( !this.isRunning ) {
			this.thread = new Thread(this);
			this.thread.start();
		}
	}
	
	public void waitExecutor()
	{
		if(this.thread != null) {
			try {
				this.thread.join();
			}
			catch(Exception e) { }
		}
		this.thread = null;
	}
	
	@Override
	public void run() 
	{
		this.isRunning = true;

		for(int i = startPos; i < endPos; i++) {
			CadEntity oEnt = this.parent.getEntityAt(i);
			
			GeomPoint2d ptMin2dMcs = new GeomPoint2d(this.ptMinMcs);
			GeomPoint2d ptMax2dMcs = new GeomPoint2d(this.ptMaxMcs);
			
			boolean bInside = oEnt.isInside(ptMin2dMcs, ptMax2dMcs);
			if( bInside ) {
				this.parent.addEntity(oEnt);
			}
		}
		this.isRunning = false;
	}
	
	/* Getters/Setters */

	public int getThreadId() {
		return threadId;
	}

	public FilterEntityTable getParent() {
		return parent;
	}

	public int getStartPos() {
		return startPos;
	}

	public int getEndPos() {
		return endPos;
	}
	
	public GeomPoint2d getPtMinMcs() {
		return ptMinMcs;
	}

	public GeomPoint2d getPtMaxMcs() {
		return ptMaxMcs;
	}
	
}
