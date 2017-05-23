package com.example.pcgravacao.tentei;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class Video extends Fragment {

    private VideoView ongVideoView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.video, container, false);
        ongVideoView = (VideoView) rootView.findViewById(R.id.ongVideoView);
        ongVideoView.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() +"/"+R.raw.ongvideo));
        ongVideoView.setMediaController(new MediaController(getActivity()));
        ongVideoView.setOnClickListener(listenerVideo);
        Toast.makeText(getContext(), getString(R.string.video), Toast.LENGTH_SHORT).show();
        return rootView;
    }

    private View.OnClickListener listenerVideo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ongVideoView.requestFocus();
            ongVideoView.start();
        }
    };
}
