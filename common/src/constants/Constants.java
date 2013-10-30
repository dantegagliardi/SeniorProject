package constants;

public interface Constants
{
   public static final float DEGREES_PER_SENSITIVITY_TILT = 0.5f;
   public static final float DEGREES_PER_SENSITIVITY_PAN = 1.8f;
   
   public static final int SERVER_PORT = 15789;
   public static final String SERVER_HOST = "localhost";
   
   // Something that is unlikely to be seen in the wild
   public static final String HEADER_TOKEN = "`:&:&`";
   public static final byte[] END_OF_MESSAGE = "&`:`:&".getBytes();
   
   public static final float POSITION_PAN_CENTER = 1500.0f;
   public static final float POSITION_PAN_MAX    = 2210.0f;
   public static final float POSITION_PAN_MIN    = 0670.0f;
   
   public static final boolean DEBUG = true;
   public static final boolean ENABLE_NETWORKING = true;
   public static final boolean ENABLE_SERIAL = false;
}
