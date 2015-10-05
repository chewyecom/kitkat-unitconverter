package com.chewy.unitconverter;

import java.sql.SQLException;

import com.chewy.unitconverter.ConverterHistory.ConverterEntry;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * A helper class to manage database creation and version management.
 * @author gndev
 *
 */
public class HistoryReaderDBHelper extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "HistoryReader.db";

	public HistoryReaderDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
    
	public HistoryReaderDBHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	public HistoryReaderDBHelper(Context context, String name,
			CursorFactory factory, int version,
			DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.beginTransaction();
		try {
			db.execSQL(ConverterEntry.SQL_CREATE_ENTRIES);
		} finally {
			db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// discard data and restart
		db.beginTransaction();
		try {
			db.execSQL(ConverterEntry.SQL_DELETE_ENTRIES);
			onCreate(db);
		}finally {
			db.endTransaction();
		}
	}
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	db.beginTransaction();
    	try {
    		onUpgrade(db, oldVersion, newVersion);
    	} finally {
    		db.endTransaction();
    	}
    }

}
