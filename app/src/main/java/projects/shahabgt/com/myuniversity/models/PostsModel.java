package projects.shahabgt.com.myuniversity.models;



public class PostsModel {

    private String postid;
    private String person;
    private String subject;
    private String catagory;
    private String catagory_sub;
    private String title;
    private String state;
    private String like;
    private String dislike;
    private String comment;

    public String get_person(){
        return person;
    }
    public String get_postid(){
        return postid;
    }
    public String get_subject(){
        return subject;
    }
    public String get_catagory(){
        return catagory;
    }
    public String get_catagory_sub(){
        return catagory_sub;
    }
    public String get_title(){
        return title;
    }
    public String get_state(){
        return state;
    }
    public String get_like(){
        return like;
    }
    public String get_dislike(){
        return dislike;
    }
    public String get_comment(){
        return comment;
    }

    public void set_person(String person){
        this.person = person;
    }
    public void set_postid(String postid){
        this.postid = postid;
    }
    public void set_subject(String subject){
        this.subject = subject;
    }
    public void set_catagory(String catagory){
        this.catagory = catagory;
    }
    public void set_catagory_sub(String catagory_sub){
        this.catagory_sub = catagory_sub;
    }
    public void set_title(String title){
        this.title = title;
    }
    public void set_state(String state){
        this.state = state;
    }
    public void set_like(String like){
        this.like = like;
    }
    public void set_dislike(String dislike){
        this.dislike = dislike;
    }
    public void set_comment(String comment){
        this.comment = comment;
    }


}
