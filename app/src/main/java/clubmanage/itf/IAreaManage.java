package clubmanage.itf;

import java.util.List;

import clubmanage.model.Area;
import clubmanage.util.BaseException;

public interface IAreaManage {
    /**
     * 列出所有可用场地
     */
    public List<Area> listUsibleSpe() throws BaseException;
    /**
     * 列出所有可用和本社团专用的场地
     */
    public List<Area> listUsibleSpe(int club_id) throws BaseException;
    /**
     * 修改场地是否为专用
     */
    public void editSpecialties(String area_name,boolean set_specialities) throws BaseException;
    /**
     * 修改场地是否可用
     */
    public void editUsable(String area_name,boolean set_usable) throws BaseException;
}
