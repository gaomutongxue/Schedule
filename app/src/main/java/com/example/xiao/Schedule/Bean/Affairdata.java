package com.example.xiao.Schedule.Bean;

import java.util.Date;

/**
 * Created by xiaowentao85336773 on 2018/3/5.
 */

public class Affairdata {
    private String _affairName;
    private String _username;
    private int _type;
    private int _level;
    private String _note;
    private Date _startTime;
    private Date _endTime;

    public String get_affairName() {
        return _affairName;
    }

    public void set_affairName(String _affairName) {
        this._affairName = _affairName;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public int get_type() {
        return _type;
    }

    public void set_type(int _type) {
        this._type = _type;
    }

    public int get_level() {
        return _level;
    }

    public void set_level(int _level) {
        this._level = _level;
    }

    public String get_note() {
        return _note;
    }

    public void set_note(String _note) {
        this._note = _note;
    }

    public Date get_startTime() {
        return _startTime;
    }

    public void set_startTime(Date _startTime) {
        this._startTime = _startTime;
    }

    public Date get_endTime() {
        return _endTime;
    }

    public void set_endTime(Date _endTime) {
        this._endTime = _endTime;
    }



}
