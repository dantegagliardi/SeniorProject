package enums;

import java.awt.event.KeyEvent;

public enum RotateInput
{
   UP("^", KeyEvent.VK_UP, 1, 0),
   DOWN("v", KeyEvent.VK_DOWN, 1, 1),
   LEFT("<", KeyEvent.VK_LEFT, 0, 1),
   RIGHT(">", KeyEvent.VK_RIGHT, 2, 1), ;

   private String m_Label;
   private int    m_KeyBinding;
   private int    m_GridX;
   private int    m_GridY;

   /**
    * Creates a new direction input button
    * 
    * @param label
    *           String to identify the direction
    */
   RotateInput(String label, int binding, int x, int y)
   {
      m_Label = label;
      m_KeyBinding = binding;
      m_GridX = x;
      m_GridY = y;
   }

   public int getGridX()
   {
      return m_GridX;
   }

   public int getGridY()
   {
      return m_GridY;
   }

   public String getLabel()
   {
      return m_Label;
   }

   public int getKeyBinding()
   {
      return m_KeyBinding;
   }

   public void setLabel(String label)
   {
      m_Label = label;
   }

   public void setKeyBinding(int keyBinding)
   {
      m_KeyBinding = keyBinding;
   }

   public void setGridX(int gridX)
   {
      m_GridX = gridX;
   }

   public void setGridY(int gridY)
   {
      m_GridY = gridY;
   }
}
