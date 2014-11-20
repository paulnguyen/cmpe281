
import java.util.concurrent.locks.ReentrantLock ;

public class Tester
{     
    
    public static void runRobotTest1() throws Exception
    {
        Robot rbt = new Robot() ;
        RobotCommandThread order = new RobotCommandThread(rbt) ;
        RobotHumanInDangerEventThread savehuman = new RobotHumanInDangerEventThread(rbt) ;
        RobotSelfInDangerActionThread saveself = new RobotSelfInDangerActionThread(rbt) ;
        saveself.start();
        Thread.currentThread().sleep(2000);
        order.start();
        Thread.currentThread().sleep(5000);
        savehuman.start();        
    }

    
    public static void runRobotTest2() throws Exception
    {
        Robot rbt = new Robot() ;
        RobotCommandThread order = new RobotCommandThread(rbt) ;
        RobotHumanInDangerEventThread savehuman = new RobotHumanInDangerEventThread(rbt) ;
        RobotSelfInDangerActionThread saveself = new RobotSelfInDangerActionThread(rbt) ;
        saveself.start();
        Thread.currentThread().sleep(2000);
        order.start();
        Thread.currentThread().sleep(15000);
        savehuman.start();        
    }

 
    
}