package org.jboss.aop.advice.annotation;

import java.lang.reflect.Method;

import org.jboss.aop.advice.AdviceMethodProperties;
import org.jboss.aop.advice.annotation.AdviceMethodFactory.ReturnType;
import org.jboss.aop.advice.annotation.assignability.DegreeAlgorithm;

/**
 * Contains information about an advice method and its matching process.
 * 
 * @author Flavia Rainone
 */
abstract class AdviceInfo implements Comparable<AdviceInfo>
{
   protected static final DegreeAlgorithm DEGREE = DegreeAlgorithm.getInstance();
   // the righest the rank the better this advice is
   protected int rank;
   // advice method
   protected Method method;
   // since method.getParameterTypes creates a vector, better store this information
   // instead of calling repeatedly getParameterTypes
   protected Class<?>[] parameterTypes;
   
   /**
    * Creates an advice info.
    * 
    * @param method the advice method
    * @param rank   the initial rank value of this advice
    */
   protected AdviceInfo(Method method, int rank)
   {
      this.method = method;
      this.rank = rank;
      this.parameterTypes = method.getParameterTypes();
   }
   
   /**
    * Returns the distance in hierarchy between the annotated parameter identified by
    * <code>annotationIndex</code>, and the expected type of this parameter.
    * 
    * @param annotationIndex  identifies a parameter annotation rule
    * @param properties       contains information about the queried advice method
    * @return                 the assignability degree if there is a parameter with the
    *                         annotation identified by <code>typeIndex</code>;
    *                         {@link DegreeAlgorithm#NOT_ASSIGNABLE_DEGREE} otherwise.
    */
   public final short getReturnAssignabilityDegree(AdviceMethodProperties properties)
   {
      Class<?> returnType = this.method.getReturnType();
      if (returnType == void.class)
      {
         return DegreeAlgorithm.NOT_ASSIGNABLE_DEGREE;
      }
      short degree = DEGREE.getAssignabilityDegree(properties.getJoinpointReturnType(),
            returnType);
      if (degree == DegreeAlgorithm.NOT_ASSIGNABLE_DEGREE)
      {
         // return type is Object.class and join point return type is not
         // Object is worse than join point return type, but better than NOT_ASSIGNABLE
         return DegreeAlgorithm.MAX_DEGREE;
      }
      return degree;
   }

   /**
    * Returns the rank of this advice.
    * @return the rank value
    */
   public final int getRank()
   {
      return rank;
   }
   
   /**
    * Compares this advice info against <code>o</code> in decreasing order of the rank
    * value.
    */
   public int compareTo(AdviceInfo o)
   {
      return o.rank - rank;
   }
   
   public String toString()
   {
      return method.toString();
   }
   
   /**
    * Matches this advice with the joinpoint to be intercepted, indicating whether it
    * can be the answer to the method query contained in <code>properties</code>.
    * 
    * @param properties        contains information about the queried method
    * @param returnType        the expected return type
    * @return                  <code>true</code> only if this advice is valid
    */
   public abstract boolean matches(AdviceMethodProperties properties,
         ReturnType returnType);
   
   /**
    * Resets all information that has been set during matching.
    */
   public abstract void resetMatching();

   /**
    * Returns the distance in hierarchy between the annotated parameter identified by
    * <code>annotationIndex</code>, and the expected type of this parameter.
    * 
    * @param annotationIndex  identifies a parameter annotation rule
    * @param isContextRule    is <code>true</code>, <code>annotationIndex</code>
    *                         refers to a context rule, instead of a factory rule
    *                         (context rules include target, caller, arg, etc;
    *                         factory rule are dependent on the advice factory,
    *                         and include join point, throwable, return and others)
    * @param properties       contains information about the queried advice method
    * @return                 the assignability degree if there is a parameter with the
    *                         annotation identified by <code>typeIndex</code>;
    *                         {@link DegreeAlgorithm#NOT_ASSIGNABLE_DEGREE} otherwise.
    */
   public abstract short getAssignabilityDegree(int annotationIndex,
         boolean isContextRule, AdviceMethodProperties properties);
   
   /**
    * Assign information of this advice to <code>properties</code>.
    * 
    * @param properties contains information about the queried advice method.
    */
   public abstract void assignAdviceInfo(AdviceMethodProperties properties);
}