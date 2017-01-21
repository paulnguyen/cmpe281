
     ,-----.,--.                  ,--. ,---.   ,--.,------.  ,------.
    '  .--./|  | ,---. ,--.,--. ,-|  || o   \  |  ||  .-.  \ |  .---'
    |  |    |  || .-. ||  ||  |' .-. |`..'  |  |  ||  |  \  :|  `--, 
    '  '--'\|  |' '-' ''  ''  '\ `-' | .'  /   |  ||  '--'  /|  `---.
     `-----'`--' `---'  `----'  `---'  `--'    `--'`-------' `------'
    ----------------------------------------------------------------- 


# Tomcat 7.0 


    https://tomcat.apache.org/tomcat-7.0-doc/appdev/installation.html
    http://tomcat.apache.org/download-70.cgi

# Tomcat Setup

    cd /home/ubuntu
    wget http://apache.forsale.plus/tomcat/tomcat-7/v7.0.73/bin/apache-tomcat-7.0.73.zip
    unzip apache-tomcat-7.0.73.zip 
    mv apache-tomcat-7.0.73/ apache-tomcat
    
# Tomcat Startup

    cd /home/ubuntu/apache-tomcat
    ./bin/startup.sh
    tail -f ./logs/catalina.out
    
# Test Console

     http://cloud-paulnguyen.c9users.io:8080/  (Tomcat Console)
     http://cloud-paulnguyen.c9users.io:8080/xmlrpc/xmlrpc.html  (App Console)
     
     Update File:  conf/tomcat-users.xml 
        <role rolename="manager-gui"/>
        <user username="tomcat" password="tomcat" roles="manager-gui"/>
    
# Tomcat Compile Notes

    http://tomcat.apache.org/whichversion.html
    http://docs.oracle.com/javase/7/docs/technotes/tools/windows/javac.html
    http://stackoverflow.com/questions/14378187/how-to-compile-a-servlet-from-command-prompt
    http://www.avajava.com/tutorials/lessons/how-do-i-create-a-war-file-using-the-jar-command.html
    
    

    

    