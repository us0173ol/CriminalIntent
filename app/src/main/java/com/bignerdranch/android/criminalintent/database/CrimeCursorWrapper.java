package com.bignerdranch.android.criminalintent.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.criminalintent.Crime;

import java.util.Date;
import java.util.UUID;

import static com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeTable.Cols.DATE;
import static com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeTable.Cols.TITLE;
import static com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeTable.Cols.UUID;

/**
 * Created by miked on 10/15/2016.
 */

public class CrimeCursorWrapper extends CursorWrapper {
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    //read from to gather info on crime
    public Crime getCrime(){
        String uuidString = getString(getColumnIndex(UUID));
        String title = getString(getColumnIndex(TITLE));
        long date = getLong(getColumnIndex(DATE));
        int isSolved = getInt(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.SOLVED));
        String suspect = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.SUSPECT));
        //set info
        Crime crime = new Crime(java.util.UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);
        crime.setSuspect(suspect);

        return crime;
    }
}
