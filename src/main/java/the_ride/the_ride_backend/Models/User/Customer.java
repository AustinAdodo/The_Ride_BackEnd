package the_ride.the_ride_backend.Models.User;

import the_ride.the_ride_backend.Models.BaseModels.UserBaseModel;

import java.util.List;
import java.util.UUID;

public class Customer extends UserBaseModel<UUID> {
    public String CurrentStreetName;
    public String CurrentTownName;
    public String CurrentLocalGovernmentName;
    public String CurrentDistrict;
    public String CurrentCity;
    public String CurrentState;
    public String CurrentProvince;
    public String DefaultHomeAddress = this.Address;
    public List<UUID> BlackListedDriversIds;
    public Customer() {
        super("customer");
    }
}
