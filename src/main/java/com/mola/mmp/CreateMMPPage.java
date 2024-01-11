package com.mola.mmp;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CreateMMPPage extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);

        String virtualFilePath = virtualFile.getPath();
        String virtualFileName = virtualFile.getName();
        System.out.println(virtualFilePath);
        System.out.println(virtualFileName);

        // index.ts
        String tsFile = virtualFilePath + "/index.ts";
        String tsContent = "import { NewPage, pageBack } from '@bike/utils\n" +
                "import { StoreAndBehavior, Data } from './types'\n" +
                "import { Toast } from '@mola/toast/toast'\n" +
                "\n" +
                "class Index extends StoreAndBehavior {\n" +
                "  /**\n" +
                "   * 页面唯一标识\n" +
                "   */\n" +
                "  name = '" + virtualFileName + "'\n" +
                "  /**\n" +
                "   * 页面的初始数据\n" +
                "   */\n" +
                "  data: Data = {}\n" +
                "\n" +
                "  /**\n" +
                "   * 生命周期函数--监听页面加载\n" +
                "   */\n" +
                "  onLoad(options: Record<string, string>) { }\n" +
                "\n" +
                "  /**\n" +
                "   * 生命周期函数--监听页面显示\n" +
                "   */\n" +
                "  onShow() { }\n" +
                "\n" +
                "  /**\n" +
                "   * 返回上一级页面响应事件\n" +
                "   */\n" +
                "  onClickBack() {\n" +
                "    pageBack()\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                "NewPage(new Index())\n";
        try {
            Files.write(Paths.get(tsFile), tsContent.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        // types.ts
        String typeFile = virtualFilePath + "/types.ts";
        String typeContent = "import { BasicPage } from '~/typings/basic'\n" +
                "\n" +
                "export interface Data {}\n" +
                "\n" +
                "export class StoreAndBehavior extends BasicPage<Data> { }";
        try {
            Files.write(Paths.get(typeFile), typeContent.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


        String jsonFile = virtualFilePath + "/index.json";
        String jsonContent = "{\n" +
                "  \"navigationStyle\": \"custom\",\n" +
                "  \"usingComponents\": {\n" +
                "    \"mola-navigation-bar\": \"@mola/navigation-bar/index\",\n" +
                "    \"mola-toast\": \"@mola/toast/index\"\n" +
                "  },\n" +
                "  \"hideCapsuleButtons\": true\n" +
                "}";
        try {
            Files.write(Paths.get(jsonFile), jsonContent.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        String lessFile = virtualFilePath + "/index.less";
        String lessContent = "/*use-double-px*/\n" +
                "@import (reference) '@npm/common-style/index.less';\n" +
                "." + virtualFileName + "-wrap {\n" +
                "  .wrap-bg()\n" +
                "}";
        try {
            Files.write(Paths.get(lessFile), lessContent.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        String wxmlFile = virtualFilePath + "/index.wxml";
        String wxmlContent = "<view class=\"" + virtualFileName + "-wrap\">\n" +
                "  <mola-navigation-bar\n" +
                "    safeAreaInsetTop=\"{{true}}\"\n" +
                "    fixed=\"{{true}}\"\n" +
                "    placeholder=\"{{true}}\"\n" +
                "    zIndex=\"{{6}}\"\n" +
                "    title=\"标题\"\n" +
                "    bind:click-left=\"onClickBack\"\n" +
                "  />\n" +
                "</view>\n" +
                "<mola-toast id=\"toast\" />";
        try {
            Files.write(Paths.get(wxmlFile), wxmlContent.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        NotificationGroupManager.getInstance()
                .getNotificationGroup("Mola MMP Notification")
                .createNotification("mmp page 模版已生成", NotificationType.INFORMATION)
                .notify(project);
    }
}
