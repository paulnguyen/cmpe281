for i in {1..100000}}
do
	echo $i
	/usr/bin/curl http://pnguyen-grails-gumball-v1.cfapps.io/gumballMachine/index  > /dev/null
	sleep 2 
done
