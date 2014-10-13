package com.msevgi.dictionary.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.msevgi.dictionary.R;
import com.msevgi.dictionary.model.Words;
import com.msevgi.dictionary.provider.BusProvider;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WordsAdapter extends ArrayAdapter<Words> implements Filterable {
    private ViewHolder mViewHolder;
    private static List<Words> mOriginItmeList;
    private static List<Words> mItemList;

    public WordsAdapter(Context context, int resource, ArrayList<Words> objects) {
        super(context, resource, objects);
        mOriginItmeList = mItemList = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.word_item, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else
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

    @Override
    public int getCount() {
        return mItemList.size();
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

    @Override
    public Filter getFilter() {
        return super.getFilter();
    }

    private class MyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            String search = constraint.toString();
            if (!TextUtils.isEmpty(search)) {
                results.values = mOriginItmeList;
                results.count = mOriginItmeList.size();
            } else {
                ArrayList<Words> tempList = new ArrayList<Words>();
                for (Words item : mOriginItmeList) {
                    if (item.getEnglish_word().contains(search) || item.getTurkish_word().contains(search)) {
                        tempList.add(item);
                    }
                }
                results.values = tempList;
                results.count = tempList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mItemList = (ArrayList<Words>) results.values;
            notifyDataSetChanged();
        }
    }
}
