package com.sama_consulting.prolabmobile5.Util;

import android.content.Context;
import android.graphics.Color;

import com.sama_consulting.prolabmobile5.DAO.ConnectionClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UtilFunctions {


    public static String getSqlParamText(String param)
    {
        return  "'" + param.replace("'", "''") + "'";
    }

    public static String rtftToPlain(String rtf) {
        rtf = rtf.replaceAll("(?s)\\\\(par|line)(?=(\\W|\\w+\\s|\\s|[{}]))", "¬");
        rtf = rtf.replaceAll("\\{\\*?\\\\[^{}]+\\}|\\\\(?=\\\\)|\\\\(?=[{}])|(?<!\\\\)[{}]|(?<!\\\\)\\\\[A-Za-z]+\n?(?:-?\\d+)?[ ]?", "");
        String speceialChars = "‰;Š;‹;Œ;;Ž;;;‘;’;“;”;•;–;—;~;™;š;›;œ;;ž;Ÿ;;¡;¢;£;¤;¥;¦;§;¨;©;ª;«;¬;(-);®;¯;°;±;²;³;´;µ;¶;·;¸;¹;º;»;¼;½;¾;¿;À;Á;Â;Ã;Ä;Å;Æ;Ç;È;É;Ê;Ë;Ì;Í;Î;Ï;Ð;Ñ;Ò;Ó;Ô;Õ;Ö;×;Ø;Ù;Ú;Û;Ü;Ý;Þ;ß;à;á;â;ã;ä;å;æ;ç;è;é;ê;ë;ì;í;î;ï;ð;ñ;ò;ó;ô;õ;ö;÷;ø;ù;ú;û;ü;ý;þ;ÿ";
        String speceialCharsCode = "\\\\'89;\\\\'8a;\\\\'8b;\\\\'8c;\\\\'8d;\\\\'8e;\\\\'8f;\\\\'90;\\\\'91;\\\\'92;\\\\'93;\\\\'94;\\\\'95;\\\\'96;\\\\'97;\\\\'98;\\\\'99;\\\\'9a;\\\\'9b;\\\\'9c;\\\\'9d;\\\\'9e;\\\\'9f;\\\\~;\\\\'a1;\\\\'a2;\\\\'a3;\\\\'a4;\\\\'a5;\\\\'a6;\\\\'a7;\\\\'a8;\\\\'a9;\\\\'aa;\\\\'ab;\\\\'ac;\\\\-;\\\\'ae;\\\\'af;\\\\'b0;\\\\'b1;\\\\'b2;\\\\'b3;\\\\'b4;\\\\'b5;\\\\'b6;\\\\'b7;\\\\'b8;\\\\'b9;\\\\'ba;\\\\'bb;\\\\'bc;\\\\'bd;\\\\'be;\\\\'bf;\\\\'c0;\\\\'c1;\\\\'c2;\\\\'c3;\\\\'c4;\\\\'c5;\\\\'c6;\\\\'c7;\\\\'c8;\\\\'c9;\\\\'ca;\\\\'cb;\\\\'cc;\\\\'cd;\\\\'ce;\\\\'cf;\\\\'d0;\\\\'d1;\\\\'d2;\\\\'d3;\\\\'d4;\\\\'d5;\\\\'d6;\\\\'d7;\\\\'d8;\\\\'d9;\\\\'da;\\\\'db;\\\\'dc;\\\\'dd;\\\\'de;\\\\'df;\\\\'e0;\\\\'e1;\\\\'e2;\\\\'e3;\\\\'e4;\\\\'e5;\\\\'e6;\\\\'e7;\\\\'e8;\\\\'e9;\\\\'ea;\\\\'eb;\\\\'ec;\\\\'ed;\\\\'ee;\\\\'ef;\\\\'f0;\\\\'f1;\\\\'f2;\\\\'f3;\\\\'f4;\\\\'f5;\\\\'f6;\\\\'f7;\\\\'f8;\\\\'f9;\\\\'fa;\\\\'fb;\\\\'fc;\\\\'fd;\\\\'fe;\\\\'ff";
        String chars[] = speceialChars.split(";");
        String charscodes[] = speceialCharsCode.split(";");
        for (int i = 0; i < chars.length; i++) {
            rtf = rtf.replaceAll(charscodes[i], chars[i]);
        }
        rtf = rtf.replaceAll("Times New Roman \\(Arabic\\);", "");
        rtf = rtf.replaceAll("Arial \\(Arabic\\);", "");
        rtf = rtf.replaceAll("¬", "\n");
        rtf = rtf.trim();
        return rtf;
    }

    public static int getColor(int rFlag, int status) {
        if (status >= 100) return Color.argb(100, 250, 50, 50);
        //    if ((status & 4L)>0)  return Color.argb(100,50,250,50);
        switch (rFlag) {
            case 0:
                return Color.argb(100, 250, 250, 250);
            case 1:
                return 0x00C0FFC0;
            case 2:
                return 0x00000080;
            case 3:
                return 0x00FFFFFF;
            case 4:
                return 0x00FFFFFF;
            case 5:
                return Color.argb(100, 250, 250, 250);
            case 6:
                return Color.argb(100, 200, 200, 200);
            case 7:
                return Color.argb(100, 150, 250, 150);
            case 8:
                return Color.argb(100, 250, 150, 150);
            case 9:
                return Color.argb(100, 250, 50, 50);
        }
        return 0x00FFFFFF;
    }

    public static ResultSet executeQuery(Context mContext, String sqlQuery) {
        Statement stmt = null;
        ResultSet rs = null;
        Connection cn = ConnectionClass.getConnection(mContext);
        if (cn == null) {
            return null;
        }
        try{
            stmt = cn.createStatement();
            rs = stmt.executeQuery(sqlQuery);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static boolean executeUpdate(Context mContext, String sqlQuery) {
        Statement stmt = null;
        Connection cn = ConnectionClass.getConnection(mContext);
        boolean rs = false;
        try {
            if (cn == null) {
                return false;
            }
            stmt = cn.createStatement();
            stmt.executeUpdate(sqlQuery);
            rs = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static String encrypt(String str) {
        if (str.equals("")) {
            return str;
        } else {
            String ss = "";
            for (int i = 0; i < str.length(); i++) {
                char sub = str.substring(i, i + 1).charAt(0);
                int asc = ((int) sub) - 10;
                char fChar = (char) asc;
                ss = ss + fChar;
            }
            return ss;
        }
    }

    public static String decrypt(String str) {
        if (str.equals("")) {
            return str;
        } else {
            String ss = "";
            for (int i = 0; i < str.length(); i++) {
                char sub = str.substring(i, i + 1).charAt(0);
                int asc = ((int) sub) + 10;
                char fChar = (char) asc;
                ss = ss + fChar;
            }
            return ss;
        }
    }
}
