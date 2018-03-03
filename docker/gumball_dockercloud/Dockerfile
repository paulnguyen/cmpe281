FROM golang:latest 
EXPOSE 80
RUN mkdir /app 
ADD . /app/ 
WORKDIR /app 
ENV GOPATH /app
RUN cd /app ; go install gumball
CMD ["/app/bin/gumball"]
