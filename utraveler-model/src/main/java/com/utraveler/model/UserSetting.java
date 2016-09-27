package com.utraveler.model;

import java.util.Map;

import com.google.common.collect.Maps;

public class UserSetting {

    private static final String CODE_LIMIT_DELIMITER = ";";
    public static final String LC7U12RI3THJX = "LC7U12RI3THJX";
    public static final String LCUV5T03R93GV = "LCUV5T03R93GV";
    private static final Map<String, String> LIMITS = Maps.newHashMap();


    static {
        LIMITS.put(LC7U12RI3THJX, "false;10;25;25;50;1;false");
        LIMITS.put(LCUV5T03R93GV, "true;20;80;80;100;5;true");
    }


    private long id;
    private String mainColor;
    private String backgroundColor;
    private String textColor;
    private String limitCode;
    private float coverOpacity;
    private boolean isLandscapeCover;


    public UserSetting() {
        coverOpacity = 1;
        limitCode = LC7U12RI3THJX;
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public String getMainColor() {
        return mainColor;
    }


    public void setMainColor(String mainColor) {
        this.mainColor = mainColor;
    }


    public String getBackgroundColor() {
        return backgroundColor;
    }


    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }


    public String getTextColor() {
        return textColor;
    }


    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }


    public float getCoverOpacity() {
        return coverOpacity;
    }


    public void setCoverOpacity(float coverOpacity) {
        this.coverOpacity = coverOpacity;
    }


    public boolean isLandscapeCover() {
        return isLandscapeCover;
    }


    public void setLandscapeCover(boolean isLandscapeCover) {
        this.isLandscapeCover = isLandscapeCover;
    }


    public String getLimitCode() {
        return limitCode;
    }


    public void setLimitCode(String limitCode) {
        if (limitCode.equals(LC7U12RI3THJX) || limitCode.equals(LCUV5T03R93GV)) {
            this.limitCode = limitCode;
        }
    }


    public boolean isExtendedColors() {
        String[] splitValues = getLimits().split(CODE_LIMIT_DELIMITER);
        return Boolean.parseBoolean(splitValues[0]);
    }


    public int getEventsLimit() {
        String[] splitValues = getLimits().split(CODE_LIMIT_DELIMITER);
        return Integer.parseInt(splitValues[1]);
    }


    public int getPhotosLimit() {
        String[] splitValues = getLimits().split(CODE_LIMIT_DELIMITER);
        return Integer.parseInt(splitValues[2]);
    }


    public int getMessagesLimit() {
        String[] splitValues = getLimits().split(CODE_LIMIT_DELIMITER);
        return Integer.parseInt(splitValues[3]);
    }


    public int getMoneySpendingsLimit() {
        String[] splitValues = getLimits().split(CODE_LIMIT_DELIMITER);
        return Integer.parseInt(splitValues[4]);
    }


    public int getRoutesLimit() {
        String[] splitValues = getLimits().split(CODE_LIMIT_DELIMITER);
        return Integer.parseInt(splitValues[5]);
    }


    private String getLimits() {
        String limits = LIMITS.get(limitCode);
        if (limits == null) {
            limits = LIMITS.get(LC7U12RI3THJX);
        }
        return limits;
    }
}
