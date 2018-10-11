/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram.pipeline;

import java.awt.image.BufferedImage;

import uk.co.eduardo.stereogram.map.HeightMap;
import uk.co.eduardo.stereogram.map.StereogramProcessor;
import uk.co.eduardo.stereogram.texture.Texture2D;

/**
 * Generates the final output.
 *
 * @author erodri02
 */
public class OutputPipelineElement extends AbstractTernaryPipelineElement< HeightMap, Texture2D, BufferedImage, BufferedImage >
{
   /**
    * {@inheritDoc}
    */
   @Override
   protected BufferedImage internalComputeOutput( final PipelineElement< HeightMap > firstInput,
                                                  final PipelineElement< Texture2D > secondInput,
                                                  final PipelineElement< BufferedImage > thirdInput )
   {
      final HeightMap map = firstInput.getOutput();
      final Texture2D texture = secondInput.getOutput();
      final BufferedImage image = thirdInput.getOutput();

      new StereogramProcessor().process( map, texture, image );
      return image;
   }
}
