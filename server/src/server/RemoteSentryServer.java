package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import jssc.SerialPortException;
import messages.FrameMessage;
import messages.PanMessage;
import messages.SerializableMessage;
import messages.TiltMessage;
import peripherals.MicroController;
import peripherals.WebcamStream;

import com.googlecode.javacv.FrameGrabber.Exception;

import constants.Constants;

public class RemoteSentryServer implements AutoCloseable
{
   private static ServerSocket       m_AcceptSocket;
   private static Socket             m_Client;
   private static ObjectInputStream  m_FromClient;
   private static ObjectOutputStream m_ToClient;
   private static MicroController    m_MicroController;
   private static WebcamStream       m_Webcam;
   private static Runnable           m_WebcamThread;

   public static void main(String args[]) throws IOException,
         SerialPortException, Exception
   {
      // When debugging, don't try to open serial port
      if (Constants.ENABLE_SERIAL) getMicroController();

      getWebcamStream();

      // Open a listening port
      m_AcceptSocket = new ServerSocket(Constants.SERVER_PORT);

      // As long as nobody is connected... (essentially while(true))
      while (m_Client == null)
      {
         // Wait for someone to connect.
         waitForConnection();

         new Thread(m_WebcamThread).start();

         try
         {
            // As long as the client is still connected, listen to messages.
            // In the future, we could thread this, but we have no real reason
            // to allow multiple connections simultaneously.
            while (!m_Client.isClosed())
            {
               if (!m_Client.isInputShutdown())
               {
                  // Read an object, check type, cast it
                  Object inObj;
                  try
                  {
                     // Read the object, reset the stream, handle the message
                     inObj = m_FromClient.readObject();
//                     m_FromClient.reset();
                     if (inObj instanceof SerializableMessage)
                        processMessage((SerializableMessage) inObj);
                  }
                  catch (ClassNotFoundException e)
                  {
                     // We lost that one. Can't go back now.
                  }
               }
            }
         }
         catch (SocketException se)
         {
            // Catch the exception that happens when a client disconnects.
            // TODO: Do this cleanly. Exceptions are grody.
            System.out.println("SERVER: Client disconnected, most likely");
            m_Client = null;
         }
      }
   }

   /**
    * Opens the serial port on /dev/ttyO3 (the microcontroller)
    * 
    * @throws SerialPortException
    */
   private static void getMicroController() throws SerialPortException
   {
      m_MicroController = new MicroController();
   }

   /**
    * Waits for an incoming connection. Accepts the connection, and sets up the
    * input and output streams to communicate over.
    * 
    * @throws IOException
    */
   private static void waitForConnection() throws IOException
   {
      // Accept a connection
      m_Client = m_AcceptSocket.accept();

      // Get the Rx and Tx channels for the client
      m_FromClient = new ObjectInputStream(m_Client.getInputStream());
      m_ToClient = new ObjectOutputStream(m_Client.getOutputStream());

   }

   private static void getWebcamStream() throws Exception
   {
      m_Webcam = new WebcamStream();

      // Create a new runnable that will harvest frames in the background
      m_WebcamThread = new Runnable()
      {
         @Override
         public void run()
         {
            while (m_Client != null && !m_Client.isClosed()
                  && m_Client.isConnected())
            {
               try
               {
                  // Write the frame, then reset the stream (memory reasons)
                  m_ToClient.writeObject(new FrameMessage(m_Webcam.getFrame()));
                  m_ToClient.reset();
               }
               catch (Exception | IOException e)
               {
                  // We couldn't grab this image. Oh well.
               }
            }
         }
      };
   }

   /**
    * Handles incoming messages. Strips the pseudo header, and runs a function
    * based on that header.
    * 
    * @param message
    */
   private static void processMessage(SerializableMessage message)
   {
      if (message instanceof PanMessage)
         sendPanString((PanMessage)message);
      
      if (message instanceof TiltMessage)
         sendTiltString((TiltMessage)message);
   }

   /**
    * Sends a string (formatted by the client) to the microcontroller that will
    * update the pan of the sentry
    * 
    * @param message
    */
   private static void sendPanString(PanMessage message)
   {
      if (Constants.DEBUG)
         System.out.println("SERVER: Pan " + message.getPanAmount());
   }

   /**
    * Sends a string (formatted by the client) to the microcontroller that will
    * update the tilt of the sentry
    * 
    * @param message
    */
   private static void sendTiltString(TiltMessage message)
   {
      if (Constants.DEBUG)
         System.out.println("SERVER: Tilt " + message.getTiltAmount());
   }

   @Override
   public void close() throws IOException
   {
      m_AcceptSocket.close();
      m_Client.close();
   }

}
