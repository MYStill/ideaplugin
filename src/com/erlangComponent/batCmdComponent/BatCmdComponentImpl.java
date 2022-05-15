package com.erlangComponent.batCmdComponent;

import com.erlangComponent.batCmdComponent.BatCmdComponent;
import com.utils.log.handle.Log;
import org.jetbrains.annotations.NotNull;

public class BatCmdComponentImpl implements BatCmdComponent {
    @NotNull
    @Override
    public String getComponentName() {
        return "BatCmdComponen";
    }

    //构造方法
    public BatCmdComponentImpl(){
        Log.debug("BatCmdComponentImpl 构造");
    }
    //通知一个project已经完成加载
    public void projectOpened() {
        Log.debug("BatCmdComponentImpl Opened");
    }

    public void projectClosed() {
        Log.debug("BatCmdComponentImpl Closed");
    }
    //执行初始化操作以及与其他 components 的通信
    public void initComponent() {
        Log.debug("BatCmdComponentImpl init");
    }
    //释放系统资源或执行其他清理
    public void disposeComponent() {
        Log.debug("BatCmdComponentImpl dispose");
    }
}
