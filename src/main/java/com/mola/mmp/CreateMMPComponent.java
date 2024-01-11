package com.mola.mmp;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CreateMMPComponent extends AnAction {


    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

        Project project = e.getProject();
        VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);

        String virtualFilePath = virtualFile.getPath();
        System.out.println(virtualFilePath);

        // index.ts
        String tsFile = virtualFilePath + "/index.ts";
        String tsContent = "import { NewComponent } from '@bike/utils'\n" +
                "import { Data } from './types'\n" +
                "\n" +
                "const data: Data = {}\n" +
                "\n" +
                "NewComponent({\n" +
                "  options: {\n" +
                "    multipleSlots: true,\n" +
                "    addGlobalClass: true\n" +
                "  },\n" +
                "  data: data,\n" +
                "  properties: {},\n" +
                "  methods: {}\n" +
                "})";
        try {
            Files.write(Paths.get(tsFile), tsContent.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        // types.ts
        String typeFile = virtualFilePath + "/types.ts";
        String typeContent = "export interface Data { }";
        try {
            Files.write(Paths.get(typeFile), typeContent.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


        // index.json
        String jsonFile = virtualFilePath + "/index.json";
        String jsonContent = "{\n" +
                "  \"component\": true,\n" +
                "  \"usingComponents\": { }\n" +
                "}";
        try {
            Files.write(Paths.get(jsonFile), jsonContent.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        // index.less
        String lessFile = virtualFilePath + "/index.less";
        String lessContent = "/*use-double-px*/";
        try {
            Files.write(Paths.get(lessFile), lessContent.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        // index.wxml
        String wxmlFile = virtualFilePath + "/index.wxml";
        String wxmlContent = "<view></view>";
        try {
            Files.write(Paths.get(wxmlFile), wxmlContent.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        NotificationGroupManager.getInstance()
                .getNotificationGroup("Mola MMP Notification")
                .createNotification("mmp Component 模版已生成", NotificationType.INFORMATION)
                .notify(project);
    }
}
