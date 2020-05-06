package clubmanage.itf;

import java.util.List;

import clubmanage.model.Club;
import clubmanage.model.Club_category;
import clubmanage.model.User;
import clubmanage.util.BaseException;

public interface IClubManage {
    /**
     * 查询所有社团
     *ifdismiss如果为true则包含已废弃社团，如果为false则不包含
     */
    public List<Club> searchAllClub(boolean ifdismiss) throws BaseException;
    /**
     * 查询所有社团
     * 模糊查询
     *club_name:社团名，ifdismiss如果为true则包含已废弃社团，如果为false则不包含
     */
    public List<Club> searchAllClub(String club_name,boolean ifdismiss) throws BaseException;
    /**
     * 按类别查询社团
     *club_name:社团名，ifdismiss如果为true则包含已废弃社团，如果为false则不包含
     */
    public List<Club> searchClubByType(String typename,boolean ifdismiss);
    /**
     * 按社团id查社团
     */
    public Club searchClubByClubid(int clubid);
    /**
     * 查询本社团所有成员
     */
    public List<User> searchMember(int club_id)throws BaseException;
    /**
     * 根据社长id查询社团
     */
    public Club searchClubByProprieter(String uid);
    /**
     * 根据社长id查询社团id
     */
    public Integer searchClubIdByProprieter(String uid);
    /**
     * 查询我的社团
     */
    public List<Club> searchMyClub(String uid);
    /**
     * 查询我的社团个数
     */
    public int searchMyClubCount(String uid);
    /**
     * 查询社团类别
     */
    public List<Club_category> searchClubType() throws BaseException;
    /**
     * 添加社团
     */
    public void addClub(String club_name,String category_name, String captain_name,String club_introduce,String club_place,String intendant);
    /**
     * 删除社团
     * 假删
     * 设置社团if_club_end标记
     */
    public void deleteClub(int club_id);
    /**
     * 编辑社团简介
     */
    public void editCategory(int club_id,String category);
    /**
     * 编辑社团简介
     */
    public void editIntroduction(int club_id,String introduction);
    /**
     * 编辑社团宣传语
     */
    public void editSlogan(int club_id,String slogan);
    /**
     * 编辑社团logo
     */
    public void editLogo(int club_id,String club_icon);
    /**
     * 编辑社团海报
     */
    public void editCover(int club_id,String club_cover);
    /**
     * 加入一个社团
     */
    public void joinClub(String uid,int club_id);
    /**
     * 退出一个社团
     */
    public void secedeClub(String uid,int club_id);
    /**
     * 查询社团告示
     */
    public String searchNotice(int club_id);
    /**
     * 修改社团告示
     */
    public void editNotice(int club_id,String notice_detail);
    /**
     * 用户是否在该社团中
     */
    public boolean if_userInClub(String uid,int club_id);
    /**
     * 用户是否为该社团社长
     */
    public boolean if_userIsCaptain(String uid,int club_id);
    /**
     * 转让社长
     */
    public void transferPresident(String uid,String puid,int clubid);
    /**
     * 删除社员
     */
    public void deleteMember(String uid,int clubid);
}
