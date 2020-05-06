package clubmanage.itf;

import java.util.List;

import clubmanage.model.Create_activity;
import clubmanage.model.Create_club;
import clubmanage.model.Dismiss_club;
import clubmanage.model.Join_club;
import clubmanage.util.BaseException;

public interface IApplicationManage {
    /**
     * 添加社团申请
     * 提交申请后申请表状态为待审查
     * state：0=未通过；1=通过；2=待审查
     */
    public void addClubAppli(String area_name,String uid,String applican_name,String club_name,String club_category,String introduce,String reason)throws BaseException;
    /**
     * 社团申请反馈
     * state：0=未通过；1=通过；2=待审查
     * 如果通过则直接调用addClub函数
     */
    public void feedbackClubAppli(int applyclub_formid,int state,String handle_people_id,String suggestion);
    /**
     * 添加活动申请
     * 提交申请后申请表状态为待审查
     * if_public_activity: 0=不公开；1=公开
     * state：0=未通过；1=通过；2=待审查
     */
    public void addActivityAppli(int club_id,String poster,String activity_name,String area_name,String activity_owner_id,String activity_owner_name,String activity_start_time,String activity_end_time,String activity_details,String sttention,String activity_category,boolean if_public_activity,String reason)throws BaseException;
    /**
     * 活动申请反馈
     * state：0=未通过；1=通过；2=待审查
     * 如果通过则直接调用addActivi函数
     */
    public void feedbackActivityAppli(int activity_approval_id,int state,String suggest,String handle_people_id);
    /**
     * 加入社团申请
     * 提交申请后申请表状态为待审查
     * state：0=未通过；1=通过；2=待审查
     */
    public void joinClubAppli(int club_id,String uid,String join_name,String reason);
    /**
     * 加入社团申请反馈
     * state：0=未通过；1=通过；2=待审查
     * 如果通过则直接调用joinClub函数
     */
    public void feedbackJoinClubAppli(int joinclub_formid,int state,String handle_people_id);
    /**
     * 解散社团申请
     * 提交申请后申请表状态为待审查
     * state：0=未通过；1=通过；2=待审查
     */
    public void dismissClubAppli(String uid,String dismiss_name,int club_id,String club_name,String reason);
    /**
     * 解散社团申请反馈
     * state：0=未通过；1=通过；2=待审查
     * 如果通过则直接删除本社团和join_club表中此社团所有成员
     */
    public void feedbackDismissClub(int dismissclub_formid,int state,String handle_people_id);
    /**
     * 报名活动
     */
    public void signupActivity(String uid,int activity_id);
    /**
     * 查找所有创建社团的申请
     */
    public List<Create_club> searchCreateClubAppli();
    /**
     * 按id查找创建社团的申请
     */
    public Create_club searchCreateClubAppliByID(int id);
    /**
     * 查找所有创建活动的申请
     */
    public List<Create_activity> searchCreateActivityAppli();
    /**
     * 按id查找创建活动的申请
     */
    public Create_activity searchCreateActivityAppliByID(int id);
    /**
     * 查找所有加入社团的申请
     */
    public List<Join_club> searchJoinClubAppli();
    /**
     * 查找所有解散社团的申请
     */
    public List<Dismiss_club> searchDismissClubAppli();
    /**
     * 查找某位用户创建活动的申请
     */
    public List<Create_activity> searchCreateActivityByUser(String uid);
    /**
     * 查找某位用户创建社团的申请
     */
    public List<Create_club> searchCreateClubByUser(String uid);
    /**
     * 查找某位用户是否有待审查的创建社团申请
     */
    public boolean ifHaveClubAppli(String uid);
}
