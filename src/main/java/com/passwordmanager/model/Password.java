package com.passwordmanager.model;

public class Password {
    private int id;
    private String siteName;
    private String siteUrl;
    private String username;
    private String encryptedPassword;
    private String category;
    private String notes;
    private int userId;

    public Password() {}

    // ESTE ES EL CONSTRUCTOR QUE NECESITAMOS
    public Password(int id, String siteName, String siteUrl, String username, String encryptedPassword, String category, String notes, int userId) {
        this.id = id;
        this.siteName = siteName;
        this.siteUrl = siteUrl;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.category = category;
        this.notes = notes;
        this.userId = userId;
    }

    // Getters y Setters (los que ya tenías están bien)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getSiteName() { return siteName; }
    public void setSiteName(String siteName) { this.siteName = siteName; }
    public String getSiteUrl() { return siteUrl; }
    public void setSiteUrl(String siteUrl) { this.siteUrl = siteUrl; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEncryptedPassword() { return encryptedPassword; }
    public void setEncryptedPassword(String encryptedPassword) { this.encryptedPassword = encryptedPassword; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}