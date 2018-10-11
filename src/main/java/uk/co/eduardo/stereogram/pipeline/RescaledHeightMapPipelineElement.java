/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram.pipeline;

import uk.co.eduardo.stereogram.map.HeightMap;
import uk.co.eduardo.stereogram.map.MaximumDepthHeightMapTransformer;

/**
 * Rescales the height map so the maximum value is as specified.
 *
 * @author erodri02
 */
public class RescaledHeightMapPipelineElement extends AbstractBinaryPipelineElement< HeightMap, Integer, HeightMap >
{
   /**
    * {@inheritDoc}
    */
   @Override
   protected HeightMap internalComputeOutput( final PipelineElement< HeightMap > firstInput,
                                              final PipelineElement< Integer > secondInput )
   {
      final HeightMap souce = firstInput.getOutput();
      return new MaximumDepthHeightMapTransformer( secondInput.getOutput() ).transform( souce );
   }
}
