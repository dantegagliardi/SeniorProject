package messages;

import java.nio.ByteBuffer;

import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class FrameMessage extends SerializableMessage
{
   private byte[] m_FrameAsBytes;
   private int m_Width, m_Height, m_Depth, m_Channels;
   
   public FrameMessage(IplImage image)
   {
      m_FrameAsBytes = convertImageToBytes(image);
      
      m_Width = image.width();
      m_Height = image.height();
      m_Depth = image.depth();
      m_Channels = image.nChannels();
   }
   
   public IplImage getImage()
   {
      IplImage image = IplImage.create(m_Width, m_Height, m_Depth, m_Channels);
      image.getByteBuffer().put(m_FrameAsBytes);
      
      return image;
   }
   
   private byte[] convertImageToBytes(IplImage image)
   {
      ByteBuffer buffer = image.getByteBuffer();
      byte[] array = new byte[buffer.capacity()];
      
      for (int i = 0; i < buffer.capacity(); i++)
         array[i] = buffer.get();
      
      return array;
   }
}
