package com.mola.mmp;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CreateMMPVMComponent extends AnAction {


    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

        Project project = e.getProject();
        VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);

        String virtualFilePath = virtualFile.getPath();
        System.out.println(virtualFilePath);

        // index.ts
        String tsFile = virtualFilePath + "/index.ts";
        String tsContent = "import { ConnectComp } from '@bike/mp-architecture-impl'\n" +
                "\n" +
                "import VM from './view-model'\n" +
                "\n" +
                "ConnectComp(\n" +
                "  {\n" +
                "    options: {\n" +
                "      addGlobalClass: true\n" +
                "    },\n" +
                "    data: {\n" +
                "      _vm: {} as VM\n" +
                "    },\n" +
                "    properties: {\n" +
                "    },\n" +
                "    attached() {\n" +
                "      this.data._vm.onLoad()\n" +
                "    },\n" +
                "    detached() {\n" +
                "      this.data._vm.onUnload()\n" +
                "    },\n" +
                "    methods: {\n" +
                "\n" +
                "    }\n" +
                "  },\n" +
                "  VM\n" +
                ")\n" +
                "\n";
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
                "  triggerEvent: (\n" +
                "    name: string,\n" +
                "    detail?: any,\n" +
                "    options?: WechatMiniprogram.Component.TriggerEventOption\n" +
                "  ) => void = () => {}\n" +
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
                "  createDataBinding(view) {\n" +
                "    // 绑定 triggerEvent\n" +
                "    this.triggerEvent = view.triggerEvent\n" +
                "    // 绑定页面 baseView 是 Page/Component this\n" +
                "    return bindUIState(view, {\n" +
                "      uiState: this.uiState,\n" +
                "      deepBind: true\n" +
                "    })\n" +
                "  }\n" +
                "\n" +
                "  onLoad(options) {\n" +
                "  }\n" +
                "\n" +
                "  onUnload() {}\n" +
                "}\n";
        try {
            Files.write(Paths.get(viewModelFile), viewModelContent.getBytes());
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
                .createNotification("mmp Component viewModel模版已生成", NotificationType.INFORMATION)
                .notify(project);
    }
}
