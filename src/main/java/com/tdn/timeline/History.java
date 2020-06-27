package com.tdn.timeline;

import java.util.ArrayList;
import java.util.List;

public class History extends TimelineEvent {

    private List<DynastyGroup> dynastyGroups;

    public History() {
        dynastyGroups = new ArrayList<>();
    }

    public List<DynastyGroup> getDynastyGroups() {
        return dynastyGroups;
    }

    public void setDynastyGroups(List<DynastyGroup> dynastyGroups) {
        this.dynastyGroups = dynastyGroups;
    }

    public void addDynastyGroup(DynastyGroup dynastyGroup) {
        this.dynastyGroups.add(dynastyGroup);
    }
}
