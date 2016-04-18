package mx.com.ioblok.buddystars.news;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import mx.com.ioblok.buddystars.R;
import mx.com.ioblok.buddystars.adapter.BlogElementAdapter;
import mx.com.ioblok.buddystars.utils.Constants;
import mx.com.ioblok.buddystars.utils.WebBridge;

public class DetailBlogActivity extends Activity implements WebBridge.WebBridgeListener{

    private RecyclerView recyclerViewBlog;
    private ImageView iv_description_blog;
    private TextView tv_txt_description_video;
    public String id,a = "o";
    int id_bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_blog);

        Bundle bundle = getIntent().getExtras();

        if (bundle!= null){
            id = bundle.getString("id");
            id_bundle = Integer.parseInt(id);
        }


        iv_description_blog = (ImageView)findViewById(R.id.iv_description_blog);
        tv_txt_description_video = (TextView)findViewById(R.id.tv_txt_description_video);

        WebBridge.send("/news", "Cargando", this, this);

    }

    @Override
    public void onWebBridgeSuccess(String url, JSONObject json) {

        Log.e("json", json.toString());
        try {
            if (json.getBoolean("success")) {
                JSONArray jsonArrayBlog = json.getJSONArray("data");

                for (int i=0 ; i<jsonArrayBlog.length();i++){
                    JSONObject blog = jsonArrayBlog.getJSONObject(i);
                    String id_servicio = blog.getString("id");

                    int id_service = Integer.parseInt(id_servicio);

                    String descriptionBlog;
                    String urlImageBlog;

                    if (id_bundle == id_service){

                        urlImageBlog = blog.getString("full");
                        Log.e("Url de imagen" , urlImageBlog);
                        Glide.with(this).load(urlImageBlog).into(iv_description_blog);
                        descriptionBlog = blog.getString("description");
                        tv_txt_description_video.setText(descriptionBlog);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

    }

    @Override
    public void onWebBridgeFailure(String url, String response) {
        Log.e("JSON", response);
    }
}
