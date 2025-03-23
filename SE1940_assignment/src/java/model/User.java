package model;

import java.sql.Date;

public class User {

    private int userId;
    private String username;
    private String password;
    private String fullName;
    private Integer divisionId; // Changed to Integer to allow null
    private Integer managerId;
    private String roleName;
    private String divisionName;
    private Date createdDate; // for agenda

    public User(int userId, String username, String password, String fullName, Integer divisionId, Integer managerId, String roleName, String divisionName, Date createdDate) {
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

    public Integer getDivisionId() {
        return divisionId;
    }

    public Integer getManagerId() {
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

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDivisionId(Integer divisionId) {
        this.divisionId = divisionId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
    
}
