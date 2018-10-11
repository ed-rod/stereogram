/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram.pipeline;

/**
 * A pipeline element that produces an output.
 *
 * @author erodri02
 * @param <T> the type of output the element generates.
 */
public interface PipelineElement< T >
{
   /**
    * Called to determine whether this pipeline element needs to be re-evaluated.
    *
    * @return whether or not this pipeline element is still valid (<code>true</code>) or is invalid and needs to be recomputed
    *         (<code>false</code>).
    */
   boolean isValid();

   /**
    * Gets the output value. Recomputes if necessary.
    *
    * @return the cached output value.
    */
   T getOutput();

   /**
    * Adds a listener that is notified when this element becomes invalid.
    *
    * @param listener the listener to add.
    */
   void addPipelineElementListener( PipelineElementListener listener );

   /**
    * Removes a listener.
    *
    * @param listener the listener to remove.
    */
   void removePipelineElementListener( PipelineElementListener listener );

   /**
    * Gets the name of the pipeline element.
    *
    * @return the name of the pipeline element.
    */
   String getName();

   /**
    * Receives notifications when an input becomes invalid.
    *
    * @author erodri02
    */
   public static interface PipelineElementListener
   {
      /**
       * Called when one of the inputs is marked as invalid.
       */
      void inputInvalid();
   }
}
