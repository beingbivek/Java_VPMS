package vpms.model;

public class SlotInstanceData {
    private int    instanceId;
    private int    slotId;
    private int    slotIndex;
    private String code;        // human readable
    private String status;      // free / occupied / reserved / disabled
    private int    levelNumber; // convenience (JOIN)
    private String vehicleType; // convenience (JOIN)

    public SlotInstanceData() { }

    public SlotInstanceData(int instanceId,int slotId,int slotIndex,
                            String code,String status,
                            int levelNumber,String vehicleType){
        this.instanceId   = instanceId;
        this.slotId       = slotId;
        this.slotIndex    = slotIndex;
        this.code         = code;
        this.status       = status;
        this.levelNumber  = levelNumber;
        this.vehicleType  = vehicleType;
    }
    public String getCode()      { return code;   }
    public String getStatus()    { return status; }
    public void   setStatus(String s){ this.status = s; }
    public int    getInstanceId(){ return instanceId; }
}
