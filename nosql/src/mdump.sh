
echo ======================================
echo VOLUME HEADER - offset: 0 
echo ======================================
hexdump -v -C -s 0 -n 100 master.db 
echo ======================================
echo PAGE 0 - BLOCK 0 - offset: 8192
echo ======================================
hexdump -v -C -s 8192 -n 112 master.db
echo ======================================
echo PAGE 0 - BLOCK 1 - offset: 16384 
echo ======================================
hexdump -v -C -s 16384 -n 1000 master.db  
