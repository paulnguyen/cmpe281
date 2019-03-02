
clean:
	rm -rf node_modules
	rm -rf package-lock.json

install:
	npm install

run: install
	node app.js

test-post:
	curl -X POST http://localhost:8080/gumball

test-get:
	curl -X GET  http://localhost:8080/gumball/$(ordnum)