package clubmanage.ui;

public class CheckResultMsg{
    private byte[] act_picid;
    private String act_name;
    private String opinion;
    private int if_pass_imgid;
    private String if_pass;
    public CheckResultMsg(String act_name,byte[] img, String opinion,String if_pass){
        this.act_name=act_name;
        this.opinion=opinion;
        this.if_pass=if_pass;
        this.act_picid=img;
    }
    public void setAct_name(String act_name){
        this.act_name=act_name;
    }
    public void setAct_picid(byte[] act_picid){this.act_picid=act_picid;}
    public void setOpinion(String opinion){this.opinion=opinion;}
    public void setIf_pass_imgid(int if_pass_imgid){this.if_pass_imgid=if_pass_imgid;}
    public void setIf_pass(String if_pass){this.if_pass=if_pass;}
    public String getAct_name(){
        return act_name;
    }
    public byte[] getAct_picid(){return act_picid;}
    public String getOpinion(){return opinion;}
    public int getIf_pass_imgid(){return if_pass_imgid;}
    public String getIf_pass(){return if_pass;}
}
