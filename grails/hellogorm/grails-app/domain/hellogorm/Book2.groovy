package hellogorm

class Book2 {

	    static belongsTo = Author2
	    static hasMany = [authors:Author2]
	    String title

}
