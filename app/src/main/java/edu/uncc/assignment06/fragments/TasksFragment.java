package edu.uncc.assignment06.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.uncc.assignment06.MainActivity;
import edu.uncc.assignment06.R;
import edu.uncc.assignment06.databinding.FragmentTasksBinding;
import edu.uncc.assignment06.models.Task;

public class TasksFragment extends Fragment {

    private ArrayList<Task> mTasks = new ArrayList<>();

    private FragmentTasksBinding binding;

    private TasksAdapter adapter;

    public TasksFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTasksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Request & assign 'ArrayList<Task> mTasks' from MainActivity
        if (mListener != null && mListener.getAllTasks() != null) {
            this.mTasks = mListener.getAllTasks();
            // Log.d(MainActivity.TAG, "List of Tasks received by TasksFragment: " + this.mTasks.toString());
        }

        // Set LayoutManager, new Task Adapter, and set the adapter
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TasksAdapter();
        binding.recyclerView.setAdapter(adapter);

        // onClick - Add New Task
        binding.buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddTask();
            }
        });

        // onClick - Clear All
        binding.buttonClearAll.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                mListener.clearAllTask();
                adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "List Cleared!", Toast.LENGTH_SHORT).show();
            }
        });

        // onClick for Sorting
        binding.imageViewSortAsc.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                sortList(1);
                binding.textViewSortIndicator.setText("Sort By Priority (ASC)");
            }
        });

        binding.imageViewSortDesc.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                sortList(-1);
                binding.textViewSortIndicator.setText("Sort By Priority (DESC)");
            }
        });
    }


    // Method for sorting (orderType --> 1 ASC/ -1 DESC)
    @SuppressLint("NotifyDataSetChanged")
    private void sortList(int orderType) {

        if (mTasks.size() <= 1) {
            Toast.makeText(getActivity(), "Not enough items to sort!", Toast.LENGTH_SHORT).show();
        } else {
            Collections.sort(mTasks, new Comparator<Task>() {
                @Override
                public int compare(Task task1, Task task2) {
                    return (int) (orderType * (task1.getPriority() - task2.getPriority()));
                }
            });
            adapter.notifyDataSetChanged();
        }
    }



    // Set up Interface
    TasksFragmentInterface mListener; // Local declaration of interface to be able to call its methods here (which are implemented and overwritten in MainActivity)

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (TasksFragmentInterface) context; // To call the methods and use the logic overwritten by MainActivity, you must typeCast the context (which is MainActivity) to this interface, indicating that mListener will execute the overrides from the context
    }

    public interface TasksFragmentInterface { // Anyone could implement this interface. But in order for this fragment to be able to communicate with Main Activity and use the logic written in @Overrides here, we need a local instance of this interface.
        ArrayList<Task> getAllTasks();
        void gotoAddTask();
        void gotoTaskDetails(Task task);
        void clearAllTask();

        void deleteItem(Task task);
    }



    /* inner class within TasksFragment. It extends RecyclerView.Adapter,
    which is a generic class used to adapt data to be displayed in a RecyclerView */
    // Tasks RecyclerView Adapter
    class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {

        /* This method is called by the RecyclerView when it needs to create a new ViewHolder instance to represent an item in the list.
           It inflates a layout (R.layout.task_list_item) to create a View object that represents the item.
           It then creates a new TasksViewHolder instance with this inflated View and returns it */
        @NonNull
        @Override
        public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item, parent, false);
            return new TasksViewHolder(view);
        }

        /* This method is called by the RecyclerView to display data at a specific position in the list.
           It receives a ViewHolder object (holder) and a position in the dataset (position) as parameters.
           It retrieves the Task object associated with the given position from the mTasks ArrayList.
           It calls the setupUI() method of the ViewHolder to bind the data from the Task object to the UI elements within the ViewHolder. */
        @Override
        public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {

            // Get the task at the current position in the list
            Task task = mTasks.get(holder.getAdapterPosition());
            // Bind the task data to the UI elements
            holder.setupUI(task);

            // onClick on item in Recycler View
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(MainActivity.TAG, "onClick: Position " + holder.getAdapterPosition() + " - " + task.getName());
                    mListener.gotoTaskDetails(task);
                }
            });

            // onClick - Delete list item
            holder.imageViewDeleteItem.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View v) {
                    mListener.deleteItem(task);
                    notifyDataSetChanged();
                    Toast.makeText(getActivity(), task.getName() + " Deleted!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }

        /* This is another inner class within your TasksAdapter.
        It extends RecyclerView.ViewHolder, which represents each item in the RecyclerView. */
        // Tasks View Holder
        class TasksViewHolder extends RecyclerView.ViewHolder {

            // Declaration
            TextView textViewName, textViewCategory, textViewPriorityStr;
            ImageView imageViewDeleteItem;

            // This constructor initializes the ViewHolder with the given View object.
            public TasksViewHolder(@NonNull View itemView) {
                super(itemView);

                // Initialization
                textViewName = itemView.findViewById(R.id.textViewName);
                textViewCategory = itemView.findViewById(R.id.textViewCategory);
                textViewPriorityStr = itemView.findViewById(R.id.textViewPriority);
                imageViewDeleteItem = itemView.findViewById(R.id.imageViewDeleteItem);
            }

            // Method to set up UI
            public void setupUI(Task task) {
                textViewName.setText(task.getName());
                textViewCategory.setText(task.getCategory());
                textViewPriorityStr.setText(task.getPriorityStr());
            }
        }
    }
}

