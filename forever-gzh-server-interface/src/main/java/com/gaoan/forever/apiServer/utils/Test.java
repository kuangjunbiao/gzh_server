package com.gaoan.forever.apiServer.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

    public static void main(String[] args) {
	// TODO Auto-generated method stub
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:sss");
	Date date = new Date(1501120920000l);

	System.out.println(sf.format(date));
    }

}
