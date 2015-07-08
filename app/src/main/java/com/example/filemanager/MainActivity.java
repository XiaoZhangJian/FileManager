package com.example.filemanager;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {


    private ListView lv;
    private FilesAdapter adapter;
    private File parent;
    private View header;
    private TextView tvName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv =(ListView) findViewById(R.id.main_files);
        tvName = (TextView) findViewById(R.id.main_title_path);
        header = LayoutInflater.from(this).inflate(R.layout.layout_item_file,null);
        initHeader();

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            // 获取sdCard根目录文件
            parent = Environment.getExternalStorageDirectory();
            tvName.setText(parent.getName());
            lv.addHeaderView(header);
            adapter = new FilesAdapter(this,getChildren(parent));
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(itemClick);
        } else{
            showToast("sdcard不可读写");
        }
    }

    private AdapterView.OnItemClickListener itemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> p, View view, int position, long id) {
            if (position == 0){
                if (isRoot(parent) ){
                    showToast("已经是根目录了");
                    return;
                }
                // 获得上一级文件夹
                parent = parent.getParentFile();
                tvName.setText(parent.getName());
                // 加载父级布局
                adapter.setFiles(getChildren(parent));
                adapter.update();
                return;
            }
            // 当添加Header后,header就是0,下面的项从1开始,所以减1
            File file = adapter.getItem(position - 1);
            if (file.isDirectory()){
                // 改变父目录为点击的项
                parent  = file;
                tvName.setText(parent.getName());
                // 更改适配器数据为点击项的子文件数组
                adapter.setFiles(getChildren(parent));
                // 刷新显示数据
                adapter.update();

            }else{
                showToast("这是个文件,傻**");
            }
        }
    };



    private void setHeaderVisiable(boolean needHeader){
        if (needHeader){
            header.setVisibility(View.VISIBLE);
        }else{
            header.setVisibility(View.GONE);
        }
    }


    // 是否是根目录
    private boolean isRoot(File file){
        return file.equals(Environment.getExternalStorageDirectory());
    }


    private void initHeader(){
        LayoutInflater.from(this).inflate(R.layout.layout_item_file,null);

    }



    public File[] getChildren(File file){
        return  file.listFiles();
    }


    public void showToast(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }





}
