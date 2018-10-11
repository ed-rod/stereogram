/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram.pipeline;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Base implementation of a pipeline element.
 *
 * @author erodri02
 * @param <S> the input, source, type.
 * @param <T> the output, target, type.
 */
public abstract class AbstractUnaryPipelineElement< S, T > extends AbstractPipelineElement< T >
      implements UnaryPipelineElement< S, T >
{
   private static final Logger LOGGER = Logger.getLogger( AbstractUnaryPipelineElement.class.getName() );

   private PipelineElement< S > inputPipelineElement;

   /** Direct access to the cached output. This value is only valid if {@link #isValid()} is <code>true</code> */
   protected T cachedOutput = null;

   /**
    * {@inheritDoc}
    */
   @Override
   public void connectFirstInput( final PipelineElement< S > input )
   {
      if( this.inputPipelineElement != null )
      {
         this.inputPipelineElement.removePipelineElementListener( this.defaultListener );
      }

      this.inputPipelineElement = input;
      this.inputPipelineElement.addPipelineElementListener( this.defaultListener );
      setDirty( true );
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isValid()
   {
      if( this.inputPipelineElement == null )
      {
         return false;
      }
      return this.inputPipelineElement.isValid() && !isDirty();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public T getOutput()
   {
      if( !isValid() )
      {
         LOGGER.log( Level.FINE, "Recomputing " + getName() ); //$NON-NLS-1$
         this.cachedOutput = internalComputeOutput( this.inputPipelineElement );
         setDirty( false );
      }
      return this.cachedOutput;
   }

   /**
    * Computes the new output from the given input.
    *
    * @param input the input pipeline element.
    * @return an output computed from the input.
    */
   protected abstract T internalComputeOutput( final PipelineElement< S > input );
}
