package com.assignment.backend;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@Setter
@Getter
@ToString
public class AllCommonProjectsByPair {
    private Set<Map<Integer, Long>> commonProjectIDs = new HashSet<>();
    private Set<Integer> pair = new TreeSet<>();
    private long totalWorkTogether;

    public void setCommonProjectIDAndWorkingTime(int projectID, long projectWorkTime) {
        Map<Integer, Long> commonProjectMappedWorkingTime = new TreeMap<>();
        commonProjectMappedWorkingTime.put(projectID, projectWorkTime);
        this.commonProjectIDs.add(commonProjectMappedWorkingTime);
    }

    public void addToTotalWorkTogether(long projectWorkTogether){
        this.totalWorkTogether += projectWorkTogether;
    }
}
