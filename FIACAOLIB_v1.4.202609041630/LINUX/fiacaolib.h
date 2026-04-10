/*
 * FIACAODLL.H
 * Copyright (C) 2017 by Luiz Marcio F A Viana, 22/12/2017
 */

#ifndef FIACAODLL_H
#define FIACAODLL_H

#ifdef __FIACAOLIB__

#include<jni.h>

extern "C" {

JNIEXPORT jint JNICALL Java_br_com_tlmv_aicadxmod_eletrica_fiacao_FiacaoHelper_processaFiacao(JNIEnv*, jobject, jstring, jstring, jstring, jstring, jint);

}

#endif

int processaFiacao(char* homeDir, char* srcFile, char* targetFile, char* debugFile, int debugMode);

#endif /* FIACAODLL_H */
