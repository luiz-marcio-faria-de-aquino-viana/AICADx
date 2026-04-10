
/*
 * STRUTIL.H
 * Copyright (C) 1996 by Luiz Marcio F A Viana, 7/10/96
 */

 #ifndef __STRUTIL_H
 #define __STRUTIL_H
 
 #include"all.h"
 
 typedef char str_t [256];
 
 /* declaracao das rotinas de manipulacao de subcadeias de strings
 */
 // strhead_fia: funcao que retorna o tamanho da cadeia (dst) formada pelos
 // elementos mais a esquerda da primeira ocorrencia do delimitador (d)
 //  dst - string que recebera o valor da cadeia (destino)
 //  src - string que sera analisada (fonte)
 //  d   - caracter delimitador da cadeia
 int strhead_fia(char *dst, char *src, char d);
 
 // strtail_fia: funcao que retorna o tamanho da cadeia (dst) formada pelos
 // elementos mais a direita da primeira ocorrencia do delimitador (d)
 //  dst - string que recebera o valor da cadeia (destino)
 //  src - string que sera analisada (fonte)
 //  d   - caracter delimitador da cadeia
 int strtail_fia(char *dst, char *src, char d);
 
 // strpiece_fia: funcao que retorna o tamanho da cadeia (dst) formada pelos
 // elementos contidos entre a ocorrencias (n-1) e (n) do delimitador (d)
 //  dst - string que recebera o valor da cadeia (destino)
 //  src - string que sera analisada (fonte)
 //  n   - numero de ocorrencias do delimitador
 //  d   - caracter delimitador da cadeia
 int strpiece_fia(char *dst, char *src, size_t n, char d);
 
 // strcpy_fia: funcao que copia a cadeia de entrada (src) na cadeia de saida (dst)
 //  dst - string que recebera o valor da cadeia (destino)
 //  src - string que sera analisada (fonte)
 int strcpy_fia(char *dst, char *src);
 
 // strupr_fia: funcao que compara a primeira cadeia de caracteres com a sugunda
 //  str - string que recebera o valor da cadeia que sera convertida em MAIUSCULA
 char *strupr_fia(char *str);
 
 // strncasecmp_fia: funcao que compara a primeira cadeia de caracteres com a sugunda
 //  src1 - string que recebera o valor da primeira cadeia
 //  src2 - string que recebera o valor da segunda cadeia
 //  sz   - comprimento maximo da cadeia
 int strncasecmp_fia(char* src1, char* src2, int sz);
 
 /* implementacao das rotinas de manipulacao de subcadeias de strings
 */
 int strhead_fia(char *dst, char *src, char d) {
   char *p;
   size_t n = 0;
   p = src;
   while((*p != '\0') && (*p != d) && (*p != '\n') && (*p != '\r')) {
     *dst++ = *p++;
     n += 1;
   }
   *dst = '\0';
   return (int)n;
 }
 
 int strtail_fia(char *dst, char *src, char d) {
    char *p;
    *dst = '\0';

    p = src;
    if(*p != '\0') {
      while((*p != '\0') && (*p != d) && (*p != '\n') && (*p != '\r')) {
        p++;
      }
      strcpy_fia(dst, p);
    }
    return (int)strlen(dst);
 }
 
 int strpiece_fia(char *dst, char *src, size_t n, char d) {
  str_t tmp;
  size_t len_dst = 0;
  size_t len_tmp = 0;
   *dst = '\0';
   while(n > 0) {
     len_dst = strhead_fia(dst, src, d);
     len_tmp = strtail_fia(tmp, src, d);
     if(len_tmp == 0) break;
     strcpy_fia(src, & tmp[1]);
     n -= 1;
   }
   return (int)len_dst;
 }

 int strcpy_fia(char *dst, char *src) {
   char *p;
   size_t n = 0;
   p = src;
   while((*p != '\0') && (*p != '\n') && (*p != '\r')) {
     *dst++ = *p++;
     n += 1;
   }
   *dst = '\0';
   return (int)n;
 }
 
 char *strupr_fia(char *str)
 {
   unsigned char *p = (unsigned char*)str;
   while (*p) {
     (*p) = toupper(*p);
     p++;
   }
   return str;
 }
 
 int strncasecmp_fia(char* src1, char* src2, int sz)
 {
   char* p_src1 = src1;
   char* p_src2 = src2;
   for(int i = 0; i < sz; i++) {
     if( (p_src1 == NULL) && (p_src2 == NULL) ) 
       return  0;
     else if( (p_src1 == NULL) && (p_src2 != NULL) ) 
       return -1;
     else if( (p_src1 != NULL) && (p_src2 == NULL) ) 
       return  1;
   
     char ch_src1 = toupper(*p_src1);
     char ch_src2 = toupper(*p_src2);
 
     if (ch_src1 < ch_src2)
       return -1;
     else if (ch_src1 > ch_src2)
       return 1;
 
     p_src1++;
     p_src2++;
   }
   return 0;
 }
 
 #endif  /* __STRUTIL_H */