/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram.pipeline;

/**
 * A pipeline element transforms an input into an output.
 *
 * @author erodri02
 * @param <S> the input, source, type.
 * @param <T> the output, target, type.
 */
public interface UnaryPipelineElement< S, T > extends PipelineElement< T >
{
   /**
    * Connects a pipeline element as the input of this pipline
    *
    * @param input the input source.
    */
   void connectFirstInput( PipelineElement< S > input );

   /**
    * Called to determine whether this pipeline element needs to be re-evaluated.
    * <p>
    * A pipeline element is valid if it hasn't been modified and its input is valid.
    * </p>
    * <p>
    * If the input is not valid or this element has changed, the output needs to be recomputed. Otherwise, if we're still valid, we
    * can just keep our cached output.
    * </p>
    *
    * @return whether or not this pipeline element is still valid (<code>true</code>) or is invalid and needs to be recomputed
    *         (<code>false</code>).
    */
   @Override
   boolean isValid();
}
