package buffer;

import java.util.concurrent.LinkedBlockingQueue;

import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class WebcamBuffer
{
   private static LinkedBlockingQueue<IplImage> m_FrameBuffer;
   
   public WebcamBuffer()
   {
      if (m_FrameBuffer == null)
         m_FrameBuffer = new LinkedBlockingQueue<IplImage>();
   }
   
   public void addFrameToBuffer(IplImage frame)
   {
      m_FrameBuffer.add(frame);
   }
   
   public IplImage getFrameFromBuffer()
   {
      // Continue looping until we get a frame.
      while (true)
      {
         try
         {
            // Return the frame
            return m_FrameBuffer.take();
         }
         catch (InterruptedException e)
         {
            // Whatever. We'll get back to it.
         }
      }
   }
}
