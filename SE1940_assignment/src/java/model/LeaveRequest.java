/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

public class LeaveRequest {

    private int requestId;
    private int userId;
    private String title;
    private Date fromDate;
    private Date toDate;
    private String reason;
    private String status;
    private Integer processedBy;
    private String createdBy;
    private String processedByUsername;

    public LeaveRequest(int requestId, int userId, String title, Date fromDate, Date toDate, String reason, String status, Integer processedBy) {
        this.requestId = requestId;
        this.userId = userId;
        this.title = title;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reason = reason;
        this.status = status;
        this.processedBy = processedBy;
    }

    public int getRequestId() {
        return requestId;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    public Integer getProcessedBy() {
        return processedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getProcessedByUsername() {
        return processedByUsername;
    }

    public void setProcessedByUsername(String processedByUsername) {
        this.processedByUsername = processedByUsername;
    }
}
