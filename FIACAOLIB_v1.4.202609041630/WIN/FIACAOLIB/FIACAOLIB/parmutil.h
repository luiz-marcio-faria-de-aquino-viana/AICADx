
/*
 * PARMUTIL.H
 * Copyright (C) 1996 by Luiz Marcio Faria Viana, 9/4/96
 */

#ifndef __PARMUTIL_H
#define __PARMUTIL_H

#include"all.h"

/* definicao da macro que fornece o comprimento de uma string
*/
#define SZ(t) (strlen(t))

/* definicao dos identificadores dos tipos de parametros
*/
#define PARM_SOURCEFILE "/F="
#define PARM_TARGETFILE "/O="
#define PARM_DEBUGFILE  "/L="
#define PARM_DEBUGMODE  "/D"
#define PARM_HOMEDIR	"/H="

/* estrutura de dados dos parametros de entrada
*/
typedef struct tagParm {
  char *homeDir;
  char *sourceFile;
  char *targetFile; 
  char *debugFile;
  int  debugMode;
} Parm_t;

/* declaracao da rotina para obtencao dos parametros de entrada
*/
void getparm(Parm_t *parm);

/* implementacao da rotina para obtencao dos parametros de entrada
*/
void getparm(int _argc, char* _argv[], Parm_t *parm)
{
  int i;
  if(_argc <= 1) errmsg(43, ERR_NUMPARM, NULL);

  parm->homeDir = NULL;
  parm->sourceFile = NULL;
  parm->targetFile = NULL;
  parm->debugFile = NULL;
  parm->debugMode = 0;

  for(i = 0; i < _argc; i++) {
  	strupr_fia(_argv[i]);
	if (!strncmp(PARM_HOMEDIR, _argv[i], SZ(PARM_HOMEDIR))) {
		if ((parm->homeDir = (char *)malloc(SZ(_argv[i]))) == NULL)
			errmsg(40, ERR_ALLOCMEM, NULL);
		strcpy(parm->homeDir, &_argv[i][SZ(PARM_HOMEDIR)]);
	}
	if(!strncmp(PARM_SOURCEFILE, _argv[i], SZ(PARM_SOURCEFILE))) {
      if((parm->sourceFile = (char *) malloc(SZ(_argv[i]))) == NULL)
	errmsg(41, ERR_ALLOCMEM, NULL);
      strcpy(parm->sourceFile, & _argv[i][SZ(PARM_SOURCEFILE)]);
    }
    if(!strncmp(PARM_TARGETFILE, _argv[i], SZ(PARM_TARGETFILE))) {
      if((parm->targetFile = (char *) malloc(SZ(_argv[i]))) == NULL)
        errmsg(56, ERR_ALLOCMEM, NULL);
      strcpy(parm->targetFile, & _argv[i][SZ(PARM_TARGETFILE)]);
    }
	if (!strncmp(PARM_DEBUGFILE, _argv[i], SZ(PARM_DEBUGFILE))) {
		if ((parm->debugFile = (char *)malloc(SZ(_argv[i]))) == NULL)
			errmsg(57, ERR_ALLOCMEM, NULL);
		strcpy(parm->debugFile, &_argv[i][SZ(PARM_DEBUGFILE)]);
	}
	if(!strncmp(PARM_DEBUGMODE, _argv[i], SZ(PARM_DEBUGMODE))) {
      parm->debugMode = -1;
    }
  }
  if(parm->sourceFile == NULL)
    errmsg(39, ERR_NOVALIDPARM, NULL);
}

#endif /* __PARMUTIL_H */

