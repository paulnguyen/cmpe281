/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */

/**
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 44138 $
 */
public class Driver
{
   public static void main(String[] args) throws Exception
   {
      System.out.println("--- POJO ---");
      POJO pojo = new POJO();

      System.out.println("--- POJO.methodFromImplements ---");
   	  pojo.methodFromImplements();

      System.out.println("--- POJO.methodFromImplementingSuper (should not intercept) ---");
   	  pojo.methodFromImplementsSuper();

      System.out.println("--- POJO.methodFromImplementing ---");
   	  pojo.methodFromImplementing();

      System.out.println("--- POJO.methodFromImplementingSuper ---");
   	  pojo.methodFromImplementingSuper();

      System.out.println("--- POJO.ownMethod (should not intercept) ---");
   	  pojo.ownMethod();
   }
}
