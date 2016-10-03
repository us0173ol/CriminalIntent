package com.bignerdranch.android.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by miked on 10/1/2016.
 */

public class CrimeLab {
    //static variable for CrimeLab Object
    private static CrimeLab sCrimeLab;
    //List of crimes
    private List<Crime> mCrimes;

    public static CrimeLab get(Context context){
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }//CrimeLabs constructor with 100 items
    private CrimeLab(Context context){
        mCrimes = new ArrayList<>();
        for(int i = 0; i<100; i++){
            Crime crime = new Crime();//for each crime # create a new Crime object to store in the list
            crime.setTitle("Crime #" + i);//setting the title and number of each object
            crime.setSolved(i%2 == 0);//Every Other is set to solved
            mCrimes.add(crime);//add each to the Arraylist
        }
    }//retunrs the list of Crimes
    public List<Crime> getCrimes(){
        return mCrimes;
    }
    //returns the Crime and ID#
    public Crime getCrime(UUID id){
        for(Crime crime : mCrimes){
            if(crime.getId().equals(id)){
                return crime;
            }
        }
        return null;
    }
}
