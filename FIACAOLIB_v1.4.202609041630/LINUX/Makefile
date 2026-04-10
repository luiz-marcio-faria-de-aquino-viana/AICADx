CC = /usr/bin/gcc
LIB_CFLAGS = -ggdb -O0 -Wall -fPIC -shared -I/java/include -I/java/include/linux -D__FIACAOLIB__
EXE_CFLAGS = -ggdb -O0 -Wall -fPIC

LIB_OBJS = libfiacaolib.so
EXE_OBJS = fiacao

SRC = fiacaolib.cpp

all: 
	$(CC) $(LIB_CFLAGS) -o $(LIB_OBJS) $(SRC)
	cp -aux ./libfiacaolib.so ../../../Bin/libfiacaolib.so
	$(CC) $(EXE_CFLAGS) -o $(EXE_OBJS) $(SRC)
	cp -aux ./fiacao ../../../Bin/fiacao

clean:
	rm -f *~
	rm -f *o
	rm -f libfiacaolib.so
	rm -f fiacao
