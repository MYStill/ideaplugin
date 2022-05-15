package com.myerlangplugin.checkcirculate.pojo;

public class ModAndMethod {
    private String mod = ""; // 模块名称

    private String curMod = ""; //当前调用模块

    private String method = ""; // 方法名称

    private String modAndMethod = ""; // Mod:Method

    private Integer line = 0; // 在第几行调用


    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public String getMod() {
        return mod;
    }

    public void setMod(String mod) {
        this.mod = mod;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getModAndMethod() {
        return modAndMethod;
    }

    public void setModAndMethod(String modAndMethod) {
        this.modAndMethod = modAndMethod;
    }

    public String getCurMod() {
        return curMod;
    }

    public void setCurMod(String curMod) {
        this.curMod = curMod;
    }

    @Override
    public String toString() {
        return "ModAndMethod{" +
                "mod='" + mod + '\'' +
                ", curMod='" + curMod + '\'' +
                ", method='" + method + '\'' +
                ", modAndMethod='" + modAndMethod + '\'' +
                ", line=" + line +
                '}';
    }
}
