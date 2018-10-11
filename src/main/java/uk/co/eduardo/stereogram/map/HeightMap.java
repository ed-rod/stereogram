/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram.map;

/**
 * Defines the height map for the image.
 *
 * @author erodri02
 */
public class HeightMap
{
   private final int width;

   private final int height;

   /**
    * The height hap data.
    */
   protected final short[][] map;

   /**
    * Initializes a new HeightMap object.
    *
    * @param map the height map.
    */
   public HeightMap( final short[][] map )
   {
      this.width = map[ 0 ].length;
      this.height = map.length;
      this.map = map;
   }

   /**
    * Gets the width of the map.
    *
    * @return the width.
    */
   public int getWidth()
   {
      return this.width;
   }

   /**
    * Gets the height of the map.
    *
    * @return the height.
    */
   public int getHeight()
   {
      return this.height;
   }

   /**
    * Gets the value of the height map at the specified position.
    *
    * @param x the x position.
    * @param y the y position.
    * @return the height of the map at the given location.
    */
   public short getValue( final int x, final int y )
   {
      return this.map[ y ][ x ];
   }
}
