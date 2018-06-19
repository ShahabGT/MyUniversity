package projects.shahabgt.com.myuniversity.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.marcohc.toasteroid.Toasteroid;

import java.util.ArrayList;

import projects.shahabgt.com.myuniversity.R;
import projects.shahabgt.com.myuniversity.classes.BackgroundTask;
import projects.shahabgt.com.myuniversity.models.CommentsModel;


public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {
    private Uri uri;
    private Context ctx;
    private ArrayList<CommentsModel> arrayList;
    public CommentsAdapter(Context ctx,ArrayList<CommentsModel> arrayList){
        this.arrayList=arrayList;
        this.ctx=ctx;

    }


    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_items,parent,false);
        CommentsViewHolder commentsViewHolder = new CommentsViewHolder(view,ctx);
        return commentsViewHolder;


    }

    @Override
    public void onBindViewHolder(CommentsViewHolder holder, int position) {
        CommentsModel commentsModel = arrayList.get(position);
        holder.ccmid.setText(commentsModel.get_cmid());
        holder.cmperson.setText(commentsModel.get_cmperson());
        if(commentsModel.get_cmperson().equals(holder.person))
        {   holder.report.setVisibility(View.GONE);
            holder.delete.setVisibility(View.VISIBLE);
        }else {
            holder.report.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.GONE);
        }
        holder.ccoment.setText(commentsModel.get_comment());
        holder.ctime.setText(commentsModel.get_time());
        holder.cname.setText(commentsModel.get_name());
        holder.cavatar.setImageURI(uri = Uri.parse(ctx.getResources().getString(R.string.avatar_url)+commentsModel.get_avatar()+".jpg"));
        holder.clike.setText(commentsModel.get_likes());
        String did = commentsModel.get_didlike();
        holder.heart.setLiked(Boolean.parseBoolean(did));



    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public static class CommentsViewHolder extends RecyclerView.ViewHolder
    {

        TextView cname,ccoment,ctime,clike,ccmid,cmperson;
        SimpleDraweeView cavatar;
        LikeButton heart;
        SharedPreferences sp;
        String person,postid;
        ImageView delete,report;

        Context ctx;
        public CommentsViewHolder(View view, Context context){
            super(view);
            this.ctx=context;
            heart = view.findViewById(R.id.comments_items_heart);
            ccmid= view.findViewById(R.id.comments_items_cmid);
            cmperson= view.findViewById(R.id.comments_items_cmperson);
            cname= view.findViewById(R.id.comments_items_name);
            clike= view.findViewById(R.id.comments_items_likes);
            ccoment= view.findViewById(R.id.comments_items_comment);
            ctime= view.findViewById(R.id.comments_items_time);
            cavatar= view.findViewById(R.id.comments_items_avatar);
            report = view.findViewById(R.id.comments_items_report);
            delete = view.findViewById(R.id.comments_items_delete);
            sp= ctx.getSharedPreferences("logininfo",0);
            person = sp.getString("id", "");
            postid = sp.getString("postid", "");

            heart.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    if(checknet(ctx)){
                BackgroundTask backgroundTask = new BackgroundTask(ctx, 0);
                backgroundTask.execute("doclike",postid,ccmid.getText().toString(),person,"donot");
                }else {
                    Toasteroid.show((Activity)ctx,"Check Your Internet Connection!", Toasteroid.STYLES.ERROR);
                }
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    if(checknet(ctx)){
                    BackgroundTask backgroundTask = new BackgroundTask(ctx, 0);
                    backgroundTask.execute("doclike",postid,ccmid.getText().toString(),person,"donot");
                }else {
                    Toasteroid.show((Activity)ctx,"Check Your Internet Connection!", Toasteroid.STYLES.ERROR);
                }
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new MaterialDialog.Builder(ctx)
                            .title("Warning")
                            .content("Are You Sure?")
                            .positiveText("Delete")
                            .negativeText("Cancel")
                            .positiveColor(Color.parseColor("#4caf50"))
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    if(checknet(ctx)){
                                    BackgroundTask backgroundTask = new BackgroundTask(ctx, 1);
                                    backgroundTask.execute("commentdelete",ccmid.getText().toString(),postid,person,"donot");
                                    }else {
                                        dialog.dismiss();
                                        Toasteroid.show((Activity)ctx,"Check Your Internet Connection!", Toasteroid.STYLES.ERROR);
                                    }
                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            }).show();

                }
            });
            report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new MaterialDialog.Builder(ctx)
                            .title("Report")
                            .inputRangeRes(5, 100, R.color.red)
                            .content("Please Write Your Reason:")
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
                                    if(checknet(ctx)){
                                        BackgroundTask backgroundTask = new BackgroundTask(ctx, 1);
                                        backgroundTask.execute("commentreport", postid, person,input.toString(),"donot");
                                    }else {
                                        dialog.dismiss();
                                        Toasteroid.show((Activity)ctx,"Check Your Internet Connection!", Toasteroid.STYLES.ERROR);
                                    }

                                }
                            }).show();
                }
            });
        }
    }
    private static boolean checknet(Context ctx){
        ConnectivityManager connMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        return activeInfo != null && activeInfo.isConnected();
    }
}