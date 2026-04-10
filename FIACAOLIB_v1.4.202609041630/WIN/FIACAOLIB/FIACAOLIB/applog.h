
/*
 * APPLOG.H
 * Copyright (C) 2017 by Luiz Marcio F A Viana, 22/12/2017
 */

#ifndef __APPLOG_H
#define __APPLOG_H

FILE *f_log = NULL;

FILE* openLog(char* logFile)
{
	if (f_log == NULL)
		f_log = fopen(logFile, "w");
	return f_log;
}

bool isOpenedLog()
{
	return (f_log != NULL);
}

void writeLog(const char* msg)
{
	if(f_log != NULL)
		fprintf(f_log, msg);
}

void writeLog(const char* msg, char* val)
{
	if (f_log != NULL)
		fprintf(f_log, msg, val);
}

void writeLog(const char* msg, int val)
{
	if (f_log != NULL)
		fprintf(f_log, msg, val);
}

void writeLog(const char* msg, char* val1, char* val2, char* val3)
{
	if (f_log != NULL)
		fprintf(f_log, msg, val1, val2, val3);
}

void writeLog(const char* msg, char* val1, int val2, int val3)
{
	if (f_log != NULL)
		fprintf(f_log, msg, val1, val2, val3);
}

void writeLog(const char* msg, char* val1, char* val2, char* val3, char* val4)
{
	if (f_log != NULL)
		fprintf(f_log, msg, val1, val2, val3, val4);
}

void writeLog(const char* msg, char* val1, char* val2, char* val3, int val4)
{
	if (f_log != NULL)
		fprintf(f_log, msg, val1, val2, val3, val4);
}

void writeLog(const char* msg, char* val1, int val2)
{
	if (f_log != NULL)
		fprintf(f_log, msg, val1, val2);
}

void closeLog()
{
	if (f_log != NULL) {
		fflush(f_log);
		fclose(f_log);
	}
}

#endif   /* __APPLOG_H */
