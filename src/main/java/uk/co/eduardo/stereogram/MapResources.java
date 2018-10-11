/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram;

import java.io.InputStream;

import uk.co.eduardo.stereogram.map.HeightMap;

/**
 * Enumeration of available map resources.
 *
 * @author erodri02
 */
@SuppressWarnings( "nls" )
public enum MapResources implements StreamSource
{
   /** Bunny */
   BUNNY( "bunny.png" ),

   /** Chairs */
   CHAIRS( "chairs.png" ),

   /** Cube */
   CUBE( "cube.png" ),

   /** Dragon */
   DRAGON( "dragon.png" ),

   /** Flower */
   FLOWER( "flower.png" ),

   /** Shark */
   SHARK( "shark.png" ),

   /** Test pattern */
   TEST_OFFSET( "test-offset.png" ),

   /** Turtle */
   TURTLE( "turtle.png" );

   private String resourceName;

   MapResources( final String resourceName )
   {
      this.resourceName = resourceName;
   }

   /**
    * Gets the resource name for the height map.
    *
    * @return the resource name.
    */
   public String getResourceName()
   {
      return this.resourceName;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public InputStream getStream()
   {
      return HeightMap.class.getResourceAsStream( this.resourceName );
   }
}
