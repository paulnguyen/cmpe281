#!/bin/sh


# Shell Variables

OPT=""
OPT1=""
OPT2=""
DEBUG="TRUE"
AUTH="FALSE"

## Set Echo Command Flavor

PROMPT=""
OS=`uname -a | cut -f1 -d" "`
if [ "$OS" = "Darwin" ] ; then
    PROMPT="echo"
else
    PROMPT="echo -e"
fi ;

#
# Shell Functions 
# for Menu Operations
#

docker_images() {
	docker images
}

docker_rmi() {
	IMG_ID=`docker images --format "table {{.ID}}\t{{.Repository}}\t{{.Tag}}" | grep $CONTAINER | tr -s ' ' | tr ' ' '|' | cut -f 1 -d '|' | head -1`
	while [ "$IMG_ID" != "" ]
	do
		echo "Removing Image: $IMG_ID"
 		docker rmi -f $IMG_ID
		IMG_ID=`docker images --format "table {{.ID}}\t{{.Repository}}\t{{.Tag}}" | grep $CONTAINER | tr -s ' ' | tr ' ' '|' | cut -f 1 -d '|' | head -1`
	done
}

docker_rmi_all() {
	IMG_ID=`docker images --format "table {{.ID}}\t{{.Repository}}\t{{.Tag}}" | grep -v "k8s" | tr -s ' ' | tr ' ' '|' | cut -f 1 -d '|' | tail -n +2 | head -1`
	while [ "$IMG_ID" != "" ]
	do
		echo "Removing Image: $IMG_ID"
 		docker rmi -f $IMG_ID
		IMG_ID=`docker images --format "table {{.ID}}\t{{.Repository}}\t{{.Tag}}" | tr -s ' ' | tr ' ' '|' | cut -f 1 -d '|' | tail -n +2 | head -1`
	done
}


docker_ps() {
	echo "Running Containers:"
	echo " "
	docker ps --format "table {{.ID}}\t{{.Names}}\t{{.Image}}\t{{.Status}}\t{{.Ports}}\t"
}

docker_stop() {
	docker stop $CONTAINER > /dev/null 2>&1
	docker rm $CONTAINER > /dev/null 2>&1
}

docker_stop_all () {
	INST_ID=`docker ps --format "table {{.ID}}\t{{.Names}}\t{{.Image}}\t{{.Status}}\t" | tr -s ' ' | tr ' ' '|' | cut -f 2 -d '|' | tail -n +2 | head -1`
	while [ "$INST_ID" != "" ]
	do
		echo "Stopping Instance: $INST_ID"
 		docker stop $INST_ID  > /dev/null 2>&1
 		docker rm $INST_ID > /dev/null 2>&1
		INST_ID=`docker ps --format "table {{.ID}}\t{{.Names}}\t{{.Image}}\t{{.Status}}\t" | tr -s ' ' | tr ' ' '|' | cut -f 2 -d '|' | tail -n +2 | head -1`
	done	
}


okay_pause() {
	$PROMPT "\n[Okay] \c"; 
	read ans ; 
}


##
## MAIN MENU LOOP
##

while [ "$OPT" != "X" ]  
do
	clear
	echo ""
	echo "============================================" ;
	echo "         D O C K E R     A D M I N          " ;
	echo "============================================" ;
	echo "[i] images     - Show Docker Images         " ;
	echo "[p] ps         - Show Running Containers    " ;
	echo "[c] cleanup    - Remove Local Images        " ;
	echo " " 
	echo "[X] Exit Menu                               " ;
	echo " "
	$PROMPT "Selection: \c"
	read OPT OPT1 OPT2
	case $OPT in
		i|images)	    echo " " ; docker_images ; okay_pause ;;
		p|ps) 			echo " " ; docker_ps ; okay_pause ;;
		c|C|cleanup) 	echo " " ; docker_stop_all; docker_rmi_all ; okay_pause ;;
		x|X) 			clear ; OPT="X" ; echo "Exiting " ;; 
	esac
done

