package com.example.notifythem.data;

import android.provider.BaseColumns;



public final class NotifyContract {

    public NotifyContract() {
    }

    public static abstract class NotifyEntry implements BaseColumns{

        public static final String TABLE_NAME="notifications";


        public static final String COLUMN_ID=BaseColumns._ID;
        public static final String COLUMN_NOTIFY_SENDERNAME="sendername";
        public static final String COLUMN_NOTIFY_TITLE="title";
        public static final String COLUMN_NOTIFY_CONTENT="content";
        public static final String COLUMN_NOTIFY_BRANCH="branch";
        public static final String COLUMN_NOTIFY_SECTION="section";
        public static final String COLUMN_NOTIFY_YEAR="year";



        public static final int BRANCH_COMPUTER_SCIENCE=1;
        public static final int BRANCH_INFORMATION_TECHNOLOGY=2;
        public static final int BRANCH_MECHANICAL=3;
        public static final int BRANCH_BIOTECHNOLOGY=4;
        public static final int BRANCH_ELECTRONICS=5;
        public static final int BRANCH_ELECTRICAL=6;

        public static final int SECTION_1=1;
        public static final int SECTION_2=2;

        public static final int YEAR_1=1;

        public static final int YEAR_2=2;
        public static final int YEAR_3=3;
        public static final int YEAR_4=4;








    }
}
