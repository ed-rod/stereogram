/*
 * Copyright (c) PRGX.
 * All Rights Reserved.
 */
package uk.co.eduardo.stereogram;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;

import uk.co.eduardo.stereogram.pipeline.StereogramPipeline;
import uk.co.eduardo.stereogram.texture.CentreAlignTextureOffsetFactory;
import uk.co.eduardo.stereogram.texture.TextureOffsetFactory;
import uk.co.eduardo.stereogram.texture.ZeroTextureOffsetFactory;

/**
 * Generates stereograms.
 *
 * @author erodri02
 */
public class StereogramViewer
{
   private static final String PNG = "png"; //$NON-NLS-1$

   private static final String PNG_EXT = '.' + PNG;

   private static final Border PADDING_BORDER = BorderFactory.createEmptyBorder( 10, 10, 10, 10 );

   private static Logger LOGGER;

   /**
    * Main entiry point
    *
    * @param args ignored.
    */
   public static void main( final String[] args )
   {
      configureLogging();
      SwingUtilities.invokeLater( () -> start() );
   }

   private static void configureLogging()
   {
      try
      {
         final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
         LogManager.getLogManager().readConfiguration( classLoader.getResourceAsStream( "logging.properties" ) ); //$NON-NLS-1$

         LOGGER = Logger.getLogger( StereogramViewer.class.getName() );
      }
      catch( final IOException exception )
      {
         // Ignore and use default logger
      }
   }

   private static void start()
   {
      setLookAndFeel();
      final JFrame frame = createFrame();

      final StereogramPipeline pipeline = new StereogramPipeline();
      pipeline.setHeightMapSource( MapResources.TEST_OFFSET );
      pipeline.setTextureOffsetFactory( new ZeroTextureOffsetFactory() );

      // Create the menu
      final JMenuBar menubar = new JMenuBar();
      final JMenu fileMenu = new JMenu( Resources.VIEWER_MENU_FILE );
      menubar.add( fileMenu );

      final JMenuItem saveItem = new JMenuItem( Resources.VIEWER_MENU_FILE_SAVE );
      saveItem.addActionListener( e -> save( frame, pipeline ) );
      fileMenu.add( saveItem );
      final JMenuItem exitItem = new JMenuItem( Resources.VIEWER_MENU_FILE_EXIT );
      exitItem.addActionListener( e -> frame.dispose() );
      fileMenu.add( exitItem );

      frame.setJMenuBar( menubar );

      // Create the screen controls
      final JComponent viewer = createViewerComponent( pipeline );
      final JSlider depthSlider = createDepthSliderComponent( viewer, pipeline );
      final JSlider textureSlider = createTextureSliderComponent( viewer, pipeline );
      final JList< StreamSource > maps = createMapsComponent( viewer, pipeline );
      final JComponent loadMap = createLoadMapComponent( frame, maps );
      final JList< StreamSource > textures = createResourcesComponent( viewer, pipeline );
      final JComponent loadTexture = createLoadTextureomponent( frame, textures );
      final JComponent offset = createOffsetComponent( viewer, pipeline );
      final JComponent invert = createInvertComponent( viewer, pipeline );

      // Hidden image area
      final DefaultFormBuilder imageBuilder = new DefaultFormBuilder( new FormLayout( "fill:p:grow", "fill:p:grow, $rg, p" ) ); //$NON-NLS-1$ //$NON-NLS-2$
      imageBuilder.append( new JScrollPane( maps ) );
      imageBuilder.nextRow();
      imageBuilder.append( loadMap );
      imageBuilder.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createTitledBorder( Resources.VIEWER_IMAGE ),
                                                                  PADDING_BORDER ) );

      // Texture area
      final DefaultFormBuilder textureBuilder = new DefaultFormBuilder( new FormLayout( "fill:p:grow", "fill:p:grow, $rg, p" ) ); //$NON-NLS-1$ //$NON-NLS-2$
      textureBuilder.append( new JScrollPane( textures ) );
      textureBuilder.nextRow();
      textureBuilder.append( loadTexture );
      textureBuilder.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createTitledBorder( Resources.VIEWER_TEXTURE ),
                                                                    PADDING_BORDER ) );

      // Options
      final DefaultFormBuilder optionsBuilder = new DefaultFormBuilder( new FormLayout( "p, $rg, p", //$NON-NLS-1$
                                                                                        " p, $rg, p, $rg, p, $rg, p" ) ); //$NON-NLS-1$
      optionsBuilder.append( Resources.VIEWER_DEPTH_SLIDER, depthSlider );
      optionsBuilder.nextRow();
      optionsBuilder.append( Resources.VIEWER_TEXTURE_SLIDER, textureSlider );
      optionsBuilder.nextRow();
      optionsBuilder.append( offset, 3 );
      optionsBuilder.nextRow();
      optionsBuilder.append( invert, 3 );
      optionsBuilder.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createTitledBorder( Resources.VIEWER_OPTIONS ),
                                                                    PADDING_BORDER ) );

      // Left-hand pane
      final DefaultFormBuilder leftPaneBuilder = new DefaultFormBuilder( new FormLayout( "fill:p:grow", //$NON-NLS-1$
                                                                                         "fill:p:grow(0.5), 10dlu, fill:p:grow(0.5), 10dlu, p, p:grow(1)" ) ); //$NON-NLS-1$
      leftPaneBuilder.append( imageBuilder.getPanel() );
      leftPaneBuilder.nextRow();
      leftPaneBuilder.append( textureBuilder.getPanel() );
      leftPaneBuilder.nextRow();
      leftPaneBuilder.append( optionsBuilder.getPanel() );

      // Main panel including the viewer
      final DefaultFormBuilder mainPanelBuilder = new DefaultFormBuilder( new FormLayout( "d, $rg, fill:p:grow", "fill:p:grow" ) ); //$NON-NLS-1$ //$NON-NLS-2$
      mainPanelBuilder.append( leftPaneBuilder.getPanel() );
      mainPanelBuilder.append( viewer );
      mainPanelBuilder.setDefaultDialogBorder();

      frame.setContentPane( mainPanelBuilder.getPanel() );
      frame.setVisible( true );
   }

   private static void save( final JFrame frame, final StereogramPipeline pipeline )
   {
      final BufferedImage output = pipeline.getOutput();
      String initialName = Resources.VIEWER_SAVE_FILE_PREFIX + pipeline.getHeightMapSource().toString();
      if( !initialName.toLowerCase().endsWith( PNG_EXT ) )
      {
         initialName = initialName + PNG_EXT;
      }

      final JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle( Resources.VIEWER_SAVE_TITLE );
      fileChooser.setFileFilter( new FileNameExtensionFilter( Resources.VIEWER_SAVE_FILTER, PNG ) );
      fileChooser.setSelectedFile( new File( initialName ) );
      if( fileChooser.showSaveDialog( frame ) == JFileChooser.APPROVE_OPTION )
      {
         Path path = fileChooser.getSelectedFile().toPath();
         if( !path.getFileName().toString().toLowerCase().endsWith( PNG_EXT ) )
         {
            path = path.resolveSibling( path.getFileName().toString() + PNG_EXT );
         }

         try( OutputStream stream = Files.newOutputStream( path ) )
         {
            ImageIO.write( output, PNG, stream );
         }
         catch( final IOException exception )
         {
            LOGGER.log( Level.SEVERE, exception.getMessage(), exception );
         }
      }
   }

   private static JComponent createViewerComponent( final StereogramPipeline pipeline )
   {
      final JPanel viewer = new JPanel( new BorderLayout() )
      {
         @Override
         public void paintComponent( final Graphics g )
         {
            super.paintComponent( g );

            final BufferedImage image = pipeline.getOutput();

            g.setColor( Color.BLACK );
            g.fillRect( 0, 0, getWidth(), getHeight() );

            // Draw the image in the centre
            final int x = ( getWidth() - image.getWidth() ) / 2;
            final int y = ( getHeight() - image.getHeight() ) / 2;

            g.drawImage( image, x, y, null );
            // g.setColor( new Color( 0, 0, 0, 220 ) );
            // g.fillRect( 0, 0, getWidth(), getHeight() );
         }
      };

      viewer.setBorder( BorderFactory.createEtchedBorder( EtchedBorder.LOWERED ) );
      return viewer;
   }

   private static JSlider createDepthSliderComponent( final JComponent viewer, final StereogramPipeline pipeline )
   {
      final JSlider slider = new JSlider( new DefaultBoundedRangeModel( 20, 0, 5, 40 ) );
      slider.getModel().addChangeListener( e -> {

         pipeline.setMaximumDepth( slider.getModel().getValue() );
         viewer.repaint();
      } );

      slider.setToolTipText( Resources.VIEWER_DEPTH_SLIDER_TOOLTIP );
      return slider;
   }

   private static JSlider createTextureSliderComponent( final JComponent viewer, final StereogramPipeline pipeline )
   {
      final JSlider slider = new JSlider( new DefaultBoundedRangeModel( 128, 0, 72, 256 ) );
      slider.getModel().addChangeListener( e -> {

         pipeline.setTextureSize( slider.getModel().getValue() );
         viewer.repaint();
      } );

      slider.setToolTipText( Resources.VIEWER_TEXTURE_SLIDER_TOOLTIP );
      return slider;
   }

   private static JList< StreamSource > createMapsComponent( final JComponent viewer, final StereogramPipeline pipeline )
   {
      final DefaultListModel< StreamSource > listModel = new DefaultListModel<>();
      for( final StreamSource mapResource : MapResources.values() )
      {
         listModel.addElement( mapResource );
      }
      final JList< StreamSource > list = new JList<>( listModel );
      list.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
      list.addListSelectionListener( e -> {

         final StreamSource selected = list.getSelectedValue();
         if( selected == null )
         {
            // Reset selection from our old value.
            list.setSelectedValue( pipeline.getHeightMapSource(), false );
         }
         else
         {
            pipeline.setHeightMapSource( selected );
            viewer.repaint();
         }
      } );
      list.setSelectedValue( MapResources.SHARK, true );
      return list;
   }

   private static JComponent createLoadMapComponent( final JFrame frame, final JList< StreamSource > list )
   {
      final JButton loadButton = new JButton( Resources.VIEWER_IMAGE_OPEN );
      loadButton.addActionListener( e -> openImage( frame, list, Resources.VIEWER_IMAGE_OPEN_TITLE ) );

      return ButtonBarFactory.buildLeftAlignedBar( loadButton );
   }

   private static JList< StreamSource > createResourcesComponent( final JComponent viewer, final StereogramPipeline pipeline )
   {
      final DefaultListModel< StreamSource > listModel = new DefaultListModel<>();
      for( final StreamSource textureResource : TextureResources.values() )
      {
         listModel.addElement( textureResource );
      }
      final JList< StreamSource > list = new JList<>( listModel );
      list.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
      list.addListSelectionListener( e -> {

         final StreamSource selected = list.getSelectedValue();
         if( selected == null )
         {
            // Reset selection from our old value.
            list.setSelectedValue( pipeline.getTextureSource(), false );
         }
         else
         {
            pipeline.setTextureSouce( selected );
            viewer.repaint();
         }
      } );
      list.setSelectedValue( TextureResources.TEXTURE_1, true );
      return list;
   }

   private static JComponent createLoadTextureomponent( final JFrame frame, final JList< StreamSource > list )
   {
      final JButton loadButton = new JButton( Resources.VIEWER_IMAGE_OPEN );
      loadButton.addActionListener( e -> openImage( frame, list, Resources.VIEWER_IMAGE_OPEN_TITLE ) );

      return ButtonBarFactory.buildLeftAlignedBar( loadButton );
   }

   private static JComponent createOffsetComponent( final JComponent viewer, final StereogramPipeline pipeline )
   {
      final JCheckBox checkbox = new JCheckBox( Resources.VIEWER_TEXTURE_ALIGN );
      checkbox.addItemListener( e -> {

         final TextureOffsetFactory factory = checkbox.isSelected() ? new CentreAlignTextureOffsetFactory() : new ZeroTextureOffsetFactory();
         pipeline.setTextureOffsetFactory( factory );
         viewer.repaint();
      } );
      checkbox.setSelected( false );
      return checkbox;
   }

   private static JComponent createInvertComponent( final JComponent viewer, final StereogramPipeline pipeline )
   {
      final JCheckBox checkbox = new JCheckBox( Resources.VIEWER_INVERT );
      checkbox.addItemListener( e -> {

         pipeline.setInvertDepth( checkbox.isSelected() );
         viewer.repaint();
      } );
      checkbox.setSelected( false );
      return checkbox;
   }

   private static JFrame createFrame()
   {
      final JFrame frame = new JFrame( Resources.VIEWER_TITLE );
      frame.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
      frame.setSize( 1600, 800 );
      frame.setLocationRelativeTo( null );

      return frame;
   }

   private static final void openImage( final JFrame frame, final JList< StreamSource > list, final String dialogTitle )
   {
      final JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle( dialogTitle );
      fileChooser.setFileFilter( new FileNameExtensionFilter( Resources.VIEWER_IMAGE_OPEN_FILTER,
                                                              ImageIO.getReaderFileSuffixes() ) );
      if( fileChooser.showOpenDialog( frame ) == JFileChooser.APPROVE_OPTION )
      {
         final Path path = fileChooser.getSelectedFile().toPath();
         final StreamSource source = new PathStreamSource( path );

         // Add to our list
         final DefaultListModel< StreamSource > model = (DefaultListModel< StreamSource >) list.getModel();
         model.addElement( source );
         list.setSelectedValue( source, true );
      }
   }

   private static final void setLookAndFeel()
   {
      try
      {
         UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
      }
      catch( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException exception )
      {
         // Ignore and use default LaF
      }
   }
}
