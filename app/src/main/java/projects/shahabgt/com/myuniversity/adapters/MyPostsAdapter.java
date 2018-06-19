package projects.shahabgt.com.myuniversity.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import projects.shahabgt.com.myuniversity.R;
import projects.shahabgt.com.myuniversity.models.MyPostsModel;


public class MyPostsAdapter extends RecyclerView.Adapter<MyPostsAdapter.MyPostsViewHolder> {

    private Context ctx;
    private ArrayList<MyPostsModel> arrayList,filterlist;

    public MyPostsAdapter(Context ctx,ArrayList<MyPostsModel> arrayList){
        this.arrayList=arrayList;
        this.ctx=ctx;
        this.filterlist=new ArrayList<MyPostsModel>();
        this.filterlist.addAll(this.arrayList);

    }


    @Override
    public MyPostsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myposts_items,parent,false);
        MyPostsViewHolder mypostsViewHolder = new MyPostsViewHolder(view,ctx);
        return mypostsViewHolder;


    }

    @Override
    public void onBindViewHolder(MyPostsViewHolder holder, int position) {
        MyPostsModel myPostsModel = filterlist.get(position);
        holder.subject.setText(myPostsModel.get_subject());
        holder.catagory.setText(myPostsModel.get_catagory());
        holder.catagory_sub.setText(myPostsModel.get_catagory_sub());
        holder.title.setText(myPostsModel.get_title());
        holder.comment.setText(myPostsModel.get_comment());
        holder.stat.setText(myPostsModel.get_stat());
            holder.state.setText(myPostsModel.get_state());

    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }
    public void search(final String title){
        filterlist.clear();
        if(TextUtils.isEmpty(title))
        {
            filterlist.addAll(arrayList);

        }else{
            for(MyPostsModel item: arrayList){
                if(item.get_title().contains(title)){
                    filterlist.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
    public static class MyPostsViewHolder extends RecyclerView.ViewHolder
    {

        TextView subject,catagory,catagory_sub,title,comment,state,stat;

        Context ctx;
        public MyPostsViewHolder(View view, Context context){
            super(view);
            this.ctx=context;
            subject = view.findViewById(R.id.myposts_subject);
            catagory = view.findViewById(R.id.myposts_catagory);
            catagory_sub = view.findViewById(R.id.myposts_catagory_sub);
            title = view.findViewById(R.id.myposts_title);
            comment = view.findViewById(R.id.myposts_comment);
            state = view.findViewById(R.id.myposts_state);
            stat = view.findViewById(R.id.myposts_stat);

        }
    }
}