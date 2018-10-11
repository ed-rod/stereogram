/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram.texture;

import uk.co.eduardo.stereogram.map.HeightMap;

/**
 * Gets the height map at the midpoint.
 *
 * @author erodri02
 */
public class CentreAlignTextureOffsetFactory implements TextureOffsetFactory
{
   /**
    * {@inheritDoc}
    */
   @Override
   public int[] getOffsets( final HeightMap heightMap, final Texture2D texture )
   {
      final int[] offsets = new int[ heightMap.getHeight() ];
      final int midPoint = heightMap.getWidth() / 2;
      for( int y = 0; y < heightMap.getHeight(); y++ )
      {
         int textureWidth = texture.getTexture1D( y ).getSize();
         int texturePos = 0;
         int last = heightMap.getValue( 0, y );
         for( int x = 0; x < midPoint; x++ )
         {
            final int current = heightMap.getValue( x, y );
            final int delta = current - last;
            textureWidth -= delta;
            texturePos = ( texturePos + 1 ) % textureWidth;

            last = current;
         }
         offsets[ y ] = texturePos;
      }
      return offsets;
   }
}
