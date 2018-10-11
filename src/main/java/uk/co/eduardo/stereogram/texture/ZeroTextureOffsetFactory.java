/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram.texture;

import java.util.Arrays;

import uk.co.eduardo.stereogram.map.HeightMap;

/**
 * Gets the height map at the midpoint.
 *
 * @author erodri02
 */
public class ZeroTextureOffsetFactory implements TextureOffsetFactory
{
   /**
    * {@inheritDoc}
    */
   @Override
   public int[] getOffsets( final HeightMap map, Texture2D texture )
   {
      final int[] offsets = new int[ map.getHeight() ];
      Arrays.fill( offsets, 0 );
      return offsets;
   }
}
