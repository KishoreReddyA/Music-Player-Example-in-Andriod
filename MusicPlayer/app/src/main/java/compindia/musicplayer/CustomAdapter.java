package compindia.musicplayer;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifTextView;

/**
 * Created by compindi on 31-10-2017.
 */

class CustomAdapter extends BaseAdapter
{
Context context;
    ArrayList<MusicPlayerData> list;
    LayoutInflater inflater;
    public CustomAdapter(Context context, ArrayList<MusicPlayerData> list)
    {
        this.list=list;
this.context=context;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int i)
    {
        return list.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {

        View songTrack=inflater.inflate(R.layout.song_track,null);
        ImageView imgSong=(ImageView)songTrack.findViewById(R.id.song_image);
        TextView txtSongName=(TextView)songTrack.findViewById(R.id.txt_song_name);
        TextView txtSingerName=(TextView)songTrack.findViewById(R.id.txt_singer_name);
        ImageView imgPlayButton=(ImageView)songTrack.findViewById(R.id.img_play_button_list);
        GifTextView gifTextView=(GifTextView)songTrack.findViewById(R.id.gif);
        if(list.get(i).isplaying())
        {
            imgPlayButton.setVisibility(View.GONE);
            gifTextView.setBackgroundResource(R.drawable.music1);
            gifTextView.setVisibility(View.VISIBLE);
        }
        else {
            gifTextView.setVisibility(View.GONE);
            imgPlayButton.setImageResource(R.drawable.play_button);
        }
       imgSong.setImageResource(list.get(i).getSongImage());
        txtSongName.setText(list.get(i).getSongName());
        txtSingerName.setText(list.get(i).getSingerName());
        return songTrack;
    }
}
