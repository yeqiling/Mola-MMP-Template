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

public class CreateMMPVMPage extends AnAction {

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
        String tsContent = "import { BaseView } from '@bike/mp-architecture'\n" +
                "import { Connect } from '@bike/mp-architecture-impl'\n" +
                "\n" +
                "import { router } from '~/utils/router/router'\n" +
                "\n" +
                "import VM from './view-model'\n" +
                "\n" +
                "class Index extends BaseView {" +
                "  /**\n" +
                "   * 页面唯一标识\n" +
                "   */\n" +
                "  name = '" + virtualFileName + "'\n" +
                "  /**\n" +
                "   * 页面的模型数据\n" +
                "   */\n" +
                "  vm = new VM()" +
                "\n" +
                "   /**\n" +
                "   * 生命周期函数--监听页面加载\n" +
                "   */\n" +
                "  onLoad(options: Record<string, string>) {\n" +
                "    this.vm.onLoad(options)\n" +
                "  }\n" +
                "\n" +
                "  onClickBack() {\n" +
                "    router.pageBackOrNative()\n" +
                "  }\n" +
                "  onUnload() {\n" +
                "    this.vm.onUnload()\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                "Connect(new Index(), VM)\n";
        try {
            Files.write(Paths.get(tsFile), tsContent.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        // ui-state.ts
        String uiStateFile = virtualFilePath + "/ui-state.ts";
        String uiStateContent = "import { BaseUiData } from '@bike/mp-architecture'\n" +
                "import { makeAutoObservable } from 'mobx'\n" +
                "\n" +
                "\n" +
                "export default class UIState implements BaseUiData {\n" +
                "\n" +
                "  constructor() {\n" +
                "    makeAutoObservable(this)\n" +
                "  }\n" +
                "\n" +
                "}";
        try {
            Files.write(Paths.get(uiStateFile), uiStateContent.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        // view-model.ts
        String viewModelFile = virtualFilePath + "/view-model.ts";
        String viewModelContent = "import { bindUIState, BaseModel } from '@bike/mp-architecture'\n" +
                "import { ViewModelImpl } from '@bike/mp-architecture-impl'\n" +
                "\n" +
                "import UIState from './ui-state'\n" +
                "\n" +
                "export default class indexVM extends ViewModelImpl<UIState> {\n" +
                "\n" +
                "  uiState: UIState\n" +
                "\n" +
                "  constructor() {\n" +
                "    super()\n" +
                "    this.createModel()\n" +
                "    this.uiState = new UIState()\n" +
                "  }\n" +
                "\n" +
                "  createModel(): BaseModel[] {\n" +
                "    return []\n" +
                "  }\n" +
                "\n" +
                "  createDataBinding(baseView) {\n" +
                "    // 绑定页面 baseView 是 Page/Component this\n" +
                "    return bindUIState<UIState>(baseView, {\n" +
                "      uiState: this.uiState,\n" +
                "      deepBind: false\n" +
                "    })\n" +
                "  }\n" +
                "\n" +
                "  onLoad(options) {\n" +
                "\n" +
                "  }\n" +
                "\n" +
                "  onUnload() {}\n" +
                "}\n";
        try {
            Files.write(Paths.get(viewModelFile), viewModelContent.getBytes());
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
                "  .mp-wrap-bg()\n" +
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
                .createNotification("mmp page viewModel模版已生成", NotificationType.INFORMATION)
                .notify(project);
    }
}
