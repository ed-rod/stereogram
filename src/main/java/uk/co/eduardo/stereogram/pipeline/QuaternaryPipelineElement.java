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
 * @param <S3> the third input, source, type.
 * @param <S4> the fourth input, source, type.
 * @param <T> the output, target, type.
 */
public interface QuaternaryPipelineElement< S1, S2, S3, S4, T > extends TernaryPipelineElement< S1, S2, S3, T >
{
   /**
    * Connects a pipeline element as the third input of this pipline element.
    *
    * @param input the input source.
    */
   void connectFourthInput( PipelineElement< S4 > input );
}
