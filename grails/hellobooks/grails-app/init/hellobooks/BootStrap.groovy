package hellobooks

class BootStrap {

    def init = { servletContext ->

    	new Author(name:"Stephen King")
        .addToBooks(new Book(title:"The Stand", releaseDate: new Date().parse('YYYY','1978'), ISBN:"978-0-385-12168-2"))
        .addToBooks(new Book(title:"The Shining", releaseDate: new Date().parse('YYYY','1977'), ISBN:"978-0-385-12167-5"))
        .save()

        new Book( title:"Groovy in Action", 
                          releaseDate: new Date().parse('YYYY','2007'), 
                          ISBN:"978-1-932-39484-9" )
        .addToAuthors(new Author(name:"Dierk Koenig"))
        .addToAuthors(new Author(name:"Guillaume Laforge"))
        .save()

    }
    
    def destroy = {
    }
}

