package model;

import java.sql.Date;

public class User {

    private int userId;
    private String username;
    private String password;
    private String fullName;
    private int divisionId;
    private int managerId;
    private String roleName;
    private String divisionName;
    private Date createdDate; // for agenda

    public User(int userId, String username, String password, String fullName, int divisionId, int managerId, String roleName, String divisionName, Date createdDate) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.divisionId = divisionId;
        this.managerId = managerId;
        this.roleName = roleName;
        this.divisionName = divisionName;
        this.createdDate = createdDate;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public int getManagerId() {
        return managerId;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public Date getCreatedDate() {
        return createdDate;
    } // Getter for createdDate

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
