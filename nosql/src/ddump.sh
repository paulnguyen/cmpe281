echo ======================================
echo VOLUME HEADER - offset: 0 
echo ======================================
hexdump -v -C -s 0 -n 112 data.db
echo ======================================
echo PAGE 0 - BLOCK 0 - offset: 8192
echo ======================================
hexdump -v -C -s 8192 -n 112 data.db
echo ======================================
echo PAGE 0 - BLOCK 1 - offset: 16384 
echo ======================================
hexdump -v -C -s 16384 -n 112 data.db
echo ======================================
echo PAGE 0 - BLOCK 2 - offset: 24576
echo ======================================
hexdump -v -C -s 24576 -n 112 data.db
echo ======================================
echo PAGE 0 - BLOCK 3 - offset: 32768
echo ======================================
hexdump -v -C -s 32768 -n 112 data.db
echo ======================================
echo PAGE 1 - BLOCK 0 - offset: 4202496 
echo ======================================
hexdump -v -C -s 4202496 -n 112 data.db