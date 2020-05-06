package clubmanage.itf;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import clubmanage.model.Activity;
import clubmanage.model.Activity_picture;
import clubmanage.util.BaseException;

public interface IActivityManage {
    /**
     * 添加活动
     */
    public void addActivity(int club_id, String activity_name,String poster, String activity_start_time, String activity_end_time, String activity_introduce, String activity_place, String activity_attention,boolean if_public_activity,String activity_category) throws ParseException;
    /**
     * 添加活动图片
     * 一次只加一张
     */
    public void addActPicture(int Activity_id,String picturePath);
    /**
     * 修改活动图片
     */
    public void editActPicture(int picture_id,String picturePath);
    /**
     * 查找某社团某活动图片
     */
    public List<Activity_picture> searchActivityPicture(int activity_id)throws BaseException;
    /**
     * 修改活动
     */
    public void editActivity(Activity activity);
    /**
     * 查找所有活动
     * 按时间排序
     */
    public List<Activity> searchAllActivity();
    /**
     * 查找某社团活动
     * 按社团名查询
     * 按时间排序
     */
    public List<Activity> searchActivityByClubName(String club_name);
    /**
     * 查找某社团活动
     * 按社团id查询
     * 按时间排序
     */
    public List<Activity> searchActivityByClubId(int club_id);
    /**
     * 查找某活动
     * 按活动名查询
     * 按时间排序
     * 模糊查询
     */
    public List<Activity> searchActivityByName(String activity_name);
    /**
     * 查找某活动
     * 按种类查询
     * 按时间排序
     */
    public List<Activity> searchActivityByCategory(String category);
    /**
     * 查询活动类别
     */
    public List<String> searchActivityType() throws BaseException;
    /**
     * 查找某用户参加的活动
     */
    public List<Activity> searchMyActivity(String uid);
    /**
     * 查找某用户参加的活动个数
     */
    public int searchMyActivityCount(String uid);
    /**
     * 查找某用户是否参加某活动
     */
    public boolean if_participate(String uid,int activity_id);
    /**
     * 按活动id查找活动
     */
    public Activity searchActivityById(int activityid);
    /**
     * 按活动id查找社团名
     */
    public String findClubNameByActivityId(int activityid);
    /**
     * 按活动id查找负责人名
     */
    public String findProprieterNameByActivityId(int activityid);
}
