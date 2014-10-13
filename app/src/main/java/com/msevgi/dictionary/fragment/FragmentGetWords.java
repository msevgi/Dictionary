
package com.msevgi.dictionary.fragment;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.msevgi.dictionary.R;
import com.msevgi.dictionary.adapter.WordsAdapter;
import com.msevgi.dictionary.helper.MySQLiteOpenHelper;
import com.msevgi.dictionary.model.Words;
import com.msevgi.dictionary.provider.BusProvider;
import com.msevgi.dictionary.provider.MyContentProvider;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.InjectView;

public class FragmentGetWords extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {
   @InjectView(R.id.listview_words)
   public ListView      mListView;
   private WordsAdapter mAdapter;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      // LoaderManager manages loaders associated with an Activity or a Fragment.
      // CursorLoader ensures queries are performed asynchronously.
      getLoaderManager().initLoader(0, null, this); // Third parameter is reference to callbacks
      setHasOptionsMenu(true);
      // BusProvider.getInstance().register(this);
      mAdapter = new WordsAdapter(getActivity(), R.layout.word_item, new ArrayList<Words>());
      mAdapter.setNotifyOnChange(true);
   }

   @Override
   public void onViewCreated(View view, @Nullable
   Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      mListView.setAdapter(mAdapter);
   }

   @Override
   public void onResume() {
      super.onResume();
      BusProvider.getInstance().register(this);
      getLoaderManager().restartLoader(0, null, this); // Third parameter is reference to callbacks
   }

   @Override
   public void onPause() {
      super.onPause();
      BusProvider.getInstance().unregister(this);
   }

   @NonNull
   @Override
   protected int getLayoutResource() {
      return R.layout.fragment_get_words;
   }

   @Subscribe
   public void onListViewItemLongClick(final Words item) {
      new AlertDialog.Builder(getActivity()).setTitle(R.string.warning)
                                            .setMessage(R.string.delete_word)
                                            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                               @Override
                                               public void onClick(DialogInterface dialog, int which) {
                                                  dialog.dismiss();
                                               }
                                            })
                                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                               @Override
                                               public void onClick(DialogInterface dialog, int which) {
                                                  mAdapter.remove(item);
                                                  ContentResolver cr = getActivity().getContentResolver();
                                                  cr.delete(MyContentProvider.CONTENT_URI,
                                                            "_id = ?",
                                                            new String[]{String.valueOf(item.getId())});
                                                  dialog.dismiss();
                                               }
                                            })
                                            .show();
   }

   @Override
   public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
      return new CursorLoader(getActivity(), MyContentProvider.CONTENT_URI, null, null, null, null);
   }

   @Override
   public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
      mAdapter.clear();
      // Gets index of the column given a name.
      final int mTurkishColoumn = cursor.getColumnIndexOrThrow(MySQLiteOpenHelper.TURKISH_WORD_COLUMN);
      final int mEnglishColoumn = cursor.getColumnIndexOrThrow(MySQLiteOpenHelper.ENGLISH_WORD_COLUMN);
      final int mID = cursor.getColumnIndexOrThrow(MySQLiteOpenHelper.ID_COLUMN);

      // Database queries are returned as Cursor objects.
      // Cursors are pointers to the result set within the underlying data.
      // Here is how to iterate over the cursor rows.
      while (cursor.moveToNext()) { // Moves cursor to next row, cursor is initialized at before first.
         final String mTurkishWord = cursor.getString(mTurkishColoumn); // Extract column data from cursor.
         final String mEnglishWord = cursor.getString(mEnglishColoumn); // Extract column data from cursor.
         final int id = cursor.getInt(mID);
         mAdapter.add(new Words(id, mTurkishWord, mEnglishWord));
      }
   }

   @Override
   public void onLoaderReset(Loader<Cursor> cursorLoader) {
      mAdapter.clear();
   }
}
