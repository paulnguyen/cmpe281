public class RobotHumanInDangerEventThread extends Thread
{
    private Robot theRobot ;
    
    public RobotHumanInDangerEventThread(Robot rbt)
    {
        this.theRobot = rbt ;
    }

    public void run()
    {     
        try {
            theRobot.humansInDangerEvent() ;
        } catch ( Exception e )
        {
            System.out.println( e.getMessage() ) ;   
        }
    }
}