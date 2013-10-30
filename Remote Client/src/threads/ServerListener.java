package threads;

import java.io.IOException;
import java.io.ObjectInputStream;

import buffer.WebcamBuffer;

import messages.FrameMessage;
import messages.SerializableMessage;
import networking.ConnectionManager;
import constants.Constants;

public class ServerListener implements Runnable
{
   private static ObjectInputStream m_InFromServer;
   private WebcamBuffer             m_WebcamBuffer;

   public ServerListener()
   {
      m_InFromServer = ConnectionManager.getRx();
      m_WebcamBuffer = new WebcamBuffer();
   }

   @Override
   public void run()
   {
      while (ConnectionManager.isAlive())
      {
         readMessageFromServer();
      }
   }

   private void readMessageFromServer()
   {
      Object inObj;
      try
      {
         // Read an object, reset the stream, handle the message
         inObj = m_InFromServer.readObject();
//         m_InFromServer.reset();
         if (inObj instanceof SerializableMessage)
            processMessage((SerializableMessage)inObj);
      }
      catch (ClassNotFoundException | IOException e)
      {
         // We lost that message. Oh well.
      }
   }

   /**
    * Read the message header, and decide what to do with the rest
    * 
    * @param message
    */
   private void processMessage(SerializableMessage message)
   {
      if (message instanceof FrameMessage) handleFrame((FrameMessage) message);
      
      message = null;
   }

   /**
    * Receive a frame
    * 
    * @param frame
    */
   private void handleFrame(FrameMessage frame)
   {
      if (Constants.DEBUG) System.out.print(".");

      if (frame != null) m_WebcamBuffer.addFrameToBuffer(frame.getImage());
      else System.out.println("CLIENT: Received a bad frame...");
   }

}
