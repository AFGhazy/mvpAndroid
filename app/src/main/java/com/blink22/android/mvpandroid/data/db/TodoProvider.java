package com.blink22.android.mvpandroid.data.db;

/**
 * Created by ahmedghazy on 7/31/18.
 */

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmResults;
import io.realm.RealmSchema;

import com.blink22.android.mvpandroid.data.db.DatabaseContract.TodoColumns;
import com.blink22.android.mvpandroid.data.db.model.Todo;


public class TodoProvider extends ContentProvider {
    private static final String TAG = TodoProvider.class.getSimpleName();

    private static final int CLEANUP_JOB_ID = 43;
    private static final int TODOS = 100;
    private static final int TODO_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        // content://com.example.rgher.realmtodo/tasks
        sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY,
                DatabaseContract.TABLE_TODOS,
                TODOS);

        // content://com.example.rgher.realmtodo/tasks/id
        sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY,
                DatabaseContract.TABLE_TODOS + "/#",
                TODO_WITH_ID);
    }


    @Override
    public boolean onCreate() {

        //Innitializing RealmDB
        Realm.init(getContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration(new MyRealmMigration())
                .build();

        Realm.setDefaultConfiguration(config);

//        manageCleanupJob();

        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null; /* Not used */
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        int match = sUriMatcher.match(uri);

        //Get Realm Instance
        Realm realm = Realm.getDefaultInstance();
        MatrixCursor myCursor = new MatrixCursor( new String[]{
                TodoColumns.ID,
                TodoColumns.TITLE,
                TodoColumns.DESCRIPTION,
                TodoColumns.DONE
        });

        try {
            switch (match) {
                //Expected "query all" Uri: content://com.example.rgher.realmtodo/tasks

                case TODOS:
                    RealmResults<Todo> todoRealmResults = realm.where(Todo.class).findAll();
                    for (Todo todo : todoRealmResults) {
                        Log.i(getClass().getSimpleName(), "tdo: " + todo.getTitle());
                        Object[] rowData = new Object[]{todo.getId(), todo.getTitle(), todo.getDescription(), todo.isDone() ? 1 : 0};

                        Log.i(getClass().getSimpleName(), "^^^" + todo.isDone());

                        myCursor.addRow(rowData);
                        Log.v("RealmDB", todo.toString());
                    }
                    break;

                //Expected "query one" Uri: content://com.example.rgher.realmtodo/tasks/{id}
                case TODO_WITH_ID:
                    Integer id = Integer.parseInt(uri.getPathSegments().get(1));

                    Todo todo = realm.where(Todo.class).equalTo(TodoColumns.ID, id).findFirst();
                    myCursor.addRow(new Object[]{todo.getId(), todo.getTitle(), todo.getDescription(), todo.isDone()});
                    Log.v("RealmDB", todo.toString());
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }


            myCursor.setNotificationUri(getContext().getContentResolver(), uri);
        } finally {
            realm.close();
        }



        return myCursor;

    }

    @Nullable
    @Override
    public Uri insert(Uri uri, final ContentValues contentValues) {
        //COMPLETE: Expected Uri: content://com.example.rgher.realmtodo/tasks

        //final SQLiteDatabase taskDb = mDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;

        //Get Realm Instance
        Realm realm = Realm.getDefaultInstance();
        try {
            switch (match) {
                case TODOS:
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {

                            Number currId = realm.where(Todo.class).max(TodoColumns.ID);

                            Integer nextId = (currId == null) ? 1 : currId.intValue() + 1;

                            Todo todo = realm.createObject(Todo.class, nextId);
                            todo.setTitle(contentValues.getAsString(TodoColumns.TITLE));
                            todo.setDescription(contentValues.getAsString(TodoColumns.DESCRIPTION));
                            todo.setDone(contentValues.getAsBoolean(TodoColumns.DONE));
                        }
                    });
                    returnUri = ContentUris.withAppendedId(DatabaseContract.CONTENT_URI, '1');
                    break;

                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }

            getContext().getContentResolver().notifyChange(uri, null);
        }finally {
            realm.close();
        }
        return returnUri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        //Expected Uri: content://com.example.rgher.realmtodo/tasks/{id}
        Realm realm = Realm.getDefaultInstance();

        int match = sUriMatcher.match(uri);
        int nrUpdated = 0;
        try {
            switch (match) {
                case TODO_WITH_ID:
                    Integer id = Integer.parseInt(uri.getPathSegments().get(1));

                    Todo todo = realm.where(Todo.class).equalTo(TodoColumns.ID, id).findFirst();
                    realm.beginTransaction();
                    todo.setDone(values.getAsBoolean(TodoColumns.DONE));
                    nrUpdated++;
                    realm.commitTransaction();
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
            }


        } finally {
            realm.close();
        }

        if (nrUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return nrUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        Realm realm = Realm.getDefaultInstance();
        try {
            switch (sUriMatcher.match(uri)) {
                case TODO_WITH_ID:
                    Integer id = Integer.parseInt(uri.getPathSegments().get(1));

                    Todo myTask = realm.where(Todo.class).equalTo(TodoColumns.ID, id).findFirst();
                    realm.beginTransaction();
                    if(myTask != null) {
                        myTask.deleteFromRealm();
                        count++;
                    }
                    realm.commitTransaction();
                    break;
                default:
                    throw new IllegalArgumentException("Illegal delete URI");
            }
        } finally {
            realm.close();
        }
        if (count > 0) {
            //Notify observers of the change
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return count;
    }

//    /* Initiate a periodic job to clear out completed items */
//    private void manageCleanupJob() {
//        Log.d(TAG, "Scheduling cleanup job");
//        JobScheduler jobScheduler = (JobScheduler) getContext()
//                .getSystemService(Context.JOB_SCHEDULER_SERVICE);
//
//        //Run the job approximately every hour
//        long jobInterval = DateUtils.MINUTE_IN_MILLIS;
//
//        ComponentName jobService = new ComponentName(getContext(), CleanupJobService.class);
//
//        JobInfo task = new JobInfo.Builder(CLEANUP_JOB_ID, jobService)
//                .setPeriodic(jobInterval)
//                .setPersisted(true)
//                .build();
//
//        if (jobScheduler.schedule(task) != JobScheduler.RESULT_SUCCESS) {
//            Log.w(TAG, "Unable to schedule cleanup job");
//        }
//    }
}

// Example of REALM migration
class MyRealmMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        RealmSchema schema = realm.getSchema();

        if (oldVersion != 0) {
            schema.create(DatabaseContract.TABLE_TODOS)
                    .addField(DatabaseContract.TodoColumns.ID, Integer.class)
                    .addField(TodoColumns.TITLE, String.class)
                    .addField(TodoColumns.DESCRIPTION, String.class)
                    .addField(TodoColumns.DONE, Boolean.class);
            oldVersion++;
        }

    }
}