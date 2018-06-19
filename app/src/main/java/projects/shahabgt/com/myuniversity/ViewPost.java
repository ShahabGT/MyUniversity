package projects.shahabgt.com.myuniversity;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.baoyz.widget.PullRefreshLayout;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.marcohc.toasteroid.Toasteroid;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import projects.shahabgt.com.myuniversity.adapters.ViewpostAdapter;
import projects.shahabgt.com.myuniversity.classes.BackgroundTask;

public class ViewPost extends AppCompatActivity {

    RecyclerView recyclerView;
    public static ViewpostAdapter adapter;
    ImageView back;
    RecyclerView.LayoutManager layoutManager;
    LinearLayout linearLayout,errorlayout,loadinglayout;
    TextView toolbar_text;
    PullRefreshLayout pullRefreshLayout;
    SimpleDraweeView loadingimg,nointernet;
    MaterialSearchView searchView;
    MenuItem item;
    public static int mstate, fstate;
    Toolbar toolbar;
    FrameLayout toolbarcontainer;
    FloatingActionButton fab1,fab2,fab3;
    FloatingActionMenu fab;
    int ws2;
    Bundle bundle;
    SharedPreferences sp;
    String person;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(ViewPost.this);
        setContentView(R.layout.activity_view_post);
        toolbarcontainer = findViewById(R.id.toolbar_container);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar_text = findViewById(R.id.toolbar_text);
        bundle = getIntent().getExtras();

        sp= getApplicationContext().getSharedPreferences("logininfo",0);
        person= sp.getString("id","");

        fab = findViewById(R.id.fab_menu);
        fab.setMenuButtonColorNormal(Color.parseColor("#1d4054"));
        fab.setMenuButtonColorPressed(Color.parseColor("#1d4054"));
        fab1 = findViewById(R.id.fab_item1);
        fab2 = findViewById(R.id.fab_item2);
        fab3 = findViewById(R.id.fab_item3);
        fab2.setEnabled(false);
        fab3.setEnabled(false);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(ViewPost.this)
                        .title("Select Subject")
                        .items(R.array.subjectall)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                fab1.setLabelText(text.toString());
                                fab2.setLabelText("Select Department");
                                fab2.setEnabled(true);
                                fab3.setLabelText("Select SubDepartment");
                                fab3.setEnabled(false);
                                return true;
                            }
                        })
                        .show();
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(ViewPost.this)
                        .title("Select Department")
                        .items(R.array.catagoryall)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                fab3.setLabelText("Select SubDepartment");
                                ws2 = which;
                                if(which==0){
                                    adapter.filter(fab1.getLabelText(),"all","all");
                                    fab3.setLabelText("all");
                                    fab3.setEnabled(false);
                                }else{
                                    fab3.setEnabled(true);
                                }

                                fab2.setLabelText(text.toString());
                                return true;
                            }
                        })
                        .show();
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog.Builder materialDialog = new MaterialDialog.Builder(ViewPost.this);
                materialDialog.title("Select SubDepartment");
                materialDialog.itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        fab3.setLabelText(text.toString());
                        adapter.filter(fab1.getLabelText(),fab2.getLabelText(),fab3.getLabelText());
                        return true;
                    }
                });
                switch (ws2){
                    case 0:
                        fab3.setLabelText("all");
                        break;
                    case 1:
                        materialDialog.items(R.array.catagory0all);
                        break;
                    case 2:
                        materialDialog.items(R.array.catagory1all);
                        break;
                    case 3:
                        materialDialog.items(R.array.catagory2all);
                        break;
                    case 4:
                        materialDialog.items(R.array.catagory3all);
                        break;
                    case 5:
                        materialDialog.items(R.array.catagory4all);
                        break;
                }
                materialDialog.show();
            }
        });

        recyclerView= findViewById(R.id.viewpost_recylcer);

        linearLayout = findViewById(R.id.viewpost_layout);
        errorlayout = findViewById(R.id.viewpost_error);
        pullRefreshLayout = findViewById(R.id.viewpost_swipeRefreshLayout);


        layoutManager=new LinearLayoutManager(this);

        loadinglayout = findViewById(R.id.viewpost_loadinglayout);
        loadingimg = findViewById(R.id.viewpost_loadingimg);
        Uri loadinguri= Uri.parse("asset:///loading.gif");
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(loadinguri)
                .setAutoPlayAnimations(true)
                .build();
        loadingimg.setController(controller);
        loadinglayout.setVisibility(View.VISIBLE);

        nointernet = findViewById(R.id.viewpost_nointernet);
        Uri noinnetneturi= Uri.parse("asset:///nointernet.gif");
        DraweeController controller2 = Fresco.newDraweeControllerBuilder()
                .setUri(noinnetneturi)
                .setAutoPlayAnimations(true)
                .build();
        nointernet.setController(controller2);

        back = findViewById(R.id.toolbar_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPost.this.finish();
            }
        });

        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                    adapter.search(query);


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                    adapter.search(newText);

                return true;
            }
        });
        String what = bundle.getString("what");
        if(what.equals("viewpost")) {
            fstate=1;
            invalidateOptionsMenu();
            toolbar_text.setText("Posts");
            pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (checknet()) {
                        fab1.setLabelText("Select Subject");
                        fab1.setEnabled(true);
                        fab2.setLabelText("Select Department");
                        fab2.setEnabled(false);
                        fab3.setLabelText("Select SubDepartment");
                        fab3.setEnabled(false);
                        BackgroundTask backgroundTask = new BackgroundTask(ViewPost.this, 0);
                        backgroundTask.execute("getposts",getResources().getString(R.string.url)+"getposts.php");
                    } else {
                        loadinglayout.setVisibility(View.VISIBLE);
                        snackError();
                        Toasteroid.show(ViewPost.this, "Check Your Internet Connection", Toasteroid.STYLES.ERROR);
                    }
                }
            });
            if (checknet()) {

                BackgroundTask backgroundTask = new BackgroundTask(ViewPost.this, 0);
                backgroundTask.execute("getposts",getResources().getString(R.string.url)+"getposts.php");

            } else {
                snackError();
                Toasteroid.show(ViewPost.this, "Check Your Internet Connection", Toasteroid.STYLES.ERROR);
            }
        }else if (what.equals("viewfav")){
            fstate=0;
            invalidateOptionsMenu();
            toolbar_text.setText("My Favorites");
            pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (checknet()) {
                        BackgroundTask backgroundTask = new BackgroundTask(ViewPost.this, 0);
                        backgroundTask.execute("getfavposts",person);
                    } else {
                        loadinglayout.setVisibility(View.VISIBLE);
                        snackError2();
                        Toasteroid.show(ViewPost.this, "Check Your Internet Connection", Toasteroid.STYLES.ERROR);
                    }
                }
            });
            if (checknet()) {

                BackgroundTask backgroundTask = new BackgroundTask(ViewPost.this, 0);
                backgroundTask.execute("getfavposts",person);

            } else {
                snackError2();
                Toasteroid.show(ViewPost.this, "Check Your Internet Connection", Toasteroid.STYLES.ERROR);
            }
        }else if (what.equals("myposts")){
            mstate=0;
            invalidateOptionsMenu();
            fab.setVisibility(View.INVISIBLE);
            fab.setEnabled(false);
            toolbar_text.setText("My Posts");
            pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if (checknet()) {
                        BackgroundTask backgroundTask = new BackgroundTask(ViewPost.this, 0);
                        backgroundTask.execute("getmyposts",person);
                    } else {
                        loadinglayout.setVisibility(View.VISIBLE);
                        snackError3();
                        Toasteroid.show(ViewPost.this, "Check Your Internet Connection", Toasteroid.STYLES.ERROR);
                    }
                }
            });
            if (checknet()) {

                BackgroundTask backgroundTask = new BackgroundTask(ViewPost.this, 0);
                backgroundTask.execute("getmyposts",person);

            } else {
                snackError3();
                Toasteroid.show(ViewPost.this, "Check Your Internet Connection", Toasteroid.STYLES.ERROR);
            }
        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                super.onScrolled(recyclerView, dx, dy);

                if (dy >0) {

                    if (fab.isShown()) {
                        fab.hideMenuButton(true);
                    }
                }
                else if (dy <0) {

                        fab.showMenuButton(true);

                }
            }
        });
    }

    @Override
    protected void onResume() {
        searchView.closeSearch();
        fab.close(true);
        super.onResume();
    }
    @Override
    public void onBackPressed() {
        if(errorlayout.getVisibility() == View.VISIBLE)
        {
            ViewPost.this.finish();
        }
        else if(searchView.isSearchOpen()) {
            searchView.closeSearch();
        }else if(fab.isOpened()){
            fab.close(true);
        }else  {
            ViewPost.this.finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        if(mstate==0)
        {
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(false);
        }
        else if(fstate==0)
         {   menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(false);
         }


        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_hot:
                if (checknet()) {
                    BackgroundTask backgroundTask = new BackgroundTask(ViewPost.this, 1);
                    backgroundTask.execute("getposts",getResources().getString(R.string.url)+"gethotposts.php");
                } else {
                    loadinglayout.setVisibility(View.VISIBLE);
                    snackError();
                    Toasteroid.show(ViewPost.this, "Check Your Internet Connection", Toasteroid.STYLES.ERROR);
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    private boolean checknet(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        return activeInfo != null && activeInfo.isConnected();
    }
    private void snackError(){
        mstate=0;
        invalidateOptionsMenu();
        loadinglayout.setVisibility(View.GONE);
        errorlayout.setVisibility(View.VISIBLE);
        Snackbar snackbar = Snackbar
                .make(linearLayout, "Something Bad Happend", Snackbar.LENGTH_INDEFINITE)
                .setAction("Try Again", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       if(checknet()) {
                           loadinglayout.setVisibility(View.VISIBLE);
                           errorlayout.setVisibility(View.GONE);
                           BackgroundTask backgroundTask = new BackgroundTask(ViewPost.this, 0);
                           backgroundTask.execute("getposts",getResources().getString(R.string.url)+"getposts.php");
                       }else {
                           snackError();
                       }
                    }
                });

        snackbar.show();
    }
    private void snackError2(){
        mstate=0;
        invalidateOptionsMenu();
        loadinglayout.setVisibility(View.GONE);
        errorlayout.setVisibility(View.VISIBLE);
        Snackbar snackbar = Snackbar
                .make(linearLayout, "Something Bad Happend", Snackbar.LENGTH_INDEFINITE)
                .setAction("Try Again", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(checknet()) {
                            loadinglayout.setVisibility(View.VISIBLE);
                            errorlayout.setVisibility(View.GONE);
                            BackgroundTask backgroundTask = new BackgroundTask(ViewPost.this, 0);
                            backgroundTask.execute("getfavposts",person);
                        }else {
                            snackError();
                        }
                    }
                });

        snackbar.show();
    }
    private void snackError3(){
        mstate=0;
        invalidateOptionsMenu();
        loadinglayout.setVisibility(View.GONE);
        errorlayout.setVisibility(View.VISIBLE);
        Snackbar snackbar = Snackbar
                .make(linearLayout, "Something Bad Happend", Snackbar.LENGTH_INDEFINITE)
                .setAction("Try Again", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(checknet()) {
                            loadinglayout.setVisibility(View.VISIBLE);
                            errorlayout.setVisibility(View.GONE);
                            BackgroundTask backgroundTask = new BackgroundTask(ViewPost.this, 0);
                            backgroundTask.execute("getmyposts",person);
                        }else {
                            snackError();
                        }
                    }
                });

        snackbar.show();
    }
}
