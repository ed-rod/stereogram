/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram.pipeline;

import uk.co.eduardo.stereogram.map.HeightMap;
import uk.co.eduardo.stereogram.texture.Texture2D;
import uk.co.eduardo.stereogram.texture.TextureOffsetFactory;

/**
 * Generates a 2D texture from a height map and texture name.
 *
 * @author erodri02
 */
public class Texture2DPipelineElement
      extends AbstractQuaternaryPipelineElement< int[][], HeightMap, TextureOffsetFactory, Boolean, Texture2D >
{
   /**
    * {@inheritDoc}
    */
   @Override
   protected Texture2D internalComputeOutput( final PipelineElement< int[][] > firstInput,
                                              final PipelineElement< HeightMap > secondInput,
                                              final PipelineElement< TextureOffsetFactory > thirdInput,
                                              final PipelineElement< Boolean > fourthInput )
   {
      final int[][] textureData = firstInput.getOutput();
      final HeightMap heightMap = secondInput.getOutput();
      final TextureOffsetFactory offsetFactory = thirdInput.getOutput();
      final boolean invert = fourthInput.getOutput();

      final Texture2D texture = create( textureData, heightMap.getHeight(), invert );
      texture.setInitialOffsets( offsetFactory.getOffsets( heightMap, texture ) );
      return texture;
   }

   private Texture2D create( final int[][] textureData, final int stereogramHeight, final boolean invert )
   {
      final int textureHeight = textureData.length;

      final int[][] fullTexture = new int[ stereogramHeight ][];
      for( int y = 0; y < stereogramHeight; y++ )
      {
         fullTexture[ y ] = textureData[ y % textureHeight ];
      }

      return new Texture2D( fullTexture, invert );
   }
}
