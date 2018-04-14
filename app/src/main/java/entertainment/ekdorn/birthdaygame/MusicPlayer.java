/*
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package entertainment.ekdorn.birthdaygame;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;
import java.util.Random;

public enum MusicPlayer {
	
	INSTANCE;
	
	private MediaPlayer player;
	
	private String lastPlayed;
	private int lastStop = 0;
	
	private boolean enabled = false;
	
	public void play(Context context) {

	    String assetName = findAsset();
		
		if (isPlaying() && lastPlayed.equals( assetName )) {
			return;
		}
		
		stop();
		
		lastPlayed = assetName;
		
		if (!enabled || assetName == null) {
			return;
		}
		
		try {
			
			AssetFileDescriptor afd = context.getAssets().openFd( assetName );
			
			player = new MediaPlayer();
			player.setAudioStreamType( AudioManager.STREAM_MUSIC );
			player.setDataSource( afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength() );
			player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    player.start();
                    player.seekTo(lastStop);
                }
            });
			player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    lastStop = 0;
                    play(context);
                }
            });
			player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    if (player != null) {
                        player.release();
                        player = null;
                    }
                    return true;
                }
            });
			player.prepareAsync();
			
		} catch (IOException e) {
			
			player.release();
			player = null;
			
		}
	}
	
	private void stop() {
		if (player != null) {
			player.stop();
			player.release();
			player = null;
		}
	}
	
	private boolean isPlaying() {
		return player != null && player.isPlaying();
	}
	
	public void enable(boolean value, Context context) {
		enabled = value;
		if (isPlaying() && !value) {
		    lastStop = player.getCurrentPosition();
			stop();
		} else if (!isPlaying() && value) {
			play(context);
		}
	}
	
	public boolean isEnabled() {
		return enabled;
	}

    private String findAsset() {
        Random rnd = new Random();
        int period = rnd.nextInt(1) + 1;
        return String.format(AssetConstants.MUSIC, period);
    }
}
