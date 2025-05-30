package com.evol.model;

import java.sql.SQLException;
import java.util.List;

abstract class Model {
//    static List<? extends Model> selectAll();

    public abstract void insertToDB() throws SQLException;
}