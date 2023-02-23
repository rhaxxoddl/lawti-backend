package com.oli.HometownPolitician.global.tool;

import java.util.List;

public class ListTool {
    public static <E> E getLastElement(List<E> list)
    {
        if((list != null) && (list.isEmpty() == false))
        {
            int lastIdx = list.size() - 1;
            return list.get(lastIdx);
        }
        else
            return null;
    }
}
