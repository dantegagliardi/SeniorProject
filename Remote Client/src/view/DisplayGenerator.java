package view;

import java.awt.GridBagConstraints;

import javax.swing.JPanel;

public abstract class DisplayGenerator
{
   public abstract JPanel createDisplay();

   /**
    * Returns the GridBagConstraints used to properly layout a panel.
    * 
    * @param x
    *           X location (0 = left)
    * @param y
    *           Y location (0 = top)
    * @param xweight
    *           Weight for x scaling
    * @param yweight
    *           Weight for y scaling
    * @param fill
    *           Fill type
    * @return
    */
   protected GridBagConstraints getGridBagLayout(int x, int y, float xweight,
         float yweight, int fill)
   {
      GridBagConstraints layout = new GridBagConstraints();

      layout.gridx = x;
      layout.gridy = y;
      layout.weightx = xweight;
      layout.weighty = yweight;
      layout.fill = fill;

      return layout;
   }

   protected GridBagConstraints getGridBagLayout(int x, int y, int fill)
   {
      return getGridBagLayout(x, y, 0, 0, fill);
   }
}
