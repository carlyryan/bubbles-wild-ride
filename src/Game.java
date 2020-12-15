import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

public class Game implements Runnable {
	public void run() {
        final JFrame frame = new JFrame("Bubbles' Wild Ride");
        frame.setLocation(300, 300);
        String input = JOptionPane.showInputDialog("Name:");
        
        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Playing...");
        status_panel.add(status);
        
        try {
        	BufferedReader in = new BufferedReader(new FileReader("files/scores.txt"));
        	String line;
        	String finalMessage = "Instructions : Bubbles needs to eat all the love" + '\n'
        			+ "boosters before the evil Mojo Jojo catches her!" + '\n'
        			+ "As Bubbles says : 'Mercy is for the weak!'" + '\n'
        			+ "Use the arrow keys to move. Press ok to begin." + '\n'
        			+ "HIGH SCORES : " + '\n';
        	while ((line=in.readLine()) != null) {
        		finalMessage += line + '\n';
        	}
        	in.close();
        	JOptionPane.showMessageDialog(status_panel,finalMessage);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
        // Main playing area
        final MazeState maze = new MazeState(status, input);
        frame.add(maze, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        final JButton reset = new JButton("Play!");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                maze.reset();
            }
        });
        control_panel.add(reset);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        maze.reset();
    }

	// Main method in  order to run the game
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}
