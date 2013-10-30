package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import messages.SerializableMessage;
import constants.Constants;

public class ConnectionManager
{

   private static Socket             m_ConnectionSocket;
   private static ObjectOutputStream m_OutToServer;
   private static ObjectInputStream  m_InFromServer;
   private static InputStream        m_ServerInputStream;
   private static boolean            m_IsAlive = false;

   public static void connectToServer() throws UnknownHostException,
         IOException
   {
      m_ConnectionSocket = new Socket(Constants.SERVER_HOST,
            Constants.SERVER_PORT);
      m_ServerInputStream = m_ConnectionSocket.getInputStream();
      m_OutToServer = new ObjectOutputStream(
            m_ConnectionSocket.getOutputStream());
      m_InFromServer = new ObjectInputStream(m_ServerInputStream);

      if (!m_ConnectionSocket.isClosed() && m_ConnectionSocket.isConnected())
         m_IsAlive = true;
   }

   /**
    * Sends a message to the server
    * 
    * @param type
    *           Type of the message, used as a header to discern data
    * @param message
    *           The contents of the message
    * @throws IOException
    */
   public static void sendMessage(SerializableMessage message)
         throws IOException
   {
      // Write the object, reset the stream (memory leak)
      m_OutToServer.writeObject(message);
      m_OutToServer.reset();
   }

   /**
    * Returns whether or not the connection is alive. It should always be alive,
    * as an exception should get raised if something happens
    * 
    * @return
    */
   public static boolean isAlive()
   {
      return m_IsAlive;
   }

   /**
    * Get the receive channel (from server)
    * 
    * @return
    */
   public static ObjectInputStream getRx()
   {
      return m_InFromServer;
   }

   public static InputStream getServerInputStream()
   {
      return m_ServerInputStream;
   }

   /**
    * Get the transmit channel (to server)
    * 
    * @return
    */
   public static ObjectOutputStream getTx()
   {
      return m_OutToServer;
   }
}
