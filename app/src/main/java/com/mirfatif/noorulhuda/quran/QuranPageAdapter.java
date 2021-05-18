package com.mirfatif.noorulhuda.quran;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class QuranPageAdapter extends FragmentStateAdapter {

  public static final String KEY_PAGE = "KEY_PAGE";

  public QuranPageAdapter(@NonNull FragmentActivity fragmentActivity) {
    super(fragmentActivity);
  }

  @NonNull
  @Override
  public Fragment createFragment(int position) {
    QuranPageFragment fragment = new QuranPageFragment();
    Bundle bundle = new Bundle();
    bundle.putInt(KEY_PAGE, position + 1);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public int getItemCount() {
    return mPageCount;
  }

  private int mPageCount;

  void setPageCount(int pageCount) {
    mIdDiff += mPageCount;
    mPageCount = pageCount;
    notifyDataSetChanged();
  }

  // Force recreate fragments when notifyDataSetChanged() is called
  // in order to update UI state.
  private int mIdDiff;

  @Override
  public long getItemId(int position) {
    return position + mIdDiff;
  }

  @Override
  public boolean containsItem(long itemId) {
    return itemId >= getItemId(0) && itemId < getItemId(getItemCount());
  }
}
