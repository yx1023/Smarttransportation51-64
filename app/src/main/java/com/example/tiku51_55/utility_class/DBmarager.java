package com.example.tiku51_55.utility_class;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.tiku51_55.Been.DB_ssjt;
import com.example.tiku51_55.Been.JL;
import com.example.tiku51_55.Been.X;

import java.util.ArrayList;
import java.util.List;

public class DBmarager extends SQLiteOpenHelper {
    SQLiteDatabase db;
static String db_name="tables.db";
static int db_version=1;

    public DBmarager(@Nullable Context context) {
        super(context, db_name, null,db_version);
        db=getWritableDatabase();
    }
    //打开数据库
    public SQLiteDatabase openDB(){
        return db=getWritableDatabase();
    }
    //关闭数据库
    public void closeDB(){
        db.close();
    }
    //建立数据表
    public boolean createtable(String sql){
        openDB();
        try {
            db.execSQL(sql);
            closeDB();
        }catch (Exception ex){
            closeDB();
            return false;
        }
        return true;
    }
    //输出表名  判断表是否存在
    public boolean isExist(String tablename){
        String sql="select * from "+tablename;
        openDB();
        try {
            db.rawQuery(sql,null);
            closeDB();
        }catch (Exception ex){
            closeDB();
            return false;
        }
        return true;
    }
    //插入
    public boolean insertDB(String table, ContentValues cv){
        openDB();
        try {
            //执行代码
            db.insert(table,null,cv);
            closeDB();
        }catch (Exception ex){
            closeDB();
            return false;
        }
        return true;
    }
    //删除
    public boolean deltable(String tablename , String position, String[] a){
        openDB();
        try {
            db.delete(tablename, position,a);
            closeDB();
        }catch (Exception ex){
            closeDB();
            return false;
        }
        return true;
    }
    //查找
    public Cursor queryDB(String tablename, String [] cols, String argwhere, String[] args, String group, String having, String order, String litmit){
        Cursor c;
        openDB();
        try {
            //执行代码
            c=db.query(tablename,cols,argwhere,args,group,having,order,litmit);

        }catch (Exception ex){

            return null;
        }
        return c;
    }
    //修改
    public boolean uptable(String tablename,ContentValues values ,String name, String[]b){
        openDB();
        try{
            db.update(tablename,values,name,b);
            closeDB();
        }catch (Exception ex){
            closeDB();
            return false;
        }
        return true;
    }
    //删除表
    public void deleteTable( String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
    }

    @SuppressLint("Range")
    public List<X> sendee(Cursor c){
        List<X>qList=new ArrayList<>();
        while (c.moveToNext()){
            X q=new X();
            q.setName(c.getString(c.getColumnIndex("name")));
            q.setUsername(c.getString(c.getColumnIndex("username")));
            q.setTel(c.getString(c.getColumnIndex("tel")));
            q.setTime(c.getString(c.getColumnIndex("time")));
            q.setStatus(c.getString(c.getColumnIndex("status")));
            q.setSex(c.getString(c.getColumnIndex("sex")));
            qList.add(q);
        }
            return qList;

    }
    @SuppressLint("Range")
    public List<DB_ssjt> sendees(Cursor c){
        List<DB_ssjt>list=new ArrayList<>();
        while (c.moveToNext()){
            DB_ssjt ssjt=new DB_ssjt();
            ssjt.setNumber(c.getInt(c.getColumnIndex("number")));
            ssjt.setXianlu(c.getString(c.getColumnIndex("xianlu")));
            list.add(ssjt);
        }
        return list;
    }

    @SuppressLint("Range")
    public List<JL> sendeeqa(Cursor c){
        List<JL>qList=new ArrayList<>();
        while (c.moveToNext()){
            JL q=new JL();
            q.setName(c.getString(c.getColumnIndex("name")));
            q.setMoney(c.getString(c.getColumnIndex("money")));
            q.setPlate(c.getString(c.getColumnIndex("plate")));
            q.setWeek(c.getString(c.getColumnIndex("week")));
            q.setTime1(c.getString(c.getColumnIndex("time1")));
            q.setTime2(c.getString(c.getColumnIndex("time2")));
            qList.add(q);
        }
        return qList;

    }







    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
