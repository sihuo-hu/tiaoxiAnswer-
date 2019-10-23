package com.royal.admin.core.util;

import java.util.concurrent.CopyOnWriteArraySet;

public class QuestionPaperUtil {
    public static final Integer COUNT_DOWN_TIME = -60*60;

    public static CopyOnWriteArraySet<String> southUserIdSet = new CopyOnWriteArraySet<String>();
    public static CopyOnWriteArraySet<String> northUserIdSet = new CopyOnWriteArraySet<String>();
    public static CopyOnWriteArraySet<String> userIdSet = new CopyOnWriteArraySet<String>();
    public static String[] titleNames = new String[]{"射潮先锋","清涟自在","八面出锋"};
}
