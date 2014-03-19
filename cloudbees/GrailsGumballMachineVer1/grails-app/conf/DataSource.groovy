hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
			pooled = true
			driverClassName = "org.h2.Driver"
			username = "sa"
			password = ""
        }
    }
    test {
        dataSource {
            dbCreate = "create-drop"  // 'create', 'create-drop','update'
			url = "jdbc:mysql://localhost:3306/cmpe281"
			driverClassName = "com.mysql.jdbc.Driver"
			username = "cmpe281"
			password = "cmpe281"
        }
    }
	production {
		dataSource {
		   dialect = 'org.hibernate.dialect.MySQL5InnoDBDialect'
		   pooled = false
		   dbCreate = 'create' // use 'update', 'validate', 'create' or 'create-drop'
		   jndiName = 'java:comp/env/jdbc/gumball_db'
		}
	 }
}


