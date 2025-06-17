package vpms.model;

/**
 * POJO for a parking slot.
 * Database columns already present:
 *    slot_id (PK), vehicletandp_id, number_of_slot, level_number
 * Extra runtime columns for the UI grid:
 *    slotIndex (0-based index inside its level)
 *    status    (free, occupied, reserved, disabled)
 *    vehicleTypeName (cached name, e.g. “Car”)
 */
public class SlotData {

    /* ------------ persistent fields ------------ */
    private int slot_id;
    private int vehicletandp;      // FK → vehicle_type_and_price.id
    private int number_of_slot;    // total slots of this VT on this level
    private int level_number;

    /* ------------ transient / UI fields -------- */
    private int    slotIndex;          // 0 .. (number_of_slot-1)
    private String status   = "free";  // default
    private String vehicleTypeName;    // “Car”, “Bike”, “EV”… (optional cache)

    /* ------------ constructors ------------ */
    public SlotData() { }

    /* for fresh inserts (id auto-generated) */
    public SlotData(int vehicletandp, int number_of_slot, int level_number) {
        this.vehicletandp = vehicletandp;
        this.number_of_slot = number_of_slot;
        this.level_number = level_number;
    }

    /* full constructor when row already exists */
    public SlotData(int slot_id, int vehicletandp,
                    int number_of_slot, int level_number) {
        this(vehicletandp, number_of_slot, level_number);
        this.slot_id = slot_id;
    }

    /* ------------ getters & setters ------------ */
    public int    getSlot_id()         { return slot_id; }
    public void   setSlot_id(int id)   { this.slot_id = id; }

    public int    getVehicletandp()    { return vehicletandp; }
    public void   setVehicletandp(int v){ this.vehicletandp = v; }

    public int    getNumber_of_slot()  { return number_of_slot; }
    public void   setNumber_of_slot(int n){ this.number_of_slot = n; }

    public int    getLevel_number()    { return level_number; }
    public void   setLevel_number(int l){ this.level_number = l; }

    /* extra UI helpers */
    public int    getSlotIndex()       { return slotIndex; }
    public void   setSlotIndex(int i)  { this.slotIndex = i; }

    public String getStatus()          { return status; }
    public void   setStatus(String s)  { this.status = s; }

    public String getVehicleTypeName()            { return vehicleTypeName; }
    public void   setVehicleTypeName(String vName){ this.vehicleTypeName = vName; }

    /* ------------ convenience ------------ */
    @Override public String toString() {
        return "SlotData{" +
               "id="     + slot_id +
               ", vt="   + vehicletandp +
               ", lvl="  + level_number +
               ", idx="  + slotIndex +
               ", status="+ status +
               '}';
    }
}
