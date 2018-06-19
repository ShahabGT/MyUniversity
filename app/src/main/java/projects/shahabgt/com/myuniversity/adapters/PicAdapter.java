package projects.shahabgt.com.myuniversity.adapters;


import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import projects.shahabgt.com.myuniversity.R;
import projects.shahabgt.com.myuniversity.models.PicModel;


public class PicAdapter extends RecyclerView.Adapter<PicAdapter.PicViewHolder> {

    private Uri uri;
    private Context ctx;
    private ArrayList<PicModel> arrayList;


    public PicAdapter(Context ctx,ArrayList<PicModel> arrayList){
        this.arrayList=arrayList;
        this.ctx=ctx;
    }

    @Override
    public PicAdapter.PicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pic_items, parent, false);
            PicAdapter.PicViewHolder picViewHolder = new PicAdapter.PicViewHolder(view, ctx);
            return picViewHolder;

    }

    @Override
    public void onBindViewHolder(PicAdapter.PicViewHolder holder, int position) {
        PicModel picModel = arrayList.get(position);

        if(picModel.get_pic().length()>5)
        {
            uri = Uri.parse(ctx.getResources().getString(R.string.img_url)+picModel.get_pic()+".jpg");
//            holder.pic.setImageURI(uri);
            final ProgressBarDrawable progressBarDrawable = new ProgressBarDrawable();
            progressBarDrawable.setColor(ctx.getResources().getColor(R.color.colorPrimaryDark));
            progressBarDrawable.setBackgroundColor(ctx.getResources().getColor(R.color.colorPrimary));
//            progressBarDrawable
//                    .setRadius(ctx.getResources().getDimensionPixelSize(R.dimen.drawee_hierarchy_progress_radius));

            holder.pic.getHierarchy().setProgressBarImage(progressBarDrawable);
            holder.pic.setController(
                    Fresco.newDraweeControllerBuilder()
                            .setTapToRetryEnabled(true)
                            .setUri(uri)
                            .build());
        }
    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }


    public static class PicViewHolder extends RecyclerView.ViewHolder
    {
        SimpleDraweeView pic;
        Context ctx;

        public PicViewHolder(View view, Context context){
            super(view);
            this.ctx=context;
            pic = view.findViewById(R.id.pic_items_img);
        }
    }

}
