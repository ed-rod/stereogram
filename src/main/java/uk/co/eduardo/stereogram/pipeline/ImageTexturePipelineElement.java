/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram.pipeline;

import java.awt.image.BufferedImage;

import uk.co.eduardo.stereogram.ImageUtilities;
import uk.co.eduardo.stereogram.StreamSource;

/**
 * Raw texture pipeline element.
 *
 * @author erodri02
 */
public class ImageTexturePipelineElement extends AbstractBinaryPipelineElement< StreamSource, Integer, BufferedImage >
{
   /**
    * {@inheritDoc}
    */
   @Override
   protected BufferedImage internalComputeOutput( final PipelineElement< StreamSource > firstInput,
                                                  final PipelineElement< Integer > secondInput )
   {
      final BufferedImage image = ImageUtilities.load( firstInput.getOutput(), BufferedImage.TYPE_INT_ARGB, 0, 0, 0, 0 );
      final BufferedImage resized = ImageUtilities.resize( image, secondInput.getOutput() );

      return resized;
   }
}
