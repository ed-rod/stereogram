/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram.map;

/**
 * Scales a height map.
 *
 * @author erodri02
 */
public class ScaleHeightMapTransformer implements HeightMapTransformer
{
   private final float scale;

   /**
    * Initializes a new ScaleHeightMapTransformer object.
    *
    * @param scale the scale factor to apply to the undelrying values of the input height map.
    */
   public ScaleHeightMapTransformer( final float scale )
   {
      this.scale = scale;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public HeightMap transform( final HeightMap input )
   {
      return rescale( input, this.scale );
   }

   /**
    * Creates a new height map by multiyplying all underlying values by the scale factor.
    *
    * @param input the input map to rescale.
    * @param scaleFactor the scale factor.
    * @return a new height map that has had its values rescaled.
    */
   protected static HeightMap rescale( final HeightMap input, final float scaleFactor )
   {
      final short[][] newData = new short[ input.getHeight() ][ input.getWidth() ];

      for( int y = 0; y < input.getHeight(); y++ )
      {
         for( int x = 0; x < input.getWidth(); x++ )
         {
            final float val = input.map[ y ][ x ] * scaleFactor;
            newData[ y ][ x ] = (short) val;
         }
      }
      return new HeightMap( newData );
   }
}
