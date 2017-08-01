package com.example.nataw.databasetest;

import android.provider.BaseColumns;

/**
 * Created by nataw on 7/31/2017.
 */

public final class FeedReaderContract {

    private FeedReaderContract() {

    }

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_TILE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";

    }
}
