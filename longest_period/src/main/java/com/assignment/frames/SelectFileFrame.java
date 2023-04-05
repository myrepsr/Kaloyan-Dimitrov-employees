package com.assignment.frames;

import com.assignment.backend.AllCommonProjectsByPair;
import com.assignment.backend.CustomFileReader;
import com.assignment.backend.DataRecord;
import com.assignment.backend.ProjectIDByPair;
import com.assignment.backend.services.DataService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectFileFrame extends JFrame implements ActionListener {
    private JButton button;
    private DataService dataService = DataService.getInstance();
    public SelectFileFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        button = new JButton("Select a file");
        button.addActionListener(this);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int x = (screenSize.width - this.getWidth()) / 3;
        int y = (screenSize.height - this.getHeight()) / 4;

        this.setLocation(x,y);
        this.add(button);
        this.pack();
        this.setSize(400,600);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == button){
            JFileChooser fileChooser = new JFileChooser();

            int response = fileChooser.showOpenDialog(null);

            if(response == JFileChooser.APPROVE_OPTION){
                try {
                    CustomFileReader customFileReader = CustomFileReader.getInstance(fileChooser.getSelectedFile().getPath());
                    dataService.setDataRecordList(customFileReader.readFile());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        dataService.mapEachProjectToPairOfEmployees();
        dataService.gatherAllCommonProjectsByPair();
        dataService.findPairWithMaxWorkTimeTogether();
        dataService.constructForPrintOut();

        new PrintOutFrame(dataService.getProjectIDByPairList());
        this.dispose();
    }
}
