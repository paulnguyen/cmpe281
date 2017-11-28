   	  _   _    _      ____                                ____ ____  _     
	 | | | |  / \    |  _ \ _ __ _____  ___   _     _    / ___/ ___|| |    
	 | |_| | / _ \   | |_) | '__/ _ \ \/ / | | |  _| |_  \___ \___ \| |    
	 |  _  |/ ___ \  |  __/| | | (_) >  <| |_| | |_   _|  ___) |__) | |___ 
	 |_| |_/_/   \_\ |_|   |_|  \___/_/\_\\__, |   |_|   |____/____/|_____|
                                      |___/    


# Docker Cloud HA Proxy

	https://github.com/docker/dockercloud-haproxy 

	HAproxy image that autoreconfigures itself when used in Docker Cloud
	https://cloud.docker.com/

# Docker Cloud with Let's Encrypt

	https://dtucker.co.uk/hack/docker-letsencrypt-pelican.html
	https://github.com/ixc/letsencrypt-docker

# Stack file example: haproxy+ssl.yml

	web:
	  autoredeploy: true
	  environment:
    	- FORCE_SSL=yes
    	- 'VIRTUAL_HOST=*,https://*'
	  image: 'dockercloud/hello-world:latest'
	  target_num_containers: 2
	haproxy:
	  image: 'dockercloud/haproxy:1.6.3'
	  links:
	    - web
	    - letsencrypt
	  ports:
	    - '80:80'
	    - '443:443'
	  roles:
	    - global
	letsencrypt:
	  environment:
	    - DOMAINS=api.nguyenresearch.com
	    - EMAIL=paul@nguyenresearch.com
	    - 'HAPROXY_IMAGE=dockercloud/haproxy:1.6.3'
	  expose:
	    - '80'
	  image: 'interaction/letsencrypt:master'


# Stack file (with self-signed cert)

	web:
	    autoredeploy: true
	    environment:
	      - FORCE_SSL=yes
	      - 'VIRTUAL_HOST=*,https://*'
	    image: 'dockercloud/hello-world:latest'
	    target_num_containers: 2
	haproxy:
	  environment:
	    - |
	      DEFAULT_SSL_CERT=-----BEGIN RSA PRIVATE KEY-----
	      MIIEpQIBAAKCAQEA3cp1UqMNJMWoHsFXWYGV080Rg8jLnhLMRFYHuF8ER56TOEi0
	      YAkEQ0uuhDzmeqjfOBWvq7UN0n/EQ1RkLaILp+De01qC4DpsEi9fa47OlSYnkffL
	      rvumJQugevruphnE9hRPd8wTXwT9Psr9qpj/6o/YmlqIBo5AhWH+TKZpoYdczuKP
	      qPfeUueZI13BdLvtIyamh5VLpIT1z1lgLTi1V0ndPaYSlJu+ZHKp33ZfAXPtdtiW
	      ZX3LAkKq/F060vYALKECaMzraoF8hoVFw1XhoiZfD+uMzrEoicSSDNPzf61YR66B
	      0HfEAsa5DnKXYmdz6/SiCJeC5PUNoHa/UtpKgwIDAQABAoIBAQDBIGl28sogKgkR
	      9tND+kXn71qAAZnkZIQAQVVpoztLzfZt/Ukrks5mdwFY3trnFiOBZ8jXYMnPcTgK
	      VPMClWdG4NXG/rqV2+l9EEkrlGeoklfEkByJm1F6UpyEWfrzId9TVC46p7Z6eBK4
	      2fN22NGHg4heJ9TouHfQbZsNTZ8Pafpqg/lCyoDVr3nWji09IsofQLupas59dZxa
	      zzTjEDmiNW20ntid9Vfm+Cnv91mvbZIXJqSIQVaTWA524bG9Myx4qTfgWUM8kmrS
	      jGg7hMa7jQ/gi44QlqJ4fRuG7dJnraI393I+32KU+n1TMRQxkD7kgAsatafser7n
	      QlZBTxlxAoGBAP7JHW0bj8BKoPbyEa3j7v2JcLp3Lankktu8UAGbZWO4EQ3tuwQ7
	      hkubSjw1tQB7PJ3R62rsxR0/nbPUWkaseh4hRDEM2xl4HStt482TGklJZxhYLPCr
	      ga3hAIbbV6tceaH8Lwf6UymPXj7xchTXoGE9R2H61du4Bp31uQLjpRKrAoGBAN7Z
	      FW4Z9Qy89barulCxv+86WCg2CjUvGk0j/bGfE5O3UNGr7DLrGg+BdWc+PTpfeotV
	      6qCwpfyNlg86a9LWjbYuc/6MUg3/H+LhhQ5yIH+x43VBvntIGzGBl4L2sgo7yzig
	      jZw3JiBi52oqyj3Zq47+Z+rEkttw1jb9ywVrAeeJAoGAYQk3q9ZTaWkC5QpMsW2e
	      F/HAfUPmoE3toMKXtgT5GIGB/UwNbg89lo4ON+A8j3JgI7tPbem/KOSRq2u09jW6
	      TL9euU+sIocclO3FVQYxfLf4C82JUCABw7dt++6N9TXDHW5uextbkPiJbG+Ig2ec
	      LWypQyfFS2lLG936GJ+avwECgYEAr8wo77Zibi4S2IwYatgORbaNHQukedgys3AV
	      Di4jz3/prb4mkvSMlnbWGyAqRQZVMd4RPbqc9uLq4hCARs8i8/kpYEFp6ci9asnl
	      oUO/B31ZpoK6tZ5odWF6GMJsKU6KPp3JmNaYqSvcyw/+g1RX1nIzoCx6fcpB8xe7
	      NQb5daECgYEAsuWv71VGelB9wYuwGIrKbAPFerHcnrrV+bxEc5wYvnU5JmgvKJkV
	      IKqvappKHJeqRJl43HZAkIauNrKTgu9peA4IXDUYwyHarbd6q3lHvpYbkZead/s/
	      fn736AJQ0OYLG59UA/xoxQQsGsmDbHCEASzTQ2uWbt1+P3LF4oMCnOM=
	      -----END RSA PRIVATE KEY-----
	      -----BEGIN CERTIFICATE-----
	      MIIDCDCCAfCgAwIBAgIJAPP3kRKrMYHmMA0GCSqGSIb3DQEBBQUAMAwxCjAIBgNV
	      BAMUASowHhcNMTcwNDEzMDc0MDA3WhcNMTcwNTEzMDc0MDA3WjAMMQowCAYDVQQD
	      FAEqMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3cp1UqMNJMWoHsFX
	      WYGV080Rg8jLnhLMRFYHuF8ER56TOEi0YAkEQ0uuhDzmeqjfOBWvq7UN0n/EQ1Rk
	      LaILp+De01qC4DpsEi9fa47OlSYnkffLrvumJQugevruphnE9hRPd8wTXwT9Psr9
	      qpj/6o/YmlqIBo5AhWH+TKZpoYdczuKPqPfeUueZI13BdLvtIyamh5VLpIT1z1lg
	      LTi1V0ndPaYSlJu+ZHKp33ZfAXPtdtiWZX3LAkKq/F060vYALKECaMzraoF8hoVF
	      w1XhoiZfD+uMzrEoicSSDNPzf61YR66B0HfEAsa5DnKXYmdz6/SiCJeC5PUNoHa/
	      UtpKgwIDAQABo20wazAdBgNVHQ4EFgQUWSM9qh5CT131fgoPp/FmBWJ8laswPAYD
	      VR0jBDUwM4AUWSM9qh5CT131fgoPp/FmBWJ8lauhEKQOMAwxCjAIBgNVBAMUASqC
	      CQDz95ESqzGB5jAMBgNVHRMEBTADAQH/MA0GCSqGSIb3DQEBBQUAA4IBAQANXwsX
	      3taLmVbiZLF1MNAF7jsxnrOAavg9lc85Ov1Qr+Z60+AJ44CaJtB1hg7UvkICRLQ8
	      LNjF4pwV+/Y0h4+lD5V5fMXa1SweWEXlsrHw7FjsuaYwFiHcU+2/CZQAH2Wc1m/N
	      V2z1mSHDMQOskPFLbeEjnZqLREc01V/XgqsBKxuCgXuI6lntRAGvIGek29As1CE2
	      TVdnUT+rr6PU2Bl5+yKQQCvXJ3fx0rlNGuf6sQkryywYMtONWk8gVgk3IC4rLMiL
	      s3KYY2u5A8AT6CJoeYFKdB38ydKA0OusVDR/9Ply+30EO8OfCB1jCY8hyXFjGtpb
	      HCbQSupHz2fuF5ka
	      -----END CERTIFICATE-----
	  image: 'dockercloud/haproxy:1.6.3'
	  links:
	    - web
	    - letsencrypt
	  ports:
	    - '443:443'
	  restart: on-failure
	  roles:
	    - global
	  volumes_from:
	    - letsencrypt
	letsencrypt:
	  image: 'interaction/letsencrypt:master'
	  environment:
	    - DOMAINS=api.nguyenresearch.com
	    - EMAIL=paul@nguyenresearch.com
	    - 'HAPROXY_IMAGE=dockercloud/haproxy:1.6.3'
	  ports:
	    - '80:80'
	  restart: on-failure
	  roles:
	    - global
	  volumes:
	    - /var/run/docker.sock:/var/run/docker.sock

