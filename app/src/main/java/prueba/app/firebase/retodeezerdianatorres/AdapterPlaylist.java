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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterPlaylist extends BaseAdapter {

    private Activity activity;
    private ArrayList<Playlist> listPlaylist;

    public AdapterPlaylist(Activity activity) {
        this.activity = activity;
        listPlaylist = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.renglonplaylist, null, false);
        ImageView imageView = view.findViewById(R.id.iv_image);
        TextView name_playlist = view.findViewById(R.id.name_playlist);
        TextView numbers_items = view.findViewById(R.id.numbers_items);
        TextView name_creator = view.findViewById(R.id.name_creator);
        Picasso.get().load(listPlaylist.get(position).getSmallImageUrl()).into(imageView);
        numbers_items.setText("Número de items: " + listPlaylist.get(position).getTracks().size());
        name_creator.setText("Nombre del usuario creador: " + listPlaylist.get(position).getCreator().getName());
        name_playlist.setText("Nombre lista: " + listPlaylist.get(position).getTitle());
        return view;
    }

    public AdapterPlaylist (){

    }


    private AdapterView.OnItemClickListener listener;
    @Override
    public int getCount() {
        return listPlaylist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public ArrayList<Playlist> getListPlaylist() {
        return listPlaylist;
    }

    public void setListPlaylist(ArrayList<Playlist> listPlaylist) {
        this.listPlaylist = listPlaylist;
    }



    public void agregarAPlaylist(Playlist playlist) {
        listPlaylist.add(playlist);
        notifyDataSetChanged();
    }

    public void borrarListadeListasRep() {
        if (listPlaylist.size() != 0 && listPlaylist != null) {
            listPlaylist.clear();
        }
        notifyDataSetChanged();
    }












}
