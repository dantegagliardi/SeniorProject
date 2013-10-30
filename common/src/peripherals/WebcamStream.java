package peripherals;

import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.FrameGrabber.Exception;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class WebcamStream implements AutoCloseable
{
   private static FrameGrabber m_Webcam;

   public WebcamStream() throws Exception
   {
      if (m_Webcam == null)
      {
         m_Webcam = new OpenCVFrameGrabber("");
         // m_Webcam.setImageHeight(1024);
         // m_Webcam.setImageWidth(768);
         m_Webcam.start();
      }
   }

   public IplImage getFrame() throws Exception
   {
      return m_Webcam.grab();
   }

   @Override
   public void close()
   {
      if (m_Webcam != null)
      {
         try
         {
            m_Webcam.stop();
         }
         catch (Exception e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         
         m_Webcam = null;
      }
   }
}
