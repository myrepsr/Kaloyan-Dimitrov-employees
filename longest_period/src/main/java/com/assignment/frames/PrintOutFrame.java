package com.assignment.frames;

import com.assignment.backend.ProjectIDByPair;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class PrintOutFrame {

    private List<ProjectIDByPair> projectIDByPairList;
    private JFrame jf;
    private JScrollPane js;
    private JTable jt;
    private String[] col;
    private Object[][] data;

    public PrintOutFrame(List<ProjectIDByPair> projectIDByPairList){

        this.projectIDByPairList = projectIDByPairList;

        jf = new JFrame("JTable");
        col = new String[]{"Project ID", "Employee ID #1", "Employee ID #2", "Days Worked"};
        data = getData();
        jt = new JTable(data, col);
        js = new JScrollPane(jt);
        jf.add(js);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - jf.getWidth()) / 3;
        int y = (screenSize.height - jf.getHeight()) / 4;

        jf.setLocation(x,y);

        jf.pack();
        jf.setVisible(true);
    }

    public Object[][] getData(){
            Object[][] data = new Object[projectIDByPairList.size()][4];

            for(int i = 0; i < projectIDByPairList.size(); i++){
                ProjectIDByPair projectIDByPair = projectIDByPairList.get(i);

                Object[] objects = new Object[4];
                objects[0] = projectIDByPair.getProjectID();

                Set<Integer> treeSet = projectIDByPair.getPair();
                Iterator<Integer> iterator = treeSet.iterator();
                objects[1] = iterator.next();
                objects[2] = iterator.next();
                objects[3] = projectIDByPair.getProjectWorkTime();

                data[i] = objects;
            }

        return data;
    }
}
