package com.myerlangplugin.findChinese.help;

public class ChinesePojo {

    private String ModName = "";
    private int line = 0;
    private String chinese = "";

    public String getModName() {
        return ModName;
    }

    public void setModName(String modName) {
        ModName = modName;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    @Override
    public String toString() {
        return
                ModName +"" +
                "[" + line + "]" +
                chinese + "\n\r";
    }
}