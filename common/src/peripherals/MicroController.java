package peripherals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import jssc.SerialPort;
import jssc.SerialPortException;
import constants.Constants;

/**
 * This class handles the micro controller, and messaging to it.
 * 
 * @author Dante
 * 
 */
public class MicroController implements AutoCloseable
{
   private static SerialPort                  m_SerialPort;
   private static LinkedBlockingQueue<String> m_MicroControllerRx;

   private static ArrayList<Byte>             m_Message;
   private static LinkedBlockingQueue<Byte>   m_SlidingEndOfMessage;

   public MicroController() throws SerialPortException
   {
      // It's a static field. Don't get a new one if we already have one.
      if (m_SerialPort == null)
      {
         m_SerialPort = new SerialPort("/dev/ttyO3");
         m_SerialPort.openPort();
         m_SerialPort.setParams(SerialPort.BAUDRATE_9600,
               SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
               SerialPort.PARITY_NONE);

         m_SerialPort.writeBytes(getPositionString(
               Constants.POSITION_PAN_CENTER).getBytes());

         m_MicroControllerRx = new LinkedBlockingQueue<String>();

         // Start a thread that reads from the micro controller
         new Thread(new Runnable()
         {
            @Override
            public void run()
            {
               // Start reading, and don't stop
               while (m_SerialPort.isOpened())
               {
                  try
                  {
                     // Read the byte
                     readByteFromController();
                  }
                  catch (SerialPortException e)
                  {
                     // TODO: Add an error system to handle these, and report
                     // them to the user. We can't just throw these, we have to
                     // bubble them asynchronously
                  }
               }
            }
         }).start();
      }
   }

   /**
    * Reads bytes from the micro controller, sends stores them in a sliding
    * frame. On each read, if we have a full frame, we put the head into our
    * message, and add the new byte. After this, we check if the sliding frame
    * is the end of message delimiter
    * 
    * @throws SerialPortException
    */
   private static void readByteFromController() throws SerialPortException
   {
      // Get one byte
      byte singleByte = m_SerialPort.readBytes(1)[0];

      // Add it to the sliding frame
      m_SlidingEndOfMessage.add(singleByte);
      if (m_SlidingEndOfMessage.size() == Constants.END_OF_MESSAGE.length)
      {
         // Add head of queue to message
         addByteToMessage();
         // Check the sliding frame for end of message, but only
         // if the head of the frame equals the start of the end of message
         // token. This short-circuit should improve performance
         if (m_SlidingEndOfMessage.peek() == Constants.END_OF_MESSAGE[0])
            checkForEndOfMessage();
      }
   }

   /**
    * Adds the head of the sliding frame to the message.
    */
   private static void addByteToMessage()
   {
      try
      {
         m_Message.add(m_SlidingEndOfMessage.take());
      }
      catch (InterruptedException e)
      {
         // Can't happen. The queue won't block, because it's got something.
      }
   }

   /**
    * Checks the sliding frame for end of message, and saves the message string
    */
   private static void checkForEndOfMessage()
   {
      // Convert the queue to a byte array. This might be expensive
      // TODO: See how expensive this actually is to do for every byte read
      Byte[] bytes = m_SlidingEndOfMessage.<Byte> toArray(new Byte[] {});

      // Check each byte of the frame against the constant
      for (int i = 0; i < Constants.END_OF_MESSAGE.length; i++)
      {
         if (bytes[i] != Constants.END_OF_MESSAGE[i]) return;
      }

      processMessage(m_Message.toArray(new Byte[]{}));
   }

   private static void processMessage(Byte[] message)
   {

   }

   /**
    * This handles sending the pan value to the micro controller
    * 
    * @param panValue
    */
   public void pan(float panValue)
   {

   }

   /**
    * This handles sending the tilt value to the micro controller
    * 
    * @param panValue
    */
   public void tilt(float tiltValue)
   {

   }

   /**
    * Returns a string representing the position float, followed by a carriage
    * return
    * 
    * @param pos
    *           Position to rotate the servo to
    * @return
    */
   public static String getPositionString(float pos)
   {
      return Float.toString(pos) + "\r";
   }

   @Override
   public void close() throws IOException
   {
      try
      {
         m_SerialPort.closePort();
      }
      catch (SerialPortException e)
      {
         // We couldn't close the port. At least we tried.
      }
   }

}
