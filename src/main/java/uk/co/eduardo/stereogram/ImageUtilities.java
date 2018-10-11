/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

/**
 * Utility methods for dealing with buffered images and arrays.
 *
 * @author erodri02
 */
public class ImageUtilities
{
   private static final Logger LOGGER = Logger.getLogger( ImageUtilities.class.getName() );

   private static final Map< StreamSource, WeakReference< BufferedImage > > CACHE = new HashMap<>();

   private ImageUtilities()
   {
      // Prevent instantiation
      throw new IllegalStateException();
   }

   /**
    * Loads an image from a source.
    *
    * @param source the source from which to load the image.
    * @param type the requested image type (e.g. {@link BufferedImage#TYPE_INT_ARGB}, {@link BufferedImage#TYPE_BYTE_GRAY} etc)
    * @param padLeft padding to add to the left of the image. Zero if no padding is required.
    * @param padRight padding to add to the right of the image. Zero if no padding is required.
    * @param padTop padding to add to the top of the image. Zero if no padding is required.
    * @param padBottom padding to add to the bottom of the image. Zero if no padding is required.
    * @return the image read from the source or <code>null</code> if the image could not be read.
    */
   public static BufferedImage load( final StreamSource source,
                                     final int type,
                                     final int padLeft,
                                     final int padRight,
                                     final int padTop,
                                     final int padBottom )
   {
      final BufferedImage cached = tryCache( source );
      if( cached != null )
      {
         return cached;
      }

      try
      {
         final BufferedImage raw = ImageIO.read( source.getStream() );

         final int width = raw.getWidth() + padLeft + padRight;
         final int height = raw.getHeight() + padTop + padBottom;

         // Blit the image into a greyscale image just in case.
         final BufferedImage correctType = new BufferedImage( width, height, type );
         final Graphics2D g2d = correctType.createGraphics();
         g2d.setColor( Color.BLACK );
         g2d.fillRect( 0, 0, width, height );
         g2d.drawImage( raw, padLeft, padTop, null );
         g2d.dispose();

         CACHE.put( source, new WeakReference<>( correctType ) );
         return correctType;
      }
      catch( final IOException exception )
      {
         LOGGER.log( Level.SEVERE, exception.getMessage(), exception );
      }
      return null;
   }

   /**
    * Resizes the image to have the target width.
    *
    * @param original the image to resize.
    * @param newWidth the target width.
    * @return the resized image.
    */
   public static BufferedImage resize( final BufferedImage original, final int newWidth )
   {
      // May not have to resize
      if( newWidth == original.getWidth() )
      {
         return original;
      }

      final double scale = (double) newWidth / original.getWidth();
      final int newHeight = (int) ( original.getHeight() * scale );

      final BufferedImage image = new BufferedImage( newWidth, newHeight, original.getType() );
      final Graphics2D g2 = image.createGraphics();
      g2.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC );
      g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

      g2.drawImage( original, 0, 0, newWidth, newHeight, null );
      g2.dispose();

      return image;
   }

   /**
    * Gets the image as a rectangular array of shorts.
    * <p>
    * The image must be backed by raster of bytes. E.g. {@link BufferedImage#TYPE_BYTE_GRAY}. Additionally, this assumes that the
    * raster is flattened row-wise.
    * </p>
    *
    * @param image the image to process.
    * @return an array of values read from the underlying raster.
    */
   public static short[][] getRasterAsShorts( final BufferedImage image )
   {
      // Get the underlying int array for the data in the raster
      final DataBuffer dataBuffer = image.getRaster().getDataBuffer();
      if( !( dataBuffer instanceof DataBufferByte ) )
      {
         throw new IllegalStateException( "the image is not backed by a byte array" ); //$NON-NLS-1$
      }
      final DataBufferByte byteBuffer = (DataBufferByte) dataBuffer;
      final byte[] data = byteBuffer.getData();

      final int width = image.getWidth();
      final int height = image.getHeight();

      // COnvert to a 2D array
      final short[][] map = new short[ height ][ width ];
      int offset = 0;
      for( int y = 0; y < height; y++ )
      {
         for( int x = 0; x < width; x++ )
         {
            final short val = (short) ( data[ offset++ ] & 0xFF );
            map[ y ][ x ] = val;
         }
      }
      return map;
   }

   /**
    * Gets the image as a rectangular array of ints.
    * <p>
    * The image must be backed by raster of ints. E.g. {@link BufferedImage#TYPE_INT_ARGB}. Additionally, this assumes that the
    * raster is flattened row-wise.
    * </p>
    *
    * @param image the image to process.
    * @return an array of values read from the underlying raster.
    */
   public static int[][] getRasterAsInts( final BufferedImage image )
   {
      // Get the underlying int array for the data in the raster
      final DataBuffer dataBuffer = image.getRaster().getDataBuffer();
      if( !( dataBuffer instanceof DataBufferInt ) )
      {
         throw new IllegalStateException( "the image is not backed by an int array" ); //$NON-NLS-1$
      }
      final DataBufferInt intBuffer = (DataBufferInt) dataBuffer;
      final int[] data = intBuffer.getData();

      final int width = image.getWidth();
      final int height = image.getHeight();

      // COnvert to a 2D array
      final int[][] t2d = new int[ height ][ width ];
      int offset = 0;
      for( int y = 0; y < height; y++ )
      {
         System.arraycopy( data, offset, t2d[ y ], 0, width );
         offset += width;
      }
      return t2d;
   }

   private static BufferedImage tryCache( final StreamSource streamSource )
   {
      final WeakReference< BufferedImage > weakReference = CACHE.get( streamSource );
      if( weakReference != null )
      {
         return weakReference.get();
      }
      return null;
   }
}
