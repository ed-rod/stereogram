/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram.texture;

/**
 * Represents a 2D texture.
 * <p>
 * The texture has the same height (rows) as the stereogram that will be generated. Each row is represented as a {@link Texture1D}.
 * </p>
 *
 * @author erodri02
 */
public class Texture2D
{
   private final Texture1D[] textures;

   /**
    * Constrcuts a 2D texture from the two dimensional array. *
    * <p>
    * Each pixel in the texture is represented as an integer. R, G, B and alpha components are packed into a single integer as
    * AABBGGRR. I.e. the least significant byte contains the red component, the next byte is the green component, then blue and,
    * finally, the alpha element is in the most significant byte.
    * </p>
    *
    * @param texture the pixel array from which to create the texture.
    * @param invert whether depth should be inverted,
    */
   public Texture2D( final int[][] texture, final boolean invert )
   {
      this.textures = new Texture1D[ texture.length ];
      for( int y = 0; y < texture.length; y++ )
      {
         this.textures[ y ] = new Texture1D( texture[ y ], invert );
      }
   }

   /**
    * Gets the 1D texture (row of pxiels) at the given Y offset.
    *
    * @param y the y offset.
    * @return the 1D texture at that position.
    */
   public Texture1D getTexture1D( final int y )
   {
      return this.textures[ y ];
   }

   /**
    * In a standard stereogram, the textures are initially generated so the left-hand-side looks normal. They are then warped for
    * the height map so that the texture becomes more and more warped towards the right hand side of the image. To combat this, it
    * is possible to set initial offsets (an integer value for each row). This warps the texture on the left hand side such that it
    * should look normal again around the middle of the image.
    *
    * @param offsets one value for each row in the texture.
    */
   public void setInitialOffsets( final int[] offsets )
   {
      // Check they're the correct length
      if( offsets.length != this.textures.length )
      {
         throw new IllegalArgumentException();
      }

      for( int y = 0; y < offsets.length; y++ )
      {
         final int offset = offsets[ y ];
         if( offset != 0 )
         {
            final int textureSize = this.textures[ y ].getSize();
            this.textures[ y ].cycle( textureSize - offset );
         }
      }
   }
}
