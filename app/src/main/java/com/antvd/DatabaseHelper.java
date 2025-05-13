package com.antvd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "employee_db";
    private static final int DATABASE_VERSION = 1;

    // Bảng Users
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_NAME = "username";
    private static final String COLUMN_USER_PASSWORD = "password";

    // Bảng Employees
    private static final String TABLE_EMPLOYEES = "employees";
    private static final String COLUMN_EMP_ID = "id";
    private static final String COLUMN_EMP_NAME = "name";
    private static final String COLUMN_EMP_POSITION = "position";
    private static final String COLUMN_EMP_DEPARTMENT = "department";
    private static final String COLUMN_EMP_EMAIL = "email";
    private static final String COLUMN_EMP_PHONE = "phone";
    private static final String COLUMN_EMP_SALARY = "salary";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Users
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_USER_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        // Tạo bảng Employees
        String CREATE_EMPLOYEES_TABLE = "CREATE TABLE " + TABLE_EMPLOYEES + "("
                + COLUMN_EMP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EMP_NAME + " TEXT,"
                + COLUMN_EMP_POSITION + " TEXT,"
                + COLUMN_EMP_DEPARTMENT + " TEXT,"
                + COLUMN_EMP_EMAIL + " TEXT,"
                + COLUMN_EMP_PHONE + " TEXT,"
                + COLUMN_EMP_SALARY + " REAL" + ")";
        db.execSQL(CREATE_EMPLOYEES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa các bảng cũ nếu tồn tại
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);

        // Tạo lại các bảng
        onCreate(db);
    }

    // Thêm người dùng mới
    public void addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, username);
        values.put(COLUMN_USER_PASSWORD, password);

        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    // Kiểm tra đăng nhập
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USER_ID};
        String selection = COLUMN_USER_NAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    // Thêm nhân viên mới
    public long addEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMP_NAME, employee.getName());
        values.put(COLUMN_EMP_POSITION, employee.getPosition());
        values.put(COLUMN_EMP_DEPARTMENT, employee.getDepartment());
        values.put(COLUMN_EMP_EMAIL, employee.getEmail());
        values.put(COLUMN_EMP_PHONE, employee.getPhone());
        values.put(COLUMN_EMP_SALARY, employee.getSalary());

        long id = db.insert(TABLE_EMPLOYEES, null, values);
        db.close();
        return id;
    }

    // Lấy tất cả nhân viên
    public Cursor getAllEmployees() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_EMPLOYEES, null, null, null, null, null, null);
    }

    // Cập nhật thông tin nhân viên
    public int updateEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMP_NAME, employee.getName());
        values.put(COLUMN_EMP_POSITION, employee.getPosition());
        values.put(COLUMN_EMP_DEPARTMENT, employee.getDepartment());
        values.put(COLUMN_EMP_EMAIL, employee.getEmail());
        values.put(COLUMN_EMP_PHONE, employee.getPhone());
        values.put(COLUMN_EMP_SALARY, employee.getSalary());

        return db.update(TABLE_EMPLOYEES, values, COLUMN_EMP_ID + " = ?",
                new String[]{String.valueOf(employee.getId())});
    }

    // Xóa nhân viên
    public void deleteEmployee(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEES, COLUMN_EMP_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
