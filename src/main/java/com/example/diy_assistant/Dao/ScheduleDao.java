package com.example.diy_assistant.Dao;

import android.content.Context;
import android.os.Parcelable;

import com.example.diy_assistant.Bean.ScheduleBean;
import com.example.diy_assistant.DB_Helper.MyHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by GeeGumb on 14:16 2019/5/10
 */
public class ScheduleDao {
    private MyHelper myHelper;
    private Dao<ScheduleBean,Integer> dao;

    public ScheduleDao(Context context){
        myHelper = MyHelper.getInstance(context);
        try {
            dao = myHelper.getMyScheduleDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(ScheduleBean scheduleBean) throws SQLException {
            dao.createOrUpdate(scheduleBean);
    }

    public void update(ScheduleBean scheduleBean) throws SQLException {
        dao.update(scheduleBean);
    }

    public void delete(ScheduleBean scheduleBean) throws SQLException {
        dao.delete(scheduleBean);
    }

    public List<ScheduleBean> getAll() throws SQLException {
        return dao.queryForAll();
    }
}
