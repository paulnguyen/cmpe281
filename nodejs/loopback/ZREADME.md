

	  _                       _                _    
	 | |                     | |              | |   
	 | |     ___   ___  _ __ | |__   __ _  ___| | __
	 | |    / _ \ / _ \| '_ \| '_ \ / _` |/ __| |/ /
	 | |___| (_) | (_) | |_) | |_) | (_| | (__|   < 
	 |______\___/ \___/| .__/|_.__/ \__,_|\___|_|\_\
	                   | |                          
	                   |_|          
           

# Resources

	http://loopback.io/
	http://loopback.io/getting-started/

# Install

	 npm install -g strongloop
	 
# Create Hello World App

	$ slc loopback
	
	> Name = helloworld
	> Application Type?
	    api-server (A LoopBack API server with local User auth) 
  	    empty-server (An empty LoopBack API, without any configured models or datasources) 
	  ❯ hello-world (A project containing a basic working example, including a memory database) 
	
	npm install (sets up project files and dependencies)
	
# Startup Loopback App

 Change directory to your app
    $ cd helloworld

  Create a model in your app
    $ slc loopback:model

  Compose your API, run, deploy, profile, and monitor it with Arc
    $ slc arc

  Note:  To Install MySQL Connector
    $ npm install --save loopback-connector-mysql

  Run the app
    $ node .

# Default Startup Port

	Web server listening at: http://0.0.0.0:3000
	Browse your REST API at http://0.0.0.0:3000/explorer

# Create Person Model

	$ slc loopback:model
	
	? Enter the model name: person
	? Select the data-source to attach person to: db (memory)
	? Select model's base class PersistedModel
	? Expose person via the REST API? Yes
	? Custom plural form (used to build REST URL): people
	? Common model or server only? common
	Let's add some person properties now.
	
	Enter an empty property name when done.
	? Property name: firstname
   		invoke   loopback:property
	? Property type: string
	? Required? (y/N) Y
	? Default value[leave blank for none]: 
	
	Let's add another person property.
	Enter an empty property name when done.
	? Property name: lastname
   		invoke   loopback:property
	? Property type: string
	? Required? Yes
	? Default value[leave blank for none]: 
	
	
	



	
