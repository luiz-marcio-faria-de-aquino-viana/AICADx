/*
 * FIACAOLIB.CPP
 * Copyright (C) 2025 by Luiz Marcio Faria de Aquino Viana, 29/09/2025
 *
 */

#include "all.h"

JNIEXPORT jint JNICALL Java_br_com_tlmv_aicadxmod_eletrica_fiacao_FiacaoHelper_processaFiacao(JNIEnv* env, jobject obj, jstring homeDir, jstring srcFile, jstring targetFile, jstring debugFile, jint debugMode)
{
	itemQdr_t *pQdr;

	itemCmd_t *pCmd;
	itemElemCmd_t *pECmd;

	Parm_t parm;

	parm.homeDir = (char*)env->GetStringUTFChars(homeDir, 0);
	printf("HomeDir: %s\n", parm.homeDir);

	parm.sourceFile = (char*)env->GetStringUTFChars(srcFile, 0);
	printf("SourceDir: %s\n", parm.sourceFile);

	parm.targetFile = (char*)env->GetStringUTFChars(targetFile, 0);
	printf("TargetDir: %s\n", parm.targetFile);

	parm.debugFile = (char*)env->GetStringUTFChars(debugFile, 0);
	printf("DebugFile: %s\n", parm.debugFile);

	if(__DEGUG_LEVEL__ != 0)
		parm.debugMode = __DEGUG_LEVEL__;
	else	
		parm.debugMode = debugMode;

	openLog(parm.debugFile);

	writeLog("\nFIACAO - Processamento da Fiacao Eletrica");
	writeLog("\npor Luiz Marcio Faria Viana, 9/5/96");

	writeLog("\n\nProcessando os dados de entrada...");
	ler_arqventr(parm.sourceFile);
	if (parm.debugMode != -1) {
		writeLog("\nLista de eletrodutos:");
		debug_eletr();
	}
	pQdr = primQdr;
	while (pQdr != NULL) {
		writeLog("\nProcessando fiacao eletrica do quadro = %s...", pQdr->qdr);
		ler_arqvtemp(pQdr);
		cons_desvios();
		if (parm.debugMode != -1) {
			writeLog("\nLista de elementos:");
			debug_elem();
			writeLog("\nLista de desvios:");
			debug_desvios();
			writeLog("\nLista de comandos ANTES do ajuste:");
			debug_comandos();
		}
		ajusta_lista();
		if (parm.debugMode != -1) {
			writeLog("\nLista de comandos APOS o ajuste:");
			debug_comandos();
		}
		leltr_limpaSinalizadores();
		cons_malha(pQdr->hnd, &primMalha);
		if (parm.debugMode != -1) {
			writeLog("\nTopo da arvore = %s\n", pQdr->qdr);
			debug_malha(primMalha);
		}
		proc_quadro(pQdr, primMalha);
		lmlh_desaloca(&primMalha);
		pCmd = primCmd;
		while (pCmd != NULL) {
			pECmd = pCmd->primElemCmd;
			while (pECmd != NULL) {
				ldes_limpaSinalizadores();
				leltr_limpaSinalizadores();
				cons_malha(pECmd->hnd, &primMalha);
				if (parm.debugMode != -1) {
					writeLog("\nTopo da arvore = comando(%s)\n", pECmd->hnd);
					debug_malha(primMalha);
				}
				proc_comando(pCmd, pECmd, primMalha);
				lmlh_desaloca(&primMalha);
				pECmd = pECmd->prox;
			}
			pCmd = pCmd->prox;
		}
		writeLog("\n  (Processamento do quadro %s concluido!)", pQdr->qdr);
		if (parm.debugMode != -1)
			debug_result();
		lcmd_desaloca();
		lelem_desaloca();
		pQdr = pQdr->prox;
	}
	if (parm.targetFile == NULL)
		gerar_arqvsaidaxml((char*)FILE_TARGET);
	else
		gerar_arqvsaidaxml(parm.targetFile);
	writeLog("\n\nProcessamento concluido!");
	closeLog();

	desalocall();

	env->ReleaseStringUTFChars(homeDir, parm.homeDir);
	env->ReleaseStringUTFChars(srcFile, parm.sourceFile);
	env->ReleaseStringUTFChars(targetFile, parm.targetFile);
	env->ReleaseStringUTFChars(debugFile, parm.debugFile);

	return(0);
}

int processaFiacao(char* homeDir, char* srcFile, char* targetFile, char* debugFile, int debugMode)
{
	itemQdr_t *pQdr;

	itemCmd_t *pCmd;
	itemElemCmd_t *pECmd;

	Parm_t parm;

	parm.homeDir = homeDir;
	parm.sourceFile = srcFile;
	parm.targetFile = targetFile;
	parm.debugFile = debugFile;

	if(__DEGUG_LEVEL__ != 0)
		parm.debugMode = __DEGUG_LEVEL__;
	else	
		parm.debugMode = debugMode;

	openLog(parm.debugFile);

	writeLog("\nFIACAO - Processamento da Fiacao Eletrica");
	writeLog("\npor Luiz Marcio Faria Viana, 9/5/96");

	writeLog("\n\nProcessando os dados de entrada...");
	ler_arqventr(parm.sourceFile);
	if (parm.debugMode != -1) {
		writeLog("\nLista de eletrodutos:");
		debug_eletr();
	}
	pQdr = primQdr;
	while (pQdr != NULL) {
		writeLog("\nProcessando fiacao eletrica do quadro = %s...", pQdr->qdr);
		ler_arqvtemp(pQdr);
		cons_desvios();
		if (parm.debugMode != -1) {
			writeLog("\nLista de elementos:");
			debug_elem();
			writeLog("\nLista de desvios:");
			debug_desvios();
			writeLog("\nLista de comandos ANTES do ajuste:");
			debug_comandos();
		}
		ajusta_lista();
		if (parm.debugMode != -1) {
			writeLog("\nLista de comandos APOS o ajuste:");
			debug_comandos();
		}
		leltr_limpaSinalizadores();
		cons_malha(pQdr->hnd, &primMalha);
		if (parm.debugMode != -1) {
			writeLog("\nTopo da arvore = %s\n", pQdr->qdr);
			debug_malha(primMalha);
		}
		proc_quadro(pQdr, primMalha);
		lmlh_desaloca(&primMalha);
		pCmd = primCmd;
		while (pCmd != NULL) {
			pECmd = pCmd->primElemCmd;
			while (pECmd != NULL) {
				ldes_limpaSinalizadores();
				leltr_limpaSinalizadores();
				cons_malha(pECmd->hnd, &primMalha);
				if (parm.debugMode != -1) {
					writeLog("\nTopo da arvore = comando(%s)\n", pECmd->hnd);
					debug_malha(primMalha);
				}
				proc_comando(pCmd, pECmd, primMalha);
				lmlh_desaloca(&primMalha);
				pECmd = pECmd->prox;
			}
			pCmd = pCmd->prox;
		}
		writeLog("\n  (Processamento do quadro %s concluido!)", pQdr->qdr);
		if (parm.debugMode != -1)
			debug_result();
		lcmd_desaloca();
		lelem_desaloca();
		pQdr = pQdr->prox;
	}
	if (parm.targetFile == NULL)
		gerar_arqvsaidaxml((char*)FILE_TARGET);
	else
		gerar_arqvsaidaxml(parm.targetFile);
	writeLog("\n\nProcessamento concluido!");
	closeLog();

	desalocall();
	return(0);
}

int main(int argc, char* argv[])
{
	char* homeDir = 	(char*)"/ACADAPPL/FIACAO/TEMP/";
	char* srcFile = 	(char*)"/ACADAPPL/FIACAO/TEMP/FIACAO.~XT";
	char* targetFile = 	(char*)"/ACADAPPL/FIACAO/TEMP/FIACAO.~ST";
	char* debugFile = 	(char*)"/ACADAPPL/FIACAO/TEMP/FIACAO.LOG";

	int rscode = processaFiacao(homeDir, srcFile, targetFile, debugFile, -1);
	return(rscode);
}

