package the_ride.the_ride_backend.Models.Admin;

import the_ride.the_ride_backend.Models.BaseModels.UserBaseModel;

/*
 * For internal Users
 */
public class BackEndUser extends UserBaseModel<Integer> {
    public BackEndUser() {
        super("superUser");
    }
}
