package com.example.sf;

import com.example.CONSTANTS_MAIN;

/**
 * Created by sf on 18-3-22.
 */

public interface CONSTANTS_SF {

//    个人使用
    public static final String URL_ROOT= CONSTANTS_MAIN.URL_ROOT+"/maprelative/";
    public interface TABLE{
        public static final String ID="id";
        public static final String CREATED_AT="created_at";
        public static final String UPDATE_AT="update_at";

    }
    /*数据库中诗词表常量*/
    public interface TABLE_POETRIES extends TABLE{
        public static final String TABLE_NAME="poetries";
        public static final String POET_ID="poet_id";
        public static final String CONTENT="content";
        public static final String TITLE="title";
    }
    /*数据库诗人表常量*/
    public interface TABLE_POET extends TABLE{
        public static final String TABLE_NAME="poet";
        public static final String NAME="name";
    }
    public interface TABLE_POETRIES_L extends TABLE{
            public static final String TABLE_NAME="poetries_l";
            public static final String LONGTITUDE="longtitude";
            public static final String LATTITUDE="lattitude";
            public static final String CITY_OF_ANCIENT="city_of_ancient";
            public static final String CITY_OF_CURRENT="city_of_current";
            public static final String COUNTY_OF_ANCIENT="county_of_ancient";
            public static final String COUNTY_OF_CURRENT="county_of_current";
            public static final String DYNASTY="dynasty";
            public static final String AGE="age";
        public static final String TITLE="title";
        public static final String SEX="sex";
        public static final String POET_ID="poet_id";
    }
    public interface TABLE_POET_L extends TABLE{
        public static final String TABLE_NAME="poet_l";
        public static final String NAME="name";
    }

}
