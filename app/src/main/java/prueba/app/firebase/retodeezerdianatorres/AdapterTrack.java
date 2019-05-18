package prueba.app.firebase.retodeezerdianatorres;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.deezer.sdk.model.Playlist;
import com.deezer.sdk.model.Track;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterTrack extends BaseAdapter {

    private Activity activity;
    private ArrayList<Track> tracks;

    public AdapterTrack(Activity activity) {
        this.activity = activity;
        tracks = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.renglonplaylisttrack, null, false);

        TextView art = view.findViewById(R.id.txtname_artist);
        TextView year = view.findViewById(R.id.txtdate);
        TextView track = view.findViewById(R.id.txtname_track);
        ImageView img = view.findViewById(R.id.image_playlist);
        track.setText("Nombre canción: " + tracks.get(position).getTitle());
        art.setText("Artista canción: " + tracks.get(position).getArtist().getName());
        year.setText("Año de lanzamiento: " + tracks.get(position).getReleaseDate());
       // Picasso.get().load(tracks.get(position).getAlbum().getSmallImageUrl()).into(img);
        return view;
    }

    public AdapterTrack(){

    }


    private AdapterView.OnItemClickListener listener;
    @Override
    public int getCount() {
        return tracks.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }



    public void addTrack(Track track) {
        tracks.add(track);
        notifyDataSetChanged();
    }

    public void clear() {
        if (tracks.size() != 0 && tracks != null) {
            tracks.clear();
        }
        notifyDataSetChanged();
    }












}
