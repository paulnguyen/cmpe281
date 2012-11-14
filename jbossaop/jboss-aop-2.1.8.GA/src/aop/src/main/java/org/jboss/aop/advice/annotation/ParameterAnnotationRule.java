package org.jboss.aop.advice.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import org.jboss.aop.advice.AdviceMethodProperties;
import org.jboss.aop.joinpoint.FieldReadInvocation;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.JoinPointBean;

/**
 * Represents the set of rules associated with a parameter annotation. Every parameter
 * that has this annotation must comply with this rule.
 * 
 * @author Flavia Rainone
 */
enum ParameterAnnotationRule
{
   /**
    * Rule for parameter annotation {@link JoinPoint}.
    */
   JOIN_POINT (
         JoinPoint.class, JoinPointBean.class, null,
         AdviceMethodProperties.JOINPOINT_ARG, 700, false, true)
   {
      public Object getAssignableFrom(AdviceMethodProperties properties)
      {
         return properties.getJoinPointBeanType();
      }
   },
   
   /**
    * Rule for parameter annotation {@link Invocation}.
    */
   INVOCATION (
         JoinPoint.class, Invocation.class, null,
         AdviceMethodProperties.INVOCATION_ARG, 700, false, true)
   {
      public Object getAssignableFrom(AdviceMethodProperties properties)
      {
         return properties.getInvocationType();
      }
   },

   /**
    * Rule for parameter annotation {@link Target}.
    */
   TARGET (
         Target.class, null, null, AdviceMethodProperties.TARGET_ARG, 300, false, true)
   {
      public Object getAssignableFrom(AdviceMethodProperties properties)
      {
         return properties.getTargetType();
      }
      
      public boolean lowerRankGrade(AdviceMethodProperties properties)
      {
         return !properties.isTargetAvailable();
      }
   },

   /**
    * Rule for parameter annotation {@link Caller}.
    */
   CALLER (
         Caller.class, null, null, AdviceMethodProperties.CALLER_ARG, 150, false, true)
   {
      public Object getAssignableFrom(AdviceMethodProperties properties)
      {
         return properties.getCallerType();
      }
      
      public boolean lowerRankGrade(AdviceMethodProperties properties)
      {
         return !properties.isCallerAvailable();
      }
   },
   
   /**
    * Rule for parameter annotation {@link Return}.
    */
   RETURN (
         Return.class, null, null, AdviceMethodProperties.RETURN_ARG, 50, false, true)
   {
      public Object getAssignableFrom(AdviceMethodProperties properties)
      {
         return properties.getJoinpointReturnType();
      }
   },
   
   /**
    * Rule for parameter annotation {@link Thrown}.
    */
   OPTIONAL_THROWN (
         Thrown.class, Throwable.class, null, AdviceMethodProperties.THROWABLE_ARG,
         50, false, true),
   
   /**
    * Rule for parameter annotation {@link Thrown}.
    */
   MANDATORY_THROWN (
         Thrown.class, Throwable.class, RuntimeException.class,
         AdviceMethodProperties.THROWABLE_ARG, 50, true, true)
   {
      public Object getAssignableFrom(AdviceMethodProperties properties)
      {
         Type[] joinpointExceptions = properties.getJoinpointExceptions();
         if (joinpointExceptions == null || joinpointExceptions.length == 0)
         {
            return super.getAssignableFrom(properties);
         }
         return joinpointExceptions;
      }   
   },
      
   /**
    * Rule for parameter annotation {@link Arg}.
    */
   ARG (
         Arg.class, null, null, AdviceMethodProperties.ARG_ARG, 2, false, false)
   {
      public Object getAssignableFrom(AdviceMethodProperties properties)
      {
         return properties.getJoinpointParameters();
      }
   },
   
   /**
    * Rule for parameter annotation {@link Args}.
    */
   ARGS (
         Args.class, Object[].class, null, AdviceMethodProperties.ARGS_ARG, 1, false, true)
   {
      public boolean lowerRankGrade(AdviceMethodProperties properties)
      {
         return properties.getInvocationType() == FieldReadInvocation.class;
      } 
   };
   
   private Class<? extends Annotation> annotation;
   private Class<?> assignableFrom;
   private Class<?> superType;
   private int rankGrade;
   private boolean mandatory;
   private boolean singleEnforced;
   private int property;
   
   /**
    * Constructor.
    * 
    * @param annotation      the parameter annotation
    * @param assignableFrom  the expected type from which the annotated parameter type
    *                        must be assignable
    * @param property        the property number identifying the parameter type. Must
    *                        be one defined in {@link AdviceMethodProperties}
    * @param rankGrade       the rank grade a parameter annotated with <code>annotatio
    *                        </code> is worth 
    * @param mandatory       indicates whether there must be a parameter annotated with
    *                        <code>annotation</code>
    * @param singleEnforced  indicates whether the multiple ocurrence of <code>
    *                        annotation</code> in the advice method parameters is
    *                        forbidden
    */
   private ParameterAnnotationRule(Class<? extends Annotation> annotation,
         Class<?> assignableFrom, Class<?> superType, int property,
         int rankGrade, boolean mandatory, boolean singleEnforced)
   {
      this.annotation = annotation;
      this.assignableFrom = assignableFrom;
      this.superType = superType;
      this.property = property;
      this.rankGrade = rankGrade;
      this.mandatory = mandatory;
      this.singleEnforced = singleEnforced;      
   }

   /**
    * Returns the annotation associated with this rule.
    * @return the annotation associated with this rule.
    */
   public final Class<? extends Annotation> getAnnotation()
   {
      return annotation;
   }

   /**
    * Returns the type from which the annotated parameter must be assignable.
    * 
    * @param properties describes the queried advice method
    * 
    * @return the type or types from which the annotated parameter must be assignable.
    *         The return type can be <code>java.lang.reflect.Type</code> or
    *         <code>java.lang.reflect.Type[]</code>. If this rule
    *         {@link #isSingleEnforced() is not single enforced}, the return type
    *         must be <code>java.lang.reflect.Type[]</code>.
    */
   public Object getAssignableFrom(AdviceMethodProperties properties)
   {
      return assignableFrom;
   }
   
   /**
    * Returns the type that can be the super type of the annotated parameter type.
    * This is an optional rule, and can be applied only if the annotated parameter
    * type is not assignable from the type(s) defined by {@link
    * #getAssignableFrom(AdviceMethodProperties)}.
    * 
    * @return the type that can be the super type of the annotated parameter type.
    *         Can be {@code null}.
    */
   public Class<?> getSuperType()
   {
      return superType;
   }
   
   /**
    * Returns the property identifying the annotated parameter type.
    * 
    * @return one of the constant values defined in {@link AdviceMethodProperties}
    */
   public final int getProperty()
   {
      return this.property;
   }

   /**
    * Returns the rank grade an annotated parameter is worth for an instance of
    * <code>AdviceInfo</code>.
    * 
    * @return the rank grade
    */
   public final int getRankGrade()
   {
      return rankGrade;
   }
   
   /**
    * Returns <code>true</code> if, in the context especified by <code>properties
    * </code>, an advice method should have a lower grade when he attends to this
    * rule.
    * 
    * @param properties describes the queried advice method
    * @return <code>true</code> if an advice compliant with this rule should have
    *         a lower rank grade
    */
   public boolean lowerRankGrade(AdviceMethodProperties properties)
   {
      return false;
   }
   
   /**
    * Indicates whether this annotation is mandatory.
    * 
    * @return <code>true</code> only if this annotation is mandatory
    */
   public final boolean isMandatory()
   {
      return mandatory;
   }
   
   /**
    * Indicates whether a multiple occurrence of this annotation is forbidden.
    *  
    * @return <code>true</code> only if there can be only one occurence of this annotation
    *         on the parameters of an advice method
    */
   public final boolean isSingleEnforced()
   {
      return singleEnforced;
   }
   
   public String toString()
   {
      return "@" + annotation.getSimpleName();
   }
}