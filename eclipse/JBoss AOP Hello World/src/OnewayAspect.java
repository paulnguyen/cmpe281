import org.jboss.aop.joinpoint.*;

public class OnewayAspect {
	private static class Task implements Runnable {
		private MethodInvocation invocation;

		public Task(MethodInvocation invocation) {
			this.invocation = invocation;
		}

		public void run() {
			try {
				invocation.invokeNext();
			} catch (Throwable ignore) {
			}
		}
	}

	public Object oneway(MethodInvocation invocation) throws Throwable {
		
		System.out.println( "In Oneway Advice...") ;
		
		MethodInvocation copy = (MethodInvocation) invocation.copy();
		Thread t = new Thread(new Task(copy));
		t.setDaemon(false);
		t.start();
		return null;
	}
}
