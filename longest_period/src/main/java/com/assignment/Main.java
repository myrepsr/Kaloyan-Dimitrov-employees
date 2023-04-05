package com.assignment;

import com.assignment.frames.SelectFileFrame;

/*
       Solution explained:
       After reading the file I needed to manipulate the data a bit
       in order to be easier for me to find the pair with the maximum work
       time together on common projects.

       Steps:
       1. Reading the file and storing each record in DataRecord instances(supports unsorted data in the file)
       2. Creating pairs of employees and mapping each pair to their projects -> mapEachProjectToPairOfEmployees
       3. Encapsulating all common projects of every pair in objects with their total work together -> gatherAllCommonProjectsByPair()
       4. Finding the pair with the maximum work time together compared to other pairs -> findPairWithMaxWorkTimeTogether()
       5. Constructing the data for printing out -> constructForPrintOut()

       Data is also displayed on the console during the steps.
 */


public class Main {

    public static void main(String[] args) {
        new SelectFileFrame();
    }
}