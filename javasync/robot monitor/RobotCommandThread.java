public class RobotCommandThread extends Thread
{
    private Robot theRobot ;
    
    public RobotCommandThread(Robot rbt)
    {
        this.theRobot = rbt ;
    }

    public void run()
    {     
        try {
           theRobot.walkIntoDangerOrder() ;              
        } catch ( Exception e )
        {
            System.out.println( e.getMessage() ) ;   
        }
    }
}