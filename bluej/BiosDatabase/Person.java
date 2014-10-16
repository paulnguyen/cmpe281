import java.util.HashMap ;
import java.util.ArrayList ;
import java.util.Date ;

public class Person
{
    public HashMap<String,String> names = new HashMap<String,String>()  ;
    public ArrayList<Award> awards = new ArrayList<Award>() ;
    public ArrayList<String> contribs = new ArrayList<String>() ;
    public Date birth ;
    public Date death ;
    
    public Person()
    {
        Award a1 = new Award( "W.W. McDowell Award", 1967, "IEEE Computer Society" ) ;
        Award a2 = new Award( "National Medal of Science", 1975, "National Science Foundation" ) ;
        Award a3 = new Award( "Turing Award", 1977, "ACM" ) ; 
        Award a4 = new Award( "Draper Prize", 1993, "National Academy of Engineering" ) ;
        names.put( "First", "John" ) ;
        names.put( "Last", "Backus" ) ;
        awards.add( a1 ) ;
        awards.add( a2 ) ;
        awards.add( a3 ) ;
        awards.add( a4 ) ;
        contribs.add( "Fortan" ) ;
        contribs.add( "ALGOL" ) ;
        contribs.add( "Backus-Naur Form" ) ;
        contribs.add( "FP" ) ;
        birth = new Date( 1924, 12, 3 ) ;
        death = new Date( 2007, 03, 17 ) ;
    }

}
