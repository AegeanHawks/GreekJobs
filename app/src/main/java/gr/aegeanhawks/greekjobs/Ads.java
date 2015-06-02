package gr.aegeanhawks.greekjobs;

import java.io.Serializable;

/**
 * Created by Nickos on 2/6/2015.
 */
public class Ads implements Serializable {
    private String Title, Area, Company, Specialty, Contact, Description;
    private Integer Type;

    public Ads(String Title, String Area, String Company, Integer Type, String Specialty, String Contact, String Description) {
        this.Title = Title;
        this.Area = Area;
        this.Company = Company;
        this.Type = Type;
        this.Specialty = Specialty;
        this.Contact = Contact;
        this.Description = Description;
    }

    public String getTitle() {
        return Title;
    }

    public String getArea() {
        return Area;
    }

    public String getCompany() {
        return Company;
    }

    public Integer getType() {
        return Type;
    }

    public String getSpecialty() {
        return Specialty;
    }

    public String getContact() {
        return Contact;
    }

    public String getDescription() {
        return Description;
    }

    public String toString() {
        return "Title: " + Title + " Area: " + Area + " Company: " + Company + " Type: " + Type.toString() + " Specialty: " + Specialty + " Contact: " + Contact + " Description: " + Description;
    }
}