/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
/**
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 44138 $
 */
public class POJO implements ImplementsInterface, ImplementingInterface
{
   public POJO() {}
   public void methodFromImplementing()
   {
	   System.out.println("*** POJO methodFromImplementing");
   }
   public void methodFromImplements()
   {
	   System.out.println("*** POJO methodFromImplements");
   }

   public void methodFromImplementingSuper()
   {
	   System.out.println("*** POJO methodFromImplementingSuper");
   }

   public void methodFromImplementsSuper()
   {
	   System.out.println("*** POJO methodFromImplementsSuper");
   }

   public void ownMethod()
   {
	   System.out.println("*** POJO ownMethod");
   }
}



