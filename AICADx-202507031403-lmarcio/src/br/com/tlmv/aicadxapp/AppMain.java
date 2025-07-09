/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * AppMain.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 04/05/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computação
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp;

import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JFrame;

import br.com.tlmv.aicadxapp.dao.BaseDao;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.res.strings.R;
import br.com.tlmv.aicadxapp.res.strings.ResourceEnUs;
import br.com.tlmv.aicadxapp.vo.DatabaseConnectionVO;
import br.com.tlmv.aicadxmod.IModule;
import br.com.tlmv.aicadxmod.AguaPluvialModule;
import br.com.tlmv.aicadxmod.ArquiteturaModule;
import br.com.tlmv.aicadxmod.DrenagemModule;
import br.com.tlmv.aicadxmod.EletricaModule;
import br.com.tlmv.aicadxmod.EsgotoModule;
import br.com.tlmv.aicadxmod.GasModule;
import br.com.tlmv.aicadxmod.HidraulicaModule;

public class AppMain 
{
//Private Static
	private static AppMain gApp;
	
//Private
	
	private AppCtx ctx;
	
	private AppConfig config;
	
	private AppDatabase db; 
	
	private AppCadMain cad;
	
	private R resource;
	
	private ArrayList<IModule> lsModule = null;
	
	private static Locale gLocalePtBr = new Locale(AppDefs.DEF_LANG_PT, AppDefs.DEF_COUNTRY_BR);
	
	private static Locale gLocaleEnUs = new Locale(AppDefs.DEF_LANG_EN, AppDefs.DEF_COUNTRY_US);	

	//MODULES
	//
	private ArquiteturaModule arqModule = null;
	private EletricaModule elModule = null;
	private EsgotoModule esModule = null;
	private AguaPluvialModule apModule = null;
	private DrenagemModule rpdModule = null;
	private HidraulicaModule hidModule = null;
	private GasModule gModule = null;
		
	private void loadModules()
	{
		this.arqModule = new ArquiteturaModule(this, this.cad);
		this.elModule = new EletricaModule(this, this.cad);
		this.esModule = new EsgotoModule(this, this.cad);
		this.apModule = new AguaPluvialModule(this, this.cad);
		this.rpdModule = new DrenagemModule(this, this.cad);
		this.hidModule = new HidraulicaModule(this, this.cad);
		this.gModule = new GasModule(this, this.cad);

		//MODULES
		//
		this.lsModule = new ArrayList<IModule>();

		this.lsModule.add(arqModule);
		this.lsModule.add(elModule);
		this.lsModule.add(esModule);
		this.lsModule.add(apModule);
		this.lsModule.add(rpdModule);
		this.lsModule.add(hidModule);
		this.lsModule.add(gModule);
	}
	
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
		
		this.db = new AppDatabase();

		this.cad = new AppCadMain();
		
		loadModules();
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

	public AppDatabase getDb() {
		return db;
	}

	public void setDb(AppDatabase db) {
		this.db = db;
	}

	public AppCadMain getCad() {
		return cad;
	}

	public ArrayList<IModule> getLsModule() {
		return lsModule;
	}

	public void setLsModule(ArrayList<IModule> lsModule) {
		this.lsModule = lsModule;
	}
	
	/* MODULES */

	public ArquiteturaModule getArqModule() {
		return arqModule;
	}

	public EletricaModule getElModule() {
		return elModule;
	}

	public EsgotoModule getEsModule() {
		return esModule;
	}

	public AguaPluvialModule getApModule() {
		return apModule;
	}

	public DrenagemModule getRpdModule() {
		return rpdModule;
	}

	public HidraulicaModule getHidModule() {
		return hidModule;
	}

	public GasModule getGasModule() {
		return gModule;
	}

	public void setGasModule(GasModule gModule) {
		this.gModule = gModule;
	}

}
