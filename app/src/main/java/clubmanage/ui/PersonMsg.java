package clubmanage.ui;

public class PersonMsg {
    private String key;
    private String value;

    public PersonMsg(String key,String value){
        this.key=key;
        this.value=value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
