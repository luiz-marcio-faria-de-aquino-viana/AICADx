
/*
 * APPLICENCA.H
 * Copyright (C) 2017 by Luiz Marcio F A Viana, 23/12/2017
 */

#ifndef APPLICENCA_H
#define APPLICENCA_H

#define BUFFSZ 1024

char lic_hostName[BUFFSZ];
char lic_hostId[BUFFSZ];
char lic_serverUrl[BUFFSZ];
char lic_serialNum[BUFFSZ];
char lic_startDate[BUFFSZ];
char lic_endDate[BUFFSZ];
char lic_modulo[BUFFSZ];
char lic_appKey[BUFFSZ];
char lic_signature[BUFFSZ];

void loadLicenca(char* licFile)
{
	FILE *f_lic = NULL;

	char sbuf[BUFFSZ];
	int numread = -1;

	char key[BUFFSZ];
	char val[BUFFSZ];

	f_lic = fopen(licFile, "r");
	if (f_lic != NULL)
	{
		while ((numread = fread(sbuf, sizeof(char), BUFFSZ, f_lic)) > 0)
		{
			strhead_fia(key, sbuf, '=');
			strtail_fia(val, sbuf, '=');

			if (strcmp(key, "HOSTNAME") == 0)
				strcpy(lic_hostName, val);
			else if (strcmp(key, "HOSTID") == 0)
				strcpy(lic_hostId, val);
			else if (strcmp(key, "SERVERURL") == 0)
				strcpy(lic_serverUrl, val);
			else if (strcmp(key, "SERIALNUM") == 0)
				strcpy(lic_serialNum, val);
			else if (strcmp(key, "STARTDATE") == 0)
				strcpy(lic_startDate, val);
			else if (strcmp(key, "ENDDATE") == 0)
				strcpy(lic_endDate, val);
			else if (strcmp(key, "MODULO") == 0)
				strcpy(lic_modulo, val);
			else if (strcmp(key, "APPKEY") == 0)
				strcpy(lic_appKey, val);
			else if (strcmp(key, "SIGNATURE") == 0)
				strcpy(lic_signature, val);
		}
		fclose(f_lic);
	}
}

#endif /* APPLICENCA_H */
