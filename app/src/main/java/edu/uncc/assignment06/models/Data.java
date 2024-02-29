package edu.uncc.assignment06.models;

import java.util.ArrayList;

public class Data {
    public static final String[] categories = {
            "Work/Professional",
            "Personal Errands",
            "Home & Family",
            "Health & Fitness",
            "Finance",
            "Education & Learning",
            "Leisure & Social",
            "Projects",
            "Urgent/Important"
    };

    public static final String[] priorities = {
            "Very High", //5
            "High",
            "Medium",
            "Low",
            "Very Low" //1
    };

    public static final ArrayList<Task> sampleTestTasks = new ArrayList<Task>(){{
        this.add(new Task("Task 1", "Work/Professional", "High", 4));
        this.add(new Task("Task 2", "Personal Errands", "Low", 2));
        this.add(new Task("Task 3", "Projects", "Very High", 5));
        this.add(new Task("Task 4", "Home & Family", "Medium", 3));
        this.add(new Task("Task 5", "Finance", "Very Low", 1));
    }};

}
