package com.chewy.unitconverter;

import java.util.Calendar;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.view.View;

public class ConverterHistory {

	public ConverterHistory() {
	}

	public static abstract class ConverterEntry implements BaseColumns {
		public static final String TABLE_ENTRY = "entry";
		public static final String COLUMN_ENTRY_ID = "entryid";  //use timestamp mmddyyhhmmss as entryid
		public static final String COLUMN_UNIT_FROM = "unitfrom";
		public static final String COLUMN_VAL_FROM = "valfrom";
		public static final String COLUMN_UNIT_TO = "unitto";
		public static final String COLUMN_VAL_TO = "valto";
		
		public static final String COMMA_SEP = ",";
		public static final String TEXT_TYPE = " TEXT ";
		public static final String COLUMN_NAME_NULLABLE = " NULLABLE ";
		
		public static final String SQL_CREATE_ENTRIES =
			new StringBuffer()
			.append("CREATE TABLE ").append(TABLE_ENTRY).append(" ( ") 
			.append(ConverterEntry.COLUMN_ENTRY_ID)
			.append(" INTEGER PRIMARY KEY").append(COMMA_SEP)
			.append(ConverterEntry.COLUMN_UNIT_FROM).append(TEXT_TYPE).append(COMMA_SEP)
			.append(ConverterEntry.COLUMN_UNIT_TO).append(TEXT_TYPE).append(COMMA_SEP)
			.append(ConverterEntry.COLUMN_VAL_FROM).append(TEXT_TYPE).append(COMMA_SEP)
			.append(ConverterEntry.COLUMN_VAL_TO).append(TEXT_TYPE)
			.append(" ) ")
			.toString();
		
		public static final String SQL_DELETE_ENTRIES =
			"DROP TABLE IF EXISTS " + ConverterEntry.TABLE_ENTRY;
		
	}
	
	public void addInformationIntoDB (
			View v, String unitFrom, String unitTo, String valFrom, String valTo) {
		
		HistoryReaderDBHelper mDbHelper = new HistoryReaderDBHelper(v.getContext());
		// Gets the data repository in write mode
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		// for debug in realtime
		String dbpath = (v.getContext().getDatabasePath(HistoryReaderDBHelper.DATABASE_NAME)).getAbsolutePath();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		
		Calendar now = Calendar.getInstance();
		String id = new StringBuffer()
		.append( String.valueOf( now.get(Calendar.YEAR) ) )
		.append( String.valueOf( now.get(Calendar.MONTH) ) )
		.append( String.valueOf( now.get(Calendar.DATE) ) )
		.append( String.valueOf( now.get(Calendar.HOUR_OF_DAY) ) )
		.append( String.valueOf( now.get(Calendar.MINUTE) ) )
		.append( String.valueOf( now.get(Calendar.SECOND) ) )
		.toString() ;
		
		values.put(ConverterHistory.ConverterEntry.COLUMN_ENTRY_ID, id);
		values.put(ConverterHistory.ConverterEntry.COLUMN_UNIT_FROM, unitFrom);
		values.put(ConverterHistory.ConverterEntry.COLUMN_UNIT_TO, unitTo);
		values.put(ConverterHistory.ConverterEntry.COLUMN_VAL_FROM, valFrom);
		values.put(ConverterHistory.ConverterEntry.COLUMN_VAL_TO, valTo);

		// Insert the new row, returning the primary key value of the new row
		long newRowId;
		newRowId = db.insert(
				ConverterHistory.ConverterEntry.TABLE_ENTRY,
				ConverterHistory.ConverterEntry.COLUMN_NAME_NULLABLE,
		         values);
		db.close();
	}
	
	public int getNumberOfRecordsFromDB(View v) {
		HistoryReaderDBHelper mDbHelper = new HistoryReaderDBHelper(v.getContext());
		// Gets the data repository in read mode
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		int records=0;

		String countQuery = new StringBuffer()
			.append("SELECT entryid FROM ")
			.append(ConverterEntry.TABLE_ENTRY)
			.toString() ;
		Cursor cursor = db.rawQuery(countQuery, null);
		records = cursor.getCount();
		cursor.close();
		db.close();
		return records;
	}
	public int removeAllRecordsFromDB(View v) {
		HistoryReaderDBHelper mDbHelper = new HistoryReaderDBHelper(v.getContext());
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		int records=0;
		
		// delete all records from the entry table
		records = db.delete(ConverterEntry.TABLE_ENTRY, null, null);
		db.close();
		return records;
	}
}
