package edu.uncc.assignment06.fragments;

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
import android.widget.TextView;

import edu.uncc.assignment06.MainActivity;
import edu.uncc.assignment06.R;
import edu.uncc.assignment06.databinding.FragmentSelectPriorityBinding;
import edu.uncc.assignment06.models.Data;

public class SelectPriorityFragment extends Fragment {

    private FragmentSelectPriorityBinding binding;

    private String[] mPriorities; // To store priorities from Data class

    private PriorityAdapter adapter; // To render the Recycler View

    public SelectPriorityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectPriorityBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get priorities list from Data class
        mPriorities = Data.priorities;

        // Set Layout Manager and Adapter
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PriorityAdapter();
        binding.recyclerView.setAdapter(adapter);

        // onClick button Cancel
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelPriority();
            }
        });
    }


    // Interface
    SelectPriorityFragmentInterface mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectPriorityFragmentInterface) context;
    }

    public interface SelectPriorityFragmentInterface {
        void sendPriority(String priority);
        void cancelPriority();
    }

    // Recycler View Priority Adapter
    class PriorityAdapter extends RecyclerView.Adapter<PriorityAdapter.PriorityViewHolder> {

        @NonNull
        @Override
        public PriorityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selection_list_item, parent, false);
            return new PriorityViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PriorityViewHolder holder, int position) {

            String mPriorityStr = mPriorities[position];
            holder.textViewPriorityStr.setText(mPriorityStr);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.sendPriority(mPriorityStr);
                    Log.d(MainActivity.TAG, "Selected Priority: " + mPriorityStr);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mPriorities.length;
        }

        class PriorityViewHolder extends RecyclerView.ViewHolder {

            TextView textViewPriorityStr;

            public PriorityViewHolder(@NonNull View itemView) {
                super(itemView);

                textViewPriorityStr = itemView.findViewById(R.id.textViewItem);
            }
        }
    }
}