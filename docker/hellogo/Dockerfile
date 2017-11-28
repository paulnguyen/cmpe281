FROM golang:latest 
EXPOSE 8080
RUN mkdir /app 
ADD . /app/ 
WORKDIR /app 
ENV GOPATH /app
RUN cd /app ; go build github.com/cloudnativego/hello
CMD ["/app/hello"]
