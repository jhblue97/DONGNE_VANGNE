package example.dongne;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;


public class LoadingActivity extends Activity {
    private ImageView loading;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0){
               // Glide.with(LoadingActivity.this).load("https://i.redd.it/ounq1mw5kdxy.gif").asGif().into(loading);
              //  Glide.with(LoadingActivity.this).load("http://192.168.0.43:8080/common/images/profile/default.png").into(loading);

            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        loading = (ImageView)findViewById(R.id.loading);
         // Glide.with(LoadingActivity.this).load("http://192.168.0.43:8080/common/images/profile/default.png").into(loading);
      //  Glide.with(LoadingActivity.this).load("http://192.168.0.43:8080/common/images/stream/loading8.gif").asGif().into(loading);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(loading);
        Glide.with(LoadingActivity.this).load(R.drawable.loading8).into(imageViewTarget);

        startLoading();
    }
   private void startLoading() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 0;
                handler.sendMessage(message);
                 //  Glide.with(LoadingActivity.this).load("https://i.redd.it/ounq1mw5kdxy.gif").asGif().into(loading);

                finish();
            }
        }, 3000);
    }
}