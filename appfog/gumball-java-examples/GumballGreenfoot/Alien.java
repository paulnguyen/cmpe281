import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Alien here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Alien extends Actor
{
    Message m = new Message() ;

    public Alien()
    {
        GreenfootImage image = getImage() ;
        image.scale( 150, 180 ) ; 
    }

    protected void setMessage( String msg ) 
    {
        int x, y ;
        x = getX() + 10;  
        y = getY() + 10;  
        World world = getWorld();
        if ( m.getWorld() != null )
        {
            world.removeObject( m ) ;
        }
        world.addObject( m, x, y ) ;
        m.setText( msg ) ;        
    }    
    
    
}
