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
 * @param <T> the output, target, type.
 */
public abstract class AbstractTernaryPipelineElement< S1, S2, S3, T > extends AbstractBinaryPipelineElement< S1, S2, T >
      implements TernaryPipelineElement< S1, S2, S3, T >
{
   private PipelineElement< S3 > thirdInputPipelineElement;

   /**
    * {@inheritDoc}
    */
   @Override
   public void connectThirdInput( final PipelineElement< S3 > input )
   {
      if( this.thirdInputPipelineElement != null )
      {
         this.thirdInputPipelineElement.removePipelineElementListener( this.defaultListener );
      }

      this.thirdInputPipelineElement = input;
      this.thirdInputPipelineElement.addPipelineElementListener( this.defaultListener );
      setDirty( true );
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isValid()
   {
      return super.isValid() && ( this.thirdInputPipelineElement != null ) && this.thirdInputPipelineElement.isValid();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   protected final T internalComputeOutput( final PipelineElement< S1 > firstInput, final PipelineElement< S2 > secondInput )
   {
      return internalComputeOutput( firstInput, secondInput, this.thirdInputPipelineElement );
   }

   /**
    * Computes the new output from the given input.
    *
    * @param firstInput the first input pipeline element.
    * @param secondInput the second input pipeline element.
    * @param thirdInput the third input pipeline element.
    * @return an output computed from the input.
    */
   protected abstract T internalComputeOutput( final PipelineElement< S1 > firstInput,
                                               final PipelineElement< S2 > secondInput,
                                               final PipelineElement< S3 > thirdInput );
}
