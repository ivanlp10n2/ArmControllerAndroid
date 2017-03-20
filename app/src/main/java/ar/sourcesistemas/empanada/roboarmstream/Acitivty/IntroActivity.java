package ar.sourcesistemas.empanada.roboarmstream.Acitivty;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.widget.Toast;
import android.widget.VideoView;

import ar.sourcesistemas.empanada.roboarmstream.BuildConfig;
import ar.sourcesistemas.empanada.roboarmstream.R;

/**
 * Created by ivan.monzon on 3/20/2017.
 */

public class IntroActivity extends Activity
{
    private VideoView videoView;
    private String extStorageDirectory;
    protected static final int PLAY = 0x101;
    protected static final int PAUSE = 0x102;
    protected static final int STOP = 0x103;
    private String concurrent;

    private String path;
    private VideoView mVideoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_intro);
        path = "android.resource://" + BuildConfig.APPLICATION_ID + "/" + R.raw.intro;
        mVideoView = (VideoView) findViewById(R.id.videoScreen);

        if(path == null || path.length() == 0)
        {
            Toast.makeText(IntroActivity.this, "File/url path is empty", Toast.LENGTH_LONG).show();
        }

        else
        {
            if (path.equals(concurrent) && mVideoView != null)
            {
                mVideoView.start();
                mVideoView.requestFocus();
                return;
            }
            concurrent = path;
            mVideoView.setVideoPath(path);
            mVideoView.start();
            mVideoView.requestFocus();
        }

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer)
            {
                Intent i = new Intent(getApplicationContext(), StreamActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
