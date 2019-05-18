package prueba.app.firebase.retodeezerdianatorres;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.deezer.sdk.model.Playlist;
import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlaylistActivity extends AppCompatActivity {


    private DeezerConnect deezerConnect;
    String applicationID = "349364" ;
    private long playlistId;
    private ImageButton btn_return;
    private ImageView image_playlist;
    private TextView txtplaylistName;
    private TextView txtDescription;
    private TextView txtInfoPlaylist;


    private AdapterTrack adapterTrack;



    private ListView lv_infoplaylists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        btn_return = findViewById(R.id.btn_return);
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlaylistActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        image_playlist = findViewById(R.id.image_playlist);
        txtplaylistName = findViewById(R.id.txtplaylistName);
        txtDescription = findViewById(R.id.txtDescription);
        txtInfoPlaylist = findViewById(R.id.txtInfoPlaylist);
        lv_infoplaylists = findViewById(R.id.lv_infoplaylists);

        adapterTrack = new AdapterTrack(this);
        lv_infoplaylists.setAdapter(adapterTrack);

        deezerConnect = new DeezerConnect(this,  applicationID);

        Bundle bundle = getIntent().getExtras();
        playlistId = 0;
        if (bundle!=null){
            playlistId = bundle.getLong("playlistId");
            complete(playlistId);
        }

        lv_infoplaylists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long idTrack = Long.parseLong(adapterTrack.getTracks().get(position).getId() + "");
                Log.e("TRACCCCKKK",idTrack+"");
                Intent i = new Intent(PlaylistActivity.this, SongActivity.class);
                i.putExtra("trackId", idTrack);
                i.putExtra("playlistId",playlistId);
                startActivity(i);
            }
        });





    }

    private void complete(long playlist_id) {

        RequestListener listener = new JsonRequestListener() {
            @Override
            public void onResult(Object o, Object o1) {
                adapterTrack.clear();
                Playlist playlist = (Playlist) o;
                Picasso.get().load(playlist.getPictureUrl()).into(image_playlist);
                txtplaylistName.setText(playlist.getTitle());
                txtDescription.setText(playlist.getDescription());
                txtInfoPlaylist.setText("("+playlist.getTracks().size()+" canciones) ("+playlist.getFans()+" fans)");
                adapterTrack.setTracks(new ArrayList<Track>(playlist.getTracks()));
            }

            @Override
            public void onUnparsedResult(String s, Object o) {

            }

            @Override
            public void onException(Exception e, Object o) {

            }
        };

        DeezerRequest request = DeezerRequestFactory.requestPlaylist(playlist_id);
        request.setId("PlaylistReq");
        deezerConnect.requestAsync(request, listener);

    }


}
