/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram.pipeline;

/**
 * Base implementation of a pipeline element.
 *
 * @author erodri02
 * @param <S1> the first input, source, type.
 * @param <S2> the second input, source, type.
 * @param <T> the output, target, type.
 */
public abstract class AbstractBinaryPipelineElement< S1, S2, T > extends AbstractUnaryPipelineElement< S1, T >
      implements BinaryPipelineElement< S1, S2, T >
{
   private PipelineElement< S2 > secondInputPipelineElement;

   /**
    * {@inheritDoc}
    */
   @Override
   public void connectSecondInput( final PipelineElement< S2 > input )
   {

      if( this.secondInputPipelineElement != null )
      {
         this.secondInputPipelineElement.removePipelineElementListener( this.defaultListener );
      }

      this.secondInputPipelineElement = input;
      this.secondInputPipelineElement.addPipelineElementListener( this.defaultListener );
      setDirty( true );
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isValid()
   {
      return super.isValid() && ( this.secondInputPipelineElement != null ) && this.secondInputPipelineElement.isValid();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   protected final T internalComputeOutput( final PipelineElement< S1 > input )
   {
      return internalComputeOutput( input, this.secondInputPipelineElement );
   }

   /**
    * Computes the new output from the given input.
    *
    * @param firstInput the first input pipeline element.
    * @param secondInput the second input pipeline element.
    * @return an output computed from the input.
    */
   protected abstract T internalComputeOutput( final PipelineElement< S1 > firstInput, final PipelineElement< S2 > secondInput );
}
