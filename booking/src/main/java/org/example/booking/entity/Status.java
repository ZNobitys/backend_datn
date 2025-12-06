package org.example.booking.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "STATUS")
public class Status {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "STATUS_ID")
    private int statusId;
    @Column(name = "STATUS_NAME")
    private String statusName;
    @Column(name = "STATUS_DESCRIPTION")
    private String statusDescription;

    public Status() {
    }

    public Status(String statusName, String statusDescription) {
        this.statusName = statusName;
        this.statusDescription = statusDescription;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
}
