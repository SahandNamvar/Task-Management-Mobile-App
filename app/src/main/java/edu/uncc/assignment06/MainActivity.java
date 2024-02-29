package edu.uncc.assignment06;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import edu.uncc.assignment06.fragments.AddTaskFragment;
import edu.uncc.assignment06.fragments.SelectCategoryFragment;
import edu.uncc.assignment06.fragments.SelectPriorityFragment;
import edu.uncc.assignment06.fragments.TaskDetailsFragment;
import edu.uncc.assignment06.fragments.TasksFragment;
import edu.uncc.assignment06.models.Data;
import edu.uncc.assignment06.models.Task;

public class MainActivity extends AppCompatActivity implements TasksFragment.TasksFragmentInterface, TaskDetailsFragment.TaskDetailsFragmentInterface,
        AddTaskFragment.AddTaskFragmentInterface, SelectPriorityFragment.SelectPriorityFragmentInterface,
        SelectCategoryFragment.SelectCategoryFragmentInterface {

    public static final String TAG = "debug";

    private FragmentManager fragmentManager;

    private ArrayList<Task> mTasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTasks.addAll(Data.sampleTestTasks); //adding for testing

        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().add(R.id.rootView, new TasksFragment(), "TasksFragment").commit();


    }

    // ****** Override Methods from TasksFragmentInterface ******
    @Override
    public ArrayList<Task> getAllTasks() {
        return this.mTasks;
    }

    @Override
    public void gotoAddTask() {
        fragmentManager.beginTransaction().replace(R.id.rootView, new AddTaskFragment(), "AddTaskFragment").addToBackStack(null).commit();
    }

    @Override
    public void gotoTaskDetails(Task task) {
        fragmentManager.beginTransaction().replace(R.id.rootView, TaskDetailsFragment.newInstance(task), "TaskDetailsFragment").addToBackStack(null).commit();
    }

    @Override
    public void clearAllTask() {
        mTasks.clear();
    }

    @Override
    public void deleteItem(Task task) {
        mTasks.remove(task);
    }

    // ****** Override Methods from TaskDetailsFragmentInterface ******
    @Override
    public void goBack() {
        fragmentManager.popBackStack();
    }

    @Override
    public void deleteTask(Task task) {
        mTasks.remove(task);
        fragmentManager.popBackStack();
    }

    // ****** Override Methods from AddTaskFragmentInterface ******
    @Override
    public void gotoSelectPriority() {
        fragmentManager.beginTransaction().replace(R.id.rootView, new SelectPriorityFragment(), "SelectPriorityFragment").addToBackStack(null).commit();
    }

    @Override
    public void gotoSelectCategory() {
        fragmentManager.beginTransaction().replace(R.id.rootView, new SelectCategoryFragment(), "SelectCategoryFragment").addToBackStack(null).commit();
    }

    @Override
    public void submitTask(Task task) {
        mTasks.add(task);
        fragmentManager.popBackStack();
    }

    // ****** Override Methods from SelectPriorityFragmentInterface ******
    @Override
    public void sendPriority(String priority) {
        AddTaskFragment fragment = (AddTaskFragment) fragmentManager.findFragmentByTag("AddTaskFragment");
        if(fragment!=null) {
            fragment.setPriorityStr(priority);
        }
        fragmentManager.popBackStack();
    }

    @Override
    public void cancelPriority() {
        fragmentManager.popBackStack();
    }

    // ****** Override Methods from SelectCategoryFragmentInterface ******
    @Override
    public void sendCategory(String category) {
        AddTaskFragment fragment = (AddTaskFragment) fragmentManager.findFragmentByTag("AddTaskFragment");
        if(fragment!=null) {
            fragment.setCategory(category);
        }
        fragmentManager.popBackStack();
    }

    @Override
    public void cancelCategory() {
        fragmentManager.popBackStack();
    }
}