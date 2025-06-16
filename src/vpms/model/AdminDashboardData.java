/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.model;

/**
 *
 * @author PRABHASH
 */
public class AdminDashboardData {
    private int totalActiveStaff;
    private int currentlyOccupiedSlots;
    private int vehiclesEnteredToday;
    private int vehiclesExitedToday;
    private String totalEarningsToday;
    
    public AdminDashboardData(int totalActiveStaff, int currentlyOccupiedSlots, int vehiclesEnteredToday, int vehiclesExitedToday, String totalEarningsToday) {
        this.totalActiveStaff = totalActiveStaff;
        this.currentlyOccupiedSlots = currentlyOccupiedSlots;
        this.vehiclesEnteredToday = vehiclesEnteredToday;
        this.vehiclesExitedToday = vehiclesExitedToday;
        this.totalEarningsToday = totalEarningsToday;
    }

    public int getTotalActiveStaff() {
        return totalActiveStaff;
    }

    public void setTotalActiveStaff(int totalActiveStaff) {
        this.totalActiveStaff = totalActiveStaff;
    }

    public int getCurrentlyOccupiedSlots() {
        return currentlyOccupiedSlots;
    }

    public void setCurrentlyOccupiedSlots(int currentlyOccupiedSlots) {
        this.currentlyOccupiedSlots = currentlyOccupiedSlots;
    }

    public int getVehiclesEnteredToday() {
        return vehiclesEnteredToday;
    }

    public void setVehiclesEnteredToday(int vehiclesEnteredToday) {
        this.vehiclesEnteredToday = vehiclesEnteredToday;
    }

    public int getVehiclesExitedToday() {
        return vehiclesExitedToday;
    }

    public void setVehiclesExitedToday(int vehiclesExitedToday) {
        this.vehiclesExitedToday = vehiclesExitedToday;
    }

    public String getTotalEarningsToday() {
        return totalEarningsToday;
    }

    public void setTotalEarningsToday(String totalEarningsToday) {
        this.totalEarningsToday = totalEarningsToday;
    }
}
