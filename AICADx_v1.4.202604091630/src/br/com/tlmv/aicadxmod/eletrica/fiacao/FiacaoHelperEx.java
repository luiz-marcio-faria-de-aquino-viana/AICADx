/*
 * Copyright(C) TLMV Consultoria e Sistemas Ltda. Todos os direitos reservados.
 *
 * FiacaoHelper.java
 * Autor: Luiz Marcio Viana, 13/02/2018
 * Revisoes: ...
 *
 */

package br.com.tlmv.aicadxmod.eletrica.fiacao;

import br.com.tlmv.aicadxapp.utils.FileUtil;

public class FiacaoHelperEx 
{
//Private
	private String homeDir;
	private String tempDir;
	private int debugMode;
	
//Public

	public FiacaoHelperEx(String homeDir, int debugMode)
	{
		this.homeDir = homeDir.toUpperCase();
		this.debugMode = debugMode;
	}
	
	public String run(String srcFile, String targetFile, String debugFile)
	{
		String result = "-1";

		try
		{	
			String[] cmdarray = {
				"/ACADAPPL/FIACAO/BIN/fiacaoapp",
				"-H=" + homeDir,
				"-F=" + homeDir + srcFile.toUpperCase(),
				"-O=" + homeDir + targetFile.toUpperCase(),
				"-L=" + homeDir + debugFile.toUpperCase(),
				"-D"
			};
			
			Runtime r = Runtime.getRuntime();
			
			Process p = r.exec(cmdarray);
			p.waitFor();
						
			result = FileUtil.readTempFile(homeDir + targetFile.toUpperCase());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
}
