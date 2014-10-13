
package com.msevgi.dictionary.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.msevgi.dictionary.R;
import com.msevgi.dictionary.model.Words;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WordsAdapter extends ArrayAdapter<Words> {
   private ViewHolder mViewHolder;

   public WordsAdapter(Context context, int resource, int textViewResourceId, Words[] objects) {
      super(context, resource, textViewResourceId, objects);

   }

   @Override
   public View getView(int position, View convertView, ViewGroup parent) {
      if (convertView == null) {
         convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.word_item, parent, false);
         mViewHolder = new ViewHolder(convertView);
         convertView.setTag(mViewHolder);
      }
      else
         mViewHolder = (ViewHolder) convertView.getTag();

      Words mItem = getItem(position);
      if (mItem != null) {
         mViewHolder.mTextViewEnglish.setText(mItem.getEnglish_word());
         mViewHolder.mTextViewTurkish.setText(mItem.getTurkish_word());
      }
      return convertView;
   }

   private class ViewHolder {
      @InjectView(R.id.textview_turkish_word)
      TextView mTextViewTurkish;
      @InjectView(R.id.textview_english_word)
      TextView mTextViewEnglish;

      public ViewHolder(View view) {
         ButterKnife.inject(this, view);
      }
   }
}
