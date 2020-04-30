package clubmanage.itf;

import java.util.List;

import clubmanage.model.Attention;
import clubmanage.model.Club;
import clubmanage.util.BaseException;

public interface IAttentionManage {
    /**
     * 添加关注
     */
    public void addAttention(String uid,int club_id);
    /**
     * 取消关注
     */
    public void deleteAttention(String uid,int club_id)throws BaseException;
    /**
     * 查找用户关注的社团
     */
    public List<Club> searchAttenByUser(String uid);
    /**
     * 查找用户关注的社团数
     */
    public int searchAttenCount(String uid);
    /**
     * 查询用户是否关注该社团
     */
    public boolean issubscribe(String uid,int club_id);
}
