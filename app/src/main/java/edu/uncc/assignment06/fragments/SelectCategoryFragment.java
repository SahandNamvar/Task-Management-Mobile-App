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
import edu.uncc.assignment06.databinding.FragmentSelectCategoryBinding;
import edu.uncc.assignment06.models.Data;

public class SelectCategoryFragment extends Fragment {

    FragmentSelectCategoryBinding binding;

    String[] mCategories;

    CategoryAdapter adapter;

    public SelectCategoryFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCategories = Data.categories;

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CategoryAdapter();
        binding.recyclerView.setAdapter(adapter);

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelCategory();
            }
        });
    }



    // Interface
    SelectCategoryFragmentInterface mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectCategoryFragmentInterface) context;
    }

    public interface SelectCategoryFragmentInterface {
        void sendCategory(String category);
        void cancelCategory();
    }




    // Set up Category Adapter
    class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

        @NonNull
        @Override // For each element in the List, inflate (being XML to life)
        public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selection_list_item, parent, false);
            return new CategoryViewHolder(view);
        }

        @Override // Then for that element grab the string value and set the textView from the ViewHolder.
        public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

            String mCategory = mCategories[position];
            holder.textViewCategory.setText(mCategory);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.sendCategory(mCategory);
                    Log.d(MainActivity.TAG, "Selected Category: " + mCategory);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mCategories.length;
        }

        // Set up ViewHolder
        class CategoryViewHolder extends RecyclerView.ViewHolder {

            TextView textViewCategory;

            public CategoryViewHolder(@NonNull View itemView) {
                super(itemView);

                textViewCategory = itemView.findViewById(R.id.textViewItem);
            }
        }
    }
}