package projects.shahabgt.com.myuniversity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.marcohc.toasteroid.Toasteroid;
import com.shehabic.droppy.DroppyClickCallbackInterface;
import com.shehabic.droppy.DroppyMenuItem;
import com.shehabic.droppy.DroppyMenuPopup;
import com.shehabic.droppy.animations.DroppyFadeInAnimation;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import projects.shahabgt.com.myuniversity.classes.BackgroundTask;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class NewPost extends AppCompatActivity {


    static final int gallery =20;
    static final int camera =50;
    ImageView back,image,image2,image3,image4;
    LinearLayout select1,select2,select3;
    public static LinearLayout send;
    TextView toolbar_text,selectTxt1,selectTxt2,selectTxt3;
    LinearLayout selectlin1,selectlin2,selectlin3;
    AlertDialog.Builder builder,builder2;
    EditText title,comment;
    Bitmap bitmap=null,bitmap2=null,bitmap3=null,bitmap4=null;
    Uri uri;
    Uri fileUri;
    int ws2;
    int num;
    public static int stat=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_new_post);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar_text = findViewById(R.id.toolbar_text);
        toolbar_text.setText("New Post");

        builder = new AlertDialog.Builder(NewPost.this);
        builder2 = new AlertDialog.Builder(NewPost.this);

        title= findViewById(R.id.newpost_title);
        comment= findViewById(R.id.newpost_comment);

        selectTxt1 = findViewById(R.id.newpost_selectText1);
        selectTxt2 = findViewById(R.id.newpost_selectText2);
        selectTxt3 = findViewById(R.id.newpost_selectText3);
        selectlin1 = findViewById(R.id.newpost_selectLin1);
        selectlin2 = findViewById(R.id.newpost_selectLin2);
        selectlin3 = findViewById(R.id.newpost_selectLin3);

        back= findViewById(R.id.toolbar_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewPost.this.finish();
            }
        });

        select1= findViewById(R.id.newpost_select1);
        DroppyMenuPopup.Builder droppyBuilder = new DroppyMenuPopup.Builder(NewPost.this, select1);
        droppyBuilder.addMenuItem(new DroppyMenuItem("report a problem"))
                .addSeparator()
                .addMenuItem(new DroppyMenuItem("suggestion"))
                .addSeparator()
                .addMenuItem(new DroppyMenuItem("strengths points"))
                .setPopupAnimation(new DroppyFadeInAnimation());

        droppyBuilder.setOnClick(new DroppyClickCallbackInterface() {
            @Override
            public void call(View v, int id) {
                selectlin1.setVisibility(View.VISIBLE);
                String text="";
                switch (id){
                    case 0:
                        text="report a problem";
                        break;
                    case 1:
                        text="suggestion";
                        break;
                    case 2:
                        text="strengths points";
                        break;
                }
                selectTxt1.setText(text);
            }
        });
        DroppyMenuPopup droppyMenu = droppyBuilder.build();

        select2= findViewById(R.id.newpost_select2);
        DroppyMenuPopup.Builder droppyBuilder2 = new DroppyMenuPopup.Builder(this, select2);
       final DroppyMenuPopup droppyMenu2 = droppyBuilder2.fromMenu(R.menu.menu_select2)
                .triggerOnAnchorClick(false)
                .setOnClick(new DroppyClickCallbackInterface() {
                    @Override
                    public void call(View v, int id) {
                        String text="";
                        switch (id){
                            case R.id.drop1:
                                text=getResources().getString(R.string.m0);
                                ws2 = R.menu.menu_catagory0;
                                break;
                            case R.id.drop2:
                                text=getResources().getString(R.string.m1);
                                ws2 = R.menu.menu_catagory1;
                                break;
                            case R.id.drop3:
                                text=getResources().getString(R.string.m2);
                                ws2 = R.menu.menu_catagory2;
                                break;
                            case R.id.drop4:
                                text=getResources().getString(R.string.m3);
                                ws2 = R.menu.menu_catagory3;
                                break;
                            case R.id.drop5:
                                text=getResources().getString(R.string.m4);
                                ws2 = R.menu.menu_catagory4;
                                break;
                        }
                        selectlin2.setVisibility(View.VISIBLE);
                        selectTxt2.setText(text);
                        selectlin3.setVisibility(View.GONE);
                        selectTxt3.setText("");
                        select3.setEnabled(true);

                    }
                })
    .setPopupAnimation(new DroppyFadeInAnimation())
                .build();

        select2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                droppyMenu2.show();
            }
        });

        select3= findViewById(R.id.newpost_select3);
        select3.setEnabled(false);


        select3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DroppyMenuPopup.Builder droppyBuilder3 = new DroppyMenuPopup.Builder(NewPost.this, select3);
                final DroppyMenuPopup droppyMenu3 = droppyBuilder3.fromMenu(ws2)
                        .triggerOnAnchorClick(false)
                        .setOnClick(new DroppyClickCallbackInterface() {
                            @Override
                            public void call(View v, int id) {
                                selectlin3.setVisibility(View.VISIBLE);
                                selectTxt3.setText(menu(id,ws2));

                            }
                        })
                        .setPopupAnimation(new DroppyFadeInAnimation())
                        .setXOffset(5)
                        .setYOffset(5)
                        .build();

                droppyMenu3.show();
            }
        });

        send= findViewById(R.id.newpost_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        try {
                            InputMethodManager inputManager = (InputMethodManager)
                                    getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                        }catch (Exception e){}
                        if(title.getText().toString().isEmpty()||comment.getText().toString().isEmpty()||selectTxt1.getText().toString().isEmpty()||selectTxt2.getText().toString().isEmpty()||selectTxt3.getText().toString().isEmpty()){
                            Toasteroid.show(NewPost.this,"Please Fill Out The Form", Toasteroid.STYLES.INFO);
                        }else {
                            if(stat==1){

                                String pic = "", pic2 = "", pic3 = "", pic4 = "";
                                if (bitmap != null) { pic = imgToString(bitmap);}
                                if (bitmap2 != null) { pic2 = imgToString(bitmap2);}
                                if (bitmap3 != null) {pic3 = imgToString(bitmap3);}
                                if (bitmap4 != null) {pic4 = imgToString(bitmap4);}

                                if (checknet()) {
                                    BackgroundTask backgroundTask = new BackgroundTask(NewPost.this, 1);
                                    backgroundTask.execute("insert", pic, pic2, pic3, pic4, selectTxt1.getText().toString(), selectTxt2.getText().toString(), selectTxt3.getText().toString(), title.getText().toString(), comment.getText().toString());
                                } else {
                                    Toasteroid.show(NewPost.this, "Check Your Internet Connection", Toasteroid.STYLES.INFO);}

                             }else if(stat==0){
                                if (checknet()) {
                                BackgroundTask backgroundTask = new BackgroundTask(NewPost.this, 1);
                                backgroundTask.execute("suggestion",title.getText().toString());
                                } else {
                                    Toasteroid.show(NewPost.this, "Check Your Internet Connection", Toasteroid.STYLES.INFO);}
                            }
                        }
                    }

        });
        image = findViewById(R.id.newpost_img);
        image2 = findViewById(R.id.newpost_img2);
        image3 = findViewById(R.id.newpost_img3);
        image4 = findViewById(R.id.newpost_img4);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num=1;
                new MaterialDialog.Builder(NewPost.this)
                        .title("Select An Image")
                        .items("From Gallery","From Camera")
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                if(which==0)
                                {
                                    gallerySelect();
                                }else{
                                    captureImage();
                                }
                                return true;
                            }
                        })
                        .show();
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num=2;
                new MaterialDialog.Builder(NewPost.this)
                        .title("Select An Image")
                        .items("From Gallery","From Camera")
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                if(which==0)
                                {
                                    gallerySelect();
                                }else{
                                    captureImage();
                                }
                                return true;
                            }
                        })
                        .show();
            }
        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num=3;
                new MaterialDialog.Builder(NewPost.this)
                        .title("Select An Image")
                        .items("From Gallery","From Camera")
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                if(which==0)
                                {
                                    gallerySelect();
                                }else{
                                    captureImage();
                                }
                                return true;
                            }
                        })
                        .show();
            }
        });
        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num=4;
                new MaterialDialog.Builder(NewPost.this)
                        .title("Select An Image")
                        .items("From Gallery","From Camera")
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                if(which==0)
                                {
                                    gallerySelect();
                                }else{
                                    captureImage();
                                }
                                return true;
                            }
                        })
                        .show();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
        {
            switch (requestCode){
                case gallery:
                    uri = data.getData();
                    CropImage.activity(uri)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(1,1)
                            .setActivityTitle("Edit")
                            .setCropShape(CropImageView.CropShape.RECTANGLE)
                            .setRequestedSize(1000,1000)
                            .start(this);
                    break;
                case camera:
                    CropImage.activity(fileUri)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(1,1)
                            .setActivityTitle("Edit")
                            .setCropShape(CropImageView.CropShape.RECTANGLE)
                            .setRequestedSize(1000,1000)
                            .start(this);
                    break;
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try{
                switch (num)
                {
                    case 1:
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),resultUri);
                        image.setImageBitmap(bitmap);
                        image2.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(),resultUri);
                        image2.setImageBitmap(bitmap2);
                        image3.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(),resultUri);
                        image3.setImageBitmap(bitmap3);
                        image4.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        bitmap4= MediaStore.Images.Media.getBitmap(getContentResolver(),resultUri);
                        image4.setImageBitmap(bitmap4);
                        break;
                }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
    private boolean checknet(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        return activeInfo != null && activeInfo.isConnected();
    }
    private String imgToString (Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,75,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, camera);
    }
    private void gallerySelect(){
                Intent galleryPickerIntent = new Intent();
                galleryPickerIntent.setType("image/*");
                galleryPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryPickerIntent, "Select An Image"), gallery);
    }
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
    private static File getOutputMediaFile(int type) {
        String IMAGE_DIRECTORY_NAME ="MyUniversity";

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
    private String menu(int id,int ws2){
        String text="";
        switch (ws2){
            case R.menu.menu_catagory0:
                switch (id){
                    case R.id.drop00:
                        text=getResources().getString(R.string.m00);
                        break;
                    case R.id.drop01:
                        text=getResources().getString(R.string.m01);
                        break;
                    case R.id.drop02:
                        text=getResources().getString(R.string.m02);
                        break;
                    case R.id.drop03:
                        text=getResources().getString(R.string.m03);
                        break;
                    case R.id.drop04:
                        text=getResources().getString(R.string.m04);
                        break;
                    case R.id.drop05:
                        text=getResources().getString(R.string.m05);
                        break;
                    case R.id.drop06:
                        text=getResources().getString(R.string.m06);
                        break;
                    case R.id.drop07:
                        text=getResources().getString(R.string.m07);
                        break;
                    case R.id.drop08:
                        text=getResources().getString(R.string.m08);
                        break;
                    case R.id.drop09:
                        text=getResources().getString(R.string.m09);
                        break;
                    case R.id.drop010:
                        text=getResources().getString(R.string.m010);
                        break;
                }

                break;
            case  R.menu.menu_catagory1:
                switch (id){
                    case R.id.drop10:
                        text=getResources().getString(R.string.m10);
                        break;
                    case R.id.drop11:
                        text=getResources().getString(R.string.m11);
                        break;
                    case R.id.drop12:
                        text=getResources().getString(R.string.m12);
                        break;
                    case R.id.drop13:
                        text=getResources().getString(R.string.m13);
                        break;
                    case R.id.drop14:
                        text=getResources().getString(R.string.m14);
                        break;
                    case R.id.drop15:
                        text=getResources().getString(R.string.m15);
                        break;
                }
                break;
            case  R.menu.menu_catagory2:
                switch (id){
                    case R.id.drop20:
                        text=getResources().getString(R.string.m20);
                        break;
                    case R.id.drop21:
                        text=getResources().getString(R.string.m21);
                        break;
                    case R.id.drop22:
                        text=getResources().getString(R.string.m22);
                        break;
                    case R.id.drop23:
                        text=getResources().getString(R.string.m23);
                        break;
                    case R.id.drop24:
                        text=getResources().getString(R.string.m24);
                        break;
                    case R.id.drop25:
                        text=getResources().getString(R.string.m25);
                        break;
                }

                break;
            case  R.menu.menu_catagory3:
                switch (id){
                    case R.id.drop30:
                        text=getResources().getString(R.string.m30);
                        break;
                    case R.id.drop31:
                        text=getResources().getString(R.string.m31);
                        break;
                    case R.id.drop32:
                        text=getResources().getString(R.string.m32);
                        break;
                    case R.id.drop33:
                        text=getResources().getString(R.string.m33);
                        break;
                    case R.id.drop34:
                        text=getResources().getString(R.string.m34);
                        break;
                }

                break;
            case  R.menu.menu_catagory4:
                switch (id){
                    case R.id.drop40:
                        text=getResources().getString(R.string.m40);
                        break;
                    case R.id.drop41:
                        text=getResources().getString(R.string.m41);
                        break;
                }

                break;
        }
        return text;
    }
}
