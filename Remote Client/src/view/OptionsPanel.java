package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import controller.ActionManager;

import enums.TargetInput;

public class OptionsPanel extends DisplayGenerator implements ActionListener
{
   private static final int          GRID_NO_LIMIT           = 0;

   private static final int          SENSITIVITY_DEFAULT     = 4;
   private static final int          SENSITIVITY_STEP_SIZE   = 1;
   private static final int          SENSITIVITY_MIN         = 1;
   private static final int          SENSITIVITY_MAX         = 10;

   private static final String       SENSITIVITY_INPUT_LABEL = "Targeting Sensitivity";

   // List of radio button options. Used for deferred panel generation
   private static List<JRadioButton> m_RadioButtons;
   // Widget used to control how much a single click will move the turret
   private static JSpinner           m_SensitivityInput;

   public OptionsPanel()
   {
      // Create our spinner
      SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(
            SENSITIVITY_DEFAULT, SENSITIVITY_MIN, SENSITIVITY_MAX,
            SENSITIVITY_STEP_SIZE);
      m_SensitivityInput = new JSpinner(spinnerNumberModel);

      // Create our list of radio buttons
      m_RadioButtons = new ArrayList<JRadioButton>();
      for (TargetInput option : TargetInput.values())
      {
         // Add label, key binding, action name, default, and action listener
         JRadioButton radioButton = new JRadioButton(option.getLabelText());
         radioButton.setMnemonic(option.getKeyBinding());
         radioButton.setActionCommand(option.name());
         radioButton.setSelected(option.isDefaultOption());
         radioButton.addActionListener(this);

         m_RadioButtons.add(radioButton);
      }
   }

   @Override
   public JPanel createDisplay()
   {
      // Panel we are going to be returning.
      // This panel will contain the panels that hold our options
      JPanel optionPanel = new JPanel(new GridBagLayout());

      optionPanel.add(getSensitivityInputPanel(),
            getGridBagLayout(0, 0, GridBagConstraints.HORIZONTAL));
      optionPanel.add(getTargetingInputPanel(),
            getGridBagLayout(0, 1, GridBagConstraints.VERTICAL));

      return optionPanel;
   }

   /**
    * Creates the sensitivity option spinner
    * 
    * @return
    */
   private JPanel getSensitivityInputPanel()
   {
      JPanel spinnerPanel = new JPanel();

      JLabel spinnerLabel = new JLabel(SENSITIVITY_INPUT_LABEL);

      spinnerPanel.add(m_SensitivityInput);
      spinnerPanel.add(spinnerLabel);

      return spinnerPanel;
   }

   /**
    * Creates the targeting input source option
    * 
    * @return
    */
   private JPanel getTargetingInputPanel()
   {
      // Radio button panel, containing the targeting options
      JPanel radioPanel = new JPanel(new GridLayout(GRID_NO_LIMIT, 1));
      ButtonGroup radioButtonGroup = new ButtonGroup();

      // Add each radio button we constructed earlier to both
      // the panel and the button group
      for (JRadioButton radioButton : m_RadioButtons)
      {
         radioButtonGroup.add(radioButton);
         radioPanel.add(radioButton);
      }

      return radioPanel;
   }

   /**
    * Returns the enum representing the current targeting type Returns null if
    * no type is selected. This shouldn't happen in ordinary use.
    * 
    * @return The targeting type (Remote, CV, etc)
    */
   public static TargetInput getTargetInput()
   {
      for (JRadioButton option : m_RadioButtons)
      {
         // Find the currently selected radio
         if (option.isSelected())
         // Return the enum associated with it
            return TargetInput.valueOf(option.getActionCommand());
      }

      return null;
   }

   /**
    * Returns the value of the JSpinner representing the targeting sensitivity
    * 
    * @return Value of the sensitivity
    */
   public static int getSensitivity()
   {
      return (int) m_SensitivityInput.getValue();
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {
      ActionManager
            .handleTargetInput(TargetInput.valueOf(e.getActionCommand()));
   }

}
