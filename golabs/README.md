
# Go Programming | APIs in Go


## References:

    https://docs.c9.io/docs/writing-a-go-app
    https://golang.org/
    https://golang.org/doc/
    https://golang.org/doc/code.html
    https://tour.golang.org/
    https://golang.org/doc/effective_go.html
    https://github.com/golang/go


## Downloads:

    https://golang.org/dl/
    https://golang.org/doc/install?download=go1.7.3.linux-amd64.tar.gz
    https://storage.googleapis.com/golang/go1.7.3.linux-amd64.tar.gz

## Updating Go (on Cloud9)

    sudo rm -rf /opt/go
    wget https://storage.googleapis.com/golang/go1.7.3.linux-amd64.tar.gz
    sudo tar -C /opt -xzf go1.7.3.linux-amd64.tar.gz
    go version
    rm go1.7.3.linux-amd64.tar.gz 

## Install Go on AWS EC2 | CentOS / Amazon Linux

    sudo wget https://storage.googleapis.com/golang/go1.7.3.linux-amd64.tar.gz
    tar -xzf go1.7.3.linux-amd64.tar.gz 
    export GOROOT=/home/ec2-user/go
    export PATH=$PATH:$GOROOT/bin 
    export GOBIN=$GOROOT/bin 
    mkdir ~/golang/ 
    export GOPATH=~/golang/ 
    export PATH=$GOPATH/bin:$PATH 


    Add system vars on ~/.bashrc

    export GOROOT=/home/ec2-user/go
    export PATH=$PATH:$GOROOT/bin
    export GOPATH=/home/ec2-user/golang/
    export PATH=$GOPATH/bin:$PATH
    export GOBIN=/home/ec2-user/golang/bin/

## Setup Go & Tools on Mac OSX

    1. Install Homebrew: 
     
    	 ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
    	 xcode-select --install
    	 brew doctor
    	 
    2. Install Mac Apps Manager

    	 brew install caskroom/cask/brew-cask

    3. Install Git (If Needed)

		git --version.  (should be 2.8.2+)
		brew install git
		
    4. Install Other Repo Tools

		brew install mercurial
		brew install bazaar

    5. Check Installation

    	brew info git
		brew info mercurial
		brew info bazaar

    6. Install Go

		brew install go
		go version

## Configure & Test Go

	1. Set $GOPATH environment variable points to Go Project directory.
	2. Add $GOPATH/bin to $PATH
	
	NOTE: Can set using Bash Alias (run to reset in each Go Project Root)
	
	alias gopath='export GOPATH=$(pwd);export PATH=$PATH:$GOPATH/bin'
	
	3. Test Environment

	go get github.com/cloudnativego/hello
	gopath
	cd src/github.com/cloudnativego/hello
	go build
	./hello
	curl http://localhost:8080
	


    