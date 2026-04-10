/*
 * FIACAO.H
 * Copyright (C) 1996 by Luiz Marcio F A Viana, 8/15/96
 */

#ifndef __FIACAO_H
#define __FIACAO_H

/* definicao do nivel de depuracao da aplicacao
*/
#define __DEBUG_LEVEL_00__          00
#define __DEBUG_LEVEL_01__          01
#define __DEBUG_LEVEL_02__          02
//
#define __DEBUG_LEVEL_99__          99

#define __DEGUG_LEVEL__             00

/* definicao das constantes de retorno das funcoes
*/
#define RTERR  0    // falha na execucao da funcao
#define RTNORM 1    // sucesso na execucao da funcao

/* definicao da constante do nome do arquivo temporario
*/
//#define FILE_TEMP   "/ACADAPPL/FIACAO/Temp/$temp$.~~$"
#define FILE_TEMP   "/home/lmarcio/9997-TLMV/002-AICADx/AICADx_v1.0/Temp/$temp$.~~$"
//#define FILE_TEMP   "Temp/$temp$.~~$"

/* definicao da constante do nome do arquivo de saida padrao
*/
#define FILE_TARGET "fiacao.dat"

/* definicao dos identificadores dos interruptores three-way e four-way
*/
#define ID_3W "3w"
#define ID_4W "4w"

/* definicao das constantes que identificam o tipo de interruptor
*/
#define _IS  0
#define _I3W 1
#define _I4W 2

/* definicao das constantes que definem o tipo de elemento
*/
#define T_CARGA      0
#define T_COMANDO    1
#define T_QUADRO     2
#define T_CAMPAINHA  3
#define T_ILUMINACAO 4
#define T_CAIXA      5
#define T_DESVIO     6
#define T_CALHA      7

/* definicao das constantes que identificam a fiacao
 * pertencente ao circuito que passa pelo eletroduto
 */
#define __FIA_TR    0x0001     // terra
#define __FIA_N    0x0002     // neutro
#define __FIA_F1   0x0004     // fase (1)
#define __FIA_F2   0x0008     // fase (2)
#define __FIA_F3   0x0010     // fase (3)
#define __FIA_RC   0x0020     // retorno campainha
#define __FIA_R1   0x0040     // retorno (1)
#define __FIA_R2   0x0080     // retorno (2)
#define __FIA_R3   0x0100     // retorno (3)
#define __FIA_R4   0x0200     // retorno (4)
#define __FIA_R5   0x0400     // retorno (5)
#define __FIA_R6   0x0800     // retorno (6)
#define __FIA_R7   0x1000     // retorno (7)
#define __FIA_R8   0x2000     // retorno (8)
#define __FIA_R9   0x4000     // retorno (9)
#define __FIA_R10  0x8000     // retorno (10)

/* definicao das constantes que identificam o sistema de fase das economias
*/
#define _FN    (__FIA_F1 + __FIA_N )                                    // fase + neutro
#define _2F    (__FIA_F1 + __FIA_F2)                                    // bi-fasico
#define _2FN   (__FIA_F1 + __FIA_F2 + __FIA_N )                         // bi-fasico + neutro
#define _3F    (__FIA_F1 + __FIA_F2 + __FIA_F3)                         // tri-fasico
#define _3FN   (__FIA_F1 + __FIA_F2 + __FIA_F3 + __FIA_N )              // tri-fasico + neutro
#define _FNT   (__FIA_F1 + __FIA_N  + __FIA_TR)                         // fase + neutro + terra
#define _2FT   (__FIA_F1 + __FIA_F2 + __FIA_TR)                         // bi-fasico + terra
#define _2FNT  (__FIA_F1 + __FIA_F2 + __FIA_N  + __FIA_TR)              // bi-fasico + neutro + terra
#define _3FT   (__FIA_F1 + __FIA_F2 + __FIA_F3 + __FIA_TR)              // tri-fasico + terra
#define _3FNT  (__FIA_F1 + __FIA_F2 + __FIA_F3 + __FIA_N + __FIA_TR)    // tri-fasico + neutro + terra

#endif   /* __FIACAO_H */
