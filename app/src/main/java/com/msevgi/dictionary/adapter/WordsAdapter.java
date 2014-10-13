
package com.msevgi.dictionary.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.msevgi.dictionary.R;
import com.msevgi.dictionary.model.Words;
import com.msevgi.dictionary.provider.BusProvider;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WordsAdapter extends ArrayAdapter<Words> {
   private ViewHolder mViewHolder;

   public WordsAdapter(Context context, int resource, ArrayList<Words> objects) {
      super(context, resource, objects);

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

      final Words mItem = getItem(position);
      if (mItem != null) {
         mViewHolder.mTextViewEnglish.setText(mItem.getEnglish_word());
         mViewHolder.mTextViewTurkish.setText(mItem.getTurkish_word());
         convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               BusProvider.getInstance().post(mItem);
               return false;
            }
         });
      }
      return convertView;
   }

   protected final class ViewHolder {
      @InjectView(R.id.textview_turkish_word)
      TextView mTextViewTurkish;
      @InjectView(R.id.textview_english_word)
      TextView mTextViewEnglish;

      public ViewHolder(View view) {
         ButterKnife.inject(this, view);
      }
   }
}
