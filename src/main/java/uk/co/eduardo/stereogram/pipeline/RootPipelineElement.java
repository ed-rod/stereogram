/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram.pipeline;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A root element that has no input.
 *
 * @author erodri02
 * @param <T> the target type.
 */
public class RootPipelineElement< T > extends AbstractPipelineElement< T > implements PipelineElement< T >
{
   private static final Logger LOGGER = Logger.getLogger( RootPipelineElement.class.getName() );

   private T value;

   /**
    * Initializes a new RootPipelineElement object.
    *
    * @param value the constant value to output from this element
    */
   public RootPipelineElement( final T value )
   {
      this.value = value;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isValid()
   {
      return !isDirty();
   }

   /**
    * Sets the value.
    *
    * @param value the new value.
    */
   public void setValue( final T value )
   {
      this.value = value;
      setDirty( true );
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public T getOutput()
   {
      LOGGER.log( Level.FINE, "Recomputing " + getName() + " " + toString() ); //$NON-NLS-1$ //$NON-NLS-2$
      setDirty( false );
      return this.value;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      return this.value.toString();
   }
}
