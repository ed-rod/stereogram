/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram;

import java.util.ResourceBundle;

/**
 * Externalized string resources for the jar.
 *
 * @author erodri02
 */
@SuppressWarnings( "nls" )
public class Resources
{
   private static final ResourceBundle BUNDLE = ResourceBundle.getBundle( "stereogram" );

   static final String VIEWER_TITLE = getString( "viewer.title" );

   static final String VIEWER_MENU_FILE = getString( "viewer.menu.file" );

   static final String VIEWER_MENU_FILE_SAVE = getString( "viewer.menu.file.save" );

   static final String VIEWER_MENU_FILE_EXIT = getString( "viewer.menu.file.exit" );

   static final String VIEWER_IMAGE = getString( "viewer.image" );

   static final String VIEWER_IMAGE_OPEN = getString( "viewer.image.open" );

   static final String VIEWER_IMAGE_OPEN_TITLE = getString( "viewer.image.open.title" );

   static final String VIEWER_IMAGE_OPEN_FILTER = getString( "viewer.image.open.filter" );

   static final String VIEWER_TEXTURE = getString( "viewer.texture" );

   static final String VIEWER_TEXTURE_OPEN = getString( "viewer.texture.open" );

   static final String VIEWER_OPTIONS = getString( "viewer.options" );

   static final String VIEWER_DEPTH_SLIDER = getString( "viewer.depth.slider" );

   static final String VIEWER_DEPTH_SLIDER_TOOLTIP = getString( "viewer.depth.slider.tooltip" );

   static final String VIEWER_TEXTURE_SLIDER = getString( "viewer.texture.slider" );

   static final String VIEWER_TEXTURE_SLIDER_TOOLTIP = getString( "viewer.texture.slider.tooltip" );

   static final String VIEWER_TEXTURE_ALIGN = getString( "viewer.texture.align" );

   static final String VIEWER_INVERT = getString( "viewer.invert" );

   static final String VIEWER_SAVE_TITLE = getString( "viewer.save.title" );

   static final String VIEWER_SAVE_FILTER = getString( "viewer.save.filter" );

   static final String VIEWER_SAVE_FILE_PREFIX = getString( "viewer.save.file.prefix" );

   private static String getString( final String key )
   {
      return BUNDLE.getString( key );
   }
}
