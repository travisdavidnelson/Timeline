package com.tdn.timeline;

import java.util.ArrayList;
import java.util.List;

public class TimelineChannel extends TimelineEvent {

    private List<DynastyGroup> dynastyGroups;

    public TimelineChannel() {
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
