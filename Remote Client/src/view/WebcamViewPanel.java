package view;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WebcamViewPanel extends DisplayGenerator
{

   @Override
   public JPanel createDisplay()
   {
      JPanel webcamPanel = new JPanel();
      Image icon;
      try
      {
         System.out.println(System.getProperty("user.dir"));
         icon = ImageIO.read(new File(
               "./resources/WebcamPlaceholder - Pokemon.png"));
      }
      catch (IOException e)
      {
         System.out.println(e.getMessage());
         return webcamPanel;
      }
      JLabel label = new JLabel(new ImageIcon(icon));
      webcamPanel.add(label);
      return webcamPanel;
   }

}
