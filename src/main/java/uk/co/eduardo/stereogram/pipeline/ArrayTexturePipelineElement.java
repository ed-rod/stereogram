/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram.pipeline;

import java.awt.image.BufferedImage;

import uk.co.eduardo.stereogram.ImageUtilities;

/**
 * Raw texture pipeline element.
 *
 * @author erodri02
 */
public class ArrayTexturePipelineElement extends AbstractUnaryPipelineElement< BufferedImage, int[][] >
{
   /**
    * {@inheritDoc}
    */
   @Override
   protected int[][] internalComputeOutput( final PipelineElement< BufferedImage > input )
   {
      return ImageUtilities.getRasterAsInts( input.getOutput() );
   }
}
