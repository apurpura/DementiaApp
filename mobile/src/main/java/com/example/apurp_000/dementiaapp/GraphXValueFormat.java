package com.example.apurp_000.dementiaapp;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by apurpura on 8/1/2015.
 */
public class GraphXValueFormat extends Format {

        private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        @Override
        public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
            long timestamp = ((Number) obj).longValue();
            Date date = new Date(timestamp);
            return dateFormat.format(date, toAppendTo, pos);
        }

        @Override
        public Object parseObject(String string, ParsePosition position) {
            return null;
        }



}
