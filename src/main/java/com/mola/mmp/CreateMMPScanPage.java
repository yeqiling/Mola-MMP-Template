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

public class CreateMMPScanPage extends AnAction {

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
        String tsContent = "import { Connect, BaseWidget } from '@bike/mp-architecture-impl'\n" +
                "import Widget, { ActionType } from '@bike/mp-widget'\n" +
                "import { selfOrEmpty } from '@bike/utils'\n" +
                "import { Toast } from '@mola/toast/toast'\n" +
                "\n" +
                "import { router } from '~/utils/router/router'\n" +
                "\n" +
                "import VM from './view-model'\n" +
                "\n" +
                "class Index extends BaseWidget {\n" +
                "  name = '" + virtualFileName + "'\n" +
                "  vm = new VM()\n" +
                "  bizCode = ''\n" +
                "  warehouseCode = ''\n" +
                "  bikeId = ''\n" +
                "  /**\n" +
                "   * 生命周期函数--监听页面加载\n" +
                "   */\n" +
                "  onLoad(options: Record<string, string>) {\n" +
                "    this.warehouseCode = options.warehouseCode\n" +
                "    this.bikeId = selfOrEmpty(options.bikeId)\n" +
                "    this.vm.onLoad(options)\n" +
                "\n" +
                "    Toast.text('测试toast').duration(3000).show()\n" +
                "  }\n" +
                "\n" +
                "  onComplete() {\n" +
                "    console.log('onComplete')\n" +
                "    // 返回给上一页面结果\n" +
                "    this.onBack()\n" +
                "  }\n" +
                "\n" +
                "  onAction(e: WechatMiniprogram.CustomEvent) {\n" +
                "    const { type } = e?.detail || {}\n" +
                "    if (type === ActionType.TYPE_INPUT) {\n" +
                "      this.navigateToUrlForResult(\n" +
                "        'mola://mmp.mola-mmp/npm/common-page/common-input/index?type=bike_id'\n" +
                "      )\n" +
                "    }\n" +
                "  }\n" +
                "\n" +
                "  onReceiveResult(defaultResult, exchangeBikeIdResult) {\n" +
                "    const { data = '' } = exchangeBikeIdResult\n" +
                "    if (data) {}\n" +
                "  }\n" +
                "\n" +
                "  onScan(defaultResult, exchangeBikeIdResult) {\n" +
                "    const { data } = exchangeBikeIdResult\n" +
                "    if (data) {}\n" +
                "  }\n" +
                "  onUnload() {\n" +
                "    this.vm.onUnload()\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                "Connect(new Index(), VM, Widget.scanWidget)";
        try {
            Files.write(Paths.get(tsFile), tsContent.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        // ui-state.ts
        String uiStateFile = virtualFilePath + "/ui-state.ts";
        String uiStateContent = "import { BaseUiData } from '@bike/mp-architecture'\n" +
                "import { ActionType, NoticeStatus } from '@bike/mp-widget'\n" +
                "import { makeAutoObservable } from 'mobx'\n" +
                "\n" +
                "export default class UIState implements BaseUiData {\n" +
                "  title = '扫码'\n" +
                "  showNotice = true\n" +
                "  noticeContent = '请扫车码'\n" +
                "  noticeStatus = NoticeStatus.NORMAL\n" +
                "  actionList = [\n" +
                "    { name: '输入设备编号', type: ActionType.TYPE_INPUT, url: '/qcloud/btn_scan_input.png' },\n" +
                "    { name: '打开手电筒', type: ActionType.TYPE_TORCH, url: '/qcloud/btn_scan_torch.png' }\n" +
                "  ]\n" +
                "  disableScanCodeRequest = false\n" +
                "  disableInputCodeRequest = false\n" +
                "  rightList = []\n" +
                "  constructor() {\n" +
                "    makeAutoObservable(this)\n" +
                "  }\n" +
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
                "  uiState: UIState\n" +
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
                "  onLoad(options) {}\n" +
                "\n" +
                "  onUnload() {}\n" +
                "}";
        try {
            Files.write(Paths.get(viewModelFile), viewModelContent.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        String jsonFile = virtualFilePath + "/index.json";
        String jsonContent = "{\n" +
                "  \"navigationStyle\": \"custom\",\n" +
                "  \"widgetBackgroundColor\": \"#00000000\",\n" +
                "  \"usingComponents\": {\n" +
                "    \"mola-scan\": \"@mola/scan/index\",\n" +
                "    \"mola-toast\": \"@mola/toast/index\"\n" +
                "  }\n" +
                "}";
        try {
            Files.write(Paths.get(jsonFile), jsonContent.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        String lessFile = virtualFilePath + "/index.less";
        String lessContent = "/* use-double-px */\n" +
                "@import (reference) '@npm/common-style/index.less';\n" +
                "page {\n" +
                "  height: 100%;\n" +
                "  background-color: transparent;\n" +
                "}\n" +
                ".container {\n" +
                "  display: flex;\n" +
                "  flex-direction: column;\n" +
                "  width: 100%;\n" +
                "  height: 100%;\n" +
                "  background-color: transparent;\n" +
                "}";
        try {
            Files.write(Paths.get(lessFile), lessContent.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        String wxmlFile = virtualFilePath + "/index.wxml";
        String wxmlContent = "<mola-scan\n" +
                "  class=\"container\"\n" +
                "  title=\"{{title}}\"\n" +
                "  showNotice=\"{{showNotice}}\"\n" +
                "  noticeStatus=\"{{noticeStatus}}\"\n" +
                "  noticeContent=\"{{noticeContent}}\"\n" +
                "  actionList=\"{{actionList}}\"\n" +
                "  catch:back=\"onBack\"\n" +
                "  catch:complete=\"onComplete\"\n" +
                "  catch:action=\"onAction\"\n" +
                "  rightList=\"{{rightList}}\"\n" +
                "/>\n" +
                "\n" +
                "<mola-toast id=\"toast\" />";
        try {
            Files.write(Paths.get(wxmlFile), wxmlContent.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        NotificationGroupManager.getInstance()
                .getNotificationGroup("Mola MMP Notification")
                .createNotification("mmp scan 页面模版已生成", NotificationType.INFORMATION)
                .notify(project);
    }
}
