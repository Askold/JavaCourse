package org.example.Models;

import org.example.Constants;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class HistoryContent implements Serializable {
    private static int count = 0;
    private static  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private long id = System.currentTimeMillis() + count;
    private String className;
    private String createdDate = dtf.format(LocalDateTime.now());
    private String actor = Constants.DEFAULT_ACTOR;
    private String methodName;
    private Status status = Status.SUCCESS;

    public HistoryContent(String className, String actor, String methodName, Status status) {
        this.className = className;
        this.actor = actor;
        setMethodName(methodName);
        setStatus(status);
        count++;
    }

    public HistoryContent(String className, String methodName, Status status) {
        this.className = className;
        setMethodName(methodName);
        setStatus(status);
        count++;
    }

    public HistoryContent(String className) {
        this.className = className;
        count++;
    }

    public HistoryContent() {
        count++;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        HistoryContent.count = count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "HistoryContent{" +
                "id=" + id +
                ", className='" + className + '\'' +
                ", createdDate=" + createdDate +
                ", actor='" + actor + '\'' +
                ", methodName='" + methodName + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoryContent)) return false;
        HistoryContent that = (HistoryContent) o;
        return getId() == that.getId() && getClassName().equals(that.getClassName()) && getCreatedDate().equals(that.getCreatedDate()) && getActor().equals(that.getActor()) && getMethodName().equals(that.getMethodName()) && getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getClassName(), getCreatedDate(), getActor(), getMethodName(), getStatus());
    }

    public enum Status{
        SUCCESS,
        FAULT
    }

}

