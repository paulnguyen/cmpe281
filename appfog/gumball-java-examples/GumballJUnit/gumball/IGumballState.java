package gumball;


/**
 * Write a description of interface IGumballState here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface IGumballState
{
    public void insertQuarter() throws GumballException ;
    public void turnCrank() throws GumballException ;
}
