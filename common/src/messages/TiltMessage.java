package messages;

public class TiltMessage extends SerializableMessage
{
   private float m_TiltAmount;
   
   public TiltMessage(float tiltAmount)
   {
      m_TiltAmount = tiltAmount;
   }
   
   public float getTiltAmount()
   {
      return m_TiltAmount;
   }
}
