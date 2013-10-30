package controller;

import java.io.IOException;

import messages.PanMessage;
import networking.ConnectionManager;
import view.OptionsPanel;
import view.RotateControlPanel;
import constants.Constants;
import enums.RotateInput;
import enums.TargetInput;

public class ActionManager
{
   /**
    * Carries out the appropriate actions for pan and tilt commands. Will
    * eventually communicate with the ConnectionManager to deliver this
    * information to a server hosted on the Pandaboard
    * 
    * Information will be used to pan and tilt the servos connected to the
    * turret apparatus.
    * 
    * @param rotate
    *           RotateInput enum that was triggered
    */
   public static void handleRotateInput(RotateInput rotate)
   {
      // Testing print statements. This information might be kept around in a
      // log console.
      switch (rotate)
      {
         case UP:
         case DOWN:
            break;
         case RIGHT:
            RemoteSentryClient.m_PositionPan += getDegreesPan() * 9.22;
            break;
         case LEFT:
            RemoteSentryClient.m_PositionPan -= getDegreesPan() * 9.22;
            break;
         default:
            throw new IllegalArgumentException(rotate
                  + " is not a valid argument for the Rotation Handler.");
      }

      System.out.println("CLIENT: Moving to " + RemoteSentryClient.m_PositionPan);
      updatePosition();
   }

   /**
    * Carries out the appropriate actions for target source commands. Will
    * eventually communicate with the ConnectionManager to deliver this
    * information to a server hosted on the Pandaboard
    * 
    * Information will be used to switch between remote operation and CV target
    * acquisition
    * 
    * @param target
    */
   public static void handleTargetInput(TargetInput target)
   {
      switch (target)
      {
         case CV: // Disable rotation controls while in CV targeting
            RotateControlPanel.setRotationControlsEnabled(false);
            break;
         case RC: // Enable rotation controls while in Manual targeting
            RotateControlPanel.setRotationControlsEnabled(true);
            break;
      }
      System.out.println("CLIENT: " + target.getLabelText());
   }

   /**
    * Retrieves the values of the sensitivity setting.
    * 
    * @return Returns an integer representing the sensitivity.
    */
   private static int getSensitivity()
   {
      return OptionsPanel.getSensitivity();
   }

   /**
    * Calculates the degrees that any given click will tile
    * 
    * @return Returns a float representing the degrees of tilt
    */
   private static float getDegreesTilt()
   {
      return getSensitivity() * Constants.DEGREES_PER_SENSITIVITY_TILT;
   }

   /**
    * Calculates the degrees that any given click will pan
    * 
    * @return Returns a float representing the degrees of pan
    */
   private static float getDegreesPan()
   {
      return getSensitivity() * Constants.DEGREES_PER_SENSITIVITY_PAN;
   }

   /**
    * Updates the position of the servo based on the value of
    * RemoteSentryClient.m_Position
    */
   private static void updatePosition()
   {
      // If we have surpassed the max value, clip
      if (RemoteSentryClient.m_PositionPan > Constants.POSITION_PAN_MAX)
         RemoteSentryClient.m_PositionPan = Constants.POSITION_PAN_MAX;

      // If we have surpassed the min value, clip
      if (RemoteSentryClient.m_PositionPan < Constants.POSITION_PAN_MIN)
         RemoteSentryClient.m_PositionPan = Constants.POSITION_PAN_MIN;

      try
      {
         ConnectionManager.sendMessage(new PanMessage(RemoteSentryClient.m_PositionPan));
      }
      catch (IOException e)
      {
         // Do nothing. Hopefully the keypress comes in again, and we update
         // rapidly.
      }
   }
}
