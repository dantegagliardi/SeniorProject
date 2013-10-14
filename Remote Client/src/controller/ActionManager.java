package controller;

import view.OptionsPanel;
import view.RotateControlPanel;
import constants.Constants;
import enums.RotateInput;
import enums.TargetInput;

public class ActionManager
{
   /**
    * Carries out the appropriate actions for pan and tilt commands. Will eventually communicate
    * with the ConnectionManager to deliver this information to a server hosted on the Pandaboard
    * 
    * Information will be used to pan and tilt the servos connected to the turret apparatus.
    * 
    * @param rotate RotateInput enum that was triggered
    */
   public static void handleRotateInput(RotateInput rotate)
   {
      // Testing print statements. This information might be kept around in a log console.
      System.out.print(rotate.name());
      switch (rotate)
      {
         case UP:
         case DOWN:
            System.out.println("\tTilting: [S - " + getSensitivity() + "] [D - " + getDegreesTilt() + "]");
            break;
         case RIGHT:
         case LEFT:
            System.out.println("\tPanning: [S - " + getSensitivity() + "] [D - " + getDegreesPan() + "]");
            break;
         default:
            throw new IllegalArgumentException(rotate + " is not a valid argument for the Rotation Handler.");
      }
   }

   /**
    * Carries out the appropriate actions for target source commands. Will eventually communicate
    * with the ConnectionManager to deliver this information to a server hosted on the Pandaboard
    * 
    * Information will be used to switch between remote operation and CV target acquisition
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
      System.out.println(target.getLabelText());
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
}
