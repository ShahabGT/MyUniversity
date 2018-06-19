package projects.shahabgt.com.myuniversity.models;



public class MessagesModel {
    private String id;
    private String title;
    private String message;
    private String date;
    private String sender;



    public String get_id(){
        return id;
    }
    public String get_title(){
        return title;
    }
    public String get_message(){
        return message;
    }
    public String get_date(){
        return date;
    }
    public String get_sender(){
        return sender;
    }

    public void set_id(String id){
        this.id = id;
    }
    public void set_title(String title){
        this.title = title;
    }
    public void set_message(String message){
        this.message = message;
    }
    public void set_date(String date){
        this.date = date;
    }
    public void set_sender(String sender){
        this.sender = sender;
    }
}


