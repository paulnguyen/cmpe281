import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Inspector here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Inspector extends Alien
{

    private ArrayList<Picker> pickers = new ArrayList<Picker>() ;

	public void addPicker(Picker obj) {
	    pickers.add(obj) ;
	}

	public void removePicker(Picker obj) {
        pickers.remove(obj) ;
	}
	
	public void inspect( Coin coin )
	{
	   System.out.println( "Coin: " + coin.getClass() ) ;
	   setMessage( coin.getClass().getName() ) ;
	   int whichPicker = Greenfoot.getRandomNumber( pickers.size() ) ;
	   Picker pickerChosen = pickers.get( whichPicker ) ;
	   System.out.println( "Picker: " + pickerChosen.getClass() ) ;
	   if ( coin.getClass() == Quarter.class )
	       pickerChosen.pick() ;
	}
	
}
