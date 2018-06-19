package projects.shahabgt.com.myuniversity.models;

public class MyPostsModel {

    private String subject;
    private String catagory;
    private String catagory_sub;
    private String title;
    private String comment;
    private String state;
    private String stat;


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
    public String get_comment(){
        return comment;
    }
    public String get_state(){
        return state;
    }
    public String get_stat(){
        return stat;
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
    public void set_comment(String comment){
        this.comment = comment;
    }
    public void set_state(String state){
        this.state = state;
    }
    public void set_stat(String stat){
        switch (Integer.parseInt(stat)){
            case 0:
                this.stat = "Unpublished";
                break;
            case 1:
                this.stat = "Published";
                break;
            case 2:
                this.stat = "Pending Removal";
                break;
        }
    }

}
