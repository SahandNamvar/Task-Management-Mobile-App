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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import edu.uncc.assignment06.MainActivity;
import edu.uncc.assignment06.R;
import edu.uncc.assignment06.databinding.FragmentTaskDetailsBinding;
import edu.uncc.assignment06.models.Task;

public class TaskDetailsFragment extends Fragment {

    private FragmentTaskDetailsBinding binding;

    private static final String ARG_PARAM1 = "TASK_KEY";

    private Task task;

    public TaskDetailsFragment() {}

    public static TaskDetailsFragment newInstance(Task task) {
        TaskDetailsFragment fragment = new TaskDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.task = (Task) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTaskDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textViewName.setText(task.getName());
        binding.textViewCategory.setText(task.getCategory());
        binding.textViewPriority.setText(task.getPriorityStr());

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goBack();
            }
        });

        binding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.deleteTask(task);
                Toast.makeText(getActivity(), task.getName() + " Deleted!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    TaskDetailsFragmentInterface mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (TaskDetailsFragmentInterface) context;
    }

    public interface TaskDetailsFragmentInterface {
        void goBack();
        void deleteTask(Task task);
    }
}