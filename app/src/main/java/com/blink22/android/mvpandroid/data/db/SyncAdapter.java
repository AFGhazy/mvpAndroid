package com.blink22.android.mvpandroid.data.db;

/**
 * Created by ahmedghazy on 7/31/18.
 */

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.blink22.android.mvpandroid.BaseApp;
import com.blink22.android.mvpandroid.data.DataManager;
import com.blink22.android.mvpandroid.data.db.model.Todo;
import com.blink22.android.mvpandroid.di.PerSyncService;
import com.blink22.android.mvpandroid.di.component.DaggerSyncAdapterComponent;
import com.blink22.android.mvpandroid.di.component.SyncAdapterComponent;
import com.blink22.android.mvpandroid.di.module.SyncAdapterModule;
import com.blink22.android.mvpandroid.ui.todos.TodosContract;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * This is used by the Android framework to perform synchronization. IMPORTANT: do NOT create
 * new Threads to perform logic, Android will do this for you; hence, the name.
 *
 * The goal here to perform synchronization, is to do it efficiently as possible. We use some
 * ContentProvider features to batch our writes to the local data source. Be sure to handle all
 * possible exceptions accordingly; random crashes is not a good user-experience.
 */

@PerSyncService
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    @Inject
    public DataManager mDataManager;

    private static final String TAG = "SYNC_ADAPTER";

    /**
     * This gives us access to our local data source.
     */
    private final ContentResolver resolver;


    public SyncAdapter(Context c, boolean autoInit) {
        this(c, autoInit, false);
    }

    public SyncAdapter(Context c, boolean autoInit, boolean parallelSync) {
        super(c, autoInit, parallelSync);
        this.resolver = c.getContentResolver();

        SyncAdapterComponent component = DaggerSyncAdapterComponent.builder()
                .applicationComponent(((BaseApp)c.getApplicationContext()).getComponent())
                .syncAdapterModule(new SyncAdapterModule(this))
                .build();

        component.inject(this);
    }

    /**
     * This method is run by the Android framework, on a new Thread, to perform a sync.
     * @param account Current account
     * @param extras Bundle extras
     * @param authority Content authority
     * @param provider {@link ContentProviderClient}
     * @param syncResult Object to write stats to
     */
    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.w(TAG, "Starting synchronization...");

        try {
            // Synchronize our news feed
            syncNewsFeed(syncResult);

            // Add any other things you may want to sync

        } catch (IOException ex) {
            Log.e(TAG, "Error synchronizing!", ex);
            syncResult.stats.numIoExceptions++;
        } catch (JSONException ex) {
            Log.e(TAG, "Error synchronizing!", ex);
            syncResult.stats.numParseExceptions++;
        } catch (RemoteException |OperationApplicationException ex) {
            Log.e(TAG, "Error synchronizing!", ex);
            syncResult.stats.numAuthExceptions++;
        }

        Log.w(TAG, "Finished synchronization!");
    }

    /**
     * Performs synchronization of our pretend news feed source.
     * @param syncResult Write our stats to this
     */
    private void syncNewsFeed(final SyncResult syncResult) throws IOException, JSONException, RemoteException, OperationApplicationException {
        final String rssFeedEndpoint = "http://www.examplejsonnews.com";

        // We need to collect all the network items in a hash table
        Log.i(TAG, "Fetching server entries...");


        mDataManager.getTodos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Todo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<Todo> todos) {
                        try {
                            onDataFeched(todos, syncResult);
                        }
                        catch (Exception e) {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    void onDataFeched(ArrayList<Todo> todosList, SyncResult syncResult)  throws OperationApplicationException, RemoteException{

        Map<Integer, Todo> todos = new HashMap<>();

        for (Todo todo: todosList) {
            todos.put( todo.getId(), todo);
        }

        // Create list for batching ContentProvider transactions
        ArrayList<ContentProviderOperation> batch = new ArrayList<>();

        // Compare the hash table of network entries to all the local entries
        Log.i(TAG, "Fetching local entries...");
        Cursor c = resolver.query(DatabaseContract.CONTENT_URI, null, null, null, null, null);



        assert c != null;
        c.moveToFirst();

        int id;
        String title;
        String description;
        boolean done;
        Todo found;
        Log.i(getClass().getSimpleName(), "found" + c.getCount());
        Log.i(getClass().getSimpleName(), "found" + c.getCount());
        Log.i(getClass().getSimpleName(), "found" + c.getCount());
        for (int i = 0; i < c.getCount(); i++) {
            Log.i(getClass().getSimpleName(), "hereeeeeeeee" + i);

            syncResult.stats.numEntries++;

            Log.i(getClass().getSimpleName(), "numEnt" + i);

            // Create local article entry
            id = c.getInt(c.getColumnIndex(DatabaseContract.TodoColumns.ID));
            title = c.getString(c.getColumnIndex(DatabaseContract.TodoColumns.TITLE));
            description = c.getString(c.getColumnIndex(DatabaseContract.TodoColumns.DESCRIPTION));
            done = c.getInt(c.getColumnIndex(DatabaseContract.TodoColumns.DONE)) != 0;

            // Try to retrieve the local entry from network entries
            found = todos.get(id);
            if (found != null) {
                Log.i(getClass().getSimpleName(), "found");
                // The entry exists, remove from hash table to prevent re-inserting it
                todos.remove(id);

                // Check to see if it needs to be updated
                if (
                        !title.equals(found.getTitle()) ||
                                !description.equals(found.getDescription()) ||
                                done != found.isDone()) {
                    // Batch an update for the existing record
                    Log.i(TAG, "Scheduling update: " + title);
                    batch.add(ContentProviderOperation.newUpdate(DatabaseContract.getElementUri(id))
                            .withSelection(DatabaseContract.TodoColumns.ID + "='" + id + "'", new String[]{Integer.toString(id)})
                            .withValue(DatabaseContract.TodoColumns.TITLE, found.getTitle())
                            .withValue(DatabaseContract.TodoColumns.DESCRIPTION, found.getDescription())
                            .withValue(DatabaseContract.TodoColumns.DONE, found.isDone())
                            .build());
                    syncResult.stats.numUpdates++;
                }
            } else {
                // Entry doesn't exist, remove it from the local database
                Log.i(TAG, "Scheduling delete: " + title);
                batch.add(ContentProviderOperation.newDelete(DatabaseContract.getElementUri(id))
                        .withSelection(DatabaseContract.TodoColumns.ID + "='" + id + "'", new String[]{Integer.toString(id)})
                        .build());
                syncResult.stats.numDeletes++;
            }
            c.moveToNext();
        }
        c.close();

        // Add all the new entries
        for (Todo todo : todos.values()) {
            Log.i(TAG, "Scheduling insert: " + todo.getTitle());
            batch.add(ContentProviderOperation.newInsert(DatabaseContract.CONTENT_URI)
                    .withValue(DatabaseContract.TodoColumns.TITLE, todo.getTitle())
                    .withValue(DatabaseContract.TodoColumns.DESCRIPTION, todo.getDescription())
                    .withValue(DatabaseContract.TodoColumns.DONE, todo.isDone())
                    .build());
            syncResult.stats.numInserts++;
        }

        // Synchronize by performing batch update
        Log.i(TAG, "Merge solution ready, applying batch update...");
        resolver.applyBatch(DatabaseContract.CONTENT_AUTHORITY, batch);
        resolver.notifyChange(DatabaseContract.CONTENT_URI, // URI where data was modified
                null, // No local observer
                false); // IMPORTANT: Do not sync to network
    }

    /**
     * Manual force Android to perform a sync with our SyncAdapter.
     */
    public static void performSync() {
        Bundle b = new Bundle();
        b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(AccountGeneral.getAccount(),
                DatabaseContract.CONTENT_AUTHORITY, b);
    }
}