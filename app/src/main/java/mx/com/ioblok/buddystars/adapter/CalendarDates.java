package mx.com.ioblok.buddystars.adapter;

import io.realm.RealmObject;

/**
 * Created by omar on 4/11/16.
 */
public class CalendarDates extends RealmObject {

    private String schedule;
    private String register_id;
    private String name;
    private String phone;
    private String email;

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getRegister_id() {
        return register_id;
    }

    public void setRegister_id(String register_id) {
        this.register_id = register_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
