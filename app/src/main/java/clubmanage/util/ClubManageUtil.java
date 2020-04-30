package clubmanage.util;

import clubmanage.control.ActivityManage;
import clubmanage.control.ApplicationManage;
import clubmanage.control.AreaManage;
import clubmanage.control.ClubManage;
import clubmanage.control.CommentManage;
import clubmanage.control.AttentionManage;
import clubmanage.control.PersonalManage;
import clubmanage.control.UserManage;
import clubmanage.itf.IActivityManage;
import clubmanage.itf.IApplicationManage;
import clubmanage.itf.IAreaManage;
import clubmanage.itf.IClubManage;
import clubmanage.itf.ICommentManage;
import clubmanage.itf.IAttentionManage;
import clubmanage.itf.IPersonalManage;
import clubmanage.itf.IUserManage;

public class ClubManageUtil {
    public static IUserManage usermanage=new UserManage();
    public static IActivityManage activityManage=new ActivityManage();
    public static IApplicationManage applicationManage=new ApplicationManage();
    public static IAreaManage areaManage=new AreaManage();
    public static IClubManage clubManage=new ClubManage();
    public static ICommentManage commentManage=new CommentManage();
    public static IPersonalManage personalManage=new PersonalManage();
    public static IAttentionManage attentionManage=new AttentionManage();
}
