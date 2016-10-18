package com.bignerdranch.android.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.bignerdranch.android.criminalintent.database.CrimeBaseHelper;
import com.bignerdranch.android.criminalintent.database.CrimeCursorWrapper;
import com.bignerdranch.android.criminalintent.database.CrimeDbSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeTable.Cols.DATE;
import static com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeTable.Cols.SOLVED;
import static com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeTable.Cols.SUSPECT;
import static com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeTable.Cols.TITLE;
import static com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeTable.Cols.UUID;

/**
 * Created by miked on 10/1/2016.
 */

public class CrimeLab {
    //static variable for CrimeLab Object
    private static CrimeLab sCrimeLab;
    //List of crimes
//    private List<Crime> mCrimes;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context){
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }//CrimeLabs constructor
    private CrimeLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
//        mCrimes = new ArrayList<>();

    }
    //add a new crime
    public void addCrime(Crime c){
//        mCrimes.add(c);
        ContentValues values = getContentValues(c);

        mDatabase.insert(CrimeDbSchema.CrimeTable.NAME, null, values);
    }
    //returns the list of Crimes
    public List<Crime> getCrimes(){
//        return mCrimes;
        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWrapper cursor = queryCrimes(null, null);

        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return crimes;
    }
    //returns the Crime and ID#
    public Crime getCrime(UUID id){

        CrimeCursorWrapper cursor = queryCrimes(
                CrimeDbSchema.CrimeTable.Cols.UUID + "= ?",
                new String[]{ id.toString() }
        );
        try{
            if(cursor.getCount()==0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        }finally {
            cursor.close();
        }
    }

    public File getPhotofile(Crime crime){
        File externalFilesDir = mContext
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if(externalFilesDir == null){
            return null;
        }
        return new File(externalFilesDir, crime.getPhotoFilename());
    }

    public void updateCrime(Crime crime){
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);

        mDatabase.update(CrimeDbSchema.CrimeTable.NAME, values,
                CrimeDbSchema.CrimeTable.Cols.UUID + " = ?",
                new String[]{ uuidString });
    }//columns for database
    private static ContentValues getContentValues(Crime crime){
        ContentValues values = new ContentValues();
        values.put(UUID, crime.getId().toString());
        values.put(TITLE, crime.getTitle());
        values.put(DATE, crime.getDate().getTime());
        values.put(SOLVED, crime.isSolved() ? 1 : 0);
        values.put(SUSPECT, crime.getSuspect());

        return values;
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                CrimeDbSchema.CrimeTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null,//groupBy
                null,//having
                null //orderBy
        );
        return new CrimeCursorWrapper(cursor);
    }
}
