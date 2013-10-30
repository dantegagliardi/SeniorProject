package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import buffer.WebcamBuffer;

import com.googlecode.javacv.cpp.opencv_core.IplImage;

import constants.Constants;


/**
 * This one isn't a DisplayGenerator, but we still use the .createDisplay()
 * design found in DisplayGenerator for consistency.
 * 
 * @author Dante
 */
public class WebcamViewPanel extends JPanel
{
   private IplImage m_WebcamFrame;
   private WebcamBuffer m_WebcamBuffer;

   public WebcamViewPanel()
   {
      m_WebcamBuffer = new WebcamBuffer();
      
      setBackground(Color.BLACK);
      setDoubleBuffered(false);
      new Timer(20, new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            getNextFrame();
            
            if (Constants.DEBUG)
               System.out.print("*");
         }
      }).start();
   }

   /**
    * Draw the picture onto this component.
    */
   @Override
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      g.drawImage(m_WebcamFrame.getBufferedImage(), 0, 0, this);
      m_WebcamFrame.release();
   }

   /**
    * Grab the next frame from the buffer, paint it.
    */
   public void getNextFrame()
   {
      m_WebcamFrame = m_WebcamBuffer.getFrameFromBuffer();
      repaint();
   }

   /**
    * Follow the design pattern of the rest of our panels. Most of them are
    * DisplayGenerators, which have this method. Let's just be consistent.
    * 
    * @return
    */
   public JPanel createDisplay()
   {
      return this;
   }
}
