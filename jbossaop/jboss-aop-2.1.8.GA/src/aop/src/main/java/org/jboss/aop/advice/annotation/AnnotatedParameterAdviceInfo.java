package org.jboss.aop.advice.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.jboss.aop.advice.AdviceMethodProperties;
import org.jboss.aop.advice.AdviceType;
import org.jboss.aop.advice.InvalidAdviceException;
import org.jboss.aop.advice.annotation.AdviceMethodFactory.ReturnType;
import org.jboss.aop.advice.annotation.assignability.AssignabilityAlgorithm;
import org.jboss.aop.advice.annotation.assignability.VariableHierarchy;

/**
 * Information about an advice method whose parameters should annotated according to
 * <code>ParameterAnnotationRule</code>s.
 * 
 * @author Flavia Rainone
 */
class AnnotatedParameterAdviceInfo extends AdviceInfo
{
   // the annotated parameter types
   private ParameterAnnotationType paramTypes[];
   // the context dependent annotated parameter types
   private ParameterAnnotationType contextParamTypes[];
   // the type of advice this advice info represents
   private AdviceType adviceType;
   // hierarchy of variable types
   private VariableHierarchy hierarchy;
   // indicates whether this advice mthod was considered valid during the
   // construction of this object
   private boolean prevalidated;
   
   /**
    * Creates an annotated parameter advice info.
    * 
    * @param properties        the properties to which <code>method</code> must
    *                          comply with
    * @param method            the advice method
    * @param rules             the annnotated parameter rules this method should
    *                          comply with
    * @param contextRules      second priority rules this method should comply with
    * @param mutuallyExclusive a list of mutually exclusive context parameter rules
    * @param compulsory        a list of the annotated parameters whose use is
    *                          compulsory only if the precondition annotation is
    *                          present among the annotated parameters.
    * 
    * @throws InvalidAdviceException thrown when the advice method does not
    *         comply with a rule regardless of the joinpoint being intercepted.
    */
   public AnnotatedParameterAdviceInfo(AdviceMethodProperties properties,
         AdviceType adviceType, Method method, ParameterAnnotationRule[] rules,
         ParameterAnnotationRule[] contextRules, int[][] mutuallyExclusive,
         int[][] compulsory, ReturnType returnType)
      throws InvalidAdviceException
   {
      super(method, 0);
      this.paramTypes = createParameterAnnotationTypes(rules);
      this.contextParamTypes = createParameterAnnotationTypes(contextRules);
      this.adviceType = adviceType;
      this.hierarchy = new VariableHierarchy();
      this.applyRules(properties);
      
      if (returnType == ReturnType.VOID && method.getReturnType()!= void.class)
      {
         throw new InvalidAdviceException("The " + adviceType +
               " advice method '" + method + "' return type must be void");
      }
      
      for (int i = 0; i < mutuallyExclusive.length; i++)
      {
         int[] exclusiveParamTypes = mutuallyExclusive[i];
         int found = -1;
         for (int j = 0; j < exclusiveParamTypes.length; j++)
         {
            if (contextParamTypes[exclusiveParamTypes[j]].isSet())
            {
               if (found != -1)
               {
                  throw new InvalidAdviceException(
                        "Mutually exclusive parameter annotations '"
                        + contextParamTypes[exclusiveParamTypes[found]].rule
                        + "' and '" + contextParamTypes[exclusiveParamTypes[j]].rule
                        + "' found on " + adviceType + " advice method '" + method +
                        "'");
               }
               found = j;
            }
         }
      }
      
      if (compulsory != null)
      {
        for (int i = 0; i < compulsory.length; i++)
        {
           ParameterAnnotationType precondition = paramTypes[compulsory[i][0]];
           if (precondition.isSet())
           {
              for (int j = 1; j < compulsory[i].length; j++)
              {
                 if (!paramTypes[compulsory[i][j]].isSet())
                 {
                    throw new InvalidAdviceException(
                          "Compulsory " + paramTypes[compulsory[i][j]].rule
                          + "-annotated parameter not found on " + adviceType + 
                          " advice method '" + method +
                          "' (this parameter is compulsory in the presence of a " + 
                          precondition.rule + "-annotated parameter)");
                 }
              }
           }
        }
      }
   }
      
   public boolean matches(AdviceMethodProperties properties, ReturnType returnType)
   {
      if (!prevalidated)
      {
         return false;
      }
      for (ParameterAnnotationType paramType: paramTypes)
      {
         if (!paramType.validate(properties))
         {
            return false;
         }
      }
      
      for (ParameterAnnotationType paramType: contextParamTypes)
      {
         if (!paramType.validate(properties))
         {
            return false;
         }
      }
      
      if (method.getReturnType() == void.class && returnType == ReturnType.NOT_VOID
            && properties.getJoinpointReturnType() != void.class)
      {
         AdviceMethodFactory.appendNewMatchingMessage(method,
            "return value cannot be void (it must match the joinpoint return type)");
         return false;
      }
      else if(method.getReturnType() != void.class &&
            method.getReturnType() != Object.class &&
            !AssignabilityAlgorithm.FROM_VARIABLE.isAssignable(
                  properties.getJoinpointReturnType(),
                  method.getGenericReturnType(), hierarchy))
      {
         AdviceMethodFactory.appendNewMatchingMessage(method,
               "return value cannot be assigned to type '");
         AdviceMethodFactory.appendMatchingMessage(properties.getJoinpointReturnType());
         AdviceMethodFactory.appendMatchingMessage("'");
         return false;
      }
      return true;
   }

   public void resetMatching()
   {
      for (int i = 0;  i < paramTypes.length; i++)
      {
         paramTypes[i].resetValidation();
      }
      for (int i = 0; i < contextParamTypes.length; i++)
      {
         contextParamTypes[i].resetValidation();
      }
   }
   
   public short getAssignabilityDegree(int annotationIndex, boolean isContextRule,
         AdviceMethodProperties properties)
   {
      if (isContextRule)
      {
         return contextParamTypes[annotationIndex].getAssignabilityDegree(properties);
      }
      return paramTypes[annotationIndex].getAssignabilityDegree(properties);
   }
   
   public void assignAdviceInfo(AdviceMethodProperties properties)
   {
      int args[] = new int[parameterTypes.length];
      for (int i = 0; i < paramTypes.length; i++)
      {
         paramTypes[i].assignParameterInfo(args);
      }
      for (int i = 0; i < contextParamTypes.length; i++)
      {
         contextParamTypes[i].assignParameterInfo(args);
      }
      properties.setFoundProperties(this.method, args);
   }
   
   /**
    * Creates a parameter annotation type array correpondent to the parameter
    * annotation rules contained in <code>rules</code>.
    * 
    * @param rules the parameter annotation rules
    * 
    * @return a parameter annotation type array correspondent to <code>rules</code>
    */
   private final ParameterAnnotationType[] createParameterAnnotationTypes(
         ParameterAnnotationRule[] rules)
   {
      ParameterAnnotationType[] types = new ParameterAnnotationType[rules.length];
      // create appropriate annotated parameter types for each AP rule
      for (int i = 0; i < rules.length; i++)
      {
         if (rules[i].isSingleEnforced())
         {
            types[i] = new SingleParameterType(rules[i]);
         }
         else
         {
            types[i] = new MultipleParameterType(rules[i],
                  method.getParameterTypes().length);
         }
      }
      return types;
   }
   
   /**
    * Applies all parameter annotation rules to the advice method parameters.
    * 
    * @param properties the properties which the searched advice method must comply
    *                   with
    */
   private void applyRules(AdviceMethodProperties properties)
   {
      Annotation[][] paramAnnotations = method.getParameterAnnotations();
      ParameterAnnotationType typeFound;
      boolean nullifyRank = false;
      for (int i = 0; i < paramAnnotations.length; i++)
      {
         typeFound = null;
         for (Annotation annotation: paramAnnotations[i])
         {
            // no valid annotation found for parameter i yet
            if (typeFound == null)
            {
               typeFound = findAnnotationType(annotation, i);
            }
            else if (findAnnotationType(annotation, i) != null)
            {
               throw new InvalidAdviceException("Parameter " + i  + " of " +
                     adviceType + " advice method '" + method + 
                     "' contains more than one valid annotation");
            }
         }
         if (typeFound == null)
         {
            if (paramAnnotations[i].length == 0)
            {
               throw new InvalidAdviceException("Parameter " + i  + " of " +
                     adviceType + " advice method '" + method +
                     "' is not annotated\nFor interception of joinpoint " +
                     properties.getJoinPoint() + " expecting one of annotations: " +
                     getDescription(paramTypes) + getDescription(contextParamTypes));
            }
            AdviceMethodFactory.appendNewMatchingMessage(method, "parameter ");
            AdviceMethodFactory.appendMatchingMessage(i);
            AdviceMethodFactory.appendMatchingMessage("' is not annotated correctly. Expecting one of: ");
            AdviceMethodFactory.appendMatchingMessage(getDescription(paramTypes));
            AdviceMethodFactory.appendMatchingMessage(getDescription(contextParamTypes));
            this.prevalidated = false;
            return;
         }
         // this happens when target or caller are nulls
         // in this case, this advice should have the smallest rank, since
         // any other advice is preferable (in case of overloaded advices)
         nullifyRank = nullifyRank || (typeFound.rule.lowerRankGrade(properties));
      }
      if (nullifyRank)
      {
         rank = 0;
      }
      this.prevalidated = true;
   }
   
   private String getDescription(ParameterAnnotationType[] types)
   {
      StringBuffer buffer = new StringBuffer();
      for (int i = 1; i < types.length; i++)
      {
         buffer.append("\n          ");
         buffer.append(types[i].rule);
      }
      return buffer.toString();
   }

   /**
    * Searches for an annotation <code>Annotation</code> on parameter annotation
    * rules.
    * 
    * @param annotation the parameter annotation to be searched on the parameter
    *                   annotation rules
    * @param i          the number of the advice parameter that is annotated with
    *                   <code>annotation</code>
    * 
    * @return           the parameter type if there is a rule correspondent to
    *                   <code>annotation</code>; <code>null</code> otherwise
    */
   private final ParameterAnnotationType findAnnotationType(Annotation annotation,
         int i)
   {
      
      for (int j = 0; j < paramTypes.length; j++)
      {
         // found
         if (paramTypes[j].applies(annotation, i))
         {
            return paramTypes[j];
         }
      }
      for (int j = 0; j < contextParamTypes.length; j++)
      {
         // found
         if (contextParamTypes[j].applies(annotation, i))
         {
            return contextParamTypes[j];
         }
      }
      return null;
   }
      
   /**
    * Contains validation data concerning a parameter annotation rule.
    */
   abstract class ParameterAnnotationType
   {
      ParameterAnnotationRule rule;
      
      public ParameterAnnotationType(ParameterAnnotationRule rule)
      {
         this.rule = rule;
      }
      
      /**
       * Verifies if <code>parameterAnnotation</code> is of this type, and, if it is,
       * sets the parameter index information.
       * 
       * @param parameterAnnotation the parameter annotation
       * @param parameterIndex      the parameter index
       * @return <code>true</code> if <code>parameterAnnotation</code> is of this type
       */
      public final boolean applies(Annotation parameterAnnotation, int parameterIndex)
      {
         if (parameterAnnotation.annotationType() == rule.getAnnotation())
         {
            setIndex(parameterIndex, parameterAnnotation);
            return true;
         }
         return false;
      }

      /**
       * Validates the occurences of this parameter type, according to the annotaion rule
       * and to <code>properties</code>.
       * 
       * @param properties contains information about the queried method
       * @return <code>true</code> if the occurrences of this parameter type are all valid
       */
      public final boolean validate(AdviceMethodProperties properties)
      {
         if (rule.isMandatory() && !isSet())
         {
            AdviceMethodFactory.appendNewMatchingMessage(method, "mandatory ");
            AdviceMethodFactory.appendMatchingMessage(rule);
            AdviceMethodFactory.appendMatchingMessage("-annotated parameter  not found");
            return false;
         }
         return internalValidate(properties);
      }
      
      /**
       * Records that the parameter identified by <code>parameterIndex</code> is of
       * this type.
       * @param parameterIndex the index of the parameter
       * @param annotation     the annotation used on parameter <code>parameterIndex
       *                       </code>
       */
      public abstract void setIndex(int parameterIndex,
            Annotation annotation);

      /**
       * Returns <code>true</code> if there is a parameter of this type.
       */
      public abstract boolean isSet();
      
      /**
       * Validates the occurences of this parameter type, according to the annotation rule
       * and to <code>properties</code>.
       * 
       * @param properties contains information about the queried method
       * @return <code>true</code> if the occurrences of this parameter type are all valid
       */
      public abstract boolean internalValidate(AdviceMethodProperties properties);
      
      /**
       * Resets all advice method properties information that has been set during
       * validation.
       */
      public abstract void resetValidation();
      
      /**
       * Returns the sum of the assignability degrees of every paramater of this type.
       * 
       * @param properties       contains information about the queried advice method
       * @return                 the assignability degree if this parameter type on the
       *                         advice method
       */
      public abstract short getAssignabilityDegree(AdviceMethodProperties properties);
      
      /**
       * Assigns information regarding all occurences of this parameter type on the
       * advice method to <code>args</code>.
       * 
       * @param args array containing information of parameter type occurrences
       */
      public abstract void assignParameterInfo(int[] args);
   }

   /**
    * A parameter type whose annotation can occur only once in an advice method.
    */
   class SingleParameterType extends ParameterAnnotationType
   {
      int index;
      Type assignableFrom;
      
      public SingleParameterType(ParameterAnnotationRule rule)
      {
         super(rule);
         this.index = -1;
      }
      
      public final void setIndex(int parameterIndex, Annotation annotation)
      {
         if (this.index != -1)
         {
            throw new InvalidAdviceException(
                  "Found more than one occurence of '"
                  + rule + "' on parameters of " + adviceType + " advice method '" +
                  method + "'");
         }
         this.index = parameterIndex;
         rank += rule.getRankGrade();
      }

      public final boolean isSet()
      {
         return this.index != -1;
      }
      
      public final boolean internalValidate(AdviceMethodProperties properties)
      {
         if (index == -1)
         {
            return true;
         }
         Type parameterType = method.getGenericParameterTypes()[index];
         Object assignableFrom = rule.getAssignableFrom(properties);
         Class<?> superType = rule.getSuperType();
         if (assignableFrom instanceof Type)
         {
            if(AssignabilityAlgorithm.VARIABLE_TARGET.isAssignable(parameterType,
               (Type) assignableFrom, hierarchy))
            {
               this.assignableFrom = (Type) assignableFrom;
               return true;
            }
            if (superType != null && superType.isAssignableFrom(
                  method.getParameterTypes()[index]))
            {
               this.assignableFrom = null;
               return true;
            }
            AdviceMethodFactory.appendNewMatchingMessage(method, rule);
            AdviceMethodFactory.appendMatchingMessage("-annotated parameter is not assignable from expected type ");
            AdviceMethodFactory.appendMatchingMessage(assignableFrom);
            if (superType != null)
            {
               AdviceMethodFactory.appendMatchingMessage(" nor it is a subtype of ");
               AdviceMethodFactory.appendMatchingMessage(superType);
            }
            return false;
         }
         for (Type type: (Type[]) assignableFrom)
         {
            if (AssignabilityAlgorithm.VARIABLE_TARGET.isAssignable(parameterType,
                  type, hierarchy))
            {
               this.assignableFrom = type;
               return true;
            }
         }
         if (superType != null &&
               superType.isAssignableFrom(method.getParameterTypes()[index]))
         {
            this.assignableFrom = null;
            return true;
         }
         AdviceMethodFactory.appendNewMatchingMessage(method, rule);
         AdviceMethodFactory.appendMatchingMessage("-annotated parameter is not assignable from any of expected types [");
         AdviceMethodFactory.appendMatchingMessage(rule.getAssignableFrom(properties));
         for (Type type: (Type[]) assignableFrom)
         {
            AdviceMethodFactory.appendMatchingMessage(", ");
            AdviceMethodFactory.appendMatchingMessage(type);
         }
         AdviceMethodFactory.appendMatchingMessage(']');
         if (superType != null)
         {
            AdviceMethodFactory.appendMatchingMessage(" nor it is a subtype of ");
            AdviceMethodFactory.appendMatchingMessage(superType);
         }
         return false;
      }

      public final void resetValidation()
      {
         this.assignableFrom = null;
      }

      public final short getAssignabilityDegree(AdviceMethodProperties properties)
      {
         if (this.index == -1)
         {
            return -1;
         }
         if (assignableFrom == null)
         {
            return 1000;
         }
         return DEGREE.getAssignabilityDegree(
               method.getGenericParameterTypes()[this.index], assignableFrom);
      }
      
      public final void assignParameterInfo(int[] args)
      {
         if (this.index != -1)
         {
            args[index] = rule.getProperty();
         }
      }
   }

   /**
    * A parameter type whose annotation can occur more than once in an advice method.
    */   
   class MultipleParameterType extends ParameterAnnotationType
   {
      private int[][] indexes;
      private int[] originalIndexValues; // for resetting purposes
      private int indexesLength;
      
      // maximum size is the total number of arguments
      public MultipleParameterType(ParameterAnnotationRule rule, int totalArgs)
      {
         super(rule);
         this.indexes = new int[totalArgs][2];
         this.indexesLength = 0;
         this.originalIndexValues = new int[totalArgs];
      }
      
      public final void setIndex(int index, Annotation annotation)
      {
         if (indexesLength == indexes.length)
         {
            throw new RuntimeException(
                  "Unexpected call to setIndex method during processing of '" +
                  method + "'");
         }
         indexes[indexesLength][0] = index;
         originalIndexValues[indexesLength] = ((Arg) annotation).index();
         indexes[indexesLength][1] = originalIndexValues[indexesLength];
         indexesLength ++;
         rank += rule.getRankGrade();
      }
      
      public final boolean isSet()
      {
         return indexesLength > 0;
      }
      
      public final boolean internalValidate(AdviceMethodProperties properties)
      {
         Type[] expectedTypes = (Type[]) rule.getAssignableFrom(properties);
         Type[] adviceTypes = method.getGenericParameterTypes();
         if (indexesLength > 0 && expectedTypes.length == 0)
         {
            AdviceMethodFactory.appendNewMatchingMessage(method, "joinpoint has no arguments; unexpected ");
            AdviceMethodFactory.appendMatchingMessage(this.rule);
            AdviceMethodFactory.appendMatchingMessage("-annotated parameter found");
            return false;
         }
         if (indexesLength > expectedTypes.length)
         {
            AdviceMethodFactory.appendNewMatchingMessage(method, "found more ");
            AdviceMethodFactory.appendMatchingMessage(this.rule);
            AdviceMethodFactory.appendMatchingMessage("-annotated parameters than the number of arguments available on the joinpoint");
            return false;
         }
         
         boolean[] taken = new boolean[expectedTypes.length];
         for (int i = 0; i < indexesLength; i++)
         {
            // parameter index is set on @Arg annotation
            if (indexes[i][1] != -1)
            {
               // negative index
               if (indexes[i][1] < 0)
               {
                  throw new InvalidAdviceException(
                        "Negative joinpoint parameter index found at method '" +
                        method + "'");
               }
               // wrong index
               if (indexes[i][1] >= expectedTypes.length)
               {
                  AdviceMethodFactory.appendNewMatchingMessage(method,
                        "there is no joinpoint argument with index ");
                  AdviceMethodFactory.appendMatchingMessage(indexes[i][1]);
                  AdviceMethodFactory.appendMatchingMessage(", since there are ");
                  AdviceMethodFactory.appendMatchingMessage(expectedTypes.length == 0? "no": expectedTypes.length);
                  AdviceMethodFactory.appendMatchingMessage(" joinpoint arguments available");
                  return false;
               }
               // wrong type
               if (!AssignabilityAlgorithm.VARIABLE_TARGET.isAssignable(
                     adviceTypes[indexes[i][0]], expectedTypes[indexes[i][1]],
                     hierarchy))
               {
                  AdviceMethodFactory.appendNewMatchingMessage(method, "advice parameter ");
                  AdviceMethodFactory.appendMatchingMessage(indexes[i][0]);
                  AdviceMethodFactory.appendMatchingMessage(", of type '");
                  AdviceMethodFactory.appendMatchingMessage(adviceTypes[indexes[i][0]]);
                  AdviceMethodFactory.appendMatchingMessage("', cannot be assigned to the value of joinpoint argument with index ");
                  AdviceMethodFactory.appendMatchingMessage(indexes[i][1] + ", whose type is '");
                  AdviceMethodFactory.appendMatchingMessage(expectedTypes[indexes[i][1]]);
                  AdviceMethodFactory.appendMatchingMessage("'");
                  return false;
               }
               // index set more than once
               if (taken[indexes[i][1]])
               {
                  throw new InvalidAdviceException(
                        "Joinpoint parameter index '" + indexes[i][0] +
                        "' cannot be assigned to more than one " +  rule +
                        "-annotated advice parameter (on " + adviceType +
                        " advice method '" + method + "')");
               }
               // mark index as set
               taken[indexes[i][1]] = true;
               continue;
            }
         }
         for (int i = 0; i < indexesLength; i++)
         {
            // parameter index is already set
            if (indexes[i][1] != -1)
            {
               continue;
            }
            boolean found = false;
            for (int j = 0; j < expectedTypes.length; j++)
            {
               if (adviceTypes[indexes[i][0]] == expectedTypes[j] && !taken[j])
               {
                  indexes[i][1] = j;
                  taken[j] = true;
                  found = true;
                  break;
               }
            }
            if (!found)
            {
               for (int j = 0; j < expectedTypes.length; j++)
               {
                  if (AssignabilityAlgorithm.VARIABLE_TARGET.isAssignable(
                        adviceTypes[indexes[i][0]], expectedTypes[j], hierarchy) &&
                        !taken[j])
                  {
                     indexes[i][1] = j;
                     taken[j] = true;
                     found = true;
                     break;
                  }
               }
               if (!found)
               {
                  AdviceMethodFactory.appendNewMatchingMessage(method,
                        "not found a match for argument ");
                  AdviceMethodFactory.appendMatchingMessage(adviceTypes[indexes[i][0]]);
                  AdviceMethodFactory.appendMatchingMessage("; expected one of types: ");
                  for (int j = 0; j < expectedTypes.length; j++)
                  {
                     if (!taken[j])
                     {
                        AdviceMethodFactory.appendMatchingMessage(expectedTypes[j]);
                        AdviceMethodFactory.appendMatchingMessage(" ");
                     }
                  }
                  return false;
               }
            }
         }
         return true;
      }
            
      public void resetValidation()
      {
         for (int i = 0; i < indexesLength; i++)
         {
            indexes[i][1] = originalIndexValues[i];
         }
      }
      
      public short getAssignabilityDegree(AdviceMethodProperties properties)
      {
         if (indexesLength == 0)
         {
            return -1;
         }
         Type[] expectedTypes = (Type[]) rule.getAssignableFrom(properties);
         short level = 0;
         for (int i = 0; i < indexesLength; i++)
         {
            level += DEGREE.getAssignabilityDegree(
                  method.getGenericParameterTypes()[this.indexes[i][0]],
                  expectedTypes[this.indexes[i][1]]);
         }
         return level; 
      }
      
      public void assignParameterInfo(int args[])
      {
         for (int i = 0; i < indexesLength; i++)
         {
            args[this.indexes[i][0]] = this.indexes[i][1];
         }
      }
   }
}