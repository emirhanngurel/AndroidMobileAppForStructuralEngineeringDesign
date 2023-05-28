package com.gema.stairreinforcement;

import java.util.Date;

public class Calculation {

    private int numOfSteps;
    private int rise;
    private int runOfStep;
    private int widthOfStair;
    private int thickness;
    private int tru;
    private int tri;
    private double ss;
    private double meter;
    private double yard;
    private double feet;
    private Date date;
    private double lira;
    private double dollar;
    private double euro;

    public double getLira() {
        return lira;
    }

    public double getDollar() {
        return dollar;
    }

    public double getEuro() {
        return euro;
    }

    public void setLira(double lira) {
        this.lira = lira;
    }

    public void setDollar(double dollar) {
        this.dollar = dollar;
    }

    public void setEuro(double euro) {
        this.euro = euro;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Calculation(){}

    public int getNumOfSteps() {
        return numOfSteps;
    }

    public void setNumOfSteps(int numOfSteps) {
        this.numOfSteps = numOfSteps;
    }

    public int getRise() {
        return rise;
    }

    public void setRise(int rise) {
        this.rise = rise;
    }

    public int getRunOfStep() {
        return runOfStep;
    }

    public void setRunOfStep(int runOfStep) {
        this.runOfStep = runOfStep;
    }

    public int getWidthOfStair() {
        return widthOfStair;
    }

    public void setWidthOfStair(int widthOfStair) {
        this.widthOfStair = widthOfStair;
    }

    public int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public int getTru() {
        return tru;
    }

    public void setTru(int tru) {
        this.tru = tru;
    }

    public int getTri() {
        return tri;
    }

    public void setTri(int tri) {
        this.tri = tri;
    }

    public double getSs() {
        return ss;
    }

    public void setSs(double ss) {
        this.ss = ss;
    }

    public double getMeter() {
        return meter;
    }

    public void setMeter(double meter) {
        this.meter = meter;
    }

    public double getYard() {
        return yard;
    }

    public void setYard(double yard) {
        this.yard = yard;
    }

    public double getFeet() {
        return feet;
    }

    public void setFeet(double feet) {
        this.feet = feet;
    }
}
