package com.antvd;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MenuAdapter adapter;
    private List<com.antvd.MenuItem> menuItems;
    private TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvWelcome = findViewById(R.id.tvWelcome);
        recyclerView = findViewById(R.id.recyclerView);

        // Lấy tên người dùng từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        tvWelcome.setText("Xin chào, " + username + "!");

        // Thiết lập RecyclerView với GridLayoutManager (2 cột)
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        // Tạo danh sách các mục menu
        prepareMenuItems();

        // Thiết lập adapter
        adapter = new MenuAdapter(this, menuItems);
        recyclerView.setAdapter(adapter);
    }

    private void prepareMenuItems() {
        menuItems = new ArrayList<>();

        // Thêm các mục menu - thêm nhiều mục để tạo hiệu ứng cuộn
        menuItems.add(new com.antvd.MenuItem("Danh sách nhân viên", R.drawable.ic_list));
        menuItems.add(new com.antvd.MenuItem("Thêm nhân viên", R.drawable.ic_add));
        menuItems.add(new com.antvd.MenuItem("Chỉnh sửa nhân viên", R.drawable.ic_edit));
        menuItems.add(new com.antvd.MenuItem("Xóa nhân viên", R.drawable.ic_delete));
        menuItems.add(new com.antvd.MenuItem("Tìm kiếm", R.drawable.ic_search));
        menuItems.add(new com.antvd.MenuItem("Thống kê", R.drawable.ic_stats));
        menuItems.add(new com.antvd.MenuItem("Báo cáo", R.drawable.ic_report));
        menuItems.add(new com.antvd.MenuItem("Phòng ban", R.drawable.ic_department));
        menuItems.add(new com.antvd.MenuItem("Lương thưởng", R.drawable.ic_salary));
        menuItems.add(new com.antvd.MenuItem("Chấm công", R.drawable.ic_attendance));
        menuItems.add(new com.antvd.MenuItem("Đào tạo", R.drawable.ic_training));
        menuItems.add(new com.antvd.MenuItem("Cài đặt", R.drawable.ic_settings));
        menuItems.add(new com.antvd.MenuItem("Hỗ trợ", R.drawable.ic_help));
        menuItems.add(new com.antvd.MenuItem("Đăng xuất", R.drawable.ic_logout));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            // Xóa trạng thái đăng nhập và quay lại màn hình Login
            SharedPreferences sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}