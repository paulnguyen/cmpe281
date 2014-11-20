
import java.util.concurrent.locks.ReentrantLock ;

public class Tester
{ 
   public static void runTest1()
    {
        Lock theLock = new Lock() ;
        LockTestThread T1 = new LockTestThread(theLock) ;
        LockTestThread T2 = new LockTestThread(theLock) ;
        T1.start() ;
        T2.start() ;
    }

    public static void runTest2()
    {
        ReentrantLock theLock = new ReentrantLock() ;
        JavaLockTestThread T1 = new JavaLockTestThread(theLock) ;
        JavaLockTestThread T2 = new JavaLockTestThread(theLock) ;
        T1.start() ;
        T2.start() ;
    }
    
    public static void runTest3( BankAccount acct )
    {
        BankAccountThread T1 = new BankAccountThread( acct, 50.00 ) ;
        BankAccountThread T2 = new BankAccountThread( acct, 100.00 ) ;
        BankAccountThread T3 = new BankAccountThread( acct, 150.00 ) ;
        BankAccountThread T4 = new BankAccountThread( acct, 200.00 ) ;
        T1.start() ;
        T2.start() ;
        T3.start() ;
        T4.start() ;
        
        /* sample run from main thread
        chk.Deposit(100) ;
        chk.SignalFundsAvailable() ;
        chk.BroadcastFundsAvailable() ;
        chk.Deposit(100);
        chk.BroadcastFundsAvailable() ;
        chk.Deposit(1000);
        chk.BroadcastFundsAvailable() ;
        */
    }
 
   public static void runTest4( BankAccountJavaSync acct )
    {
        BankAccountJavaSyncThread T1 = new BankAccountJavaSyncThread( acct, 50.00 ) ;
        BankAccountJavaSyncThread T2 = new BankAccountJavaSyncThread( acct, 100.00 ) ;
        BankAccountJavaSyncThread T3 = new BankAccountJavaSyncThread( acct, 150.00 ) ;
        BankAccountJavaSyncThread T4 = new BankAccountJavaSyncThread( acct, 200.00 ) ;
        T1.start() ;
        T2.start() ;
        T3.start() ;
        T4.start() ;
    }
    
   public static void runTest5()
    {
        BoundedBuffer buffer = new BoundedBuffer(10) ;
        ProducerThread P1 = new ProducerThread( "P1", buffer ) ;
        ConsumerThread C1 = new ConsumerThread( "C1", buffer ) ;
        ConsumerThread C2 = new ConsumerThread( "C2", buffer ) ;
        P1.start() ;
        C1.start() ;
        C2.start() ;
    }    
    
}