package com.fetch2.taleemabad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class VideoPlayerActivity extends AppCompatActivity {

    private PlayerView playerView;
    private SimpleExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        // Get the video path from the intent
        String videoPath = getIntent().getStringExtra("videoPath");

        // Initialize the PlayerView
        playerView = findViewById(R.id.exoPlayerView);

        // Create a SimpleExoPlayer instance
        player = new SimpleExoPlayer.Builder(this).build();

        // Attach the player to the PlayerView
        playerView.setPlayer(player);

        // Create a MediaItem using the video path
        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(videoPath));

        // Set the MediaItem to the player
        player.setMediaItem(mediaItem);

        // Prepare the player
        player.prepare();

        // Start playback when the player is ready
        player.setPlayWhenReady(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Release the player when the activity is destroyed
        if (player != null) {
            player.release();
        }
    }
}
