package controller;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;

import networking.ConnectionManager;

import com.googlecode.javacv.FrameGrabber.Exception;

import jssc.SerialPortException;
import peripherals.MicroController;
import peripherals.WebcamStream;
import threads.ServerListener;
import view.MainWindow;
import constants.Constants;

public class RemoteSentryClient
{
   public static MicroController m_MicroController;
   public static WebcamStream    m_WebcamStream;
   public static float           m_PositionPan = Constants.POSITION_PAN_CENTER;
   public static float           m_PositionTilt;

   /**
    * Run this application
    * 
    * @param args
    *           No arguments are supported yet. Document them here.
    * @throws IOException
    * @throws UnknownHostException
    * @throws SerialPortException
    * @throws Exception
    */
   public static void main(String[] args) throws UnknownHostException,
         IOException, SerialPortException, Exception
   {
      if (Constants.ENABLE_NETWORKING) ConnectionManager.connectToServer();

      if (Constants.ENABLE_SERIAL) m_MicroController = new MicroController();

      listenToServer();

      // Display and run our gui
      javax.swing.SwingUtilities.invokeLater(new Runnable()
      {
         public void run()
         {
            createAndShowGUI();
         }
      });
   }

   private static void listenToServer()
   {
      new Thread(new ServerListener()).start();
   }

   /**
    * Create the display and render it
    */
   private static void createAndShowGUI()
   {
      JFrame frame = new JFrame("Remote Sentry Client");
      frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

      // Get a new MainWindow and display it
      frame.getContentPane().add((new MainWindow()).createDisplay());

      // Pack and render the application
      frame.pack();
      frame.setVisible(true);
   }
}
