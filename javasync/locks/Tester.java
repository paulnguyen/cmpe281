
import java.util.concurrent.locks.ReentrantLock ;

public class Tester
{ 

   public static void runTest0()
    {
        BadLock theLock = new BadLock() ;
        BadLockTestThread T1 = new BadLockTestThread(theLock) ;
        BadLockTestThread T2 = new BadLockTestThread(theLock) ;
        T1.start() ;
        T2.start() ;
    }

   public static void runTest1()
    {
        Lock theLock = new Lock() ;
        LockTestThread T1 = new LockTestThread(theLock) ;
        LockTestThread T2 = new LockTestThread(theLock) ;
        LockTestThread T3 = new LockTestThread(theLock) ;
        T1.start() ;
        T2.start() ;
        T3.start() ;
    }

    public static void runTest2()
    {
        ReentrantLock theLock = new ReentrantLock() ;
        JavaLockTestThread T1 = new JavaLockTestThread(theLock) ;
        JavaLockTestThread T2 = new JavaLockTestThread(theLock) ;
        T1.start() ;
        T2.start() ;
    }
    

    
}