/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.model;

/**
 *
 * @author Rupes
 */
public class SlotData {
//    slot_id, vehicletandp, number_of_slot,level_number
    private int slot_id;
    private int vehicletandp;
    private int number_of_slot;
    private int level_number;
    
    public SlotData(int vehicletandp,int number_of_slot,int level_number){
        this.vehicletandp=vehicletandp;
        this.number_of_slot= number_of_slot;
        this.level_number=level_number;          
    }
    public SlotData(int slot_id,int vehicletandp,int number_of_slot,int level_number){
        this.slot_id=slot_id;
        this.vehicletandp=vehicletandp;
        this.number_of_slot= number_of_slot;
        this.level_number=level_number;  
    }
    public SlotData(){
        
    }

    public int getSlot_id() {
        return slot_id;
    }

    public void setSlot_id(int slot_id) {
        this.slot_id = slot_id;
    }

    public int getVehicletandp() {
        return vehicletandp;
    }

    public void setVehicletandp(int vehicletandp) {
        this.vehicletandp = vehicletandp;
    }

    public int getNumber_of_slot() {
        return number_of_slot;
    }

    public void setNumber_of_slot(int number_of_slot) {
        this.number_of_slot = number_of_slot;
    }

    public int getLevel_number() {
        return level_number;
    }

    public void setLevel_number(int level_number) {
        this.level_number = level_number;
    }
    
    
            
            
            
            
    
            
}
