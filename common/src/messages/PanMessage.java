package messages;

import messages.SerializableMessage;

public class PanMessage extends SerializableMessage
{
   private float m_PanAmount;
   
   public PanMessage(float panAmount)
   {
      m_PanAmount = panAmount;
   }
   
   public float getPanAmount()
   {
      return m_PanAmount;
   }
}
