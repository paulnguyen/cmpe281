dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    username = "cmpe281"
    password = "cmpe281"
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = "net.sf.ehcache.hibernate.EhCacheProvider"
}

// environment specific settings
environments {
      development {
		    dataSource {
			      dbCreate = "update" // one of 'create', 'create-drop','update'
			      //url = "jdbc:mysql://localhost:3306/GrailsGumball_development"
				  url = "jdbc:mysql://localhost:3306/cmpe281"
		    }
	  }
	  test {
		    dataSource {
			      dbCreate = "update"
			      url = "jdbc:mysql://localhost:3306/GrailsGumball_test"
		    }
	  }	  
	  /**
	  production {
		    dataSource {
			      dbCreate = "update"
			      url = "jdbc:mysql://localhost:3306/GrailsGumballAppFog"
		    }
	  }
	  **/	  
	  production {
		  def envVar = System.env.VCAP_SERVICES
		  def credentials = envVar?grails.converters.JSON.parse(envVar)["mysql-5.1"][0]["credentials"]:null
	   
		  dataSource {
			 pooled = true
			 dbCreate = "update"
			 driverClassName = "com.mysql.jdbc.Driver"
			 url =  credentials?"jdbc:mysql://${credentials.hostname}:${credentials.port}/${credentials.name}?useUnicode=yes&characterEncoding=UTF-8":""
			 username = credentials?credentials.username:""
			 password = credentails?credentials.password:""
		  }
	   }
	  
}