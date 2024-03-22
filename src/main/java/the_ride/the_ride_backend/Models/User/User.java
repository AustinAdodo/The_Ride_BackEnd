package the_ride.the_ride_backend.Models.User;

import the_ride.the_ride_backend.Models.BaseModels.UserBaseModel;

import java.util.List;
import java.util.UUID;

public class User extends UserBaseModel<UUID> {
    public String StreetName;
    public String TownName;
    public String LocalGovernmentName;
    public String District;
    public String City;
    public String State;
    public String Province;
    public String DefaultHomeAddress = this.Address;
    public List<UUID> BlackListedDriversIds;
    public User() {
        super("customer");
    }
}
