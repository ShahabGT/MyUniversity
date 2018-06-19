package projects.shahabgt.com.myuniversity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import projects.shahabgt.com.myuniversity.adapters.MessagesAdapter;
import projects.shahabgt.com.myuniversity.classes.DatabaseOperations;

public class Messages extends AppCompatActivity {

    ImageView back;
    TextView toolbar_text;
    String title,message;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar_text = findViewById(R.id.toolbar_text);
        toolbar_text.setText("Messages");

        back= findViewById(R.id.toolbar_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Messages.this.finish();
            }
        });
        loadData(Messages.this);

    }

    @Override
    public void onBackPressed() {
        sp= getApplicationContext().getSharedPreferences("logininfo",0);
        int stat = sp.getInt("stat",0);
        if(stat!=1)
        {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }else{
            super.onBackPressed();
        }
    }

    public static void loadData(Activity activity){
        DatabaseOperations db = new DatabaseOperations(activity);
        RecyclerView recyclerView = activity.findViewById(R.id.messages_recylcer);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        MessagesAdapter adapter = new MessagesAdapter(activity,activity.getApplicationContext(),db.getData());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        db.close();
    }
}
