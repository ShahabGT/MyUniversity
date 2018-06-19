package projects.shahabgt.com.myuniversity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.marcohc.toasteroid.Toasteroid;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.HashMap;
import java.util.Map;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;
import projects.shahabgt.com.myuniversity.classes.CustomDialogClass;
import projects.shahabgt.com.myuniversity.classes.Mysingleton;


public class Main extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce,konfetti;
    int konf=0;
    AlertDialog.Builder builder;
    ImageView menuOpener,newPost,explore,logo;
    SharedPreferences sp;
    Intent intent;
    ShowcaseView showcaseView;
    ProgressDialog progressDialog;
    String id;
    Drawer result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        doubleBackToExitPressedOnce = false;
        konfetti =false;

        sp= getApplicationContext().getSharedPreferences("logininfo",0);
        int help = sp.getInt("help",88);
        if(help!=1){
            showcase();
        }
        ndrawer();
        validate();

        menuOpener = findViewById(R.id.main_exit);
        newPost = findViewById(R.id.main_newPost);
        explore = findViewById(R.id.main_explore);
        logo = findViewById(R.id.main_logo);
        final KonfettiView konfettiView = findViewById(R.id.viewKonfetti);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (konfetti) {
                    konfetti = false;
                    konfettiView.build()
                            .addColors(getResources().getColor(R.color.logocolor1),getResources().getColor(R.color.logocolor2),getResources().getColor(R.color.logocolor3))
                            .setDirection(0.0, 359.0)
                            .setSpeed(1f, 5f)
                            .setFadeOutEnabled(true)
                            .setTimeToLive(4000L)
                            .addShapes(Shape.RECT, Shape.CIRCLE)
                            .addSizes(new Size(12, 5f))
                            .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                            .stream(1500, 15000L);
                    return;
                }
                konf++;
                if(konf==9) {
                    konfetti = true;
                }

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        konfetti=false;
                        konf=0;
                    }
                }, 4000);



            }
        });

        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main.this,ViewPost.class);
                intent.putExtra("what","viewpost");
                startActivity(intent);
                overridePendingTransition(R.anim.rtl,R.anim.ltr);
            }
        });

        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main.this,NewPost.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_to_right,R.anim.right_to_left);

            }
        });

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate_around_center_point);
        menuOpener.startAnimation(animation);
        menuOpener.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      //  drawer.openDrawer(Gravity.RIGHT);
                        result.openDrawer();
                    }
                });

    }


    @Override
    public void onBackPressed() {
        if(result.isDrawerOpen()){
            result.closeDrawer();
        }else{
        if (doubleBackToExitPressedOnce) {
            Main.this.finish();
            Toasteroid.dismiss();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toasteroid.show(Main.this,"Press Back Again To Exit!", Toasteroid.STYLES.INFO);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);}
    }

    public void ndrawer(){

         result = new DrawerBuilder()
                .withActivity(this)
                .withHasStableIds(true)
                 .withSelectedItem(-1)
                 .withDrawerGravity(Gravity.LEFT)
                 .withHeader(R.layout.nav_header)
                 .withHeaderDivider(true)
                 .withSliderBackgroundColor(Color.parseColor("#2a2a2a"))
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Usercp").withIcon(R.mipmap.ic_menu_cp).withSelectable(false).withIdentifier(1).withTextColor(Color.WHITE),
                        new PrimaryDrawerItem().withName("My Favorites").withIcon(R.mipmap.ic_menu_fav).withSelectable(false).withIdentifier(2).withTextColor(Color.WHITE),
                        new PrimaryDrawerItem().withName("My Posts").withIcon(R.mipmap.ic_menu_myposts).withSelectable(false).withIdentifier(3).withTextColor(Color.WHITE),
                        new PrimaryDrawerItem().withName("Messages").withIcon(R.mipmap.ic_menu_messages).withSelectable(false).withIdentifier(4).withTextColor(Color.WHITE),
                        new PrimaryDrawerItem().withName("Contact Us").withIcon(R.mipmap.ic_menu_contact).withSelectable(false).withIdentifier(5).withTextColor(Color.WHITE),
                        new PrimaryDrawerItem().withName("Share").withIcon(R.mipmap.ic_menu_share).withSelectable(false).withIdentifier(6).withTextColor(Color.WHITE),
                        new PrimaryDrawerItem().withName("Help").withIcon(R.mipmap.ic_menu_help).withSelectable(false).withIdentifier(7).withTextColor(Color.WHITE),
                        new PrimaryDrawerItem().withName("About").withIcon(R.mipmap.ic_menu_about).withSelectable(false).withIdentifier(8).withTextColor(Color.WHITE),
                        new PrimaryDrawerItem().withName("Clear Used Space").withIcon(R.mipmap.ic_menu_cach).withSelectable(false).withIdentifier(9).withTextColor(Color.WHITE),
                        new PrimaryDrawerItem().withName("Exit").withIcon(R.mipmap.ic_menu_exit).withSelectable(false).withIdentifier(10).withTextColor(Color.RED)
                )
              .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                  @Override
                  public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                      switch ((int)drawerItem.getIdentifier()){
                          case 1:
                              break;
                          case 2:
                              intent = new Intent(Main.this,ViewPost.class);
                              intent.putExtra("what","viewfav");
                              startActivity(intent);
                              break;
                          case 3:
                              intent = new Intent(Main.this,ViewPost.class);
                              intent.putExtra("what","myposts");
                              startActivity(intent);
                              break;
                          case 4:
                              intent = new Intent(Main.this,Messages.class);
                              startActivity(intent);
                              break;
                          case 5:
                              break;
                          case 6:
                              break;
                          case 7:
                           //   startActivity(new Intent(Main.this,HelpActivity.class));
                              showcase();
                              break;
                          case 8:
                        //      showcase();

                              CustomDialogClass cdd = new CustomDialogClass(Main.this);
                              cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                              cdd.show();
                              break;
                          case 9:
                              try {
                                  ImagePipeline imagePipeline = Fresco.getImagePipeline();
                                  imagePipeline.clearMemoryCaches();
                                  imagePipeline.clearDiskCaches();
                                  imagePipeline.clearCaches();
                                  Toasteroid.show(Main.this,"Space Cleared...", Toasteroid.STYLES.SUCCESS);
                              }catch (Exception e){
                                  Toasteroid.show(Main.this,"Please Try Again", Toasteroid.STYLES.ERROR);
                              }
                              break;
                          case 10:
                              logOut();
                              break;

                      }
                      return false;
                  }
              })
              .build();
    }
    public void showcase(){
        final Button button = new Button(this);
        button.setWidth(25);
        button.setHeight(25);
        button.setText("OK");
        button.setTextColor(Color.parseColor("#ffffff"));
        button.setBackground(getResources().getDrawable(R.drawable.shape4));
        final TextPaint contentPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        float textSize =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getDimension(R.dimen.abc_edit_text_inset_bottom_material), getResources().getDisplayMetrics());
        contentPaint.setTextSize(textSize);
        contentPaint.setColor(Color.WHITE);
        contentPaint.setTextAlign(Paint.Align.LEFT);
        showcaseView = new ShowcaseView.Builder(Main.this)
                .setTarget(new ViewTarget(R.id.main_newPost,this))
                .withHoloShowcase()
                .blockAllTouches()
                .replaceEndButton(button)
                .setContentTextPaint(contentPaint)
                .setContentText("Use This To Send New Posts")
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showcaseView.hide();
                        showcase2();
                    }
                })
                .build();
    }
    public void showcase2(){
        final Button button = new Button(this);
        button.setWidth(25);
        button.setHeight(25);
        button.setText("OK");
        button.setTextColor(Color.parseColor("#ffffff"));
        button.setBackground(getResources().getDrawable(R.drawable.shape4));
        final TextPaint contentPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        float textSize =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getDimension(R.dimen.abc_edit_text_inset_bottom_material), getResources().getDisplayMetrics());
        contentPaint.setTextSize(textSize);
        contentPaint.setColor(Color.WHITE);
        contentPaint.setTextAlign(Paint.Align.LEFT);
        showcaseView = new ShowcaseView.Builder(Main.this)
                .setTarget(new ViewTarget(R.id.main_explore,this))
                .withHoloShowcase()
                .blockAllTouches()
                .replaceEndButton(button)
                .setContentTextPaint(contentPaint)
                .setContentText("Use This To Explore Posts")
                 .setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         showcaseView.hide();
                         showcase3();
                     }
                 })
                .build();
    }
    public void showcase3(){
        final Button button = new Button(this);
        button.setWidth(25);
        button.setHeight(25);
        button.setText("OK");
        button.setTextColor(Color.parseColor("#ffffff"));
        button.setBackground(getResources().getDrawable(R.drawable.shape4));
        final TextPaint contentPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        float textSize =  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getDimension(R.dimen.abc_edit_text_inset_bottom_material), getResources().getDisplayMetrics());
        contentPaint.setTextSize(textSize);
        contentPaint.setColor(Color.WHITE);
        contentPaint.setTextAlign(Paint.Align.LEFT);
        showcaseView = new ShowcaseView.Builder(Main.this)
                .setTarget(new ViewTarget(R.id.main_exit,this))
                .withHoloShowcase()
                .blockAllTouches()
                .replaceEndButton(button)
                .setContentTextPaint(contentPaint)
                .setContentText("Use This For More Options")
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor e=sp.edit();
                        e.putInt("help",1);
                        e.apply();
                        showcaseView.hide();
                    }
                })
                .build();
    }
    private boolean checknet(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        return activeInfo != null && activeInfo.isConnected();
    }
    private void logOut(){

        if(checknet()){
            progressDialog= new ProgressDialog(Main.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
            final String url=getResources().getString(R.string.url)+"logout.php";
            StringRequest loginrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.equals("ok"))
                    {
                        progressDialog.dismiss();
                        SharedPreferences.Editor e=sp.edit();
                        e.putString("id","");
                        e.putString("password","");
                        e.putInt("stat",0);
                        e.apply();
                        startActivity(new Intent(Main.this,Login.class));
                        Main.this.finish();
                    }else if(response.equals("notok")){
                        progressDialog.dismiss();
                        Toasteroid.show(Main.this,"Please Try Again!", Toasteroid.STYLES.ERROR);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toasteroid.show(Main.this,"Please Try Again!", Toasteroid.STYLES.ERROR);
                }
            }){

                @Override
                protected Map<String, String> getParams() {
                    Map<String,String> params = new HashMap<String, String>();
                    String idtext =sp.getString("id","");
                    params.put("id",idtext);
                    return params;
                }
            };
            Mysingleton.getmInstance(Main.this).addToRequestque(loginrequest);

        }else{
            Toasteroid.show(Main.this,"Check Your Internet Connection", Toasteroid.STYLES.ERROR);
        }
    }
    private void validate(){
        final String url=getResources().getString(R.string.url)+"getdeviceid.php";
        id = sp.getString("id","");
        StringRequest validaterequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String deviceid= sp.getString("deviceid","");
                if(!response.equals(deviceid)){
                    builder = new AlertDialog.Builder(Main.this);
                    builder.setTitle("Error");
                    builder.setMessage("This Account Is Loged On From Another Device");
                    builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor e=sp.edit();
                            e.putString("id","");
                            e.putString("password","");
                            e.putInt("stat",0);
                            e.apply();
                            startActivity(new Intent(Main.this,Login.class));
                            Main.this.finish();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("id",id);
                return params;
            }
        };
        Mysingleton.getmInstance(Main.this).addToRequestque(validaterequest);
    }
}
