package projects.shahabgt.com.myuniversity.models;



public class CommentsModel {
    private String comment;
    private String likes;
    private String time;
    private String name;
    private String avatar;
    private String cmid;
    private String cmperson;
    private String didlike;

    public String get_cmid(){
        return cmid;
    }
    public String get_cmperson(){
        return cmperson;
    }
    public String get_comment(){
        return comment;
    }
    public String get_likes(){
        return likes;
    }
    public String get_time(){
        return time;
    }
    public String get_name(){
        return name;
    }
    public String get_avatar(){
        return avatar;
    }
    public String get_didlike(){
        return didlike;
    }

    public void set_cmid(String cmid){
        this.cmid = cmid;
    }
    public void set_cmperson(String cmperson){
        this.cmperson = cmperson;
    }
    public void set_comment(String comment){
        this.comment = comment;
    }
    public void set_likes(String likes){
        this.likes = likes;
    }
    public void set_time(String time){
        this.time = time;
    }
    public void set_name(String name){
        this.name = name;
    }
    public void set_avatar(String avatar){
        this.avatar = avatar;
    }
    public void set_didlike(String didlike){
        this.didlike = didlike;
    }

}
