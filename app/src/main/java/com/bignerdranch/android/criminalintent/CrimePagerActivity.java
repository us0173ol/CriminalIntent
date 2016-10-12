package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.UUID;

/**
 * Created by miked on 10/11/2016.
 */

public class CrimePagerActivity extends FragmentActivity {
    //EXTRA for crime ID
    private static final String EXTRA_CRIME_ID =
            "com.bignerdranch.android.criminalintent.crime_id";
    //reference variables for ViewPager and Crime array
    private ViewPager mViewPager;
    private List<Crime> mCrimes;
    //new intent to access crime ID from crime array
    public static Intent newIntent(Context packageContext, UUID crimeId){
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);//View pager layout file
        //when the activity is created, use EXTRA to get crime Array
        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_CRIME_ID);
        //find the view pager in the activities view(by resource id)
        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);
        //get the array of crimes from crime lab
        mCrimes = CrimeLab.get(this).getCrimes();
        //get the fragment manager (required for the adapter)
        FragmentManager fragmentManager = getSupportFragmentManager();
        //set the adapter to manage data with the viewPager
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override//find the Crime instance for the given position in crime array
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                //display that crime instance in view pager
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override//number of crimes in the crime array
            public int getCount() {
                return mCrimes.size();
            }
        });
        //sets current index to index of that crime
        for (int i = 0; i< mCrimes.size(); i++){
            if(mCrimes.get(i).getId().equals(crimeId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
