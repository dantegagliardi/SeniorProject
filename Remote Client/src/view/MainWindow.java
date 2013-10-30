package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainWindow extends DisplayGenerator
{
   private JPanel m_MainPanel;

   public MainWindow()
   {
      m_MainPanel = new JPanel(new GridBagLayout());
   }

   public JPanel createDisplay()
   {
      // Place the webcam pane in the left, filling everything
      m_MainPanel.add(new WebcamViewPanel().createDisplay(),
            getGridBagLayout(0, 0, 2, 2, GridBagConstraints.CENTER));

      // Place the option and control panes on the right
      m_MainPanel.add(getOptionControlPanel(),
            getGridBagLayout(1, 0, GridBagConstraints.CENTER));

      return m_MainPanel;
   }

   /**
    * Returns a panel containing the options pane and control pane stacked
    * 
    * @return JPanel containing the options and control panes
    */
   private JPanel getOptionControlPanel()
   {
      JPanel optionControlPanel = new JPanel(new GridBagLayout());

      optionControlPanel.add(new OptionsPanel().createDisplay(),
            getGridBagLayout(0, 0, GridBagConstraints.CENTER));

      optionControlPanel.add(new JLabel("\n \n \n \n"),
            getGridBagLayout(0, 1, 0, 2, GridBagConstraints.CENTER));

      optionControlPanel.add(new RotateControlPanel().createDisplay(),
            getGridBagLayout(0, 2, GridBagConstraints.CENTER));

      return optionControlPanel;
   }
}
