
package com.msevgi.dictionary.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.msevgi.dictionary.R;
import com.msevgi.dictionary.adapter.WordsAdapter;
import com.msevgi.dictionary.provider.BusProvider;

import butterknife.InjectView;

public class FragmentGetWords extends BaseFragment {
    @InjectView(R.id.listview_words)
    public ListView listview_words;
    private WordsAdapter adapter;

   @Override
   public void onViewCreated(View view, @Nullable
   Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
   }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
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

}
