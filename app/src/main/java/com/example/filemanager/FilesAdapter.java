package com.example.filemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * Created by ZhangJian on 2015/7/8.
 */
public class FilesAdapter extends BaseAdapter {

    private File[] files;
    private LayoutInflater lif;

    public FilesAdapter(Context context, File[] files) {
        this.files = files;
        this.lif = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return files != null ? files.length : 0;
    }

    @Override
    public File getItem(int position) {
        return files[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = lif.inflate(R.layout.layout_item_file, null);
            holder = new ViewHolder();
            holder.imgIcn = (ImageView) convertView.findViewById(R.id.item_file_photo);
            holder.tvName = (TextView) convertView.findViewById(R.id.item_file_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        File file = getItem(position);
        if (file.isDirectory()) {
            holder.imgIcn.setImageResource(R.mipmap.me_accept_order);
        } else {
            holder.imgIcn.setImageResource(R.mipmap.me_accepted_order);
        }
        holder.tvName.setText(file.getName());

        return convertView;
    }


    public void setFiles(File[] files){

        this.files = files;
    }

    public void update(){
        // 刷新数据的显示
        notifyDataSetChanged();
    }


    class ViewHolder {
        TextView tvName;
        ImageView imgIcn;
    }


}
