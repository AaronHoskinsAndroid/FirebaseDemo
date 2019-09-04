package examples.aaronhoskins.com.firebasedemo;

import java.util.ArrayList;

public class Profile {
    private String name;
    private String age;
    private String gender;
    private ArrayList<String> interestList;

    public Profile(String name, String age, String gender, ArrayList<String> interestList) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.interestList = interestList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ArrayList<String> getInterestList() {
        return interestList;
    }

    public void setInterestList(ArrayList<String> interestList) {
        this.interestList = interestList;
    }
}
