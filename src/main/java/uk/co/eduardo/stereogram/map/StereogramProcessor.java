/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram.map;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import uk.co.eduardo.stereogram.texture.Texture1D;
import uk.co.eduardo.stereogram.texture.Texture2D;

/**
 * Fills a bvuffered image with a generated stereogram.
 *
 * @author erodri02
 */
public class StereogramProcessor
{
   /**
    * Processes the height mpa and fills in the supplied image with the generated stereogram.
    * <p>
    * The image must have the same dimensions as the heigth map.
    * </p>
    *
    * @param heightMap the height map to process.
    * @param texture the texture for the image.
    * @param image the image to fill.
    */
   public void process( final HeightMap heightMap, final Texture2D texture, final BufferedImage image )
   {
      final DataBufferInt buffer = (DataBufferInt) image.getRaster().getDataBuffer();
      final int[] data = buffer.getData();

      int offset = 0;
      for( int y = 0; y < image.getHeight(); y++ )
      {
         final Texture1D rowTexture = texture.getTexture1D( y );
         int last = heightMap.map[ y ][ 0 ];

         for( int x = 0; x < image.getWidth(); x++ )
         {
            final int current = heightMap.map[ y ][ x ];
            final int delta = current - last;
            if( delta != 0 )
            {
               rowTexture.setLevelDelta( delta );
               last = current;
            }
            data[ offset++ ] = rowTexture.getAndIncrement();
         }
      }
   }
}
