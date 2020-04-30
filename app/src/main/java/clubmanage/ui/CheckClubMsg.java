package clubmanage.ui;

public class CheckClubMsg {
    private String act_name;
    private String act_time;
    private String act_location;
    private int act_picid;

    public CheckClubMsg(String act_name, String act_time, String act_location){
        this.act_name=act_name;
        this.act_time=act_time;
        this.act_location=act_location;
    }
    public void setAct_name(String act_name){
        this.act_name=act_name;
    }
    public void setAct_time(String act_time){
        this.act_time=act_time;
    }
    public void setAct_location(String act_location){
        this.act_location=act_location;
    }
    public void setAct_picid(int act_picid){
        this.act_picid=act_picid;
    }
    public String getAct_name(){
        return act_name;
    }
    public String getAct_time(){
        return act_time;
    }
    public String getAct_location(){
        return act_location;
    }
    public int getAct_picid(){
        return act_picid;
    }
}
