

# Fork the Node.js project into your own GitHub (Public) Repo

	Fork Repo:  https://github.com/paulnguyen/nodejs

# Clone the Forked Repo into your AWS Cloud9 Workspace from a Terminal Window

	git clone https://github.com/paulnguyen/nodejs.git

# Install MongoDB in your AWS Cloud9 Workspace

## Configure the package management system (yum).

    Create a /etc/yum.repos.d/mongodb-org-3.6.repo file so that you can install MongoDB directly, using yum.

    sudo touch /etc/yum.repos.d/mongodb-org-3.6.repo
    sudo vi /etc/yum.repos.d/mongodb-org-3.6.repo

```
    [mongodb-org-3.6]
    name=MongoDB Repository
    baseurl=https://repo.mongodb.org/yum/amazon/2013.03/mongodb-org/3.6/x86_64/
    gpgcheck=1
    enabled=1
    gpgkey=https://www.mongodb.org/static/pgp/server-3.6.asc
```  

## Install MongoDB by running the following command from the Terminal:

    sudo yum install -y mongodb-org

## Create the default DB Path for Mongo in your workspace:

    sudo mkdir -p /data/db
    sudo chown ec2-user /data/db  
    ls -ld /data/db

    drwxr-xr-x 2 ec2-user root 4096 Feb 14 15:47 /data/db

## Then, from the terminal, run the following command to start Mongo:

    mongod --bind_ip=$IP --nojournal

    The output will include:
    ...
    waiting for connections on port 27017

## Now you can open the mongo shell in a new Terminal, running following command:

    mongo

## To stop the MongoDB instance press Control + C in the Terminal where mongod is running.

    $ mongo
    mongo> db
    test

# Install Gumball Collection

	Add Mongodb Admin User
	See:  https://docs.mongodb.com/manual/reference/method/db.createUser/

```
	 use test
	 db.createUser(
	    {
	      user: "cmpe281",
	      pwd: "cmpe281",
	      roles: [ "readWrite", "dbAdmin" ]
	    }
	 )
```

	Gumball MongoDB Collection (Create Document)

```
	db.gumball.insert(
	{ 
	  id: 1,
	  countGumballs: 8,
	  modelNumber: 'M102988',
	  serialNumber: '1234998871109' 
	}
	) ;
```

	Gumball MongoDB Collection - Find Gumball Document

```
	db.gumball.find( { id: 1 } ) ;
```

# Run the Node.js App on in your AWS Cloud9 Workspace

REF: https://docs.aws.amazon.com/cloud9/latest/user-guide/ide.html

# Heroku Toolbelt Install on AWS Cloud9

REF: https://devcenter.heroku.com/articles/heroku-cli

## Update Cloud9
	
	sudo yum -y update

## Install NVM

	curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.33.0/install.sh | bash

## Install Node 8.9.4

	nvm install v8.9.4
	node --version
	nvm alias default 8.9.4
	nvm ls

## Install Heroku Toolbelt   

	npm install -g heroku-cli
	heroku --version

## Heroku Login

	heroku login

## List Heroku Apps (in your account).  Create a new App with Node.js Build Pack.   

	heroku apps

## Tail Heroku App (i.e. pnguyen-gumball)

	heroku logs --tail --app pnguyen-gumball

# Config MongoDB in your Heroku Account

REF: https://devcenter.heroku.com/categories/data-management

# Deploy Node.js App to Heroku

REF: https://devcenter.heroku.com/categories/deployment

1. Connect Heroku App to your Git Repo and set for Auto Deploy from Master Branch 
2. On Manual Deploy Section, select Version4 and click on "Deploy Branch"
3. Upon Completion of Deploy, click on the "View" Button





