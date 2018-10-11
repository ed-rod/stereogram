/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram.texture;

/**
 * Represents a 1D texture (row of pixels).
 * <p>
 * Each pixel in the texture is represented as an integer. R, G, B and alpha components are packed into a single integer as
 * AABBGGRR. I.e. the least significant byte contains the red component, the next byte is the green component, then blue and,
 * finally, the alpha element is in the most significant byte.
 * </p>
 *
 * @author erodri02
 */
public class Texture1D
{
   private TextureItem position;

   private final int size;

   private final int invertMultiplier;

   /**
    * Initializes a new Texture object.
    *
    * @param texture the texture. Must be rectangular and not a ragged array.
    * @param invert whether depth should be inverted
    */
   public Texture1D( final int[] texture, final boolean invert )
   {
      this.invertMultiplier = invert ? -1 : 1;
      this.position = createList( texture );
      this.size = texture.length;
   }

   /**
    * Gets the number of pixels in this 1D texture.
    *
    * @return the number of pixels in the texture.
    */
   public int getSize()
   {
      return this.size;
   }

   /**
    * Rotates the texture by cycling by the given amount.
    * <p>
    * E.g. given the texture
    *
    * <pre>
    * ABCDE
    * </pre>
    *
    * Cycling by 1 produces:
    *
    * <pre>
    * BCDEA
    * </pre>
    * </p>
    *
    * @param cycle the number of pixels by which to rotate the texture.
    */
   public void cycle( final int cycle )
   {
      for( int i = 0; i < cycle; i++ )
      {
         this.position = this.position.next;
      }
   }

   /**
    * Gets the pixel value of the texture at the current position.
    * <p>
    * Each pixel in the texture is represented as an integer. R, G, B and alpha components are packed into a single integer as
    * AABBGGRR. I.e. the least significant byte contains the red component, the next byte is the green component, then blue and,
    * finally, the alpha element is in the most significant byte.
    * </p>
    *
    * @return the pixel value at the current position.
    */
   public int get()
   {
      return this.position.val;
   }

   /**
    * Gets the pixel value of the texture at the current position and advances the position to the next pixel (wrapping if
    * nexcessary).
    * <p>
    * Each pixel in the texture is represented as an integer. R, G, B and alpha components are packed into a single integer as
    * AABBGGRR. I.e. the least significant byte contains the red component, the next byte is the green component, then blue and,
    * finally, the alpha element is in the most significant byte.
    * </p>
    *
    * @return the pixel value at the current position.
    */
   public int getAndIncrement()
   {
      final int val = this.position.val;
      this.position = this.position.next;
      return val;
   }

   /**
    * Changes the apparent (pseudo 3D depth) of the texture by the given amount. A positive value will increase the apparent depth,
    * a negative value will decrease the apparent depth and a value of zero will leave the apprent depth unchanged.
    *
    * @param levelDelta the amount by which to change the pseudo 3D depth of the texture.
    */
   public void setLevelDelta( final int levelDelta )
   {
      final int value = levelDelta * this.invertMultiplier;
      if( value > 0 )
      {
         increaseDepth( value );
      }
      else if( value < 0 )
      {
         decreaseDepth( -value );
      }
   }

   /**
    * Increases the pseudo 3D depth by the specified amount.
    * <p>
    * Modifies the underlying texture so that the pseudo 3D level is increased by the amount.
    * </p>
    *
    * @param increaseAmount the amount (positive) by which to increase the pseudo 3D depth.
    */
   private void increaseDepth( final int increaseAmount )
   {
      TextureItem newNext = this.position.next;
      for( int i = 0; i < increaseAmount; i++ )
      {
         newNext = newNext.next;
      }
      this.position.next = newNext;
   }

   /**
    * Decreases the pseudo 3D depth by the specified amount.
    * <p>
    * Modifies the underlying texture so that the pseudo 3D level is decreased by the amount.
    * </p>
    *
    * @param decreaseAmount the amount (positive) by which to decrease the pseudo 3D depth.
    */
   private void decreaseDepth( final int decreaseAmount )
   {
      final int val = this.position.val;
      final TextureItem oldNext = this.position.next;

      TextureItem current = this.position;
      for( int i = 0; i < decreaseAmount; i++ )
      {
         final TextureItem item = new TextureItem( val );
         current.next = item;
         current = item;
      }
      current.next = oldNext;
   }

   private static TextureItem createList( final int[] input )
   {
      final TextureItem first = new TextureItem( input[ 0 ] );
      TextureItem last = first;
      for( int i = 1; i < input.length; i++ )
      {
         final TextureItem ti = new TextureItem( input[ i ] );
         last.next = ti;
         last = ti;
      }
      last.next = first;
      return first;
   }

   private static final class TextureItem
   {
      private final int val;

      private TextureItem next;

      private TextureItem( final int val )
      {
         this.val = val;
      }
   }
}
