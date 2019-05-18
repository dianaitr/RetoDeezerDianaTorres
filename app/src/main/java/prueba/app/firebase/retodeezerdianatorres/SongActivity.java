package prueba.app.firebase.retodeezerdianatorres;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;
import com.squareup.picasso.Picasso;

public class SongActivity extends AppCompatActivity {

    private DeezerConnect deezerConnect;
    private ImageButton btn_return;
    String applicationID = "349364" ;
    private ImageView imageTrack;
    private TextView txtTrackName;
    private TextView txtArtistTrack;
    private TextView txtAlbum;
    private TextView txtDuration;
    private Button btn_escuchar;
    private long playlistId;
    private String previewUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        btn_return = findViewById(R.id.btn_return);
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SongActivity.this, PlaylistActivity.class);
                startActivity(i);
                finish();
            }
        });

        imageTrack = findViewById(R.id.imageTrack);
        txtTrackName = findViewById(R.id.txtTrackName);
        txtArtistTrack = findViewById(R.id.txtArtistTrack);
        txtAlbum = findViewById(R.id.txtAlbum);
        txtDuration = findViewById(R.id.txtDuration);
        btn_escuchar = findViewById(R.id.btn_escuchar);

        deezerConnect = new DeezerConnect(this,  applicationID);

        Bundle bundle = getIntent().getExtras();
        long idTrack = 0;
        if (bundle!=null){
            idTrack = bundle.getLong("trackId");
            playlistId = bundle.getLong("playlistId");
            complete(idTrack);
        }


    }

    private void complete(final long track_id) {

        RequestListener listener = new JsonRequestListener() {
            @Override
            public void onResult(Object o, Object o1) {
                Track track = (Track) o;
                previewUrl = track.getPreviewUrl();
                Picasso.get().load(track.getArtist().getSmallImageUrl()).into(imageTrack);
                txtTrackName.setText(track.getTitle());
                txtArtistTrack.setText(track.getArtist().getName());
                txtAlbum.setText(track.getAlbum().getTitle());
                int duration = track.getDuration();
                txtDuration.setText((int) duration/60+" mn "+duration%60+" sg");

            }

            @Override
            public void onUnparsedResult(String s, Object o) {

            }

            @Override
            public void onException(Exception e, Object o) {

            }
        };

        DeezerRequest request =DeezerRequestFactory.requestTrack(track_id);
        request.setId("TrackRe");
        deezerConnect.requestAsync(request,listener);
        btn_escuchar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] arrayWeb = previewUrl.split("//");
                Uri web = Uri.parse(arrayWeb[0] + "//" + arrayWeb[1]);
                Intent i = new Intent(Intent.ACTION_VIEW, web);
                Intent chooser = Intent.createChooser(i, "Next : ");
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
            }
        });

    }

}
