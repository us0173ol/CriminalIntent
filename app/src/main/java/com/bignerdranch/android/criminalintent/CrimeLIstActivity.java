package com.bignerdranch.android.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by miked on 10/1/2016.
 */

public class CrimeListActivity extends SingleFragmentActivity {

    @Override//method to create a new CrimeListFragment
    protected Fragment createFragment(){
        return new CrimeListFragment();
    }
}
