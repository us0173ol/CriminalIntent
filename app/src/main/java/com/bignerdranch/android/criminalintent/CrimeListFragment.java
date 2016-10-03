package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by miked on 10/1/2016.
 */

public class CrimeListFragment extends Fragment {
    //reference variables for recyclerview and Adapter
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    @Override//display the list when the view is created
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        //assign the recyclerview to the variable for it by id
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        //layout manager handles positioning of items and defines scrolling behavior
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //call this method to update the user interface
        updateUI();

        return view;
    }
    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }//when called the list is updated
    private void updateUI(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        //make sure adapter is present to manage viewholders
        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }else {//if present, notify that data has changed to update
            mAdapter.notifyDataSetChanged();
        }
    }//inner class
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Crime mCrime;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;


        public CrimeHolder(View itemView) {
            super(itemView);
            //listener for clicking on each items textview and checkbox
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView)
                    itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView)
                    itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox)
                    itemView.findViewById(R.id.list_item_crime_solved_check_box);
        }
        @Override//launch crimeactivity on click of crime items
        public void onClick(View v){
            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
        }

        public void bindCrime(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

    }
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes){
            mCrimes = crimes;
        }
        @Override//inflate the view from layout list_item_crime
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent,false);
            return new CrimeHolder(view);
        }
        @Override//bind viewholder view to model object
        public void onBindViewHolder(CrimeHolder holder, int position){
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);
        }
        @Override//get number of items in crime list
        public int getItemCount(){
            return mCrimes.size();
        }
    }
}
