/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram.pipeline;

import java.awt.image.BufferedImage;

import uk.co.eduardo.stereogram.map.HeightMap;

/**
 * Creates a buffered image for a height map.
 *
 * @author erodri02
 */
public class BufferedImagePipelineElement extends AbstractUnaryPipelineElement< HeightMap, BufferedImage >
{
   /**
    * {@inheritDoc}
    */
   @Override
   protected BufferedImage internalComputeOutput( final PipelineElement< HeightMap > input )
   {
      final HeightMap map = input.getOutput();
      return new BufferedImage( map.getWidth(), map.getHeight(), BufferedImage.TYPE_INT_ARGB );
   }
}
