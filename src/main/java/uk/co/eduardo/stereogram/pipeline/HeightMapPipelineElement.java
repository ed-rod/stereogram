/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram.pipeline;

import java.awt.image.BufferedImage;

import uk.co.eduardo.stereogram.ImageUtilities;
import uk.co.eduardo.stereogram.StreamSource;
import uk.co.eduardo.stereogram.map.HeightMap;

/**
 * Pipeline element that recomputes a depth map from a resource.
 *
 * @author erodri02
 */
public class HeightMapPipelineElement extends AbstractUnaryPipelineElement< StreamSource, HeightMap >
{
   /**
    * {@inheritDoc}
    */
   @Override
   protected HeightMap internalComputeOutput( final PipelineElement< StreamSource > input )
   {
      final BufferedImage image = ImageUtilities.load( input.getOutput(), BufferedImage.TYPE_BYTE_GRAY, 159, 159, 40, 40 );
      final short[][] map = ImageUtilities.getRasterAsShorts( image );
      return new HeightMap( map );
   }
}
