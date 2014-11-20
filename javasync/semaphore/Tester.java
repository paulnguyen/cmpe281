public class Tester
{
    public static void runTest1()
    {
        Semaphore sem = new Semaphore(2) ;
        ThreadTest T1 = new ThreadTest("T1",sem) ;
        ThreadTest T2 = new ThreadTest("T2",sem) ;
        ThreadTest T3 = new ThreadTest("T3",sem) ;
        ThreadTest T4 = new ThreadTest("T4",sem) ;
        T1.start() ;
        T2.start() ;
        T3.start() ;
        T4.start() ;
    }

    public static void runTest2()
    {
        java.util.concurrent.Semaphore sem = new java.util.concurrent.Semaphore(2) ;
        ThreadTestJavaSemaphore T1 = new ThreadTestJavaSemaphore("T1",sem) ;
        ThreadTestJavaSemaphore T2 = new ThreadTestJavaSemaphore("T2",sem) ;
        ThreadTestJavaSemaphore T3 = new ThreadTestJavaSemaphore("T3",sem) ;
        ThreadTestJavaSemaphore T4 = new ThreadTestJavaSemaphore("T4",sem) ;
        T1.start() ;
        T2.start() ;
        T3.start() ;
        T4.start() ;
    }   

    public static void runTest3()
    {
        java.util.concurrent.Semaphore sem = new java.util.concurrent.Semaphore(2,true) ;
        ThreadTestJavaSemaphore T1 = new ThreadTestJavaSemaphore("T1",sem) ;
        ThreadTestJavaSemaphore T2 = new ThreadTestJavaSemaphore("T2",sem) ;
        ThreadTestJavaSemaphore T3 = new ThreadTestJavaSemaphore("T3",sem) ;
        ThreadTestJavaSemaphore T4 = new ThreadTestJavaSemaphore("T4",sem) ;
        T1.start() ;
        T2.start() ;
        T3.start() ;
        T4.start() ;
    }   
    
       
    public static void runTest4()
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






