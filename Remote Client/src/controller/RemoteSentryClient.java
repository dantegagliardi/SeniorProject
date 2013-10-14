package controller;

import javax.swing.JFrame;

import view.MainWindow;

public class RemoteSentryClient
{
   /**
    * Run this application
    * 
    * @param args
    *           No arguments are supported yet. Document them here.
    */
   public static void main(String[] args)
   {
      // Display and run our gui
      javax.swing.SwingUtilities.invokeLater(new Runnable()
      {
         public void run()
         {
            createAndShowGUI();
         }
      });
   }

   /**
    * Create the display and render it
    */
   private static void createAndShowGUI()
   {
      JFrame frame = new JFrame("Remote Sentry Client");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // Get a new MainWindow and display it
      frame.getContentPane().add((new MainWindow()).createDisplay());

      // Pack and render the application
      frame.pack();
      frame.setVisible(true);
   }
}
