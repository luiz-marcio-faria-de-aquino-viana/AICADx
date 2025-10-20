/*
 * Copyright(C) TLMV Consultoria e Sistemas EIRELI. Todos os direitos reservados.
 *
 * CmdLoadSample.java
 * Autor: 
 *   Luiz Marcio Faria de Aquino Viana, Pos-D.Sc. - Engenheiro, 28/07/2025
 *   Unidade: Universidade do Estado do Rio de Janeiro
 *   Curso: Engenharia Eletrica, Enfase em Engenharia de Sistemas e Computacao
 *   Unico Socio e Administrador da Empresa - Desde: 02/08/2000
 *
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxapp.cmd;

import java.util.ArrayList;

import br.com.tlmv.aicadxapp.AppCadMain;
import br.com.tlmv.aicadxapp.AppDefs;
import br.com.tlmv.aicadxapp.cad.CadDocumentDef;
import br.com.tlmv.aicadxapp.cad.utils.PromptUtil;
import br.com.tlmv.aicadxapp.frm.MainFrame;
import br.com.tlmv.aicadxapp.frm.MainPanel;
import br.com.tlmv.aicadxapp.frm.view.ICompView;
import br.com.tlmv.aicadxapp.samples.BasicSample;
import br.com.tlmv.aicadxapp.samples.Box3DSample;
import br.com.tlmv.aicadxapp.samples.Cilinder3DSample;
import br.com.tlmv.aicadxapp.samples.Cone3DSample;
import br.com.tlmv.aicadxapp.samples.DrenagemSample;
import br.com.tlmv.aicadxapp.samples.DxfSample;
import br.com.tlmv.aicadxapp.samples.LineSample;
import br.com.tlmv.aicadxapp.samples.PipeSample;
import br.com.tlmv.aicadxapp.samples.PointSample;
import br.com.tlmv.aicadxapp.samples.ProjetoCampoSacoSample;
import br.com.tlmv.aicadxapp.samples.Sphere3DSample;
import br.com.tlmv.aicadxapp.samples.TesteDrenagemRede1Sample;
import br.com.tlmv.aicadxapp.samples.TesteDrenagemRede2Sample;
import br.com.tlmv.aicadxapp.samples.TesteDrenagemRede3Sample;
import br.com.tlmv.aicadxapp.samples.TesteDrenagemRede4Sample;
import br.com.tlmv.aicadxapp.samples.TesteDrenagemRede5Sample;
import br.com.tlmv.aicadxapp.samples.TesteDrenagemRede6Sample;
import br.com.tlmv.aicadxapp.samples.TesteDrenagemRede7Sample;
import br.com.tlmv.aicadxapp.samples.TesteDrenagemSample;
import br.com.tlmv.aicadxapp.samples.TestePipeSample;
import br.com.tlmv.aicadxapp.samples.Torus3DSample;
import br.com.tlmv.aicadxapp.samples.TransMarR1Sample;
import br.com.tlmv.aicadxapp.samples.TroncoCone3DSample;
import br.com.tlmv.aicadxapp.vo.InputParamVO;
import br.com.tlmv.aicadxapp.vo.PromptOptionVO;

public class CmdLoadSample extends CmdBase
{
//Private
	
	/* PromptOption
	*/
	private PromptOptionVO optBasicSample = new PromptOptionVO(AppDefs.OPT_SAMPLEDATA_BASIC, "(A)Basic", "A", false);
	private PromptOptionVO optBox3DSample = new PromptOptionVO(AppDefs.OPT_SAMPLEDATA_BOX3D, "(B)Box3D Sample", "B", true);
	private PromptOptionVO optCilinder3DSample = new PromptOptionVO(AppDefs.OPT_SAMPLEDATA_CILINDER3D, "(C)Cilinder3D", "C", false);
	private PromptOptionVO optCone3DSample = new PromptOptionVO(AppDefs.OPT_SAMPLEDATA_CONE3D, "(D)Cone3D", "D", false);
	private PromptOptionVO optTroncoCone3DSample = new PromptOptionVO(AppDefs.OPT_SAMPLEDATA_TRONCOCONE3D, "(E)Tronco Cone3D", "E", false);
	private PromptOptionVO optSphere3DSample = new PromptOptionVO(AppDefs.OPT_SAMPLEDATA_SPHERE3D, "(F)Sphere3D", "F", false);
	private PromptOptionVO optTorus3DSample = new PromptOptionVO(AppDefs.OPT_SAMPLEDATA_TORUS3D, "(G)Torus3D", "G", false);
	private PromptOptionVO optDxfSample = new PromptOptionVO(AppDefs.OPT_SAMPLEDATA_DXF, "(H)DXF", "H", false);
	private PromptOptionVO optLineSample = new PromptOptionVO(AppDefs.OPT_SAMPLEDATA_LINE, "(I)Line", "I", false);
	private PromptOptionVO optPointSample = new PromptOptionVO(AppDefs.OPT_SAMPLEDATA_POINT, "(J)Point", "J", false);
	private PromptOptionVO optDrenagemSample = new PromptOptionVO(AppDefs.OPT_SAMPLEDATA_DRENAGEM, "(K)Drenagem", "K", false);
	private PromptOptionVO optPipeSample = new PromptOptionVO(AppDefs.OPT_SAMPLEDATA_PIPE, "(L)Pipe", "L", false);
	private PromptOptionVO optTestPipeSample = new PromptOptionVO(AppDefs.OPT_SAMPLEDATA_TESTPIPE, "(M)Pipe Test", "M", false);
	private PromptOptionVO optProjetoCampoSacoSample = new PromptOptionVO(AppDefs.OPT_SAMPLEDATA_PROJETOCAMPOSACO, "(N)Campo do Saco", "N", false);
	private PromptOptionVO optTesteDrenagemSample = new PromptOptionVO(AppDefs.OPT_SAMPLEDATA_TESTEDRENAGEM, "(O)Drenagem Teste", "O", true);
	//
	private PromptOptionVO optTesteDrenagemRalo1Sample = new PromptOptionVO(AppDefs.OPT_SAMPLEDATA_TESTEDRENAGEMRALO1, "(1)Drenagem Teste R1", "1", true);
	private PromptOptionVO optTesteDrenagemRalo2Sample = new PromptOptionVO(AppDefs.OPT_SAMPLEDATA_TESTEDRENAGEMRALO2, "(2)Drenagem Teste R2", "2", true);
	private PromptOptionVO optTesteDrenagemRalo3Sample = new PromptOptionVO(AppDefs.OPT_SAMPLEDATA_TESTEDRENAGEMRALO3, "(3)Drenagem Teste R3", "3", true);
	private PromptOptionVO optTesteDrenagemRalo4Sample = new PromptOptionVO(AppDefs.OPT_SAMPLEDATA_TESTEDRENAGEMRALO4, "(4)Drenagem Teste R4", "4", true);
	private PromptOptionVO optTesteDrenagemRalo5Sample = new PromptOptionVO(AppDefs.OPT_SAMPLEDATA_TESTEDRENAGEMRALO5, "(5)Drenagem Teste R5", "5", true);
	private PromptOptionVO optTesteDrenagemRalo6Sample = new PromptOptionVO(AppDefs.OPT_SAMPLEDATA_TESTEDRENAGEMRALO6, "(6)Drenagem Teste R6", "6", true);
	private PromptOptionVO optTesteDrenagemRalo7Sample = new PromptOptionVO(AppDefs.OPT_SAMPLEDATA_TESTEDRENAGEMRALO7, "(7)Drenagem Teste R7", "7", true);
	//
	private PromptOptionVO optTransMarSample1 = new PromptOptionVO(AppDefs.OPT_SAMPLEDATA_TRANSMARSAMPLE01, "(Z)TransMar Teste R1", "Z", true);
	
//Public
	
	public CmdLoadSample() {
		super(AppDefs.ACTION_FILE_LOADSAMPLE, false, true);
	}
	
	/* Methodes */
	
	@Override
	public void initCommand() { }

	@Override
	public void finishCommand() {
		MainPanel panel = (MainPanel)this.getFrm().getPanel();

		ICompView v = panel.getCurrView();
		v.clearBlips();
		v.repaintAll();
	}	
	
	@Override
	public InputParamVO promptInputParam(MainFrame frm, InputParamVO oParam)
	{
		InputParamVO result = null;
		
		PromptUtil.prompt("Load Sample...");

		ArrayList<PromptOptionVO> lsPromptOptions = new ArrayList<PromptOptionVO>();
		lsPromptOptions.add(this.optBasicSample);
		lsPromptOptions.add(this.optBox3DSample);
		lsPromptOptions.add(this.optCilinder3DSample);
		lsPromptOptions.add(this.optCone3DSample);
		lsPromptOptions.add(this.optTroncoCone3DSample);
		lsPromptOptions.add(this.optSphere3DSample);
		lsPromptOptions.add(this.optTorus3DSample);
		lsPromptOptions.add(this.optDxfSample);
		lsPromptOptions.add(this.optLineSample);
		lsPromptOptions.add(this.optPointSample);
		lsPromptOptions.add(this.optDrenagemSample);
		lsPromptOptions.add(this.optPipeSample);
		lsPromptOptions.add(this.optTestPipeSample);
		lsPromptOptions.add(this.optProjetoCampoSacoSample);
		lsPromptOptions.add(this.optTesteDrenagemSample);
		//
		lsPromptOptions.add(this.optTesteDrenagemRalo1Sample);
		lsPromptOptions.add(this.optTesteDrenagemRalo2Sample);
		lsPromptOptions.add(this.optTesteDrenagemRalo3Sample);
		lsPromptOptions.add(this.optTesteDrenagemRalo4Sample);
		lsPromptOptions.add(this.optTesteDrenagemRalo5Sample);
		lsPromptOptions.add(this.optTesteDrenagemRalo6Sample);
		lsPromptOptions.add(this.optTesteDrenagemRalo7Sample);
		//
		lsPromptOptions.add(this.optTransMarSample1);
		
		PromptOptionVO oKeyword = PromptUtil.getKeyword(lsPromptOptions, "Load sample: ");
		if(oKeyword == null) {
			oKeyword = this.optTesteDrenagemSample;
		}
		
		result = new InputParamVO();
		result.initKey(oKeyword);		
		return result;
	}
	
	@Override
	public void doCommand() {
		InputParamVO oParam = this.promptInputParam(this.getFrm(), null);
		if(oParam == null) return;

		CadDocumentDef doc = this.getDoc();

		PromptOptionVO oKeyword = oParam.getKeyword();
		int optionId = oKeyword.getOptionId();
		switch( optionId ) {
		case AppDefs.OPT_SAMPLEDATA_BASIC:
			(new BasicSample()).initSampleData();
			break;
		case AppDefs.OPT_SAMPLEDATA_BOX3D:
			(new Box3DSample()).initSampleData();
			break;
		case AppDefs.OPT_SAMPLEDATA_CILINDER3D:
			(new Cilinder3DSample()).initSampleData();
			break;
		case AppDefs.OPT_SAMPLEDATA_CONE3D:
			(new Cone3DSample()).initSampleData();
			break;
		case AppDefs.OPT_SAMPLEDATA_TRONCOCONE3D:
			(new TroncoCone3DSample()).initSampleData();
			break;
		case AppDefs.OPT_SAMPLEDATA_SPHERE3D:
			(new Sphere3DSample()).initSampleData();
			break;
		case AppDefs.OPT_SAMPLEDATA_TORUS3D:
			(new Torus3DSample()).initSampleData();
			break;
		case AppDefs.OPT_SAMPLEDATA_DXF:
			(new DxfSample()).initSampleData();
			break;
		case AppDefs.OPT_SAMPLEDATA_LINE:
			(new LineSample()).initSampleData();
			break;
		case AppDefs.OPT_SAMPLEDATA_POINT:
			(new PointSample()).initSampleData();
			break;
		case AppDefs.OPT_SAMPLEDATA_DRENAGEM:
			(new DrenagemSample()).initSampleData();
			break;
		case AppDefs.OPT_SAMPLEDATA_PIPE:
			(new PipeSample()).initSampleData();
			break;
		case AppDefs.OPT_SAMPLEDATA_TESTPIPE:
			(new TestePipeSample()).initSampleData();
			break;
		case AppDefs.OPT_SAMPLEDATA_PROJETOCAMPOSACO:
			(new ProjetoCampoSacoSample()).initSampleData();
			break;
		case AppDefs.OPT_SAMPLEDATA_TESTEDRENAGEM:
			(new TesteDrenagemSample()).initSampleData();
			break;
		case AppDefs.OPT_SAMPLEDATA_TESTEDRENAGEMRALO1:
			(new TesteDrenagemRede1Sample()).initSampleData();
			break;
		case AppDefs.OPT_SAMPLEDATA_TESTEDRENAGEMRALO2:
			(new TesteDrenagemRede2Sample()).initSampleData();
			break;
		case AppDefs.OPT_SAMPLEDATA_TESTEDRENAGEMRALO3:
			(new TesteDrenagemRede3Sample()).initSampleData();
			break;
		case AppDefs.OPT_SAMPLEDATA_TESTEDRENAGEMRALO4:
			(new TesteDrenagemRede4Sample()).initSampleData();
			break;
		case AppDefs.OPT_SAMPLEDATA_TESTEDRENAGEMRALO5:
			(new TesteDrenagemRede5Sample()).initSampleData();
			break;
		case AppDefs.OPT_SAMPLEDATA_TESTEDRENAGEMRALO6:
			(new TesteDrenagemRede6Sample()).initSampleData();
			break;
		case AppDefs.OPT_SAMPLEDATA_TESTEDRENAGEMRALO7:
			(new TesteDrenagemRede7Sample()).initSampleData();
			break;
		case AppDefs.OPT_SAMPLEDATA_TRANSMARSAMPLE01:
			(new TransMarR1Sample()).initSampleData();
			break;
		}
		
	}

}
