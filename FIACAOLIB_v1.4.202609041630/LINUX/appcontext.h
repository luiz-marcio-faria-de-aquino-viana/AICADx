
/*
 * APPCONTEXT.H
 * Copyright (C) 2017 by Luiz Marcio F A Viana, 23/12/2017
 */

#ifndef APPCONTEXT_H
#define APPCONTEXT_H

#define MAX_PATH 255

char ctx_homeDir[MAX_PATH];
//char ctx_licencaDir[MAX_PATH];
//char ctx_licencaFile[MAX_PATH];

void initContext(char* homeDir)
{
	strcpy(ctx_homeDir, homeDir);
	//sprintf(ctx_licencaDir, "%sLicenca\\", ctx_homeDir);
	//sprintf(ctx_licencaFile, "%s_ai-licenca.lic", ctx_licencaDir);
}

#endif /* APPLICENCA_H */
