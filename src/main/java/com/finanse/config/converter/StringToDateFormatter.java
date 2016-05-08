package com.finanse.config.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class StringToDateFormatter implements Converter<String, Date>, InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(StringToDateFormatter.class);

    @Value("${date.pattern.format}")
    private String DATE_PATTERN_FORMAT;

    private SimpleDateFormat formatter;

    @Override
    public void afterPropertiesSet() throws Exception {
        formatter = new SimpleDateFormat(DATE_PATTERN_FORMAT);
    }

    @Override
    public Date convert(String dateStr) {
        Date date;
        try {
            date = formatter.parse(dateStr);
        } catch (ParseException e) {
            LOG.error("Parse String to Date ", e);
            throw new IllegalArgumentException("Date format not correct");
        }
        return date;
    }
}
