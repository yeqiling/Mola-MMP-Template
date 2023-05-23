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
        System.out.println(virtualFilePath);

        // index.ts
        String tsFile = virtualFilePath + "/index.ts";
        String tsContent = "import { NewPage } from '~/utils/main'\n" +
                "import pageBack from '~/behaviors/page-back'\n" +
                "import { StoreAndBehavior, Data } from './types'\n" +
                "\n" +
                "class Index extends StoreAndBehavior {\n" +
                "  /**\n" +
                "   * 页面唯一标识，需要手动修改\n" +
                "   */\n" +
                "  name = 'index'\n" +
                "  behaviors = [pageBack]\n" +
                "  /**\n" +
                "   * 页面的初始数据\n" +
                "   */\n" +
                "  data: Data = {}\n" +
                "\n" +
                "  /**\n" +
                "   * 生命周期函数--监听页面加载\n" +
                "   */\n" +
                "  onLoad() { }\n" +
                "\n" +
                "  /**\n" +
                "   * 生命周期函数--监听页面显示\n" +
                "   */\n" +
                "  onShow() { }\n" +
                "}\n" +
                "\n" +
                "NewPage(new Index())";
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
                "  \"usingComponents\": {},\n" +
                "  \"hideCapsuleButtons\": true\n" +
                "}";
        try {
            Files.write(Paths.get(jsonFile), jsonContent.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        String lessFile = virtualFilePath + "/index.less";
        String lessContent = "/*use-double-px*/\n" +
                "@import '@client/styles/index.less';\n" +
                "page {\n" +
                "  .bg-main();\n" +
                "  \n" +
                "  min-height: 100%;\n" +
                "  box-sizing: border-box;\n" +
                "}";
        try {
            Files.write(Paths.get(lessFile), lessContent.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        String wxmlFile = virtualFilePath + "/index.wxml";
        String wxmlContent = "<view>\n" +
                "    <mola-navigation-bar\n" +
                "      safeAreaInsetTop=\"{{true}}\"\n" +
                "      fixed=\"{{true}}\"\n" +
                "      placeholder=\"{{true}}\"\n" +
                "      zIndex=\"{{2}}\"\n" +
                "      title=\"标题\"\n" +
                "      bind:click-left=\"onClickBack\"\n" +
                "    />\n" +
                "</view>";
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
