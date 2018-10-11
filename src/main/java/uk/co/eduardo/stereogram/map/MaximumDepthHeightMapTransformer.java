/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram.map;

/**
 * Rescales a height map so that all values are at most the max value specified in the constructor.
 *
 * @author erodri02
 */
public class MaximumDepthHeightMapTransformer implements HeightMapTransformer
{
   /** The default maximum value to which the height map will be rescaled. */
   public static final int DEFAULT_MAXIMUM_DEPTH = 20;

   private final int maxValue;

   /**
    * Initializes a new MaximumDepthHeightMapTransformer object.
    *
    * @param maxValue The maximum allowable value in the height map.
    */
   public MaximumDepthHeightMapTransformer( final int maxValue )
   {
      this.maxValue = maxValue;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public HeightMap transform( final HeightMap input )
   {
      int max = input.map[ 0 ][ 0 ];
      for( int y = 0; y < input.getHeight(); y++ )
      {
         for( int x = 0; x < input.getWidth(); x++ )
         {
            max = Math.max( max, input.map[ y ][ x ] );
         }
      }
      final float scale = this.maxValue / (float) max;
      return ScaleHeightMapTransformer.rescale( input, scale );
   }
}
