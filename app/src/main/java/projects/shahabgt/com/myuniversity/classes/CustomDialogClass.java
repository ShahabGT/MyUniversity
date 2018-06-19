package projects.shahabgt.com.myuniversity.classes;


import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import projects.shahabgt.com.myuniversity.R;

public class CustomDialogClass extends Dialog implements View.OnClickListener {
    public Activity c;
    TextView versionname;
    String versionName="";
    SimpleDraweeView loadingimg;

    public CustomDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Fresco.initialize(c);
        setContentView(R.layout.customdialog_layout);
        versionname = findViewById(R.id.customdialog_versionname);
        try {
            versionName = c.getPackageManager().getPackageInfo(c.getPackageName(), 0).versionName;
            versionname.setText(" version "+versionName);
        }catch (Exception e){}

        loadingimg = findViewById(R.id.customdialog_loadingimg);
        Uri loadinguri= Uri.parse("asset:///shahabgt.gif");
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(loadinguri)
                .setAutoPlayAnimations(true)
                .build();
        loadingimg.setController(controller);

    }


    @Override
    public void onClick(View v) {
        dismiss();
    }
}
