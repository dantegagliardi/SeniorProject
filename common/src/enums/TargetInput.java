package enums;

import java.awt.event.KeyEvent;

public enum TargetInput
{
   RC("Remote Control", KeyEvent.VK_L, true),
   CV("CV Targeting", KeyEvent.VK_K, false), ;

   private int     m_KeyBinding;
   private String  m_LabelText;
   private boolean m_DefaultOption;

   /**
    * Creates a new targeting radio option
    * 
    * @param label
    *           Name of this option
    * @param binding
    *           Key to bind this option to
    * @param defaultOption
    *           True if this should be the default selection
    */
   TargetInput(String label, int binding, boolean defaultOption)
   {
      m_KeyBinding = binding;
      m_LabelText = label;
      m_DefaultOption = defaultOption;
   }

   public int getKeyBinding()
   {
      return m_KeyBinding;
   }

   public String getLabelText()
   {
      return m_LabelText;
   }

   public boolean isDefaultOption()
   {
      return m_DefaultOption;
   }

   public void setKeyBinding(int keyBinding)
   {
      m_KeyBinding = keyBinding;
   }

   public void setLabelText(String labelText)
   {
      m_LabelText = labelText;
   }

   public void setDefaultOption(boolean defaultOption)
   {
      m_DefaultOption = defaultOption;
   }
}