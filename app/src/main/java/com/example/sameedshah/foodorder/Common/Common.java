package com.example.sameedshah.foodorder.Common;

import com.example.sameedshah.foodorderserver.Model.User;

public class Common {

    public static User currentUser;


    public static   String convertCodeToStatus(String status) {

        if("0".equals(status))
            return "Placed";
        else if("1".equals(status))
            return "On my way";

        else
            return "Shipped";

    }

}
