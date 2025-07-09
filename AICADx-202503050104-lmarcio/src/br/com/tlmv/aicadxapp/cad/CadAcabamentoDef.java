/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CadAcabamentoDef.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 29/02/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cad;

import java.text.NumberFormat;

import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.AppError;
import br.com.tlmv.aicadxapp.utils.FormatUtil;

public class CadAcabamentoDef extends CadObject 
{
//Private
	private CadLayerDef layer = null;
	
//Public

	public CadAcabamentoDef(int objtype, CadLayerDef layer) 
	{
		super(objtype);
		
		this.init(layer); 
	}

	/* Methodes */
	
	private void init(CadLayerDef layer) 
	{
		this.layer = layer;
	}
	
	/* CREATE */
	
	public static CadAcabamentoDef create(int objtype, CadLayerDef layer)
	{
		CadAcabamentoDef o = new CadAcabamentoDef(objtype, layer);
		return o;
	}
	
	/* DEBUG */
	
	@Override
	public String toStr() {
		NumberFormat nf0 = FormatUtil.newNumberFormatEnUs(0);
		
		String strObjtype = Integer.toString(getObjectId());
		String strLayerName = this.layer.getName();
		
		String str = String.format(
			"Objtype:%s;" +	
			"Layer:%s;",
			strObjtype,
			strLayerName );
		return str;
	}
	
	public void debug(int debugLevel) {
		if(debugLevel != AppDefs.DEBUG_LEVEL) return;

		String warnmsg = this.toStr() + "\n";
		AppError.showCmdWarn(debugLevel, warnmsg, this.getClass());
	}

	/* Getters/Setters */
	
	public CadLayerDef getLayer() {
		return layer;
	}

	public void setLayer(CadLayerDef layer) {
		this.layer = layer;
	}
	
}
