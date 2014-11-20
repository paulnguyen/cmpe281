public class RobotSelfInDangerActionThread extends Thread
{
    private Robot theRobot ;
    
    public RobotSelfInDangerActionThread(Robot rbt)
    {
        this.theRobot = rbt ;
    }

    public void run()
    {     
        try {
            while ( true )
            {
                if (theRobot.isSelfInDanger())
                    theRobot.selfInDangerAction() ;
                sleep(5000) ;
            }
        } catch ( Exception e )
        {
            System.out.println( e.getMessage() ) ;   
        }
    }
}