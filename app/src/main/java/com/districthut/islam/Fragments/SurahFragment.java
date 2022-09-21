package com.districthut.islam.Fragments;

import static com.mirfatif.noorulhuda.prefs.MySettings.SETTINGS;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.districthut.islam.Activities.Quran.adapter.SurahAdapter;
import com.districthut.islam.Activities.Quran.model.Surah;
import com.districthut.islam.utils.datasource.SurahDataSource;
import com.mirfatif.noorulhuda.R;
import com.districthut.islam.utils.DatabaseHelper;

import com.mirfatif.noorulhuda.databinding.FragmentSurahBinding;
import com.mirfatif.noorulhuda.db.DbBuilder;
import com.mirfatif.noorulhuda.ui.dialog.AlertDialogFragment;
import com.mirfatif.noorulhuda.util.Utils;

import java.util.ArrayList;


public class SurahFragment extends Fragment {


    public SurahFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getActivity().getSharedPreferences("QuranPref", 0); // 0 - for private mode
        editor = pref.edit();
        //CheckLastSeen();

        //surahData = new ArrayList<>();
    }

    FragmentSurahBinding binding;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    Long lastsura,lastaya;
    String lastSura_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSurahBinding.inflate(inflater, container, false);

//        if(SDCardManager.isSdCardWritable()) {
//            SDCardManager.CreateFolders(Constants.QURAN_PATH);
//            SDCardManager.CreateFolders(Constants.TILAWAT_PATH);
//            SDCardManager.CreateFolders(Constants.TRANSLATIONS_PATH);
//        }




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DatabaseHelper dbHelper= new DatabaseHelper(getContext());
                try {
                    dbHelper.createDataBase();
                    dbHelper.openDataBase();
                    SurahDataSource dataSource = new SurahDataSource(getContext());
                    ArrayList<Surah> surahs = dataSource.getEnglishSurahArrayList();
                    SurahAdapter surahAdapter = new SurahAdapter(surahs, getContext());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    binding.surahRecyclerView.setLayoutManager(linearLayoutManager);
                    binding.surahRecyclerView.setAdapter(surahAdapter);
                   // dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 250);
        
        return binding.getRoot();
    }

    void CheckLastSeen(){
        lastSura_name = pref.getString("lastsura_arabic","");
        binding.suraArabicLastseen.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),  "fonts/arabic.ttf"));
        if(!lastSura_name.equals("")) {
            binding.lastSeenLayout.setVisibility(View.VISIBLE);
            lastsura = pref.getLong("lastSura",0);
            lastaya = pref.getLong("lastAya",0);
            binding.suraArabicLastseen.setText(lastSura_name);
            binding.surahLastSeen.setText("Surah: " + lastsura);
            binding.ayaLastSeen.setText("Aya: " + lastaya);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //CheckLastSeen();
    }
}