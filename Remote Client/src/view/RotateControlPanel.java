package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.ActionManager;
import enums.RotateInput;

public class RotateControlPanel extends DisplayGenerator implements
      ActionListener
{
   private static Map<RotateInput, JButton> m_Buttons;

   public RotateControlPanel()
   {
      // Creates the buttons used for rotation controls.
      // Saved to a hashmap for later lookup
      m_Buttons = new HashMap<RotateInput, JButton>();
      for (RotateInput direction : RotateInput.values())
      {
         // Add label, key binding, action name, and action listener
         JButton button = new JButton(direction.getLabel());
         button.setMnemonic(direction.getKeyBinding());
         button.setActionCommand(direction.name());
         button.addActionListener(this);

         m_Buttons.put(direction, button);
      }
   }

   public static void setRotationControlsEnabled(boolean enabled)
   {
      for (RotateInput rotate : RotateInput.values())
      {
         m_Buttons.get(rotate).setEnabled(enabled);
      }
   }

   @Override
   public JPanel createDisplay()
   {
      JPanel directionPane = new JPanel(new GridBagLayout());

      for (RotateInput rotate : RotateInput.values())
      {
         directionPane.add(
               m_Buttons.get(rotate),
               getGridBagLayout(rotate.getGridX(), rotate.getGridY(),
                     GridBagConstraints.CENTER));
      }

      return directionPane;
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {
      ActionManager
            .handleRotateInput(RotateInput.valueOf(e.getActionCommand()));
   }

}
