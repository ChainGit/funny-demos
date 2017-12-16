package com.chain.project.common.converter;

import com.chain.project.common.utils.ChainProjectUtils;

import java.beans.PropertyEditorSupport;
import java.util.Date;

/**
 * 用于注解
 */
public class DatePropertyEditorSupport extends PropertyEditorSupport {

    @Override
    public void setAsText(String s) throws IllegalArgumentException {
        Date date = null;
        if (!ChainProjectUtils.isEmpty(s))
            date = ChainProjectUtils.parseDateFormString(s);
        this.setValue(date);
    }

}