package com.tdn.timeline;

import java.util.ArrayList;
import java.util.List;

public class History extends TimelineEvent {

    private boolean stackable = false;
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

    public boolean isStackable() {
        return stackable;
    }

    public void setStackable(boolean stackable) {
        this.stackable = stackable;
    }
}
