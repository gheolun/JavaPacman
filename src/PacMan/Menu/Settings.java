package PacMan.Menu;

import PacMan.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Norbi on 2016. 11. 27..
 */
public class Settings extends JFrame implements ActionListener{
    private JPanel globPanel = new JPanel();
    private JButton okButton = new JButton("OK");
    private JButton cancelButton = new JButton("Cancel");


    private JComboBox ghostNum = new JComboBox();
    private JComboBox mapNames= new JComboBox();
    private TextField time = new TextField(4);
    private Checkbox mario = new Checkbox("Mario");
    private Checkbox pony = new Checkbox("Pony");

    public Settings() {
        super("Settings");

        setLocationRelativeTo(null);

        Label ghLabel = new Label("Number of Ghost: ");
        Label mLabel = new Label("Name of Map: ");
        Label tLabel = new Label("Time: ");

        for(int i = 0; i < 5; i++)
            ghostNum.addItem(i);

        File f = new File("map");
        String[] maps = f.list();

        for(String s: maps)
            mapNames.addItem(s.substring(0, s.lastIndexOf('.')));



        ghostNum.setSelectedIndex(0);
        time.setText(String.valueOf(300));
        mario.setState(Game.getMario());
        pony.setState(Game.getPony());

        okButton.setActionCommand("ok");
        okButton.addActionListener(this);

        cancelButton.setActionCommand("cancel");
        cancelButton.addActionListener(this);

        JPanel[] settingPanel = new JPanel[5];

        for(int i = 0; i < settingPanel.length; i++)
            settingPanel[i] = new JPanel();

        settingPanel[0].add(ghLabel);
        settingPanel[0].add(ghostNum);
        settingPanel[1].add(mLabel);
        settingPanel[1].add(mapNames);
        settingPanel[2].add(tLabel);
        settingPanel[2].add(time);
        settingPanel[3].add(mario);
        settingPanel[3].add(pony);
        settingPanel[4].add(okButton);
        settingPanel[4].add(cancelButton);

        globPanel.setLayout(new BoxLayout(globPanel, BoxLayout.Y_AXIS));

        for(JPanel jp: settingPanel)
            globPanel.add(jp);

        add(globPanel);



        pack();
        setSize(300, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void ok(){
        Game.setNumOfGhosts((int) ghostNum.getSelectedItem());
        Game.setMapName((String) mapNames.getSelectedItem());
        Game.setMario(mario.getState());
        Game.setPony(pony.getState());
        Game.setTime(Double.parseDouble(time.getText()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "ok":
                ok();
                dispose();
                break;
            case "cancel":
                dispose();
                break;
            default:
                break;
        }
    }
}
