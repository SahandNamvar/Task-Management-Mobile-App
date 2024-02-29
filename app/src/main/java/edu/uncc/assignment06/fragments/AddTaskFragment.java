package edu.uncc.assignment06.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import edu.uncc.assignment06.MainActivity;
import edu.uncc.assignment06.R;
import edu.uncc.assignment06.databinding.FragmentAddTaskBinding;
import edu.uncc.assignment06.models.Task;

public class AddTaskFragment extends Fragment {

    private FragmentAddTaskBinding binding;

    private String name, priorityStr, category;
    private int priorityInt;

    public AddTaskFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (priorityStr != null) {
            binding.textViewPriority.setText(priorityStr);
        }

        if (category != null) {
            binding.textViewCategory.setText(category);
        }

        // onClick Select Priority
        binding.buttonSelectPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectPriority();
            }
        });

        // onClick Select Category
        binding.buttonSelectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectCategory();
            }
        });

        // onClick Submit
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                priorityInt = 5;
                name = binding.editTextName.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(getActivity(), "Enter Name!", Toast.LENGTH_SHORT).show();
                } else if (priorityStr == null) {
                    Toast.makeText(getActivity(), "Select Priority!", Toast.LENGTH_SHORT).show();
                } else if (category == null) {
                    Toast.makeText(getActivity(), "Select Category!", Toast.LENGTH_SHORT).show();
                } else {
                    switch (priorityStr) {
                        case "Very Low":
                            priorityInt = 1;
                            break;
                        case "Low":
                            priorityInt = 2;
                            break;
                        case "Medium":
                            priorityInt = 3;
                            break;
                        case "High":
                            priorityInt = 4;
                            break;
                    }
                    Task task = new Task(name, category, priorityStr, priorityInt);
                    Log.d(MainActivity.TAG, "Submit Task: " + task.toString());
                    mListener.submitTask(task);
                }
            }
        });
    }


    // Interface
    AddTaskFragmentInterface mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AddTaskFragmentInterface) context;
    }

    public interface AddTaskFragmentInterface {
        void gotoSelectPriority();
        void gotoSelectCategory();
        void submitTask(Task task);
    }

    // Methods to set Values received from other fragments
    public void setPriorityStr(String priority) {
        this.priorityStr = priority;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}