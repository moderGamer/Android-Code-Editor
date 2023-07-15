package android.code.editor.files.utils;

import android.code.editor.activity.CodeEditorActivity;
import android.code.editor.activity.FileManagerActivity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import editor.tsd.tools.Language;

import java.io.File;

public class FileTypeHandler {
    private File file;
    private View view;
    private Context context;
    public FileManagerActivity fileManagerActivity;

    public FileTypeHandler(Context context, FileManagerActivity fileManagerActivity) {
        this.context = context;
        this.fileManagerActivity = fileManagerActivity;
    }

    public void handleFile(File file) {
        this.file = file;
    }

    public void setTargetView(View view) {
        this.view = view;
    }

    public void startHandling() {

        if (file.isDirectory()) {
            view.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fileManagerActivity.loadFileList(file.getAbsolutePath());
                        }
                    });
        } else {
            final File FinalFile = file;
            switch (getFileFormat(file.getAbsolutePath())) {
                case "java":
                case "xml":
                case "html":
                case "css":
                case "js":
                case "md":
                    view.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.putExtra("path", file.getAbsolutePath());
                                    intent.setClass(context, CodeEditorActivity.class);
                                    context.startActivity(intent);
                                }
                            });
                    break;
                default:
                    view.setOnClickListener(null);
                    break;
            }
        }
    }

    public static String getFileFormat(String path) {
        int index = path.lastIndexOf('.');
        if (index > 0) {
            String extension = path.substring(index + 1);
            return extension;
        } else {
            return "";
        }
    }
}
