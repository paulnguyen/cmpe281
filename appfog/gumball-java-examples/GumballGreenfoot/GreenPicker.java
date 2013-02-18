import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GreenPicker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GreenPicker extends Picker
{

    public void pick()
    {
        World world = getWorld();
        Gumball gb = new GreenGumball() ;
        world.addObject( gb, 500, 500 ) ;
        setMessage ( gb.getClass().getName() ) ;
    }

}
