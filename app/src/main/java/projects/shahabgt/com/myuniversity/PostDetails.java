package projects.shahabgt.com.myuniversity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.marcohc.toasteroid.Toasteroid;

import projects.shahabgt.com.myuniversity.classes.BackgroundTask;

public class PostDetails extends AppCompatActivity {
    String postid,person,personpost;
    TextView subject,catagory,catagory_sub,title,text,time,date,state;
    TextView toolbar_text,likec,dislikec,commentsc;
    SimpleDraweeView img,loadingimg,nointernet;
    ImageView back,comments;
    LikeButton like,dislike,star;
    LinearLayout linearLayout,errorlayout,loadinglayout,report,delete;
    Bundle bundle;
    SharedPreferences sp;
    String d,dn;
    String report_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_post_details);

        bundle = getIntent().getExtras();
        sp= getApplicationContext().getSharedPreferences("logininfo",0);
        person= sp.getString("id","");
        postid= bundle.getString("postid");
        personpost=bundle.getString("person");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar_text = findViewById(R.id.toolbar_text);
        toolbar_text.setText("Post Details");

        report = findViewById(R.id.postdetails_report);
        delete = findViewById(R.id.postdetails_delete);

        if(personpost.equals(person)){
            report.setVisibility(View.GONE);
            delete.setVisibility(View.VISIBLE);
        }else{
            report.setVisibility(View.VISIBLE);
            delete.setVisibility(View.GONE);}

        linearLayout = findViewById(R.id.postdetails_layout);
        errorlayout = findViewById(R.id.postdetails_error);

        loadinglayout = findViewById(R.id.postdetails_loadinglayout);
        loadingimg = findViewById(R.id.postdetails_loadingimg);
        Uri loadinguri= Uri.parse("asset:///loading.gif");
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(loadinguri)
                .setAutoPlayAnimations(true)
                .build();
        loadingimg.setController(controller);
        loadinglayout.setVisibility(View.VISIBLE);

        nointernet = findViewById(R.id.postdetails_nointernet);
        Uri noinnetneturi= Uri.parse("asset:///nointernet.gif");
        DraweeController controller2 = Fresco.newDraweeControllerBuilder()
                .setUri(noinnetneturi)
                .setAutoPlayAnimations(true)
                .build();
        nointernet.setController(controller2);

        subject = findViewById(R.id.postdetails_subject);
        catagory = findViewById(R.id.postdetails_catagory);
        catagory_sub = findViewById(R.id.postdetails_catagory_sub);
        title = findViewById(R.id.postdetails_title);
        state = findViewById(R.id.postdetails_state);
        text = findViewById(R.id.postdetails_text);
        time = findViewById(R.id.postdetails_time);
        date = findViewById(R.id.postdetails_date);
      //  img = (SimpleDraweeView)findViewById(R.id.postdetails_img);
     //   img.getHierarchy().setProgressBarImage(new ProgressBarDrawable());

        likec = findViewById(R.id.postdetails_like_c);
        dislikec = findViewById(R.id.postdetails_dislike_c);
        commentsc = findViewById(R.id.postdetails_comments_c);

        like = findViewById(R.id.postdetails_like);
        star = findViewById(R.id.postdetails_star);
        dislike = findViewById(R.id.postdetails_dislike);
        comments = findViewById(R.id.postdetails_comments);
        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostDetails.this,ViewComments.class);
                sp.edit().putString("postid",postid).apply();
                intent.putExtra("postid",bundle.getString("postid"));
                startActivity(intent);
            }
        });

        star.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                BackgroundTask backgroundTask = new BackgroundTask(PostDetails.this, 0);
                backgroundTask.execute("dofav", person, postid);
                likeButton.setEnabled(false);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                BackgroundTask backgroundTask = new BackgroundTask(PostDetails.this, 0);
                backgroundTask.execute("dofav", person, postid);
                likeButton.setEnabled(false);

            }
        });
        like.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if(checknet()) {
                    like.setLiked(true);
                    dislike.setLiked(false);
                    like.setEnabled(false);
                    dislike.setEnabled(false);
                    d = "1";
                    dn = "0";
                    BackgroundTask backgroundTask = new BackgroundTask(PostDetails.this, 0);
                    backgroundTask.execute("dolike", postid, person, d, dn);
                }else {
                    Toasteroid.show(PostDetails.this,"Check Your Internet Connection", Toasteroid.STYLES.ERROR);
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
            }
        });

        dislike.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if(checknet()){
                    dislike.setLiked(true);
                    like.setLiked(false);
                    dislike.setEnabled(false);
                    like.setEnabled(false);
                    d="0";
                    dn="1";
                    BackgroundTask backgroundTask = new BackgroundTask(PostDetails.this, 0);
                    backgroundTask.execute("dolike", postid, person, d, dn);
                }else {
                    Toasteroid.show(PostDetails.this,"Check Your Internet Connection", Toasteroid.STYLES.ERROR);
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {

            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(PostDetails.this)
                        .title("Report")
                        .inputRangeRes(5, 100, R.color.red)
                        .content("Please Write Your Reasons")
                        .inputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_MULTI_LINE)
                        .positiveText("Send")
                        .negativeText("Cancel")
                        .positiveColor(Color.parseColor("#4caf50"))
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .input(null,null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                report_text=input.toString();
                                if(checknet()){
                                    BackgroundTask backgroundTask = new BackgroundTask(PostDetails.this, 1);
                                    backgroundTask.execute("postreport", postid, person,report_text);
                                }else {
                                    dialog.dismiss();
                                    Toasteroid.show(PostDetails.this,"Check Your Internet Connection", Toasteroid.STYLES.ERROR);

                                }

                            }
                        }).show();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(PostDetails.this)
                        .title("Delete")
                        .inputRangeRes(5, 100, R.color.red)
                        .content("Please Write Youe Reasons")
                        .inputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_MULTI_LINE)
                        .positiveText("Send")
                        .negativeText("Cancel")
                        .positiveColor(Color.parseColor("#4caf50"))
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .input(null,null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                if(checknet()){
                                    BackgroundTask backgroundTask = new BackgroundTask(PostDetails.this, 1);
                                    backgroundTask.execute("postdelete", postid,input.toString());
                                }else {
                                    dialog.dismiss();
                                    Toasteroid.show(PostDetails.this,"Check Your Internet Connection", Toasteroid.STYLES.ERROR);
                                }

                            }
                        }).show();
            }
        });

        subject.setText(bundle.getString("subject"));
        catagory.setText(bundle.getString("catagory"));
        catagory_sub.setText(bundle.getString("catagory_sub"));
        title.setText(bundle.getString("title"));
        state.setText(bundle.getString("state"));
if(checknet()) {
    BackgroundTask backgroundTask = new BackgroundTask(PostDetails.this, 0);
    backgroundTask.execute("getcount", postid, person);
}else
{
    loadinglayout.setVisibility(View.GONE);
    snackError();
}


        back= findViewById(R.id.toolbar_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostDetails.this.finish();
            }
        });


    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed()
    {
        PostDetails.this.finish();
    }
    private boolean checknet(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        return activeInfo != null && activeInfo.isConnected();
    }
    private void snackError(){
        loadinglayout.setVisibility(View.GONE);
        errorlayout.setVisibility(View.VISIBLE);
        final Snackbar snackbar = Snackbar
                .make(linearLayout, "Check Your Internet Connection", Snackbar.LENGTH_INDEFINITE)
                .setAction("Try Again", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // progressDialog.show();
                        if(checknet()){
                        errorlayout.setVisibility(View.GONE);
                        loadinglayout.setVisibility(View.VISIBLE);
                        BackgroundTask backgroundTask = new BackgroundTask(PostDetails.this, 0);
                        backgroundTask.execute("getcount", postid, person);}
                        else {
                            snackError();
                        }
                    }
                });

        snackbar.show();
    }
}
