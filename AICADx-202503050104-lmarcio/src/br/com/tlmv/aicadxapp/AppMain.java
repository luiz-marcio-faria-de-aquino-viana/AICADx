/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * AppMain.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 26/01/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp;

import java.util.Locale;

import javax.swing.JFrame;

import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.res.strings.ResourceEnUs;

public class AppMain 
{
//Private Static
	private static AppMain gApp;
	
//Private
	
	private AppCtx ctx;
	
	private AppConfig config;
	
	private AppCadMain cad;
	
	private R resource;
	
	private static Locale gLocalePtBr = new Locale(AppDefs.DEF_LANG_PT, AppDefs.DEF_COUNTRY_BR);
	
	private static Locale gLocaleEnUs = new Locale(AppDefs.DEF_LANG_EN, AppDefs.DEF_COUNTRY_US);	
	
	private void copyright()
	{
		String str = String.format(
			"%s\n\n%s %s\n%s\n%s\n%s\n%s\n", 
			AppDefs.APP_COPYRIGHT,
			AppDefs.APP_NAME, 
			AppDefs.APP_VERSAO,
			AppDefs.APP_AUTHOR_NAME,
			AppDefs.APP_AUTHOR_REGISTRO,
			AppDefs.APP_AUTHOR_EMAIL,
			AppDefs.APP_AUTHOR_TELEFONE);
		System.out.println(str);
	}
	
//Public
	
	public AppMain()
	{
		AppMain.gApp = this;
		
		this.resource = new ResourceEnUs();		

		this.ctx = new AppCtx();
		
		String confFile = ctx.getConfFile();
		this.config = new AppConfig(confFile);

		this.cad = new AppCadMain();
	}
	
	/* Methodes */
	
	public int init()
	{
		return AppDefs.RSOK;
	}
	
	public int execute()
	{
		AppMain app = AppMain.getApp();
		R res = app.getResource();
		
		try {
			JFrame f = null;
			f = new MainFrame();
			f.show();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
			String errmsg = res.getString("ERR_PROCESSING_FAILURE");
			AppError.showCmdError(errmsg, this.getClass());
		}
		
		return AppDefs.RSOK;
	}
	
	/* MAIN */
	
	public static void main(String[] args) 
	{
		AppMain.gApp = new AppMain();		
		AppMain.gApp.copyright();
		if(AppMain.gApp.init() == AppDefs.RSOK) {
			AppMain.gApp.execute();
		}
	}

	/* Getters/Setters */
	
	public static AppMain getApp() {
		return gApp;
	}

	public static Locale getPtBr() {
		return gLocalePtBr;
	}

	public static Locale getEnUs() {
		return gLocaleEnUs;
	}

	public AppCtx getCtx() {
		return ctx;
	}

	public AppConfig getConfig() {
		return config;
	}

	public R getResource() {
		return resource;
	}

	public AppCadMain getCad() {
		return cad;
	}

}
