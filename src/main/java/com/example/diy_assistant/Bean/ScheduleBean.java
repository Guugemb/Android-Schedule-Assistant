package com.example.diy_assistant.Bean;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by GeeGumb on 18:28 2019/5/9
 */
@DatabaseTable(tableName = "Tab_Schedule")
public class ScheduleBean {
    /*
     * 这里本来是id = true的
     * 然后这样设置会只能插入第一条数据，后续数据插不进去，十分诡异
     * 将id = true 改成generateId = true后正常*/
    @DatabaseField(generatedId = true, columnName = "_id", dataType = DataType.INTEGER)
    private int id;

    @DatabaseField(columnName = "title")
    private String title;

    @DatabaseField(columnName = "desc")
    private String desc;

    @DatabaseField(columnName = "fromTime", dataType = DataType.DATE)
    private Date fromTime;

    @DatabaseField(columnName = "deadline", dataType = DataType.DATE)
    private Date deadline;


    public int getHasSend() {
        return hasSend;
    }

    public void setHasSend(int hasSend) {
        this.hasSend = hasSend;
    }

    @DatabaseField(columnName = "hasSend", dataType = DataType.INTEGER)
    private int hasSend = 0;


    public ScheduleBean(String title, String desc, Date deadline) {
        this.title = title;
        this.desc = desc;
        this.deadline = deadline;

        fromTime = new Date();
    }

    public ScheduleBean() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getFromTime() {
        return fromTime;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void main(String[] args) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String str = simpleDateFormat.format(new Date());
        String year = str.substring(0, 4);
        String month = str.substring(5, 7);
        String day = str.substring(8, 10);
        String hour = str.substring(11, 13);
        String minute = str.substring(14, 16);

        System.out.println(String.format("%s,%s,%s,%s,%s", year, month, day, hour, minute));
    }

}
