
CC = /usr/bin/gcc
CFLAGS = -ggdb -O0 -Wall -fPIC -shared -I/java/include -I/java/include/linux
#CFLAGS = -ggdb -O0 -Wall -m32 -shared -I/java/include -I/java/include/linux

OBJS = libfiacaolib.so

SRC = fiacaolib.cpp

all: 
	$(CC) $(CFLAGS) -o $(OBJS) $(SRC)
	cp -aux ./*so ../../../Bin/

clean:
	rm -f *~
	rm -f *o
	rm -f *so

