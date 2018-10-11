/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import uk.co.eduardo.stereogram.MapResources;
import uk.co.eduardo.stereogram.TextureResources;
import uk.co.eduardo.stereogram.pipeline.StereogramPipeline;
import uk.co.eduardo.stereogram.texture.CentreAlignTextureOffsetFactory;
import uk.co.eduardo.stereogram.texture.TextureOffsetFactory;
import uk.co.eduardo.stereogram.texture.ZeroTextureOffsetFactory;

/**
 * Generates stereograms.
 *
 * @author erodri02
 */
@SuppressWarnings( "nls" )
public class StereogramPeformanceTest
{
   private static final Logger LOGGER = Logger.getLogger( StereogramPeformanceTest.class.getName() );

   private static final TextureResources DEFAULT_TEXTURE = TextureResources.TEXTURE_13;

   private static final MapResources DEFAULT_HEIGHT_MAP = MapResources.TEST_OFFSET;

   private static final TextureOffsetFactory ZERO_OFFSET_FACTORY = new ZeroTextureOffsetFactory();

   private static final TextureOffsetFactory CENTRE_ALIGN_FACTORY = new CentreAlignTextureOffsetFactory();

   private static final int DEFAULT_DEPTH = 30;

   private static final int TEST_COUNT = 2_000;

   /**
    * Main entiry point
    *
    * @param args ignored.
    */
   public static void main( final String[] args )
   {
      final StereogramPipeline pipeline = new StereogramPipeline();

      configureLogging();
      warmup( pipeline );

      rescaleTest( pipeline );
      offsetZeroTest( pipeline );
      offsetCentreTest( pipeline );
      textureTest( pipeline );
      mapTest( pipeline );
   }

   private static void configureLogging()
   {
      try
      {
         final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
         LogManager.getLogManager().readConfiguration( classLoader.getResourceAsStream( "logging.properties" ) );
      }
      catch( final IOException exception )
      {
         // Ignore and use default logger
      }
   }

   private static void warmup( final StereogramPipeline pipeline )
   {
      reset( pipeline );

      LOGGER.log( Level.INFO, "WARMING UP" );
      LOGGER.log( Level.INFO, "" );
      for( int i = 0; i < ( 10 * TEST_COUNT ); i++ )
      {
         pipeline.setTextureSouce( DEFAULT_TEXTURE );
         pipeline.setHeightMapSource( DEFAULT_HEIGHT_MAP );
         pipeline.getOutput();
      }
   }

   private static void rescaleTest( final StereogramPipeline pipeline )
   {
      reset( pipeline );

      // Time how long it takes to regenerate when changing apparent depth
      final long start = System.nanoTime();
      for( int i = 0; i < TEST_COUNT; i++ )
      {
         pipeline.setMaximumDepth( i % 40 );
         pipeline.getOutput();
      }
      outputTimings( "RESCALE TESTS", start );
   }

   private static void offsetZeroTest( final StereogramPipeline pipeline )
   {
      reset( pipeline );

      // Time how long it takes to regenerate when changing texture offsets
      final long start = System.nanoTime();
      for( int i = 0; i < TEST_COUNT; i++ )
      {
         pipeline.setTextureOffsetFactory( ZERO_OFFSET_FACTORY );
         pipeline.getOutput();
      }
      outputTimings( "TEXTURE OFFSET ZERO TESTS", start );
   }

   private static void offsetCentreTest( final StereogramPipeline pipeline )
   {
      reset( pipeline );

      // Time how long it takes to regenerate when changing texture offsets
      final long start = System.nanoTime();
      for( int i = 0; i < TEST_COUNT; i++ )
      {
         pipeline.setTextureOffsetFactory( CENTRE_ALIGN_FACTORY );
         pipeline.getOutput();
      }
      outputTimings( "TEXTURE OFFSET CENTRE TESTS", start );
   }

   private static void textureTest( final StereogramPipeline pipeline )
   {
      reset( pipeline );

      // Time how long it takes to regenerate when changing textures
      final long start = System.nanoTime();
      for( int i = 0; i < TEST_COUNT; i++ )
      {
         pipeline.setTextureSouce( DEFAULT_TEXTURE );
         pipeline.getOutput();
      }
      outputTimings( "RAW TEXTURE TESTS", start );
   }

   private static void mapTest( final StereogramPipeline pipeline )
   {
      reset( pipeline );

      // Time how long it takes to regenerate when changing depth map
      final long start = System.nanoTime();
      for( int i = 0; i < TEST_COUNT; i++ )
      {
         pipeline.setHeightMapSource( DEFAULT_HEIGHT_MAP );
         pipeline.getOutput();
      }
      outputTimings( "HEIGHT MAP UPDATE TESTS", start );
   }

   private static void reset( final StereogramPipeline pipeline )
   {
      // Sets default values on the pipeline.
      pipeline.setHeightMapSource( DEFAULT_HEIGHT_MAP );
      pipeline.setTextureSouce( DEFAULT_TEXTURE );
      pipeline.setTextureOffsetFactory( ZERO_OFFSET_FACTORY );
      pipeline.setMaximumDepth( DEFAULT_DEPTH );
   }

   private static void outputTimings( final String title, final long start )
   {
      final long elapsed = TimeUnit.NANOSECONDS.toMillis( System.nanoTime() - start );
      final double seconds = elapsed / 1000.0;
      LOGGER.log( Level.INFO, title );
      LOGGER.log( Level.INFO, String.format( "Took: %.3f s", seconds ) );
      LOGGER.log( Level.INFO, String.format( "Took: %.3f s per stereogram", seconds / TEST_COUNT ) );
      LOGGER.log( Level.INFO, String.format( "Took: %.3f Stereograms per second", TEST_COUNT / seconds ) );
      LOGGER.log( Level.INFO, "" );
   }
}
