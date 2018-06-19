package projects.shahabgt.com.myuniversity.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import projects.shahabgt.com.myuniversity.PostDetails;
import projects.shahabgt.com.myuniversity.R;
import projects.shahabgt.com.myuniversity.models.PostsModel;


public class ViewpostAdapter extends RecyclerView.Adapter<ViewpostAdapter.RecyclerViewHolder> {
   private Context ctx;
   private ArrayList<PostsModel> arrayList,filterlist;


    public ViewpostAdapter(Context ctx, ArrayList<PostsModel> arrayList){
        this.arrayList=arrayList;
        this.ctx=ctx;
        this.filterlist=new ArrayList<PostsModel>();
        this.filterlist.addAll(this.arrayList);

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items,parent,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view,ctx);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        PostsModel postsModel = filterlist.get(position);
        holder.title.setText(postsModel.get_title());
        holder.state.setText(postsModel.get_state());
        holder.catagory.setText(postsModel.get_catagory());
        holder.catagory_sub.setText(postsModel.get_catagory_sub());
        holder.subject.setText(postsModel.get_subject());
        holder.postid.setText(postsModel.get_postid());
        holder.person.setText(postsModel.get_person());
        holder.like.setText(postsModel.get_like());
        holder.dislike.setText(postsModel.get_dislike());
        holder.comment.setText(postsModel.get_comment());

    }

    @Override
    public int getItemCount() {

        return filterlist.size();
    }

    public void search (final String title){
        filterlist.clear();
        if(TextUtils.isEmpty(title))
        {
            filterlist.addAll(arrayList);

        }else{
            for(PostsModel item: arrayList){
                if(item.get_title().contains(title)){
                    filterlist.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
    public void filter (final String subject,final String catagory,final String catagory_sub){
        filterlist.clear();
        if(subject.equals("all")&& catagory.equals("all")&&catagory_sub.equals("all")) {
            filterlist.addAll(arrayList);

        }else if (subject.equals("all") && !catagory.equals("all") && catagory_sub.equals("all")) {
            for(PostsModel item: arrayList){
                if(item.get_catagory().contains(catagory)){
                    filterlist.add(item);
                }
            }
        }else if (!subject.equals("all") && catagory.equals("all") && catagory_sub.equals("all")) {
            for(PostsModel item: arrayList){
                if(item.get_subject().contains(subject)){
                    filterlist.add(item);
                }
            }
        }else if (subject.equals("all") && !catagory.equals("all") && !catagory_sub.equals("all")) {
            for(PostsModel item: arrayList){
                if(item.get_catagory().contains(catagory)&&item.get_catagory_sub().contains(catagory_sub)){
                    filterlist.add(item);
                }
            }
        }
        else if (!subject.equals("all") && !catagory.equals("all") && !catagory_sub.equals("all")) {
            for(PostsModel item: arrayList){
                if(item.get_subject().contains(subject)&&item.get_catagory().contains(catagory)&&item.get_catagory_sub().contains(catagory_sub)){
                    filterlist.add(item);
                }
            }
        }else if (!subject.equals("all") && !catagory.equals("همه") && catagory_sub.equals("all")) {
            for(PostsModel item: arrayList){
                if(item.get_subject().contains(subject)&&item.get_catagory().contains(catagory)){
                    filterlist.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title,catagory,catagory_sub,subject,postid,state,person,like,dislike,comment;
        Context ctx;
        public RecyclerViewHolder(View view, Context context){
            super(view);
            this.ctx=context;
            view.setOnClickListener(this);
            title= view.findViewById(R.id.recycler_item_title);
            catagory= view.findViewById(R.id.recycler_item_catagory);
            catagory_sub= view.findViewById(R.id.recycler_item_catagory_sub);
            subject= view.findViewById(R.id.recycler_item_subject);
            postid= view.findViewById(R.id.recycler_item_postid);
            person= view.findViewById(R.id.recycler_item_person);
            state= view.findViewById(R.id.recycler_item_state);
            like= view.findViewById(R.id.recycler_item_likes);
            dislike= view.findViewById(R.id.recycler_item_dislikes);
            comment= view.findViewById(R.id.recycler_item_comments);

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ctx,PostDetails.class);
            intent.putExtra("postid",postid.getText());
            intent.putExtra("subject",subject.getText());
            intent.putExtra("catagory",catagory.getText());
            intent.putExtra("catagory_sub",catagory_sub.getText());
            intent.putExtra("title",title.getText());
            intent.putExtra("state",state.getText());
            intent.putExtra("person",person.getText());
            this.ctx.startActivity(intent);

        }


    }


}

