package com.example.thanhthuy.nhac_mp3.Frangment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.thanhthuy.nhac_mp3.ChoiNhacActivity;
import com.example.thanhthuy.nhac_mp3.LayMusic.SongMusic;
import com.example.thanhthuy.nhac_mp3.LayMusic.TungBaiHat;
import com.example.thanhthuy.nhac_mp3.R;
import com.example.thanhthuy.nhac_mp3.LayMusic.Songs;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment {

    ListView lv;
    String[] items;

    public MusicFragment() {
        // Required empty public constructor
    }

    /**
     * Đọc từ SD Card
     * Environment.getExternalStorageDirectory().getAbsolutePath():
     * để lấy đường dẫn trên SD Card
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_music, container, false);

        lv = (ListView) v.findViewById(R.id.lvBaiHat);
        Songs song = new Songs();

        final  List<SongMusic> listbaihat = song.getMusic(getActivity());

        final TungBaiHat adTungBaiHat = new TungBaiHat(getActivity(), R.layout.bai_hat, listbaihat);
        lv.setAdapter(adTungBaiHat);

        // ArrayList<File> mySongs = findSongs
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SongMusic item = (SongMusic) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), ChoiNhacActivity.class)
                        .putExtra("ten",item.getNameMusic());
                Gson gson = new Gson();

                // chuyển chuỗi listbaihat thành string
                String json = gson.toJson(listbaihat);
                intent.putExtra("arr_song", json);
                intent.putExtra("position", position);

                startActivity(intent);
            }
        });
        return v;
    }

}
