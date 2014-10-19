
package com.msevgi.dictionary.fragment;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.msevgi.dictionary.provider.MyContentProvider;
import com.msevgi.dictionary.helper.MySQLiteOpenHelper;
import com.msevgi.dictionary.R;

import butterknife.InjectView;

public class FragmentSaveWords extends BaseFragment implements View.OnClickListener {
   @InjectView(R.id.edittext_english)
   public EditText mEditTextEnglish;
   @InjectView(R.id.edittext_turkish)
   public EditText mEditTextTurkish;
   @InjectView(R.id.button_save_word)
   public RippleView mButtonSave;

   @Override
   public void onViewCreated(View view, @Nullable
   Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      mButtonSave.setOnClickListener(this);
   }

   @NonNull
   @Override
   protected int getLayoutResource() {
      return R.layout.fragment_save_word;
   }

   @Override
   public void onClick(View v) {
      String mTurkishWord = mEditTextTurkish.getText().toString().trim();
      String mEnglishWord = mEditTextEnglish.getText().toString().trim();
      if (!TextUtils.isEmpty(mTurkishWord) && !TextUtils.isEmpty(mEnglishWord)) {
         ContentValues contentValues = new ContentValues();
         contentValues.put(MySQLiteOpenHelper.TURKISH_WORD_COLUMN, mTurkishWord);
         contentValues.put(MySQLiteOpenHelper.ENGLISH_WORD_COLUMN, mEnglishWord);
         ContentResolver cr = getActivity().getContentResolver();
         cr.insert(MyContentProvider.CONTENT_URI, contentValues);
         mEditTextEnglish.setText("");
         mEditTextTurkish.setText("");
         Toast.makeText(getActivity().getApplicationContext(), R.string.succes_save_word, Toast.LENGTH_SHORT).show();

      }
      else
         Toast.makeText(getActivity().getApplicationContext(), R.string.enter_word, Toast.LENGTH_SHORT).show();
   }
}
