package projects.shahabgt.com.myuniversity.classes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.baoyz.widget.PullRefreshLayout;
import com.facebook.drawee.view.SimpleDraweeView;
import com.like.LikeButton;
import com.marcohc.toasteroid.Toasteroid;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import projects.shahabgt.com.myuniversity.NewPost;
import projects.shahabgt.com.myuniversity.R;
import projects.shahabgt.com.myuniversity.ViewPost;
import projects.shahabgt.com.myuniversity.adapters.CommentsAdapter;
import projects.shahabgt.com.myuniversity.adapters.MyPostsAdapter;
import projects.shahabgt.com.myuniversity.adapters.PicAdapter;
import projects.shahabgt.com.myuniversity.adapters.ViewpostAdapter;
import projects.shahabgt.com.myuniversity.models.CommentsModel;
import projects.shahabgt.com.myuniversity.models.DateModel;
import projects.shahabgt.com.myuniversity.models.MyPostsModel;
import projects.shahabgt.com.myuniversity.models.PicModel;
import projects.shahabgt.com.myuniversity.models.PostsModel;


public class BackgroundTask extends AsyncTask<String,Void,String>{
    Activity activity;
    Context ctx;
    ProgressDialog progressDialog;
    AlertDialog.Builder builder,builder2;
    SharedPreferences sp;
    int stat;
    ViewpostAdapter adapter;
    CommentsAdapter adapter2;
    PicAdapter adapter3;
    MyPostsAdapter adapter4;
    String method,pic,pic2,pic3,pic4,didlike;
    String postidx,person,scroll;
    RecyclerView.LayoutManager layoutManager;
    RelativeLayout relativeLayout;
    RecyclerView recyclerView;
    PullRefreshLayout pullRefreshLayout;
    LinearLayout errorlayout,loadinglayout,linearLayout;
    LinearLayout errorlayout1,loadinglayout1,linearLayout1;
    LinearLayout errorlayout2,loadinglayout2;
    String[] s=new String[4];
    Uri uri;
    TextView text,time,date,commentsc,likec,dislikec;
    SimpleDraweeView img;
    LikeButton like,dislike;
    EditText etext;
    LikeButton star,heart;

    public BackgroundTask(Context context,int stat){

        this.ctx = context;
        this.activity = (Activity)context;
        this.stat=stat;

    }

    @Override
    protected void onPreExecute() {

            builder = new AlertDialog.Builder(activity);
            builder2 = new AlertDialog.Builder(activity);
            progressDialog = new ProgressDialog(ctx);
            progressDialog.setMessage("لطفا منتظر بمانید...");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        if(stat==1) {
            progressDialog.show();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        method = params[0];
        if(method.equals("insert")) {
            final String urlLink = ctx.getResources().getString(R.string.url)+"insert.php";
            sp = activity.getSharedPreferences("logininfo", 0);
            String person = sp.getString("id", "");
            Calendar c = Calendar.getInstance();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            String date = df.format(c.getTime());
            SimpleDateFormat tf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
            String time = tf.format(c.getTime());

            SimpleDateFormat ndf = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
            String ndate = ndf.format(c.getTime());
            SimpleDateFormat ntf = new SimpleDateFormat("HHmmss", Locale.ENGLISH);
            String ntime = ntf.format(c.getTime());
            String postid = person + ndate + ntime;
            try {
                URL url = new URL(urlLink);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data =
                        URLEncoder.encode("postid", "UTF-8") + "=" + URLEncoder.encode(postid, "UTF-8") + "&" +
                                URLEncoder.encode("person", "UTF-8") + "=" + URLEncoder.encode(person, "UTF-8") + "&" +
                                URLEncoder.encode("subject", "UTF-8") + "=" + URLEncoder.encode(params[5], "UTF-8") + "&" +
                                URLEncoder.encode("catagory", "UTF-8") + "=" + URLEncoder.encode(params[6], "UTF-8") + "&" +
                                URLEncoder.encode("catagory_sub", "UTF-8") + "=" + URLEncoder.encode(params[7], "UTF-8") + "&" +
                                URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(params[8], "UTF-8") + "&" +
                                URLEncoder.encode("comment", "UTF-8") + "=" + URLEncoder.encode(params[9], "UTF-8") + "&" +
                                URLEncoder.encode("pic", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" +
                                URLEncoder.encode("pic2", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8") + "&" +
                                URLEncoder.encode("pic3", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8") + "&" +
                                URLEncoder.encode("pic4", "UTF-8") + "=" + URLEncoder.encode(params[4], "UTF-8") + "&" +
                                URLEncoder.encode("picurl", "UTF-8") + "=" + URLEncoder.encode(postid+"_1", "UTF-8") + "&" +
                                URLEncoder.encode("picurl2", "UTF-8") + "=" + URLEncoder.encode(postid+"_2", "UTF-8") + "&" +
                                URLEncoder.encode("picurl3", "UTF-8") + "=" + URLEncoder.encode(postid+"_3", "UTF-8") + "&" +
                                URLEncoder.encode("picurl4", "UTF-8") + "=" + URLEncoder.encode(postid+"_4", "UTF-8") + "&" +
                                URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8") + "&" +
                                URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8") + "&" +
                                URLEncoder.encode("stat", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                return "noway1";
            }
        }else if (method.equals("getposts"))
        {
           // final String urlLink = "http://ashpazchili.ir/iau/getposts.php";
             String urlLink = params[1];
            try{
                URL url = new URL(urlLink);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(false);
                httpURLConnection.setDoInput(true);
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            }catch (Exception e){
                return "noway2";
            }

        }else if(method.equals("postdetails")){
            final String urlLink = ctx.getResources().getString(R.string.url)+"postdetails.php";
            postidx = params[1];
            person  = params[2];
            try {
                URL url = new URL(urlLink);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data =
                        URLEncoder.encode("postid", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                return "noway3";
            }

        }else if(method.equals("getcount")){
            final String urlLink = ctx.getResources().getString(R.string.url)+"getcommentscount.php";
            postidx = params[1];
            person  = params[2];
            try {
                URL url = new URL(urlLink);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data =
                        URLEncoder.encode("postid", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                return "noway4";
            }

        }else if(method.equals("getlikes")){
            final String urlLink =ctx.getResources().getString(R.string.url)+"getlikes.php";
            postidx=params[1];
            person=params[2];
            try {
                URL url = new URL(urlLink);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data =URLEncoder.encode("postid", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" +
                        URLEncoder.encode("person", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                return "noway5";
            }

        }else if(method.equals("dolike")){
            final String urlLink = ctx.getResources().getString(R.string.url)+"dolike.php";
            didlike=params[3];
            postidx = params[1];
            person  = params[2];
            try {
                URL url = new URL(urlLink);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data =URLEncoder.encode("postid", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" +
                        URLEncoder.encode("person", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8")+ "&" +
                        URLEncoder.encode("like", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8")+ "&" +
                        URLEncoder.encode("dislike", "UTF-8") + "=" + URLEncoder.encode(params[4], "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                return "noway6";
            }

        }else if(method.equals("getcomments")) {
            scroll=params[3];
            final String urlLink = ctx.getResources().getString(R.string.url)+"getcomments.php";
            try {
                URL url = new URL(urlLink);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data =
                        URLEncoder.encode("postid", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" +
                                URLEncoder.encode("person", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                return "noway7";
            }
        }else if(method.equals("insertcomment")) {
            scroll=params[5];
            final String urlLink = ctx.getResources().getString(R.string.url)+"insertcomment.php";
            postidx = params[1];
            person = params[2];
            Calendar c = Calendar.getInstance();
            SimpleDateFormat ndf = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
            String ndate = ndf.format(c.getTime());
            SimpleDateFormat ntf = new SimpleDateFormat("HHmmss", Locale.ENGLISH);
            String ntime = ntf.format(c.getTime());
            String cmid = "c"+person + ndate + ntime;

            try {
                URL url = new URL(urlLink);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data =
                                URLEncoder.encode("cmid", "UTF-8") + "=" + URLEncoder.encode(cmid, "UTF-8") + "&" +
                                URLEncoder.encode("postid", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" +
                                URLEncoder.encode("cmperson", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8") + "&" +
                                URLEncoder.encode("cmcomment", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8") + "&" +
                                URLEncoder.encode("cmtime", "UTF-8") + "=" + URLEncoder.encode(params[4], "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                return "noway8";
            }
        }else if(method.equals("dofav")){
            final String urlLink = ctx.getResources().getString(R.string.url)+"dofav.php";
            try {
                URL url = new URL(urlLink);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data =URLEncoder.encode("person", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" +
                        URLEncoder.encode("postid", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                return "noway9";
            }

        }else if(method.equals("getfav")){
            final String urlLink = ctx.getResources().getString(R.string.url)+"getfav.php";
            try {
                URL url = new URL(urlLink);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data =URLEncoder.encode("person", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" +
                        URLEncoder.encode("postid", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                return "noway10";
            }

        }else if (method.equals("getfavposts"))
        {
            final String urlLink = ctx.getResources().getString(R.string.url)+"getfavposts.php";
            try{
                URL url = new URL(urlLink);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data =URLEncoder.encode("person", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            }catch (Exception e){
                return "noway11";
            }

        }else if (method.equals("doclike"))
        {   postidx=params[1];
            person=params[3];
            scroll=params[4];
            final String urlLink = ctx.getResources().getString(R.string.url)+"doclike.php";
            try{
                URL url = new URL(urlLink);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data =URLEncoder.encode("cmid", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8") + "&" +
                        URLEncoder.encode("person", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            }catch (Exception e){
                return "noway12";
            }

        }else if (method.equals("postreport"))
        {
            final String urlLink = ctx.getResources().getString(R.string.url)+"postreport.php";
            try{
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                String date = df.format(c.getTime());
                SimpleDateFormat tf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
                String time = tf.format(c.getTime());

                URL url = new URL(urlLink);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data =URLEncoder.encode("postid", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" +
                        URLEncoder.encode("person", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8")+ "&" +
                        URLEncoder.encode("reason", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8")+ "&" +
                        URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")+ "&" +
                        URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            }catch (Exception e){
                return "noway13";
            }
        }else if (method.equals("postdelete"))
        {
            final String urlLink = ctx.getResources().getString(R.string.url)+"postdelete.php";
            try{
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                String date = df.format(c.getTime());
                SimpleDateFormat tf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
                String time = tf.format(c.getTime());

                URL url = new URL(urlLink);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data =URLEncoder.encode("postid", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" +
                        URLEncoder.encode("reason", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8")+ "&" +
                        URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")+ "&" +
                        URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            }catch (Exception e){
                return "noway14";
            }
        }else if (method.equals("commentdelete"))
        {scroll=params[4];
            final String urlLink = ctx.getResources().getString(R.string.url)+"commentdelete.php";
            try{
                postidx = params[2];
                person = params[3];
                URL url = new URL(urlLink);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data =URLEncoder.encode("cmid", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            }catch (Exception e){
                return "noway15";
            }
        }else if (method.equals("commentreport"))
        {   scroll=params[4];
            final String urlLink = ctx.getResources().getString(R.string.url)+"commentreport.php";
            try{
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                String date = df.format(c.getTime());
                SimpleDateFormat tf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
                String time = tf.format(c.getTime());

                URL url = new URL(urlLink);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data =URLEncoder.encode("cmid", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" +
                        URLEncoder.encode("person", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8")+ "&" +
                        URLEncoder.encode("reason", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8")+ "&" +
                        URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")+ "&" +
                        URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            }catch (Exception e){
                return "noway16";
            }
        }else if (method.equals("suggestion"))
        {
            final String urlLink = ctx.getResources().getString(R.string.url)+"suggestion.php";
            try{
                URL url = new URL(urlLink);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data =URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            }catch (Exception e){
                return "noway17";
            }
        }else if (method.equals("getmyposts"))
        {
            final String urlLink = ctx.getResources().getString(R.string.url)+"getmyposts.php";
            try{
                URL url = new URL(urlLink);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data =URLEncoder.encode("person", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            }catch (Exception e){
                return "noway18";
            }

        }
        return "ee";
    }

    @Override
    protected void onPostExecute(String response) {
        loadinglayout1 = activity.findViewById(R.id.postdetails_loadinglayout);
        errorlayout1 = activity.findViewById(R.id.postdetails_error);
        loadinglayout = activity.findViewById(R.id.viewpost_loadinglayout);
        errorlayout = activity.findViewById(R.id.viewpost_error);
        linearLayout = activity.findViewById(R.id.viewpost_layout);
        linearLayout1 = activity.findViewById(R.id.postdetails_layout);
        like = activity.findViewById(R.id.postdetails_like);
        dislike = activity.findViewById(R.id.postdetails_dislike);
        star = activity.findViewById(R.id.postdetails_star);
        heart = activity.findViewById(R.id.comments_items_heart);
        if(method.equals("insert")){
            progressDialog.dismiss();
            if(response.equals("noway1"))
            {
                DisplayMassage("Error!","Please Try Again",0);
            }
            else if(response.equals("ok"))
            {
                DisplayMassage("Sent!","Post Sent Successfully!",1);
            }else if(response.equals("notok")) {
                DisplayMassage("Error!","Please Try Again",0);
            }

        }else if(method.equals("getposts")) {
            if (response.equals("noway2")) {
                errorlayout.setVisibility(View.VISIBLE);
                loadinglayout.setVisibility(View.GONE);
                Snackbar snackbar = Snackbar
                        .make(linearLayout, "Something Bad Has Happend", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Back", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                activity.finish();
                            }
                        });

                snackbar.show();
            } else {
                pullRefreshLayout = activity.findViewById(R.id.viewpost_swipeRefreshLayout);
                recyclerView = activity.findViewById(R.id.viewpost_recylcer);
                ArrayList<PostsModel> postsModels = new ArrayList<PostsModel>();
                postsModels.clear();
                try {
                    JSONArray jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++) {

                        PostsModel postsModel = new PostsModel();
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        postsModel.set_postid(jsonobject.getString("postid"));
                        postsModel.set_like(jsonobject.getString("likes"));
                        postsModel.set_dislike(jsonobject.getString("dislikes"));
                        postsModel.set_comment(jsonobject.getString("comments"));
                        postsModel.set_person(jsonobject.getString("person"));
                        postsModel.set_subject(jsonobject.getString("subject"));
                        postsModel.set_catagory(jsonobject.getString("catagory"));
                        postsModel.set_catagory_sub(jsonobject.getString("catagory_sub"));
                        postsModel.set_title(jsonobject.getString("title"));
                        postsModel.set_state(jsonobject.getString("state"));
                        postsModels.add(postsModel);
                    }

                    layoutManager = new LinearLayoutManager(activity);
                    adapter = new ViewpostAdapter(activity, postsModels);
                    ViewPost.adapter = adapter;
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                    loadinglayout.setVisibility(View.GONE);
                    errorlayout.setVisibility(View.GONE);
                    pullRefreshLayout.setRefreshing(false);
                    ViewPost.mstate = 1;
                    activity.invalidateOptionsMenu();
                } catch (Exception e) {
                    Toasteroid.show(activity, "Please Try Again", Toasteroid.STYLES.ERROR);
                }

            }
        }else if(method.equals("postdetails")){
            if(response.equals("noway3"))
            {
                errorlayout1.setVisibility(View.VISIBLE);
                loadinglayout1.setVisibility(View.GONE);
                Snackbar snackbar = Snackbar
                        .make(linearLayout, "Something Bad Has Happend", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Back", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                activity.finish();
                            }
                        });

                snackbar.show();
            }
            else if(!response.equals("nothing"))
            {
               // img = (SimpleDraweeView)activity.findViewById(R.id.postdetails_img);
                text = activity.findViewById(R.id.postdetails_text);
                time = activity.findViewById(R.id.postdetails_time);
                date = activity.findViewById(R.id.postdetails_date);
                String[] data = response.split("::");
                text.setText(data[1]);
                pic=data[2];
                pic2=data[3];
                pic3=data[4];
                pic4=data[5];
                DateParser dp = new DateParser(data[7]+"-11-11-11");
                DateModel dm = DateParser.dateAndTimeParser();
                String temp = dm.toString();
                date.setText(data[7]);
                time.setText(data[6]);
                RecyclerView mRecyclerView = activity.findViewById(R.id.list);
                ArrayList<PicModel> picModels = new ArrayList<PicModel>();
                picModels.clear();
                if(pic.length()>5) {
                    PicModel picModel = new PicModel();
                    picModel.set_pic(pic);
                    picModels.add(picModel);
                    if(pic2.length()>5) {
                        PicModel picModel2 = new PicModel();
                        picModel2.set_pic(pic2);
                        picModels.add(picModel2);
                        if(pic3.length()>5) {
                            PicModel picModel3 = new PicModel();
                            picModel3.set_pic(pic3);
                            picModels.add(picModel3);
                            if(pic4.length()>5) {
                                PicModel picModel4 = new PicModel();
                                picModel4.set_pic(pic4);
                                picModels.add(picModel4);
                            }
                        }
                    }
                }else
                {
                    PicModel picModel = new PicModel();
                    picModel.set_pic("noimage");
                    picModels.add(picModel);
                }
                adapter3 = new PicAdapter(activity.getApplicationContext(), picModels);
                LinearLayoutManager layout = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false);
                mRecyclerView.setLayoutManager(layout);
                mRecyclerView.setAdapter(adapter3);
                adapter3.notifyDataSetChanged();
                PagerSnapHelper snapHelper = new PagerSnapHelper();
                snapHelper.attachToRecyclerView(mRecyclerView);
                mRecyclerView.addItemDecoration( new LinePagerIndicatorDecoration());
                BackgroundTask backgroundTask =new BackgroundTask(activity,0);
                backgroundTask.execute("getlikes",postidx,person);
            }else
            {
                DisplayMassage("Error!","Please Try Again",1);

            }
        }else if(method.equals("getcount")){
            if(response.equals("noway4"))
            {
                errorlayout1.setVisibility(View.VISIBLE);
                loadinglayout1.setVisibility(View.GONE);
                Snackbar snackbar = Snackbar
                        .make(linearLayout1, "Something Bad Has Happend", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Back", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                activity.finish();
                            }
                        });

                snackbar.show();
            }else {
                commentsc = activity.findViewById(R.id.postdetails_comments_c);
                commentsc.setText(response);
                BackgroundTask backgroundTask = new BackgroundTask(activity, 0);
                backgroundTask.execute("postdetails", postidx, person);
            }
        }else if(method.equals("getlikes")){
            if(response.equals("noway5")){
                errorlayout1.setVisibility(View.VISIBLE);
                loadinglayout1.setVisibility(View.GONE);
                Snackbar snackbar = Snackbar
                        .make(linearLayout1, "Something Bad Has Happend", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Back", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                activity.finish();
                            }
                        });

                snackbar.show();
            }else if(!response.isEmpty()) {
                likec = activity.findViewById(R.id.postdetails_like_c);
                dislikec = activity.findViewById(R.id.postdetails_dislike_c);
                like = activity.findViewById(R.id.postdetails_like);
                dislike = activity.findViewById(R.id.postdetails_dislike);
                String[] datax = response.split("::");
                likec.setText(datax[0]);
                dislikec.setText(datax[1]);
                String wyd=datax[2];
                if(wyd.equals("1"))
                {   like.setLiked(true);
                    like.setEnabled(false);
                    dislike.setEnabled(true);
                    dislike.setLiked(false);
                }else if(wyd.equals("0"))
                {   dislike.setLiked(true);
                    dislike.setEnabled(false);
                    like.setEnabled(true);
                    like.setLiked(false);
                }
                BackgroundTask backgroundTask = new BackgroundTask(activity, 0);
                backgroundTask.execute("getfav", person, postidx);

            }
        }else if(method.equals("dolike")) {


            if (response.equals("noway6")) {

            } else {
                if (didlike.equals("1")) {
                    dislike.setEnabled(true);
                } else if (didlike.equals("0")) {
                    like.setEnabled(true);
                }
                BackgroundTask backgroundTask = new BackgroundTask(activity, 0);
                backgroundTask.execute("getlikes", postidx, person);
            }
        }else if(method.equals("getcomments")) {
            recyclerView = activity.findViewById(R.id.viewcomments_recylcer);
            loadinglayout2 = activity.findViewById(R.id.viewcomments_loadinglayout);
            errorlayout2 = activity.findViewById(R.id.viewcomments_error);
            relativeLayout = activity.findViewById(R.id.viewcomments_layout);
            if (response.equals("noway7")) {
                errorlayout2.setVisibility(View.VISIBLE);
                loadinglayout2.setVisibility(View.GONE);
                Snackbar snackbar = Snackbar
                        .make(relativeLayout, "Something Bad Has Happend", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Back", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                activity.finish();
                            }
                        });

                snackbar.show();
            } else if (!response.equals("nothing")) {

                ArrayList<CommentsModel> commentsModels = new ArrayList<CommentsModel>();
                commentsModels.clear();
                try {
                    JSONArray jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++) {

                        CommentsModel commentsModel = new CommentsModel();
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        commentsModel.set_cmid(jsonobject.getString("cmid"));
                        commentsModel.set_cmperson(jsonobject.getString("cmperson"));
                        commentsModel.set_comment(jsonobject.getString("comment"));
                        DateParser dp = new DateParser(jsonobject.getString("time"));
                        DateModel dm = DateParser.dateAndTimeParser();
                        String temp = dm.toString();
                        commentsModel.set_time(temp);
                        commentsModel.set_name(jsonobject.getString("name"));
                        commentsModel.set_avatar(jsonobject.getString("avatar"));
                        commentsModel.set_likes(jsonobject.getString("likes"));
                        commentsModel.set_didlike(jsonobject.getString("didlike"));
                        commentsModels.add(commentsModel);
                    }

                    layoutManager = new LinearLayoutManager(activity);
                    adapter2 = new CommentsAdapter(activity, commentsModels);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter2);
                    if(scroll.equals("do")) {
                        recyclerView.scrollToPosition(adapter2.getItemCount() - 1);
                    }
                    loadinglayout2.setVisibility(View.GONE);
                    errorlayout2.setVisibility(View.GONE);
                } catch (Exception e) {
                    Toasteroid.show(activity, "Please Try Again", Toasteroid.STYLES.ERROR);
                }

            }else if (response.equals("nothing")) {
                recyclerView.setAdapter(null);
                loadinglayout2.setVisibility(View.GONE);
                errorlayout2.setVisibility(View.GONE);

            }
        }else if(method.equals("insertcomment")) {
            etext = activity.findViewById(R.id.viewcomments_text);
            if(response.equals("noway7")||response.equals("notok")){
                progressDialog.dismiss();
                DisplayMassage("Error!","Please Try Again",0);
            }else if(response.equals("ok")){
                etext.setText("");
                progressDialog.dismiss();
                BackgroundTask backgroundTask =new BackgroundTask(activity,0);
                backgroundTask.execute("getcomments",postidx,person,scroll);
            }
        }else if(method.equals("dofav")) {


            if (response.equals("noway9")) {

            } else if(response.equals("ok")) {
                star.setEnabled(true);
            }
        }else if(method.equals("getfav")) {
            if (response.equals("noway10")) {

            } else if(response.equals("yes")) {
                star.setLiked(true);
                star.setEnabled(true);
            } else if(response.equals("no")) {
                star.setLiked(false);
                star.setEnabled(true);
            }
            loadinglayout1.setVisibility(View.GONE);
            errorlayout1.setVisibility(View.GONE);
        }else if(method.equals("getfavposts")) {
            if (response.equals("noway11")) {
                errorlayout.setVisibility(View.VISIBLE);
                loadinglayout.setVisibility(View.GONE);
                Snackbar snackbar = Snackbar
                        .make(linearLayout, "Something Bad Has Happend", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Back", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                activity.finish();
                            }
                        });

                snackbar.show();
            } else if (!response.equals("empty")) {
                pullRefreshLayout = activity.findViewById(R.id.viewpost_swipeRefreshLayout);
                recyclerView = activity.findViewById(R.id.viewpost_recylcer);
                ArrayList<PostsModel> postsModels = new ArrayList<PostsModel>();
                postsModels.clear();
                try {
                    JSONArray jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++) {

                        PostsModel postsModel = new PostsModel();
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        postsModel.set_postid(jsonobject.getString("postid"));
                        postsModel.set_like(jsonobject.getString("likes"));
                        postsModel.set_dislike(jsonobject.getString("dislikes"));
                        postsModel.set_comment(jsonobject.getString("comments"));
                        postsModel.set_subject(jsonobject.getString("subject"));
                        postsModel.set_catagory(jsonobject.getString("catagory"));
                        postsModel.set_catagory_sub(jsonobject.getString("catagory_sub"));
                        postsModel.set_title(jsonobject.getString("title"));
                        postsModel.set_state(jsonobject.getString("state"));
                        postsModels.add(postsModel);
                    }

                    layoutManager = new LinearLayoutManager(activity);
                    adapter = new ViewpostAdapter(activity, postsModels);
                    ViewPost.adapter = adapter;
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    loadinglayout.setVisibility(View.GONE);
                    errorlayout.setVisibility(View.GONE);
                    pullRefreshLayout.setRefreshing(false);
                    ViewPost.fstate = 0;
                    activity.invalidateOptionsMenu();
                } catch (Exception e) {
                    Toasteroid.show(activity, "Please Try Again", Toasteroid.STYLES.ERROR);
                }

            }else if (response.equals("empty")){
                DisplayMassage("Error!","No Favorite Post",1);
            }
        }else if(method.equals("doclike")) {
            etext = activity.findViewById(R.id.viewcomments_text);
            if(response.equals("noway12")||response.equals("notok")){
                DisplayMassage("Error!","Please Try Again",0);
            }else if(response.equals("ok")){
                BackgroundTask backgroundTask =new BackgroundTask(activity,0);
                backgroundTask.execute("getcomments",postidx,person,scroll);
            }
        }else if(method.equals("postreport")) {
            progressDialog.dismiss();
            if(response.equals("noway13")||response.equals("notok")) {
                Toasteroid.show(activity,"Please Try Again", Toasteroid.STYLES.ERROR);
            }else if(response.equals("did")){
                Toasteroid.show(activity,"You Did Report This Before", Toasteroid.STYLES.INFO);
            }else if(response.equals("ok")){
                Toasteroid.show(activity,"Reported Successfully!", Toasteroid.STYLES.SUCCESS);
            }
        }else if(method.equals("postdelete")) {
            progressDialog.dismiss();
            if(response.equals("noway14")||response.equals("notok")) {
                Toasteroid.show(activity,"Please Try Again", Toasteroid.STYLES.ERROR);
            }else if(response.equals("did")){
                Toasteroid.show(activity,"You Did Request To Delete This Before", Toasteroid.STYLES.INFO);
            }else if(response.equals("ok")){
                Toasteroid.show(activity,"Delete Request Sent Successfully", Toasteroid.STYLES.SUCCESS);
            }
        }else if(method.equals("commentdelete")) {
            if(response.equals("noway15")||response.equals("notok")){
                progressDialog.dismiss();
                DisplayMassage("Error!","Please Try Again",0);
            }else if(response.equals("ok")){
                progressDialog.dismiss();
                BackgroundTask backgroundTask =new BackgroundTask(activity,0);
                backgroundTask.execute("getcomments",postidx,person,scroll);
            }
        }else if(method.equals("commentreport")) {
            progressDialog.dismiss();
            if(response.equals("noway16")||response.equals("notok")) {
                Toasteroid.show(activity,"Please Try Again", Toasteroid.STYLES.ERROR);
            }else if(response.equals("did")){
                Toasteroid.show(activity,"You Did Report This Before", Toasteroid.STYLES.INFO);
            }else if(response.equals("ok")){
                Toasteroid.show(activity,"Reported Successfully!", Toasteroid.STYLES.SUCCESS);
            }
        }else if(method.equals("suggestion")) {
            progressDialog.dismiss();

            if(response.equals("noway17")||response.equals("notok")||response.equals("empty")) {
                new MaterialDialog.Builder(ctx)
                        .title("Warning")
                        .content("Are You Sure?")
                        .positiveText("Send")
                        .negativeText("Cancel")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                NewPost.stat = 1;
                                NewPost.send.callOnClick();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }else {
                try {
                    JSONArray jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        s[i]=jsonobject.getString("title");
                    }
                }catch (Exception e){}
                new MaterialDialog.Builder(ctx)
                        .title("Warning!")
                        .content("Below Post(s) Have Similar Subject As Yours, Do You Want To Send Your Post?" + "\n" + "\n"+ s[0]+ "\n" + s[1]+ "\n" + s[2]+ "\n" + s[3])
                        .positiveText("Send")
                        .negativeText("Cancel")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                NewPost.stat = 1;
                                NewPost.send.callOnClick();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        }else if(method.equals("getmyposts")) {
            if (response.equals("noway18")) {
                errorlayout.setVisibility(View.VISIBLE);
                loadinglayout.setVisibility(View.GONE);
                Snackbar snackbar = Snackbar
                        .make(linearLayout, "Something Bad Has Happend", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Back", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                activity.finish();
                            }
                        });

                snackbar.show();
            } else if (!response.equals("empty")) {
                pullRefreshLayout = activity.findViewById(R.id.viewpost_swipeRefreshLayout);
                recyclerView = activity.findViewById(R.id.viewpost_recylcer);
                ArrayList<MyPostsModel> mypostsModels = new ArrayList<MyPostsModel>();
                mypostsModels.clear();
                try {
                    JSONArray jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++) {

                        MyPostsModel mypostsModel = new MyPostsModel();
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        mypostsModel.set_subject(jsonobject.getString("subject"));
                        mypostsModel.set_catagory(jsonobject.getString("catagory"));
                        mypostsModel.set_catagory_sub(jsonobject.getString("catagory_sub"));
                        mypostsModel.set_title(jsonobject.getString("title"));
                        mypostsModel.set_comment(jsonobject.getString("comment"));
                        mypostsModel.set_state(jsonobject.getString("state"));
                        mypostsModel.set_stat(jsonobject.getString("stat"));
                        mypostsModels.add(mypostsModel);
                    }

                    layoutManager = new LinearLayoutManager(activity);
                    adapter4 = new MyPostsAdapter(activity, mypostsModels);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter4);
                    loadinglayout.setVisibility(View.GONE);
                    errorlayout.setVisibility(View.GONE);
                    pullRefreshLayout.setRefreshing(false);
                    ViewPost.mstate=0;
                    activity.invalidateOptionsMenu();
                } catch (Exception e) {
                    Toasteroid.show(activity, "Please Try Again", Toasteroid.STYLES.ERROR);
                }

            }else if (response.equals("empty")){
                DisplayMassage("Error!","You Have Not Shared A Post Yet!",1);
            }
        }
    }
    private void DisplayMassage(String title, String message,final int stat){
        builder2.setTitle(title);
        builder2.setMessage(message);
        builder2.setPositiveButton("Close", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (stat==1){
                    activity.finish();
                }

            }
        });
        AlertDialog alertDialog = builder2.create();
        alertDialog.show();
    }
}
