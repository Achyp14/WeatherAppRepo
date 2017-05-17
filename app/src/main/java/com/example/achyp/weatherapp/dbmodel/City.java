package com.example.achyp.weatherapp.dbmodel;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * City object which is store in db.
 */
@Table(name = City.TABLE_NAME)
public class City extends Model {
    public static final String TABLE_NAME = "CITY";

    @Column(name = "cityName", unique = true, onUniqueConflict = Column.ConflictAction.FAIL)
    private String name;

    public City(){
        super();
    }

    public City(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
