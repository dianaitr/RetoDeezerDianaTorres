package prueba.app.firebase.retodeezerdianatorres;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.deezer.sdk.model.Playlist;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public String getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(String applicationID) {
        this.applicationID = applicationID;
    }

    private String applicationID = "349364" ;
    AdapterPlaylist adapterPlaylist;
    ListView lvPlaylist;
    EditText txtListSearched;
    ImageButton btn_search;
    Playlist playlistSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Bundle bundle = getIntent().getExtras();

        adapterPlaylist=new AdapterPlaylist(this);
        lvPlaylist=findViewById(R.id.lv_playLists);
        lvPlaylist.setAdapter(adapterPlaylist);
        adapterPlaylist.notifyDataSetChanged();

        txtListSearched=findViewById(R.id.txtListSearched);
        btn_search=findViewById(R.id.btn_search);


        // reemplazar con su propia ID de aplicación
        final DeezerConnect deezerConnect = new DeezerConnect ( this , applicationID );
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterPlaylist.borrarListadeListasRep();
                RequestListener listener = new JsonRequestListener() {

                    public void onResult(Object result, Object requestId) {

                        List<Playlist> list = (List<Playlist>) result;
                        adapterPlaylist.setListPlaylist(new ArrayList<Playlist>(list));
                    }

                    public void onUnparsedResult(String requestResponse, Object requestId) {
                    }

                    public void onException(Exception e, Object requestId) {
                    }

                };

                DeezerRequest request = null;

                if (txtListSearched!=null && !txtListSearched.getText().toString().equals("")) {
                    request = DeezerRequestFactory.requestSearchPlaylists(txtListSearched.getText().toString());
                    request.setId("PlaylistsReq");
                    deezerConnect.requestAsync(request, listener);
                } else {
                    Toast.makeText(getApplicationContext(), "Debe ingresar información en la busqueda", Toast.LENGTH_SHORT).show();
                }

            }
        });

        lvPlaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //playlistSelected=(Playlist) parent.getItemAtPosition(position);
                long idPlaylist = Long.parseLong(adapterPlaylist.getListPlaylist().get(position).getId() + "");
                Log.e("PLAYYYLISTTT",idPlaylist+"");
                Intent i = new Intent(MainActivity.this, PlaylistActivity.class);
                i.putExtra("playlistId", idPlaylist);
                startActivity(i);
            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.icon_return: {
                //Intent i= new Intent(MainActivity.this,MainActivity.this);
                break;
            }
        }
        return true;
    }
}
