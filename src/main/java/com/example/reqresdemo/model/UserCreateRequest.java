package com.example.reqresdemo.model;
public class UserCreateRequest {
    private String username; // Divergence: should be 'name'
    private String jobTitle; // Divergence: should be 'job'
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
}
