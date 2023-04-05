package com.assignment.backend.services;

import com.assignment.backend.AllCommonProjectsByPair;
import com.assignment.backend.DataRecord;
import com.assignment.backend.ProjectIDByPair;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Getter
@Setter
public class DataService {
    private static DataService dataService;
    private LocalDate empOneDateFrom;
    private LocalDate empOneDateTo;
    private ProjectIDByPair projectIDByPair;
    private AllCommonProjectsByPair allCommonProjectsByPair;
    private List<ProjectIDByPair> projectIDByPairList;
    private final List<AllCommonProjectsByPair> allCommonProjectsByPairList = new ArrayList<>();
    private AllCommonProjectsByPair pairWithMaxWorkTimeTogether;
    private List<DataRecord> dataRecordList = new ArrayList<>();

    public static DataService getInstance(){
        if(dataService == null)
            dataService = new DataService();

        return dataService;
    }
    public void findPairWithMaxWorkTimeTogether(){

        /*
            In this method I'm finding the pair with maximum working
            time together on common projects compared to other pairs

            source: List of all common projects by pair
            product: Pair with maximum work time together on common projects
         */

        Long val = allCommonProjectsByPairList.stream().map(AllCommonProjectsByPair::getTotalWorkTogether).max(Long::compare).get();
        System.out.println(val);

        Optional<AllCommonProjectsByPair> optionalAllCommonProjectsByPair = allCommonProjectsByPairList.stream().filter(a -> a.getTotalWorkTogether() == val).findFirst();
        optionalAllCommonProjectsByPair.ifPresent(o -> pairWithMaxWorkTimeTogether = o);
    }
    public void constructForPrintOut(){

        /*
            In this method I'm constructing the data which needs to be printed out
            in PrintOutFrame

            I already found the pair with maximum work time together on their common projects

            I'm filling the list with instances of projects mapped to the pair and project work time
            I'm not adding dates, because they are not wanted for print out

            print out data -> records of { Project ID, Pair, Project Work Time }
         */
        projectIDByPairList.clear();
        for(Map<Integer, Long> commonProjects : pairWithMaxWorkTimeTogether.getCommonProjectIDs()){

            Set<Map.Entry<Integer, Long>> entries = commonProjects.entrySet();

            for(Map.Entry<Integer, Long> entry : entries){
                ProjectIDByPair projectIDByPair = new ProjectIDByPair();
                projectIDByPair.setProjectID(entry.getKey());
                projectIDByPair.setProjectWorkTime(entry.getValue());
                projectIDByPair.setPair(pairWithMaxWorkTimeTogether.getPair());
                projectIDByPairList.add(projectIDByPair);
            }
        }

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        projectIDByPairList.forEach(System.out::println);
    }
    public void gatherAllCommonProjectsByPair(){
        /*
            In this method I'm encapsulating all common projects of each pair in a single instance
            I'm also evaluating the total work time together on all of their common projects
         */


        List<Set<Integer>> trackPassedPairsList = new ArrayList<>(projectIDByPairList.size());

        for(ProjectIDByPair projectIDByPair : projectIDByPairList){

            if(!trackPassedPairsList.contains(projectIDByPair.getPair())) {
                allCommonProjectsByPair = new AllCommonProjectsByPair();
                allCommonProjectsByPair.setPair(projectIDByPair.getPair());
                allCommonProjectsByPair.setTotalWorkTogether(projectIDByPair.getProjectWorkTime());
                allCommonProjectsByPair.setCommonProjectIDAndWorkingTime(projectIDByPair.getProjectID(), projectIDByPair.getProjectWorkTime());
                allCommonProjectsByPairList.add(allCommonProjectsByPair);
                trackPassedPairsList.add(projectIDByPair.getPair());
            }
            else {
                for(AllCommonProjectsByPair allCommonProjectsByPair1 : allCommonProjectsByPairList){
                    if(allCommonProjectsByPair1.getPair().equals(projectIDByPair.getPair()))
                        allCommonProjectsByPair = allCommonProjectsByPair1;
                }
                allCommonProjectsByPair.setCommonProjectIDAndWorkingTime(projectIDByPair.getProjectID(), projectIDByPair.getProjectWorkTime());
                allCommonProjectsByPair.addToTotalWorkTogether(projectIDByPair.getProjectWorkTime());
            }

        }

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        allCommonProjectsByPairList.forEach(System.out::println);
    }
    public void mapEachProjectToPairOfEmployees() {
        /*
            In this method I'm encapsulating each project with its pair
            in ProjectIDByPair objects.

            source: List of read data records from the file
            product: List of every project mapped to its pair with project work time, starting and ending dates

            Later it will be easier for me to evaluate the total work time for every pair
        */

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        List<Integer> trackPassedProjects = new ArrayList<>(dataRecordList.size());
        projectIDByPairList = new ArrayList<>(dataRecordList.size());

        for (DataRecord dataRecord : dataRecordList) {
            if (!trackPassedProjects.contains(dataRecord.projectID())) {
                empOneDateFrom = dataRecord.dateFrom();
                empOneDateTo = dataRecord.dateTo();

                projectIDByPair = new ProjectIDByPair();
                projectIDByPair.setProjectID(dataRecord.projectID());
                projectIDByPair.addEmployee(dataRecord.empID());
                projectIDByPair.setEmpOneDateFrom(empOneDateFrom);
                projectIDByPair.setEmpOneDateTo(empOneDateTo);

                trackPassedProjects.add(dataRecord.projectID());
                projectIDByPairList.add(projectIDByPair);
            } else {
                for(ProjectIDByPair projectIDByPair1 : projectIDByPairList){
                    if(projectIDByPair1.getProjectID() == dataRecord.projectID())
                        projectIDByPair = projectIDByPair1;
                }
                projectIDByPair.addEmployee(dataRecord.empID());

                LocalDate empTwoDateFrom = dataRecord.dateFrom();
                LocalDate empTwoDateTo = dataRecord.dateTo();
                LocalDate empOneDateFrom = projectIDByPair.getEmpOneDateFrom();
                LocalDate empOneDateTo = projectIDByPair.getEmpOneDateTo();

                projectIDByPair.setEmpTwoDateFrom(empTwoDateFrom);
                projectIDByPair.setEmpTwoDateTo(empTwoDateTo);

                LocalDate workDateTogetherFrom = (empOneDateFrom.isAfter(empTwoDateFrom))? empOneDateFrom : empTwoDateFrom;
                LocalDate workDateTogetherTo = (empOneDateTo.isBefore(empTwoDateTo))? empOneDateTo : empTwoDateTo;

                long workTimeTogether = ChronoUnit.DAYS.between(workDateTogetherFrom, workDateTogetherTo);
                projectIDByPair.setProjectWorkTime(workTimeTogether);
            }
        }
        projectIDByPairList.forEach(System.out::println);
    }
}
