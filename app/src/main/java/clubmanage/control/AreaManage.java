package clubmanage.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import clubmanage.itf.IAreaManage;
import clubmanage.model.Area;
import clubmanage.util.BaseException;
import clubmanage.util.DBUtil;
import clubmanage.util.DbException;

public class AreaManage implements IAreaManage {
    @Override
    public List<Area> listUsibleSpe() throws BaseException {//列处所有可用场地
        List<Area> result = new ArrayList<Area>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select area_name,specialties,usable from area where specialties=0 and usable=1";//非专用并且可用
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Area area = new Area();
                area.setArea_name(rs.getString(1));
                area.setSpecialties(rs.getByte(2));
                area.setUsable(rs.getByte(3));
                result.add(area);
            }
            rs.close();
            pst.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return result;
    }

    @Override
    public List<Area> listUsibleSpe(int club_id) throws BaseException{//列出本社专用场地和所有可用场地
            List<Area> result = new ArrayList<Area>();
            Connection conn = null;
            try {
                conn = DBUtil.getConnection();
                String sql = "select area_name,specialties,usable from area where specialties=0 and usable=1";//列出所有非专用并且可用的场地
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                java.sql.ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    Area area = new Area();
                    area.setArea_name(rs.getString(1));
                    area.setSpecialties(rs.getByte(2));
                    area.setUsable(rs.getByte(3));
                    result.add(area);
                }
                rs.close();
                pst.close();
                sql = "select area_name,specialties,usable from area,club where area.area_name=club.club_place and club_id=?";//列出本社的专用场地
                pst = conn.prepareStatement(sql);
                pst.setInt(1,club_id);
                rs = pst.executeQuery();
                while (rs.next()) {
                    Area area = new Area();
                    area.setArea_name(rs.getString(1));
                    area.setSpecialties(rs.getByte(2));
                    area.setUsable(rs.getByte(3));
                    result.add(area);
                }
                rs.close();
                pst.close();

            } catch (SQLException e) {
                e.printStackTrace();
                throw new DbException(e);
            } finally {
                if (conn != null)
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
            }
            return result;
    }

    @Override
    public void editSpecialties(String area_name,boolean set_specialities) throws BaseException{
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            Byte flag=0;
            if(set_specialities==true)
                flag=1;
            else
                flag=0;
            String sql = "update area set specialties=? where area_name=?";//更新其专用性
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setByte(1,flag);
            pst.setString(2, area_name);
            pst.execute();
            pst.close();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public void editUsable(String area_name,boolean set_usable) throws BaseException{
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            Byte flag=0;
            if(set_usable==true)
                flag=1;
            else
                flag=0;
            String sql = "update area set usable=? where area_name=?";//更新其可用性
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setByte(1,flag);
            pst.setString(2, area_name);
            pst.execute();
            pst.close();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }
    public static void main(String[] args) throws BaseException {
        AreaManage am=new AreaManage();
//        List<Area>  ar=am.listUsibleSpe();//查找所有可用场地
//        for(int i=0;i<ar.size();i++){
//            System.out.print(ar.get(i).getArea_name());
//            System.out.print(ar.get(i).getSpecialties());
//            System.out.print(ar.get(i).getUsable());
//        }//文10301文10401文10501文10601

//        List<Area>  ar=am.listUsibleSpe(2);
//        for(int i=0;i<ar.size();i++){
//            System.out.print(ar.get(i).getArea_name());
//            System.out.print(ar.get(i).getSpecialties());
//            System.out.print(ar.get(i).getUsable());
//        }//文10301文10401文10501文10601文10211

        //修改其专有状态
//        am.editSpecialties("文103");

        //修改其可用状态
//        am.editUsable("文103");
    }
}
