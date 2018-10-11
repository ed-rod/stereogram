/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram.pipeline;

/**
 * A pipeline element transforms an input into an output.
 *
 * @author erodri02
 * @param <S1> the first input, source, type.
 * @param <S2> the second input, source, type.
 * @param <T> the output, target, type.
 */
public interface BinaryPipelineElement< S1, S2, T > extends UnaryPipelineElement< S1, T >
{
   /**
    * Connects a pipeline element as the second input of this pipline element.
    *
    * @param input the input source.
    */
   void connectSecondInput( PipelineElement< S2 > input );
}
