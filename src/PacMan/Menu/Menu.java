package PacMan.Menu;

import PacMan.Game;
import PacMan.ScoreBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Norbi on 2016. 10. 22..
 */
public class Menu extends JFrame implements ActionListener {

    private JPanel  menuPanel  = new JPanel();
    private JButton playButton = new JButton("Play PacMan");
    private JButton settingsButton = new JButton("Settings");
    private JButton saveButton = new JButton("Save");
    private JButton loadButton = new JButton("Load");
    private JButton exitButton = new JButton("Exit PacMan");


    public Menu() {
        super("Pac-Man");
        setLocationRelativeTo(null);

        playButton.addActionListener(this);
        playButton.setActionCommand("play");
        playButton.setFont(new Font(playButton.getFont().getFontName(), playButton.getFont().getStyle(), 30));
        saveButton.addActionListener(this);
        saveButton.setActionCommand("save");
        saveButton.setFont(new Font(playButton.getFont().getFontName(), playButton.getFont().getStyle(), 30));
        loadButton.addActionListener(this);
        loadButton.setActionCommand("load");
        loadButton.setFont(new Font(playButton.getFont().getFontName(), playButton.getFont().getStyle(), 30));
        settingsButton.addActionListener(this);
        settingsButton.setActionCommand("settings");
        settingsButton.setFont(new Font(playButton.getFont().getFontName(), playButton.getFont().getStyle(), 30));
        exitButton.addActionListener(this);
        exitButton.setActionCommand("exit");
        exitButton.setFont(new Font(playButton.getFont().getFontName(), playButton.getFont().getStyle(), 30));


        menuPanel.setLayout(new GridLayout(5, 1, 0, 20));

        menuPanel.add(playButton);
        menuPanel.add(settingsButton);
        menuPanel.add(saveButton);
        menuPanel.add(loadButton);
        menuPanel.add(exitButton);
        add(menuPanel);

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            /** Menu */
            case "play":
                Thread gameThread = new Thread(new Game());
                gameThread.start();
                break;
            case "settings":
                Settings settings = new Settings();
                break;
            case "save":
                ScoreBoard.save("scoreBoard.txt");
                break;
            case "load":
                ScoreBoard.load("scoreBoard.txt");
                break;
            case "exit":
                System.exit(0);
                break;
        }
    }
}
