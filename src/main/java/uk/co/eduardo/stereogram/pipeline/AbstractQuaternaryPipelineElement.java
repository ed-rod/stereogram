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
 * @param <S3> the third input, source, type.
 * @param <S4> the fourth input, source, type.
 * @param <T> the output, target, type.
 */
public abstract class AbstractQuaternaryPipelineElement< S1, S2, S3, S4, T > extends AbstractTernaryPipelineElement< S1, S2, S3, T >
      implements QuaternaryPipelineElement< S1, S2, S3, S4, T >
{
   private PipelineElement< S4 > fourthInputPipelineElement;

   /**
    * {@inheritDoc}
    */
   @Override
   public void connectFourthInput( final PipelineElement< S4 > input )
   {
      if( this.fourthInputPipelineElement != null )
      {
         this.fourthInputPipelineElement.removePipelineElementListener( this.defaultListener );
      }

      this.fourthInputPipelineElement = input;
      this.fourthInputPipelineElement.addPipelineElementListener( this.defaultListener );
      setDirty( true );
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isValid()
   {
      return super.isValid() && ( this.fourthInputPipelineElement != null ) && this.fourthInputPipelineElement.isValid();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   protected final T internalComputeOutput( final PipelineElement< S1 > firstInput,
                                            final PipelineElement< S2 > secondInput,
                                            final PipelineElement< S3 > thirdInput )
   {
      return internalComputeOutput( firstInput, secondInput, thirdInput, this.fourthInputPipelineElement );
   }

   /**
    * Computes the new output from the given input.
    *
    * @param firstInput the first input pipeline element.
    * @param secondInput the second input pipeline element.
    * @param thirdInput the third input pipeline element.
    * @param fourthInput the fourth input pipeline element.
    * @return an output computed from the input.
    */
   protected abstract T internalComputeOutput( final PipelineElement< S1 > firstInput,
                                               final PipelineElement< S2 > secondInput,
                                               final PipelineElement< S3 > thirdInput,
                                               final PipelineElement< S4 > fourthInput );
}
